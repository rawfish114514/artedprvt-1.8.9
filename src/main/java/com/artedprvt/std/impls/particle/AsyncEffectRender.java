package com.artedprvt.std.impls.particle;

import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 扩展原版粒子控制器
 * 原版处理能力极其有限
 */
public final class AsyncEffectRender extends EffectRenderer {
    public AsyncEffectRender(World worldIn, TextureManager rendererIn) {
        super(worldIn, rendererIn);

        this.renderer = rendererIn;

        init(256);
        updateTP = (ThreadPoolExecutor) Executors.newFixedThreadPool(32);
        renderTP = (ThreadPoolExecutor) Executors.newFixedThreadPool(256);
    }

    /**
     * @param capacity 非合并粒子列表容量
     */
    void init(int capacity) {
        particleListNameList = new ArrayList<>();
        uParticleListNameList = new ArrayList<>();
        this.capacity = capacity;

        fx01WorldRenderer = new WorldRenderer(1 << 25);
        worldRendererList = new ArrayList<>();
        int bufferSize = AsyncWorldRenderer.PARTICLE_RENDER_SIZE * capacity;
        for (int i = 0; i < 4096; i++) {
            worldRendererList.add(new AsyncWorldRenderer(bufferSize));
        }
    }

    public List<EntityFX>[][] fxLayers;

    ThreadPoolExecutor updateTP;
    ThreadPoolExecutor renderTP;


    int capacity;
    List<String> particleListNameList;
    List<String> uParticleListNameList;

    final Object pll = new Object();

    WorldRenderer fx01WorldRenderer;
    List<AsyncWorldRenderer> worldRendererList;


    UnionListView<EntityFX, VanillaControlParticle> unionListView = null;


    public List<VanillaControlParticle> getParticleList() {
        synchronized (pll) {
            List<VanillaControlParticle> particleList = null;
            for (int i = 0; i < particleListNameList.size(); i++) {
                particleList = unionListView.get(particleListNameList.get(i));
                if (particleList.size() < capacity) {
                    return particleList;
                }
            }
            particleList = new CopyOnWriteArrayList<VanillaControlParticle>();
            String name = "p" + Math.random();
            unionListView.put(name, particleList);
            particleListNameList.add(name);
            return particleList;
        }
    }

    public void addParticle(VanillaControlParticle particle) {
        getParticleList().add(particle);
    }

    public void addParticles(List<VanillaControlParticle> particleList) {
        for (int i = 0; i < particleList.size(); i += capacity) {
            int endIndex = Math.min(i + capacity, particleList.size());
            List<VanillaControlParticle> list = particleList.subList(i, endIndex);
            String name = "up" + Math.random();
            unionListView.put(name, new CopyOnWriteArrayList<VanillaControlParticle>(list));
            uParticleListNameList.add(name);
        }
    }

    @Override
    public void addEffect(EntityFX effect) {
        if (effect == null) return; //Forge: Prevent modders from being bad and adding nulls causing untraceable NPEs.
        int i = effect.getFXLayer();
        int j = effect.getAlpha() != 1.0F ? 0 : 1;

        this.fxLayers[i][j].add(effect);
    }

