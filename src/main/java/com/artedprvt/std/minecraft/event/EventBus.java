package com.artedprvt.std.minecraft.event;

import com.artedprvt.core.InProcess;
import com.artedprvt.core.Process;
import net.minecraftforge.common.MinecraftForge;

import java.util.*;

public class EventBus implements InProcess {
    public static Map<net.minecraftforge.fml.common.eventhandler.EventBus, EventBusCore> map = new HashMap<>();

    public EventBusCore eventBusCore;

    public Map<EventType<?>, Set<EventListener<?>>> listeners = new HashMap<>();

    public EventBus() {
        this(MinecraftForge.EVENT_BUS);
    }

    public EventBus(net.minecraftforge.fml.common.eventhandler.EventBus v_eventBus) {
        Process process = up();
        if (process == null) {
            System.out.println("Process is null");
        }
        eventBusCore = map.computeIfAbsent(v_eventBus, EventBusCore::new);
    }

    public <T extends Event> void register(EventType<T> eventType, EventListener<T> eventListener) {
        register(eventType, EventPriority.NORMAL, eventListener);
    }

    public <T extends Event> void register(EventType<T> eventType, EventPriority priority, EventListener<T> eventListener) {
        eventBusCore.register(eventType, priority, eventListener);
        Set<EventListener<?>> set = listeners.computeIfAbsent(eventType, k -> new HashSet<>());
        set.add(eventListener);
    }

    public <T extends Event> void unregister(EventType<T> eventType, EventListener<T> eventListener) {
        eventBusCore.unregister(eventType, eventListener);
        Set<EventListener<?>> set = listeners.get(eventType);
        if (set != null) {
            set.remove(eventListener);
        }
    }

    @Override
    public void close() throws Exception {
        eventBusCore.unregisterAll(listeners);
    }
}
