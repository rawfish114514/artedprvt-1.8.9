package rawfish.artedprvt.common;

import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import rawfish.artedprvt.block.BlockLoader;
import rawfish.artedprvt.command.CommandLoader;
import rawfish.artedprvt.creativetab.CreativeTabsLoader;
import rawfish.artedprvt.inventory.GuiElementLoader;
import rawfish.artedprvt.item.ItemLoader;

import java.util.Map;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        new ConfigLoader(event);
        new CreativeTabsLoader(event);
        new ItemLoader(event);
        new BlockLoader(event);
    }

    public void init(FMLInitializationEvent event)
    {
        new EventLoader();
        new GuiElementLoader();
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

    public static CommandHandler ch;
    public void serverStarting(FMLServerStartingEvent event)
    {
        ch = (CommandHandler) event.getServer().getCommandManager();
        Map<String, ICommand> commands= ch.getCommands();
        System.out.println(commands);

        new CommandLoader(event);
    }
}