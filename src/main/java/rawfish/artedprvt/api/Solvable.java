package rawfish.artedprvt.api;

import java.lang.annotation.*;

/**
 * 表示一个通用的编程接口
 * 完全屏蔽原版代码
 * 这些{@link net.minecraft} {@link com.mojang} {@link net.minecraftforge}
 * 不应该出现在类的继承，方法返回类型和参数类型，字段类型中
 * 因为脚本程序直接调用原版代码对版本兼容性是破坏性的
 * 同一个类，字段，方法，构造函数在不同版本中应提供完全相同的功能
 * 如果字段，方法，构造函数使用此注解那么类也必须使用此注解
 * 如果方法和构造函数使用此注解那么方法的重载方法也必须使用此注解
 * 另外它们必须是public的
 *
 * @mcversion 1.8.9
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD,ElementType.CONSTRUCTOR})
public @interface Solvable {
}
