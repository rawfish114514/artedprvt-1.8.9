package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.InfoHandler;
import rawfish.artedprvt.command.util.CommandMessages;
import rawfish.artedprvt.command.util.FormatHandlerListBuilder;
import rawfish.artedprvt.command.util.Literals;
import rawfish.artedprvt.core.WorkSpace;
import rawfish.artedprvt.core.localization.types.CIS;
import rawfish.artedprvt.core.localization.types.CMS;
import rawfish.artedprvt.core.script.ScriptLanguage;
import rawfish.artedprvt.core.script.ScriptProcess;
import rawfish.artedprvt.core.script.engine.Engines;
import rawfish.artedprvt.core.script.engine.ServiceEngine;
import rawfish.artedprvt.core.script.struct.SourceFileLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 从src目录创建脚本进程
 */
public class CommandScript extends Command {
    public static final Function<String[],String> SCRIPT= strings -> "/src/script";
    public static final Function<String[],String> LITERAL= strings -> "/src/literal."+strings[0];
    public Pattern packPattern=Pattern.compile("([a-z]+:|)(([a-zA-Z_][0-9a-zA-Z_]*\\.)*)([a-zA-Z_][0-9a-zA-Z_]*)");

    public CommandScript(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()<1){
            CommandMessages.exception(getName(), CMS.cms1);
            return;
        }
        String pack=args.get(0);
        Matcher matcher=packPattern.matcher(pack);
        if(!matcher.matches()){
            CommandMessages.exception(getName(),CMS.cms17);
            return;
        }
        List<String> scriptArgs=args.subList(1,args.size());

