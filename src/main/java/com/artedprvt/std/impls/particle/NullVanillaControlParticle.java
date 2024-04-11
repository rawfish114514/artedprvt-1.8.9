package com.artedprvt.std.impls.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;

public final class NullVanillaControlParticle extends VanillaControlParticle {
    public NullVanillaControlParticle(net.minecraft.world.World worldIn, double posXIn, double posYIn, double posZIn) {
        super(worldIn, posXIn, posYIn, posZIn);
    }

    public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
    }

    @Override
    public void setDead() {
        isDead = true;
    }
}
