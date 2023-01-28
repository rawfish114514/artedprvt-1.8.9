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

public class CommandApkg extends CommandBase {
    public CommandApkg(String nameIn){
        name=nameIn;
    }

    public final String name;
    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.apkg.usage";
    }

    @Override
    public void processCommand(ICommandSender senderIn, String[] argsIn) throws CommandException {
        if(argsIn.length<1){
            throw new WrongUsageException("commands.apkg.usage");
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
            //没有文件
            throw new CommandException("找不到文件 请检查参数");
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
        String dir=System.getProperties().get("user.dir").toString()+"/artedprvt/lib/"+pack.replace('.','/')+".apkg";//项目目录
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
}
