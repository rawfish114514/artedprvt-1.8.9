package rawfish.artedprvt.std.cli.info;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.cli.InfoHandler;

@Solvable
public class InfoHandlerString implements InfoHandler {
    private String string;

    @Solvable
    public InfoHandlerString(String string) {
        this.string = string;
    }

    @Override
    @Solvable
    public String handleInfo(String source) {
        return string;
    }
}
