package rawfish.artedprvt.core.app.script.rhino;

import org.mozilla.javascript.NativeJavaClass;
import org.mozilla.javascript.Scriptable;
import rawfish.artedprvt.core.localization.types.SES;
import rawfish.artedprvt.core.app.script.ScriptExceptions;
import rawfish.artedprvt.core.app.script.ScriptSystem;

import java.util.Collection;

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
                    Collection<Class> classes=scriptSystem.importClassGroup(name.substring(1));
                    for(Class clas:classes){
                        scope.put(clas.getSimpleName(),scope,new NativeJavaClass(scope,clas));
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
