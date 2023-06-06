package rawfish.artedprvt.core.rhino;

import org.mozilla.javascript.NativeJavaClass;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;

public class SystemMethodImport extends SystemMethod{
    public SystemMethodImport(ScriptSystem scriptSystem) {
        super(scriptSystem);
        name="import";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length==1){
            if(args[0] instanceof String) {
                String name=(String) args[0];
                Object object=scriptSystem.importModule(name);
                if(name.length()>0&&name.charAt(0)=='-'){
                    Class clazz=(Class)object;
                    Scriptable scope=ScriptableObject.getTopLevelScope(getScope());
                    scope.put(clazz.getSimpleName(),scope,new NativeJavaClass(scope,clazz));
                    return null;
                }
                return object;
            }else{
                ScriptExceptions.exceptionSystemMethodInvoke(this);
            }
        }
        ScriptExceptions.exceptionSystemMethodInvoke(this);
        return null;
    }
}
