package rawfish.artedprvt.command.util;

public class HandleResult {
    private boolean handle;
    private String result;
    private int pos;

    public HandleResult(boolean handle, String result, int pos) {
        this.handle = handle;
        this.result = result;
        this.pos = pos;
    }

    public HandleResult() {
        this(false, null, 0);
    }

    public HandleResult(String result) {
        this(true, result, 0);
    }

    public HandleResult(String result, int pos) {
        this(true, result, pos);
    }

    public boolean isHandle() {
        return handle;
    }

    public String getResult() {
        return result;
    }

    public int getPos() {
        return pos;
    }

}
