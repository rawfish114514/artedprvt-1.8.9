package rawfish.artedprvt.std.cgl;

import rawfish.artedprvt.core.script.BaseClassGroup;
import rawfish.artedprvt.std.minecraft.chat.*;
import rawfish.artedprvt.std.minecraft.entity.*;
import rawfish.artedprvt.std.math.*;

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
        /// chat
        add(ChatClick.class);
        add(ChatComponent.class);
        add(ChatConsole.class);
        add(ChatHover.class);
        add(ChatHoverString.class);
        add(ChatStyle.class);

        /// entity
        add(Entity.class);
        add(PlayerEntity.class);

        /// math
        add(Vector.class);
        add(Vector3.class);
    }
}
