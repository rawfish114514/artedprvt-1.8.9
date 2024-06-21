package com.artedprvt.std.minecraft.event;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.impls.minecraft.event.VanillaProxyEvent;
import com.artedprvt.std.impls.minecraft.event.game.VanillaProxyEventClientTick;
import com.artedprvt.std.minecraft.event.game.EventClientTick;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@InterfaceView
public class EventTypes extends EventHelper {
    @InterfaceView
    public static final EventType<Event> ALL = new EventType<>(
            Event.class,
            net.minecraftforge.fml.common.eventhandler.Event.class,
            VanillaProxyEvent::new);

    @InterfaceView
    public static final EventType<EventClientTick> CLIENT_TICK = new EventType<>(
            EventClientTick.class,
            TickEvent.ClientTickEvent.class,
            (v) -> new VanillaProxyEventClientTick((TickEvent.ClientTickEvent) v));
}
