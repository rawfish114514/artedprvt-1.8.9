package rawfish.artedprvt.core.rhino;

import org.mozilla.javascript.*;
import rawfish.artedprvt.core.localization.Localization;
import rawfish.artedprvt.core.ScriptLanguage;
import rawfish.artedprvt.core.engine.ServiceEngine;

/**
 * 服务引擎
 * 允许非脚本进程内的脚本运行
 * 主要由系统调用
 */
public class RhinoServiceEngine implements ServiceEngine {
    public static RhinoServiceEngine service=null;
    public RhinoServiceEngine(){
    }

    public boolean isExecutable(ScriptLanguage language){
        return Rhino.LANGUAGE.equals(language);
    }

    @Override
    public Object call(String code,String func,Object... args) throws Exception{
        Context rhino=Context.enter();
        ScriptableObject scope=rhino.initStandardObjects();
        NativeObject object=new NativeObject();
        object.put("language", object, Localization.getLanguage());
        scope.put("core",scope,object);
        rhino.evaluateString(scope,code,"service",1,null);
        Object f=scope.get(func);
        if(f==null){
            return null;
        }
        for(int i=0;i<args.length;i++){
            if(args[i] instanceof Class){
                args[i]=new NativeJavaClass(scope, (Class<?>) args[i]);
            }
        }
        return ((Function) f).call(rhino,scope,scope,args);
    }

    @Override
    public Object unwrap(Object obj) {
        if(obj instanceof Wrapper){
            obj=((Wrapper)obj).unwrap();
        }
        return obj;
    }
}
