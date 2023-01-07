package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import rawfish.artedprvt.script.ScriptProcess;

public class CommandStops extends CommandBase {
    public CommandStops(String nameIn){
        name=nameIn;
    }

    public final String name;
    @Override
    public String getCommandName() {
        return name;
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
        if(args.length>1){
            throw new WrongUsageException("commands.stops.usage");
        }
        ScriptProcess.proList.toArray(pros);

        if(args.length==1){
            String sp=args[0];
            boolean nisp=true;
            for (ScriptProcess pro : pros) {
                if(sp.equals(pro.getName())||sp.equals(String.valueOf(pro.getId()))){
                    pro.stop(null);
                    nisp=false;
                }
            }
            if(nisp){
                throw new CommandException("找不到进程 请检查进程名或进程id");
            }
        }else {
            for (ScriptProcess pro : pros) {
                pro.stop(null);
            }
        }
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 1;
    }
}
