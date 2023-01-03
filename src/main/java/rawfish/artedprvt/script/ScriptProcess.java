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
        sender=senderIn;
        pack=packIn;
        args=argsIn;
        dir=System.getProperties().get("user.dir").toString()+"/artedprvt/";

        proList.add(this);
    }

    /**
     * 读取工作目录下的文件
     * @param pack 模块名
     * @return
     */
    protected String readString(String pack){
        if(pack.indexOf("/")!=-1){
            try {
                throw new FileNotFoundException("pack: "+pack+" (用'.'分隔符而不是'/')");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        String path=pack.replace('.','/')+".js";
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
        tl=new ArrayList<>();
        thread=new MainThread(this);

        thread.start();
    }
    //准备工作完成
    public void begin(){
        time=new Date().getTime();
        ret=0;
        sys.print(pack,"\u00a77run:\u00a7a " + pack);
    }
    //终止进程
    public void stop(){
        ret=-1;
        thread.jstop();
    }
    //运行结束
    public void end(){
        time=new Date().getTime()-time;
        if(ret==0) {
            sys.print(pack,"\u00a77end:\u00a7a " + pack + "\u00a77(" + time + "ms)");
        }else{
            sys.print(pack,"\u00a74end:\u00a7a " + pack + "\u00a77(" + time + "ms)");
        }
        proList.remove(this);
    }
}
