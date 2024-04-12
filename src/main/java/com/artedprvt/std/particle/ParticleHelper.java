package com.artedprvt.std.particle;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.impls.particle.ParticleServiceImpl;

@InterfaceView
public class ParticleHelper {
    @InterfaceView
    public static ParticleService createParticleService(){
        return new ParticleServiceImpl();
    }
}
