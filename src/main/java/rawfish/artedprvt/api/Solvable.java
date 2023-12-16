package rawfish.artedprvt.api;

import java.lang.annotation.*;

/**
 * 表示一个通用的编程接口
 * 完全屏蔽原版代码
 * 而且它规定接口调用的范围
 * 不管是脚本编写aar程序还是java编写用于apf的程序都只能使用 "有Solvable效果的" 类和成员
 * "有Solvable效果的" 包括
 * {@link java}包和所有子包的public的类和成员(不包括{@link javax} {@link sun}等包)
 * 有{@link Solvable}注解的类和成员
 * "没有Solvable效果的" 的类不能被继承
 *
 * 同一个类，字段，方法，构造函数在不同版本中应提供完全相同的功能
 * 如果字段，方法，构造函数使用此注解那么类也必须使用此注解
 * 如果方法和构造函数使用此注解那么方法的重载方法也必须使用此注解
 * 另外它们必须是public的
 *
 * @mcversion 1.8.9
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.TYPE,
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.CONSTRUCTOR})
@Solvable
public @interface Solvable {
}
