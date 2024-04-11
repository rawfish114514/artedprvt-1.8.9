package com.artedprvt.std.particle;

import com.artedprvt.iv.anno.InterfaceView;
import net.minecraft.client.particle.EntityFX;

@InterfaceView
public interface Particle {
    EntityFX v_getEntityFX();

    @InterfaceView
    void setParticleUpdate(ParticleUpdate particleUpdate);

    @InterfaceView
    int counting();

    @InterfaceView
    void setColor(int r,int g,int b);

    @InterfaceView
    void setScale(float s);

    @InterfaceView
    void setMaxAge(int n);

    @InterfaceView
    void setPos(double x, double y, double z);

    @InterfaceView
    void setSpeed(double x, double y, double z);

    @InterfaceView
    void setAccel(double x, double y, double z);

    @InterfaceView
    int[] getColor();

    @InterfaceView
    float getScale();

    @InterfaceView
    int getMaxAge();

    @InterfaceView
    int getAge();

    @InterfaceView
    double[] getPos();

    @InterfaceView
    double[] getSpeed();

    @InterfaceView
    double[] getAccel();

    @InterfaceView
    void initInts(int length);

    @InterfaceView
    void initDoubles(int length);

    @InterfaceView
    int getIntsLength();

    @InterfaceView
    int getDoublesLength();

    @InterfaceView
    void setInt(int index, int value);

    @InterfaceView
    void setDouble(int index, double value);

    @InterfaceView
    int getInt(int index);

    @InterfaceView
    double getDouble(int index);

    @InterfaceView
    double transInt(int index0, int index1, double value);

    @InterfaceView
    double transDouble(int index0, int index1, double value);
}
