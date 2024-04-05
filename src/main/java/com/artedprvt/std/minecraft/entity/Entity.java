package com.artedprvt.std.minecraft.entity;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.math.Vector3;

@InterfaceView
public interface Entity {
    net.minecraft.entity.Entity v_getEntity();

    @InterfaceView
    int getId();

    @InterfaceView
    String getName();

    @InterfaceView
    Vector3 getPosition();

    @InterfaceView
    void setPosition(Vector3 vector3);
}
