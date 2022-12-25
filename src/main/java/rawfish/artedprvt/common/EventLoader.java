package rawfish.artedprvt.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import rawfish.artedprvt.item.itemFile.PlayerFileSpace;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class EventLoader
{
    public EventLoader()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }


    public static int chat=1;
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (event.world.isRemote)
        {
            ItemStack item=event.entityPlayer.getHeldItem();
            if(!(item ==null)){
                if(chat==0) {
                    event.entityPlayer.addChatComponentMessage(new ChatComponentText(String.valueOf(item.getTagCompound())));
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event){
        if(!event.world.isRemote) {
            Entity entity = event.entity;
            if (entity instanceof EntityPlayer) {
                onPlayerJoinWorld((EntityPlayer) entity);
            }
        }
    }

    public void onPlayerJoinWorld(EntityPlayer entity){
        //创建玩家文件空间
        PlayerFileSpace.set(new PlayerFileSpace(entity));
    }

}