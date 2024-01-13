package rawfish.artedprvt.std.minecraft.world;

import net.minecraft.entity.player.EntityPlayer;
import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.minecraft.entity.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

@Solvable
public class World {
    protected net.minecraft.world.World Mworld;

    public World(net.minecraft.world.World Mworld) {
        this.Mworld = Mworld;
    }

    @Solvable
    public List<PlayerEntity> getPlayerEntityList() {
        List<EntityPlayer> MplayerEntities = Mworld.playerEntities;
        List<PlayerEntity> playerEntityList = new ArrayList<>();
        for (EntityPlayer MentityPlayer : MplayerEntities) {
            playerEntityList.add(new PlayerEntity(MentityPlayer));
        }
        return playerEntityList;
    }
}
