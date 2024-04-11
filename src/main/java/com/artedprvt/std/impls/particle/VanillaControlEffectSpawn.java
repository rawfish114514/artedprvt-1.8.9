package com.artedprvt.std.impls.particle;

import com.artedprvt.std.minecraft.world.World;
import com.artedprvt.std.particle.EffectSpawn;
import com.artedprvt.std.particle.Particle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class VanillaControlEffectSpawn implements EffectSpawn {
    static {
        try {
            // 扩展某个缓冲区
            Tessellator.class.getClass();

            Field instanceField = ReflectionHelper.findField(Tessellator.class, "instance", "field_78398_a");
            instanceField.setAccessible(true);
            Tessellator instance = (Tessellator) instanceField.get(Tessellator.class);

            Field worldRendererField = ReflectionHelper.findField(Tessellator.class, "worldRenderer", "field_178183_a");
            worldRendererField.setAccessible(true);

            WorldRenderer worldRenderer = new WorldRenderer(1 << 27);
            worldRendererField.set(instance, worldRenderer);

            /*
            Field byteBufferField = ReflectionHelper.findField(WorldRenderer.class, "byteBuffer", "field_179001_a");
            byteBufferField.setAccessible(true);
            Field rawIntBufferField = ReflectionHelper.findField(WorldRenderer.class, "rawIntBuffer", "field_178999_b");
            rawIntBufferField.setAccessible(true);
            Field rawShortBufferField = ReflectionHelper.findField(WorldRenderer.class, "rawShortBuffer", "field_181676_c");
            rawShortBufferField.setAccessible(true);
            Field rawFloatBufferField = ReflectionHelper.findField(WorldRenderer.class, "rawFloatBuffer", "field_179000_c");
            rawFloatBufferField.setAccessible(true);

            // vanilla size 2^21*4
            // current size 2^25*4
            ByteBuffer byteBuffer = GLAllocation.createDirectByteBuffer(1 << 27);
            IntBuffer rawIntBuffer = byteBuffer.asIntBuffer();
            ShortBuffer rawShortBuffer = byteBuffer.asShortBuffer();
            FloatBuffer rawFloatBuffer = byteBuffer.asFloatBuffer();

            byteBufferField.set(worldRenderer, byteBuffer);
            rawIntBufferField.set(worldRenderer, rawIntBuffer);
            rawShortBufferField.set(worldRenderer, rawShortBuffer);
            rawFloatBufferField.set(worldRenderer, rawFloatBuffer);

             */
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public net.minecraft.world.World v_world;
    public EffectRenderer effectRenderer = Minecraft.getMinecraft().effectRenderer;
    public List<EntityFX>[][] fxLayers;

    public AsyncEffectRender asyncEffectRender;

    public VanillaControlEffectSpawn(net.minecraft.world.World v_world) {
        v_setWorld(v_world);
    }

    public VanillaControlEffectSpawn() {

    }

    public void v_setWorld(net.minecraft.world.World v_world) {
        this.v_world = v_world;
        try {
            Field field = ReflectionHelper.findField(EffectRenderer.class, "fxLayers", "field_78876_b");
            field.setAccessible(true);
            Object object = field.get(effectRenderer);
            fxLayers = (List<EntityFX>[][]) object;

            for (int i = 0; i < fxLayers.length; i++) {
                for (int j = 0; j < fxLayers[i].length; j++) {
                    if (fxLayers[i][j].getClass() != NullableList.class) {
                        NullableList nullableList = new NullableList(v_world, fxLayers[i][j]);
                        fxLayers[i][j] = nullableList;
                    }
                }
            }

            UnionListView<EntityFX,VanillaControlParticle> unionListView = new UnionListView<>(
                    fxLayers[0][1], new NullVanillaControlParticle(v_world, 0, 0, 0));
            fxLayers[0][1] = unionListView;


            if (effectRenderer.getClass() != AsyncEffectRender.class) {
                AsyncEffectRender asyncEffectRender = new AsyncEffectRender(
                        Minecraft.getMinecraft().theWorld, Minecraft.getMinecraft().renderEngine
                );
                asyncEffectRender.unionListView = unionListView;
                asyncEffectRender.fxLayers = fxLayers;
                field.set(asyncEffectRender, fxLayers);
                Minecraft.getMinecraft().effectRenderer = asyncEffectRender;
                effectRenderer = asyncEffectRender;

                this.asyncEffectRender = asyncEffectRender;
            } else {
                asyncEffectRender = (AsyncEffectRender) effectRenderer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setWorld(World world) {
        v_setWorld(world.v_getWorld());
    }

    @Override
    public VanillaControlParticle createParticle(double x, double y, double z, int age) {
        synchronized (VanillaControlEffectSpawn.class) {
            VanillaControlParticle particle = new VanillaControlParticle(v_world, x, y, z);
            particle.setMaxAge(age);
            particle.setSpeed(0, 0, 0);
            return particle;
        }
    }

    @Override
    public void spawnParticle(Particle particle) {
        asyncEffectRender.addParticle((VanillaControlParticle) particle);
    }

    @Override
    public void spawnParticles(List<Particle> particles) {
        asyncEffectRender.addParticles(particles.stream().<VanillaControlParticle>map(p->(VanillaControlParticle)p).collect(Collectors.toList()));
    }
    
}
