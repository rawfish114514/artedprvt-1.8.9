package rawfish.artedprvt.core.rhino;

import org.mozilla.javascript.NativeJavaClass;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;

import java.util.List;

public class SystemMethodImport extends SystemMethod{
    public SystemMethodImport(ScriptSystem scriptSystem) {
        super(scriptSystem);
        name="import";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length==1){
            String name=String.valueOf(args[0]);
            if(name.length()>0) {
                if(name.charAt(0)=='-'){
                    Scriptable scope=ScriptableObject.getTopLevelScope(getScope());
                    Class clazz=scriptSystem.importClass(name.substring(1));
                    scope.put(clazz.getSimpleName(),scope,new NativeJavaClass(scope,clazz));
                    return null;
                }
                if(name.charAt(0)=='*'){
                    Scriptable scope=ScriptableObject.getTopLevelScope(getScope());
                    List<Class> classList=scriptSystem.importClassGroup(name.substring(1));
                    Class clazz;
                    for(int i=0;i<classList.size();i++){
                        clazz=classList.get(i);
                        scope.put(clazz.getSimpleName(),scope,new NativeJavaClass(scope,clazz));
                    }
                    return null;
                }
            }
            return scriptSystem.importModule(name);
        }
        ScriptExceptions.exceptionSystemMethodInvoke(this);
        return null;
    }
}
