package rawfish.artedprvt.work.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 生命周期
 * 由阶段构成
 * <p>
 * 不参与命令系统
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Lifecycle {
    /**
     * Phase注解的类
     *
     * @return
     */
    Class<?>[] value();
}
