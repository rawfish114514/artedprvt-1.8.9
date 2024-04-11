package com.artedprvt.std.impls.particle;

import java.util.ArrayList;
import java.util.List;

public class DefaultUpdateTask extends UpdateTask {
    public DefaultUpdateTask(List<VanillaControlParticle> particleList) {
        super(particleList);
    }

    @Override
    public void run() {
        List<VanillaControlParticle> removeList = new ArrayList<>();

        VanillaControlParticle[] particles = new VanillaControlParticle[particleList.size()];
        particleList.toArray(particles);
        for (VanillaControlParticle particle : particles) {
            particle.onUpdate();
            if (particle.isDead) {
                removeList.add(particle);
            }
        }
        particleList.removeAll(removeList);
    }
}
