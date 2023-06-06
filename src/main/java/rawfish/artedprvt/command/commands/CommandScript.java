package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.core.ScriptLanguage;
import rawfish.artedprvt.core.ScriptProcess;
import rawfish.artedprvt.core.FrameProperties;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandScript extends Command {
    public CommandScript(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()<1){
            CommandMessages.exception(getCommandName(),"参数不能为空");
            return;
        }
        String pack=args.get(0);
        List<String> scriptArgs=args.subList(1,args.size());

        try {
            ScriptProcess scriptProcess=new ScriptProcess("script",pack,scriptArgs);
            scriptProcess.start();
        } catch (Exception e) {
            CommandMessages.exception(getCommandName(),"脚本进程创建失败");
        }
    }

    @Override
    public List<String> tab(List<String> args) {
        List<String> opt=new ArrayList<>();
        String lastArgs;
        if(args.size()==0){
            lastArgs="";
        }else if(args.size()==1){
            lastArgs=args.get(args.size()-1);
        }else{
            return opt;
        }
        //包名
        File script=new File(FrameProperties.props.get("frame.dir")+"/src/script");
        if(script.isDirectory()){
            List<String> packs=pack(script,"");
            opt.addAll(match(packs,lastArgs));
        }
        return opt;
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
}
