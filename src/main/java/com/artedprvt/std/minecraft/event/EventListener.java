package com.artedprvt.std.minecraft.event;

public interface EventListener<T extends Event> {
    void onEvent(T event);
}
