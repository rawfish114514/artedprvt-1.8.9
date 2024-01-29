package com.artedprvt.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示一个通用的编程接口
 * 完全屏蔽原版代码
 * 而且它规定接口调用的范围
 * 不管是脚本编写aar程序还是java编写用于apf的程序都只能使用 "有Solvable效果的" 类和成员
 * "有Solvable效果的" 包括
 * {@link java}包和所有子包的public的类和成员(不包括{@link javax} {@link sun}等包)
 * 有{@link Solvable}注解的类和成员
 * "没有Solvable效果的" 的类不能被继承
 * <p>
 * 同一个类，字段，方法，构造函数在不同版本中应提供完全相同的功能
 * 如果字段，方法，构造函数使用此注解那么类也必须使用此注解
 * 如果方法和构造函数使用此注解那么方法的重载方法也必须使用此注解
 * 另外它们必须是public的
 * <p>
 * 使用此注解是为了代码不被版本限制
 * 通常这应该是向下兼容的，如果你从1.8.9构建你的应用那么在1.16.5也应该能正常工作
 * 此注解会生成特殊的仅包含声明的java文件
 * 普通方法将返回null或基本类型默认值
 * 构造函数将匹配超类但什么都不做
 * 常量将设定为null或基本类型默认值
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
