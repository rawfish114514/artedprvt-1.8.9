package com.artedprvt.std.minecraft.event;

import net.minecraftforge.fml.common.eventhandler.ListenerList;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EventBusCore {
    static Field vFiled_busID;

    static {
        vFiled_busID = ReflectionHelper.findField(net.minecraftforge.fml.common.eventhandler.EventBus.class,
                "busID");
    }

    public final net.minecraftforge.fml.common.eventhandler.EventBus v_eventBus;
    public final int v_busID;

    public Map<EventType<?>, ListenerList> typeListenerListMap = new HashMap<>();
    public Map<EventType<?>, Map<EventPriority, ListenerListAdapter<?>>> typePriorityListAdapterMap = new HashMap<>();

    public EventBusCore(net.minecraftforge.fml.common.eventhandler.EventBus v_eventBus) {
        this.v_eventBus = v_eventBus;
        try {
            v_busID = (int) vFiled_busID.get(v_eventBus);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends Event> void register(EventType<T> eventType, EventPriority priority, EventListener<T> eventListener) {

        Map<EventPriority, ListenerListAdapter<?>> priorityListAdapterMap = typePriorityListAdapterMap.computeIfAbsent(eventType, k -> new HashMap<>());

        ListenerListAdapter<T> listener = (ListenerListAdapter<T>) priorityListAdapterMap.get(priority);
        if (listener == null) {
            listener = new ListenerListAdapter<>(eventType);
            priorityListAdapterMap.put(priority, listener);
            ListenerList listenerList = typeListenerListMap.get(eventType);
            try {
                if (listenerList == null) {
                    Constructor<?> ctr = eventType.v_eventClass.getConstructor();
                    ctr.setAccessible(true);
                    net.minecraftforge.fml.common.eventhandler.Event event =
                            (net.minecraftforge.fml.common.eventhandler.Event) ctr.newInstance();
                    listenerList = event.getListenerList();
                    typeListenerListMap.put(eventType, listenerList);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            listenerList.register(v_busID, priority.v_getEventPriority(), listener);
        }
        listener.listeners.add(eventListener);
    }

    public <T extends Event> void unregister(EventType<T> eventType, EventListener<T> eventListener) {
        Map<EventPriority, ListenerListAdapter<?>> priorityListAdapterMap = typePriorityListAdapterMap.get(eventType);
        if (priorityListAdapterMap != null) {
            for (ListenerListAdapter<?> listenerListAdapter : priorityListAdapterMap.values()) {
                listenerListAdapter.listeners.remove(eventListener);
            }
        }
    }

    public <T extends Event> void unregisterAll(EventType<T> eventType, Collection<EventListener<T>> eventListeners) {
        Map<EventPriority, ListenerListAdapter<?>> priorityListAdapterMap = typePriorityListAdapterMap.get(eventType);
        if (priorityListAdapterMap != null) {
            for (ListenerListAdapter<?> listenerListAdapter : priorityListAdapterMap.values()) {
                for (EventListener<T> eventListener : eventListeners) {
                    listenerListAdapter.listeners.remove(eventListener);
                }
            }
        }
    }

    public void unregisterAll(Map<EventType<?>, ? extends Collection<EventListener<?>>> listeners) {
        for (Map.Entry<EventType<?>, ? extends Collection<EventListener<?>>> entry : listeners.entrySet()) {
            Map<EventPriority, ListenerListAdapter<?>> priorityListAdapterMap = typePriorityListAdapterMap.get(entry.getKey());
            if (priorityListAdapterMap != null) {
                for (ListenerListAdapter<?> listenerListAdapter : priorityListAdapterMap.values()) {
                    listenerListAdapter.listeners.removeAll(entry.getValue());
                }
            }
        }
    }

    /**
     * 此对象代理原版事件总线 当不在需要时可能调用此方法
     */
    public void clear() {
        for (Map.Entry<EventType<?>, ListenerList> entry : typeListenerListMap.entrySet()) {
            Map<EventPriority, ListenerListAdapter<?>> priorityListAdapterMap = typePriorityListAdapterMap.get(entry.getKey());
            if (priorityListAdapterMap != null) {
                for (ListenerListAdapter<?> listenerListAdapter : priorityListAdapterMap.values()) {
                    entry.getValue().unregister(v_busID, listenerListAdapter);
                }
            }
        }
        typeListenerListMap.clear();
        typePriorityListAdapterMap.clear();
    }
}
