package rawfish.artedprvt.common;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class EventLoader
{
    public static final EventBus EVENT_BUS = MinecraftForge.EVENT_BUS;
    public EventLoader()
    {
        EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onLoad(WorldEvent.Load event){

    }

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onUnload(WorldEvent.Unload event){

    }

    public static void post(Event event){
        EVENT_BUS.post(event);
    }
}