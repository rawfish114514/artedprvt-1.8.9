package com.artedprvt.std.impls.particle;

import com.artedprvt.std.particle.ParticleService;
import com.artedprvt.std.particle.Particle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;

import java.util.*;
import java.util.stream.Collectors;

public class ParticleServiceImpl implements ParticleService {
    public AsyncEffectRender asyncEffectRender;

    public ParticleServiceImpl() {
        EffectRenderer effectRenderer = Minecraft.getMinecraft().effectRenderer;
        if (effectRenderer instanceof AsyncEffectRender) {
            this.asyncEffectRender = (AsyncEffectRender) effectRenderer;
        } else {
            throw new RuntimeException("不支持新粒子系统");
        }
    }

    @Override
    public ParticleImpl createParticle(float x, float y, float z, int age) {
        ParticleImpl particle = new ParticleImpl(x,y,z,age);
        return particle;
    }

    @Override
    public void spawnParticle(Particle particle) {
        asyncEffectRender.addParticle((ParticleImpl) particle);
    }

    @Override
    public void spawnParticles(List<Particle> particles) {
        asyncEffectRender.addParticles(particles.stream().<ParticleImpl>map(p -> (ParticleImpl) p).collect(Collectors.toList()));
    }

}
