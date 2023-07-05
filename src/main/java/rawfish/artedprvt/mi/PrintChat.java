package rawfish.artedprvt.mi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import rawfish.artedprvt.core.ScriptObject;
import rawfish.artedprvt.core.ProcedureUsable;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

/**
 * 聊天消息打印
 */
@ProcedureUsable
public class PrintChat{
    public GuiNewChat guiNewChat;

    @ProcedureUsable
    public PrintChat(){
        guiNewChat=Minecraft.getMinecraft().ingameGUI.getChatGUI();
    }

    /**
     * 打印到聊天栏
     * 但他们只对自己是可见的
     * @param chat 聊天信息字符串
     */
    @ProcedureUsable
    public void print(String chat){
        guiNewChat.printChatMessage(new ChatComponentText(chat));
    }

    /**
     * 打印到聊天栏
     * 但他们只对自己是可见的
     * @param chat 聊天信息字符串
     * @param hover 悬浮信息字符串
     */
    @ProcedureUsable
    public void print(String chat,String hover){
        ChatComponentText chatComponentText=new ChatComponentText(chat);
        ChatComponentText hoverComponent=new ChatComponentText(hover);
        chatComponentText.setChatStyle(new ChatStyle()
                .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,hoverComponent))
                .setChatClickEvent(new CopyEvent(hoverComponent))
        );
        guiNewChat.printChatMessage(chatComponentText);
    }

    /**
     * 打印到聊天栏
     * 但他们只对自己是可见的
     * @param chat 聊天信息字符串
     * @param hover 悬浮信息供应商
     */
    @ProcedureUsable
    public void print(String chat, ChatProvider hover){
        ChatComponentText chatComponentText=new ChatComponentText(chat);
        ChatComponentText hoverComponent=new ChatProviderComponent(hover);
        chatComponentText.setChatStyle(new ChatStyle()
                .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,hoverComponent))
                .setChatClickEvent(new CopyEvent(hoverComponent))
        );
        guiNewChat.printChatMessage(chatComponentText);
    }

    public static class ChatProviderComponent extends ChatComponentText implements ScriptObject {
        private ChatProvider provider;
        private String str;
        public ChatProviderComponent(ChatProvider provider){
            super("");
            up();
            this.provider=provider;
            if(provider==null){
                str="null";
            }else{
                str=provider.getChat();
            }
        }

        public String getUnformattedTextForChat(){
            try{
                String ts;
                if(provider!=null){
                    ts=provider.getChat();
                    if(ts!=null){
                        str=ts;
                    }
                }
                return str;
            }catch (Throwable e){
                e.printStackTrace(System.err);
                return str;
            }
        }

        @Override
        public void onClose() {
            provider=null;
        }
    }

    /**
     * 点击复制事件
     */
    public static class CopyEvent extends ClickEvent {
        public ChatComponentText chatComponents;
        public CopyEvent(ChatComponentText chatComponents) {
            super(null, null);
            this.chatComponents=chatComponents;
        }

        @Override
        public ClickEvent.Action getAction(){
            set(chatComponents.getUnformattedTextForChat().replaceAll("\u00a7[0-9a-fk-or]",""));
            return null;
        }

        public void set(String str){
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(str),null);
        }
    }
}
