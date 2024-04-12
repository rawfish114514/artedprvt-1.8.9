package com.artedprvt.std.impls.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;

import java.util.concurrent.Callable;

public final class RenderTask implements Callable<WorldRenderer> {
    final ParticleImpl[] particles;
    final AsyncWorldRenderer worldRenderer;
    final Entity entity;
    final float partialTicks;
    final float rotationX;
    final float rotationZ;
    final float rotationYZ;
    final float rotationXY;
    final float rotationXZ;

    public RenderTask(ParticleImpl[] particles,
                      AsyncWorldRenderer worldRenderer, Entity entity,
                      float partialTicks, float rotationX, float rotationZ,
                      float rotationYZ, float rotationXY, float rotationXZ) {
        this.particles = particles;
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
        for (ParticleImpl particle : particles) {
            particle.render(offset,
                    worldRenderer, partialTicks,
                    rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);

            offset += AsyncWorldRenderer.PARTICLE_RENDER_SIZE;
        }
        return worldRenderer;
    }
}
