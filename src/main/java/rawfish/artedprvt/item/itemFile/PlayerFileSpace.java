package rawfish.artedprvt.item.itemFile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import rawfish.artedprvt.item.ItemLoader;

import java.util.HashMap;
import java.util.Map;

public class PlayerFileSpace implements FileSpace{
    private EntityPlayer player;
    public PlayerFileSpace(EntityPlayer playerIn){
        player=playerIn;
    }
    @Override
    public ItemStack getFileByName(String fullName) {
        //获取文件 在这里完成文件的获取和文件检查
        InventoryPlayer inventory=player.inventory;
        ItemStack[] mainInventory=inventory.mainInventory;

        for(ItemStack itemStack:mainInventory){
            if(itemStack!=null) {
                if (isItemFile(itemStack)) {
                    if (getFullName(itemStack).equals(fullName)) {
                        return itemStack;
                    }
                }
            }
        }
        return null;
    }

    private boolean isItemFile(ItemStack itemStack) {
        if(itemStack.getItem().getRegistryName().equals("artedprvt:file")){
            NBTTagCompound tag=itemStack.getTagCompound();
            if(tag!=null){
                if(tag.hasKey("data",8)){
                    if(tag.hasKey("head",10)){
                        tag=(NBTTagCompound)tag.getTag("head");
                        if(tag.hasKey("name",8)&&tag.hasKey("type",8)&&tag.hasKey("describe",8)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private String getFullName(ItemStack itemStack){
        NBTTagCompound tag=itemStack.getTagCompound();
        tag=tag.getCompoundTag("head");
        return tag.getString("name")+'.'+tag.getString("type");
    }

    @Override
    public String getSpaceName() {
        return player.getName();
    }

    private static Map<String,PlayerFileSpace> map=new HashMap<String,PlayerFileSpace>();
    public static void set(PlayerFileSpace playerFileSpace){
        map.put(playerFileSpace.getSpaceName(),playerFileSpace);
    }
    public static PlayerFileSpace get(String playerName){
        return map.get(playerName);
    }
}
