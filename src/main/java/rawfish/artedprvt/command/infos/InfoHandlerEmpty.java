package rawfish.artedprvt.command.infos;

import rawfish.artedprvt.command.InfoHandler;

public class InfoHandlerEmpty implements InfoHandler {
    @Override
    public String handleInfo(String source) {
        return "";
    }
}
