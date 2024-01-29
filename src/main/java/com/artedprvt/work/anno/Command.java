package com.artedprvt.work.anno;

import com.artedprvt.std.cli.CompleteInterface;
import com.artedprvt.std.cli.FormatInterface;
import com.artedprvt.std.cli.InfoInterface;
import com.artedprvt.std.cli.ProcessInterface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 命令 定义子命令
 * 具有命令的所有功能
 * <p>
 * 命令可以实现
 * {@link ProcessInterface}
 * {@link CompleteInterface}
 * {@link FormatInterface}
 * {@link InfoInterface}
 * <p>
 * 或直接继承
 * {@link com.artedprvt.std.cli.Command}
 * <p>
 * 类名必须是: CommandName
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Command {
}
