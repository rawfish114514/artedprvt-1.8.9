package rawfish.artedprvt.mi;


import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import rawfish.artedprvt.event.InputStringEvent;

/**
 * 常用的事件
 */
public enum Events {
    /**
     * 服务端刻事件
     */
    @ScriptCallable
    @EventTarget(TickEvent.ServerTickEvent.class)
    tick,

    /**
     * 点击事件
     */
    @ScriptCallable
    @EventTarget(PlayerInteractEvent.class)
    click,

    /**
     * 使用物品事件
     */
    @ScriptCallable
    @EventTarget(PlayerUseItemEvent.Finish.class)
    use,

    /**
     * 实体加入世界事件
     */
    @ScriptCallable
    @EventTarget(EntityJoinWorldEvent.class)
    join,

    /**
     * 输入字符串事件
     */
    @ScriptCallable
    @EventTarget(InputStringEvent.class)
    input,

    //SideOnly Client

    /**
     * 客户端刻事件
     */
    @ScriptCallable
    @EventTarget(TickEvent.ClientTickEvent.class)
    c_tick,

    /**
     * 渲染刻事件
     */
    @ScriptCallable
    @EventTarget(TickEvent.RenderTickEvent.class)
    r_tick,

    /**
     * 客户端聊天事件
     */
    @ScriptCallable
    @EventTarget(ClientChatReceivedEvent.class)
    c_chat,
}
