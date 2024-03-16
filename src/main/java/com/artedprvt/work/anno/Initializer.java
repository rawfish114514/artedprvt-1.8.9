package com.artedprvt.work.anno;

import com.artedprvt.work.ProjectAccess;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用此注解的类必须实现
 * {@link ProjectAccess}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Initializer {
    String name();

    String description();

    String author();
}
