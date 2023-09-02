package rawfish.artedprvt.core.rhino;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;

import java.util.Locale;

public class SystemMethodThread extends SystemMethod{
    public SystemMethodThread(ScriptSystem scriptSystem) {
        super(scriptSystem);
        name="thread";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length==1){
            if(args[0] instanceof Function){
                Function function=(Function)args[0];
                return scriptSystem.createThread(new RunnableFunction(function));
            }
        }
        ScriptExceptions.exception("ses0",this.getName());
        return null;
    }

    private static class RunnableFunction implements Runnable{
        private Function function;
        public RunnableFunction(Function function){
            this.function=function;
        }
        @Override
        public void run() {
            Context rhino=Context.enter();
            rhino.unseal();
            rhino.setOptimizationLevel(-1);
            rhino.setLocale(Locale.ENGLISH);
            function.call(
                    rhino,
                    function.getParentScope(),
                    function.getParentScope(),
                    new Object[]{});
        }
    }
}
