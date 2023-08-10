package rawfish.artedprvt.command.item;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.core.ScriptLanguage;
import rawfish.artedprvt.core.ScriptProcess;
import rawfish.artedprvt.core.FrameProperties;
import rawfish.artedprvt.core.engine.ServiceEngine;
import rawfish.artedprvt.core.engine.ServiceEngines;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            return scriptComplete(args.subList(1,args.size()));
        }
        return nullTab;
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
                if(ind>0&&(ScriptLanguage.abbrOf(abbr)!=null)){
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

    public List<String> scriptComplete(List<String> args){
        //获取补全脚本代码
        String code=null;
        String abbr=null;
        try {
            for (ScriptLanguage language : ScriptLanguage.values()) {
                String a=language.getAbbr();
                String f=readCompleteFile(a);
                if(f!=null){
                    code=f;
                    abbr=a;
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
        if(code!=null){
            ServiceEngine engine= ServiceEngines.getService(abbr);
            if(engine!=null){
                try {
                    Object result=engine.call(code, "complete", args);
                    List rl=(List)result;
                    List<String> stringList=new ArrayList<>();
                    for(int i=0;i<rl.size();i++){
                        stringList.add(rl.get(i).toString());
                    }
                    return stringList;
                }catch (Exception e){
                    e.printStackTrace(System.err);
                }
            }
        }
        return nullTab;
    }

    public String readCompleteFile(String abbr) throws Exception{
        File file=new File(FrameProperties.props.get("frame.dir")+"/src/complete."+abbr);
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