        try {
            ScriptProcess scriptProcess=new ScriptProcess(pack,new SourceFileLoader(WorkSpace.derivation(WorkSpace.SRC)),scriptArgs);
            scriptProcess.start();
        } catch (Exception e) {
            e.printStackTrace();
            CommandMessages.exception(getName(),CMS.cms3,e.getMessage());
        }
    }

    @Override
    public List<String> complete(List<String> args) {
        if(args.size()==1) {
            /*补全包名*/
            List<String> opt = new ArrayList<>();
            String lastArgs=args.get(0);
            //包名
            File script = new File(WorkSpace.derivation(SCRIPT));
            if (script.isDirectory()) {
                List<String> packs = pack(script, "");
                opt.addAll(match(packs, lastArgs));
            }
            opt.sort(Comparator.comparingInt(String::length));
            return opt;
        }else if(args.size()>1) {
            /*补全脚本参数*/
            literal(true);
            if(code!=null){
                ServiceEngine engine= Engines.getService(abbr);
                if(engine!=null){
                    try {
                        Object result=engine.unwrap(engine.call(code, "complete", args.subList(1,args.size()), Literals.class));
                        if(result instanceof Collection){
                            List<String> stringList=new ArrayList<>();
                            for(Object obj:(Collection)result){
                                stringList.add(String.valueOf(obj));
                            }
                            return new ArrayList<>(stringList);
                        }else{
                            return Literals.emptyComplete();
                        }
                    }catch (Exception e){
                        e.printStackTrace(System.err);
                    }
                }
            }
        }
        return Literals.emptyComplete();
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        FormatHandlerListBuilder fl=Literals.formatListBuilder();
        String a0=args.get(0);
        List<String> all=complete(Literals.stringListBuilder().adds(""));
        String c;
        for(int i=0;i<all.size();i++){
            c=all.get(i);
            if(c.equals(a0)||c.substring(c.indexOf(':')+1).equals(a0)){
                fl.append("6");
            }
        }
        if(fl.size()==0){
            fl.append("c");
        }
        if(args.size()>1) {
            /*格式脚本参数*/
            literal(false);
            if (code != null) {
                ServiceEngine engine = Engines.getService(abbr);
                if (engine != null) {
                    try {
                        Object result = engine.unwrap(engine.call(code, "format", args.subList(1, args.size()), Literals.class));
                        if (result instanceof Collection) {
                            FormatHandlerListBuilder scriptfl = Literals.formatListBuilder();
                            for (Object obj : (Collection) result) {
                                if(obj instanceof FormatHandler){
                                    scriptfl.add((FormatHandler) obj);
                                }else {
                                    scriptfl.append(String.valueOf(obj));
                                }
                            }
                            fl.addAll(scriptfl);
                        }
                    } catch (Exception e) {
                        e.printStackTrace(System.err);
                    }
                }
            }
        }
        return fl;
    }

    @Override
    public InfoHandler info(List<String> args) {
        if(args.size()==1) {
            if(args.get(0).isEmpty()){
                return Literals.infoFactory().string(CIS.cis6);
            }
            String a0 = args.get(0);
            List<String> all = complete(Literals.stringListBuilder().adds(""));
            String c;
            for (int i = 0; i < all.size(); i++) {
                c = all.get(i);
                if (c.equals(a0) || c.substring(c.indexOf(':')+1).equals(a0)) {
                    return Literals.emptyInfo();
                }
            }
            return Literals.infoFactory().string(CIS.cis2);
        }
        /*信息脚本参数*/
        literal(false);
        if(code!=null){
            ServiceEngine engine= Engines.getService(abbr);
            if(engine!=null){
                try {
                    Object result=engine.unwrap(engine.call(code, "info", args.subList(1,args.size()), Literals.class));
                    if(result!=null){
                        if(result instanceof InfoHandler){
                            return (InfoHandler) result;
                        }
                        return Literals.infoFactory().string(String.valueOf(result));
                    }
                }catch (Exception e){
                    e.printStackTrace(System.err);
                }
            }
        }
        return Literals.emptyInfo();
    }

    @Override
    public void reset(){
        code=null;
        abbr=null;
    }

    public List<String> pack(File dir,String p){
        List<String> packs=new ArrayList<>();
        File[] files=dir.listFiles();
        List<File> fileList=new ArrayList<>();
        fileList.addAll(Arrays.asList(files));
        for (int i = 0; i < fileList.size(); i++) {
            File file=fileList.get(i);
            if(file.isFile()){
                //是文件
                String name=file.getName();
                int ind=name.lastIndexOf('.');
                String abbr=name.substring(ind+1);
                if(ind>0&&(Engines.getLanguageOfAbbr(abbr)!=null)){
                    packs.add(abbr+":"+p+name.substring(0,ind));
                }
            }else{
                //是目录
                packs.addAll(pack(file,p+file.getName()+"."));
            }
        }
        return packs;
    }

    public List<String> match(List<String> packs,String arg){
        List<String> npacks=new ArrayList<>();
        for(String pack:packs){
            if(pack.length()>=arg.length()){
                if(pack.startsWith(arg)){
                    npacks.add(pack);
                }else{
                    if(pack.contains(":")){
                        String bpack=pack.substring(pack.indexOf(":")+1);
                        if(bpack.startsWith(arg)){
                            npacks.add(pack);
                        }
                    }
                }
            }
        }
        return npacks;
    }


    public String code=null;
    public String abbr=null;

    /**
     *
     * @param flag 强制更新
     */
    public void literal(boolean flag){
        if(flag||code==null||abbr==null){
            try {
                for (ScriptLanguage language : Engines.getLanguages()) {
                    String a=language.getAbbr();
                    String f= readLiteralFile(a);
                    if(f!=null){
                        code=f;
                        abbr=a;
                        break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace(System.err);
            }
        }
    }

    public String readLiteralFile(String abbr) throws Exception{
        File file=new File(WorkSpace.derivation(LITERAL,abbr));
        if(file.isFile()){
            Reader reader=new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            StringBuilder sb=new StringBuilder();
            int n;
            while(true){
                n=reader.read();
                if(n==-1){
                    break;
                }
                sb.append((char)n);
            }
            return sb.toString();
        }else{
            return null;
        }
    }
}
