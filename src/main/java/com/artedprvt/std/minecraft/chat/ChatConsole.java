package com.artedprvt.std.minecraft.chat;

import com.artedprvt.core.Environment;
import com.artedprvt.core.InProcess;
import com.artedprvt.core.Logger;
import com.artedprvt.core.Process;
import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.cli.Messager;
import com.artedprvt.std.cli.util.Literals;
import com.artedprvt.std.text.Formatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@InterfaceView
public class ChatConsole implements Messager, InProcess {
    private Logger logger = null;
    private GuiNewChat v_guiNewChat = null;
    private boolean log = true;
    private boolean longtime = false;

    @InterfaceView
    public ChatConsole() {
        Process process = up();
        if (process != null) {
            logger = process.logger();
        }
        if (Environment.MCCLIENT) {
            Minecraft minecraft = Minecraft.getMinecraft();
            if (Objects.nonNull(minecraft)) {
                GuiIngame guiIngame = minecraft.ingameGUI;
                if (Objects.nonNull(guiIngame)) {
                    v_guiNewChat = guiIngame.getChatGUI();
                }
            }
        }
    }

    @InterfaceView
    public ChatConsole print(ChatComponent chat) {
        return print(chat, 0);
    }

    @InterfaceView
    public ChatConsole print(ChatComponent chat, int id) {
        if (isGuiNewChatNonnull()) {
            printChat(LTM_ChatComponent_IChatComponent(chat), id);
        }
        info(Literals.fcClear(chat.getChatString()));

        return this;
    }

    @InterfaceView
    public ChatConsole print(String chat) {
        return print(chat, 0);
    }

    @InterfaceView
    public ChatConsole print(String chat, int id) {
        return print(new ChatComponent(String.valueOf(chat)), id);
    }

    @InterfaceView
    public ChatConsole delete(int id) {
        if (isGuiNewChatNonnull()) {
            synchronized (this) {
                v_guiNewChat.deleteChatLine(id);
            }
        }
        return this;
    }

    private void info(String message) {
        if (log && Objects.nonNull(logger)) {
            logger.info(message);
        }
    }

    public void setLog(boolean b) {
        log = b;
    }

    public void setLongtime(boolean b) {
        longtime = b;
    }

    boolean close = false;

    @Override
    public void close() throws Exception {
        if (longtime) {
            return;
        }
        logger = null;
        v_guiNewChat = null;
        close = true;
    }

    private boolean isGuiNewChatNonnull() {
        return Objects.nonNull(v_guiNewChat);
    }

    private void printChat(IChatComponent chatComponent, int chatLineId) {
        synchronized (this) {
            v_guiNewChat.printChatMessageWithOptionalDeletion(chatComponent, chatLineId);
        }
    }

    private IChatComponent LTM_ChatComponent_IChatComponent(ChatComponent chat) {
        ChatComponentText MsuperChatComponentText = new ChatComponentText("");
        List<ChatStyle> chatStyles = chat.getChatStyles();
        ChatStyle chatStyle;
        for (int i = 0; i < chatStyles.size(); i++) {
            chatStyle = chatStyles.get(i);
            ChatComponentText MchatComponentText = new ChatComponentText(chatStyle.getChat());
            net.minecraft.util.ChatStyle MchatStyle = new net.minecraft.util.ChatStyle();
            if (chatStyle.isClickNonnull()) {
                MchatStyle.setChatClickEvent(new LClickEvent(chatStyle.getClick()));
            }
            if (chatStyle.isHoverNonnull()) {
                MchatStyle.setChatHoverEvent(new LHoverEvent(chatStyle.getHover()));
            }
            MchatComponentText.setChatStyle(MchatStyle);
            MsuperChatComponentText.appendSibling(MchatComponentText);
        }
        return MsuperChatComponentText;
    }

    @Override
    @InterfaceView
    public void send(String message) {
        print(message);
    }

    @Override
    @InterfaceView
    public void send(String message, String hover) {
        print(new ChatComponent(message, hover));
    }

    @Override
    @InterfaceView
    public boolean canHover() {
        return true;
    }

    static Object target = null;

    @Override
    @InterfaceView
    public int dialog(String message, String... buttons) {
        if (buttons.length == 0) {
            throw new RuntimeException("对话框没有按钮");
        }
        if (target != null) {
            //System.out.println("抢占目标");
        }
        target = Thread.currentThread();
        try {
            List<String> buttonList = Arrays.asList(buttons);
            List<Integer> actions = new ArrayList<>();
            AtomicBoolean x = new AtomicBoolean(false);

            ChatComponent c1 = new ChatComponent(message + "   ", "选择以下选项中的一个点击")
                    .add(new ChatStyle(Formatting.GRAY + Formatting.BOLD + "(X)", "关闭").click(() -> {
                        x.set(true);
                    }));
            print(c1, 1919810);


            ChatComponent c2 = new ChatComponent();
            for (int i = 0; i < buttonList.size(); i++) {
                int I = i;
                c2.add(" ");
                c2.add(new ChatStyle(buttonList.get(i)).click(() -> {
                    actions.add(I);
                }));
            }
            print(c2, 1919811);
            int result = -1;

            try {
                long time = System.currentTimeMillis();
                while (true) {
                    if (Thread.currentThread() != target) {
                        //System.out.println(target+": 被抢占目标");
                        return -1;
                    }
                    if (close) {
                        return -1;
                    }
                    if (x.get()) {
                        return -1;
                    }
                    if (actions.size() > 0) {
                        return result = actions.get(0);
                    }
                    if (System.currentTimeMillis() - time >= 60000) {
                        return -1;
                    }
                    Thread.sleep(20);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (!close && Thread.currentThread() == target) {
                    print(c1, 1919810);
                    if (result == -1) {
                        c2.add(Formatting.RESET + "   已关闭");
                    } else {
                        c2.add(Formatting.RESET + "   已选择" + buttonList.get(result));
                    }
                    print(c2, 1919811);
                }
            }
        } finally {
            //System.out.println(target+": 释放目标");
            if (Thread.currentThread() == target) {
                target = null;
            }
        }
    }

    @Override
    @InterfaceView
    public boolean canDialog() {
        return true;
    }


    private static class LClickEvent extends ClickEvent implements InProcess {
        private ChatClick click;

        public LClickEvent(ChatClick click) {
            super(Action.SUGGEST_COMMAND, "");
            up();
            this.click = click;
        }

        @Override
        public String getValue() {
            synchronized (this) {
                if (Objects.nonNull(click)) {
                    click.click();
                }
            }
            return "";
        }

        @Override
        public void close() throws Exception {
            synchronized (this) {
                click = null;
            }
        }
    }

    private static class LHoverEvent extends HoverEvent implements InProcess {
        private ChatHover hover;

        public LHoverEvent(ChatHover hover) {
            super(Action.SHOW_TEXT, null);
            up();
            this.hover = hover;
        }

        @Override
        public IChatComponent getValue() {
            synchronized (this) {
                if (Objects.nonNull(hover)) {
                    return new ChatComponentText(hover.hover());
                }
            }
            return new ChatComponentText("");
        }

        @Override
        public void close() throws Exception {
            synchronized (this) {
                if (hover instanceof ChatHoverString) {
                    return;
                }
                hover = null;
            }
        }
    }
}
