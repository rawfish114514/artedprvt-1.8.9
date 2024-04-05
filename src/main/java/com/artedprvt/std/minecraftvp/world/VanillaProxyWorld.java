package com.artedprvt.std.minecraftvp.world;

import com.artedprvt.std.minecraft.entity.Entity;
import com.artedprvt.std.minecraft.entity.EntityCow;
import com.artedprvt.std.minecraft.entity.EntityPlayer;
import com.artedprvt.std.minecraft.world.World;
import com.artedprvt.std.minecraftvp.entity.VanillaProxyEntityCow;
import com.artedprvt.std.minecraftvp.entity.VanillaProxyEntityPlayer;

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
}
