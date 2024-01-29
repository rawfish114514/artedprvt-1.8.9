package com.artedprvt.std.cgl.cli.util.parser;

import com.artedprvt.core.BaseClassGroup;
import com.artedprvt.std.cli.util.parser.ArgumentsParserRegex;
import com.artedprvt.std.cli.util.parser.ArgumentsParserSet;
import com.artedprvt.api.Solvable;

@Solvable
public class ClassGroupCliUtilParser extends BaseClassGroup {
    public static final ClassGroupCliUtilParser INSTANCE = new ClassGroupCliUtilParser("cli.util.parser");

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