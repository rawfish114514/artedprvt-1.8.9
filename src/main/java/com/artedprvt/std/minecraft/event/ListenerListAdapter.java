package com.artedprvt.std.minecraft.event;

import com.artedprvt.std.impls.minecraft.event.VanillaProxyEvent;
import com.artedprvt.std.minecraft.event.game.EventClientTick;
import net.minecraftforge.fml.common.eventhandler.IEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 监听器列表适配器
 * 对于每个原版事件总线的每个事件类型的每个优先级都有一个
 * 分离类型映射的逻辑并且保证了每个事件只创建一个包装
 * 此对象可直接注册到原版事件总线
 *
 * @param <T>
 */
public class ListenerListAdapter<T extends Event> implements IEventListener {
    public List<EventListener<T>> listeners;
    public EventType<T> eventType;

    public ListenerListAdapter(EventType<T> eventType) {
        listeners = new ArrayList<EventListener<T>>();
        this.eventType = eventType;
    }

    @Override
    public void invoke(net.minecraftforge.fml.common.eventhandler.Event v_event) {
        if (eventType.v_eventClass.isAssignableFrom(v_event.getClass())) {
            T event = eventType.newInstance(v_event);
            for (EventListener<T> listener : listeners) {
                listener.onEvent(event);
                if (event.isCanceled()) {
                    break;
                }
            }
        }
    }
}
