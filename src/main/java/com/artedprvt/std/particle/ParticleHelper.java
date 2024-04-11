package com.artedprvt.std.particle;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.impls.particle.VanillaControlEffectSpawn;

@InterfaceView
public class ParticleHelper {
    @InterfaceView
    public static EffectSpawn createEffectSpawn(){
        return new VanillaControlEffectSpawn();
    }
}
