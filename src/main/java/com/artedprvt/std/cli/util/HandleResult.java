package com.artedprvt.std.cli.util;

import com.artedprvt.iv.anno.InterfaceView;

@InterfaceView
public class HandleResult {
    private boolean handle;
    private String result;
    private int pos;

    @InterfaceView
    public HandleResult(boolean handle, String result, int pos) {
        this.handle = handle;
        this.result = result;
        this.pos = pos;
    }

    @InterfaceView
    public HandleResult() {
        this(false, null, 0);
    }

    @InterfaceView
    public HandleResult(String result) {
        this(true, result, 0);
    }

    @InterfaceView
    public HandleResult(String result, int pos) {
        this(true, result, pos);
    }

    @InterfaceView
    public boolean isHandle() {
        return handle;
    }

    @InterfaceView
    public String getResult() {
        return result;
    }

    @InterfaceView
    public int getPos() {
        return pos;
    }

}
