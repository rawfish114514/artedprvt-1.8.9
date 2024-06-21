package com.artedprvt.std.minecraft.event;

import com.artedprvt.core.InProcess;
import com.artedprvt.core.NonInpLogic;
import com.artedprvt.core.Process;
import com.artedprvt.iv.anno.InterfaceView;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 事件总线
 */
@InterfaceView
public class EventBus implements InProcess {
    public static Map<net.minecraftforge.fml.common.eventhandler.EventBus, EventBusCore> map = new HashMap<>();

    public EventBusCore eventBusCore;

    public Map<EventType<?>, Set<EventListener<?>>> listeners = new HashMap<>();

    /**
     * 创建事件总线
     */
    @InterfaceView
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

    /**
     * 注册
     * @param eventType 事件类型
     * @param eventListener 事件监听器
     */
    @InterfaceView
    public <T extends Event> void register(EventType<T> eventType, @NonInpLogic EventListener<T> eventListener) {
        register(eventType, EventPriority.NORMAL, eventListener);
    }
    /**
     * 注册
     * @param eventType 事件类型
     * @param priority 事件优先级
     * @param eventListener 事件监听器
     */
    @InterfaceView
    public <T extends Event> void register(EventType<T> eventType, EventPriority priority, @NonInpLogic EventListener<T> eventListener) {
        eventBusCore.register(eventType, priority, eventListener);
        Set<EventListener<?>> set = listeners.computeIfAbsent(eventType, k -> new HashSet<>());
        set.add(eventListener);
    }

    /**
     * 注销
     * @param eventType 事件类型
     * @param eventListener 事件监听器
     */
    @InterfaceView
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
