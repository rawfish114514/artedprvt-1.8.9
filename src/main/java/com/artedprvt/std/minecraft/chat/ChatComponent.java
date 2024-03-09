package com.artedprvt.std.minecraft.chat;

import com.artedprvt.iv.anno.InterfaceView;

import java.util.ArrayList;
import java.util.List;

@InterfaceView
public class ChatComponent {
    private List<ChatStyle> chatStyles = new ArrayList<>();

    @InterfaceView
    public ChatComponent() {

    }

    @InterfaceView
    public ChatComponent(String chat) {
        add(chat);
    }

    @InterfaceView
    public ChatComponent(String chat, String hover) {
        add(chat, hover);
    }

    @InterfaceView
    public ChatComponent(ChatStyle chatStyle) {
        add(chatStyle);
    }

    @InterfaceView
    public ChatComponent add(String chat) {
        return add(new ChatStyle(chat));
    }

    @InterfaceView
    public ChatComponent add(String chat, String hover) {
        return add(new ChatStyle(chat, hover));
    }

    @InterfaceView
    public ChatComponent add(ChatStyle chatStyle) {
        chatStyles.add(chatStyle);
        return this;
    }

    @InterfaceView
    public List<ChatStyle> getChatStyles() {
        return chatStyles;
    }

    @InterfaceView
    public String getChatString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chatStyles.size(); i++) {
            sb.append(chatStyles.get(i).getChat());
        }
        return sb.toString();
    }
}
