package rawfish.artedprvt.dev;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示一个没完成的功能在可预见的未来中可能会完成
 * 使用注释可能不被察觉
 * 依靠IDE的定位功能快速找到没做完的工作
 */
@Retention(RetentionPolicy.SOURCE)
@Target({
        ElementType.TYPE,
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.CONSTRUCTOR,
        ElementType.ANNOTATION_TYPE,
        ElementType.LOCAL_VARIABLE})

@rawfish.artedprvt.dev.FutureWork("")
public @interface FutureWork {
    String value();
}
