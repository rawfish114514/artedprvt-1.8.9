package com.artedprvt.std.impls.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;

import java.util.List;
import java.util.concurrent.Callable;

public class UnionRenderTask implements Callable<WorldRenderer> {
    final List<VanillaControlParticle> particleList;
    final AsyncWorldRenderer worldRenderer;
    final Entity entity;
    final float partialTicks;
    final float rotationX;
    final float rotationZ;
    final float rotationYZ;
    final float rotationXY;
    final float rotationXZ;

    public UnionRenderTask(List<VanillaControlParticle> particleList,
                           AsyncWorldRenderer worldRenderer, Entity entity,
                           float partialTicks, float rotationX, float rotationZ,
                           float rotationYZ, float rotationXY, float rotationXZ) {
        this.particleList = particleList;
        this.worldRenderer = worldRenderer;
        this.entity = entity;
        this.partialTicks = partialTicks;
        this.rotationX = rotationX;
        this.rotationZ = rotationZ;
        this.rotationYZ = rotationYZ;
        this.rotationXY = rotationXY;
        this.rotationXZ = rotationXZ;
    }

    @Override
    public WorldRenderer call() {
        int offset = 0;
        VanillaControlParticle[] particles=new VanillaControlParticle[particleList.size()];
        particleList.toArray(particles);
        for (VanillaControlParticle particle : particles) {
            particle.asyncRenderParticle(offset,
                    worldRenderer, partialTicks,
                    rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);

            offset += AsyncWorldRenderer.PARTICLE_RENDER_SIZE;
        }
        return worldRenderer;
    }
}