package rawfish.artedprvt.command;

import net.minecraft.command.*;
import net.minecraft.util.BlockPos;
import rawfish.artedprvt.scriptold.ScriptProcess;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandScript extends CommandBase {
    public CommandScript(String nameIn){
        name=nameIn;
    }

    public final String name;
    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.script.usage";
    }

    @Override
    public void processCommand(ICommandSender senderIn, String[] argsIn) throws CommandException {
        if(argsIn.length<1){
            throw new WrongUsageException("commands.script.usage");
        }

        //去空参数
        List<String> slist=new ArrayList<>();
        for(String arg:argsIn){
            if(!arg.equals("")){
                slist.add(arg);
            }
        }


        int packindex=calpack(slist);
        if(packindex==-1){
            //没有包名
            throw new CommandException("找不到包名 请检查参数");
        }

        List<String> sargs=sargs(slist,packindex);//命令参数
        for(String sarg:sargs){
            if(!containsSargs(sarg)){
                throw new CommandException("无效参数: "+sarg);
            }
            if(repeatSargs(sargs,sarg)){
                throw new CommandException("重复参数: "+sarg);
            }
        }

        String pack=pack(slist,packindex);//脚本包名
        List<String> args=args(slist,packindex);//脚本参数
        String dir=System.getProperties().get("user.dir").toString()+"/artedprvt/src";//项目目录
        ScriptProcess script=new ScriptProcess(senderIn,getCommandName(),dir,sargs,pack,args);

        if(script.getRet()==0) {
            script.start();
        }
    }

    //计算包名索引
    public int calpack(List<String> argsIn){
        //每次都匹配命令参数直到匹配到非命令参数 再区分包名和脚本参数
        for(int i=0;i<argsIn.size();i++){
            if(!isSarg(argsIn.get(i))){
                //包名索引
                return i;
            }
        }
        return -1;
    }
    //分配参数
    public List<String> sargs(List<String> argsIn,int packindex){
        //[0,p)
        List<String> list=new ArrayList<>();
        for(int i=0;i<packindex;i++){
            list.add(argsIn.get(i));
        }
        return list;
    }
    public String pack(List<String> argsIn,int packindex){
        //[p]
        return argsIn.get(packindex);
    }
    public List<String> args(List<String> argsIn,int packindex){
        //[p+1,l)
        List<String> list=new ArrayList<>();
        for(int i=0;i<argsIn.size()-packindex-1;i++){
            list.add(argsIn.get(i+packindex+1));
        }
        return list;
    }


    //判断命令参数存在
    public boolean containsSargs(String sarg){
        return ScriptProcess.sargList.contains(sarg);
    }

    //判断命令参数重复
    public boolean repeatSargs(List<String> sargs,String sarg){
        int n=0;
        for(String s:sargs){
            if(s.equals(sarg)){
                n++;
            }
        }
        return n>1;
    }

    protected static String sargMatcher="-\\w+";
    //判断命令参数合法
    public boolean isSarg(String arg){
        return arg.matches(sargMatcher);
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        if(!sender.getEntityWorld().isRemote){
            return null;
        }
        List<String> opt=new ArrayList<>();
        String lastArgs=args[args.length-1];
        if(lastArgs.length()>0&&lastArgs.charAt(0)=='-'){
            //系统参数
            opt.addAll(ScriptProcess.sargList);
        }else{
            //包名
            File script=new File(System.getProperties().get("user.dir").toString()+"/artedprvt/src/script");
            if(script.isDirectory()){
                List<String> packs=pack(script,"");
                opt.addAll(match(packs,lastArgs));
            }
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
                if(ind>0&&name.substring(ind).equals(".js")){
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
                if(pack.substring(0,arg.length()).equals(arg)){
                    npacks.add(pack);
                }
            }
        }
        return npacks;
    }
}
