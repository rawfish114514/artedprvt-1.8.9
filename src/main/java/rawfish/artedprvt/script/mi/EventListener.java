package rawfish.artedprvt.script.mi;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventListener extends LifeDepend{
    public EventFunction f;
    public EventListener(EventFunction f){
        this.f=f;
        up();
    }

    public void register(){
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent event){
        f.run(event);
    }

    @Override
    public void terminate() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}
