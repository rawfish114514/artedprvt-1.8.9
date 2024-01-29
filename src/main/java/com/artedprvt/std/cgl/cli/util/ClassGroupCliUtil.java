package com.artedprvt.std.cgl.cli.util;

import com.artedprvt.core.BaseClassGroup;
import com.artedprvt.std.cgl.cli.util.parser.ClassGroupCliUtilParser;
import com.artedprvt.std.cli.util.ArgumentsParserInterface;
import com.artedprvt.std.cli.util.CommandBuilder;
import com.artedprvt.std.cli.util.FormatHandlerFactory;
import com.artedprvt.std.cli.util.FormatHandlerListBuilder;
import com.artedprvt.std.cli.util.FormatHandlerMapBuilder;
import com.artedprvt.std.cli.util.HandleResult;
import com.artedprvt.std.cli.util.InfoHandlerFactory;
import com.artedprvt.std.cli.util.InfoHandlerMapBuilder;
import com.artedprvt.std.cli.util.Literals;
import com.artedprvt.std.cli.util.ParseResult;
import com.artedprvt.std.cli.util.StringListBuilder;
import com.artedprvt.api.Solvable;

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