package net.technicpack.mym.client;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class LeafBurstFX extends EntityFX {
    public static IIcon[] leafIcons = new IIcon[4];

    public LeafBurstFX(World world, double x, double y, double z) {
        super(world, x, y, z, 0, 0, 0);

        this.particleScale = 1;
        this.particleIcon = leafIcons[world.rand.nextInt(4)];
    }


    @Override
    public int getFXLayer() {
        return 2;
    }

    public void onUpdate()
    {
        this.motionY -= 0.01;
        super.onUpdate();
    }
}
