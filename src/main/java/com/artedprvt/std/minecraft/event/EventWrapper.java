package com.artedprvt.std.minecraft.event;

import com.artedprvt.std.impls.minecraft.event.VanillaProxyEvent;
import com.artedprvt.std.impls.minecraft.event.game.VanillaProxyEventClientTick;
import com.artedprvt.std.minecraft.event.game.EventClientTick;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventWrapper {
    public static Event wrap(final net.minecraftforge.fml.common.eventhandler.Event v_event) {
        return new VanillaProxyEvent(v_event);
    }

    public static EventClientTick wrap(final TickEvent.ClientTickEvent v_event) {
        return new VanillaProxyEventClientTick(v_event);
    }
}
