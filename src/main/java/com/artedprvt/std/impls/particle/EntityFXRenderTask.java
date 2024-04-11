package com.artedprvt.std.impls.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;

import java.util.List;
import java.util.concurrent.Callable;

public final class EntityFXRenderTask implements Callable<WorldRenderer> {
    final List<EntityFX> entityFXList;
    final WorldRenderer worldRenderer;
    final Entity entity;
    final float partialTicks;
    final float rotationX;
    final float rotationZ;
    final float rotationYZ;
    final float rotationXY;
    final float rotationXZ;

    public EntityFXRenderTask(List<EntityFX> entityFXList,
                              WorldRenderer worldRenderer, Entity entity,
                              float partialTicks, float rotationX, float rotationZ,
                              float rotationYZ, float rotationXY, float rotationXZ) {
        this.entityFXList = entityFXList;
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
        for (EntityFX entityFX : entityFXList) {
            entityFX.renderParticle(worldRenderer, entity, partialTicks,
                    rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);

        }

        return worldRenderer;
    }
}