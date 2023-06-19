package rawfish.artedprvt.mi;


import net.minecraftforge.event.entity.player.ItemTooltipEvent;
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
    @ScriptUsable
    @EventTarget(TickEvent.ServerTickEvent.class)
    tick,

    /**
     * 点击事件
     */
    @ScriptUsable
    @EventTarget(PlayerInteractEvent.class)
    click,

    /**
     * 使用物品事件
     */
    @ScriptUsable
    @EventTarget(PlayerUseItemEvent.Finish.class)
    use,

    /**
     * 实体加入世界事件
     */
    @ScriptUsable
    @EventTarget(EntityJoinWorldEvent.class)
    join,

    /**
     * 输入字符串事件
     */
    @ScriptUsable
    @EventTarget(InputStringEvent.class)
    input,

    /**
     * 工具提示事件
     */
    @ScriptUsable
    @EventTarget(ItemTooltipEvent.class)
    tooltip,

    //side only Client

    /**
     * 客户端刻事件
     */
    @ScriptUsable
    @EventTarget(TickEvent.ClientTickEvent.class)
    c_tick,

    /**
     * 渲染刻事件
     */
    @ScriptUsable
    @EventTarget(TickEvent.RenderTickEvent.class)
    r_tick,

    /**
     * 客户端聊天事件
     */
    @ScriptUsable
    @EventTarget(ClientChatReceivedEvent.class)
    c_chat,
}
