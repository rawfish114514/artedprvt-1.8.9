package rawfish.artedprvt.script;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentSelector;
import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import rawfish.artedprvt.id.FormatCode;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 脚本系统
 * 提供框架层的功能
 */
public class ScriptSystem {
    protected ScriptProcess pro;
    protected ICommandSender sender;
    public ScriptSystem(ScriptProcess proIn,ICommandSender senderIn){
        pro=proIn;
        sender=senderIn;
    }

    //获取参数
    public List getArgs(String pack){
        return Arrays.asList(pro.args);
    }
    //输出消息
    public void print(String pack,Object object){
        if(ScriptConst.chat) {
            sender.addChatMessage(new ChatComponentText(String.valueOf(object)));
        }
    }

    protected SimpleDateFormat datef = new SimpleDateFormat("HH:mm:ss");
    //输出记录
    public void log(String pack,Object object,Object hover){
        if(ScriptConst.debug) {
            Date date=new Date();
            String head;
            if(pro.pack.equals(pack)) {
                head = String.format("[%s] [%s] [%s] ", datef.format(date),pro.pack,Thread.currentThread().getName());
            }else{
                head = String.format("[%s] [%s] [%s] [%s] ", datef.format(date),pro.pack,Thread.currentThread().getName(),pack);
            }
            ChatComponentText chat=new ChatComponentText(FormatCode.COLOR_7+head+FormatCode.FONT_r+String.valueOf(object));
            String hs=String.valueOf(hover);
            if(!(hs.equals("null")||hs.equals("undefined"))){
                chat.setChatStyle(new ChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(String.valueOf(hover)))));
            }

            sender.addChatMessage(chat);

        }
    }
    public void log(String pack,Object object){
        log(pack,object,null);
    }

    //导入模块
    public Object importModule(String pack,String target){
        ScriptUnit module=pro.env.get(target);
        //检查加载
        if(module==null){
            pro.load(target,pack);
            module=pro.env.get(target);
        }
        return module.export;
    }

    //导出模块
    public void exportModule(String pack,Object object){
        ScriptUnit module=pro.env.get(pack);
        module.export=object;
    }

    //创建线程
    public Thread createThread(String pack,Runnable target){
        return new ScriptThread(pro,target);
    }

    //测试接口
    public Object runFunction(String pack,Function function,Object[] args){
        return function.invoke(args);
    }
}
