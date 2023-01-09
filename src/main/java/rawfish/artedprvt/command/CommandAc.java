package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import rawfish.artedprvt.script.ScriptConst;

public class CommandAc extends CommandBase {
    public CommandAc(String nameIn){
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
        return "commands.ac.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length > 0) {
            throw new WrongUsageException("commands.ac.usage");
        }
        ScriptConst.chat=!ScriptConst.chat;
        sender.addChatMessage(new ChatComponentText("Chat模式: "+ScriptConst.chat));
    }
    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}
