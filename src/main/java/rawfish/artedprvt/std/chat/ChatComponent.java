package rawfish.artedprvt.std.chat;

import rawfish.artedprvt.api.Solvable;

import java.util.ArrayList;
import java.util.List;

@Solvable
public class ChatComponent {
    private List<ChatStyle> chatStyles=new ArrayList<>();

    @Solvable
    public ChatComponent(){

    }

    @Solvable
    public ChatComponent(String chat){
        add(chat);
    }

    @Solvable
    public ChatComponent(String chat,String hover){
        add(chat,hover);
    }

    @Solvable
    public ChatComponent(ChatStyle chatStyle){
        add(chatStyle);
    }

    @Solvable
    public ChatComponent add(String chat){
        return add(new ChatStyle(chat));
    }

    @Solvable
    public ChatComponent add(String chat,String hover){
        return add(new ChatStyle(chat,hover));
    }

    @Solvable
    public ChatComponent add(ChatStyle chatStyle){
        chatStyles.add(chatStyle);
        return this;
    }

    @Solvable
    public List<ChatStyle> getChatStyles(){
        return chatStyles;
    }

    @Solvable
    public String getChatString(){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<chatStyles.size();i++){
            sb.append(chatStyles.get(i).getChat());
        }
        return sb.toString();
    }
}
