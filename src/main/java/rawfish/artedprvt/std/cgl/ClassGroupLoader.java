package rawfish.artedprvt.std.cgl;

import rawfish.artedprvt.core.ClassGroup;
import rawfish.artedprvt.core.ClassGroupSystem;
import rawfish.artedprvt.std.cgl.cgl.ClassGroupCgl;
import rawfish.artedprvt.std.cgl.cli.ClassGroupCli;
import rawfish.artedprvt.std.cgl.math.ClassGroupMath;
import rawfish.artedprvt.std.cgl.minecraft.ClassGroupClient;
import rawfish.artedprvt.std.cgl.minecraft.ClassGroupCommon;
import rawfish.artedprvt.std.cgl.minecraft.ClassGroupMc;
import rawfish.artedprvt.std.cgl.minecraft.ClassGroupServer;
import rawfish.artedprvt.std.cgl.minecraft.chat.ClassGroupMcChat;
import rawfish.artedprvt.std.cgl.minecraft.client.ClassGroupMcClient;
import rawfish.artedprvt.std.cgl.minecraft.entity.ClassGroupMcEntity;
import rawfish.artedprvt.std.cgl.minecraft.world.ClassGroupMcWorld;

import java.util.Arrays;

public class ClassGroupLoader {
    public static void load(){
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


    private static void addAll(ClassGroup[] classGroups){
        Arrays.stream(classGroups).forEach(ClassGroupSystem::add);
    }
}
