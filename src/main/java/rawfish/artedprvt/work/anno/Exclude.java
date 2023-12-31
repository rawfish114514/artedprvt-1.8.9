package rawfish.artedprvt.work.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 排除
 * 被排除的类在编译期间不会被注册
 * 另外这个注解也会使相关的类也被排除，通常这是最小化的且必要的
 * <p>
 * 生命周期: 此生命周期和此生命周期包括的所有阶段以及这些阶段的目标都被排除
 * 阶段: 此阶段的生命周期不再包含此阶段，此阶段和此阶段的目标都被排除
 * 目标: 目标被排除不会影响其他类
 * 命令: 命令被排除不会影响其他类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Exclude {
}
