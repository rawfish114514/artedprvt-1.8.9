package rawfish.artedprvt.script.mi;

import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * 常用的事件
 */
public enum Events {
    tick,//TickEvent.ServerTickEvent
    click,//PlayerInteractEvent
    use,//PlayerUseItemEvent.Finish

    //SideOnly Client
    c_chat,//ClientChatReceivedEvent
}
