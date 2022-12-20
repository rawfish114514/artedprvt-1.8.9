package rawfish.artedprvt.item.itemFile;


import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;
import rawfish.artedprvt.common.CommonProxy;
import rawfish.artedprvt.id.KeyWord;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JsFile {
    public static void onItemRightClick(World worldIn, EntityPlayer playerIn,String nameIn,String dataIn,String describeIn) {
        class JsThread extends Thread{
            private World world;
            private EntityPlayer player;
            private String name;
            private String data;
            private String describe;
            public JsThread(World worldIn, EntityPlayer playerIn, String nameIn, String dataIn, String describeIn){
                super("JavaScript thread-"+ nameIn);
                world=worldIn;
                player=playerIn;
                name=nameIn;
                data=dataIn;
                describe=describeIn;

                if(KeyWord.ISNO.equals(data)){
                    data="";
                }
            }
            @Override
            public void run() {
                final boolean[] succeed = {true};
                setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        player.addChatComponentMessage(new ChatComponentText("\u00a74" + name + ": " + String.valueOf(e.getMessage())));
                        succeed[0] =false;
                    }
                });
                Context rhino = Context.enter();
                rhino.setOptimizationLevel(-1);
                rhino.setLocale(Locale.ENGLISH);
                long jstime = 0;
                try {
                    playerIn.addChatComponentMessage(new ChatComponentText("\u00a77run:\u00a7a " + name));
                    jstime = new Date().getTime();

                    //初始化作用域
                    ScriptableObject scope = rhino.initStandardObjects();
                    //执行js
                    PrintStream object_p=new PrintStream(playerIn, name);
                    PlayerContext object_c=new PlayerContext(worldIn,playerIn);
                    scope.put("_var114514", scope, object_p);
                    scope.put("Script", scope, object_c);
                    rhino.evaluateString(scope, "print=function(object){_var114514.print(object)}", "define_print", 1, null);
                    rhino.evaluateString(scope, data.replace(KeyWord.ENTER,"\n").replace(KeyWord.QUOTE,"\""), name, 1, null);

                } finally {
                    //释放资源
                    Context.exit();
                    playerIn.addChatComponentMessage(new ChatComponentText("\u00a77end:\u00a7a " + name + "\u00a77(" + (new Date().getTime() - jstime) + "ms)"));
                }
            }
        }
        JsThread thread=new JsThread(worldIn,playerIn,nameIn,dataIn,describeIn);
        thread.start();
    }



    /**
     * 提供一些操作
     */
    public static class PlayerContext {
        private final World world;
        private final EntityPlayer player;

        public PlayerContext(World worldIn, EntityPlayer playerIn){
            this.world = worldIn;
            this.player = playerIn;
        }

        //创建Pos对象
        public Pos creatPos(double x,double y,double z){
            return new Pos(x,y,z);
        }

        //获取玩家坐标
        public Pos getPos(){
            return new Pos(player.posX,player.posY,player.posZ);
        }

        //获取玩家名称
        public String getName(){
            return player.getName();
        }

        //播放声音
        public void playSound(Pos pos,String sound,float pitchParam){
            float f = (float)Math.pow(2.0D, (double)(pitchParam - 12) / 12.0D);
            world.playSound(pos.x + 0.5D, pos.y + 0.5D, pos.z + 0.5D, sound, 3.0F, f,false);
        }

        //粒子
        public void spawnParticle(Pos pos,String particle,int[] off){
            world.spawnParticle(EnumParticleTypes.valueOf(particle),pos.x,pos.y,pos.z,0,0,0);
        }

        //获取玩家渲染
        public Render getRender(){
            return Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(player);
        }

        //获取玩家聊天
        public List<String>  getMessages(){
            return Minecraft.getMinecraft().ingameGUI.getChatGUI().getSentMessages();
        }

        //运行命令
        public void runCommand(String commandS,String[] args){
            ICommand command = CommonProxy.ch.getCommands().get(commandS);
            try {
                if(command==null){
                    throw new RuntimeException("not command");
                }
                command.processCommand(player, args);
            } catch (CommandException e) {
                throw new RuntimeException(e);
            }
        }

        //设置方块
        public void setBlock(Pos pos,String block){
            world.setBlockState(new BlockPos((int)Math.floor(pos.x),(int)Math.floor(pos.y),(int)Math.floor(pos.z)),Block.getBlockFromName(block).getDefaultState());
        }

        //获取Shape对象
        public Shape creatShape(){
            return new Shape(this);
        }

        public static class Pos{
            public final double x;
            public final double y;
            public final double z;

            public Pos(double x, double y, double z){

                this.x = x;
                this.y = y;
                this.z = z;
            }
            public Pos addV(double lx,double ly,double lz){
                return new Pos(x+lx,y+ly,z+lz);
            }
            public Pos floor(){
                return new Pos(Math.floor(x),Math.floor(y),Math.floor(z));
            }
            @Override
            public String toString() {
                return String.format("(%s,%s,%s)", x,y,z);
            }
        }

        public static class Shape{
            private PlayerContext script;
            public Shape(PlayerContext scriptIn){
                script=scriptIn;
            }

            public void drawUnit(Pos pos,String block){
                script.setBlock(pos,block);
            }

            public void drawCircle(Pos pos,String block,int length){
                int x=0;
                int y=length;
                int p=3-2*length;
                for(;x<=y;x++){
                    drawCircle1(pos,x,0,y,block);
                    if(p>=0){
                        p+=4*(x-y)+10;
                        y--;
                    }else{
                        p+=4*x+6;
                    }
                };
            }
            public void drawCircle0(Pos pos,int x,int y,int z,String block){
                script.setBlock(pos.floor().addV(x,y,z),block);
            }
            public void drawCircle1(Pos pos,int x,int y,int z,String block){
                drawCircle0(pos,x,y,z,block);
                drawCircle0(pos,z,y,x,block);
                drawCircle0(pos,z,y,-x,block);
                drawCircle0(pos,x,y,-z,block);
                drawCircle0(pos,-x,y,-z,block);
                drawCircle0(pos,-z,y,-x,block);
                drawCircle0(pos,-z,y,x,block);
                drawCircle0(pos,-x,y,z,block);
            }
        }
    }


    /**
     * 提供打印方法
     */
    public static class PrintStream {
        public EntityPlayer player;
        public String name;
        public PrintStream(EntityPlayer playerIn,String nameIn){
            player=playerIn;
            name=nameIn;
        }
        public void print(Object object){
            player.addChatComponentMessage(new ChatComponentText("\u00a77"+name+":\u00a7r "+String.valueOf(object)));
        }
    }

}
