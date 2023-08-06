package rawfish.artedprvt.core;

import java.lang.annotation.*;

/**
 * 我们约定这个注解标记的类名和方法名和字段名不变
 * 如果这个方法或字段相关的类型没有{@link ProgramUsable}注解
 * 例如它们的参数类型和返回类型或字段类型
 * 我们也无法保证这些方法或字段的使用不会变化
 * 尽量在不同的版本中提供完全相同的功能
 * 但无法保证所有功能都能在所有版本中实现
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.CONSTRUCTOR,ElementType.METHOD,ElementType.FIELD})
public @interface ProgramUsable {
}
