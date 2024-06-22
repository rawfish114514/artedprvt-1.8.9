package com.artedprvt.std.minecraft.event.game;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.minecraft.event.Event;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@InterfaceView
public interface EventTick extends Event {
    @InterfaceView
    enum Type {
        @InterfaceView
        WORLD(TickEvent.Type.WORLD),
        @InterfaceView
        PLAYER(TickEvent.Type.PLAYER),
        @InterfaceView
        CLIENT(TickEvent.Type.CLIENT),
        @InterfaceView
        SERVER(TickEvent.Type.SERVER),
        @InterfaceView
        RENDER(TickEvent.Type.RENDER),
        ;

        TickEvent.Type v_type;

        Type(TickEvent.Type v_type) {
            this.v_type = v_type;
        }
    }

    @InterfaceView
    enum Phase {
        @InterfaceView
        START(TickEvent.Phase.START),
        @InterfaceView
        END(TickEvent.Phase.END),
        ;

        TickEvent.Phase v_phase;

        Phase(TickEvent.Phase v_phase) {
            this.v_phase = v_phase;
        }
    }

    Type getType();

    Phase getPhase();
}
