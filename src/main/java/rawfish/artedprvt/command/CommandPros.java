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
            ChatComponentText chat=new ChatComponentText(String.format("process: %s pid: %s",pro.getPack(),pro.getId()));
            int ret=pro.getRet();
            if(ret!=0&&ret!=1){
                String hover=pro.getStatistics();
                chat.setChatStyle(new ChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(String.valueOf(hover)))));
            }
            sender.addChatMessage(chat);
        }
    }
    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}
