package com.artedprvt.std.impls.particle;

import java.util.List;

public class UnionUpdateTask extends UpdateTask {
    public UnionUpdateTask(List<VanillaControlParticle> particleList) {
        super(particleList);
    }

    @Override
    public void run() {
        VanillaControlParticle[] particles = new VanillaControlParticle[particleList.size()];
        particleList.toArray(particles);
        for (VanillaControlParticle particle : particles) {
            if (particle.isDead) {
                particleList.clear();
                return;
            }
            particle.onUpdate();
        }
    }
}
