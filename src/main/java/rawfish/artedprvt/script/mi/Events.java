package rawfish.artedprvt.script.mi;


/**
 * 常用的事件
 */
public enum Events {
    /**
     * 刻事件
     * 源: TickEvent.ServerTickEvent
     */
    tick,

    /**
     * 点击事件
     * 源: PlayerInteractEvent
     */
    click,

    /**
     * 使用物品事件
     * 源: PlayerUseItemEvent.Finish
     */
    use,

    /**
     * 用户输入字符串事件
     * 源: InputStringEvent
     */
    input,

    //SideOnly Client

    /**
     * 客户端聊天事件
     * 源: ClientChatReceivedEvent
     */
    c_chat,
}
