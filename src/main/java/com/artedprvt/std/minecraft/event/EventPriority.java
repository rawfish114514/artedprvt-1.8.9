package com.artedprvt.std.minecraft.event;

import com.artedprvt.iv.anno.InterfaceView;

/**
 * 事件优先级
 * 优先级高的先执行
 */
@InterfaceView
public enum EventPriority {
    @InterfaceView
    HIGHEST(net.minecraftforge.fml.common.eventhandler.EventPriority.HIGHEST),
    @InterfaceView
    HIGH(net.minecraftforge.fml.common.eventhandler.EventPriority.HIGH),
    @InterfaceView
    NORMAL(net.minecraftforge.fml.common.eventhandler.EventPriority.NORMAL),
    @InterfaceView
    LOW(net.minecraftforge.fml.common.eventhandler.EventPriority.LOW),
    @InterfaceView
    LOWEST(net.minecraftforge.fml.common.eventhandler.EventPriority.LOWEST),

    ;

    final net.minecraftforge.fml.common.eventhandler.EventPriority v_eventPriority;

    EventPriority(net.minecraftforge.fml.common.eventhandler.EventPriority v_eventPriority) {
        this.v_eventPriority = v_eventPriority;
    }

    public net.minecraftforge.fml.common.eventhandler.EventPriority v_getEventPriority() {
        return v_eventPriority;
    }
}
