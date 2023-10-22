package rawfish.artedprvt.command.util;

import rawfish.artedprvt.core.localization.Localization;
import rawfish.artedprvt.mi.PrintChat;

import java.text.MessageFormat;

public class CommandMessages {
    public static PrintChat printChat=new PrintChat();
    public static void exception(String source,String str,Object... args){
        printChat.print("§4"+source+": "+MessageFormat.format(str,args));
    }

    public static void key(String source,String str,Object... args){
        printChat.print("§6"+source+"§7: "+ MessageFormat.format(str,args));
    }
}
