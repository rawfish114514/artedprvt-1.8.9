package com.artedprvt.std.impls.particle;

import java.util.List;

public abstract class UpdateTask implements Runnable {
    public final List<ParticleImpl> particleList;

    public UpdateTask(List<ParticleImpl> particleList) {
        this.particleList = particleList;
    }

}
