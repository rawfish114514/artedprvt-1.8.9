package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Command extends CommandBase {
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

    public abstract void process(List<String> args);

    public abstract List<String> tab(List<String> args);

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
        return tab(Arrays.asList(args));
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
