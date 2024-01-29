package com.artedprvt.std.cli.util;

import com.artedprvt.api.Solvable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Solvable
public @interface ArgumentsParserInterface {
}
