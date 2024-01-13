package rawfish.artedprvt.std.cgl.cgl;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.core.BaseClassGroup;
import rawfish.artedprvt.std.cgl.cli.ClassGroupCli;
import rawfish.artedprvt.std.cgl.cli.format.ClassGroupCliFormat;
import rawfish.artedprvt.std.cgl.cli.info.ClassGroupCliInfo;
import rawfish.artedprvt.std.cgl.cli.util.ClassGroupCliUtil;
import rawfish.artedprvt.std.cgl.cli.util.parser.ClassGroupCliUtilParser;
import rawfish.artedprvt.std.cgl.math.ClassGroupMath;
import rawfish.artedprvt.std.cgl.minecraft.ClassGroupClient;
import rawfish.artedprvt.std.cgl.minecraft.ClassGroupCommon;
import rawfish.artedprvt.std.cgl.minecraft.ClassGroupMc;
import rawfish.artedprvt.std.cgl.minecraft.ClassGroupServer;
import rawfish.artedprvt.std.cgl.minecraft.ClientBaseClassGroup;
import rawfish.artedprvt.std.cgl.minecraft.chat.ClassGroupMcChat;
import rawfish.artedprvt.std.cgl.minecraft.client.ClassGroupMcClient;
import rawfish.artedprvt.std.cgl.minecraft.entity.ClassGroupMcEntity;
import rawfish.artedprvt.std.cgl.minecraft.world.ClassGroupMcWorld;

@Solvable
public class ClassGroupCgl extends BaseClassGroup {
    @Solvable
    public static final ClassGroupCgl INSTANCE = new ClassGroupCgl("cgl");

    @Solvable
    public ClassGroupCgl(Object name) {
        super(name);
        init();
    }

    private void init() {
        add(ClassGroupCgl.class);
        add(ClassGroupCli.class);
        add(ClassGroupCliFormat.class);
        add(ClassGroupCliInfo.class);
        add(ClassGroupCliUtil.class);
        add(ClassGroupCliUtilParser.class);
        add(ClassGroupMath.class);

        add(ClassGroupClient.class);
        add(ClassGroupCommon.class);
        add(ClassGroupServer.class);
        add(ClassGroupMc.class);
        add(ClientBaseClassGroup.class);

        add(ClassGroupMcChat.class);
        add(ClassGroupMcClient.class);
        add(ClassGroupMcEntity.class);
        add(ClassGroupMcWorld.class);
    }
}