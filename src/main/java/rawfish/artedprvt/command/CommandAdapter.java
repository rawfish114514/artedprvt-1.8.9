package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandAdapter extends CommandBase {
    private final Command command;
    public CommandAdapter(Command command){
        this.command=command;
    }

    @Override
    public String getCommandName() {
        return command.getName();
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands."+command.getName()+".usage";
    }


    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        //去空参数
        List<String> slist=new ArrayList<>();
        for(String arg:args){
            if(!arg.equals("")){
                slist.add(arg);
            }
        }
        command.process(slist);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos){
        if(!(sender.getEntityWorld() instanceof WorldServer)){
            return command.complete(Arrays.asList(args));
        }
        return Command.getEmptyList();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
