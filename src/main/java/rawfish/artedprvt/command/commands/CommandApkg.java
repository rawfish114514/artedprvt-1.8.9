package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.core.ScriptProcess;
import rawfish.artedprvt.core.FrameProperties;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandApkg extends Command {
    public Pattern packPattern=Pattern.compile("(([a-zA-Z_][0-9a-zA-Z_]*\\.)*)([a-zA-Z_][0-9a-zA-Z_]*)");

    public CommandApkg(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()<1){
            CommandMessages.exception(getCommandName(),"参数不能为空");
            return;
        }
        String pack=args.get(0);
        Matcher matcher=packPattern.matcher(pack);
        if(!matcher.matches()){
            CommandMessages.exception(getCommandName(),"包名格式异常");
            return;
        }
        List<String> scriptArgs=args.subList(1,args.size());

        try {
            ScriptProcess scriptProcess=new ScriptProcess("apkg",pack,scriptArgs);
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
        File script=new File(FrameProperties.props.get("frame.dir")+"/lib");
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
                if(ind>0&&abbr.equals("apkg")){
                    packs.add(p+name.substring(0,ind));
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
                }
            }
        }
        return npacks;
    }
}
