package com.artedprvt.std.minecraft.event.entity.player;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.minecraft.entity.EntityPlayer;
import com.artedprvt.std.minecraft.event.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@InterfaceView
public interface EventPlayerInteract extends Event {
    @InterfaceView
    enum Action {
        @InterfaceView
        RIGHT_CLICK_AIR(PlayerInteractEvent.Action.RIGHT_CLICK_AIR),
        @InterfaceView
        RIGHT_CLICK_BLOCK(PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK),
        @InterfaceView
        LEFT_CLICK_BLOCK(PlayerInteractEvent.Action.LEFT_CLICK_BLOCK),
        ;
        PlayerInteractEvent.Action v_action;

        Action(PlayerInteractEvent.Action v_action) {
            this.v_action = v_action;
        }
    }

    @InterfaceView
    Action getAction();

    @InterfaceView
    EntityPlayer getPlayer();

}
