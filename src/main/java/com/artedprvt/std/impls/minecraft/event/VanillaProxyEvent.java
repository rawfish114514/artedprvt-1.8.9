package com.artedprvt.std.impls.minecraft.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class VanillaProxyEvent extends VanillaProxyBaseEvent<Event> {
    public VanillaProxyEvent() {
        super(new Event());
    }

    public VanillaProxyEvent(Event v_event) {
        super(v_event);
    }
}
