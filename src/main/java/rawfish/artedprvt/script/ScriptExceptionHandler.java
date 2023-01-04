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
        if(!String.valueOf(e.getMessage()).equals("null")){
            if(ScriptConst.debug) {
                pro.sender.addChatMessage(new ChatComponentText("\u00a74" + pro.pack + ": " + String.valueOf(e.getMessage())));
            }
            pro.stop();
        }
    }
}

