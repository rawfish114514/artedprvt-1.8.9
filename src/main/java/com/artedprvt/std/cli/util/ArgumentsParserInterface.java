package com.artedprvt.std.cli.util;

import com.artedprvt.iv.anno.InterfaceView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@InterfaceView
public @interface ArgumentsParserInterface {
}
