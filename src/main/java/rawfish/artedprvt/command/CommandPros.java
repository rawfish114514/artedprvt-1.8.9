package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import rawfish.artedprvt.script.ScriptProcess;

import java.util.Date;

public class CommandPros extends CommandBase {
    public CommandPros(String nameIn){
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
        return "commands.pros.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length > 0) {
            throw new WrongUsageException("commands.pros.usage");
        }
        if(ScriptProcess.proList.size()==0){
            throw new WrongUsageException("commands.pros.usage");
        }
        for(ScriptProcess pro:ScriptProcess.proList){
            int ret=pro.getRet();
            if(ret!=0&&ret!=1){
                pro.getSys().print(pro.getPack(),
                        String.format("process: %s pid: %s",pro.getPack(),pro.getId()),
                        new ChatComponentText(null){
                            public String hover=null;
                            @Override
                            public String getUnformattedTextForChat()
                            {
                                if(pro.getRet()!=7){
                                    hover=pro.getStatistics();
                                }
                                return String.valueOf(hover);
                            }
                        });
            }
        }
    }
    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}
