package com.artedprvt.std.cli.util;

import com.artedprvt.iv.anno.InterfaceView;

import java.util.HashMap;
import java.util.Map;

@InterfaceView
public class ParseResult {
    private boolean correct;

    private Map<String, String> map;

    @InterfaceView
    public ParseResult(boolean correct) {
        this.correct = correct;
        map = new HashMap<>();
    }

    @InterfaceView
    public void put(String key, String value) {
        map.put(key, value);
    }

    @InterfaceView
    public String get(String key) {
        return map.get(key);
    }

    @InterfaceView
    public boolean isCorrect() {
        return correct;
    }
}
