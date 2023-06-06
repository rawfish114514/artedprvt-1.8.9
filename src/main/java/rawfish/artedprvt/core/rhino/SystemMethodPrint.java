package rawfish.artedprvt.core.rhino;

import rawfish.artedprvt.mi.ChatProvider;
import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;

public class SystemMethodPrint extends SystemMethod{
    public SystemMethodPrint(ScriptSystem scriptSystem) {
        super(scriptSystem);
        name="print";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length==0){
            scriptSystem.print(ScriptSystem.CHAT,"");
            return null;
        }
        if(args.length==1){
            scriptSystem.print(ScriptSystem.CHAT,String.valueOf(args[0]));
            return null;
        }
        if(args.length==2){
            if(args[1]!=null&&args[1] instanceof ChatProvider){
                scriptSystem.print(ScriptSystem.CHAT,String.valueOf(args[0]), (ChatProvider)args[1]);
                return null;
            }else {
                scriptSystem.print(ScriptSystem.CHAT,String.valueOf(args[0]), String.valueOf(args[1]));
                return null;
            }
        }
        ScriptExceptions.exceptionSystemMethodInvoke(this);
        return null;
    }
}
