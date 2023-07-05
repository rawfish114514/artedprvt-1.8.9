package rawfish.artedprvt.mi;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Particle extends EntityFX {
    public float aparticleScale;
    public Particle(World worldIn, double posXIn, double posYIn, double posZIn) {
        super(worldIn, posXIn, posYIn, posZIn);
        aparticleScale=particleScale;
    }

    public void setMotion(double x,double y,double z){
        motionX=x;
        motionY=y;
        motionZ=z;
    }

    public void setMaxAge(int n){
        particleMaxAge=n;
    }

    public int getBrightnessForRender(float partialTicks)
    {
        synchronized (Particle.class) {
            float f = ((float) this.particleAge + partialTicks) / (float) this.particleMaxAge;
            f = MathHelper.clamp_float(f, 0.0F, 1.0F);
            int i = super.getBrightnessForRender(partialTicks);
            int j = 240;
            int k = i >> 16 & 255;
            return j | k << 16;
        }
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float partialTicks)
    {
        return 1.0F;
    }

    /**
     * Renders the particle
     */
    public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
        synchronized (Particle.class) {
            float f = ((float) this.particleAge + partialTicks) / (float) this.particleMaxAge;
            this.particleScale = this.aparticleScale * (1.0F - f * f);
            super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        }
    }

    public boolean isInRangeToRenderDist(double distance)
    {
        return true;
    }
}
