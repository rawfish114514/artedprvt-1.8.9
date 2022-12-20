package rawfish.artedprvt.item;

import net.minecraft.block.BlockNote;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.ScriptableObject;
import rawfish.artedprvt.creativetab.CreativeTabsLoader;
import rawfish.artedprvt.id.FormatCode;
import rawfish.artedprvt.id.KeyWord;
import rawfish.artedprvt.item.itemFile.CmdFile;
import rawfish.artedprvt.item.itemFile.ImgFile;
import rawfish.artedprvt.item.itemFile.JsFile;
import rawfish.artedprvt.item.itemFile.PyFile;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * ItemFile表示文件结构也是文件系统最重要的部分
 * 文件相对文件空间的完整文件名是name.type
 * 文件相对全局的全局文件名是space:name.type
 * 真正的结构是 一级空间.二级空间:一级路径/二级路径...
 * 所有一级空间和部分二级空间都由AP完全控制
 * 玩家可以自定义二级空间 其中final空间只能作为文件管理层
 * unfinal空间只能扩展子空间
 * 玩家背包内的文件的访问方式是Player.Steve:file
 * 如果没有指定空间则会根据发出者尝试匹配合理的空间
 * 比如玩家在执行命令时会自动补上Player.Steve二级空间
 * 空间的设计是为了应对minecraft复杂的容器
 */
public class ItemFile extends Itemis {
    //保证有效的类型
    public static List<String> typeList=getTypeList();
    public long listenTime;

    private static List<String> getTypeList() {
        List<String> list=new ArrayList<String>();
        list.add("text");//文本文件 主要给玩家看
        list.add("cmd");//命令集文件 按顺序发送多个命令 //已实现简单的运行
        list.add("img");//图像文件 主要给玩家看 //正在实现
        list.add("world");//地图数据文件
        list.add("js");//脚本文件 使用js引擎 //已实现简单的运行
        list.add("py");//脚本文件 使用python引擎 //已实现简单的运行 但没完全实现
        list.add("pack");//包文件 包含多个文件
        list.add("json");//数据交换文件 使用json规则
        list.add("rs");//红石电路文件 模拟红石系统的程序文件
        list.add("mic");//乐谱文件
        list.add("mov");//动画工程文件
        return list;
    }

    public ItemFile(){
        super();
        setUnlocalizedName("file");
        setRegistryName("file");
        setCreativeTab(CreativeTabsLoader.tab);
        setMaxStackSize(1);
        setMaxDamage(0);
        setHasSubtypes(true);
        listenTime=0;
    }
    public ItemStack setAttribute(ItemStack itemstack,String type){
        NBTTagCompound tag=new NBTTagCompound();
        NBTTagCompound headTag=new NBTTagCompound();
        headTag.setTag("name",new NBTTagString("File"));
        headTag.setTag("type",new NBTTagString(type));
        headTag.setTag("describe",new NBTTagString(StatCollector.translateToLocal(String.format("fileDescribe.%s", type))));
        tag.setTag("head",headTag);
        String s= KeyWord.ISNO;
        NBTTagString dataTag=new NBTTagString(s);
        tag.setTag("data",dataTag);
        itemstack.setTagCompound(tag);
        return itemstack;
    }
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        NBTTagCompound tag=stack.getTagCompound();
        if(tag==null){
            return;
        }
        if(tag.hasKey("head",10)){
            tag=(NBTTagCompound)tag.getTag("head");
            if(tag.hasKey("name",8)&&tag.hasKey("type",8)&&tag.hasKey("describe",8)){
                String s=((NBTTagString)tag.getTag("type")).getString();
                if(typeList.contains(s)) {
                    tooltip.add(String.format(".%s(\u00a7f%s\u00a77)", s, StatCollector.translateToLocal(String.format("fileType.%s", s))));
                }else{
                    tooltip.add(String.format(".%s", s));
                }
                s=((NBTTagString)tag.getTag("describe")).getString();
                if(!s.equals(".no")) {
                    tooltip.add("\u00a7a" + s);
                }
            }
        }
    }
    @Override
    public void appendCreativeItem(List<ItemStack> subItems){
        for(String type:typeList) {
            subItems.add(setAttribute(new ItemStack(this, 1, 0),type));
        }
    }
    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        NBTTagCompound tag=stack.getTagCompound();
        if(tag==null){
            return new StringBuilder().append((char)25991).append((char)26412).toString();
        }
        if(tag.hasKey("head",10)){
            tag=(NBTTagCompound)tag.getTag("head");
            if(tag.hasKey("name",8)&&tag.hasKey("type",8)&&tag.hasKey("describe",8)){
                return ((NBTTagString)tag.getTag("name")).getString();
            }
        }
        return new StringBuilder().append((char)25991).append((char)26412).toString();
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        if(!worldIn.isRemote) {
            long time=new Date().getTime();

            if(time-listenTime>600){
                listenTime=time;
            }else{
                return super.onItemRightClick(itemStackIn, worldIn, playerIn);
            }

            NBTTagCompound tag = itemStackIn.getTagCompound();
            boolean ok = false;
            String name = null;
            String type = null;
            String describe = null;
            String data = null;
            if (tag != null) {
                if (tag.hasKey("data", 8)) {
                    data = ((NBTTagString) tag.getTag("data")).getString();
                }
                if (tag.hasKey("head", 10)) {
                    tag = (NBTTagCompound) tag.getTag("head");
                    if (tag.hasKey("name", 8) && tag.hasKey("type", 8) && tag.hasKey("describe", 8)) {
                        ok = true;
                        name = ((NBTTagString) tag.getTag("name")).getString();
                        type = ((NBTTagString) tag.getTag("type")).getString();
                        describe = ((NBTTagString) tag.getTag("describe")).getString();
                    }
                }
            }

            if (ok && data != null) {
                boolean ext = false;
                if ("js".equals(type)) {
                    JsFile.onItemRightClick(worldIn, playerIn, name, data, describe);
                    ext = true;
                }
                if ("cmd".equals(type)) {
                    CmdFile.onItemRightClick(worldIn, playerIn, name, data, describe);
                    ext = true;
                }
                if ("py".equals(type)) {
                    PyFile.onItemRightClick(worldIn, playerIn, name, data, describe);
                    ext = true;
                }
                if ("img".equals(type)) {
                    ImgFile.onItemRightClick(worldIn, playerIn, name, data, describe);
                    ext = true;
                }

                if (!ext) {
                    playerIn.addChatComponentMessage(new ChatComponentText(FormatCode.COLOR_4 + "未找到打开方式"));
                }
            }
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn);
    }

    public void playSoundEffect(World worldIn,EntityPlayer playerIn,int eventParam){
        float f = (float)Math.pow(2.0D, (double)(eventParam - 12) / 12.0D);
        worldIn.playSound(playerIn.posX + 0.5D, playerIn.posY + 0.5D, playerIn.posZ + 0.5D, "random.click", 3.0F, f,false);

    }
}
