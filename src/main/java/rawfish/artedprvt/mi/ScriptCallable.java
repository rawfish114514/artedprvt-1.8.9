package rawfish.artedprvt.mi;

import java.lang.annotation.*;

/**
 * 标记应该可用于脚本内使用的字段或方法
 */

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR,ElementType.METHOD,ElementType.FIELD})
public @interface ScriptCallable {
}
