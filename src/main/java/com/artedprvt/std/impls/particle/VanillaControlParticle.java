package com.artedprvt.std.impls.particle;

import com.artedprvt.std.particle.Particle;
import com.artedprvt.std.particle.ParticleUpdate;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class VanillaControlParticle extends EntityFX implements Particle {
    public int count = 0;//计数器

    public double accelerationX = 0;
    public double accelerationY = 0;
    public double accelerationZ = 0;

    public ParticleUpdate particleUpdate = null;

    public int[] ints;//额外可选数量的整数数据
    public double[] doubles;//额外可选数量的小数数据

    public byte red = -1;
    public byte green = -1;
    public byte blue = -1;
    public byte alpha = -1;

    public boolean nonUnitScale = true;

    public short skyLight = 240;
    public short blockLight = 240;

    public static final float textureBegin = 0;
    public static final float textureEnd = 0.0624375f;

    public VanillaControlParticle(World worldIn, double posXIn, double posYIn, double posZIn) {
        super(worldIn, posXIn, posYIn, posZIn);
    }

    @Override
    public EntityFX v_getEntityFX() {
        return this;
    }

    @Override
    public void setColor(int r, int g, int b) {
        red = (byte) (r);
        green = (byte) g;
        blue = (byte) b;
    }

    @Override
    public void setScale(float s) {
        particleScale = s;
        nonUnitScale = s != 1.0f;
    }

    @Override
    public void setMaxAge(int n) {
        particleMaxAge = n;
    }

    @Override
    public int getBrightnessForRender(float partialTicks) {
        return 0xf0f0;
    }

    /**
     * Gets how bright this entity is.
     */
    @Override
    public float getBrightness(float partialTicks) {
        return 1.0F;
    }

    /**
     * Renders the particle
     */
    @Override
    public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }

    public void asyncRenderParticle(int offset,
                                    final AsyncWorldRenderer asyncWorldRenderer,
                                    final float partialTicks,
                                    float rX,
                                    float rZ,
                                    float rYZ,
                                    float rXY,
                                    float rXZ) {
        float baseX = (float) prevPosX - (float) interpPosX + addsPrevPosX * partialTicks;
        float baseY = (float) prevPosY - (float) interpPosY + addsPrevPosY * partialTicks;
        float baseZ = (float) prevPosZ - (float) interpPosZ + addsPrevPosZ * partialTicks;

        if (nonUnitScale) {
            rX = rX * particleScale;
            rZ = rZ * particleScale;
            rXY = rXY * particleScale;
            rXZ = rXZ * particleScale;
            rYZ = rYZ * particleScale;
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

    public void asyncRenderParticle2(int offset,
                                     AsyncWorldRenderer asyncWorldRenderer) {

        float begin = 0;
        float end = 0.0624375F;

        asyncWorldRenderer.vertex(
                offset,
                end, end,
                red, green, blue, alpha,
                skyLight, blockLight);

        asyncWorldRenderer.vertex(
                offset += AsyncWorldRenderer.PARTICLE_RENDER_VERTEX_SIZE,
                end, begin,
                red, green, blue, alpha,
                skyLight, blockLight);

        asyncWorldRenderer.vertex(
                offset += AsyncWorldRenderer.PARTICLE_RENDER_VERTEX_SIZE,
                begin, begin,
                red, green, blue, alpha,
                skyLight, blockLight);

        asyncWorldRenderer.vertex(
                offset + AsyncWorldRenderer.PARTICLE_RENDER_VERTEX_SIZE,
                begin, end,
                red, green, blue, alpha,
                skyLight, blockLight);
    }

    @Override
    public void setDead() {
        super.setDead();
    }

    public void onUpdate() {
        if (++this.particleAge == this.particleMaxAge) {
            this.setDead();
            return;
        }
        motionUpdate();
        addsPos();
    }

    public float addsPrevPosX;
    public float addsPrevPosY;
    public float addsPrevPosZ;

    public void addsPos() {
        addsPrevPosX = (float) (posX - prevPosX);
        addsPrevPosY = (float) (posY - prevPosY);
        addsPrevPosZ = (float) (posZ - prevPosZ);
    }


    public boolean isInRangeToRenderDist(double distance) {
        return true;
    }


    public void setLastPosition() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
    }

    public void setParticleUpdate(ParticleUpdate particleUpdate) {
        this.particleUpdate = particleUpdate;
    }

    public int counting() {
        return count++;
    }

    //运动学


    //设置位置
    @Override
    public void setPos(double x, double y, double z) {
        posX = x;
        posY = y;
        posZ = z;
    }

    //设置速度
    @Override
    public void setSpeed(double x, double y, double z) {
        motionX = x;
        motionY = y;
        motionZ = z;
    }

    //设置加速度
    @Override
    public void setAccel(double x, double y, double z) {
        accelerationX = x;
        accelerationY = y;
        accelerationZ = z;
    }


    @Override
    public int[] getColor() {
        return new int[]{red, green, blue};
    }

    @Override
    public float getScale() {
        return particleScale;
    }

    @Override
    public int getAge() {
        return particleAge;
    }

    @Override
    public int getMaxAge() {
        return particleMaxAge;
    }

    @Override
    public double[] getPos() {
        return new double[]{posX, posY, posZ};
    }

    @Override
    public double[] getSpeed() {
        return new double[]{motionX, motionY, motionZ};
    }

    @Override
    public double[] getAccel() {
        return new double[]{accelerationX, accelerationY, accelerationZ};
    }

    public void motionUpdate() {
        if (particleUpdate != null) {
            particleUpdate.update(this);
        }
        setLastPosition();
        setPos(posX + motionX, posY + motionY, posZ + motionZ);
        setSpeed(motionX + accelerationX, motionY + accelerationY, motionZ + accelerationZ);
    }

    @Override
    public void initInts(int length) {
        ints = new int[length];
    }

    @Override
    public void initDoubles(int length) {
        doubles = new double[length];
    }

    @Override
    public int getIntsLength() {
        return ints.length;
    }

    @Override
    public int getDoublesLength() {
        return doubles.length;
    }

    @Override
    public void setInt(int index, int value) {
        ints[index] = value;
    }

    @Override
    public void setDouble(int index, double value) {
        doubles[index] = value;
    }

    @Override
    public int getInt(int index) {
        return ints[index];
    }

    @Override
    public double getDouble(int index) {
        return doubles[index];
    }

    @Override
    public double transInt(int index0, int index1, double value) {
        return ints[index0] + (ints[index1] - ints[index0]) * value;
    }

    @Override
    public double transDouble(int index0, int index1, double value) {
        return doubles[index0] + (doubles[index1] - doubles[index0]) * value;
    }

}
