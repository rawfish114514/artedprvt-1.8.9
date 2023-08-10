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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class CommandApkg extends Command {
    public Pattern packPattern=Pattern.compile("(([^\\/]+\\/)*)([^\\/]+)");

    public CommandApkg(String commandName) {
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
            CommandMessages.exception(getName(),"cms2");
            return;
        }
        List<String> scriptArgs=args.subList(1,args.size());

        try {
            ScriptProcess scriptProcess=new ScriptProcess("apkg",pack,scriptArgs);
            scriptProcess.start();
        } catch (Exception e) {
            e.printStackTrace(System.err);
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
            File script = new File(FrameProperties.props.get("frame.dir") + "/lib");
            if (script.isDirectory()) {
                List<String> packs = pack(script, "");
                opt.addAll(match(packs, lastArgs));
            }
            opt.sort(Comparator.comparingInt(String::length));
            return opt;
        }else if(args.size()>1) {
            /*补全脚本参数*/
            return scriptComplete(args.get(0),args.subList(1,args.size()));
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

    public List<String> scriptComplete(String pack,List<String> args){
        //获取补全脚本代码
        String code=null;
        String abbr=null;
        try {
            for (ScriptLanguage language : ScriptLanguage.values()) {
                String a=language.getAbbr();
                String f=readCompleteFile(pack,a);
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

    public String readCompleteFile(String pack,String abbr) throws Exception{
        File file=new File(FrameProperties.props.get("frame.dir")+"/lib/"+pack+".apkg");
        String target="complete."+abbr;
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
}
