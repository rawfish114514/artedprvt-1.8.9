package rawfish.artedprvt.script.mi;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 事件监听器
 */
public class EventListener extends LifeDepend{
    /**
     * 回调函数
     */
    public EventFunction f;
    /**
     * 监听器代理
     */
    public EventListener listener=null;

    /**
     * 构造一个事件监听器但不会监听任何事件
     * 不要使用此构造函数
     * @param f 回调函数
     */
    public EventListener(EventFunction f){
        this.f=f;
        listener=this;
        up();
    }

    /**
     * 构造一个事件监听器
     * @param type 事件类型
     * @param f 回调函数
     */
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


        //SideOnly Client
        if(type==Events.c_chat){
            listener=new ClientChatEventListener(f);
        }

        if(listener==null){
            throw new RuntimeException("未知的Events枚举???");
        }
    }

    /**
     * 注册监听器
     */
    public void register(){
        MinecraftForge.EVENT_BUS.register(listener);
    }

    /**
     * 注销监听器
     */
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

    public static class ClientChatEventListener extends EventListener{
        public ClientChatEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(ClientChatReceivedEvent event){
            f.run(event);
        }
    }


    //未定义事件监听器
    public static class _EventListener extends EventListener{
        public _EventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(Event event){
            f.run(event);
        }
    }

}
