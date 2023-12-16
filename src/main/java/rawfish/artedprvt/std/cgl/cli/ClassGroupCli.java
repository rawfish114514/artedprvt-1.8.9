package rawfish.artedprvt.std.cgl.cli;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.core.BaseClassGroup;
import rawfish.artedprvt.std.cgl.cli.format.ClassGroupCliFormat;
import rawfish.artedprvt.std.cgl.cli.info.ClassGroupCliInfo;
import rawfish.artedprvt.std.cgl.cli.util.ClassGroupCliUtil;
import rawfish.artedprvt.std.cgl.cli.util.parser.ClassGroupCliUtilParser;
import rawfish.artedprvt.std.cli.Command;
import rawfish.artedprvt.std.cli.CommandComplete;
import rawfish.artedprvt.std.cli.CommandFormat;
import rawfish.artedprvt.std.cli.CommandInfo;
import rawfish.artedprvt.std.cli.CommandProcess;
import rawfish.artedprvt.std.cli.CommandRegistry;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.InfoHandler;

@Solvable
public class ClassGroupCli extends BaseClassGroup {
    @Solvable
    public static final ClassGroupCli INSTANCE=new ClassGroupCli("cli");

    @Solvable
    public ClassGroupCli(Object name) {
        super(name);
        init();
    }

    private void init() {
        union(ClassGroupCliFormat.INSTANCE);
        union(ClassGroupCliInfo.INSTANCE);
        union(ClassGroupCliUtil.INSTANCE);


        add(Command.class);
        add(CommandComplete.class);
        add(CommandFormat.class);
        add(CommandInfo.class);
        add(CommandProcess.class);
        add(CommandRegistry.class);
        add(FormatHandler.class);
        add(InfoHandler.class);
    }
}
