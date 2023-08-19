package rawfish.artedprvt.mi;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class EffectSpawn {
    public World world;
    public EffectSpawn(World world){
        this.world=world;
    }

    public EffectSpawn(){
        world=null;
    }

    public Particle particle(double x,double y,double z,int age){
        Particle particle=new Particle(world,x,y,z);
        particle.setMaxAge(age);
        particle.setSpeed(0,0,0);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
        return particle;
    }
}
