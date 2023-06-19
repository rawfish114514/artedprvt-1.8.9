package rawfish.artedprvt.mi;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
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
     * 监听器
     */
    public EventListener listener=null;

    private ScriptProcess process;

    private boolean breakk=false;

    /**
     * 构造一个事件监听器
     * 这个类不会监听任何事件
     * 必须通过子类订阅指定事件
     * @param f 回调函数
     */
    public EventListener(EventFunction f){
        this.f=f;
        listener=this;
        process=up();
        if(process==null){
            ScriptExceptions.exception("脚本对象上下文异常");
            breakk=true;
        }
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
     * 通过这个构造函数创建的事件监听器实际不监听事件
     * 它仅通过事件类型创建相应的具体事件监听器
     * @param type 事件类型
     * @param f 回调函数
     */
    @ScriptUsable
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
        if(type==Events.tooltip){
            listener=new ItemTooltipEventListener(f);
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
    public void register(){
        if(!listener.breakk){
            EVENT_BUS.register(listener);
        }
    }

    /**
     * 注销监听器
     */
    @Override
    public void onClose() {
        EVENT_BUS.unregister(listener);
    }


    public static class TickEventListener extends EventListener{
        @ScriptUsable
        public TickEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(TickEvent.ServerTickEvent event){
            run(event);
        }
    }

    public static class ClickEventListener extends EventListener{
        @ScriptUsable
        public ClickEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(PlayerInteractEvent event){
            run(event);
        }
    }

    public static class UseEventListener extends EventListener{
        @ScriptUsable
        public UseEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(PlayerUseItemEvent.Finish event){
            run(event);
        }
    }

    public static class JoinEventListener extends EventListener{
        @ScriptUsable
        public JoinEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(EntityJoinWorldEvent event){
            run(event);
        }
    }

    public static class InputStringEventListener extends EventListener{
        @ScriptUsable
        public InputStringEventListener(EventFunction f){
            super(f);
        }
        @SubscribeEvent
        public void onEvent(InputStringEvent event){
            run(event);
        }
    }

    public static class ItemTooltipEventListener extends EventListener{
        @ScriptUsable
        public ItemTooltipEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(ItemTooltipEvent event){
            run(event);
        }
    }

    public static class ClientTickEventListener extends EventListener{
        @ScriptUsable
        public ClientTickEventListener(EventFunction f){
            super(f);
        }
        @SubscribeEvent
        public void onEvent(TickEvent.ClientTickEvent event){
            run(event);
        }
    }

    public static class RenderTickEventListener extends EventListener{
        @ScriptUsable
        public RenderTickEventListener(EventFunction f){
            super(f);
        }
        @SubscribeEvent
        public void onEvent(TickEvent.RenderTickEvent event){
            run(event);
        }
    }

    public static class ClientChatEventListener extends EventListener{
        @ScriptUsable
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
