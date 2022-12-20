package rawfish.artedprvt.item.itemFile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;
import rawfish.artedprvt.Artedprvt;
import rawfish.artedprvt.id.KeyWord;
import rawfish.artedprvt.inventory.GuiElementLoader;

import java.util.Date;
import java.util.Locale;

public class ImgFile {
    public static void onItemRightClick(World worldIn, EntityPlayer playerIn, String nameIn, String dataIn, String describeIn) {
        class ImgThread extends Thread{
            private World world;
            private EntityPlayer player;
            private String name;
            private String data;
            private String describe;
            public ImgThread(World worldIn, EntityPlayer playerIn, String nameIn, String dataIn, String describeIn){
                super("Image thread-"+ nameIn);
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
                //创建gui操作图片啊
                BlockPos pos = playerIn.getPosition();
                int id = GuiElementLoader.GUI_IMAGE;
                GuiElementLoader.data=data;
                playerIn.openGui(Artedprvt.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());


            }
        }
        ImgThread thread=new ImgThread(worldIn,playerIn,nameIn,dataIn,describeIn);
        thread.start();
    }
}
