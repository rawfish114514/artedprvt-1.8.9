package rawfish.artedprvt.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import rawfish.artedprvt.Artedprvt;
import rawfish.artedprvt.client.gui.GuiContainerImage;

public class GuiElementLoader implements IGuiHandler
{
    public static final int GUI_IMAGE = 1;

    public GuiElementLoader()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(Artedprvt.instance, this);
    }

    public static String data=null;
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case GUI_IMAGE:
                return new ContainerImage(player,data);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case GUI_IMAGE:
                return new GuiContainerImage(new ContainerImage(player,data));
            default:
                return null;
        }
    }
}