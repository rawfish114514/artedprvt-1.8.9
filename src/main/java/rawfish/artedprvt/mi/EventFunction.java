package rawfish.artedprvt.mi;

import net.minecraftforge.fml.common.eventhandler.Event;
import rawfish.artedprvt.api.Solvable;

/**
 * 事件回调函数
 * 运行时动态代理实现
 */
@FunctionalInterface
@Solvable
public interface EventFunction {
    /**
     * 函数接口方法
     * @param event 事件
     */
    void run(Event event);
}
