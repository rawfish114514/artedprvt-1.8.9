package com.artedprvt.std.minecraft.chat;

import com.artedprvt.api.Solvable;

@Solvable
public class ChatHoverString implements ChatHover {
    private String chat;

    @Solvable
    public ChatHoverString(String chat) {
        this.chat = chat;
    }

    @Solvable
    @Override
    public String hover() {
        return chat;
    }
}
