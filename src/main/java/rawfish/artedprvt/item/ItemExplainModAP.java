package rawfish.artedprvt.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.StatCollector;

import java.util.List;


public class ItemExplainModAP extends Itemis {
    public NBTTagCompound tag=null;
    public ItemStack book=new ItemStack(Items.written_book);
    public ItemExplainModAP(){
        super();

        //init tag

        tag=new NBTTagCompound();
            NBTTagString authorTag=new NBTTagString("Rawfishc");
            NBTTagString titleTag=new NBTTagString("\u00a7b\u00a7lExplain Artedprvt");
            NBTTagByte resolvedTag=new NBTTagByte((byte) 1);
        tag.setTag("pages",getPagesTag());
        tag.setTag("author",authorTag);
        tag.setTag("title",titleTag);
        tag.setTag("resolved",resolvedTag);
        book.setTagCompound(tag);
    }


    @Override
    public void appendCreativeItem(List<ItemStack> subItems){
        subItems.add(book);
    }


    public NBTTagList getPagesTag(){
        NBTTagList pagesTag=new NBTTagList();;
        pagesTag.appendTag(new NBTTagString("Artedprvt Frame\n\n作者:Rawfishc\nbilibili@Raw闲鱼\n\n\n玩家在游戏中创造"));
        pagesTag.appendTag(new NBTTagString("此模组融合了以下软件包\n\nrhino"));
        return pagesTag;
    }
}
