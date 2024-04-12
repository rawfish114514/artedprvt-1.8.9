package com.artedprvt.std.particle;

import com.artedprvt.iv.anno.InterfaceView;

/**
 * 粒子修改器
 */
@InterfaceView
public interface ParticleModifier {
    @InterfaceView
    void modify(Particle particle);
}
