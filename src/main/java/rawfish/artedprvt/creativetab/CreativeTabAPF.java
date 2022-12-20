package rawfish.artedprvt.creativetab;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabAPF extends CreativeTabs
{
    public CreativeTabAPF()
    {
        super("artedprvt");
    }

    @Override
    public Item getTabIconItem()
    {
        return Items.written_book;
    }
}