/*
 * This file is part of Move Your Mobs
 * Copyright ©2015 Syndicate, LLC
 *
 * Move Your Mobs is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Move Your Mobs is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License,
 * as well as a copy of the GNU Lesser General Public License,
 * along with Technic Launcher Core.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.technicpack.mym.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.technicpack.mym.client.LeafBurstFX;

public class ClientEffectEntity extends Entity {
    int serverLife = 0;
    public ClientEffectEntity(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    @Override
    public void onUpdate() {
        if (worldObj.isRemote) {
            for (int i = 0; i < 40; i++) {
                LeafBurstFX fx = new LeafBurstFX(worldObj, posX + (this.rand.nextDouble() - 0.5D) * 2,
                        posY + this.rand.nextDouble() * 2,
                        posZ + (this.rand.nextDouble() - 0.5D) * 2);
                Minecraft.getMinecraft().effectRenderer.addEffect(fx);
            }

            for (int i = 0; i < 40; i++) {
                LeafBurstFX fx = new LeafBurstFX(worldObj, posX + (this.rand.nextDouble() - 0.5D),
                        posY + this.rand.nextDouble(),
                        posZ + (this.rand.nextDouble() - 0.5D));
                Minecraft.getMinecraft().effectRenderer.addEffect(fx);
            }
            setDead();
        } else if (serverLife++ >= 20)
            setDead();
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {

    }
}
