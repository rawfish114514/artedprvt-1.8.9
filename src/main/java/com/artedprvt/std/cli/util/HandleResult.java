package com.artedprvt.std.cli.util;

import com.artedprvt.api.Solvable;

@Solvable
public class HandleResult {
    private boolean handle;
    private String result;
    private int pos;

    @Solvable
    public HandleResult(boolean handle, String result, int pos) {
        this.handle = handle;
        this.result = result;
        this.pos = pos;
    }

    @Solvable
    public HandleResult() {
        this(false, null, 0);
    }

    @Solvable
    public HandleResult(String result) {
        this(true, result, 0);
    }

    @Solvable
    public HandleResult(String result, int pos) {
        this(true, result, pos);
    }

    @Solvable
    public boolean isHandle() {
        return handle;
    }

    @Solvable
    public String getResult() {
        return result;
    }

    @Solvable
    public int getPos() {
        return pos;
    }

}
