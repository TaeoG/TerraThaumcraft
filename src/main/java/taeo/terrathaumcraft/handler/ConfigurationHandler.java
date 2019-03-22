/*
 *     Copyright 2019 TaeoGDev
 *
 *     This file is part of TerraThaumcraft.
 *
 *     TerraThaumcraft is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     TerraThaumcraft is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with TerraThaumcraft.  If not, see <https://www.gnu.org/licenses/>.
 */

package taeo.terrathaumcraft.handler;

import java.io.File;


import taeo.terrathaumcraft.reference.Names;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler 
{
	public static Configuration config;
	
	public static void init(File configFile)
	{
		if (config==null)
		{
			config = new Configuration(configFile);
			loadConfiguration();
		}

	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.modID.equalsIgnoreCase(ReferenceTTC.MOD_ID))
		{
			loadConfiguration();	
		}
	}
	
	private static void loadConfiguration()
	{

		config.setCategoryComment(Names.Categories.WORLD_GEN, "This is where the magic happens");
		config.setCategoryComment(Names.Categories.OREGEN, "Rarity of ore generation. 1 in x chunks should have a vein");
		config.setCategoryComment(Names.Categories.INFUSED_OREGEN, "Properties of all Infused Ores");
		config.setCategoryComment(Names.Categories.VEGGEN, "Generation of magical vegetation");
		config.setCategoryComment(Names.Categories.RECIPES, "Recipe adjustments");

		ReferenceTTC.enableWorldGen = config.getBoolean("Enable World Gen", Names.Categories.WORLD_GEN,true,"Enable the generation of magic biomes and Thaumcraft Ores.");
		//ReferenceTTC.magicOreRarity = config.getInt("Infused Ore Rarity", Names.Categories.WORLD_GEN, 20, 1, 200, "The rarity of Infused Stone. 1 in x chunks should have a vein");
		ReferenceTTC.oreFailureLimit = config.getBoolean("Enable forced ore", Names.Categories.INFUSED_OREGEN, false, "Enabling this limits the amount of times the random roll to produce an ore vein can fail");
		ReferenceTTC.oreFailureLimitMulti = config.getFloat("Forced Ore Multiplier", Names.Categories.INFUSED_OREGEN, 1.0f, 1.0f, 10.0f, "Amount of times failure is allowed, as a multiple of rarity. ie an ore with rarity 40 and a multiplier of 1.5 can only fail its roll 60 times before an ore spawn is forced");
		ReferenceTTC.airOreRarity = config.getInt("Air Infused", 		Names.Categories.OREGEN, 100, 1, 1000, "Ore Rarity");
		ReferenceTTC.earthOreRarity = config.getInt("Earth Infused", 	Names.Categories.OREGEN, 100, 1, 1000, "Ore Rarity");
		ReferenceTTC.waterOreRarity = config.getInt("Water Infused", 	Names.Categories.OREGEN, 100, 1, 1000, "Ore Rarity");
		ReferenceTTC.fireOreRarity = config.getInt("Fire Infused", 	Names.Categories.OREGEN, 100, 1, 1000, "Ore Rarity");
		ReferenceTTC.orderOreRarity = config.getInt("Order Infused", 	Names.Categories.OREGEN, 100, 1, 1000, "Ore Rarity");
		ReferenceTTC.chaosOreRarity = config.getInt("Chaos Infused", 	Names.Categories.OREGEN, 100, 1, 1000, "Ore Rarity");
		ReferenceTTC.amberOreRarity = config.getInt("Amber", 			Names.Categories.OREGEN, 100, 1, 1000, "Ore Rarity");
		ReferenceTTC.cinnabarOreRarity = config.getInt("Cinnabar", 	Names.Categories.OREGEN, 100, 1, 1000, "Ore Rarity");

		ReferenceTTC.removeOrigRecipes = config.getBoolean("Remove Old Recipes", Names.Categories.RECIPES, true, "Remove the original recipes.");
		ReferenceTTC.balancedShardMelt = config.getFloat("Balanced Shard Melting Point", Names.Categories.RECIPES, 950f, 100, 10000, "Temp at which Balanced Shards smelt into Salis Mundus");
		ReferenceTTC.balancedShardSHC = config.getFloat("Balanced Shard Specific Heat Capacity", Names.Categories.RECIPES, 0.35f, 0.1f, 10f, "SHC of Balanced Shards");


		ReferenceTTC.greatwoodTreeRarity = config.getInt("Greatwood Trees", Names.Categories.VEGGEN, 25, 1, 1000, "Spawn Rarity (1 in x chunks)");
		ReferenceTTC.greatwoodSaplingsDrop = config.getBoolean("Greatwood Sapling", Names.Categories.VEGGEN, true, "Allow greatwood saplings to drop");
		if(config.hasChanged())
		{
			config.save();
		}
	}
}
