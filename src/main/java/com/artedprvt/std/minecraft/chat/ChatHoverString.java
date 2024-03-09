package com.artedprvt.std.minecraft.chat;

import com.artedprvt.iv.anno.InterfaceView;

@InterfaceView
public class ChatHoverString implements ChatHover {
    private String chat;

    @InterfaceView
    public ChatHoverString(String chat) {
        this.chat = chat;
    }

    @InterfaceView
    @Override
    public String hover() {
        return chat;
    }
}
