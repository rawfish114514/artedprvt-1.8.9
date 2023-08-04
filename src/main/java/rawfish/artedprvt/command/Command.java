package rawfish.artedprvt.command;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Command extends CommandBase {
    public abstract void process(List<String> args);
    public abstract List<String> complete(List<String> args);


    public String commandName;

    public Command(String commandName){
        this.commandName=commandName;
    }
    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands."+commandName+".usage";
    }

    public static final List<String> nullTab=new ArrayList<>();

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        //去空参数
        List<String> slist=new ArrayList<>();
        for(String arg:args){
            if(!arg.equals("")){
                slist.add(arg);
            }
        }
        process(slist);
    }

    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos){
        if(!(sender.getEntityWorld() instanceof WorldServer)){
            return complete(Arrays.asList(args));
        }
        return nullTab;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
