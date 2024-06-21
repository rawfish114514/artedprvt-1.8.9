package com.artedprvt.std.minecraft.event;

import com.artedprvt.iv.anno.InterfaceView;

import java.util.function.Function;

/**
 * 事件类型
 */
@InterfaceView
public class EventType<T extends Event> {
    /**
     * 事件类
     */
    @InterfaceView
    public final Class<T> eventClass;
    public final Class<? extends net.minecraftforge.fml.common.eventhandler.Event> v_eventClass;
    public final Function<net.minecraftforge.fml.common.eventhandler.Event, T> function;

    EventType(Class<T> eventClass,
              Class<? extends net.minecraftforge.fml.common.eventhandler.Event> v_eventClass,
              Function<net.minecraftforge.fml.common.eventhandler.Event, T> function) {
        this.eventClass = eventClass;
        this.v_eventClass = v_eventClass;
        this.function = function;
    }

    public <V extends net.minecraftforge.fml.common.eventhandler.Event> T newInstance(V v) {
        return function.apply(v);
    }


}
