package rawfish.artedprvt.std.minecraft.entity;

import net.minecraft.entity.player.EntityPlayer;
import rawfish.artedprvt.api.Solvable;

@Solvable
public class PlayerEntity extends Entity {
    public PlayerEntity(EntityPlayer MentityPlayer) {
        super(MentityPlayer);
    }
}
