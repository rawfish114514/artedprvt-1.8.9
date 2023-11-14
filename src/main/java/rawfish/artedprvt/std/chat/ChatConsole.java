package rawfish.artedprvt.std.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.core.Environment;
import rawfish.artedprvt.core.script.ScriptLogger;
import rawfish.artedprvt.core.script.ScriptObject;
import rawfish.artedprvt.core.script.ScriptProcess;

import java.util.List;
import java.util.Objects;

@Solvable
public class ChatConsole implements ScriptObject {
    private ScriptLogger logger=null;
    private GuiNewChat guiNewChat=null;
    private boolean log=true;
    private boolean longtime=false;

    @Solvable
    public ChatConsole(){
        ScriptProcess scriptProcess=up();
        if(scriptProcess!=null){
            logger=scriptProcess.getScriptLogger();
        }
        if(Environment.MCCLIENT){
            Minecraft minecraft=Minecraft.getMinecraft();
            if(Objects.nonNull(minecraft)){
                GuiIngame guiIngame=minecraft.ingameGUI;
                if(Objects.nonNull(guiIngame)){
                    guiNewChat=guiIngame.getChatGUI();
                }
            }
        }
    }

    @Solvable
    public ChatConsole print(ChatComponent chat){
        return print(chat,0);
    }

    @Solvable
    public ChatConsole print(ChatComponent chat,int id){
        if(isGuiNewChatNonnull()){
            printChat(LTM_ChatComponent_IChatComponent(chat),id);
        }
        info(chat.getChatString());

        return this;
    }

    @Solvable
    public ChatConsole print(String chat){
        return print(chat,0);
    }

    @Solvable
    public ChatConsole print(String chat,int id){
        return print(new ChatComponent(String.valueOf(chat)),id);
    }

    @Solvable
    public ChatConsole delete(int id){
        synchronized (this) {
            guiNewChat.deleteChatLine(id);
        }
        return this;
    }

    private void info(String message){
        if(log&&Objects.nonNull(logger)){
            logger.info(message);
        }
    }

    public void setLog(boolean b){
        log=b;
    }

    public void setLongtime(boolean b){
        longtime=b;
    }

    @Override
    public void close() throws Exception {
        if(longtime){
            return;
        }
        logger=null;
        guiNewChat=null;
    }

    private boolean isGuiNewChatNonnull(){
        return Objects.nonNull(guiNewChat);
    }

    private void printChat(IChatComponent chatComponent, int chatLineId){
        synchronized (this) {
            guiNewChat.printChatMessageWithOptionalDeletion(chatComponent, chatLineId);
        }
    }

    private IChatComponent LTM_ChatComponent_IChatComponent(ChatComponent chat) {
        ChatComponentText MsuperChatComponentText=new ChatComponentText("");
        List<ChatStyle> chatStyles=chat.getChatStyles();
        ChatStyle chatStyle;
        for(int i=0;i<chatStyles.size();i++){
            chatStyle=chatStyles.get(i);
            ChatComponentText MchatComponentText=new ChatComponentText(chatStyle.getChat());
            net.minecraft.util.ChatStyle MchatStyle=new net.minecraft.util.ChatStyle();
            if(chatStyle.isClickNonnull()){
                MchatStyle.setChatClickEvent(new LClickEvent(chatStyle.getClick()));
            }
            if(chatStyle.isHoverNonnull()){
                MchatStyle.setChatHoverEvent(new LHoverEvent(chatStyle.getHover()));
            }
            MchatComponentText.setChatStyle(MchatStyle);
            MsuperChatComponentText.appendSibling(MchatComponentText);
        }
        return MsuperChatComponentText;
    }


    private static class LClickEvent extends ClickEvent implements ScriptObject{
        private ChatClick click;
        public LClickEvent(ChatClick click) {
            super(Action.SUGGEST_COMMAND, "");
            up();
            this.click=click;
        }

        @Override
        public String getValue()
        {
            synchronized (this) {
                if(Objects.nonNull(click)) {
                    click.click();
                }
            }
            return "";
        }

        @Override
        public void close() throws Exception {
            synchronized (this){
                click=null;
            }
        }
    }

    private static class LHoverEvent extends HoverEvent implements ScriptObject{
        private ChatHover hover;

        public LHoverEvent(ChatHover hover) {
            super(Action.SHOW_TEXT, null);
            up();
            this.hover=hover;
        }

        @Override
        public IChatComponent getValue()
        {
            synchronized (this) {
                if(Objects.nonNull(hover)) {
                    return new ChatComponentText(hover.hover());
                }
            }
            return new ChatComponentText("");
        }

        @Override
        public void close() throws Exception {
            synchronized (this){
                if(hover instanceof ChatHoverString){
                    return;
                }
                hover=null;
            }
        }
    }
}
