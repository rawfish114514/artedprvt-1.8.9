package rawfish.artedprvt.command;

import rawfish.artedprvt.mi.PrintChat;

public class CommandMessages {
    public static PrintChat printChat=new PrintChat();
    public static void exception(String source,String str){
        printChat.print("§4"+source+": "+str);
    }

    public static void key(String source,String str){
        printChat.print("§6"+source+"§7: "+str);
    }
}
