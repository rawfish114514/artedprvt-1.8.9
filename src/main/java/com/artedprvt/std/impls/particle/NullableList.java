package com.artedprvt.std.impls.particle;

import net.minecraft.client.particle.EntityFX;

import java.util.ArrayList;
import java.util.Collection;

public final class NullableList extends ArrayList<EntityFX> {
    public net.minecraft.world.World v_world;

    public NullableList(net.minecraft.world.World v_world, Collection<? extends EntityFX> c) {
        super(c);
        this.v_world = v_world;
    }

    public NullableList(net.minecraft.world.World v_world) {
        this.v_world = v_world;
    }

    @Override
    public EntityFX get(int index) {
        EntityFX entityFX = super.get(index);
        if (entityFX == null) {
            return getNull();
        }
        return entityFX;
    }

    public EntityFX getNull() {
        return new NullVanillaControlParticle(v_world, 0, 0, 0);
    }

    @Override
    public synchronized boolean removeAll(Collection<?> c) {
        try {
            return super.removeAll(c);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
