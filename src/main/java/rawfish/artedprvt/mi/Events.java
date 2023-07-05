package rawfish.artedprvt.mi;


import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import rawfish.artedprvt.core.ProcedureUsable;
import rawfish.artedprvt.event.InputStringEvent;

/**
 * 常用的事件
 */
@ProcedureUsable
public enum Events {
    /**
     * 服务端刻事件
     */
    @ProcedureUsable
    @EventTarget(TickEvent.ServerTickEvent.class)
    tick,

    /**
     * 点击事件
     */
    @ProcedureUsable
    @EventTarget(PlayerInteractEvent.class)
    click,

    /**
     * 使用物品事件
     */
    @ProcedureUsable
    @EventTarget(PlayerUseItemEvent.Finish.class)
    use,

    /**
     * 实体加入世界事件
     */
    @ProcedureUsable
    @EventTarget(EntityJoinWorldEvent.class)
    join,

    /**
     * 输入字符串事件
     */
    @ProcedureUsable
    @EventTarget(InputStringEvent.class)
    input,

    /**
     * 工具提示事件
     */
    @ProcedureUsable
    @EventTarget(ItemTooltipEvent.class)
    tooltip,

    /**
     * 方块放置事件
     */
    @ProcedureUsable
    @EventTarget(BlockEvent.PlaceEvent.class)
    bl_place,

    /**
     * 方块破坏事件
     */
    @ProcedureUsable
    @EventTarget(BlockEvent.BreakEvent.class)
    bl_break,

    //side only Client

    /**
     * 客户端刻事件
     */
    @ProcedureUsable
    @EventTarget(TickEvent.ClientTickEvent.class)
    c_tick,

    /**
     * 渲染刻事件
     */
    @ProcedureUsable
    @EventTarget(TickEvent.RenderTickEvent.class)
    r_tick,

    /**
     * 客户端聊天事件
     */
    @ProcedureUsable
    @EventTarget(ClientChatReceivedEvent.class)
    c_chat,
}
