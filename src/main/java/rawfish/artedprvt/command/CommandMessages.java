package rawfish.artedprvt.command;

import rawfish.artedprvt.core.Localization;
import rawfish.artedprvt.mi.PrintChat;

public class CommandMessages {
    public static PrintChat printChat=new PrintChat();
    public static void exception(String source,String str,Object... args){
        printChat.print("§4"+source+": "+translate(str,args));
    }

    public static void key(String source,String str,Object... args){
        printChat.print("§6"+source+"§7: "+translate(str,args));
    }

    private static String translate(String str,Object... args){
        return Localization.getTranslate(str, args);
    }
}
