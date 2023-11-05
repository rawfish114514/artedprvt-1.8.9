package rawfish.artedprvt.core.script;

import java.util.Collection;

/**
 * 组的概念
 * 定义了一组class对象的集合
 */
public interface ClassGroup {
    /**
     * 要求 for each loop 是安全的
     * @return
     */
    Collection<Class> getClasses();

    String getName();

    boolean permission();
}
