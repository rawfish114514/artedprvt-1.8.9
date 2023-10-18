package rawfish.artedprvt.mi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EffectSpawn {
    public World world;
    public EffectRenderer effectRenderer=Minecraft.getMinecraft().effectRenderer;
    public List<EntityFX>[][] fxLayers;
    public EffectSpawn(World world){
        this.world=world;
        Field field=null;
        try {
            field=effectRenderer.getClass().getDeclaredField("field_78876_b");
        } catch (Exception ignored) {
        }
        try {
            if(field==null) {
                field = effectRenderer.getClass().getDeclaredField("fxLayers");
            }
        } catch (Exception ignored) {
        }
        if(field==null){
            throw new RuntimeException("null");
        }
        field.setAccessible(true);
        Object object= null;
        try {
            object = field.get(effectRenderer);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        fxLayers=(List<EntityFX>[][])object;
        for(int i=0;i<fxLayers.length;i++){
            for(int j=0;j<fxLayers[i].length;j++){
                if(fxLayers[i][j].getClass()!=NullableList.class) {
                    NullableList nullableList = new NullableList(world);
                    fxLayers[i][j]=nullableList;
                }
            }
        }
    }

    public EffectSpawn(){
        world=null;
    }

    public Particle particle(double x,double y,double z,int age){
        synchronized (EffectSpawn.class) {
            Particle particle = new Particle(world, x, y, z);
            particle.setMaxAge(age);
            particle.setSpeed(0, 0, 0);
            fxLayers[0][1].add(particle);
            return particle;
        }
    }

    public static class NullableList extends ArrayList<EntityFX>{
        public World world;
        public NullableList(World world,Collection<? extends EntityFX> c){
            super(c);
            this.world=world;
        }

        public NullableList(World world){
            this.world=world;
        }

        public EntityFX get(int index){
            EntityFX entityFX=super.get(index);
            if(entityFX==null){
                return new NullParticle(world,0,0,0);
            }
            return entityFX;
        }
    }

    public static class NullParticle extends Particle{
        public NullParticle(World worldIn, double posXIn, double posYIn, double posZIn) {
            super(worldIn, posXIn, posYIn, posZIn);
        }
        public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
        {}
    }
}
