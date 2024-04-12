package com.artedprvt.std.particle;

import com.artedprvt.iv.anno.InterfaceView;

/**
 * 粒子
 */
@InterfaceView
public interface Particle {
    /**
     * 设置修改器
     * @param modifier
     */
    @InterfaceView
    void setModifier(ParticleModifier modifier);

    /**
     * 获取修改器
     * @return
     */
    @InterfaceView
    ParticleModifier getModifier();

    /**
     * 移除修改器
     */
    @InterfaceView
    void removeModifier();

    /**
     * 设置更新
     * @param updating
     */
    @InterfaceView
    void setUpdating(boolean updating);

    /**
     * 获取更新
     * @return
     */
    @InterfaceView
    boolean isUpdating();

    /**
     * 设置最大年龄
     *
     * @param maxAge 最大年龄 单位 tick
     */
    @InterfaceView
    void setMaxAge(int maxAge);

    /**
     * 获取最大年龄
     *
     * @return
     */
    @InterfaceView
    int getMaxAge();

    /**
     * 获取年龄
     *
     * @return
     */
    @InterfaceView
    int getAge();

    /**
     * 设置颜色
     *
     * @param r
     * @param g
     * @param b
     */
    @InterfaceView
    void setColor(int r, int g, int b);

    /**
     * 获取颜色
     *
     * @return
     */
    @InterfaceView
    int[] getColor();

    /**
     * 设置位置
     *
     * @param x
     * @param y
     */
    @InterfaceView
    void setPos(float x, float y, float z);

    /**
     * 获取位置
     *
     * @return
     */
    @InterfaceView
    float[] getPos();

    /**
     * 设置缩放
     *
     * @param s
     */
    @InterfaceView
    void setScale(float s);

    /**
     * 获取缩放
     *
     * @return
     */
    @InterfaceView
    float getScale();

    /**
     * 更新
     */
    @InterfaceView
    void update();
}
