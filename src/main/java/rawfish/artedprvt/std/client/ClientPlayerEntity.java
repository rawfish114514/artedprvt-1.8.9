package rawfish.artedprvt.std.client;

import net.minecraft.client.entity.EntityPlayerSP;
import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.entity.PlayerEntity;

@Solvable
public class ClientPlayerEntity extends PlayerEntity {
    public ClientPlayerEntity(EntityPlayerSP MentityPlayerSP) {
        super(MentityPlayerSP);
    }

    @Solvable
    public ClientNetwork getNetwork(){
        return new ClientNetwork(((EntityPlayerSP)Mentity).sendQueue);
    }
}
