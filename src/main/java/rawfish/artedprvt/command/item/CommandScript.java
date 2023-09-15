package rawfish.artedprvt.command.item;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.core.ScriptLanguage;
import rawfish.artedprvt.core.ScriptProcess;
import rawfish.artedprvt.core.FrameProperties;
import rawfish.artedprvt.core.engine.Engines;
import rawfish.artedprvt.core.engine.ServiceEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 从src目录创建脚本进程
 */
public class CommandScript extends Command {
    public Pattern packPattern=Pattern.compile("([a-z]+:|)(([a-zA-Z_][0-9a-zA-Z_]*\\.)*)([a-zA-Z_][0-9a-zA-Z_]*)");

    public CommandScript(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()<1){
            CommandMessages.exception(getName(),"cms1");
            return;
        }
        String pack=args.get(0);
        Matcher matcher=packPattern.matcher(pack);
        if(!matcher.matches()){
            CommandMessages.exception(getName(),"cms17");
            return;
        }
        List<String> scriptArgs=args.subList(1,args.size());

        try {
            ScriptProcess scriptProcess=new ScriptProcess("script",pack,scriptArgs);
            scriptProcess.start();
        } catch (Exception e) {
            e.printStackTrace();
            CommandMessages.exception(getName(),"cms3",e.getMessage());
        }
    }

    @Override
    public List<String> complete(List<String> args) {
        if(args.size()==1) {
            /*补全包名*/
            List<String> opt = new ArrayList<>();
            String lastArgs=args.get(0);
            //包名
            File script = new File(FrameProperties.props.get("frame.dir") + "/src/script");
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
                        Object result=engine.unwrap(engine.call(code, "complete", args.subList(1,args.size())));
                        if(result instanceof Collection){
                            List<String> stringList=new ArrayList<>();
                            for(Object obj:(Collection)result){
                                stringList.add(String.valueOf(obj));
                            }
                            return startWith(args,new ArrayList<>(stringList));
                        }else{
                            return getEmptyList();
                        }
                    }catch (Exception e){
                        e.printStackTrace(System.err);
                    }
                }
            }
        }
        return getEmptyList();
    }

    @Override
    public List<String> format(List<String> args) {
        List<String> fl=new ArrayList<>();
        String a0=args.get(0);
        List<String> all=complete(stringList(""));
        String c;
        for(int i=0;i<all.size();i++){
            c=all.get(i);
            if(c.equals(a0)||c.substring(c.indexOf(':')+1).equals(a0)){
                fl.add("6");
            }
        }
        if(fl.size()==0){
            fl.add("c");
        }
        if(args.size()>1) {
            /*格式脚本参数*/
            literal(false);
            if (code != null) {
                ServiceEngine engine = Engines.getService(abbr);
                if (engine != null) {
                    try {
                        Object result = engine.unwrap(engine.call(code, "format", args.subList(1, args.size())));
                        if (result instanceof Collection) {
                            List<String> stringList = new ArrayList<>();
                            for (Object obj : (Collection) result) {
                                stringList.add(String.valueOf(obj));
                            }
                            fl.addAll(stringList);
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
    public String info(List<String> args) {
        if(args.size()==1) {
            if(args.get(0).isEmpty()){
                return CommandMessages.translate("cis6");
            }
            String a0 = args.get(0);
            List<String> all = complete(stringList(""));
            String c;
            for (int i = 0; i < all.size(); i++) {
                c = all.get(i);
                if (c.equals(a0) || c.substring(c.indexOf(':')+1).equals(a0)) {
                    return getEmptyString();
                }
            }
            return CommandMessages.translate("cis2");
        }
        /*信息脚本参数*/
        literal(false);
        if(code!=null){
            ServiceEngine engine= Engines.getService(abbr);
            if(engine!=null){
                try {
                    Object result=engine.unwrap(engine.call(code, "info", args.subList(1,args.size())));
                    if(result!=null){
                        return String.valueOf(result);
                    }
                }catch (Exception e){
                    e.printStackTrace(System.err);
                }
            }
        }
        return getEmptyString();
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
        File file=new File(FrameProperties.props.get("frame.dir")+"/src/literal."+abbr);
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

    public List<String> startWith(List<String> args,List<String> cs){
        List<String> ns=cs.stream().filter(v->v.startsWith(args.get(args.size()-1))).collect(Collectors.toList());
        if(ns.size()>0){
            return ns;
        }
        return cs;
    }
}
