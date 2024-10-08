package com.artedprvt.std.impls.minecraft.world;

import com.artedprvt.std.impls.minecraft.block.VanillaProxyBlockState;
import com.artedprvt.std.minecraft.block.BlockState;
import com.artedprvt.std.minecraft.entity.Entity;
import com.artedprvt.std.minecraft.entity.EntityCow;
import com.artedprvt.std.minecraft.entity.EntityPlayer;
import com.artedprvt.std.minecraft.world.World;
import com.artedprvt.std.impls.minecraft.entity.VanillaProxyEntityCow;
import com.artedprvt.std.impls.minecraft.entity.VanillaProxyEntityPlayer;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class VanillaProxyWorld implements World {
    public net.minecraft.world.World v_world;

    public VanillaProxyWorld(net.minecraft.world.World v_world) {
        this.v_world = v_world;
    }

    @Override
    public net.minecraft.world.World v_getWorld() {
        return v_world;
    }

    @Override
    public List<EntityPlayer> getEntityPlayerList() {
        List<net.minecraft.entity.player.EntityPlayer> v_playerEntities = v_world.playerEntities;
        List<EntityPlayer> entityPlayerList = new ArrayList<>();
        for (net.minecraft.entity.player.EntityPlayer v_entityPlayer : v_playerEntities) {
            entityPlayerList.add(new VanillaProxyEntityPlayer(v_entityPlayer));
        }
        return entityPlayerList;
    }

    @Override
    public boolean spawnEntity(Entity entity) {
        return v_world.spawnEntityInWorld(entity.v_getEntity());
    }

    @Override
    public EntityCow createEntityCow() {
        return new VanillaProxyEntityCow(v_world);
    }

    @Override
    public BlockState getBlockState(int x, int y, int z) {
        return new VanillaProxyBlockState(v_world.getBlockState(new BlockPos(x, y, z)));
    }

    @Override
    public boolean setBlockState(int x, int y, int z, BlockState blockState) {
        return v_world.setBlockState(new BlockPos(x, y, z), blockState.v_getIBlockState());
    }
}
