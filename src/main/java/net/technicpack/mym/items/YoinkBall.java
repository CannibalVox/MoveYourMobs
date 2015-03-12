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

package net.technicpack.mym.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.technicpack.mym.entities.CatchMobYoinkEntity;
import net.technicpack.mym.entities.ReleaseMobYoinkEntity;

import java.util.List;

public class YoinkBall extends Item {
    @Override
    public boolean hasCustomEntity(ItemStack stack)
    {
        return true;
    }

    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack)
    {
        CatchMobYoinkEntity entity = null;
        if (itemstack.getItemDamage() != 0 && itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("TypeName", 8))
            entity = new ReleaseMobYoinkEntity(world, location.posX, location.posY, location.posZ, itemstack);
        else
            entity = new CatchMobYoinkEntity(world, location.posX, location.posY, location.posZ, itemstack);

        entity.motionX = location.motionX;
        entity.motionY = location.motionY;
        entity.motionZ = location.motionZ;

        if (location instanceof EntityItem) {
            EntityItem itemLoc = (EntityItem)location;
            entity.delayBeforeCanPickup = ((EntityItem) location).delayBeforeCanPickup;
            entity.func_145799_b(itemLoc.func_145800_j());
        }
        return entity;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List tooltipText, boolean advancedTooltips) {
        if (itemStack.getItemDamage() == 1 && itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("TypeName", 8)) {
            String computerName = itemStack.getTagCompound().getString("TypeName");
            String peopleName = StatCollector.translateToLocal("entity."+computerName+".name");
            String coloredName = "\u00A7e" + peopleName + "\u00A77";
            tooltipText.add(StatCollector.translateToLocalFormatted("info.fullball",coloredName));
        }
    }
}
