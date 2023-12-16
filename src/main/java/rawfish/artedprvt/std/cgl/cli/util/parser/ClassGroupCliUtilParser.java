package rawfish.artedprvt.std.cgl.cli.util.parser;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.core.BaseClassGroup;
import rawfish.artedprvt.std.cli.util.parser.ArgumentsParserRegex;
import rawfish.artedprvt.std.cli.util.parser.ArgumentsParserSet;

@Solvable
public class ClassGroupCliUtilParser extends BaseClassGroup {
    public static final ClassGroupCliUtilParser INSTANCE=new ClassGroupCliUtilParser("cli.util.parser");

    @Solvable
    public ClassGroupCliUtilParser(Object name) {
        super(name);
        init();
    }

    private void init() {
        add(ArgumentsParserRegex.class);
        add(ArgumentsParserSet.class);
    }
}