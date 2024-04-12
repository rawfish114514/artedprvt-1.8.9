package com.artedprvt.std.impls.particle;

import java.util.ArrayList;
import java.util.List;

/**
 * 合并的粒子列表
 * 可以根据特征优化
 */
public class UnionUpdateTask extends UpdateTask {
    public UnionUpdateTask(List<ParticleImpl> particleList) {
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
