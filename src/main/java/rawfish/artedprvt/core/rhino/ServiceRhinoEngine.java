package rawfish.artedprvt.core.rhino;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;
import rawfish.artedprvt.core.ScriptLanguage;
import rawfish.artedprvt.core.engine.ServiceEngine;
import scala.util.control.TailCalls;

/**
 * 服务引擎
 * 允许非脚本进程内的脚本运行
 * 主要由系统调用
 */
public class ServiceRhinoEngine implements ServiceEngine {
    public static ServiceRhinoEngine service=null;
    public ServiceRhinoEngine(){
    }

    public boolean isExecutable(ScriptLanguage language){
        return language==ScriptLanguage.JAVASCRIPT;
    }

    @Override
    public Object call(String code,String func,Object... args) throws Exception{
        Context rhino=Context.enter();
        ScriptableObject scope=rhino.initStandardObjects();
        rhino.evaluateString(scope,code,"service",1,null);
        Object f=scope.get(func);
        return ((Function) f).call(rhino,scope,scope,args);
    }
}
