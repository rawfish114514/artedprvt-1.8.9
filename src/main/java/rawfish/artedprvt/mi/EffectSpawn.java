package rawfish.artedprvt.mi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import scala.annotation.meta.field;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
}
