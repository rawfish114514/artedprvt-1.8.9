package rawfish.artedprvt.command.infos;

import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.InfoHandler;

public abstract class InfoHandlerInspect implements InfoHandler {
    public String type;
    public InfoHandlerInspect(String type){
        this.type=type;
    }

    public String inspect(String source,boolean inspect){
        if(inspect){
            return FormatHandler.toFormatCode("a")+ "<"+type+">";
        }
        if(source.isEmpty()){
            return "<"+type+">";
        }
        return FormatHandler.toFormatCode("c")+ "<"+type+"> -> "+source;
    }
}
