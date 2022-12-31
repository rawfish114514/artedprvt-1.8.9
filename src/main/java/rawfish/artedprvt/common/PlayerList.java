package rawfish.artedprvt.common;

import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;

public class PlayerList extends HashMap<String, EntityPlayer> {
    public static PlayerList pl=new PlayerList();
    private PlayerList(){}
}
