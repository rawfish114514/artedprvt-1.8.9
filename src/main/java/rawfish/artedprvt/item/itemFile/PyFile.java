package rawfish.artedprvt.item.itemFile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import rawfish.artedprvt.id.KeyWord;

public class PyFile {
    public static void onItemRightClick(World worldIn, EntityPlayer playerIn, String nameIn, String dataIn, String describeIn) {
        class PyThread extends Thread {
            private World world;
            private EntityPlayer player;
            private String name;
            private String data;
            private String describe;
            public PyThread(World worldIn, EntityPlayer playerIn,String nameIn,String dataIn,String describeIn){
                super("Python thread-"+ nameIn);
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
                        succeed[0] = false;
                    }
                });
                playerIn.addChatComponentMessage(new ChatComponentText("\u00a74暂不支持python脚本"));
            }
        }
        PyThread thread=new PyThread(worldIn,playerIn,nameIn,dataIn,describeIn);
        thread.start();
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
