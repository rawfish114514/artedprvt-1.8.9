package rawfish.artedprvt.item.itemFile;

import net.minecraft.item.ItemStack;

public interface FileSpace {
    ItemStack getFileByName(String fullName);
    String getSpaceName();
}
