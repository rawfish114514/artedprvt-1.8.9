package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import rawfish.artedprvt.script.ScriptConst;

public class CommandApf extends CommandBase {
    @Override
    public String getCommandName()
    {
        return "apf";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.apf.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        throw new WrongUsageException("commands.apf.usage");
    }
    @Override
    public int getRequiredPermissionLevel()
    {
        return 1;
    }

    public static class CommandArtedprvt extends CommandApf{
        @Override
        public String getCommandName()
        {
            return "artedprvt";
        }
    }
}
