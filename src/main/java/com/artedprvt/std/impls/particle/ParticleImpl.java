package com.artedprvt.std.impls.particle;

import com.artedprvt.std.particle.Particle;
import com.artedprvt.std.particle.ParticleModifier;

public class ParticleImpl implements Particle {
    public ParticleModifier modifier;
    public boolean updating = true;
    public int maxAge;
    public int age;
    public boolean dead;

    public int red = 255;
    public int green = 255;
    public int blue = 255;
    public static final int alpha = 255;

    public float scale = 1;
    public boolean nonUnitScale = false;

    public float posX;
    public float posY;
    public float posZ;

    public ParticleImpl(float x, float y, float z, int age) {
        posX = x;
        posY = y;
        posZ = z;
        maxAge = age;

        putLast();
    }

    @Override
    public void setModifier(ParticleModifier modifier) {
        this.modifier = modifier;
    }

    @Override
    public ParticleModifier getModifier() {
        return modifier;
    }

    @Override
    public void removeModifier() {
        modifier = null;
    }

    @Override
    public void setUpdating(boolean updating) {
        this.updating = updating;
    }

    @Override
    public boolean isUpdating() {
        return updating;
    }

    @Override
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public int getMaxAge() {
        return maxAge;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setColor(int r, int g, int b) {
        red = r;
        green = g;
        blue = b;
    }

    @Override
    public int[] getColor() {
        return new int[]{red, green, blue};
    }

    @Override
    public void setScale(float s) {
        scale = s;
        nonUnitScale = s != 1;
    }

    @Override
    public float getScale() {
        return scale;
    }

    @Override
    public void setPos(float x, float y, float z) {
        posX = x;
        posY = y;
        posZ = z;
    }

    @Override
    public float[] getPos() {
        return new float[]{posX, posY, posZ};
    }


    /* 逻辑 */

    public float lastX;
    public float lastY;
    public float lastZ;

    public float addsX;
    public float addsY;
    public float addsZ;

    public void update() {
        if (++age == maxAge) {
            dead = true;
            return;
        }

        putLast();
        putAdds();
    }

    public void putLast() {
        lastX = posX;
        lastY = posY;
        lastZ = posZ;
    }

    public void putAdds() {
        addsX = posX - lastX;
        addsY = posY - lastY;
        addsZ = posZ - lastZ;
    }

    /* 渲染 */

    public static float interpX;
    public static float interpY;
    public static float interpZ;

    public static final short skyLight = 240;
    public static final short blockLight = 240;
    public static final float textureBegin = 0;
    public static final float textureEnd = 0.0624375f;

    public void render(int offset,
                       final AsyncWorldRenderer asyncWorldRenderer,
                       final float partialTicks,
                       float rX,
                       float rZ,
                       float rYZ,
                       float rXY,
                       float rXZ) {
        float baseX = lastX - interpX + addsX * partialTicks;
        float baseY = lastY - interpY + addsY * partialTicks;
        float baseZ = lastZ - interpZ + addsZ * partialTicks;

        if (nonUnitScale) {
            rX = rX * scale;
            rZ = rZ * scale;
            rXY = rXY * scale;
            rXZ = rXZ * scale;
            rYZ = rYZ * scale;
        }

        float x1 = baseX - rX;
        float x2 = baseX + rX;
        float y1 = baseY - rZ;
        float y2 = baseY + rZ;
        float z1 = baseZ - rYZ;
        float z2 = baseZ + rYZ;


        asyncWorldRenderer.vertex(
                offset,
                x1 - rXY,
                y1,
                z1 - rXZ,
                textureEnd, textureEnd,
                red, green, blue, alpha,
                skyLight, blockLight);

        asyncWorldRenderer.vertex(
                offset += AsyncWorldRenderer.PARTICLE_RENDER_VERTEX_SIZE,
                x1 + rXY,
                y2,
                z1 + rXZ,
                textureEnd, textureBegin,
                red, green, blue, alpha,
                skyLight, blockLight);

        asyncWorldRenderer.vertex(
                offset += AsyncWorldRenderer.PARTICLE_RENDER_VERTEX_SIZE,
                x2 + rXY,
                y2,
                z2 + rXZ,
                textureBegin, textureBegin,
                red, green, blue, alpha,
                skyLight, blockLight);

        asyncWorldRenderer.vertex(
                offset + AsyncWorldRenderer.PARTICLE_RENDER_VERTEX_SIZE,
                x2 - rXY,
                y1,
                z2 - rXZ,
                textureBegin, textureEnd,
                red, green, blue, alpha,
                skyLight, blockLight);
    }
}
