package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import rawfish.artedprvt.common.EventLoader;
import rawfish.artedprvt.script.ScriptConst;

public class CommandDebug extends CommandBase {
    @Override
    public String getCommandName()
    {
        return "debug";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.debug.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length > 0) {
            throw new WrongUsageException("commands.debug.usage");
        }
        ScriptConst.debug=!ScriptConst.debug;
        sender.addChatMessage(new ChatComponentText("Debug模式: "+ScriptConst.debug));
    }
    @Override
    public int getRequiredPermissionLevel()
    {
        return 1;
    }
}
