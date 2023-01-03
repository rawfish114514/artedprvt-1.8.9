package rawfish.artedprvt.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

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
        scope.put("_114514",scope,sys);
        scope.put("_1919810",scope,pack);
        scope.put("_18140",scope,invoker);
        rhino.evaluateString(scope,initScript(),pack,1,null);
        rhino.evaluateString(scope,script,pack,1,null);
    }

    protected Object export=null;

    protected String initScript(){
        return  "var invoker=function(){return _18140;};" +
                "var pack=function(){return _1919810;};" +
                "var args=function(){return _114514.getArgs(_1919810);};" +
                "var print=function(object){_114514.print(_1919810,object);};" +
                "var log=function(object){_114514.log(_1919810,object);};" +
                "var import=function(pack){return _114514.importModule(_1919810,pack);};" +
                "var export=function(object){_114514.exportModule(_1919810,object);};" +
                "var thread=function(runnable){return _114514.createThread(_1919810,runnable);};" +
                "var runf=function(func,args){return _114514.runFunction(_1919810,func,args);};";
    }
}