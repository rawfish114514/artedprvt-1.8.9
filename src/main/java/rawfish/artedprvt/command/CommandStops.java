package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import rawfish.artedprvt.script.ScriptProcess;

public class CommandStops extends CommandBase {
    @Override
    public String getCommandName() {
        return "stops";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.stops.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        ScriptProcess[] pros=new ScriptProcess[ScriptProcess.proList.size()];
        if(pros.length==0){
            throw new WrongUsageException("commands.stops.usage");
        }
        ScriptProcess.proList.toArray(pros);
        for(ScriptProcess pro:pros){
            pro.stop();
        }
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 1;
    }
}
