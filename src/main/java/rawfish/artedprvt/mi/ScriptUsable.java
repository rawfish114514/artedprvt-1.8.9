package rawfish.artedprvt.mi;

import java.lang.annotation.*;

/**
 * 标记适用于脚本或应该被使用在脚本内的字段或方法
 */

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR,ElementType.METHOD,ElementType.FIELD})
public @interface ScriptUsable {
}
