package com.artedprvt.std.impls.particle;

import java.util.ArrayList;
import java.util.List;

public class DefaultUpdateTask extends UpdateTask {
    public DefaultUpdateTask(List<ParticleImpl> particleList) {
        super(particleList);
    }

    @Override
    public void run() {
        List<ParticleImpl> removeList = new ArrayList<>();

        ParticleImpl[] particles = new ParticleImpl[particleList.size()];
        particleList.toArray(particles);
        for (ParticleImpl particle : particles) {
            particle.update();
            if (particle.dead) {
                removeList.add(particle);
            }
        }
        particleList.removeAll(removeList);
    }
}
