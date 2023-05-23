package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import rawfish.artedprvt.scriptold.ScriptConst;

public class CommandAd extends CommandBase {
    public CommandAd(String nameIn){
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
        return "commands.ad.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length > 0) {
            throw new WrongUsageException("commands.ad.usage");
        }
        ScriptConst.debug=!ScriptConst.debug;
        sender.addChatMessage(new ChatComponentText("Debug模式: "+ScriptConst.debug));
    }
    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}
