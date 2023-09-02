package rawfish.artedprvt.mi;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Particle extends EntityFX {
    public int tick=0;//计数器
    public int d0=0;//额外4字节数据

    public double accelerationX=0;
    public double accelerationY=0;
    public double accelerationZ=0;

    public ParticleUpdate particleUpdate=null;

    public int[] ints;//额外可选数量的整数数据
    public double[] doubles;//额外可选数量的小数数据

    public Particle(World worldIn, double posXIn, double posYIn, double posZIn) {
        super(worldIn, posXIn, posYIn, posZIn);
    }

    public void setColor(float r,float g,float b){
        particleRed=r;
        particleGreen=g;
        particleBlue=b;
    }

    public void setScale(float s){
        particleScale=s;
    }

    public void setMaxAge(int n){
        particleMaxAge=n;
    }

    public int getBrightnessForRender(float partialTicks)
    {
            float f = ((float) this.particleAge + partialTicks) / (float) this.particleMaxAge;
            f = MathHelper.clamp_float(f, 0.0F, 1.0F);
            int i = super.getBrightnessForRender(partialTicks);
            int j = 240;
            int k = i >> 16 & 255;
            return j | k << 16;

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
            //float f = ((float) this.particleAge + partialTicks) / (float) this.particleMaxAge;
            //this.particleScale = this.aparticleScale * (1.0F - f * f);
            super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);

    }

    public void onUpdate() {
        synchronized (Particle.class) {
            if (this.particleAge++ >= this.particleMaxAge) {
                this.setDead();
            }
            //this.moveEntity(this.motionX, this.motionY, this.motionZ);
        }
    }

    public boolean isInRangeToRenderDist(double distance)
    {
        return true;
    }

    public float[] getColor(){
        return new float[]{particleRed,particleGreen,particleBlue};
    }



    public void setLastPosition(double x,double y,double z){
        lastTickPosX=x;
        lastTickPosY=y;
        lastTickPosZ=z;
        prevPosX=x;
        prevPosY=y;
        prevPosZ=z;
    }

    public void setParticleUpdate(ParticleUpdate particleUpdate){
        this.particleUpdate=particleUpdate;
    }

    public int tick(){
        return tick++;
    }

    public void setD(int d){
        d0=d;
    }

    public int getD(){
        return d0;
    }

    public int getAge(){
        return particleAge;
    }

    //运动学


    //设置位置
    public void setPos(double x,double y,double z){
        setLastPosition(posX,posY,posZ);
        posX=x;
        posY=y;
        posZ=z;
    }

    //设置速度
    public void setSpeed(double x,double y,double z){
        motionX=x;
        motionY=y;
        motionZ=z;
    }

    //设置加速度
    public void setAccel(double x,double y,double z){
        accelerationX=x;
        accelerationY=y;
        accelerationZ=z;
    }

    public double[] getPos(){
        return new double[]{posX,posY,posZ};
    }

    public double[] getSpeed(){
        return new double[]{motionX,motionY,motionZ};
    }

    public double[] getAccel(){
        return new double[]{accelerationX,accelerationY,accelerationZ};
    }

    public void motionUpdate(){
        if(particleUpdate!=null){
            particleUpdate.update(this);
        }
        setPos(posX+motionX,posY+motionY,posZ+motionZ);
        setSpeed(motionX+accelerationX,motionY+accelerationY,motionZ+accelerationZ);
    }

    public void initInts(int length){
        ints=new int[length];
    }

    public void initDoubles(int length){
        doubles=new double[length];
    }

    public int getIntsLength(){
        return ints.length;
    }

    public int getDoublesLength(){
        return doubles.length;
    }

    public void setInt(int index,int value){
        ints[index]=value;
    }

    public void setDouble(int index,double value){
        doubles[index]=value;
    }

    public int getInt(int index){
        return ints[index];
    }

    public double getDouble(int index){
        return doubles[index];
    }

}
