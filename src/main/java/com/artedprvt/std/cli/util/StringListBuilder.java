package com.artedprvt.std.cli.util;

import com.artedprvt.iv.anno.InterfaceView;

import java.util.ArrayList;
import java.util.Arrays;

@InterfaceView
public class StringListBuilder extends ArrayList<String> {
    @InterfaceView
    public StringListBuilder adds(String string) {
        add(string);
        return this;
    }

    @InterfaceView
    public StringListBuilder adds(String... strings) {
        addAll(Arrays.asList(strings));
        return this;
    }
}
