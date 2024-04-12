package com.artedprvt.std.impls.particle;

import net.minecraft.client.particle.EntityFX;

import java.util.ArrayList;
import java.util.List;

public final class EntityFXUpdateTask implements Runnable {
    public final List<EntityFX> entityFXList;

    public EntityFXUpdateTask(List<EntityFX> entityFXList) {
        this.entityFXList = entityFXList;
    }

    @Override
    public void run() {
        List<EntityFX> removeList = new ArrayList<>();

        EntityFX[] entityFXArray =new EntityFX[entityFXList.size()];
        entityFXList.toArray(entityFXArray);
        for (EntityFX entityFX:entityFXArray) {
            entityFX.onUpdate();
            if (entityFX.isDead) {
                removeList.add(entityFX);
            }
        }

        entityFXList.removeAll(removeList);
    }
}
