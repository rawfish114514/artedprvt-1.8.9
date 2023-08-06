package rawfish.artedprvt.core;

import rawfish.artedprvt.core.struct.FileLoader;
import rawfish.artedprvt.mi.ChatProvider;
import rawfish.artedprvt.mi.PrintChat;
import rawfish.artedprvt.core.engine.ScriptEngine;
import rawfish.artedprvt.core.struct.ScriptModule;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 脚本系统
 * 提供通用的公共方法
 */
public class ScriptSystem {
    private ScriptProcess process;
    private ScriptLogger logger;
    private FileLoader fileLoader;
    private PrintChat printChat;
    public ScriptSystem(ScriptProcess process){
        this.process=process;
        logger=process.getScriptLogger();
        fileLoader=process.getFileLoader();
        printChat=new PrintChat();
        printChat.isLog=false;
        printChat.longtime=true;
    }

    /**
     * 打印类型
     */
    public static final int
    CHAT=0,
    DEBUG=1,
    HIGH=2,
    DISPLAY=3;

    /**
     * 打印开关
     */
    public static boolean
    B_CHAT=true,
    B_DEBUG=false;

    /**
     * 打印
     * @param type 打印类型
     * @param chat 聊天信息
     */
    public void print(int type,String chat){
        if(type==CHAT){
            logger.info(chat);
            if(B_CHAT){
                printChat.print(chat);
                return;
            }
        }
        if(type==DEBUG){
            chat=getDebugHead()+chat;
            if(B_DEBUG){
                printChat.print(chat);
                return;
            }
        }
        if(type==HIGH){
            printChat.print(chat);
            return;
        }
        if(type==DISPLAY){
            if(B_CHAT||B_DEBUG){
                printChat.print(chat);
            }
        }
    }

    /**
     * 打印
     * @param type 打印类型
     * @param chat 聊天消息
     * @param hover 悬浮聊天消息
     */
    public void print(int type,String chat,String hover){
        if(type==CHAT){
            logger.info(chat);
            if(B_CHAT){
                printChat.print(chat,hover);
                return;
            }
        }
        if(type==DEBUG){
            chat=getDebugHead()+chat;
            if(B_DEBUG){
                printChat.print(chat,hover);
                return;
            }
        }
        if(type==HIGH){
            printChat.print(chat,hover);
            return;
        }
        if(type==DISPLAY){
            if(B_CHAT||B_DEBUG){
                printChat.print(chat,hover);
            }
        }
    }

    /**
     * 打印
     * @param type 打印类型
     * @param chat 聊天消息
     * @param hover 悬浮聊天消息供应商
     */
    public void print(int type,String chat, ChatProvider hover){
        if(type==CHAT){
            logger.info(chat);
            if(B_CHAT){
                printChat.print(chat,hover);
                return;
            }
        }
        if(type==DEBUG){
            chat=getDebugHead()+chat;
            if(B_DEBUG){
                printChat.print(chat,hover);
                return;
            }
        }
        if(type==HIGH){
            printChat.print(chat,hover);
            return;
        }
        if(type==DISPLAY){
            if(B_CHAT||B_DEBUG){
                printChat.print(chat,hover);
            }
        }
    }

    private String getDebugHead(){
        String str="§7[";
        str+=process.getName();
        str+="] [";
        if(process.isThread(Thread.currentThread())){
            str+=Thread.currentThread().getName();
        }else{
            str+="§n"+Thread.currentThread().getName()+"§r§7";
        }
        str+="] ";
        return str;
    }

    private String chatAddHover(String chat,String hover){
        return chat+" -> \n"+hover;
    }

    private String chatAddHover(String chat,ChatProvider hover){
        return chat+" -> \n"+hover.getChat();
    }

    /**
     * 导入模块
     * @param moduleName 模块名
     * @return
     */
    public Object importModule(String moduleName){
        return importModule(process.getScriptLoader().loadModule(moduleName));
    }

    /**
     * 导入模块
     * @param scriptModule 模块
     * @return
     */
    public Object importModule(ScriptModule scriptModule){
        List<ScriptEngine> engines=process.getEngines();
        int s=0;
        ScriptEngine scriptEngine;
        if(scriptModule.getStatus()==ScriptModule.LOAD) {
            for (int i = 0; i < engines.size(); i++) {
                scriptEngine = engines.get(i);
                if (scriptEngine.isExecutable(scriptModule)) {
                    scriptModule.setStatus(ScriptModule.RUN);
                    scriptEngine.execute(scriptModule);
                    scriptModule.setStatus(ScriptModule.END);
                    s = 1;
                    break;
                }
            }
            if (s == 0) {
                throw new RuntimeException("找不到合适的引擎: " + scriptModule.getModuleFullName());
            }
        }
        return scriptModule.getExport();
    }

    /**
     * 导入java类
     * @param className 类名
     * @return
     */
    public Class importClass(String className){
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 导入java组
     * @param groupName 组名
     * @return
     */
    public List<Class> importClassGroup(String groupName) {
        List<Class> classes= ClassGroupSystem.get(groupName);
        if(classes!=null){
            return classes;
        }
        throw new RuntimeException("未定义的组: "+groupName);
    }

    /**
     * 导出模块
     * @param moduleName 模块名
     * @param export 导出对象
     */
    public void exportModule(String moduleName,Object export){
        exportModule(process.getScriptLoader().loadModule(moduleName),export);
    }

    /**
     * 导出模块
     * @param scriptModule 模块
     * @param export 导出对象
     */
    public void exportModule(ScriptModule scriptModule,Object export){
        scriptModule.setExport(export);
    }

    /**
     * 创建脚本线程
     * @param target
     * @return
     */
    public ScriptThread createThread(Runnable target){
        return new ScriptThread(process,target);
    }

    /**
     * 使当前线程休眠
     * @param millis 毫秒数
     */
    public void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 退出进程
     * @param exitCode 退出代码
     */
    public void exit(int exitCode){
        process.stop(exitCode);
    }

    /**
     * 获取文件内容
     * 只能获取src目录下的或apkg包内的文件
     * @param path
     * @return
     */
    public String getFile(String path){
        return fileLoader.getString(path);
    }

    /**
     * 获取文件的输入流
     * @param path
     * @return
     */
    public InputStream getFileInputStream(String path){
        return fileLoader.getInputStream(path);
    }

    /**
     * 获取脚本参数列表
     * @return
     */
    public List<String> getArgs(){
        return process.getScriptArgument();
    }

    /**
     * 获取脚本配置信息
     * @return
     */
    public ScriptInfo getInfo(){
        return process.getScriptInfo();
    }

    /**
     * 获取脚本属性
     * @return
     */
    public Map<String,String> getProps(){
        return process.getProps();
    }

}
