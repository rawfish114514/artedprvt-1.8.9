package rawfish.artedprvt.work.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 目标
 * 真正的任务单元
 * <p>
 * 可以绑定到阶段在阶段被调用时调用自身(有阶段目标)
 * 或者使用默认值(Goal.class)表示不绑定阶段(无阶段目标)
 * <p>
 * 命令系统
 * goal可以实现处理，补全，格式，信息接口
 * {@link rawfish.artedprvt.std.cli.ProcessInterface}
 * {@link rawfish.artedprvt.std.cli.CompleteInterface}
 * {@link rawfish.artedprvt.std.cli.FormatInterface}
 * {@link rawfish.artedprvt.std.cli.InfoInterface}
 * goal必须实现处理的接口
 * <p>
 * 当参数属于goal类型时(有冒号)
 * 有阶段目标: "phase:goal"
 * 无阶段目标: ":goal"
 * <p>
 * 参数处理，补全，格式，信息由此goal处理
 * 对于有阶段目标,如果没有对应接口实现会尝试调用阶段的实现
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Goal {
    /**
     * Phase注解的类
     *
     * @return
     */
    Class value() default Goal.class;
}
