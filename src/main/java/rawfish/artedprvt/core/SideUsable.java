package rawfish.artedprvt.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ���ע���ʾ���������ڿͻ��˻�������ʹ��
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SideUsable {
    Sides[] value();
}
