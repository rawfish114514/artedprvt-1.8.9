package rawfish.artedprvt.std.minecraft.client;

import net.minecraft.client.entity.EntityPlayerSP;
import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.minecraft.entity.PlayerEntity;

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
