package com.artedprvt.std.minecraft.event;

import com.artedprvt.core.NonInpLogic;
import com.artedprvt.iv.anno.InterfaceView;

@InterfaceView
public interface EventListener<T extends Event> {
    @NonInpLogic
    @InterfaceView
    void onEvent(T event);
}