    @Override
    public void updateEffects() {
        updateTP.purge();

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 2; ++j) {
                updateTP.submit(new EntityFXUpdateTask(fxLayers[i][j]));
            }
        }

        for (int i = 0; i < particleListNameList.size(); i++) {
            String name = particleListNameList.get(i);
            updateTP.submit(new DefaultUpdateTask(unionListView.get(name)));
        }
        for (int i = 0; i < uParticleListNameList.size(); i++) {
            String name = uParticleListNameList.get(i);
            updateTP.submit(new UnionUpdateTask(unionListView.get(name)));
        }

        synchronized (pll) {
            List<String> removeList = new ArrayList<>();

            for (int i = 0; i < particleListNameList.size(); i++) {
                if (unionListView.get(particleListNameList.get(i)).isEmpty()) {
                    removeList.add(particleListNameList.get(i));
                }
            }

            for (String name : removeList) {
                particleListNameList.remove(name);
                unionListView.remove(name);
            }

            for (int i = 0; i < uParticleListNameList.size(); i++) {
                if (unionListView.get(uParticleListNameList.get(i)).isEmpty()) {
                    removeList.add(uParticleListNameList.get(i));
                }
            }

            for (String name : removeList) {
                uParticleListNameList.remove(name);
                unionListView.remove(name);
            }
        }
    }

    private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
    public TextureManager renderer;


    public void bindTexture() {
        bindTexture(0, 1);
    }

    public void bindTexture(int i, int j) {
        switch (j) {
            case 0:
                GlStateManager.depthMask(false);
                break;
            case 1:
                GlStateManager.depthMask(true);
        }

        switch (i) {
            case 0:
            default:
                this.renderer.bindTexture(particleTextures);
                break;
            case 1:
                this.renderer.bindTexture(TextureMap.locationBlocksTexture);
        }
    }


    @Override
    public void renderParticles(Entity entityIn, float partialTicks) {
        renderTP.purge();

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        float f = ActiveRenderInfo.getRotationX();
        float f1 = ActiveRenderInfo.getRotationZ();
        float f2 = ActiveRenderInfo.getRotationYZ();
        float f3 = ActiveRenderInfo.getRotationXY();
        float f4 = ActiveRenderInfo.getRotationXZ();
        EntityFX.interpPosX = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double) partialTicks;
        EntityFX.interpPosY = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double) partialTicks;
        EntityFX.interpPosZ = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double) partialTicks;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.alphaFunc(516, 0.003921569F);

        worldRenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);


        for (int i = 0; i < fxLayers.length; i++) {
            for (int j = 0; j < fxLayers[i].length; j++) {
                if (i == 0 && j == 1) {
                    continue;
                }
                if (fxLayers[i][j].isEmpty()) {
                    continue;
                }
                bindTexture(i, j);
                new EntityFXRenderTask(fxLayers[i][j],
                        worldRenderer, entityIn,
                        partialTicks, f, f4, f1, f2, f3).call();
            }
        }

        tessellator.draw();

        bindTexture();
        List<Future<WorldRenderer>> futureList = new ArrayList<>();

        fx01WorldRenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

        futureList.add(renderTP.submit(
                new EntityFXRenderTask(new ArrayList<>(unionListView),
                        fx01WorldRenderer, entityIn,
                        partialTicks, f, f4, f1, f2, f3)));

        synchronized (pll) {
            int a = 0;
            a = submitRenderTask(entityIn, partialTicks, f, f1, f2, f3, f4, a, futureList, particleListNameList);
            submitUnionRenderTask(entityIn, partialTicks, f, f1, f2, f3, f4, a, futureList, uParticleListNameList);
        }


        try {
            WorldRenderer currentWorldRenderer;
            for (Future<WorldRenderer> future : futureList) {
                currentWorldRenderer = future.get();
                currentWorldRenderer.finishDrawing();
                draw(currentWorldRenderer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1F);
    }

    public int submitRenderTask(
            Entity entityIn, float partialTicks,
            float f, float f1, float f2, float f3, float f4,
            int a, List<Future<WorldRenderer>> futureList, List<String> particleListNameList) {
        for (int i = 0; i < particleListNameList.size(); i++) {
            if (worldRendererList.size() > a) {
                AsyncWorldRenderer asyncWorldRenderer = worldRendererList.get(a++);
                asyncWorldRenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

                List<VanillaControlParticle> particleList = unionListView.get(particleListNameList.get(i));
                VanillaControlParticle[] particles = new VanillaControlParticle[particleList.size()];
                particleList.toArray(particles);
                futureList.add(renderTP.submit(
                        new RenderTask(particles,
                                asyncWorldRenderer, entityIn,
                                partialTicks, f, f4, f1, f2, f3)));
            } else {
                System.out.println("wRenderer不足");
            }
        }
        return a;
    }

    public int submitUnionRenderTask(
            Entity entityIn, float partialTicks,
            float f, float f1, float f2, float f3, float f4,
            int a, List<Future<WorldRenderer>> futureList, List<String> particleListNameList) {
        for (int i = 0; i < particleListNameList.size(); i++) {
            if (worldRendererList.size() > a) {
                AsyncWorldRenderer asyncWorldRenderer = worldRendererList.get(a++);
                asyncWorldRenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

                List<VanillaControlParticle> particleList = unionListView.get(particleListNameList.get(i));
                futureList.add(renderTP.submit(
                        new UnionRenderTask(particleList,
                                asyncWorldRenderer, entityIn,
                                partialTicks, f, f4, f1, f2, f3)));
            } else {
                System.out.println("wRenderer不足");
            }
        }
        return a;
    }

    public void draw(WorldRenderer worldRenderer) {
        if (worldRenderer.getVertexCount() > 0) {
            VertexFormat vertexformat = worldRenderer.getVertexFormat();
            int i = vertexformat.getNextOffset();
            ByteBuffer bytebuffer = worldRenderer.getByteBuffer();
            List<VertexFormatElement> list = vertexformat.getElements();

            for (int j = 0; j < list.size(); ++j) {
                // moved to VertexFormatElement.preDraw
                VertexFormatElement vertexformatelement = (VertexFormatElement) list.get(j);
                vertexformatelement.getUsage().preDraw(vertexformat, j, i, bytebuffer);
            }

            GL11.glDrawArrays(worldRenderer.getDrawMode(), 0, worldRenderer.getVertexCount());
            int i1 = 0;

            for (int j1 = list.size(); i1 < j1; ++i1) {
                VertexFormatElement vertexformatelement1 = (VertexFormatElement) list.get(i1);
                // moved to VertexFormatElement.postDraw
                vertexformatelement1.getUsage().postDraw(vertexformat, i1, i, bytebuffer);
            }


            worldRenderer.reset();
        }
    }

    @Override
    public String getStatistics() {
        //printActiveCount();
        int i = unionListView.sumSize();

        for (int j = 0; j < 4; ++j) {
            for (int k = 0; k < 2; ++k) {
                if (i == 0 && k == 1) {
                    continue;
                }
                i += this.fxLayers[j][k].size();
            }
        }

        return "" + i;
    }
}
