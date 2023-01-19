package rawfish.artedprvt.script;

import org.mozilla.javascript.*;
import rawfish.artedprvt.script.mi.Events;

import java.util.Map;


/**
 * 脚本单位
 */
public class ScriptUnit{
    protected Context rhino;
    protected ScriptableObject scope;

    protected ScriptProcess pro;
    protected ScriptSystem sys;
    protected String script;
    protected String pack;
    protected String invoker;
    /**
     * 初始化域
     * @param proIn 进程对象
     * @param scriptIn 代码
     * @param packIn 模块名
     */
    public ScriptUnit(ScriptProcess proIn,String scriptIn,String packIn){
        pro=proIn;
        sys=pro.sys;
        script=scriptIn;
        pack=packIn;
        rhino=pro.rhino;
        scope=rhino.initStandardObjects();
    }

    /**
     * 运行脚本
     * 只调用一次
     * @param invokerIn 调用上级
     */
    public void run(String invokerIn){
        invoker=invokerIn;

        //添加系统相关功能
        scope.put(InitScript.varSys,scope,sys);
        scope.put(InitScript.varPack,scope,pack);
        scope.put(InitScript.varInvoker,scope,invoker);
        rhino.evaluateString(scope,InitScript.script,"init_sys",1,null);

        //添加客户端功能
        scope.put(InitScript.varClient,scope,pro.client);
        rhino.evaluateString(scope,InitScript.clientscript,"init_client",1,null);

        //添加游戏相关功能
        Map<String,NativeJavaClass> map=pro.port.classes;

        for(String key:map.keySet()){
            scope.put(key,scope,map.get(key));
        }


        //添加事件监听器 前置函数定义
        if(pro.al_value){
            StringBuilder sb=new StringBuilder();
            for(Events type: Events.values()){
                sb.append(SupplementScript.getEventListenerRegisterCode(type));
            }
            script=sb.append(script).toString();
        }


        //运行脚本内容
        rhino.evaluateString(scope,script,pack,1,null);


    }

    protected Object export=null;

}
