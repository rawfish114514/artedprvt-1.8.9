package rawfish.artedprvt.client;

import net.minecraftforge.client.ClientCommandHandler;
import rawfish.artedprvt.command.CommandLoader;
import rawfish.artedprvt.common.CommonProxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import rawfish.artedprvt.script.ScriptProcess;
import rawfish.artedprvt.script.js.ClassCollection;
import rawfish.artedprvt.script.js.McpToSrgString;


public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);

        new CommandLoader(ClientCommandHandler.instance);
        ScriptProcess.initSargs();
        ClassCollection.load(McpToSrgString.getMcpToSrgString());
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }
}