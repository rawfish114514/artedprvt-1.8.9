package com.artedprvt.core;

import com.artedprvt.iv.anno.InterfaceView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 非InProcess逻辑
 * 它提醒你 与此相关的代码可能不在进程上下文中执行
 * 对于提供者 应该谨慎在这样的代码中使用实现了InProcess接口的类
 * 对于消费者 需要重视应用了此注解的代码 这类代码极其容易泄露导致严重错误
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.METHOD,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,})
@InterfaceView
public @interface NonInpLogic {
}
