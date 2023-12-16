package rawfish.artedprvt.std.cli.info;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.cli.InfoHandler;

@Solvable
public class InfoHandlerEmpty implements InfoHandler {
    @Override
    @Solvable
    public String handleInfo(String source) {
        return "";
    }
}
