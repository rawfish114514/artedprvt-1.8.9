package com.artedprvt.std.minecraftvp.entity;

import com.artedprvt.std.minecraft.entity.EntityCow;
import net.minecraft.world.World;

public class VanillaProxyEntityCow extends VanillaProxyEntity implements EntityCow {
    public net.minecraft.entity.passive.EntityCow v_entityCoe;

    public VanillaProxyEntityCow(net.minecraft.entity.passive.EntityCow v_entityCoe) {
        super(v_entityCoe);
        this.v_entityCoe = v_entityCoe;
    }

    public VanillaProxyEntityCow(World v_world){
        super(new net.minecraft.entity.passive.EntityCow(v_world));
    }

    @Override
    public net.minecraft.entity.passive.EntityCow v_getEntityCow() {
        return v_entityCoe;
    }
}
