package rawfish.artedprvt.std.cgl.cli.format;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.core.BaseClassGroup;
import rawfish.artedprvt.std.cli.format.FormatHandlerAppend;
import rawfish.artedprvt.std.cli.format.FormatHandlerEmpty;
import rawfish.artedprvt.std.cli.format.FormatHandlerNumber;
import rawfish.artedprvt.std.cli.format.FormatHandlerRegex;
import rawfish.artedprvt.std.cli.format.FormatHandlerSet;

@Solvable
public class ClassGroupCliFormat extends BaseClassGroup {
    public static final ClassGroupCliFormat INSTANCE=new ClassGroupCliFormat("cli.format");

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