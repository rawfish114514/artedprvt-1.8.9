package com.artedprvt.std.cgl.cli;

import com.artedprvt.core.BaseClassGroup;
import com.artedprvt.std.cgl.cli.format.ClassGroupCliFormat;
import com.artedprvt.std.cgl.cli.info.ClassGroupCliInfo;
import com.artedprvt.std.cgl.cli.util.ClassGroupCliUtil;
import com.artedprvt.std.cli.Command;
import com.artedprvt.std.cli.CommandRegistry;
import com.artedprvt.std.cli.CompleteInterface;
import com.artedprvt.std.cli.FormatHandler;
import com.artedprvt.std.cli.FormatInterface;
import com.artedprvt.std.cli.InfoHandler;
import com.artedprvt.std.cli.InfoInterface;
import com.artedprvt.std.cli.Messager;
import com.artedprvt.std.cli.ProcessInterface;
import com.artedprvt.api.Solvable;

@Solvable
public class ClassGroupCli extends BaseClassGroup {
    @Solvable
    public static final ClassGroupCli INSTANCE = new ClassGroupCli("cli");

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
        add(CompleteInterface.class);
        add(FormatInterface.class);
        add(InfoInterface.class);
        add(ProcessInterface.class);
        add(CommandRegistry.class);
        add(FormatHandler.class);
        add(InfoHandler.class);
        add(Messager.class);
    }
}
