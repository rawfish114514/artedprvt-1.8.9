package com.artedprvt.std.minecraft.chat;

import com.artedprvt.api.Solvable;

import java.util.Objects;

@Solvable
public class ChatStyle {
    private String chat;

    private ChatClick click = null;

    private ChatHover hover = null;

    @Solvable
    public ChatStyle() {
        this("");
    }

    @Solvable
    public ChatStyle(String chat) {
        this.chat = chat;
    }

    @Solvable
    public ChatStyle(String chat, String hover) {
        this(chat);
        hover(hover);
    }

    @Solvable
    public ChatStyle click(ChatClick chatClick) {
        click = chatClick;
        return this;
    }

    @Solvable
    public ChatStyle hover(ChatHover chatHover) {
        hover = chatHover;
        return this;
    }

    @Solvable
    public ChatStyle hover(String chatHover) {
        hover = new ChatHoverString(chatHover);
        return this;
    }

    @Solvable
    public String getChat() {
        return chat;
    }

    @Solvable
    public ChatClick getClick() {
        return click;
    }

    @Solvable
    public ChatHover getHover() {
        return hover;
    }

    @Solvable
    public boolean isClickNonnull() {
        return Objects.nonNull(click);
    }

    @Solvable
    public boolean isHoverNonnull() {
        return Objects.nonNull(hover);
    }
}
