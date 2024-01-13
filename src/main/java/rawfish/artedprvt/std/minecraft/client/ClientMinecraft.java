package rawfish.artedprvt.std.minecraft.client;

import net.minecraft.client.Minecraft;
import rawfish.artedprvt.api.Solvable;

@Solvable
public class ClientMinecraft {
    private Minecraft minecraft;

    @Solvable
    public ClientMinecraft() {
        minecraft = Minecraft.getMinecraft();
    }

    @Solvable
    public ClientPlayerEntity getPlayer() {
        return new ClientPlayerEntity(minecraft.thePlayer);
    }

    @Solvable
    public ClientWorld getWorld() {
        return new ClientWorld(minecraft.theWorld);
    }
}
