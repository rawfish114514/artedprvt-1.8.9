package rawfish.artedprvt.script;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class ScriptExceptionHandler implements Thread.UncaughtExceptionHandler {
    protected ICommandSender sender;
    protected String pack;
    public ScriptExceptionHandler(ICommandSender senderIn,String packIn){
        sender=senderIn;
        pack=packIn;
    }
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if(!String.valueOf(e.getMessage()).equals("null")){
            if(ScriptConst.debug) {
                sender.addChatMessage(new ChatComponentText("\u00a74" + pack + ": " + String.valueOf(e.getMessage())));
            }
        }
    }
}

