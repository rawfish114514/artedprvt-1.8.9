package com.artedprvt.std.impls.minecraft.entity;

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
    public double[] getPosition() {
        return new double[]{v_entity.posX, v_entity.posY, v_entity.posZ};
    }

    @Override
    public void setPosition(double x, double y, double z) {
        v_entity.posX=x;
        v_entity.posY=y;
        v_entity.posZ=z;
    }

    @Override
    public float getRotationYaw() {
        return v_entity.rotationYaw;
    }

    @Override
    public float getRotationPitch() {
        return v_entity.rotationPitch;
    }
}
