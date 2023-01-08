package rawfish.artedprvt.script.mi;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class EventListener extends LifeDepend{
    public EventFunction f;
    public EventListener listener=null;
    public EventListener(EventFunction f){
        this.f=f;
        listener=this;
        up();
    }
    public EventListener(Events type,EventFunction f){
        if(type==Events.tick){
            listener=new TickEventListener(f);
        }
        if(type==Events.click){
            listener=new ClickEventListener(f);
        }
        if(type==Events.use){
            listener=new UseEventListener(f);
        }

        if(listener==null){
            throw new RuntimeException("未知的Events枚举???");
        }
    }

    public void register(){
        MinecraftForge.EVENT_BUS.register(listener);
    }
    @Override
    public void terminate() {
        MinecraftForge.EVENT_BUS.unregister(listener);
    }

    public static class TickEventListener extends EventListener{
        public TickEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(TickEvent.ServerTickEvent event){
            f.run(event);
        }
    }

    public static class ClickEventListener extends EventListener{
        public ClickEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(PlayerInteractEvent event){
            f.run(event);
        }
    }

    public static class UseEventListener extends EventListener{
        public UseEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(PlayerUseItemEvent.Finish event){
            f.run(event);
        }
    }
}
