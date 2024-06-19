package com.artedprvt.std.impls.minecraft.event;


import com.artedprvt.std.minecraft.event.Event;

public class VanillaProxyBaseEvent<T extends net.minecraftforge.fml.common.eventhandler.Event> implements Event {
    public T v_event;

    public VanillaProxyBaseEvent(T v_event) {
        this.v_event = v_event;
    }

    @Override
    public T v_getEvent() {
        return v_event;
    }

    @Override
    public boolean isCanceled() {
        return v_event.isCanceled();
    }
}
