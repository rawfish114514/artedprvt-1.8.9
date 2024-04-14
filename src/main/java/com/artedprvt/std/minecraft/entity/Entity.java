package com.artedprvt.std.minecraft.entity;

import com.artedprvt.iv.anno.InterfaceView;

@InterfaceView
public interface Entity {
    net.minecraft.entity.Entity v_getEntity();

    @InterfaceView
    int getId();

    @InterfaceView
    String getName();

    @InterfaceView
    double[] getPosition();

    @InterfaceView
    void setPosition(double x, double y, double z);

    @InterfaceView
    float getRotationYaw();

    @InterfaceView
    float getRotationPitch();

}
