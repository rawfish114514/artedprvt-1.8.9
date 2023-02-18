package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import rawfish.artedprvt.script.ScriptConst;

import java.util.HashMap;
import java.util.Map;

public class CommandApf extends CommandBase {
    public CommandApf(String nameIn){
        name=nameIn;
        cmdm=new HashMap<>();
        cmdm.put("ac",new CommandAc("ac"));
        cmdm.put("ad",new CommandAd("ad"));
        cmdm.put("apkg",new CommandApkg("apkg"));
        cmdm.put("in",new CommandIn("in"));
        cmdm.put("pros",new CommandPros("pros"));
        cmdm.put("script",new CommandScript("script"));
        cmdm.put("stops",new CommandStops("stops"));
    }

    public Map<String,CommandBase> cmdm;

    public final String name;
    @Override
    public String getCommandName() {
        return name;
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
        return 0;
    }
}
