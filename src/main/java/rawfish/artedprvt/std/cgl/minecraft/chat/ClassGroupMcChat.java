package rawfish.artedprvt.std.cgl.minecraft.chat;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.core.BaseClassGroup;
import rawfish.artedprvt.std.minecraft.chat.ChatClick;
import rawfish.artedprvt.std.minecraft.chat.ChatComponent;
import rawfish.artedprvt.std.minecraft.chat.ChatConsole;
import rawfish.artedprvt.std.minecraft.chat.ChatHover;
import rawfish.artedprvt.std.minecraft.chat.ChatHoverString;
import rawfish.artedprvt.std.minecraft.chat.ChatStyle;

@Solvable
public class ClassGroupMcChat extends BaseClassGroup {
    @Solvable
    public static final ClassGroupMcChat INSTANCE=new ClassGroupMcChat("mc.chat");

    @Solvable
    public ClassGroupMcChat(Object name) {
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