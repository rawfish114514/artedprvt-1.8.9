package com.artedprvt.std.particle;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.minecraft.world.World;

import java.util.Collection;
import java.util.List;

@InterfaceView
public interface EffectSpawn {
    @InterfaceView
    void setWorld(World world);

    /**
     * 创建粒子
     * @param x 初始位置
     * @param y 初始位置
     * @param z 初始位置
     * @param age 年龄 逻辑刻
     * @return
     */
    @InterfaceView
    Particle createParticle(double x, double y, double z, int age);

    /**
     * 添加粒子
     * @param particle
     */
    @InterfaceView
    void spawnParticle(Particle particle);

    /**
     * 添加粒子列表
     * @param particles 所有粒子的年龄应相同 以此获取最高性能
     */
    @InterfaceView
    void spawnParticles(List<Particle> particles);
}
