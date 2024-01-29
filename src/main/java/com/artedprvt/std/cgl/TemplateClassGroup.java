package com.artedprvt.std.cgl;

import com.artedprvt.core.BaseClassGroup;

public class TemplateClassGroup extends BaseClassGroup {
    public static final TemplateClassGroup INSTANCE = new TemplateClassGroup("name");

    public TemplateClassGroup(Object name) {
        super(name);
        init();
    }

    private void init() {
        add(null);
    }
}