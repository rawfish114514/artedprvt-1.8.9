package com.artedprvt.std.minecraft.world;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.minecraft.block.BlockState;
import com.artedprvt.std.minecraft.entity.Entity;
import com.artedprvt.std.minecraft.entity.EntityCow;
import com.artedprvt.std.minecraft.entity.EntityPlayer;

import java.util.List;

@InterfaceView
public interface World {
    net.minecraft.world.World v_getWorld();

    @InterfaceView
    List<EntityPlayer> getEntityPlayerList();

    @InterfaceView
    boolean spawnEntity(Entity entity);

    @InterfaceView
    EntityCow createEntityCow();

    @InterfaceView
    BlockState getBlockState(int x, int y, int z);

    @InterfaceView
    boolean setBlockState(int x, int y, int z, BlockState blockState);

}
