package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatComponentText;
import rawfish.artedprvt.common.EventLoader;
import rawfish.artedprvt.item.itemFile.PlayerFileSpace;

public class CommandFile extends CommandBase {
    @Override
    public String getCommandName()
    {
        return "file";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.file.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if(sender instanceof EntityPlayer){
            if(args.length==1){
                String fileName=String.valueOf(args[0]);
                PlayerFileSpace space= PlayerFileSpace.get(sender.getName());
                ItemStack itemStack=space.getFileByName(fileName);
                if(itemStack!=null) {
                    NBTTagCompound nbt = itemStack.getTagCompound();
                    NBTTagCompound headTag = nbt.getCompoundTag("head");
                    NBTTagString nameTag = (NBTTagString) headTag.getTag("name");
                    NBTTagString typeTag = (NBTTagString) headTag.getTag("type");
                    NBTTagString describeTag = (NBTTagString) headTag.getTag("describe");
                    NBTTagString dataTag = (NBTTagString) nbt.getTag("data");
                    sender.addChatMessage(new ChatComponentText("name: " + nameTag.getString()));
                    sender.addChatMessage(new ChatComponentText("type: " + typeTag.getString()));
                    sender.addChatMessage(new ChatComponentText("describe: " + describeTag.getString()));
                    sender.addChatMessage(new ChatComponentText("data: " + dataTag.getString()));
                    return;
                }
            }
        }
        throw new WrongUsageException("commands.file.usage");
    }
    @Override
    public int getRequiredPermissionLevel()
    {
        return 4;
    }
}