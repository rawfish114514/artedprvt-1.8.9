package com.artedprvt.std.impls.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public final class AsyncWorldRenderer extends WorldRenderer {
    public static final Field vertexCountField;

    static {
        vertexCountField = ReflectionHelper.findField(WorldRenderer.class, "vertexCount", "field_178997_d");
    }

    public static final int PARTICLE_RENDER_VERTEX_SIZE = 28;
    public static final int PARTICLE_RENDER_SIZE = PARTICLE_RENDER_VERTEX_SIZE * 4;
    public ByteBuffer byteBuffer;
    public long address;


    public AsyncWorldRenderer(int bufferSizeIn) {
        super(bufferSizeIn);
        byteBuffer = getByteBuffer();
        try {
            Method addressMethod = byteBuffer.getClass().getDeclaredMethod("address");
            addressMethod.setAccessible(true);
            address = (long) addressMethod.invoke(byteBuffer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WorldRenderer pos(double x, double y, double z) {
        return super.pos(x, y, z);
    }

    public void vertex(int offset,
                       float x, float y, float z,
                       float u, float v,
                       byte r, byte g, byte b, byte a,
                       short b1, short b2) {
        // vertex 12 bytes
        byteBuffer.putFloat(offset, x);
        byteBuffer.putFloat(offset + 4, y);
        byteBuffer.putFloat(offset + 8, z);

        // texture 8 bytes
        byteBuffer.putFloat(offset + 12, u);
        byteBuffer.putFloat(offset + 16, v);

        // color 4 bytes
        byteBuffer.put(offset + 20, r);
        byteBuffer.put(offset + 21, g);
        byteBuffer.put(offset + 22, b);
        byteBuffer.put(offset + 23, a);

        // light 4 bytes
        byteBuffer.putShort(offset + 24, b1);
        byteBuffer.putShort(offset + 26, b2);

        endVertex();
    }

    public void vertex(int offset,
                       float u, float v,
                       byte r, byte g, byte b, byte a,
                       short b1, short b2) {

        // texture 8 bytes
        byteBuffer.putFloat(offset + 12, u);
        byteBuffer.putFloat(offset + 16, v);

        // color 4 bytes
        byteBuffer.put(offset + 20, r);
        byteBuffer.put(offset + 21, g);
        byteBuffer.put(offset + 22, b);
        byteBuffer.put(offset + 23, a);

        // light 4 bytes
        byteBuffer.putShort(offset + 24, b1);
        byteBuffer.putShort(offset + 26, b2);

        endVertex();
    }

    public int getSize() {
        return byteBuffer.array().length;
    }
}
