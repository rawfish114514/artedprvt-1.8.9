package com.artedprvt.std.minecraftvp.entity;

import com.artedprvt.std.math.Vector3;
import com.artedprvt.std.minecraft.entity.Entity;

public class VanillaProxyEntity implements Entity {
    public net.minecraft.entity.Entity v_entity;

    public VanillaProxyEntity(net.minecraft.entity.Entity v_entity) {
        this.v_entity = v_entity;
    }

    @Override
    public net.minecraft.entity.Entity v_getEntity() {
        return v_entity;
    }

    @Override
    public int getId() {
        return v_entity.getEntityId();
    }

    @Override
    public String getName() {
        return v_entity.getName();
    }

    @Override
    public Vector3 getPosition() {
        return new Vector3(v_entity.posX, v_entity.posY, v_entity.posZ);
    }

    @Override
    public void setPosition(Vector3 vector3) {
        v_entity.posX=vector3.getX();
        v_entity.posY=vector3.getY();
        v_entity.posZ=vector3.getZ();
    }
}
