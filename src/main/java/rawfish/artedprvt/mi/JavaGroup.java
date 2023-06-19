package rawfish.artedprvt.mi;

import java.util.ArrayList;
import java.util.List;

public class JavaGroup {
    private static List<Class> javaGroup=new ArrayList<Class>(){{
        add(ChatProvider.class);
        add(EventFunction.class);
        add(EventListener.class);
        add(Events.class);
        add(GameClient.class);
        add(GameServer.class);
        add(PrintChat.class);
        add(WorldGraphics.class);
        add(WorldManager.class);
    }};

    public static List<Class> getJavaGroup(){
        return javaGroup;
    }

    public static String getName(){
        return "mi";
    }
}
