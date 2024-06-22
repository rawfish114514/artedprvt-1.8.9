package com.artedprvt.std.impls.minecraft.event.entity.player;

import com.artedprvt.std.impls.minecraft.event.VanillaProxyBaseEvent;
import com.artedprvt.std.minecraft.event.entity.player.EventPlayerInteract;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class VanillaProxyEventPlayerInteract extends VanillaProxyBaseEvent<PlayerInteractEvent> implements EventPlayerInteract {
    public VanillaProxyEventPlayerInteract(PlayerInteractEvent v_event) {
        super(v_event);
    }

    @Override
    public Action getAction() {
        return Action.values()[v_event.action.ordinal()];
    }
}
