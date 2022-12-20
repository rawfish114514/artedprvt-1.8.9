package rawfish.artedprvt.item.itemFile;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import rawfish.artedprvt.common.CommonProxy;
import rawfish.artedprvt.id.KeyWord;

import java.util.Date;

public class CmdFile {
    public static void onItemRightClick(World worldIn, EntityPlayer playerIn, String nameIn, String dataIn, String describeIn) {
        class CmdThread extends Thread {
            private World world;
            private EntityPlayer player;
            private String name;
            private String data;
            private String describe;
            public CmdThread(World worldIn, EntityPlayer playerIn,String nameIn,String dataIn,String describeIn){
                super("Command thread-"+ nameIn);
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
                setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        player.addChatComponentMessage(new ChatComponentText("\u00a74" + name + ": " + String.valueOf(e.getMessage())));
                        succeed[0] = false;
                    }
                });
                String[] commands = data.replace(KeyWord.ENTER, "\n").split("\n");
                playerIn.addChatComponentMessage(new ChatComponentText("\u00a77run:\u00a7a " + name));
                long jstime = new Date().getTime();
                for (String commandString : commands) {
                    String[] params = commandString.split("\\s+");
                    if ("@sleep".equals(params[0])) {
                        try {
                            Thread.sleep(Long.parseLong(params[1]));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        ICommand command = CommonProxy.ch.getCommands().get(params[0]);
                        String[] args = new String[params.length - 1];
                        for (int i = 0; i < args.length; i++) {
                            args[i] = params[i + 1];
                        }
                        try {
                            if(command==null){
                                throw new RuntimeException("not command");
                            }
                            command.processCommand(playerIn, args);
                        } catch (CommandException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                playerIn.addChatComponentMessage(new ChatComponentText("\u00a77end:\u00a7a " + name + "\u00a77(" + (new Date().getTime() - jstime) + "ms)"));

            }
        }
        CmdThread thread=new CmdThread(worldIn,playerIn,nameIn,dataIn,describeIn);
        thread.start();
    }
}
