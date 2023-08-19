package rawfish.artedprvt.common;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rawfish.artedprvt.core.DebugInfo;

public class EventLoader
{
    public static final EventBus EVENT_BUS = MinecraftForge.EVENT_BUS;
    public EventLoader()
    {
        EVENT_BUS.register(this);
    }

    GuiIngameForge a;
    RenderGameOverlayEvent b;
    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onRenderOverlay(RenderGameOverlayEvent.Text event){
        event.right.addAll(DebugInfo.call(Minecraft.getMinecraft().gameSettings.showDebugInfo));
    }

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onUnload(WorldEvent.Unload event){
        System.out.println("卸载: "+event.world.getProviderName());
    }

    public static void post(Event event){
        EVENT_BUS.post(event);
    }
}