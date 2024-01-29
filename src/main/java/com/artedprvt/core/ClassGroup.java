package com.artedprvt.core;

import com.artedprvt.api.Solvable;

import java.util.Collection;

/**
 * 组的概念
 * 定义了一组class对象的集合
 */
@Solvable
public interface ClassGroup {
    /**
     * 要求 for each loop 是安全的
     *
     * @return
     */
    @Solvable
    Collection<Class> getClasses();

    @Solvable
    String getName();

    @Solvable
    boolean permission();
}
