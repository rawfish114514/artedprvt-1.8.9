package rawfish.artedprvt.script;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.Arrays;
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
    public List getArgs(){
        return Arrays.asList(pro.args);
    }
    //输出消息
    public void print(Object object){
        sender.addChatMessage(new ChatComponentText(String.valueOf(object)));
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
    public Thread createThread(Runnable target){
        return new ScriptThread(pro,target);
    }
}
