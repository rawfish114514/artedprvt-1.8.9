package rawfish.artedprvt.core.script.rhino;

import org.mozilla.javascript.NativeJavaClass;
import org.mozilla.javascript.Scriptable;
import rawfish.artedprvt.core.script.ScriptExceptions;
import rawfish.artedprvt.core.script.ScriptSystem;
import rawfish.artedprvt.core.localization.types.SES;

import java.util.List;

public class SystemMethodImport extends SystemMethod{
    public SystemMethodImport(Scriptable scope,ScriptSystem scriptSystem) {
        super(scope,scriptSystem);
        name="import";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length==1){
            String name=String.valueOf(args[0]);
            if(name.length()>0) {
                if(name.charAt(0)=='-'){
                    Scriptable scope=getScope();
                    Class clazz=scriptSystem.importClass(name.substring(1));
                    scope.put(clazz.getSimpleName(),scope,new NativeJavaClass(scope,clazz));
                    return null;
                }
                if(name.charAt(0)=='*'){
                    Scriptable scope=getScope();
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
        ScriptExceptions.exception(SES.ses0,this.getName());
        return null;
    }
}
