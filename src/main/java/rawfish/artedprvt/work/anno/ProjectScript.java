package rawfish.artedprvt.work.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目脚本必须存在public class ap
 * 这个类必须使用此注解
 * 这个注解只能由此类使用
 * <p></p>
 * 这个类必须实现
 * {@link rawfish.artedprvt.work.ProjectAccess}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ProjectScript {
    String name();

    String description();

    String created();
}
