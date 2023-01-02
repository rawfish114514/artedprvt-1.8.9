package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import rawfish.artedprvt.script.ScriptProcess;

public class CommandScript extends CommandBase {
    @Override
    public String getCommandName() {
        return "script";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.script.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length<1){
            throw new WrongUsageException("commands.script.usage");
        }
        String[] sargs=new String[args.length-1];
        System.arraycopy(args, 1, sargs, 0, sargs.length);
        ScriptProcess script=new ScriptProcess(sender,args[0],sargs);

        script.start();
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 1;
    }
}
