package com.artedprvt.std.impls.minecraft.event.game;

import com.artedprvt.std.impls.minecraft.event.VanillaProxyBaseEvent;
import com.artedprvt.std.minecraft.event.game.EventClientTick;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class VanillaProxyEventClientTick extends VanillaProxyBaseEvent<TickEvent.ClientTickEvent> implements EventClientTick {
    public VanillaProxyEventClientTick(TickEvent.ClientTickEvent v_event) {
        super(v_event);
    }

    public TickEvent.ClientTickEvent v_getClientTickEvent() {
        return v_event;
    }
}
