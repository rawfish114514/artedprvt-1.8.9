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
import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptObject;
import rawfish.artedprvt.core.ScriptProcess;
import rawfish.artedprvt.event.InputStringEvent;

/**
 * 事件监听器
 */
public class EventListener implements ScriptObject {
    /**
     * 回调函数
     */
    public EventFunction f;
    /**
     * 监听器代理
     */
    public EventListener listener=null;

    private ScriptProcess process;

    /**
     * 构造一个事件监听器但不会监听任何事件
     * @param f 回调函数
     */
    public EventListener(EventFunction f){
        this.f=f;
        listener=this;
        process=up();
        if(process==null){
            ScriptExceptions.exception("脚本对象上下文异常");
        }
        register();
    }

    /**
     * 调用监听函数
     * @param event 事件
     */
    public void run(Event event){
        try {
            f.run(event);
        }catch(Throwable e){
            process.getExceptionHandler().uncaughtException(new RuntimeException("在调用监听函数时发生错误"));
        }
    }

    /**
     * 构造一个事件监听器
     * 这个构造函数没有添加对象到主线程，因为没有必要
     * @param type 事件类型
     * @param f 回调函数
     */
    @ScriptCallable
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


        //side only Client
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
     * 设置事件监听器和代理事件监听器的事件总线
     * @param listener
     * @param bus
     */
    public static void setEventBus(EventListener listener,EventBus bus){
        listener.EVENT_BUS=bus;
        if(listener.listener!=null){
            listener.listener.EVENT_BUS=bus;
        }
    }

    public EventBus EVENT_BUS=MinecraftForge.EVENT_BUS;
    /**
     * 注册监听器
     */
    private void register(){
        EVENT_BUS.register(listener);
    }

    /**
     * 注销监听器
     */
    @Override
    public void onClose() {
        EVENT_BUS.unregister(listener);
    }


    public static class TickEventListener extends EventListener{
        @ScriptCallable
        public TickEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(TickEvent.ServerTickEvent event){
            run(event);
        }
    }

    public static class ClickEventListener extends EventListener{
        @ScriptCallable
        public ClickEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(PlayerInteractEvent event){
            run(event);
        }
    }

    public static class UseEventListener extends EventListener{
        @ScriptCallable
        public UseEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(PlayerUseItemEvent.Finish event){
            run(event);
        }
    }

    public static class JoinEventListener extends EventListener{
        @ScriptCallable
        public JoinEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(EntityJoinWorldEvent event){
            run(event);
        }
    }

    public static class InputStringEventListener extends EventListener{
        @ScriptCallable
        public InputStringEventListener(EventFunction f){
            super(f);
        }
        @SubscribeEvent
        public void onEvent(InputStringEvent event){
            run(event);
        }
    }

    public static class ClientTickEventListener extends EventListener{
        @ScriptCallable
        public ClientTickEventListener(EventFunction f){
            super(f);
        }
        @SubscribeEvent
        public void onEvent(TickEvent.ClientTickEvent event){
            run(event);
        }
    }

    public static class RenderTickEventListener extends EventListener{
        @ScriptCallable
        public RenderTickEventListener(EventFunction f){
            super(f);
        }
        @SubscribeEvent
        public void onEvent(TickEvent.RenderTickEvent event){
            run(event);
        }
    }

    public static class ClientChatEventListener extends EventListener{
        @ScriptCallable
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
