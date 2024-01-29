package com.artedprvt.std.cgl.minecraft.chat;

import com.artedprvt.core.BaseClassGroup;
import com.artedprvt.std.minecraft.chat.ChatClick;
import com.artedprvt.std.minecraft.chat.ChatComponent;
import com.artedprvt.std.minecraft.chat.ChatConsole;
import com.artedprvt.std.minecraft.chat.ChatHover;
import com.artedprvt.std.minecraft.chat.ChatHoverString;
import com.artedprvt.std.minecraft.chat.ChatStyle;
import com.artedprvt.api.Solvable;

@Solvable
public class ClassGroupMcChat extends BaseClassGroup {
    @Solvable
    public static final ClassGroupMcChat INSTANCE = new ClassGroupMcChat("mc.chat");

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