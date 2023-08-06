package rawfish.artedprvt.mi;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import rawfish.artedprvt.common.EventLoader;
import rawfish.artedprvt.core.*;
import rawfish.artedprvt.event.InputStringEvent;

/**
 * 事件监听器
 */
@SideUsable(Sides.ALL)
@ProgramUsable
public class EventListener implements ScriptObject {
    private ScriptProcess process;

    /**
     * 回调函数
     */
    public EventFunction f;
    
    /**
     * 监听器
     */
    public EventListener listener=null;

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
    @ProgramUsable
    public EventListener(Events type,EventFunction f){
        if(type==Events.tick){
            listener=new TickEventListener(f);return;
        }
        if(type==Events.click){
            listener=new ClickEventListener(f);return;
        }
        if(type==Events.use){
            listener=new UseEventListener(f);return;
        }
        if(type==Events.join){
            listener=new JoinEventListener(f);return;
        }
        if(type==Events.input){
            listener=new InputStringEventListener(f);
            setEventBus(this,EventLoader.EVENT_BUS);return;
        }
        if(type==Events.tooltip){
            listener=new ItemTooltipEventListener(f);return;
        }
        if(type==Events.bl_place){
            listener=new BlockPlaceEventListener(f);return;
        }
        if(type==Events.bl_break){
            listener=new BlockBreakEventListener(f);return;
        }

        //side only Client
        if(type==Events.c_tick){
            listener=new ClientTickEventListener(f);return;
        }
        if(type==Events.r_tick){
            listener=new RenderTickEventListener(f);return;
        }
        if(type==Events.c_chat){
            listener=new ClientChatEventListener(f);return;
        }

        throw new RuntimeException("未知的Events枚举???");
    }

    /**
     * 设置事件监听器的事件总线
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
    @ProgramUsable
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
        @ProgramUsable
        public TickEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(TickEvent.ServerTickEvent event){
            run(event);
        }
    }

    public static class ClickEventListener extends EventListener{
        @ProgramUsable
        public ClickEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(PlayerInteractEvent event){
            run(event);
        }
    }

    public static class UseEventListener extends EventListener{
        @ProgramUsable
        public UseEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(PlayerUseItemEvent.Finish event){
            run(event);
        }
    }

    public static class JoinEventListener extends EventListener{
        @ProgramUsable
        public JoinEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(EntityJoinWorldEvent event){
            run(event);
        }
    }

    public static class InputStringEventListener extends EventListener{
        @ProgramUsable
        public InputStringEventListener(EventFunction f){
            super(f);
        }
        @SubscribeEvent
        public void onEvent(InputStringEvent event){
            run(event);
        }
    }

    public static class ItemTooltipEventListener extends EventListener{
        @ProgramUsable
        public ItemTooltipEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(ItemTooltipEvent event){
            run(event);
        }
    }

    public static class BlockPlaceEventListener extends EventListener{
        public BlockPlaceEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(BlockEvent.PlaceEvent event){
            run(event);
        }
    }

    public static class BlockBreakEventListener extends EventListener{
        public BlockBreakEventListener(EventFunction f) {
            super(f);
        }
        @SubscribeEvent
        public void onEvent(BlockEvent.BreakEvent event){
            run(event);
        }
    }

    public static class ClientTickEventListener extends EventListener{
        @ProgramUsable
        public ClientTickEventListener(EventFunction f){
            super(f);
        }
        @SubscribeEvent
        public void onEvent(TickEvent.ClientTickEvent event){
            run(event);
        }
    }

    public static class RenderTickEventListener extends EventListener{
        @ProgramUsable
        public RenderTickEventListener(EventFunction f){
            super(f);
        }
        @SubscribeEvent
        public void onEvent(TickEvent.RenderTickEvent event){
            run(event);
        }
    }

    public static class ClientChatEventListener extends EventListener{
        @ProgramUsable
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
