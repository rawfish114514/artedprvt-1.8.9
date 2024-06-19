package com.artedprvt.std.minecraft.event;

public enum EventPriority {
    HIGHEST(net.minecraftforge.fml.common.eventhandler.EventPriority.HIGHEST),
    HIGH(net.minecraftforge.fml.common.eventhandler.EventPriority.HIGH),
    NORMAL(net.minecraftforge.fml.common.eventhandler.EventPriority.NORMAL),
    LOW(net.minecraftforge.fml.common.eventhandler.EventPriority.LOW),
    LOWEST(net.minecraftforge.fml.common.eventhandler.EventPriority.LOWEST),

    ;

    final net.minecraftforge.fml.common.eventhandler.EventPriority v_eventPriority;

    EventPriority(net.minecraftforge.fml.common.eventhandler.EventPriority v_eventPriority){
        this.v_eventPriority = v_eventPriority;
    }

    public net.minecraftforge.fml.common.eventhandler.EventPriority v_getEventPriority(){
        return v_eventPriority;
    }
}
