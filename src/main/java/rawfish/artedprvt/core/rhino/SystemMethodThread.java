package rawfish.artedprvt.core.rhino;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;

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
        ScriptExceptions.exceptionSystemMethodInvoke(this);
        return null;
    }

    private static class RunnableFunction implements Runnable{
        private Function function;
        public RunnableFunction(Function function){
            this.function=function;
        }
        @Override
        public void run() {
            function.call(
                    Context.enter(),
                    function.getParentScope(),
                    function.getParentScope(),
                    new Object[]{});
        }
    }
}
