package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import rawfish.artedprvt.common.EventLoader;
import rawfish.artedprvt.script.ScriptConst;

public class CommandChat extends CommandBase {
    @Override
    public String getCommandName()
    {
        return "chat";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.chat.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length > 0) {
            throw new WrongUsageException("commands.chat.usage");
        }
        ScriptConst.chat=!ScriptConst.chat;
        sender.addChatMessage(new ChatComponentText("Chat模式: "+ScriptConst.chat));
    }
    @Override
    public int getRequiredPermissionLevel()
    {
        return 1;
    }
}
