package rawfish.artedprvt.std.client;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.world.World;

@Solvable
public class ClientWorld extends World {
    public ClientWorld(net.minecraft.client.multiplayer.WorldClient MworldClient){
        super(MworldClient);
    }
}
