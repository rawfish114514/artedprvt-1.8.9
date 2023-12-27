package rawfish.artedprvt.command;

import rawfish.artedprvt.std.cli.Messager;
import rawfish.artedprvt.std.text.Formatting;

import java.text.MessageFormat;

public class FormatMessager implements Messager {
    private Messager messager;
    private String source;
    public FormatMessager(Messager messager,String source) {
        this.messager=messager;
        this.source=source;
    }

    @Override
    public void send(String message) {
        messager.send(message);
    }

    @Override
    public void send(String message, String hover) {
        messager.send(message, hover);
    }

    @Override
    public boolean canHover() {
        return messager.canHover();
    }

    @Override
    public int dialog(String message, String... buttons) {
        return messager.dialog(Formatting.GOLD+source+Formatting.RESET+": "+message, buttons);
    }

    @Override
    public boolean canDialog() {
        return messager.canDialog();
    }


    public void red(String patt,Object... args){
        send(Formatting.DARK_RED+source+": "+MessageFormat.format(patt,args));
    }

    public void gold(String patt,Object... args){
        send(Formatting.GOLD+source+Formatting.GRAY+": "+MessageFormat.format(patt,args));
    }

    public void white(String patt,Object... args){
        send(Formatting.WHITE+source+Formatting.GRAY+": "+MessageFormat.format(patt,args));
    }

}
