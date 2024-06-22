package com.artedprvt.std.impls.minecraft.event.game;

import com.artedprvt.std.impls.minecraft.event.VanillaProxyBaseEvent;
import com.artedprvt.std.minecraft.event.game.EventTick;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class VanillaProxyEventTick extends VanillaProxyBaseEvent<TickEvent> implements EventTick {
    public VanillaProxyEventTick(TickEvent v_event) {
        super(v_event);
    }

    @Override
    public Type getType() {
        return Type.values()[v_event.type.ordinal()];
    }

    @Override
    public Phase getPhase() {
        return Phase.values()[v_event.phase.ordinal()];
    }
}
