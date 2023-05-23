package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.common.eventhandler.Event;
import rawfish.artedprvt.common.EventLoader;
import rawfish.artedprvt.event.InputStringEvent;

public class CommandIn extends CommandBase {
    public CommandIn(String nameIn){
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
        return "commands.in.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        String string=String.join(" ",args);
        Event e=new InputStringEvent(string);
        EventLoader.EVENT_BUS.post(e);
    }
    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}