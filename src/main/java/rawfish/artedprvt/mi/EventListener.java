package rawfish.artedprvt.mi;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import rawfish.artedprvt.common.EventLoader;
import rawfish.artedprvt.event.InputStringEvent;

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
     * 调用监听函数
     * 在此进行异常捕获
     * @param event 事件
     */
    public void run(Event event){
        try {
            f.run(event);
        }catch(Exception e){
            terminate();
            pro.getThread().getUncaughtExceptionHandler().uncaughtException(pro.getThread(),e);
        }
    }

    /**
     * 构造一个事件监听器
     * 这个构造函数没有添加对象到主线程，因为没有必要
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
        if(type==Events.join){
            listener=new JoinEventListener(f);
        }
        if(type==Events.input){
            listener=new InputStringEventListener(f);
            setEventBus(this,EventLoader.EVENT_BUS);
        }


        //SideOnly Client
        if(type==Events.c_tick){
            listener=new ClientTickEventListener(f);
        }
        if(type==Events.r_tick){
            listener=new RenderTickEventListener(f);
        }
        if(type==Events.c_chat){
            listener=new ClientChatEventListener(f);
        }

        if(listener==null){
            throw new RuntimeException("未知的Events枚举???");
        }
    }

    /**
     * 设置事件监听器和代理监听器的事件总线
     * @param listener
     * @param bus
     */
    public static void setEventBus(EventListener listener,EventBus bus){
        listener.EVENT_BUS=bus;
        listener.listener.EVENT_BUS=bus;
    }

    public EventBus EVENT_BUS=MinecraftForge.EVENT_BUS;
    /**
     * 注册监听器
     */
    public void register(){
        EVENT_BUS.register(listener);
    }

    /**
     * 注销监听器
     */
    @Override
    public void terminate() {
        EVENT_BUS.unregister(listener);
    }

    public static class TickEventListener extends EventListener{
        public TickEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(TickEvent.ServerTickEvent event){
            run(event);
        }
    }

    public static class ClickEventListener extends EventListener{
        public ClickEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(PlayerInteractEvent event){
            run(event);
        }
    }

    public static class UseEventListener extends EventListener{
        public UseEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(PlayerUseItemEvent.Finish event){
            run(event);
        }
    }

    public static class JoinEventListener extends EventListener{
        public JoinEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(EntityJoinWorldEvent event){
            run(event);
        }
    }

    public static class InputStringEventListener extends EventListener{
        public InputStringEventListener(EventFunction f){
            super(f);
        }
        @SubscribeEvent
        public void onEvent(InputStringEvent event){
            run(event);
        }
    }

    public static class ClientTickEventListener extends EventListener{
        public ClientTickEventListener(EventFunction f){
            super(f);
        }
        @SubscribeEvent
        public void onEvent(TickEvent.ClientTickEvent event){
            run(event);
        }
    }

    public static class RenderTickEventListener extends EventListener{
        public RenderTickEventListener(EventFunction f){
            super(f);
        }
        @SubscribeEvent
        public void onEvent(TickEvent.RenderTickEvent event){
            run(event);
        }
    }

    public static class ClientChatEventListener extends EventListener{
        public ClientChatEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(ClientChatReceivedEvent event){
            run(event);
        }
    }




    //未定义事件监听器
    public static class _EventListener extends EventListener{
        public _EventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(Event event){
            run(event);
        }
    }

}
