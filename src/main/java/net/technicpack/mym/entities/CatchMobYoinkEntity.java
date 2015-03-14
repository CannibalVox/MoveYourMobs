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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.technicpack.mym.MoveYourMobs;

public class CatchMobYoinkEntity extends EntityItem {
    boolean catchComplete = false;

    public CatchMobYoinkEntity(World world) {
        super(world);
    }

    public CatchMobYoinkEntity(World world, double x, double y, double z, ItemStack itemStack) {
        super(world, x, y, z, itemStack);
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer p_70100_1_) {
        if (!this.worldObj.isRemote) {
            if (!catchComplete) {
                return;
            }
        }
        super.onCollideWithPlayer(p_70100_1_);
    }

    @Override
    protected void fall(float p_70069_1_) {
        if (!worldObj.isRemote) {
            //We've hit the ground, let's go to work!
            EntityLiving bestAnimal = null;
            double bestAnimalSquaredDistance = 0;
            for (Object entity : worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(5, 5, 5))) {
                if (entity instanceof EntityLiving && MoveYourMobs.isYoinkable((EntityLiving)entity)) {
                    EntityLiving animal = (EntityLiving) entity;
                    double xDist = this.posX - animal.posX;
                    double yDist = this.posY - animal.posY;
                    double zDist = this.posZ - animal.posZ;
                    double distSquared = xDist * xDist + yDist * yDist + zDist * zDist;

                    if (bestAnimal == null || distSquared < bestAnimalSquaredDistance) {
                        bestAnimal = animal;
                        bestAnimalSquaredDistance = distSquared;
                    }
                }
            }

            if (bestAnimal == null) {
                catchComplete = true;
                return;
            }

            getEntityItem().setItemDamage(1);

            NBTTagCompound rootTag = new NBTTagCompound();
            rootTag.setString("TypeName", EntityList.getEntityString(bestAnimal));

            NBTTagCompound entityData = new NBTTagCompound();
            bestAnimal.writeEntityToNBT(entityData);
            rootTag.setTag("EntityData", entityData);

            getEntityItem().setTagCompound(rootTag);

            bestAnimal.setDead();
            ClientEffectEntity effect = new ClientEffectEntity(worldObj);
            effect.setPosition(this.posX, this.posY, this.posZ);
            worldObj.spawnEntityInWorld(effect);
            catchComplete = true;
        }
    }
}
