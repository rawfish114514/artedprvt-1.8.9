package com.artedprvt.std.minecraft.event.game;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.minecraft.event.Event;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@InterfaceView
public interface EventClientTick extends Event {
    TickEvent.ClientTickEvent v_getEvent();
}
