package rawfish.artedprvt.work.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 阶段
 * 是生命周期的一部分
 * <p>
 * 命令系统
 * phase可以实现补全,格式,信息接口
 * {@link rawfish.artedprvt.std.cli.CompleteInterface}
 * {@link rawfish.artedprvt.std.cli.FormatInterface}
 * {@link rawfish.artedprvt.std.cli.InfoInterface}
 * <p>
 * 当参数属于phase类型时(无冒号)
 * <p>
 * phase被调用时，会找到自己的生命周期并从第一个的阶段开始执行直到目的阶段
 * 每个阶段都调用所有自身阶段的目标,调用顺序无法确定
 * <p>
 * 参数补全格式信息由此phase处理
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Phase {
}
