package com.artedprvt.std.minecraft.event;

public interface Event {
    net.minecraftforge.fml.common.eventhandler.Event v_getEvent();

    boolean isCanceled();

}
