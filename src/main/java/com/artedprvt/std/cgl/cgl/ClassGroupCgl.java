package com.artedprvt.std.cgl.cgl;

import com.artedprvt.core.BaseClassGroup;
import com.artedprvt.std.cgl.cli.ClassGroupCli;
import com.artedprvt.std.cgl.cli.format.ClassGroupCliFormat;
import com.artedprvt.std.cgl.cli.info.ClassGroupCliInfo;
import com.artedprvt.std.cgl.cli.util.ClassGroupCliUtil;
import com.artedprvt.std.cgl.cli.util.parser.ClassGroupCliUtilParser;
import com.artedprvt.std.cgl.math.ClassGroupMath;
import com.artedprvt.std.cgl.minecraft.ClassGroupClient;
import com.artedprvt.std.cgl.minecraft.ClassGroupCommon;
import com.artedprvt.std.cgl.minecraft.ClassGroupMc;
import com.artedprvt.std.cgl.minecraft.ClassGroupServer;
import com.artedprvt.std.cgl.minecraft.ClientBaseClassGroup;
import com.artedprvt.std.cgl.minecraft.chat.ClassGroupMcChat;
import com.artedprvt.std.cgl.minecraft.client.ClassGroupMcClient;
import com.artedprvt.std.cgl.minecraft.entity.ClassGroupMcEntity;
import com.artedprvt.std.cgl.minecraft.world.ClassGroupMcWorld;
import com.artedprvt.api.Solvable;

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