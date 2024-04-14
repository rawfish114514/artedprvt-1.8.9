package com.artedprvt.std.impls.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 不更改原版粒子的能力
 * 修复原版粒子一堆bug
 * 扩展新的粒子系统
 * <p>
 * 这个粒子系统因为需要高性能和其他功能脱离原版实体
 */
public class ParticleSystem {
    public static void init() {
        MinecraftForge.EVENT_BUS.register(new ParticleSystem());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLoad(WorldEvent.Load load) {
        World world = load.world;
        EffectRenderer effectRenderer = Minecraft.getMinecraft().effectRenderer;
        List<EntityFX>[][] fxLayers;

        try {
            Field field = ReflectionHelper.findField(EffectRenderer.class, "fxLayers", "field_78876_b");
            field.setAccessible(true);
            Object object = field.get(effectRenderer);
            fxLayers = (List<EntityFX>[][]) object;

            for (int i = 0; i < fxLayers.length; i++) {
                for (int j = 0; j < fxLayers[i].length; j++) {
                    if (fxLayers[i][j].getClass() != NullableList.class) {
                        NullableList nullableList = new NullableList(world, fxLayers[i][j]);
                        fxLayers[i][j] = nullableList;
                    }
                }
            }


            AsyncEffectRender asyncEffectRender = new AsyncEffectRender(
                    Minecraft.getMinecraft().theWorld, Minecraft.getMinecraft().renderEngine
            );
            asyncEffectRender.fxLayers = fxLayers;
            field.set(asyncEffectRender, fxLayers);
            Minecraft.getMinecraft().effectRenderer = asyncEffectRender;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onUnload(WorldEvent.Unload unload) {
        EffectRenderer effectRenderer = Minecraft.getMinecraft().effectRenderer;
        if (effectRenderer instanceof AsyncEffectRender) {
            AsyncEffectRender asyncEffectRender = (AsyncEffectRender) effectRenderer;
            asyncEffectRender.clear();
        }
    }
}
