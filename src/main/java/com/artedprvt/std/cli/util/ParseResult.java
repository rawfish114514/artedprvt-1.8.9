package com.artedprvt.std.cli.util;

import com.artedprvt.api.Solvable;

import java.util.HashMap;
import java.util.Map;

@Solvable
public class ParseResult {
    private boolean correct;

    private Map<String, String> map;

    @Solvable
    public ParseResult(boolean correct) {
        this.correct = correct;
        map = new HashMap<>();
    }

    @Solvable
    public void put(String key, String value) {
        map.put(key, value);
    }

    @Solvable
    public String get(String key) {
        return map.get(key);
    }

    @Solvable
    public boolean isCorrect() {
        return correct;
    }
}
