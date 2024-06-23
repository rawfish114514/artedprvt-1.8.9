package com.artedprvt.std.minecraft.event;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.impls.minecraft.event.VanillaProxyEvent;
import com.artedprvt.std.impls.minecraft.event.entity.player.VanillaProxyEventPlayerInteract;
import com.artedprvt.std.impls.minecraft.event.game.VanillaProxyEventTick;
import com.artedprvt.std.minecraft.event.entity.player.EventPlayerInteract;
import com.artedprvt.std.minecraft.event.game.EventTick;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@InterfaceView
public class EventTypes extends EventHelper {
    @InterfaceView
    public static final EventType<Event> all = new EventType<>(
            Event.class,
            net.minecraftforge.fml.common.eventhandler.Event.class,
            VanillaProxyEvent::new);

    @InterfaceView
    public static final EventType<EventTick> tick = new EventType<>(
            EventTick.class,
            TickEvent.class,
            (v) -> new VanillaProxyEventTick((TickEvent) v));

    @InterfaceView
    public static final EventType<EventPlayerInteract> player_interact = new EventType<>(
            EventPlayerInteract.class,
            PlayerInteractEvent.class,
            (v) -> new VanillaProxyEventPlayerInteract((PlayerInteractEvent) v));
}
