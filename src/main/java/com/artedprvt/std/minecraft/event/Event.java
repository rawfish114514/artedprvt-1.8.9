package com.artedprvt.std.minecraft.event;

import com.artedprvt.iv.anno.InterfaceView;

/**
 * 事件基类
 */
@InterfaceView
public interface Event {
    net.minecraftforge.fml.common.eventhandler.Event v_getEvent();

    /**
     * 此事件是否可以取消
     * @return
     */
    @InterfaceView
    boolean isCancelable();

    /**
     * 已取消
     * @return 事件是否已取消 true 否则 false
     */
    @InterfaceView
    boolean isCanceled();

    /**
     * 设置已取消
     * @param cancel
     */
    @InterfaceView
    void setCanceled(boolean cancel);

}
