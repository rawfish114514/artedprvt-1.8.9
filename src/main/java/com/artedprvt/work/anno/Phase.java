package com.artedprvt.work.anno;

import com.artedprvt.std.cli.InfoHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 阶段
 * 是生命周期的一部分
 * <p>
 * 阶段可以实现
 * {@link InfoHandler}
 * 仅在需要获取此阶段的info时调用
 * <p>
 * 当参数属于阶段类型时(无冒号)
 * <p>
 * 阶段被调用时，会找到自己的生命周期并从第一个阶段开始执行直到此阶段
 * 每个阶段都调用所有自身阶段的目标,调用顺序无法确定
 * <p>
 * 类名必须是: PhaseName
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Phase {
}
