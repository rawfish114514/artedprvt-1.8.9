package com.artedprvt.std.minecraft.entity;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.math.Vector3;

import java.util.UUID;

@InterfaceView
public class Entity {
    protected net.minecraft.entity.Entity Mentity;

    public Entity(net.minecraft.entity.Entity Mentity) {
        this.Mentity = Mentity;
    }

    public net.minecraft.entity.Entity getMentity() {
        return Mentity;
    }

    @InterfaceView
    public int getId() {
        return Mentity.getEntityId();
    }

    @InterfaceView
    public String getName() {
        return Mentity.getName();
    }

    @InterfaceView
    public UUID getUUID() {
        return Mentity.getUniqueID();
    }

    @InterfaceView
    public Vector3 getPosition() {
        return new Vector3(Mentity.posX, Mentity.posY, Mentity.posZ);
    }


}
