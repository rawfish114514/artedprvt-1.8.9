package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.*;
import rawfish.artedprvt.command.util.CommandMessages;
import rawfish.artedprvt.command.util.FormatHandlerListBuilder;
import rawfish.artedprvt.command.util.Literals;
import rawfish.artedprvt.core.ScriptLanguage;
import rawfish.artedprvt.core.ScriptProcess;
import rawfish.artedprvt.core.FrameProperties;
import rawfish.artedprvt.core.engine.Engines;
import rawfish.artedprvt.core.engine.ServiceEngine;
import rawfish.artedprvt.core.localization.types.CIS;
import rawfish.artedprvt.core.localization.types.CMS;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 从apkg文件创建脚本进程
 */
public class CommandApkg extends Command {
    public Pattern packPattern=Pattern.compile("(([^\\/]+\\/)*)([^\\/]+)");

    public CommandApkg(String commandName) {
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
            CommandMessages.exception(getName(),CMS.cms2);
            return;
        }
        List<String> scriptArgs=args.subList(1,args.size());

        try {
            ScriptProcess scriptProcess=new ScriptProcess("apkg",pack,scriptArgs);
            scriptProcess.start();
        } catch (Exception e) {
            e.printStackTrace(System.err);
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
            File script = new File(FrameProperties.props.get("frame.dir") + "/lib");
            if (script.isDirectory()) {
                List<String> packs = pack(script, "");
                opt.addAll(match(packs, lastArgs));
            }
            opt.sort(Comparator.comparingInt(String::length));
            return opt;
        }else if(args.size()>1) {
            /*补全脚本参数*/
            literal(args.get(0),true);
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
                            return startWith(args,new ArrayList<>(stringList));
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
        File apkg = new File(FrameProperties.props.get("frame.dir") + "/lib/"+args.get(0)+".apkg");
        FormatHandlerListBuilder fl=Literals.formatListBuilder();
        if(apkg.isFile()){
            fl.append("6");
        }else{
            fl.append("c");
            return fl;
        }
        if(args.size()>1) {
            /*格式脚本参数*/
            literal(args.get(0), false);
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
        if(args.size()==1){
            if(args.get(0).isEmpty()){
                return Literals.infoBuilder().string(CIS.cis5);
            }
            File apkg = new File(FrameProperties.props.get("frame.dir") + "/lib/"+args.get(0)+".apkg");
            if(apkg.isFile()){
                return Literals.emptyInfo();
            }
            return Literals.infoBuilder().string(CIS.cis1);
        }
        /*信息脚本参数*/
        literal(args.get(0),false);
        if(code!=null){
            ServiceEngine engine= Engines.getService(abbr);
            if(engine!=null){
                try {
                    Object result=engine.unwrap(engine.call(code, "info", args.subList(1,args.size()), Literals.class));
                    if(result!=null){
                        if(result instanceof InfoHandler){
                            return (InfoHandler) result;
                        }
                        return Literals.infoBuilder().string(String.valueOf(result));
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
        pack=null;
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
                if(ind>0&&abbr.equals("apkg")){
                    packs.add(p+name.substring(0,ind));
                }
            }else{
                //是目录
                packs.addAll(pack(file,p+file.getName()+"/"));
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
                }
            }
        }
        return npacks;
    }


    public String pack=null;
    public String code=null;
    public String abbr=null;

    /**
     *
     * @param pack
     * @param flag 强制更新
     */
    public void literal(String pack,boolean flag){
        if(flag||(!Objects.equals(this.pack, pack))||code==null||abbr==null){
            this.pack=pack;
            try {
                code=null;
                for (ScriptLanguage language : Engines.getLanguages()) {
                    String a=language.getAbbr();
                    String f= readLiteralFile(pack,a);
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

    public String readLiteralFile(String pack, String abbr) throws Exception{
        File file=new File(FrameProperties.props.get("frame.dir")+"/lib/"+pack+".apkg");
        String target="literal."+abbr;
        if(file.isFile()){
            boolean t=false;
            StringBuilder sb=new StringBuilder();
            ZipInputStream zip = new ZipInputStream(
                    new FileInputStream(file),
                    Charset.forName("cp437"));
            Reader reader=new InputStreamReader(zip, StandardCharsets.UTF_8);
            ZipEntry entry;
            int n;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().equals(target)) {
                    while(true){
                        n=reader.read();
                        if(n==-1){
                            break;
                        }
                        sb.append((char) n);
                    }
                    t=true;
                    break;
                }
                zip.closeEntry();
            }
            return t?sb.toString():null;
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
