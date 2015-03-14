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
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.technicpack.mym.entities.CatchMobYoinkEntity;
import net.technicpack.mym.entities.ClientEffectEntity;
import net.technicpack.mym.entities.ReleaseMobYoinkEntity;
import net.technicpack.mym.items.YoinkBall;

import java.util.LinkedList;
import java.util.List;

@Mod(modid = MoveYourMobs.MODID, version = MoveYourMobs.VERSION)
public class MoveYourMobs
{
    public static final String MODID = "mym";
    public static final String VERSION = "1.0";

    public static List<String> mobBlacklist = new LinkedList<String>();
    public static List<String> mobWhitelist = new LinkedList<String>();

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        String whitelist = config.getString("Mob Whitelist", Configuration.CATEGORY_GENERAL, "", "A list of mobs that aren't EntityAnimals that should be yoinkable.");
        String blacklist = config.getString("Mob Blacklist", Configuration.CATEGORY_GENERAL, "", "A list of mobs that are EntityAnimals that shouldn't be yoinkable.");
        config.save();

        if (whitelist != null && !whitelist.isEmpty()) {
            String[] whitelistTokens = whitelist.split(",");
            for (String token : whitelistTokens)
                mobWhitelist.add(token);
        }

        if (blacklist != null && !blacklist.isEmpty()) {
            String[] blacklistTokens = blacklist.split(",");
            for (String token : blacklistTokens)
                mobBlacklist.add(token);
        }

        Item ball = new YoinkBall().setUnlocalizedName("yoinkball").setTextureName("yoinkballempty").setCreativeTab(CreativeTabs.tabTransport).setMaxStackSize(1);
        GameRegistry.registerItem(ball, "yoinkball");

        GameRegistry.addRecipe(new ItemStack(ball), "XXX","AOA","XXX",'X', Items.iron_ingot,'A',Items.redstone,'O',Items.slime_ball);

        EntityRegistry.registerModEntity(CatchMobYoinkEntity.class, "yoinkCatchItem", 0, MoveYourMobs.MODID, 250, 5, true);
        EntityRegistry.registerModEntity(ReleaseMobYoinkEntity.class, "yoinkReleaseItem", 1, MoveYourMobs.MODID, 250, 5, true);
        EntityRegistry.registerModEntity(ClientEffectEntity.class, "yoinkEffect", 2, MoveYourMobs.MODID, 250, 5, true);
    }

    public static boolean isYoinkable(EntityLiving entity) {
        String name = EntityList.getEntityString(entity);

        if (mobWhitelist.contains(name))
            return true;
        if (entity instanceof EntityAnimal && !mobBlacklist.contains(name))
            return true;
        return false;
    }
}
