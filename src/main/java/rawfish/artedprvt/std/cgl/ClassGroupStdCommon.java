package rawfish.artedprvt.std.cgl;

import rawfish.artedprvt.core.script.BaseClassGroup;
import rawfish.artedprvt.std.chat.*;

/**
 * 可用于 ClientSide 和 ServerSide 的组
 */
public class ClassGroupStdCommon extends BaseClassGroup {
    public static final ClassGroupStdCommon INSTANCE=new ClassGroupStdCommon(null);

    public ClassGroupStdCommon(Object name) {
        super(name);
        init();
    }

    private void init() {
        add(ChatClick.class);
        add(ChatComponent.class);
        add(ChatConsole.class);
        add(ChatHover.class);
        add(ChatHoverString.class);
        add(ChatStyle.class);
    }
}
