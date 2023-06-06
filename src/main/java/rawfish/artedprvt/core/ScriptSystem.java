package rawfish.artedprvt.core;

import rawfish.artedprvt.mi.ChatProvider;
import rawfish.artedprvt.mi.PrintChat;
import rawfish.artedprvt.core.engine.ScriptEngine;
import rawfish.artedprvt.core.struct.ScriptModule;

import java.util.List;
import java.util.Map;

/**
 * 脚本系统
 * 提供通用的公共方法
 */
public class ScriptSystem {
    private ScriptProcess process;
    private PrintChat printChat;
    public ScriptSystem(ScriptProcess process){
        this.process=process;
        printChat=new PrintChat();
    }

    /**
     * 打印类型
     */
    public static final int
    CHAT=0,
    DEBUG =1,
    HIGH=2;

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
            if(B_CHAT){
                printChat.print(chat);
                return;
            }
        }
        if(type== DEBUG){
            if(B_DEBUG){
                printChat.print("§7["+process.getName()+"]["+Thread.currentThread().getName()+"] "+chat);
                return;
            }
        }
        if(type==HIGH){
            printChat.print(chat);
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
            if(B_CHAT){
                printChat.print(chat,hover);
                return;
            }
        }
        if(type== DEBUG){
            if(B_DEBUG){
                printChat.print("§7["+process.getName()+"]["+Thread.currentThread().getName()+"] "+chat,hover);
                return;
            }
        }
        if(type==HIGH){
            printChat.print(chat,hover);
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
            if(B_CHAT){
                printChat.print(chat,hover);
                return;
            }
        }
        if(type== DEBUG){
            if(B_DEBUG){
                printChat.print("§7["+process.getName()+"]["+Thread.currentThread().getName()+"] "+chat,hover);
                return;
            }
        }
        if(type==HIGH){
            printChat.print(chat,hover);
        }
    }

    /**
     * 导入模块
     * @param moduleName 模块名
     * @return
     */
    public Object importModule(String moduleName){
        if(moduleName.length()>0&&moduleName.charAt(0)=='-'){
            //将导入java类
            return importJava(moduleName.substring(1));
        }
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
    public Class importJava(String className){
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
