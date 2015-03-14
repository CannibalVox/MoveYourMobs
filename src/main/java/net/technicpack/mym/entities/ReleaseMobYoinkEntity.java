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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.technicpack.mym.MoveYourMobs;

public class ReleaseMobYoinkEntity extends CatchMobYoinkEntity {
    public ReleaseMobYoinkEntity(World world) {
        super(world);
    }

    public ReleaseMobYoinkEntity(World world, double x, double y, double z, ItemStack itemStack) {
        super(world, x, y, z, itemStack);
    }

    @Override
    protected void fall(float p_70069_1_) {
        if (!worldObj.isRemote) {
            Entity entity = EntityList.createEntityByName(getEntityItem().getTagCompound().getString("TypeName"), worldObj);

            if (!(entity instanceof EntityLiving) || !MoveYourMobs.isYoinkable((EntityLiving)entity)) {
                //How did this happen?!
                entity.setDead();
                this.setDead();
                return;
            }
            NBTTagCompound entityData = getEntityItem().getTagCompound().getCompoundTag("EntityData");
            EntityLiving animal = (EntityLiving) entity;
            animal.readEntityFromNBT(entityData);
            animal.setLocationAndAngles(this.posX, this.posY, this.posZ, 0, 0);
            worldObj.spawnEntityInWorld(animal);
            this.setDead();
            ClientEffectEntity effect = new ClientEffectEntity(worldObj);
            effect.setPosition(this.posX, this.posY, this.posZ);
            worldObj.spawnEntityInWorld(effect);
        }
    }
}
