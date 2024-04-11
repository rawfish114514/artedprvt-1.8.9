package com.artedprvt.std.impls.particle;

import java.util.List;

public abstract class UpdateTask implements Runnable {
    public final List<VanillaControlParticle> particleList;

    public UpdateTask(List<VanillaControlParticle> particleList) {
        this.particleList = particleList;
    }

}
