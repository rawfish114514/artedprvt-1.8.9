package com.artedprvt.std.particle;

import com.artedprvt.iv.anno.InterfaceView;

import java.util.List;

/**
 * 粒子服务
 */
@InterfaceView
public interface ParticleService {
    /**
     * 创建粒子
     *
     * @param x   初始位置
     * @param y   初始位置
     * @param z   初始位置
     * @param age 年龄 逻辑刻
     * @return
     */
    @InterfaceView
    Particle createParticle(float x, float y, float z, int age);

    /**
     * 添加粒子
     * 同时注册粒子
     * 粒子被粒子系统接管 对其更新和渲染
     *
     * @param particle
     */
    @InterfaceView
    void spawnParticle(Particle particle);

    /**
     * 添加粒子列表
     * 同时注册粒子
     * 粒子被粒子系统接管 对其更新和渲染
     *
     * @param particles
     */
    @InterfaceView
    void spawnParticles(List<Particle> particles);
}
