package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class CommandStt extends CommandBase {
    public CommandScript commandScript=new CommandScript("");
    public CommandStt(String nameIn){
        name=nameIn;
    }

    public final String name;
    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.stt.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        commandScript.processCommand(sender,new String[]{"main"});
    }
    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}