package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import rawfish.artedprvt.script.ScriptProcess;

public class CommandStop extends CommandBase {
    @Override
    public String getCommandName() {
        return "stop";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.stop.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        ScriptProcess[] pros=new ScriptProcess[ScriptProcess.proList.size()];
        if(pros.length==0){
            throw new WrongUsageException("commands.stop.usage");
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
