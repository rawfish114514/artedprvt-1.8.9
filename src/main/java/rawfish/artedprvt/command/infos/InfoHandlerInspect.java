package rawfish.artedprvt.command.infos;

import rawfish.artedprvt.command.InfoHandler;
import rawfish.artedprvt.command.util.Literals;

public abstract class InfoHandlerInspect implements InfoHandler {
    public String type;
    public InfoHandlerInspect(String type){
        this.type=type;
    }

    public String inspect(String source,boolean inspect){
        if(inspect){
            return Literals.Formats.toFormatCode("a")+ "<"+type+">";
        }
        if(source.isEmpty()){
            return "<"+type+">";
        }
        return Literals.Formats.toFormatCode("c")+ "<"+type+"> -> "+source;
    }
}
