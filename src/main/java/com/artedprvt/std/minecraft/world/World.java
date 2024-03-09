package com.artedprvt.std.minecraft.world;

import com.artedprvt.std.minecraft.entity.PlayerEntity;
import net.minecraft.entity.player.EntityPlayer;
import com.artedprvt.iv.anno.InterfaceView;

import java.util.ArrayList;
import java.util.List;

@InterfaceView
public class World {
    protected net.minecraft.world.World Mworld;

    public World(net.minecraft.world.World Mworld) {
        this.Mworld = Mworld;
    }

    @InterfaceView
    public List<PlayerEntity> getPlayerEntityList() {
        List<EntityPlayer> MplayerEntities = Mworld.playerEntities;
        List<PlayerEntity> playerEntityList = new ArrayList<>();
        for (EntityPlayer MentityPlayer : MplayerEntities) {
            playerEntityList.add(new PlayerEntity(MentityPlayer));
        }
        return playerEntityList;
    }
}
