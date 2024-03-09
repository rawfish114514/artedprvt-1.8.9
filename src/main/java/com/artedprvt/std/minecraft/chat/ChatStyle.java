package com.artedprvt.std.minecraft.chat;

import com.artedprvt.iv.anno.InterfaceView;

import java.util.Objects;

@InterfaceView
public class ChatStyle {
    private String chat;

    private ChatClick click = null;

    private ChatHover hover = null;

    @InterfaceView
    public ChatStyle() {
        this("");
    }

    @InterfaceView
    public ChatStyle(String chat) {
        this.chat = chat;
    }

    @InterfaceView
    public ChatStyle(String chat, String hover) {
        this(chat);
        hover(hover);
    }

    @InterfaceView
    public ChatStyle click(ChatClick chatClick) {
        click = chatClick;
        return this;
    }

    @InterfaceView
    public ChatStyle hover(ChatHover chatHover) {
        hover = chatHover;
        return this;
    }

    @InterfaceView
    public ChatStyle hover(String chatHover) {
        hover = new ChatHoverString(chatHover);
        return this;
    }

    @InterfaceView
    public String getChat() {
        return chat;
    }

    @InterfaceView
    public ChatClick getClick() {
        return click;
    }

    @InterfaceView
    public ChatHover getHover() {
        return hover;
    }

    @InterfaceView
    public boolean isClickNonnull() {
        return Objects.nonNull(click);
    }

    @InterfaceView
    public boolean isHoverNonnull() {
        return Objects.nonNull(hover);
    }
}
