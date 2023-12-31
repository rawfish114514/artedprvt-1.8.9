package rawfish.artedprvt.work.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 命令 定义子命令
 * 具有命令的所有功能
 * <p>
 * 命令可以实现
 * {@link rawfish.artedprvt.std.cli.ProcessInterface}
 * {@link rawfish.artedprvt.std.cli.CompleteInterface}
 * {@link rawfish.artedprvt.std.cli.FormatInterface}
 * {@link rawfish.artedprvt.std.cli.InfoInterface}
 * <p>
 * 或直接继承
 * {@link rawfish.artedprvt.std.cli.Command}
 * <p>
 * 类名必须是: CommandName
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Command {
}
