package com.artedprvt.std.cgl;

import com.artedprvt.core.ClassGroup;
import com.artedprvt.core.ClassGroupSystem;
import com.artedprvt.std.cgl.cgl.ClassGroupCgl;
import com.artedprvt.std.cgl.cli.ClassGroupCli;
import com.artedprvt.std.cgl.math.ClassGroupMath;
import com.artedprvt.std.cgl.minecraft.ClassGroupClient;
import com.artedprvt.std.cgl.minecraft.ClassGroupCommon;
import com.artedprvt.std.cgl.minecraft.ClassGroupMc;
import com.artedprvt.std.cgl.minecraft.ClassGroupServer;
import com.artedprvt.std.cgl.minecraft.chat.ClassGroupMcChat;
import com.artedprvt.std.cgl.minecraft.client.ClassGroupMcClient;
import com.artedprvt.std.cgl.minecraft.entity.ClassGroupMcEntity;
import com.artedprvt.std.cgl.minecraft.world.ClassGroupMcWorld;

import java.util.Arrays;

public class ClassGroupLoader {
    public static void load() {
        addAll(new ClassGroup[]{
                //other
                ClassGroupCgl.INSTANCE,
                ClassGroupCli.INSTANCE,
                ClassGroupMath.INSTANCE,

                //mc
                ClassGroupClient.INSTANCE,
                ClassGroupCommon.INSTANCE,
                ClassGroupServer.INSTANCE,
                ClassGroupMc.INSTANCE,

                ClassGroupMcChat.INSTANCE,
                ClassGroupMcClient.INSTANCE,
                ClassGroupMcEntity.INSTANCE,
                ClassGroupMcWorld.INSTANCE,

        });
    }


    private static void addAll(ClassGroup[] classGroups) {
        Arrays.stream(classGroups).forEach(ClassGroupSystem::add);
    }
}
