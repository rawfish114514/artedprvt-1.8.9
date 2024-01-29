package com.artedprvt.std.cgl.cli.format;

import com.artedprvt.core.BaseClassGroup;
import com.artedprvt.std.cli.format.FormatHandlerAppend;
import com.artedprvt.std.cli.format.FormatHandlerEmpty;
import com.artedprvt.std.cli.format.FormatHandlerNumber;
import com.artedprvt.std.cli.format.FormatHandlerRegex;
import com.artedprvt.std.cli.format.FormatHandlerSet;
import com.artedprvt.api.Solvable;

@Solvable
public class ClassGroupCliFormat extends BaseClassGroup {
    public static final ClassGroupCliFormat INSTANCE = new ClassGroupCliFormat("cli.format");

    @Solvable
    public ClassGroupCliFormat(Object name) {
        super(name);
        init();
    }

    private void init() {
        add(FormatHandlerAppend.class);
        add(FormatHandlerEmpty.class);
        add(FormatHandlerNumber.class);
        add(FormatHandlerRegex.class);
        add(FormatHandlerSet.class);
    }
}