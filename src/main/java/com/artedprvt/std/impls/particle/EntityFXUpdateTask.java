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

        EntityFX entityFX;
        for (int i = 0; i < entityFXList.size(); i++) {
            entityFX = entityFXList.get(i);
            entityFX.onUpdate();
            if (entityFX.isDead) {
                removeList.add(entityFX);
            }
        }

        entityFXList.removeAll(removeList);
    }
}
