package rawfish.artedprvt.core.rhino;

import org.mozilla.javascript.*;
import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;

public abstract class SystemMethod extends BaseFunction {
    protected ScriptSystem scriptSystem;
    private Scriptable scope;
    protected String name="null";
    public SystemMethod(ScriptSystem scriptSystem){
        this.scriptSystem=scriptSystem;
    }

    public String getName(){
        return name;
    }

    public abstract Object invoke(Object[] args);

    private void setScope(Scriptable scope){
        this.scope=scope;
    }

    public Scriptable getScope(){
        return scope;
    }

    @Override
    public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
        setScope(scope);
        return invoke(unwrap(args));
    }

    @Override
    public void put(String name, Scriptable start, Object value) {
        ScriptExceptions.exception("系统方法禁止设置成员: "+name);
    }

    @Override
    public void put(Symbol key, Scriptable start, Object value) {
        ScriptExceptions.exception("系统方法禁止设置成员: "+key);
    }

    @Override
    public Object get(String name, Scriptable start) {
        ScriptExceptions.exception("系统方法禁止访问成员: "+name);
        return null;
    }

    @Override
    public Object get(Symbol key, Scriptable start) {
        ScriptExceptions.exception("系统方法禁止访问成员: "+key);
        return null;
    }

    /**
     * 拆箱
     * @param objs
     * @return
     */
    public static Object[] unwrap(Object[] objs){
        Object[] nobjs=new Object[objs.length];
        Object obj;
        for(int i=0;i<objs.length;i++){
            obj=objs[i];
            if(obj instanceof Wrapper){
                obj=((Wrapper)obj).unwrap();
            }
            nobjs[i]=obj;
        }
        return nobjs;
    }
}