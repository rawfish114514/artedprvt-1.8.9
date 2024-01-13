package rawfish.artedprvt.std.cgl.cli.util;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.core.BaseClassGroup;
import rawfish.artedprvt.std.cgl.cli.util.parser.ClassGroupCliUtilParser;
import rawfish.artedprvt.std.cli.util.ArgumentsParserInterface;
import rawfish.artedprvt.std.cli.util.CommandBuilder;
import rawfish.artedprvt.std.cli.util.FormatHandlerFactory;
import rawfish.artedprvt.std.cli.util.FormatHandlerListBuilder;
import rawfish.artedprvt.std.cli.util.FormatHandlerMapBuilder;
import rawfish.artedprvt.std.cli.util.HandleResult;
import rawfish.artedprvt.std.cli.util.InfoHandlerFactory;
import rawfish.artedprvt.std.cli.util.InfoHandlerMapBuilder;
import rawfish.artedprvt.std.cli.util.Literals;
import rawfish.artedprvt.std.cli.util.ParseResult;
import rawfish.artedprvt.std.cli.util.StringListBuilder;

@Solvable
public class ClassGroupCliUtil extends BaseClassGroup {
    public static final ClassGroupCliUtil INSTANCE = new ClassGroupCliUtil("cli.util");

    @Solvable
    public ClassGroupCliUtil(Object name) {
        super(name);
        init();
    }

    private void init() {
        union(ClassGroupCliUtilParser.INSTANCE);

        add(ArgumentsParserInterface.class);
        add(CommandBuilder.class);
        add(FormatHandlerFactory.class);
        add(FormatHandlerListBuilder.class);
        add(FormatHandlerMapBuilder.class);
        add(HandleResult.class);
        add(InfoHandlerFactory.class);
        add(InfoHandlerMapBuilder.class);
        add(Literals.class);
        add(ParseResult.class);
        add(StringListBuilder.class);
    }
}