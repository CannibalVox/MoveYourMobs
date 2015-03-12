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

package net.technicpack.mym;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.technicpack.mym.items.YoinkBall;

@Mod(modid = MoveYourMobs.MODID, version = MoveYourMobs.VERSION)
public class MoveYourMobs
{
    public static final String MODID = "mym";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        Item ball = new YoinkBall().setUnlocalizedName("yoinkball").setTextureName("yoinkballempty").setCreativeTab(CreativeTabs.tabTransport).setMaxStackSize(1);
        GameRegistry.registerItem(ball, "yoinkball");

        GameRegistry.addRecipe(new ItemStack(ball), "XXX","AOA","XXX",'X', Items.iron_ingot,'A',Items.redstone,'O',Items.slime_ball);
    }
}
