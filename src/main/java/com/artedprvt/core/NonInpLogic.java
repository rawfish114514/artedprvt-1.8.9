package com.artedprvt.core;

import com.artedprvt.iv.anno.InterfaceView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 非InProcess逻辑
 * 它提醒你 与此相关的代码可能不在进程上下文中执行 应该谨慎在这样的代码中使用实现了InProcess接口的类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.TYPE,
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,})
@InterfaceView
public @interface NonInpLogic {
}
