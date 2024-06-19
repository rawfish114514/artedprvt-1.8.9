package com.artedprvt.std.minecraft.event;

import com.artedprvt.std.impls.minecraft.event.VanillaProxyEvent;

public class EventHelper {
    public static Event createEvent() {
        return new VanillaProxyEvent();
    }
}
