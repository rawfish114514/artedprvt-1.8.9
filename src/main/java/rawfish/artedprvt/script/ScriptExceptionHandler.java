package rawfish.artedprvt.script;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class ScriptExceptionHandler implements Thread.UncaughtExceptionHandler {
    protected ScriptProcess pro;
    public ScriptExceptionHandler(ScriptProcess proIn){
        pro=proIn;
    }
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if(!e.getMessage().equals("null")){
            if(ScriptConst.debug) {
                pro.sys.log(pro.pack,"\u00a74" +": " + e.getMessage());
            }
            if(t instanceof ScriptThread){
                ScriptThread st=(ScriptThread)t;
                if(!st.errorHandle){
                    pro.stop(st);
                }
            }else {
                pro.stop(null);
            }
        }
    }
}

