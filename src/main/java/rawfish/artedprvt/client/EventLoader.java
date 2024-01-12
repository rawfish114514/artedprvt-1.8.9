package rawfish.artedprvt.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventLoader {
    public static final EventBus EVENT_BUS = MinecraftForge.EVENT_BUS;

    public EventLoader() {
        EVENT_BUS.register(this);
    }

    GuiIngameForge a;
    RenderGameOverlayEvent b;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        if (event.left != null && event.left.size() > 0 && event.left.get(0) != null) {
            event.left.set(0, event.left.get(0) + DebugInfo.call(Minecraft.getMinecraft().gameSettings.showDebugInfo));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onGuiOpen(GuiOpenEvent event) {
        if (event == null) return;
        if (event.gui == null) return;
        if (event.gui instanceof GuiChat && !(event.gui instanceof GuiSleepMP)) {
            event.gui = new CommandLiteralGuiChat((GuiChat) event.gui);
        }
        if (event.gui instanceof GuiCommandBlock) {
            event.gui = new CommandLiteralGuiCommandBlock((GuiCommandBlock) event.gui);
        }
    }
}
