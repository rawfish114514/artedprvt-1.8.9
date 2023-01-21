package rawfish.artedprvt.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.*;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.server.MinecraftServer;
import rawfish.artedprvt.script.ScriptProcess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        argsIn=new String[slist.size()];
        for(int i=0;i<slist.size();i++){
            argsIn[i]=slist.get(i);
        }


        int packindex=calpack(argsIn);
        if(packindex==-1){
            //没有包名
            throw new CommandException("找不到包名 请检查参数");
        }

        String[] sargs=sargs(argsIn,packindex);//命令参数
        for(String sarg:sargs){
            if(!containsSargs(sarg)){
                throw new CommandException("无效参数: "+sarg);
            }
            if(repeatSargs(sargs,sarg)){
                throw new CommandException("重复参数: "+sarg);
            }
        }

        String pack=pack(argsIn,packindex);//脚本包名
        String[] args=args(argsIn,packindex);//脚本参数

        ScriptProcess script=new ScriptProcess(senderIn,sargs,pack,args);

        if(script.getRet()==0) {
            script.start();
        }
    }

    //计算包名索引
    public int calpack(String [] argsIn){
        //每次都匹配命令参数直到匹配到非命令参数 再区分包名和脚本参数
        for(int i=0;i<argsIn.length;i++){
            if(!isSarg(argsIn[i])){
                //包名索引
                return i;
            }
        }
        return -1;
    }
    //分配参数
    public String[] sargs(String [] argsIn,int packindex){
        //[0,p)
        String[] sargs=new String[packindex];
        System.arraycopy(argsIn, 0, sargs, 0, sargs.length);
        return sargs;
    }
    public String pack(String [] argsIn,int packindex){
        //[p]
        return argsIn[packindex];
    }
    public String[] args(String [] argsIn,int packindex){
        //[p+1,l)
        String[] args=new String[argsIn.length-packindex-1];
        System.arraycopy(argsIn, packindex+1, args, 0, args.length);
        return args;
    }


    //判断命令参数存在
    public boolean containsSargs(String sarg){
        return ScriptProcess.sargList.contains(sarg);
    }

    //判断命令参数重复
    public boolean repeatSargs(String[] sargs,String sarg){
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
}
