package rawfish.artedprvt.core.script;

import rawfish.artedprvt.core.Logger;
import rawfish.artedprvt.core.WorkSpace;
import rawfish.artedprvt.core.localization.types.SES;
import rawfish.artedprvt.core.script.engine.ScriptEngine;
import rawfish.artedprvt.core.script.struct.FileLoader;
import rawfish.artedprvt.core.script.struct.ScriptModule;
import rawfish.artedprvt.std.minecraft.chat.ChatComponent;
import rawfish.artedprvt.std.minecraft.chat.ChatConsole;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * 脚本系统
 * 提供通用的公共方法
 */
public class ScriptSystem {
    private ScriptProcess process;
    private Logger logger;
    private FileLoader fileLoader;
    private ChatConsole chatConsole;
    public ScriptSystem(ScriptProcess process){
        this.process=process;
        logger=process.logger();
        fileLoader=process.getFileLoader();
        chatConsole=new ChatConsole();
        chatConsole.setLongtime(true);
        chatConsole.setLog(false);
    }

    /**
     * 聊天信息类型
     */
    public static final int
    CHAT=0,
    DEBUG=1;

    /**
     * 开关
     */
    public static boolean CHAT_SWITCH =false;

    /**
     * 打印
     * @param type 聊天信息类型
     * @param chat 聊天信息
     */
    public void print(int type,String chat) {
        logger.info(chat);

        if (CHAT_SWITCH) {
            if (type == CHAT) {
                chatConsole.print(chat);
                return;
            }
            if (type == DEBUG) {
                chat = getDebugHead() + chat;
                chatConsole.print(chat);
            }
        }
    }

    /**
     * 打印
     * @param type 聊天信息类型
     * @param chat 聊天信息
     * @param hover 悬浮信息
     */
    public void print(int type,String chat,String hover) {
        logger.info(chat);

        if (CHAT_SWITCH) {
            if (type == CHAT) {
                chatConsole.print(new ChatComponent(chat,hover));
                return;
            }
            if (type == DEBUG) {
                chat = getDebugHead() + chat;
                chatConsole.print(new ChatComponent(chat,hover));
            }
        }
    }

    /**
     * 打印
     * 没有聊天信息分类也不走日志
     * @param chat
     */
    public void print(String chat){
        if (CHAT_SWITCH) {
            chatConsole.print(chat);
        }
    }

    public void print(String chat,String hover) {
        if (CHAT_SWITCH) {
            chatConsole.print(new ChatComponent(chat,hover));
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

    /**
     * 导入模块
     * @param moduleFullName 模块完整名 abbr?:package.module
     * @return
     */
    public Object importModule(String moduleFullName){
        return importModule(process.getScriptLoader().getModule(moduleFullName));
    }

    /**
     * 导入模块
     * @param scriptModule 模块
     * @return
     */
    public Object importModule(ScriptModule scriptModule){
        executeModule(scriptModule);
        return scriptModule.getExport();
    }

    /**
     *
     * @param scriptModule 模块
     */
    private void executeModule(ScriptModule scriptModule){
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
                ScriptExceptions.exception(SES.ses9,scriptModule.getScriptLanguage().abbr);
            }
        }
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
    public Collection<Class> importClassGroup(String groupName) {
        ClassGroup classGroup= ClassGroupSystem.get(groupName);
        if(classGroup!=null){
            Collection<Class> classes=classGroup.getClasses();
            if(classes!=null){
                return classes;
            }
        }
        ScriptExceptions.exception(SES.ses10,groupName);
        return null;
    }

    /**
     * 导出模块
     * @param moduleName 模块名
     * @param export 导出对象
     */
    public void exportModule(String moduleName,Object export){
        exportModule(process.getScriptLoader().getModule(moduleName),export);
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
     * 只能获取src目录下的或aar包内的文件
     * @param path
     * @return
     */
    public String getFile(String path){
        return fileLoader.getContent(path);
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
    public MetaData getInfo(){
        return process.getScriptInfo();
    }

    /**
     * 获取工作空间
     * @return
     */
    public WorkSpace getWorkSpace(){
        return process.getWorkSpace();
    }

}
