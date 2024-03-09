package com.artedprvt.core;

import com.artedprvt.iv.anno.InterfaceView;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@InterfaceView
public class BaseClassGroup implements ClassGroup {
    private final String name;
    private final Set<Class> set;

    //super
    @InterfaceView
    public BaseClassGroup(Object name) {
        this.name = String.valueOf(name);
        set = new HashSet<>();
    }

    @InterfaceView
    //operate
    protected void add(Class clas) {
        set.add(clas);
    }

    @InterfaceView
    //operate
    protected void union(BaseClassGroup target) {
        if (target.permission()) {
            set.addAll(target.getClasses());
        }
    }


    @Override
    public Set<Class> getClasses() {
        return Collections.unmodifiableSet(set);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean permission() {
        return true;
    }
}
