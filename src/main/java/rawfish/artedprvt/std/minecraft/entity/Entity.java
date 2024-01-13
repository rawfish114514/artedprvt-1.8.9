package rawfish.artedprvt.std.minecraft.entity;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.math.Vector3;

import java.util.UUID;

@Solvable
public class Entity {
    protected net.minecraft.entity.Entity Mentity;

    public Entity(net.minecraft.entity.Entity Mentity) {
        this.Mentity = Mentity;
    }

    public net.minecraft.entity.Entity getMentity() {
        return Mentity;
    }

    @Solvable
    public int getId() {
        return Mentity.getEntityId();
    }

    @Solvable
    public String getName() {
        return Mentity.getName();
    }

    @Solvable
    public UUID getUUID() {
        return Mentity.getUniqueID();
    }

    @Solvable
    public Vector3 getPosition() {
        return new Vector3(Mentity.posX, Mentity.posY, Mentity.posZ);
    }


}
