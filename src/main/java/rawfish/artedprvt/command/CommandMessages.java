package rawfish.artedprvt.command;

import rawfish.artedprvt.id.Local;
import rawfish.artedprvt.mi.PrintChat;

import java.util.Arrays;

public class CommandMessages {
    public static PrintChat printChat=new PrintChat();
    public static void exception(String source,String str,Object... args){
        printChat.print("§4"+source+": "+translate(str,args));
    }

    public static void key(String source,String str,Object... args){
        printChat.print("§6"+source+"§7: "+translate(str,args));
    }

    private static String translate(String str,Object... args){
        return Local.getTranslate(str, args);
    }
}
