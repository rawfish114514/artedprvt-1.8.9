package rawfish.artedprvt.command.util;

import rawfish.artedprvt.std.minecraft.chat.ChatConsole;

import java.text.MessageFormat;

public class CommandMessages {
    public static ChatConsole chatConsole=new ChatConsole();
    public static void exception(String source,String str,Object... args){
        chatConsole.print("§4"+source+": "+MessageFormat.format(str,args));
    }

    public static void key(String source,String str,Object... args){
        chatConsole.print("§6"+source+"§7: "+ MessageFormat.format(str,args));
    }
}
