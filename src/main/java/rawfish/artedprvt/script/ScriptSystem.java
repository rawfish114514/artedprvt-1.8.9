package rawfish.artedprvt.script;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentSelector;
import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import org.mozilla.javascript.NativeJavaClass;
import rawfish.artedprvt.id.FormatCode;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    /**
     * 获取参数
     * @param pack 调用者
     * @return 写在包名后的参数列表
     */
    public List getArgs(String pack){
        return Arrays.asList(pro.args);
    }

    /**
     * 打印消息
     * @param pack 调用者
     * @param object 消息
     */
    public void print(String pack,Object object){
        if(ScriptConst.chat) {
            sender.addChatMessage(new ChatComponentText(String.valueOf(object)));
        }
    }

    protected SimpleDateFormat datef = new SimpleDateFormat("HH:mm:ss");

    /**
     * 输出调试信息
     * @param pack 调用者
     * @param object 信息
     * @param hover 鼠标悬浮信息 可为null
     */
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

    /**
     * 导入模块 如果目标的第一个字符是'-'则方法变为导入java类
     * @param pack 调用者
     * @param target 目标
     * @return 在脚本中寻找js文件运行并返回 导入java类则返回null
     */
    public Object importModule(String pack,String target){
        if(target.length()>0&&target.charAt(0)=='-'){
            importJava(pack,target.substring(1));
            return null;
        }
        ScriptUnit module=pro.env.get(target);
        //检查加载
        if(module==null){
            pro.load(target,pack);
            module=pro.env.get(target);
        }
        return module.export;
    }

    /**
     * 导出模块 后导出的对象会覆盖先导出的对象
     * @param pack 调用者
     * @param object 导出对象
     */
    public void exportModule(String pack,Object object){
        ScriptUnit module=pro.env.get(pack);
        module.export=object;
    }

    //导入java类
    public void importJava(String pack,String className){
        int ind=className.lastIndexOf('.');
        if(ind==-1){
            throw new RuntimeException("java类名异常");
        }
        ScriptUnit module=pro.env.get(pack);
        String classPack=className.substring(0,ind);
        String simpleName=className.substring(ind+1);
        if(simpleName.equals("*")){
            //按需类型导入
            throw new RuntimeException("暂不支持按需类型导入");
        }else{
            //单类型导入
            try {
                Class clas=Class.forName(className);
                module.scope.put(simpleName,module.scope,new NativeJavaClass(module.scope,clas));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 创建线程 这个线程被主线程管理 在脚本里创建线程都应该使用这个方法而不是
     * @param pack 调用者
     * @param target 函数接口
     * @return 线程
     */
    public Thread createThread(String pack,Runnable target){
        return new ScriptThread(pro,target);
    }

    //测试接口
    public Object runFunction(String pack,Function function,Object[] args){
        return function.invoke(args);
    }
}
