package rawfish.artedprvt.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import rawfish.artedprvt.core.DebugInfo;
import rawfish.artedprvt.core.ScriptProcess;
import rawfish.artedprvt.core.ScriptSystem;
import rawfish.artedprvt.event.InputStringEvent;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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