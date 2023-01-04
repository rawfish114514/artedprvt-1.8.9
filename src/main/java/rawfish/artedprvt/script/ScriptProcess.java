package rawfish.artedprvt.script;

import net.minecraft.command.ICommandSender;
import org.mozilla.javascript.Context;

import java.io.*;
import java.util.*;

/**
 * 脚本进程
 * 运行脚本时创建
 */
public class ScriptProcess {
    public static List<ScriptProcess> proList=new ArrayList<>();
    protected String dir;
    protected ICommandSender sender;
    protected String pack;
    protected String[] args;
    protected Context rhino;
    protected ScriptSystem sys;
    protected Map<String,ScriptUnit> env;
    protected MainThread thread;
    protected List<ScriptThread> tl;

    protected long time;
    protected int ret;
    public ScriptProcess(ICommandSender senderIn,String packIn, String[] argsIn){
        ret=0;//进程创建 无效退出
        sender=senderIn;
        pack=packIn;
        args=argsIn;
        dir=System.getProperties().get("user.dir").toString()+"/artedprvt/script/";

        proList.add(this);
    }

    /**
     * 读取工作目录下的文件
     * @param packIn 模块名
     * @return
     */
    protected String readString(String packIn){
        if(packIn.indexOf("/")!=-1){
            try {
                throw new FileNotFoundException("pack: "+packIn+" (用'.'分隔符而不是'/')");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        if(!withPackRule(packIn)){
            try {
                throw new FileNotFoundException("pack: "+packIn+" (开头为英文字母后接英文字母数字下划线)");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        String path=packIn.replace('.','/')+".js";
        File file=new File(dir+path);
        Reader reader;
        StringBuilder sb;
        try {
            reader=new FileReader(file);
            sb=new StringBuilder();
            while(true){
                int n=reader.read();
                if(n==-1) {
                    break;
                }
                sb.append((char)n);
            }
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    protected boolean withPackRule(String path){
        String[] layers=path.split("\\.");
        for(String s:layers){
            if(!s.matches("^[a-zA-Z][a-zA-Z0-9_]*$")){
                return false;
            }
        }
        return true;
    }

    /**
     * 加载模块
     * @param packIn 包名
     * @param invokerIn 调用上级
     */
    public void load(String packIn,String invokerIn){
        ScriptUnit module=new ScriptUnit(this,readString(packIn),packIn);
        env.put(module.pack,module);
        module.run(invokerIn);
    }


    //准备工作并运行
    public void start(){
        ret=1;//进程启动 无效退出
        time=0;
        tl=new ArrayList<>();
        ScriptThread.n=0;
        thread=new MainThread(this);

        thread.start();
    }
    //准备工作完成
    public void begin(){
        ret=2;//进程准备 正常退出
        time=new Date().getTime();
        sys.print(pack,"\u00a77run:\u00a7a " + pack);
    }
    //终止进程
    public void stop(){
        if(ret==1) {
            ret=-1;//进程终止 无效退出
        }
        if(ret==2){
            ret=-2;//进程终止 非正常退出
        }
        thread.jstop();
    }
    //运行结束
    public void end(){
        time=new Date().getTime()-time;
        if(ret==2) {
            sys.print(pack,"\u00a77end:\u00a7a " + pack + "\u00a77(" + time + "ms)");
        }else if(ret==-2){
            sys.print(pack,"\u00a74end:\u00a7a " + pack + "\u00a77(" + time + "ms)");
        }
        proList.remove(this);
        ret=-2;//进程结束
    }
}
