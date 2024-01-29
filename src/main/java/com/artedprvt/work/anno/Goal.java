package com.artedprvt.work.anno;

import com.artedprvt.std.cli.InfoHandler;
import com.artedprvt.std.cli.ProcessInterface;

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
 * 目标可以实现
 * {@link InfoHandler}
 * 仅在需要获取此目标的info时调用
 * 如果没有实现此接口可能会尝试调用有阶段目标的阶段的实现
 * <p>
 * 目标必须实现
 * {@link ProcessInterface}
 * <p>
 * 当参数属于目标类型时(有冒号)
 * 有阶段目标: "phase:goal"
 * 无阶段目标: ":goal"
 * <p>
 * 类名必须是: GoalName
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
