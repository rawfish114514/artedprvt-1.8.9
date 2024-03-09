package com.artedprvt.std.minecraft.entity;

import net.minecraft.entity.player.EntityPlayer;
import com.artedprvt.iv.anno.InterfaceView;

@InterfaceView
public class PlayerEntity extends Entity {
    public PlayerEntity(EntityPlayer MentityPlayer) {
        super(MentityPlayer);
    }
}
