package rawfish.artedprvt.mi;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EffectSpawn {
    public World world;
    public EffectSpawn(World world){
        this.world=world;
    }

    public Particle particle(double x,double y,double z,int age,int light){
        Particle particle=new Particle(world,x,y,z);
        particle.setMotion(0,0,0);
        particle.setMaxAge(age);
        world.spawnEntityInWorld(particle);

        return particle;
    }
}
