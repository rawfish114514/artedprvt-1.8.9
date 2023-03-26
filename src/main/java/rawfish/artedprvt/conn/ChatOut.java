package rawfish.artedprvt.conn;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class ChatOut {
    public ICommandSender sender;

    public ChatOut(ICommandSender sender){
        this.sender=sender;
    }

    public void print(String str){
        sender.addChatMessage(new ChatComponentText(str));
    }
}
