package rawfish.artedprvt.command.infos;

import rawfish.artedprvt.command.InfoHandler;

public class InfoHandlerString implements InfoHandler {
    private String string;
    public InfoHandlerString(String string){
        this.string=string;
    }
    @Override
    public String handleInfo(String source) {
        return string;
    }
}
