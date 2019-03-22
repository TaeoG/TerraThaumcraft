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

package taeo.terrathaumcraft;

import com.bioxx.tfc.Core.Metal.MetalRegistry;
import com.bioxx.tfc.Food.ItemMeal;
import com.bioxx.tfc.Food.ItemSandwich;
import com.bioxx.tfc.Items.ItemIngot;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.*;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;


import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.terrathaumcraft.init.TTCItems;
import taeo.terrathaumcraft.reference.ReferenceTTC;

import taeo.ttfcapi.init.TAPIBlocks;
import taeo.ttfcmat.init.TMatItems;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

import java.util.ArrayList;
import java.util.Arrays;

public class TTCAspects {

	private static int wild = OreDictionary.WILDCARD_VALUE;
	public static void registerEntities()
	{
		ThaumcraftApi.registerEntityTag("zombieTFC", new AspectList().add(Aspect.UNDEAD, 2).add(Aspect.MAN, 1).add(Aspect.EARTH, 1), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("skeletonTFC", new AspectList().add(Aspect.UNDEAD, 3).add(Aspect.MAN, 1).add(Aspect.EARTH, 1), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("creeperTFC", new AspectList().add(Aspect.PLANT, 2).add(Aspect.FIRE, 2), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("horseTFC", new AspectList().add(Aspect.BEAST, 4).add(Aspect.EARTH, 1).add(Aspect.AIR, 1), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("pigTFC", new AspectList().add(Aspect.BEAST, 2).add(Aspect.EARTH, 2), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("sheepTFC", new AspectList().add(Aspect.BEAST, 2).add(Aspect.EARTH, 2), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("cowTFC", new AspectList().add(Aspect.BEAST, 3).add(Aspect.EARTH, 3), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("chickenTFC", new AspectList().add(Aspect.BEAST, 2).add(Aspect.FLIGHT, 2).add(Aspect.AIR, 1), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("squidTFC", new AspectList().add(Aspect.BEAST, 2).add(Aspect.WATER, 2), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("wolfTFC", new AspectList().add(Aspect.BEAST, 3).add(Aspect.EARTH, 3), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("spiderTFC", new AspectList().add(Aspect.BEAST, 3).add(Aspect.ENTROPY, 2), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("slimeTFC", new AspectList().add(Aspect.SLIME, 2).add(Aspect.WATER, 2), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("ghastTFC", new AspectList().add(Aspect.UNDEAD, 3).add(Aspect.FIRE, 2), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("pigZombieTFC", new AspectList().add(Aspect.UNDEAD, 4).add(Aspect.FIRE, 2), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("endermanTFC", new AspectList().add(Aspect.ELDRITCH, 4).add(Aspect.TRAVEL, 2).add(Aspect.AIR, 2), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("caveSpiderTFC", new AspectList().add(Aspect.BEAST, 2).add(Aspect.POISON, 2).add(Aspect.EARTH, 1), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("silverfishTFC", new AspectList().add(Aspect.BEAST, 1).add(Aspect.EARTH, 1), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("blazeTFC", new AspectList().add(Aspect.ELDRITCH, 4).add(Aspect.FIRE, 1), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("bearTFC", new AspectList().add(Aspect.BEAST, 5), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("fishTFC", new AspectList().add(Aspect.BEAST, 1).add(Aspect.WATER, 1), new ThaumcraftApi.EntityTagsNBT[0]);
		ThaumcraftApi.registerEntityTag("pheasantTFC", new AspectList().add(Aspect.BEAST, 2).add(Aspect.FLIGHT, 3), new ThaumcraftApi.EntityTagsNBT[0]);

	}

	public static boolean removeAspects(String oredict)
	{
		ArrayList ores = OreDictionary.getOres(oredict);
		if(ores != null && ores.size() > 0)
		{
			for(ItemStack stack : (ArrayList<ItemStack>)ores)
			{
				removeAspects(stack);
			}
		}
		return false;
	}
	public static boolean removeAspects(ItemStack stack)
	{

		ThaumcraftApi.objectTags.remove(Arrays.asList(new Object[]{stack.getItem(), stack.getItemDamage()}));
		return false;
	}
	public static void registerItems()
	{
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.blockMagicOre,1, ReferenceTTC.OreMeta.AIR), new AspectList().add(Aspect.AIR,3).add(Aspect.EARTH,1).add(Aspect.CRYSTAL,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.blockMagicOre,1, ReferenceTTC.OreMeta.AMBER), new AspectList().add(Aspect.EARTH, 1).add(Aspect.TRAP, 3).add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.blockMagicOre,1, ReferenceTTC.OreMeta.ENTROPY), new AspectList().add(Aspect.ENTROPY, 3).add(Aspect.EARTH, 1).add(Aspect.CRYSTAL,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.blockMagicOre,1, ReferenceTTC.OreMeta.CINNABAR), new AspectList().add(Aspect.METAL,2).add(Aspect.EXCHANGE, 2).add(Aspect.EARTH, 1).add(Aspect.POISON,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.blockMagicOre,1, ReferenceTTC.OreMeta.EARTH), new AspectList().add(Aspect.EARTH, 4).add(Aspect.CRYSTAL,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.blockMagicOre,1, ReferenceTTC.OreMeta.FIRE), new AspectList().add(Aspect.FIRE,3).add(Aspect.EARTH, 1).add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.blockMagicOre,1, ReferenceTTC.OreMeta.ORDER), new AspectList().add(Aspect.ORDER,3).add(Aspect.EARTH, 1).add(Aspect.CRYSTAL,2));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.blockMagicOre,1, ReferenceTTC.OreMeta.QUARTZ), new AspectList().add(Aspect.EARTH, 1).add(Aspect.CRYSTAL, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.blockMagicOre,1, ReferenceTTC.OreMeta.WATER), new AspectList().add(Aspect.WATER, 3).add(Aspect.EARTH, 1).add(Aspect.CRYSTAL, 2));
		//System.out.println("Registering Aspects Chunk of Gems");
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemRuby,1,0), new AspectList().add(Aspect.CRYSTAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemRuby,1,1), new AspectList().add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemRuby,1,2), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.GREED, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemRuby,1,3), new AspectList().add(Aspect.CRYSTAL, 6).add(Aspect.GREED, 6));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemRuby,1,4), new AspectList().add(Aspect.CRYSTAL, 8).add(Aspect.GREED, 8));
		
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemSapphire,1,0), new AspectList().add(Aspect.CRYSTAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemSapphire,1,1), new AspectList().add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemSapphire,1,2), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.GREED, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemSapphire,1,3), new AspectList().add(Aspect.CRYSTAL, 6).add(Aspect.GREED, 6));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemSapphire,1,4), new AspectList().add(Aspect.CRYSTAL, 8).add(Aspect.GREED, 8));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemEmerald,1,0), new AspectList().add(Aspect.CRYSTAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemEmerald,1,1), new AspectList().add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemEmerald,1,2), new AspectList().add(Aspect.CRYSTAL, 4).add(Aspect.GREED, 5));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemEmerald,1,3), new AspectList().add(Aspect.CRYSTAL, 8).add(Aspect.GREED, 10));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemEmerald,1,4), new AspectList().add(Aspect.CRYSTAL, 12).add(Aspect.GREED, 15));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemTopaz,1,0), new AspectList().add(Aspect.CRYSTAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemTopaz,1,1), new AspectList().add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemTopaz,1,2), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.GREED, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemTopaz,1,3), new AspectList().add(Aspect.CRYSTAL, 6).add(Aspect.GREED, 6));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemTopaz,1,4), new AspectList().add(Aspect.CRYSTAL, 8).add(Aspect.GREED, 8));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemGarnet,1,0), new AspectList().add(Aspect.CRYSTAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemGarnet,1,1), new AspectList().add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemGarnet,1,2), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.GREED, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemGarnet,1,3), new AspectList().add(Aspect.CRYSTAL, 6).add(Aspect.GREED, 6));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemGarnet,1,4), new AspectList().add(Aspect.CRYSTAL, 8).add(Aspect.GREED, 8));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemOpal,1,0), new AspectList().add(Aspect.CRYSTAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemOpal,1,1), new AspectList().add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemOpal,1,2), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.GREED, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemOpal,1,3), new AspectList().add(Aspect.CRYSTAL, 6).add(Aspect.GREED, 6));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemOpal,1,4), new AspectList().add(Aspect.CRYSTAL, 8).add(Aspect.GREED, 8));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemAmethyst,1,0), new AspectList().add(Aspect.CRYSTAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemAmethyst,1,1), new AspectList().add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemAmethyst,1,2), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.GREED, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemAmethyst,1,3), new AspectList().add(Aspect.CRYSTAL, 6).add(Aspect.GREED, 6));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemAmethyst,1,4), new AspectList().add(Aspect.CRYSTAL, 8).add(Aspect.GREED, 8));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemJasper,1,0), new AspectList().add(Aspect.CRYSTAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemJasper,1,1), new AspectList().add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemJasper,1,2), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.GREED, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemJasper,1,3), new AspectList().add(Aspect.CRYSTAL, 6).add(Aspect.GREED, 6));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemJasper,1,4), new AspectList().add(Aspect.CRYSTAL, 8).add(Aspect.GREED, 8));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemBeryl,1,0), new AspectList().add(Aspect.CRYSTAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemBeryl,1,1), new AspectList().add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemBeryl,1,2), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.GREED, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemBeryl,1,3), new AspectList().add(Aspect.CRYSTAL, 6).add(Aspect.GREED, 6));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemBeryl,1,4), new AspectList().add(Aspect.CRYSTAL, 8).add(Aspect.GREED, 8));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemTourmaline,1,0), new AspectList().add(Aspect.CRYSTAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemTourmaline,1,1), new AspectList().add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemTourmaline,1,2), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.GREED, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemTourmaline,1,3), new AspectList().add(Aspect.CRYSTAL, 6).add(Aspect.GREED, 6));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemTourmaline,1,4), new AspectList().add(Aspect.CRYSTAL, 8).add(Aspect.GREED, 8));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemJade,1,0), new AspectList().add(Aspect.CRYSTAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemJade,1,1), new AspectList().add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemJade,1,2), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.GREED, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemJade,1,3), new AspectList().add(Aspect.CRYSTAL, 6).add(Aspect.GREED, 6));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemJade,1,4), new AspectList().add(Aspect.CRYSTAL, 8).add(Aspect.GREED, 8));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemAgate,1,0), new AspectList().add(Aspect.CRYSTAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemAgate,1,1), new AspectList().add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemAgate,1,2), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.GREED, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemAgate,1,3), new AspectList().add(Aspect.CRYSTAL, 6).add(Aspect.GREED, 6));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemAgate,1,4), new AspectList().add(Aspect.CRYSTAL, 8).add(Aspect.GREED, 8));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemDiamond,1,0), new AspectList().add(Aspect.CRYSTAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemDiamond,1,1), new AspectList().add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemDiamond,1,2), new AspectList().add(Aspect.CRYSTAL, 4).add(Aspect.GREED, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemDiamond,1,3), new AspectList().add(Aspect.CRYSTAL, 8).add(Aspect.GREED, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gemDiamond,1,4), new AspectList().add(Aspect.CRYSTAL, 12).add(Aspect.GREED, 12));

		//System.out.println("Registering Aspects of Ingots");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthIngot), new AspectList().				add(Aspect.METAL, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeIngot), new AspectList().		add(Aspect.METAL, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeIngot), new AspectList().			add(Aspect.METAL, 3).add(Aspect.DARKNESS, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelIngot), new AspectList().			add(Aspect.METAL, 3).add(Aspect.DARKNESS, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.highCarbonBlackSteelIngot), new AspectList().add(Aspect.METAL, 3).add(Aspect.DARKNESS, 1).add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelIngot), new AspectList().			add(Aspect.METAL, 4).add(Aspect.COLD, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.weakBlueSteelIngot), new AspectList().		add(Aspect.METAL, 3).add(Aspect.ENTROPY, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.highCarbonBlueSteelIngot), new AspectList().	add(Aspect.METAL, 3).add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.brassIngot), new AspectList().				add(Aspect.METAL, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeIngot), new AspectList().				add(Aspect.METAL, 4));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperIngot), new AspectList().				add(Aspect.METAL, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.goldIngot), new AspectList().				add(Aspect.METAL, 3).add(Aspect.GREED, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronIngot), new AspectList().			add(Aspect.METAL, 4));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.leadIngot), new AspectList().				add(Aspect.METAL, 3).add(Aspect.POISON, 1));
		//ThaumcraftApi.objectTags.remove("ingotLead");
		//removeAspects("ingotLead");
		//removeAspects(new ItemStack(TFCItems.leadIngot));
		ThaumcraftApi.registerObjectTag("ingotLead", (new AspectList()).add(Aspect.METAL, 3).add(Aspect.POISON, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.nickelIngot), new AspectList().				add(Aspect.METAL, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.pigIronIngot), new AspectList().				add(Aspect.METAL, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.platinumIngot), new AspectList().			add(Aspect.METAL, 4).add(Aspect.FLIGHT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelIngot), new AspectList().			add(Aspect.METAL, 4).add(Aspect.GREED,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.weakRedSteelIngot), new AspectList().		add(Aspect.METAL, 3).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.highCarbonRedSteelIngot), new AspectList().	add(Aspect.METAL, 3).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.roseGoldIngot), new AspectList().			add(Aspect.METAL, 4).add(Aspect.GREED, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.silverIngot), new AspectList().				add(Aspect.METAL, 4).add(Aspect.GREED, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelIngot), new AspectList().				add(Aspect.METAL, 4).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.weakSteelIngot), new AspectList().			add(Aspect.METAL, 3).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.highCarbonSteelIngot), new AspectList().		add(Aspect.METAL, 3).add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sterlingSilverIngot), new AspectList().		add(Aspect.METAL, 4).add(Aspect.GREED, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.tinIngot), new AspectList().					add(Aspect.METAL, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.zincIngot), new AspectList().				add(Aspect.METAL, 4));
		//System.out.println("Registering Aspects of Double Ingots");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthIngot2x), new AspectList().			add(Aspect.METAL, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeIngot2x), new AspectList().		add(Aspect.METAL, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeIngot2x), new AspectList().		add(Aspect.METAL, 6).add(Aspect.DARKNESS, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelIngot2x), new AspectList().		add(Aspect.METAL, 6).add(Aspect.DARKNESS, 2).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelIngot2x), new AspectList().			add(Aspect.METAL, 8).add(Aspect.COLD,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.brassIngot2x), new AspectList().				add(Aspect.METAL, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeIngot2x), new AspectList().			add(Aspect.METAL, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperIngot2x), new AspectList().			add(Aspect.METAL, 6).add(Aspect.EXCHANGE,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.goldIngot2x), new AspectList().				add(Aspect.METAL, 8).add(Aspect.GREED, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronIngot2x), new AspectList().		add(Aspect.METAL, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.leadIngot2x), new AspectList().				add(Aspect.METAL, 6).add(Aspect.POISON, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.nickelIngot2x), new AspectList().			add(Aspect.METAL, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.pigIronIngot2x), new AspectList().			add(Aspect.METAL, 8).add(Aspect.BEAST,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.platinumIngot2x), new AspectList().			add(Aspect.METAL, 8).add(Aspect.FLIGHT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelIngot2x), new AspectList().			add(Aspect.METAL, 8).add(Aspect.GREED,6));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.roseGoldIngot2x), new AspectList().			add(Aspect.METAL, 8).add(Aspect.GREED, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.silverIngot2x), new AspectList().			add(Aspect.METAL, 8).add(Aspect.GREED, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelIngot2x), new AspectList().				add(Aspect.METAL, 8).add(Aspect.ORDER,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sterlingSilverIngot2x), new AspectList().	add(Aspect.METAL, 8).add(Aspect.GREED, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.tinIngot2x), new AspectList().				add(Aspect.METAL, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.zincIngot2x), new AspectList().				add(Aspect.METAL, 8));
		
		//System.out.println("Registering Aspects Chunk of Stone Shovels, Axes and Hoes");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.igInShovel), new AspectList().				add(Aspect.TREE,1).add(Aspect.TOOL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.igInAxe), new AspectList().					add(Aspect.TREE,1).add(Aspect.TOOL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.igInHoe), new AspectList().					add(Aspect.TREE,1).add(Aspect.HARVEST, 2));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sedShovel), new AspectList().				add(Aspect.TREE,1).add(Aspect.TOOL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sedAxe), new AspectList().					add(Aspect.TREE,1).add(Aspect.TOOL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sedHoe), new AspectList().					add(Aspect.TREE,1).add(Aspect.HARVEST, 2));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.igExShovel), new AspectList().				add(Aspect.TREE,1).add(Aspect.TOOL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.igExAxe), new AspectList().					add(Aspect.TREE,1).add(Aspect.TOOL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.igExHoe), new AspectList().					add(Aspect.TREE,1).add(Aspect.HARVEST, 2));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.mMShovel), new AspectList().					add(Aspect.TREE,1).add(Aspect.TOOL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.mMAxe), new AspectList().					add(Aspect.TREE,1).add(Aspect.TOOL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.mMHoe), new AspectList().					add(Aspect.TREE,1).add(Aspect.HARVEST, 2));

		//System.out.println("Registering Aspects of Metal Picks, Shovels, Axes and Hoes");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzePick), new AspectList().		add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeShovel), new AspectList().		add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeHoe), new AspectList().			add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzePick), new AspectList().			add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeShovel), new AspectList().		add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeHoe), new AspectList().			add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelPick), new AspectList().			add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelShovel), new AspectList().			add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelHoe), new AspectList().			add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelPick), new AspectList().			add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelShovel), new AspectList().			add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelHoe), new AspectList().				add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzePick), new AspectList().				add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeShovel), new AspectList().				add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeHoe), new AspectList().				add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperPick), new AspectList().				add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperShovel), new AspectList().				add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperHoe), new AspectList().				add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronPick), new AspectList().			add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronShovel), new AspectList().		add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronHoe), new AspectList().			add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelPick), new AspectList().				add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelShovel), new AspectList().			add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelHoe), new AspectList().				add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelPick), new AspectList().				add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelShovel), new AspectList().				add(Aspect.TREE, 1).add(Aspect.METAL,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelHoe), new AspectList().					add(Aspect.TREE, 1).add(Aspect.METAL,3));


		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeAxe), new AspectList().			add(Aspect.TOOL, 3).add(Aspect.METAL,3).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeAxe), new AspectList().			add(Aspect.TOOL, 3).add(Aspect.METAL,3).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelAxe), new AspectList().			add(Aspect.TOOL, 4).add(Aspect.METAL,6).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelAxe), new AspectList().				add(Aspect.TOOL, 5).add(Aspect.METAL,6).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeAxe), new AspectList().				add(Aspect.TOOL, 3).add(Aspect.METAL,3).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperAxe), new AspectList().				add(Aspect.TOOL, 3).add(Aspect.METAL,3).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelAxe), new AspectList().				add(Aspect.TOOL, 5).add(Aspect.METAL,6).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelAxe), new AspectList().					add(Aspect.TOOL, 3).add(Aspect.METAL,6).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronAxe), new AspectList().			add(Aspect.TOOL, 3).add(Aspect.METAL,6).add(Aspect.TREE,1));

		//System.out.println("Registering Aspects of Chisels");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeChisel), new AspectList().		add(Aspect.TOOL, 3).add(Aspect.METAL,3).add(Aspect.EXCHANGE, 1).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeChisel), new AspectList().		add(Aspect.TOOL, 3).add(Aspect.METAL,3).add(Aspect.EXCHANGE, 1).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelChisel), new AspectList().			add(Aspect.TOOL, 4).add(Aspect.METAL,6).add(Aspect.EXCHANGE, 1).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelChisel), new AspectList().			add(Aspect.TOOL, 5).add(Aspect.METAL,6).add(Aspect.EXCHANGE, 1).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeChisel), new AspectList().				add(Aspect.TOOL, 3).add(Aspect.METAL,3).add(Aspect.EXCHANGE, 1).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperChisel), new AspectList().				add(Aspect.TOOL, 3).add(Aspect.METAL,3).add(Aspect.EXCHANGE, 1).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronChisel), new AspectList().		add(Aspect.TOOL, 3).add(Aspect.METAL,6).add(Aspect.EXCHANGE, 1).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelChisel), new AspectList().			add(Aspect.TOOL, 5).add(Aspect.METAL,6).add(Aspect.EXCHANGE, 1).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelChisel), new AspectList().				add(Aspect.TOOL, 3).add(Aspect.METAL,6).add(Aspect.EXCHANGE, 1).add(Aspect.TREE,1));

		//System.out.println("Registering Aspects of Swords");
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeSword), new AspectList().		add(Aspect.METAL, 3).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeSword), new AspectList().			add(Aspect.METAL, 3).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelSword), new AspectList().			add(Aspect.METAL, 6).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelSword), new AspectList().			add(Aspect.METAL, 6).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeSword), new AspectList().				add(Aspect.METAL, 3).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperSword), new AspectList().				add(Aspect.METAL, 3).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronSword), new AspectList().			add(Aspect.METAL, 6).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelSword), new AspectList().			add(Aspect.METAL, 6).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelSword), new AspectList().				add(Aspect.METAL, 6).add(Aspect.TREE,1));

		//System.out.println("Registering Aspects of Maces");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeMace), new AspectList().		add(Aspect.METAL, 3).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeMace), new AspectList().			add(Aspect.METAL, 3).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelMace), new AspectList().			add(Aspect.METAL, 6).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelMace), new AspectList().			add(Aspect.METAL, 6).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeMace), new AspectList().				add(Aspect.METAL, 3).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperMace), new AspectList().				add(Aspect.METAL, 3).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronMace), new AspectList().			add(Aspect.METAL, 6).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelMace), new AspectList().				add(Aspect.METAL, 6).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelMace), new AspectList().				add(Aspect.METAL, 6).add(Aspect.TREE,1));

		//System.out.println("Registering Aspects of Saws");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeSaw), new AspectList().			add(Aspect.TOOL, 3).add(Aspect.METAL, 3).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeSaw), new AspectList().			add(Aspect.TOOL, 3).add(Aspect.METAL, 3).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelSaw), new AspectList().			add(Aspect.TOOL, 4).add(Aspect.METAL, 6).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelSaw), new AspectList().				add(Aspect.TOOL, 5).add(Aspect.METAL, 6).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeSaw), new AspectList().				add(Aspect.TOOL, 3).add(Aspect.METAL, 3).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperSaw), new AspectList().				add(Aspect.TOOL, 3).add(Aspect.METAL, 3).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronSaw), new AspectList().			add(Aspect.TOOL, 3).add(Aspect.METAL, 6).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelSaw), new AspectList().				add(Aspect.TOOL, 5).add(Aspect.METAL, 6).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelSaw), new AspectList().					add(Aspect.TOOL, 3).add(Aspect.METAL, 6).add(Aspect.TREE,1));

		//System.out.println("Registering Aspects of Coal, Logs and Loom");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.coal,1,wild), new AspectList().add(Aspect.ENERGY, 2).add(Aspect.FIRE, 2));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.smallOreChunk,1,wild), new AspectList().add(Aspect.EARTH, 1));

		int poorOreAspect =  (int)(((float)TFCOptions.poorOreUnits)/100f * 4f);
		int normalOreAspect = Math.max((int)(((float)TFCOptions.normalOreUnits)/100f * 4f),1);
		int richOreAspect = Math.max((int)(((float)TFCOptions.richOreUnits)/100f * 4f),1);

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,0), new AspectList().						add(Aspect.METAL, normalOreAspect).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,1), new AspectList().						add(Aspect.METAL, normalOreAspect).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,2), new AspectList().						add(Aspect.METAL, normalOreAspect).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,3), new AspectList().						add(Aspect.METAL, normalOreAspect).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,4), new AspectList().						add(Aspect.METAL, normalOreAspect).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,5), new AspectList().						add(Aspect.METAL, normalOreAspect).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,6), new AspectList().						add(Aspect.METAL, normalOreAspect).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,7), new AspectList().						add(Aspect.METAL, normalOreAspect).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,8), new AspectList().						add(Aspect.METAL, normalOreAspect).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,9), new AspectList().						add(Aspect.METAL, normalOreAspect).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,10), new AspectList().					add(Aspect.METAL, normalOreAspect).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,11), new AspectList().					add(Aspect.METAL, normalOreAspect).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,12), new AspectList().					add(Aspect.METAL, normalOreAspect).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,13), new AspectList().					add(Aspect.METAL, normalOreAspect).add(Aspect.EARTH,1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,14), new AspectList().					add(Aspect.ENERGY, 2).add(Aspect.FIRE,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,15), new AspectList().					add(Aspect.ENERGY, 2).add(Aspect.FIRE, 2));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,16), new AspectList().					add(Aspect.WATER, 1).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,17), new AspectList().					add(Aspect.CRYSTAL, 1).add(Aspect.EARTH,1).add(Aspect.WATER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,18), new AspectList().					add(Aspect.CRYSTAL, 1).add(Aspect.EARTH,1).add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,19), new AspectList().					add(Aspect.CRYSTAL, 1).add(Aspect.EARTH,1).add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,20), new AspectList().					add(Aspect.CRYSTAL, 1).add(Aspect.ENERGY,3).add(Aspect.FIRE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,21), new AspectList().					add(Aspect.GREED, 1).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,22), new AspectList().					add(Aspect.TREE, 1).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,23), new AspectList().					add(Aspect.FIRE, 1).add(Aspect.LIFE,1).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,24), new AspectList().					add(Aspect.DARKNESS, 1).add(Aspect.TREE,1).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,25), new AspectList().					add(Aspect.CRYSTAL, 1).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,26), new AspectList().					add(Aspect.POISON, 2).add(Aspect.ENERGY, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,27), new AspectList().					add(Aspect.ENERGY,1).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,28), new AspectList().					add(Aspect.POISON, 1).add(Aspect.CRYSTAL,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,29), new AspectList().					add(Aspect.FIRE, 1).add(Aspect.CRYSTAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,30), new AspectList().					add(Aspect.CRYSTAL, 1).add(Aspect.BEAST,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,31), new AspectList().					add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,32), new AspectList().					add(Aspect.CRYSTAL, 1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,33), new AspectList().					add(Aspect.TRAP, 1).add(Aspect.CRYSTAL,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,34), new AspectList().					add(Aspect.SENSES, 3).add(Aspect.EARTH, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,35), new AspectList().					add(Aspect.METAL, richOreAspect));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,36), new AspectList().					add(Aspect.METAL, richOreAspect));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,37), new AspectList().					add(Aspect.METAL, richOreAspect));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,38), new AspectList().					add(Aspect.METAL, richOreAspect));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,39), new AspectList().					add(Aspect.METAL, richOreAspect));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,40), new AspectList().					add(Aspect.METAL, richOreAspect));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,41), new AspectList().					add(Aspect.METAL, richOreAspect));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,42), new AspectList().					add(Aspect.METAL, richOreAspect));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,43), new AspectList().					add(Aspect.METAL, richOreAspect));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,44), new AspectList().					add(Aspect.METAL, richOreAspect));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,45), new AspectList().					add(Aspect.METAL, richOreAspect));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,46), new AspectList().					add(Aspect.METAL, richOreAspect));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,47), new AspectList().					add(Aspect.METAL, richOreAspect));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,48), new AspectList().					add(Aspect.METAL, richOreAspect));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,49), new AspectList().					add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,50), new AspectList().					add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,51), new AspectList().					add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,52), new AspectList().					add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,53), new AspectList().					add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,54), new AspectList().					add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,55), new AspectList().					add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,56), new AspectList().					add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,57), new AspectList().					add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,58), new AspectList().					add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,59), new AspectList().					add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,60), new AspectList().					add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,61), new AspectList().					add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,62), new AspectList().					add(Aspect.EARTH, 1));

		if(poorOreAspect >= 1)
		{
			for(int i = 49; i<=62; i ++)
			{
				ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oreChunk,1,i), new AspectList().add(Aspect.METAL, poorOreAspect));
			}
		}

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.logs,1,wild), new AspectList()  .add(Aspect.TREE, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.barrel,1,wild), new AspectList().add(Aspect.TREE, 2).add(Aspect.VOID, 4).add(Aspect.EXCHANGE, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.loom,1,wild), new AspectList()  .add(Aspect.TREE, 4));


		//System.out.println("Registering Aspects of Javelins");
/*
		// javelins
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.igInStoneJavelin), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sedStoneJavelin), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.igExStoneJavelin), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.mMStoneJavelin), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperJavelin), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeJavelin), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeJavelin), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeJavelin), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronJavelin), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelJavelin), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelJavelin), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelJavelin), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelJavelin), new AspectList().add(Aspect.EARTH, 1));
*/
		//System.out.println("Registering Aspects of Javelin Heads");

		// javelin heads
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.igInStoneJavelinHead), new AspectList().add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sedStoneJavelinHead), new AspectList().add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.igExStoneJavelinHead), new AspectList().add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.mMStoneJavelinHead), new AspectList().add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperJavelinHead), new AspectList().add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeJavelinHead), new AspectList().add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeJavelinHead), new AspectList().add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeJavelinHead), new AspectList().add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronJavelinHead), new AspectList().add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelJavelinHead), new AspectList().add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelJavelinHead), new AspectList().add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelJavelinHead), new AspectList().add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelJavelinHead), new AspectList().add(Aspect.WEAPON, 1));

		//System.out.println("Registering Aspects of Scythes");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeScythe), new AspectList()   .add(Aspect.TOOL, 1).add(Aspect.HARVEST, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeScythe), new AspectList()     .add(Aspect.TOOL, 1).add(Aspect.HARVEST, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelScythe), new AspectList()      .add(Aspect.TOOL, 1).add(Aspect.HARVEST, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelScythe), new AspectList()       .add(Aspect.TOOL, 1).add(Aspect.HARVEST, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeScythe), new AspectList()          .add(Aspect.TOOL, 1).add(Aspect.HARVEST, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperScythe), new AspectList()          .add(Aspect.TOOL, 1).add(Aspect.HARVEST, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronScythe), new AspectList()     .add(Aspect.TOOL, 1).add(Aspect.HARVEST, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelScythe), new AspectList()        .add(Aspect.TOOL, 1).add(Aspect.HARVEST, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelScythe), new AspectList()           .add(Aspect.TOOL, 1).add(Aspect.HARVEST, 2));

		//System.out.println("Registering Aspects of Knives");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeKnife), new AspectList()    .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeKnife), new AspectList()      .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelKnife), new AspectList()       .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelKnife), new AspectList()        .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeKnife), new AspectList()           .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperKnife), new AspectList()           .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronKnife), new AspectList()      .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelKnife), new AspectList()         .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelKnife), new AspectList()            .add(Aspect.TOOL, 1));

		//System.out.println("Registering Aspects of Firestarter, Fishing Rod and Bow");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.fireStarter), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.fishingRod), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bow), new AspectList().add(Aspect.EARTH, 1));

		//System.out.println("Registering Aspects of Hammers");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.stoneHammer,1,wild), new AspectList()           .add(Aspect.TOOL, 1).add(Aspect.EARTH,1).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeHammer), new AspectList()   .add(Aspect.TOOL, 1).add(Aspect.METAL, 4).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeHammer), new AspectList()     .add(Aspect.TOOL, 1).add(Aspect.METAL, 4).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelHammer), new AspectList()      .add(Aspect.TOOL, 1).add(Aspect.METAL, 8).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelHammer), new AspectList()       .add(Aspect.TOOL, 1).add(Aspect.METAL, 8).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeHammer), new AspectList()          .add(Aspect.TOOL, 1).add(Aspect.METAL, 4).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperHammer), new AspectList()          .add(Aspect.TOOL, 1).add(Aspect.METAL, 4).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronHammer), new AspectList()     .add(Aspect.TOOL, 1).add(Aspect.METAL, 8).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelHammer), new AspectList()        .add(Aspect.TOOL, 1).add(Aspect.METAL, 8).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelHammer), new AspectList()           .add(Aspect.TOOL, 1).add(Aspect.METAL, 8).add(Aspect.TREE,1));

		//System.out.println("Registering Aspects of Unshaped Ingots");
/*
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.highCarbonBlackSteelUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.weakBlueSteelUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.highCarbonBlueSteelUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.brassUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.goldUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.leadUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.nickelUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.pigIronUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.platinumUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.weakRedSteelUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.highCarbonRedSteelUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.roseGoldUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.silverUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.weakSteelUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.highCarbonSteelUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sterlingSilverUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.tinUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.zincUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));
		*/
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.ceramicMold), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.ink), new AspectList().add(Aspect.EARTH, 1));

		//System.out.println("Registering Aspects of Metal Tool Heads");
		//Tool Heads
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzePickaxeHead), new AspectList()  .add(Aspect.MINE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzePickaxeHead), new AspectList()    .add(Aspect.MINE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelPickaxeHead), new AspectList()     .add(Aspect.MINE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelPickaxeHead), new AspectList()      .add(Aspect.MINE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzePickaxeHead), new AspectList()         .add(Aspect.MINE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperPickaxeHead), new AspectList()         .add(Aspect.MINE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronPickaxeHead), new AspectList()    .add(Aspect.MINE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelPickaxeHead), new AspectList()       .add(Aspect.MINE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelPickaxeHead), new AspectList()          .add(Aspect.MINE, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeShovelHead), new AspectList()   .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeShovelHead), new AspectList()     .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelShovelHead), new AspectList()      .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelShovelHead), new AspectList()       .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeShovelHead), new AspectList()          .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperShovelHead), new AspectList()          .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronShovelHead), new AspectList()     .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelShovelHead), new AspectList()        .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.silverShovelHead), new AspectList()          .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelShovelHead), new AspectList()           .add(Aspect.TOOL, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeHoeHead), new AspectList()      .add(Aspect.HARVEST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeHoeHead), new AspectList()        .add(Aspect.HARVEST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelHoeHead), new AspectList()         .add(Aspect.HARVEST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelHoeHead), new AspectList()          .add(Aspect.HARVEST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeHoeHead), new AspectList()             .add(Aspect.HARVEST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperHoeHead), new AspectList()             .add(Aspect.HARVEST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronHoeHead), new AspectList()        .add(Aspect.HARVEST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelHoeHead), new AspectList()           .add(Aspect.HARVEST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelHoeHead), new AspectList()              .add(Aspect.HARVEST, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeAxeHead), new AspectList()      .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeAxeHead), new AspectList()        .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelAxeHead), new AspectList()         .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelAxeHead), new AspectList()          .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeAxeHead), new AspectList()             .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperAxeHead), new AspectList()             .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronAxeHead), new AspectList()        .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelAxeHead), new AspectList()           .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelAxeHead), new AspectList()              .add(Aspect.TOOL, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeHammerHead), new AspectList()   .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeHammerHead), new AspectList()     .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelHammerHead), new AspectList()      .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelHammerHead), new AspectList()       .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeHammerHead), new AspectList()          .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperHammerHead), new AspectList()          .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronHammerHead), new AspectList()     .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelHammerHead), new AspectList()        .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelHammerHead), new AspectList()           .add(Aspect.TOOL, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeChiselHead), new AspectList()   .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeChiselHead), new AspectList()     .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelChiselHead), new AspectList()      .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelChiselHead), new AspectList()       .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeChiselHead), new AspectList()          .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperChiselHead), new AspectList()          .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronChiselHead), new AspectList()     .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelChiselHead), new AspectList()        .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelChiselHead), new AspectList()           .add(Aspect.TOOL, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeSwordHead), new AspectList()    .add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeSwordHead), new AspectList()      .add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelSwordHead), new AspectList()       .add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelSwordHead), new AspectList()        .add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeSwordHead), new AspectList()           .add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperSwordHead), new AspectList()           .add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronSwordHead), new AspectList()      .add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelSwordHead), new AspectList()         .add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelSwordHead), new AspectList()            .add(Aspect.WEAPON, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeMaceHead), new AspectList()     .add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeMaceHead), new AspectList()       .add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelMaceHead), new AspectList()        .add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelMaceHead), new AspectList()         .add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeMaceHead), new AspectList()            .add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperMaceHead), new AspectList()            .add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronMaceHead), new AspectList()       .add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelMaceHead), new AspectList()          .add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelMaceHead), new AspectList()             .add(Aspect.WEAPON, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeSawHead), new AspectList()      .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeSawHead), new AspectList()        .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelSawHead), new AspectList()         .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelSawHead), new AspectList()          .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeSawHead), new AspectList()             .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperSawHead), new AspectList()             .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronSawHead), new AspectList()        .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelSawHead), new AspectList()           .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelSawHead), new AspectList()              .add(Aspect.TOOL, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeProPickHead), new AspectList()  .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeProPickHead), new AspectList()    .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelProPickHead), new AspectList()     .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelProPickHead), new AspectList()      .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeProPickHead), new AspectList()         .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperProPickHead), new AspectList()         .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronProPickHead), new AspectList()    .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelProPickHead), new AspectList()       .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelProPickHead), new AspectList()          .add(Aspect.TOOL, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeScytheHead), new AspectList()   .add(Aspect.HARVEST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeScytheHead), new AspectList()     .add(Aspect.HARVEST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelScytheHead), new AspectList()      .add(Aspect.HARVEST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelScytheHead), new AspectList()       .add(Aspect.HARVEST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeScytheHead), new AspectList()          .add(Aspect.HARVEST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperScytheHead), new AspectList()          .add(Aspect.HARVEST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronScytheHead), new AspectList()     .add(Aspect.HARVEST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelScytheHead), new AspectList()        .add(Aspect.HARVEST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelScytheHead), new AspectList()           .add(Aspect.HARVEST, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeKnifeHead), new AspectList()    .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeKnifeHead), new AspectList()      .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelKnifeHead), new AspectList()       .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelKnifeHead), new AspectList()        .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeKnifeHead), new AspectList()           .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperKnifeHead), new AspectList()           .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronKnifeHead), new AspectList()      .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelKnifeHead), new AspectList()         .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelKnifeHead), new AspectList()            .add(Aspect.TOOL, 1));

		//System.out.println("Registering Aspects of Coke, Powder, and Dye");
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.coke), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.powder,1,0), new AspectList().add(Aspect.CRAFT, 1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.powder,1,1), new AspectList().add(Aspect.FIRE, 1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.powder,1,2), new AspectList().add(Aspect.ENERGY, 1).add(Aspect.FIRE,1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.powder,1,3), new AspectList().add(Aspect.FIRE, 1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.powder,1,4), new AspectList().add(Aspect.FIRE, 1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.powder,1,5), new AspectList().add(Aspect.SENSES, 1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.powder,1,6), new AspectList().add(Aspect.SENSES, 1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.powder,1,7), new AspectList().add(Aspect.SENSES, 1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.powder,1,8), new AspectList().add(Aspect.SENSES, 1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.powder,1,9), new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.ENTROPY,1));

		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.dye), new AspectList().add(Aspect.EARTH, 1));

		//Formerly TFC_Mining
		//System.out.println("Registering Aspects of Gold Pan and Sluice");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.goldPan), new AspectList().add(Aspect.MOTION, 1).add(Aspect.GREED,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sluiceItem), new AspectList().add(Aspect.MOTION, 2).add(Aspect.GREED,1));

		//System.out.println("Registering Aspects of prospector's pick");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.proPickBismuthBronze), new AspectList()  .add(Aspect.TOOL, 1).add(Aspect.SENSES, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.proPickBlackBronze), new AspectList()    .add(Aspect.TOOL, 1).add(Aspect.SENSES, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.proPickBlackSteel), new AspectList()     .add(Aspect.TOOL, 3).add(Aspect.SENSES, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.proPickBlueSteel), new AspectList()      .add(Aspect.TOOL, 4).add(Aspect.SENSES, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.proPickBronze), new AspectList()         .add(Aspect.TOOL, 1).add(Aspect.SENSES, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.proPickCopper), new AspectList()         .add(Aspect.TOOL, 1).add(Aspect.SENSES, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.proPickIron), new AspectList()           .add(Aspect.TOOL, 2).add(Aspect.SENSES, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.proPickRedSteel), new AspectList()       .add(Aspect.TOOL, 4).add(Aspect.SENSES, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.proPickSteel), new AspectList()          .add(Aspect.TOOL, 3).add(Aspect.SENSES, 1));

		//System.out.println("Registering Aspects of Metal Sheets ");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthSheet), new AspectList()      .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeSheet), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeSheet), new AspectList()  .add(Aspect.METAL, 6).add(Aspect.CRAFT, 2).add(Aspect.DARKNESS,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelSheet), new AspectList()   .add(Aspect.METAL, 6).add(Aspect.CRAFT, 2).add(Aspect.DARKNESS,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelSheet), new AspectList()    .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeSheet), new AspectList()       .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperSheet), new AspectList()       .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronSheet), new AspectList()  .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelSheet), new AspectList()     .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.roseGoldSheet), new AspectList()     .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelSheet), new AspectList()        .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.tinSheet), new AspectList()          .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.zincSheet), new AspectList()         .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));

		//System.out.println("Registering Aspects of Metal Double Sheets");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.brassSheet), new AspectList()            .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.goldSheet), new AspectList()             .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.leadSheet), new AspectList()             .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.nickelSheet), new AspectList()           .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.pigIronSheet), new AspectList()          .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.platinumSheet), new AspectList()         .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.silverSheet), new AspectList()           .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sterlingSilverSheet), new AspectList()   .add(Aspect.METAL, 6).add(Aspect.CRAFT,2));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthSheet2x), new AspectList()        .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeSheet2x), new AspectList()  .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeSheet2x), new AspectList()    .add(Aspect.METAL, 12).add(Aspect.CRAFT, 4).add(Aspect.DARKNESS,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelSheet2x), new AspectList()     .add(Aspect.METAL, 12).add(Aspect.CRAFT, 4).add(Aspect.DARKNESS,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelSheet2x), new AspectList()      .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeSheet2x), new AspectList()         .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperSheet2x), new AspectList()         .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronSheet2x), new AspectList()    .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelSheet2x), new AspectList()       .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.roseGoldSheet2x), new AspectList()       .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelSheet2x), new AspectList()          .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.tinSheet2x), new AspectList()            .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.zincSheet2x), new AspectList()           .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.brassSheet2x), new AspectList()          .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.goldSheet2x), new AspectList()           .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.leadSheet2x), new AspectList()           .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.nickelSheet2x), new AspectList()         .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.pigIronSheet2x), new AspectList()        .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.platinumSheet2x), new AspectList()       .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.silverSheet2x), new AspectList()         .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sterlingSilverSheet2x), new AspectList() .add(Aspect.METAL, 12).add(Aspect.CRAFT,4));

		//System.out.println("Registering Aspects of Unfinished Armor");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeUnfinishedChestplate,1, 0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeUnfinishedChestplate,1,   0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelUnfinishedChestplate,1,    0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelUnfinishedChestplate,1,     0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeUnfinishedChestplate,1,        0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperUnfinishedChestplate,1,        0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronUnfinishedChestplate,1,   0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelUnfinishedChestplate,1,      0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelUnfinishedChestplate,1,         0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeUnfinishedChestplate,1, 1), new AspectList().add(Aspect.METAL, 24).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeUnfinishedChestplate,1,   1), new AspectList().add(Aspect.METAL, 24).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelUnfinishedChestplate,1,    1), new AspectList().add(Aspect.METAL, 24).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelUnfinishedChestplate,1,     1), new AspectList().add(Aspect.METAL, 24).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeUnfinishedChestplate,1,        1), new AspectList().add(Aspect.METAL, 24).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperUnfinishedChestplate,1,        1), new AspectList().add(Aspect.METAL, 24).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronUnfinishedChestplate,1,   1), new AspectList().add(Aspect.METAL, 24).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelUnfinishedChestplate,1,      1), new AspectList().add(Aspect.METAL, 24).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelUnfinishedChestplate,1,         1), new AspectList().add(Aspect.METAL, 24).add(Aspect.CRAFT,2));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeUnfinishedGreaves,1,    0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeUnfinishedGreaves,1,      0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelUnfinishedGreaves,1,       0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelUnfinishedGreaves,1,        0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeUnfinishedGreaves,1,           0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperUnfinishedGreaves,1,           0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronUnfinishedGreaves,1,      0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelUnfinishedGreaves,1,         0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelUnfinishedGreaves,1,            0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeUnfinishedGreaves,1,    1), new AspectList().add(Aspect.METAL, 18).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeUnfinishedGreaves,1,      1), new AspectList().add(Aspect.METAL, 18).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelUnfinishedGreaves,1,       1), new AspectList().add(Aspect.METAL, 18).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelUnfinishedGreaves,1,        1), new AspectList().add(Aspect.METAL, 18).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeUnfinishedGreaves,1,           1), new AspectList().add(Aspect.METAL, 18).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperUnfinishedGreaves,1,           1), new AspectList().add(Aspect.METAL, 18).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronUnfinishedGreaves,1,      1), new AspectList().add(Aspect.METAL, 18).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelUnfinishedGreaves,1,         1), new AspectList().add(Aspect.METAL, 18).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelUnfinishedGreaves,1,            1), new AspectList().add(Aspect.METAL, 18).add(Aspect.CRAFT,2));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeUnfinishedBoots,1,      0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeUnfinishedBoots,1,        0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelUnfinishedBoots,1,         0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelUnfinishedBoots,1,          0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeUnfinishedBoots,1,             0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperUnfinishedBoots,1,             0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronUnfinishedBoots,1,        0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelUnfinishedBoots,1,           0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelUnfinishedBoots,1,              0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeUnfinishedBoots,1,      1), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeUnfinishedBoots,1,        1), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelUnfinishedBoots,1,         1), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelUnfinishedBoots,1,          1), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeUnfinishedBoots,1,             1), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperUnfinishedBoots,1,             1), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronUnfinishedBoots,1,        1), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelUnfinishedBoots,1,           1), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelUnfinishedBoots,1,              1), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,2));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeUnfinishedHelmet,1,     0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeUnfinishedHelmet,1,       0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelUnfinishedHelmet,1,        0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelUnfinishedHelmet,1,         0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeUnfinishedHelmet,1,            0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperUnfinishedHelmet,1,            0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronUnfinishedHelmet,1,       0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelUnfinishedHelmet,1,          0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelUnfinishedHelmet,1,             0), new AspectList().add(Aspect.METAL, 6).add(Aspect.CRAFT,1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeUnfinishedHelmet,1,     0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeUnfinishedHelmet,1,       0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelUnfinishedHelmet,1,        0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelUnfinishedHelmet,1,         0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeUnfinishedHelmet,1,            0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperUnfinishedHelmet,1,            0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronUnfinishedHelmet,1,       0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelUnfinishedHelmet,1,          0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelUnfinishedHelmet,1,             0), new AspectList().add(Aspect.METAL, 12).add(Aspect.CRAFT, 2));

		//System.out.println("Registering Aspects of Finishied Armor");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.leatherChestplate), new AspectList()				.add(Aspect.BEAST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeChestplate), new AspectList()		.add(Aspect.METAL, 24));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeChestplate), new AspectList()			.add(Aspect.METAL, 24));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelChestplate), new AspectList()			.add(Aspect.METAL, 24));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelChestplate), new AspectList()			.add(Aspect.METAL, 24));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeChestplate), new AspectList()				.add(Aspect.METAL, 24));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperChestplate), new AspectList()				.add(Aspect.METAL, 24));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronChestplate), new AspectList()			.add(Aspect.METAL, 24));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelChestplate), new AspectList()			.add(Aspect.METAL, 24));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelChestplate), new AspectList()				.add(Aspect.METAL, 24));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.leatherLeggings), new AspectList()				.add(Aspect.BEAST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeGreaves), new AspectList()			.add(Aspect.METAL, 18));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeGreaves), new AspectList()			.add(Aspect.METAL, 18));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelGreaves), new AspectList()				.add(Aspect.METAL, 18));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelGreaves), new AspectList()				.add(Aspect.METAL, 18));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeGreaves), new AspectList()					.add(Aspect.METAL, 18));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperGreaves), new AspectList()					.add(Aspect.METAL, 18));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronGreaves), new AspectList()			.add(Aspect.METAL, 18));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelGreaves), new AspectList()				.add(Aspect.METAL, 18));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelGreaves), new AspectList()					.add(Aspect.METAL, 18));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.leatherBoots), new AspectList()					.add(Aspect.BEAST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeBoots), new AspectList()			.add(Aspect.METAL, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeBoots), new AspectList()				.add(Aspect.METAL, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelBoots), new AspectList()				.add(Aspect.METAL, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelBoots), new AspectList()				.add(Aspect.METAL, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeBoots), new AspectList()					.add(Aspect.METAL, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperBoots), new AspectList()					.add(Aspect.METAL, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronBoots), new AspectList()				.add(Aspect.METAL, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelBoots), new AspectList()					.add(Aspect.METAL, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelBoots), new AspectList()					.add(Aspect.METAL, 12));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.leatherHelmet), new AspectList()					.add(Aspect.BEAST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bismuthBronzeHelmet), new AspectList()			.add(Aspect.METAL, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackBronzeHelmet), new AspectList()				.add(Aspect.METAL, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackSteelHelmet), new AspectList()				.add(Aspect.METAL, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelHelmet), new AspectList()				.add(Aspect.METAL, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bronzeHelmet), new AspectList()					.add(Aspect.METAL, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.copperHelmet), new AspectList()					.add(Aspect.METAL, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wroughtIronHelmet), new AspectList()				.add(Aspect.METAL, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelHelmet), new AspectList()				.add(Aspect.METAL, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.steelHelmet), new AspectList()					.add(Aspect.METAL, 12));

		//System.out.println("Registering Aspects of Buckets");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.woodenBucketEmpty), new AspectList().add(Aspect.TREE, 1).add(Aspect.VOID,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.woodenBucketWater), new AspectList().add(Aspect.TREE, 1).add(Aspect.WATER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.woodenBucketSaltWater), new AspectList().add(Aspect.TREE, 1).add(Aspect.WATER, 1).add(Aspect.CRYSTAL,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.woodenBucketMilk), new AspectList().add(Aspect.TREE, 1).add(Aspect.HEAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.vinegar), new AspectList().add(Aspect.TREE, 1).add(Aspect.WATER,1).add(Aspect.EXCHANGE,1));

		//System.out.println("Registering Aspects of seeds ");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsWheat), new AspectList()            .add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsMaize), new AspectList()            .add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsTomato), new AspectList()           .add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsBarley), new AspectList()           .add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsRye), new AspectList()              .add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsOat), new AspectList()              .add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsRice), new AspectList()             .add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsPotato), new AspectList()           .add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsOnion), new AspectList()            .add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsCabbage), new AspectList()          .add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsGarlic), new AspectList()           .add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsCarrot), new AspectList()           .add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsSugarcane), new AspectList()        .add(Aspect.PLANT, 1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsHemp), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsYellowBellPepper), new AspectList() .add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsRedBellPepper), new AspectList()    .add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsSoybean), new AspectList()          .add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsGreenbean), new AspectList()        .add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsSquash), new AspectList()           .add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seedsJute), new AspectList()             .add(Aspect.PLANT, 1));

		//System.out.println("Registering Aspects of fruit tree saplings");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.fruitTreeSapling,1,wild), new AspectList().add(Aspect.PLANT, 1).add(Aspect.TREE,1));

		//System.out.println("Registering Aspects of fruit");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redApple), new AspectList()      .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.greenApple), new AspectList()    .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.banana), new AspectList()        .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.orange), new AspectList()        .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.lemon), new AspectList()         .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.olive), new AspectList()         .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.cherry), new AspectList()        .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.peach), new AspectList()         .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.plum), new AspectList()          .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.egg), new AspectList()           .add(Aspect.SLIME, 8).add(Aspect.LIFE, 8).add(Aspect.BEAST, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.eggCooked), new AspectList()     .add(Aspect.FLESH, 8));//TODO make the arcane furnace cook egg properly
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.cheese), new AspectList()        .add(Aspect.EXCHANGE, 10));

		//System.out.println("Registering Aspects of crops");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wheatGrain), new AspectList()       .add(Aspect.CROP, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.barleyGrain), new AspectList()      .add(Aspect.CROP, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oatGrain), new AspectList()         .add(Aspect.CROP, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.ryeGrain), new AspectList()         .add(Aspect.CROP, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.riceGrain), new AspectList()        .add(Aspect.CROP, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.maizeEar), new AspectList()         .add(Aspect.CROP, 12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.tomato), new AspectList()           .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.potato), new AspectList()           .add(Aspect.PLANT, 16).add(Aspect.EARTH, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.onion), new AspectList()            .add(Aspect.PLANT, 16).add(Aspect.EARTH, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.cabbage), new AspectList()          .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.garlic), new AspectList()           .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.carrot), new AspectList()           .add(Aspect.PLANT, 12).add(Aspect.EARTH, 8).add(Aspect.SENSES, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sugarcane), new AspectList()        .add(Aspect.CROP, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.soybean), new AspectList()          .add(Aspect.PLANT, 12).add(Aspect.FLESH, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.greenbeans), new AspectList()       .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.greenBellPepper), new AspectList()  .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.yellowBellPepper), new AspectList() .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redBellPepper), new AspectList()    .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.squash), new AspectList()           .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.seaWeed), new AspectList()          .add(Aspect.PLANT, 8).add(Aspect.WATER, 8).add(Aspect.CRYSTAL, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sugar), new AspectList()            .add(Aspect.CRYSTAL, 16));

		//System.out.println("Registering Aspects of whole grains");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wheatWhole), new AspectList()    .add(Aspect.CROP, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.barleyWhole), new AspectList()   .add(Aspect.CROP, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oatWhole), new AspectList()      .add(Aspect.CROP, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.ryeWhole), new AspectList()      .add(Aspect.CROP, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.riceWhole), new AspectList()     .add(Aspect.CROP, 16));
		//System.out.println("Registering Aspects of ground grains");


		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wheatGround), new AspectList()   .add(Aspect.CROP, 12).add(Aspect.CRAFT, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.barleyGround), new AspectList()  .add(Aspect.CROP, 12).add(Aspect.CRAFT, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oatGround), new AspectList()     .add(Aspect.CROP, 12).add(Aspect.CRAFT, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.ryeGround), new AspectList()     .add(Aspect.CROP, 12).add(Aspect.CRAFT, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.riceGround), new AspectList()    .add(Aspect.CROP, 12).add(Aspect.CRAFT, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.cornmealGround), new AspectList().add(Aspect.CROP, 12).add(Aspect.CRAFT, 4));

		//System.out.println("Registering Aspects of dough");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wheatDough), new AspectList()    .add(Aspect.CROP, 8).add(Aspect.CRAFT, 8).add(Aspect.WATER, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.barleyDough), new AspectList()   .add(Aspect.CROP, 8).add(Aspect.CRAFT, 8).add(Aspect.WATER, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oatDough), new AspectList()      .add(Aspect.CROP, 8).add(Aspect.CRAFT, 8).add(Aspect.WATER, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.ryeDough), new AspectList()      .add(Aspect.CROP, 8).add(Aspect.CRAFT, 8).add(Aspect.WATER, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.riceDough), new AspectList()     .add(Aspect.CROP, 8).add(Aspect.CRAFT, 8).add(Aspect.WATER, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.cornmealDough), new AspectList() .add(Aspect.CROP, 8).add(Aspect.CRAFT, 8).add(Aspect.WATER, 8));

		//System.out.println("Registering Aspects of bread");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wheatBread), new AspectList()    .add(Aspect.CROP, 8).add(Aspect.HUNGER, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.barleyBread), new AspectList()   .add(Aspect.CROP, 8).add(Aspect.HUNGER, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.oatBread), new AspectList()      .add(Aspect.CROP, 8).add(Aspect.HUNGER, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.ryeBread), new AspectList()      .add(Aspect.CROP, 8).add(Aspect.HUNGER, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.riceBread), new AspectList()     .add(Aspect.CROP, 8).add(Aspect.HUNGER, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.cornBread), new AspectList()     .add(Aspect.CROP, 8).add(Aspect.HUNGER, 8));

		//System.out.println("Registering Aspects of raw meat");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.muttonRaw), new AspectList()                                 .add(Aspect.FLESH, 8).add(Aspect.LIFE, 8).add(Aspect.BEAST, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.calamariRaw), new AspectList()                               .add(Aspect.FLESH, 8).add(Aspect.LIFE, 8).add(Aspect.WATER, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.venisonRaw), new AspectList()                                .add(Aspect.FLESH, 8).add(Aspect.LIFE, 8).add(Aspect.BEAST, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.horseMeatRaw), new AspectList()                              .add(Aspect.FLESH, 8).add(Aspect.LIFE, 8).add(Aspect.BEAST, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.porkchopRaw), new AspectList()                               .add(Aspect.FLESH, 8).add(Aspect.LIFE, 8).add(Aspect.BEAST, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.fishRaw), new AspectList()                                   .add(Aspect.FLESH, 8).add(Aspect.LIFE, 8).add(Aspect.WATER, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.beefRaw), new AspectList()                                   .add(Aspect.FLESH, 8).add(Aspect.LIFE, 8).add(Aspect.BEAST, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.chickenRaw), new AspectList()                                .add(Aspect.FLESH, 8).add(Aspect.LIFE, 8).add(Aspect.BEAST, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sandwich,1,wild), new AspectList()   .add(Aspect.HUNGER, 160));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.salad,1,wild), new AspectList()      .add(Aspect.HUNGER, 160));

		//System.out.println("Registering Aspects of rocks");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.looseRock,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.flatRock,1,wild), new AspectList().add(Aspect.EARTH, 1));

		//System.out.println("Registering Aspects of stone tool heads");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.igInStoneShovelHead), new AspectList()   .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sedStoneShovelHead), new AspectList()    .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.igExStoneShovelHead), new AspectList()   .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.mMStoneShovelHead), new AspectList()     .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.igInStoneAxeHead), new AspectList()      .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sedStoneAxeHead), new AspectList()       .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.igExStoneAxeHead), new AspectList()      .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.mMStoneAxeHead), new AspectList()        .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.igInStoneHoeHead), new AspectList()      .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sedStoneHoeHead), new AspectList()       .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.igExStoneHoeHead), new AspectList()      .add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.mMStoneHoeHead), new AspectList()        .add(Aspect.TOOL, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.stoneKnife), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.stoneKnifeHead), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.stoneHammerHead), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.singlePlank), new AspectList().add(Aspect.TREE, 1));

		//System.out.println("Registering Aspects of minecarts");

		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.minecartEmpty), new AspectList().add(Aspect.EARTH, 1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.minecartCrate), new AspectList().add(Aspect.EARTH, 1));

		//System.out.println("Registering Aspects of metal buckets");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelBucketEmpty), new AspectList().add(Aspect.METAL, 2).add(Aspect.VOID,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelBucketWater), new AspectList().add(Aspect.METAL, 2).add(Aspect.WATER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.redSteelBucketSaltWater), new AspectList().add(Aspect.METAL, 2).add(Aspect.WATER, 1).add(Aspect.CRYSTAL,1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelBucketEmpty), new AspectList().add(Aspect.METAL, 2).add(Aspect.VOID,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueSteelBucketLava), new AspectList().add(Aspect.METAL, 2).add(Aspect.FIRE, 1));

		//System.out.println("Registering Aspects of quern and flint steel");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.quern), new AspectList().add(Aspect.MECHANISM, 5).add(Aspect.EARTH, 1).add(Aspect.ENTROPY,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.flintSteel), new AspectList().add(Aspect.FIRE, 4));

		//System.out.println("Registering Aspects of Doors");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.doorOak), new AspectList()           .add(Aspect.TREE, 4).add(Aspect.MOTION,1).add(Aspect.MECHANISM,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.doorAspen), new AspectList()         .add(Aspect.TREE, 4).add(Aspect.MOTION,1).add(Aspect.MECHANISM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.doorBirch), new AspectList()         .add(Aspect.TREE, 4).add(Aspect.MOTION,1).add(Aspect.MECHANISM,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.doorChestnut), new AspectList()      .add(Aspect.TREE, 4).add(Aspect.MOTION,1).add(Aspect.MECHANISM,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.doorDouglasFir), new AspectList()    .add(Aspect.TREE, 4).add(Aspect.MOTION,1).add(Aspect.MECHANISM,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.doorHickory), new AspectList()       .add(Aspect.TREE, 4).add(Aspect.MOTION,1).add(Aspect.MECHANISM,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.doorMaple), new AspectList()         .add(Aspect.TREE, 4).add(Aspect.MOTION,1).add(Aspect.MECHANISM,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.doorAsh), new AspectList()           .add(Aspect.TREE, 4).add(Aspect.MOTION,1).add(Aspect.MECHANISM,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.doorPine), new AspectList()          .add(Aspect.TREE, 4).add(Aspect.MOTION,1).add(Aspect.MECHANISM,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.doorSequoia), new AspectList()       .add(Aspect.TREE, 4).add(Aspect.MOTION,1).add(Aspect.MECHANISM,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.doorSpruce), new AspectList()        .add(Aspect.TREE, 4).add(Aspect.MOTION,1).add(Aspect.MECHANISM,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.doorSycamore), new AspectList()      .add(Aspect.TREE, 4).add(Aspect.MOTION,1).add(Aspect.MECHANISM,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.doorWhiteCedar), new AspectList()    .add(Aspect.TREE, 4).add(Aspect.MOTION,1).add(Aspect.MECHANISM,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.doorWhiteElm), new AspectList()      .add(Aspect.TREE, 4).add(Aspect.MOTION,1).add(Aspect.MECHANISM,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.doorWillow), new AspectList()        .add(Aspect.TREE, 4).add(Aspect.MOTION,1).add(Aspect.MECHANISM,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.doorKapok), new AspectList()         .add(Aspect.TREE, 4).add(Aspect.MOTION,1).add(Aspect.MECHANISM,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.doorAcacia), new AspectList()        .add(Aspect.TREE, 4).add(Aspect.MOTION, 1).add(Aspect.MECHANISM,1));

		//System.out.println("Registering Aspects of leather and cloth stuff");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueprint), new AspectList().add(Aspect.CLOTH, 1).add(Aspect.CRAFT,1).add(Aspect.MINE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.nametag), new AspectList().add(Aspect.BEAST, 2).add(Aspect.MIND,2));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.writabeBookTFC), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.woolYarn), new AspectList().add(Aspect.BEAST, 2).add(Aspect.CLOTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wool), new AspectList().add(Aspect.BEAST, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.woolCloth), new AspectList().add(Aspect.BEAST, 1).add(Aspect.CLOTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.silkCloth), new AspectList().add(Aspect.CLOTH, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.jute), new AspectList().add(Aspect.PLANT, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.juteFiber), new AspectList().add(Aspect.PLANT, 2).add(Aspect.CLOTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.burlapCloth), new AspectList().add(Aspect.PLANT, 1).add(Aspect.CLOTH,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.spindle), new AspectList().add(Aspect.MECHANISM, 1).add(Aspect.MOTION, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.spindleHead), new AspectList().add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.stoneBrick), new AspectList().add(Aspect.EARTH, 1).add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.mortar), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ORDER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.hide,1,0), new AspectList().add(Aspect.BEAST, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.hide,1,1), new AspectList().add(Aspect.BEAST, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.hide,1,2), new AspectList().add(Aspect.BEAST, 3));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.soakedHide,1,0), new AspectList().add(Aspect.BEAST, 1).add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.soakedHide,1,1), new AspectList().add(Aspect.BEAST, 2).add(Aspect.WATER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.soakedHide,1,2), new AspectList().add(Aspect.BEAST, 3).add(Aspect.WATER,1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.scrapedHide,1,0), new AspectList().add(Aspect.BEAST, 1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.scrapedHide,1,1), new AspectList().add(Aspect.BEAST, 2).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.scrapedHide,1,2), new AspectList().add(Aspect.BEAST, 3).add(Aspect.CRAFT,1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.prepHide,1,0), new AspectList().add(Aspect.BEAST, 1).add(Aspect.CRAFT, 1).add(Aspect.WATER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.prepHide,1,1), new AspectList().add(Aspect.BEAST, 2).add(Aspect.CRAFT,1).add(Aspect.WATER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.prepHide,1,2), new AspectList().add(Aspect.BEAST, 3).add(Aspect.CRAFT, 1).add(Aspect.WATER,1));


		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sheepSkin,1,0), new AspectList().add(Aspect.BEAST, 1).add(Aspect.CLOTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sheepSkin,1,1), new AspectList().add(Aspect.BEAST, 2).add(Aspect.CLOTH,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sheepSkin,1,2), new AspectList().add(Aspect.BEAST, 3).add(Aspect.CLOTH, 3));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.leather), new AspectList().add(Aspect.BEAST, 1).add(Aspect.CLOTH,2).add(Aspect.ARMOR,1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.flatLeather), new AspectList().add(Aspect.EARTH, 1));
		//System.out.println("Registering Aspects of booze");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.beer), new AspectList().add(Aspect.CROP, 1).add(Aspect.WATER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.cider), new AspectList().add(Aspect.PLANT, 1).add(Aspect.WATER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.rum), new AspectList().add(Aspect.SOUL, 1).add(Aspect.WATER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.ryeWhiskey), new AspectList().add(Aspect.FIRE, 1).add(Aspect.WATER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.sake), new AspectList().add(Aspect.CROP, 1).add(Aspect.WATER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.vodka), new AspectList().add(Aspect.SOUL, 1).add(Aspect.WATER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.whiskey), new AspectList().add(Aspect.SOUL, 1).add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.cornWhiskey), new AspectList().add(Aspect.SOUL, 1).add(Aspect.WATER, 1));

		//System.out.println("Registering Aspects of bottles and potions");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.glassBottle), new AspectList().add(Aspect.VOID, 1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.potion), new AspectList().add(Aspect.EARTH, 1));

		//System.out.println("Registering Aspects of clay stuff ");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayBall), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.potteryJug,1,0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER, 1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.potteryJug,1,1), new AspectList().add(Aspect.EARTH, 1).add(Aspect.VOID,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.potteryJug,1,2), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER,1));

		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.potteryPot), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.potterySmallVessel,1,0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER,1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.potterySmallVessel,1,1), new AspectList().add(Aspect.EARTH, 1).add(Aspect.VOID,1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.potteryBowl,1,0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER,1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.potteryBowl,1,1), new AspectList().add(Aspect.VOID, 1));

		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.kilnRack), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.straw), new AspectList().add(Aspect.CROP, 1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.flatClay), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.fireBrick,1,0), new AspectList().add(Aspect.WATER, 1).add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.fireBrick,1,1), new AspectList().add(Aspect.FIRE, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.stick), new AspectList().add(Aspect.TREE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.arrow), new AspectList().add(Aspect.WEAPON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.rope), new AspectList().add(Aspect.BEAST, 1).add(Aspect.CLOTH,1));

		//System.out.println("Registering Aspects of molds");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldAxe,1,       0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER,1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldChisel,1,    0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER,1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldHammer,1,    0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER,1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldHoe,1,       0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER,1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldKnife,1,     0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER,1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldMace,1,      0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER,1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldPick,1,      0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER,1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldProPick,1,   0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER,1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldSaw,1,       0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER,1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldScythe,1,    0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER,1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldShovel,1,    0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER,1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldSword,1,     0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER,1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldJavelin,1,   0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER,1).add(Aspect.CRAFT,1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldAxe,1,       1), new AspectList().add(Aspect.VOID, 1).add(Aspect.WEAPON,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldChisel,1,    1), new AspectList().add(Aspect.VOID, 1).add(Aspect.TOOL,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldHammer,1,    1), new AspectList().add(Aspect.VOID, 1).add(Aspect.TOOL,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldHoe,1,       1), new AspectList().add(Aspect.VOID, 1).add(Aspect.HARVEST,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldKnife,1,     1), new AspectList().add(Aspect.VOID, 1).add(Aspect.TOOL,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldMace,1,      1), new AspectList().add(Aspect.VOID, 1).add(Aspect.WEAPON,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldPick,1,      1), new AspectList().add(Aspect.VOID, 1).add(Aspect.MINE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldProPick,1,   1), new AspectList().add(Aspect.VOID, 1).add(Aspect.TOOL,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldSaw,1,       1), new AspectList().add(Aspect.VOID, 1).add(Aspect.TOOL,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldScythe,1,    1), new AspectList().add(Aspect.VOID, 1).add(Aspect.HARVEST,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldShovel,1,    1), new AspectList().add(Aspect.VOID, 1).add(Aspect.TOOL,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldSword,1,     1), new AspectList().add(Aspect.VOID, 1).add(Aspect.WEAPON,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.clayMoldJavelin,1,   1), new AspectList().add(Aspect.VOID, 1).add(Aspect.WEAPON,1));

		//System.out.println("Registering Aspects of tuyere");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.tuyereCopper), new AspectList()          .add(Aspect.AIR, 1).add(Aspect.METAL,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.tuyereBronze), new AspectList()          .add(Aspect.AIR, 1).add(Aspect.METAL,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.tuyereBlackBronze), new AspectList()     .add(Aspect.AIR, 1).add(Aspect.METAL,2).add(Aspect.DARKNESS,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.tuyereBismuthBronze), new AspectList()   .add(Aspect.AIR, 1).add(Aspect.METAL,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.tuyereWroughtIron), new AspectList()     .add(Aspect.AIR, 1).add(Aspect.METAL,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.tuyereSteel), new AspectList()           .add(Aspect.AIR, 1).add(Aspect.METAL,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.tuyereBlackSteel), new AspectList()      .add(Aspect.AIR, 1).add(Aspect.METAL,2).add(Aspect.DARKNESS,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.tuyereBlueSteel), new AspectList()       .add(Aspect.AIR, 1).add(Aspect.METAL,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.tuyereRedSteel), new AspectList()        .add(Aspect.AIR, 1).add(Aspect.METAL,2));

		//System.out.println("Registering Aspects of blooms and unknown ingots");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bloom), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.rawBloom,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.unknownIngot), new AspectList().add(Aspect.METAL, 4));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.unknownUnshaped,1,wild), new AspectList().add(Aspect.EARTH, 1));

		//System.out.println("Registering Aspects of a quiver, Jute products and reeds");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.quiver), new AspectList().add(Aspect.BEAST, 1).add(Aspect.VOID,1));


		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.reeds), new AspectList().add(Aspect.PLANT, 1).add(Aspect.AIR,1).add(Aspect.WATER,1));

		//System.out.println("Registering Aspects of fruit and fruit leaves");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.wintergreenBerry), new AspectList()  .add(Aspect.PLANT, 16).add(Aspect.COLD,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blueberry), new AspectList()         .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.raspberry), new AspectList()         .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.strawberry), new AspectList()        .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.blackberry), new AspectList()        .add(Aspect.PLANT, 16).add(Aspect.DARKNESS,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.bunchberry), new AspectList()        .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.cranberry), new AspectList()         .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.snowberry), new AspectList()         .add(Aspect.PLANT, 16).add(Aspect.COLD, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.elderberry), new AspectList()        .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.gooseberry), new AspectList()        .add(Aspect.PLANT, 16));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.cloudberry), new AspectList()        .add(Aspect.PLANT, 16));

		//System.out.println("Registering Aspects of random remaining crap ");

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.fertilizer), new AspectList().add(Aspect.EARTH, 1).add(Aspect.PLANT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCItems.shears), new AspectList().add(Aspect.HARVEST, 3));
		registerTTCItems();;
	}

	public static void registerTTCItems()
	{
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.magiclogs, 1, 1				), new AspectList().add(Aspect.TREE, 3).add(Aspect.ORDER,1).add(Aspect.MAGIC, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.magiclogs, 1, 0				), new AspectList().add(Aspect.TREE, 3).add(Aspect.MAGIC, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumUnshaped				), new AspectList());
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumIngot				), new AspectList().add(Aspect.METAL,4).add(Aspect.MAGIC,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumIngot2x				), new AspectList().add(Aspect.METAL,8).add(Aspect.MAGIC,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumSheet				), new AspectList().add(Aspect.METAL, 4).add(Aspect.MAGIC,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumSheet2x				), new AspectList().add(Aspect.METAL, 8).add(Aspect.MAGIC,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumAxe					), new AspectList().add(Aspect.METAL,9).add(Aspect.MAGIC, 4).add(Aspect.TOOL, 4).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumShovel				), new AspectList().add(Aspect.METAL, 3).add(Aspect.MAGIC,1).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumPickaxe				), new AspectList().add(Aspect.METAL, 9).add(Aspect.MAGIC,4).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumHoe					), new AspectList().add(Aspect.METAL, 6).add(Aspect.MAGIC, 3).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumChisel				), new AspectList().merge(Aspect.TOOL, 3).add(Aspect.EXCHANGE, 1).add(Aspect.MAGIC, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumScythe				), new AspectList().merge(Aspect.HARVEST, 2).add(Aspect.MAGIC, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumMace					), new AspectList().add(Aspect.MAGIC, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumProPick				), new AspectList().add(Aspect.TOOL,2).add(Aspect.SENSES, 1).add(Aspect.MAGIC, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumHammer				), new AspectList().add(Aspect.TOOL, 1).add(Aspect.MAGIC,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumKnife                ), new AspectList().add(Aspect.TOOL,1).add(Aspect.MAGIC, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumSaw					), new AspectList().add(Aspect.TOOL, 3).add(Aspect.MAGIC, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumJavelin				), new AspectList().add(Aspect.MAGIC, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumToolHead	,1,0		), new AspectList().add(Aspect.TOOL,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumToolHead	,1,1		), new AspectList().add(Aspect.TOOL,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumToolHead	,1,2		), new AspectList().add(Aspect.TOOL,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumToolHead	,1,3		), new AspectList().add(Aspect.HARVEST,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumToolHead	,1,4		), new AspectList().add(Aspect.WEAPON,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumToolHead	,1,5		), new AspectList().add(Aspect.TOOL,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumToolHead	,1,6		), new AspectList().add(Aspect.WEAPON,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumToolHead	,1,7		), new AspectList().add(Aspect.MINE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumToolHead	,1,8		), new AspectList().add(Aspect.TOOL,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumToolHead	,1,9		), new AspectList().add(Aspect.TOOL,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumToolHead	,1,10		), new AspectList().add(Aspect.HARVEST,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumToolHead	,1,11		), new AspectList().add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumToolHead	,1,12		), new AspectList().add(Aspect.WEAPON, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumSword				), new AspectList().add(Aspect.MAGIC,3).add(Aspect.METAL, 6).add(Aspect.TREE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumGreaves				), new AspectList().add(Aspect.MAGIC, 10).add(Aspect.METAL,18));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumChest				), new AspectList().add(Aspect.MAGIC,12).add(Aspect.METAL,24));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumBoots				), new AspectList().add(Aspect.MAGIC,6).add(Aspect.METAL,12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumHelm					), new AspectList().add(Aspect.MAGIC,7).add(Aspect.METAL,12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumUnfinishedGreaves,1,0), new AspectList().add(Aspect.MAGIC, 7).add(Aspect.METAL,12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumUnfinishedGreaves,1,1), new AspectList().add(Aspect.MAGIC, 10).add(Aspect.METAL,18));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumUnfinishedChest,1,	0), new AspectList().add(Aspect.MAGIC, 8).add(Aspect.METAL,12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumUnfinishedChest,1,	1), new AspectList().add(Aspect.MAGIC, 12).add(Aspect.METAL,24));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumUnfinishedBoots,1,	0), new AspectList().add(Aspect.MAGIC, 3).add(Aspect.METAL,6));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumUnfinishedBoots,1,	1), new AspectList().add(Aspect.MAGIC, 6).add(Aspect.METAL,12));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumUnfinishedHelm,1,	0), new AspectList().add(Aspect.MAGIC, 3).add(Aspect.METAL,6));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.thaumiumUnfinishedHelm,1,	1), new AspectList().add(Aspect.MAGIC, 6).add(Aspect.METAL,12));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.transmutedOre				), new AspectList());
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.rottenMeatRaw				), new AspectList().add(Aspect.FLESH,16).add(Aspect.MAN,8));

		for(int quality = 0; quality <=3; quality ++)
		{
			for(int typeOffset = 0; typeOffset < 6; typeOffset ++)
			{
				Aspect aspectType;
				switch(typeOffset)
				{
					case 0: aspectType = Aspect.AIR;continue;
					case 1: aspectType = Aspect.FIRE;continue;
					case 2: aspectType = Aspect.WATER;continue;
					case 3: aspectType = Aspect.EARTH;continue;
					case 4: aspectType = Aspect.ORDER;continue;
					case 5: aspectType = Aspect.ENTROPY;continue;
						default:aspectType = Aspect.EARTH;
				}
				ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.roughShard, 1, quality + typeOffset), new AspectList().add(aspectType,1).add(Aspect.CRYSTAL,1));
			}
		}

		for(int typeOffset = 0; typeOffset < 6; typeOffset ++)
		{
			Aspect aspectType;
			switch(typeOffset)
			{
				case 0: aspectType = Aspect.AIR;continue;
				case 1: aspectType = Aspect.FIRE;continue;
				case 2: aspectType = Aspect.WATER;continue;
				case 3: aspectType = Aspect.EARTH;continue;
				case 4: aspectType = Aspect.ORDER;continue;
				case 5: aspectType = Aspect.ENTROPY;continue;
				default:aspectType = Aspect.EARTH;
			}
			ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.shardDust, 1, typeOffset), new AspectList().add(aspectType,1).add(Aspect.ENTROPY,1));
		}
		//ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.roughShard					), new AspectList());
		//ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.shardDust					), new AspectList());
		//ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.logsTTC,1,wild				), new AspectList().add(Aspect.TREE,4));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.debugstick					), new AspectList());
		ThaumcraftApi.registerObjectTag(new ItemStack(TMatItems.nuggetMold,1,0					), new AspectList().add(Aspect.EARTH,1).add(Aspect.WATER,1).add(Aspect.CRAFT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TMatItems.nuggetMold,1,1					), new AspectList().add(Aspect.VOID,1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TMatItems.metalNuggets,1,wild					), new AspectList().add(Aspect.METAL,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.netherwartSeeds				), new AspectList().add(Aspect.PLANT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.greatwoodDoor				), new AspectList() .add(Aspect.TREE,4).add(Aspect.MECHANISM,1).add(Aspect.MOTION,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.silverwoodDoor				), new AspectList().add(Aspect.TREE,4).add(Aspect.MECHANISM,1).add(Aspect.MOTION,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCItems.lavaSeed						), new AspectList().add(Aspect.FIRE,1).add(Aspect.LIFE,1));


	}
	public static void registerBlocks()
	{
		//public static Block[] Doors = new Block[Global.WOOD_ALL.length];


		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneIgIn,1,wild), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneIgEx,1,wild), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneSed,1,wild), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneMM,1,wild), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneIgInCobble,1,wild), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneIgExCobble,1,wild), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneSedCobble,1,wild), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneMMCobble,1,wild), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneIgInBrick,1,wild), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneIgExBrick,1,wild), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneSedBrick,1,wild), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneMMBrick,1,wild), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneIgInSmooth,1,wild), new AspectList().add(Aspect.EARTH, 2).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneIgExSmooth,1,wild), new AspectList().add(Aspect.EARTH, 2).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneSedSmooth,1,wild), new AspectList().add(Aspect.EARTH, 2).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneMMSmooth,1,wild), new AspectList().add(Aspect.EARTH, 2).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.ore), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.ore2), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.ore3), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.sulfur), new AspectList().add(Aspect.EARTH, 1).add(Aspect.FIRE,1).add(Aspect.LIFE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.planks,1,wild), new AspectList().add(Aspect.TREE , 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.planks2,1,wild), new AspectList().add(Aspect.TREE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.leaves,1,wild), new AspectList().add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.sapling,1,wild), new AspectList().add(Aspect.PLANT, 1).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.leaves2,1,wild), new AspectList().add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.sapling2,1,wild), new AspectList().add(Aspect.PLANT, 1).add(Aspect.TREE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.woodSupportV,1,wild ), new AspectList().add(Aspect.TREE, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.woodSupportH,1,wild ), new AspectList().add(Aspect.TREE, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.woodSupportV2,1,wild), new AspectList().add(Aspect.TREE, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.woodSupportH2,1,wild), new AspectList().add(Aspect.TREE, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerComplexObjectTag(new ItemStack(TFCBlocks.grass,1,wild), new AspectList().add(Aspect.PLANT, 1).add(Aspect.EARTH, 1));
		ThaumcraftApi.registerComplexObjectTag(new ItemStack(TFCBlocks.grass2,1,wild), new AspectList().add(Aspect.PLANT, 1).add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.dirt,1,wild), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.dirt2,1,wild), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.clay,1,wild), new AspectList().add(Aspect.EARTH, 3).add(Aspect.WATER,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.clay2,1,wild), new AspectList().add(Aspect.EARTH, 3).add(Aspect.WATER,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.clayGrass,1,wild), new AspectList().add(Aspect.EARTH, 3).add(Aspect.WATER,3).add(Aspect.PLANT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.clayGrass2,1,wild), new AspectList().add(Aspect.EARTH, 3).add(Aspect.WATER,3).add(Aspect.PLANT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.peat,1,wild), new AspectList().add(Aspect.EARTH, 3).add(Aspect.ENERGY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.peatGrass,1,wild), new AspectList().add(Aspect.EARTH, 3).add(Aspect.ENERGY,1).add(Aspect.PLANT,1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.worldItem), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.logPile), new AspectList().add(Aspect.TREE,5).add(Aspect.ORDER,5));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.tilledSoil,1,wild), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.tilledSoil2,1,wild), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.firepit), new AspectList().add(Aspect.ENERGY, 3).add(Aspect.FIRE,3));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.bellows), new AspectList().add(Aspect.TREE, 6).add(Aspect.AIR,4).add(Aspect.ORDER,2).add(Aspect.CLOTH,1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.forge,1,wild), new AspectList().add(Aspect.FIRE, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.blastFurnace), new AspectList().add(Aspect.FIRE, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.molten), new AspectList().add(Aspect.FIRE, 2).add(Aspect.METAL,2));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.sluice), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.fruitTreeWood,1,wild), new AspectList().add(Aspect.TREE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.fruitTreeLeaves,1,wild), new AspectList().add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.fruitTreeLeaves2,1,wild), new AspectList().add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneStairs,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneSlabs,1,wild), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.stoneStalac,1,wild), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.sand,1,wild), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.sand2,1,wild), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.dryGrass,1,wild), new AspectList().add(Aspect.EARTH, 1).add(Aspect.PLANT,1).add(Aspect.AIR,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.dryGrass2,1,wild), new AspectList().add(Aspect.EARTH, 1).add(Aspect.PLANT,1).add(Aspect.AIR,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.tallGrass,1,wild), new AspectList().add(Aspect.AIR, 1).add(Aspect.PLANT,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.charcoal), new AspectList().add(Aspect.FIRE, 2).add(Aspect.ENERGY,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.detailed), new AspectList().add(Aspect.CRAFT, 5));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.woodConstruct), new AspectList().add(Aspect.TREE, 1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.woodVert), new AspectList().add(Aspect.EARTH, 1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.woodHoriz), new AspectList().add(Aspect.EARTH, 1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.woodHoriz2), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.toolRack,1,wild), new AspectList().add(Aspect.TOOL, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.spawnMeter), new AspectList().add(Aspect.VOID, 2).add(Aspect.DARKNESS,2).add(Aspect.SENSES,1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.foodPrep), new AspectList().add(Aspect.EARTH, 1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.quern), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.wallCobbleIgIn,1,wild), new AspectList()    .add(Aspect.EARTH, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.wallCobbleIgEx,1,wild), new AspectList()    .add(Aspect.EARTH, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.wallCobbleSed,1,wild), new AspectList()     .add(Aspect.EARTH, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.wallCobbleMM,1,wild), new AspectList()      .add(Aspect.EARTH, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.wallRawIgIn,1,wild), new AspectList()       .add(Aspect.EARTH, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.wallRawIgEx,1,wild), new AspectList()       .add(Aspect.EARTH, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.wallRawSed,1,wild), new AspectList()        .add(Aspect.EARTH, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.wallRawMM,1,wild), new AspectList()         .add(Aspect.EARTH, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.wallBrickIgIn,1,wild), new AspectList()     .add(Aspect.EARTH, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.wallBrickIgEx,1,wild), new AspectList()     .add(Aspect.EARTH, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.wallBrickSed,1,wild), new AspectList()      .add(Aspect.EARTH, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.wallBrickMM,1,wild), new AspectList()       .add(Aspect.EARTH, 1).add(Aspect.ORDER,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.wallSmoothIgIn,1,wild), new AspectList()    .add(Aspect.EARTH, 1).add(Aspect.ORDER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.wallSmoothIgEx,1,wild), new AspectList()    .add(Aspect.EARTH, 1).add(Aspect.ORDER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.wallSmoothSed,1,wild), new AspectList()     .add(Aspect.EARTH, 1).add(Aspect.ORDER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.wallSmoothMM,1,wild), new AspectList()      .add(Aspect.EARTH, 1).add(Aspect.ORDER, 1));



		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.ingotPile), new AspectList().add(Aspect.METAL, 5));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.barrel, 1, wild), new AspectList().add(Aspect.TREE, 2).add(Aspect.VOID, 4).add(Aspect.EXCHANGE, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.loom,1,wild), new AspectList().add(Aspect.TREE, 4));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.pottery), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.thatch), new AspectList().add(Aspect.CROP, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.moss), new AspectList().add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.berryBush,1,wild), new AspectList().add(Aspect.PLANT, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.crops), new AspectList().add(Aspect.HUNGER, 5).add(Aspect.CROP, 5));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.lilyPad), new AspectList().add(Aspect.WATER, 1).add(Aspect.PLANT,2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.flowers,1,wild), new AspectList().add(Aspect.PLANT, 1).add(Aspect.SENSES, 1).add(Aspect.LIFE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.flowers2,1,wild), new AspectList().add(Aspect.PLANT, 1).add(Aspect.SENSES, 1).add(Aspect.LIFE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.fungi,1,0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.PLANT, 1).add(Aspect.DARKNESS,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.fungi,1,1), new AspectList().add(Aspect.EARTH, 1).add(Aspect.PLANT, 1).add(Aspect.FIRE,1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.flora,1,wild), new AspectList().add(Aspect.WATER, 1).add(Aspect.PLANT,1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.bloom), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.crucible), new AspectList().add(Aspect.FIRE, 5).add(Aspect.EARTH,5).add(Aspect.VOID, 2).add(Aspect.EXCHANGE, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.fireBrick), new AspectList().add(Aspect.EARTH, 2).add(Aspect.FIRE, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.metalSheet), new AspectList().add(Aspect.METAL, 4));
//TODO add thaumium metal sheet
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.nestBox), new AspectList().add(Aspect.TREE, 2).add(Aspect.LIFE,1).add(Aspect.CROP, 1));

		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.fence), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.fenceGate,1,wild), new AspectList().add(Aspect.TRAVEL, 1).add(Aspect.MECHANISM, 1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.fence2), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.fenceGate2,1,wild), new AspectList().add(Aspect.TRAVEL, 1).add(Aspect.MECHANISM,1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.strawHideBed), new AspectList().add(Aspect.CLOTH, 2).add(Aspect.BEAST,2).add(Aspect.CROP,2));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.armorStand,1,wild), new AspectList().add(Aspect.TREE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.armorStand2,1,wild), new AspectList().add(Aspect.TREE, 1));

		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.logNatural), new AspectList().add(Aspect.EARTH, 1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.logNatural2), new AspectList().add(Aspect.EARTH, 1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.woodHoriz3), new AspectList().add(Aspect.EARTH, 1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.woodHoriz4), new AspectList().add(Aspect.EARTH, 1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.woodVert2), new AspectList().add(Aspect.EARTH, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.saltWater), new AspectList().add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.saltWaterStationary), new AspectList().add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.freshWater), new AspectList().add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.freshWaterStationary), new AspectList().add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.hotWater), new AspectList().add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.hotWaterStationary), new AspectList().add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.lava), new AspectList().add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.lavaStationary), new AspectList().add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.ice,1,wild), new AspectList().add(Aspect.COLD, 4));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.waterPlant,1,wild), new AspectList().add(Aspect.PLANT, 1).add(Aspect.WATER, 1));

		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.bookshelf), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.torch), new AspectList().add(Aspect.LIGHT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.chest, 1, wild), new AspectList().add(Aspect.VOID, 4));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.workbench), new AspectList().add(Aspect.CRAFT, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.cactus), new AspectList().add(Aspect.WATER, 1).add(Aspect.PLANT,3).add(Aspect.ENTROPY, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.reeds), new AspectList().add(Aspect.PLANT, 1).add(Aspect.AIR,1).add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.pumpkin), new AspectList().add(Aspect.CROP, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.litPumpkin), new AspectList().add(Aspect.CROP, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.buttonWood), new AspectList().add(Aspect.MECHANISM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.vine), new AspectList().add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.leatherRack), new AspectList().add(Aspect.BEAST, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.gravel,1,wild), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.gravel2,1,wild), new AspectList().add(Aspect.EARTH, 2));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.grill), new AspectList().add(Aspect.METAL, 2).add(Aspect.MECHANISM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.metalTrapDoor), new AspectList().add(Aspect.METAL, 1).add(Aspect.MOTION, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.vessel,1,1), new AspectList().add(Aspect.VOID, 3).add(Aspect.EXCHANGE,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.vessel,1,0), new AspectList().add(Aspect.EARTH,2).add(Aspect.WATER, 2).add(Aspect.CRAFT, 1));

		//ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.smoke), new AspectList().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.smokeRack), new AspectList().add(Aspect.CLOTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.snow), new AspectList().add(Aspect.COLD, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.oilLamp,1,wild), new AspectList().add(Aspect.LIGHT, 3).add(Aspect.MECHANISM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.hopper), new AspectList().add(Aspect.MECHANISM, 1).add(Aspect.EXCHANGE, 1).add(Aspect.VOID, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.anvil, 1, wild), new AspectList().add(Aspect.CRAFT, 5).add(Aspect.TOOL, 2).add(Aspect.METAL, 42));
		ThaumcraftApi.registerObjectTag(new ItemStack(TFCBlocks.anvil2, 1, wild), new AspectList().add(Aspect.CRAFT, 5).add(Aspect.TOOL, 2).add(Aspect.METAL, 42));
		registerTTCBlocks();
	}
	public static void registerTTCBlocks()
	{

		//ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.blockMagicLog), new AspectList().add(Aspect.TREE,3).add(Aspect.MAGIC,1));

		//ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.blockMagicTrunk), new AspectList().add(Aspect.TREE,3).add(Aspect.MAGIC,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.blockMagicLog,1,0), new AspectList().add(Aspect.TREE,3).add(Aspect.MAGIC,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.blockMagicLog,1,1), new AspectList().add(Aspect.TREE, 3).add(Aspect.ORDER,1).add(Aspect.MAGIC,1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.blockMagicTrunk), new int[] {0,4,8}, new AspectList().add(Aspect.TREE, 3).add(Aspect.MAGIC,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.blockMagicTrunk), new int[] {1,5,9}, new AspectList().add(Aspect.TREE, 3).add(Aspect.ORDER,1).add(Aspect.MAGIC,1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.blockMagicLeaves,1,wild), new AspectList().add(Aspect.PLANT, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(TAPIBlocks.blockFakeIgExCobble,1,wild	), new AspectList().add(Aspect.EARTH,1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TAPIBlocks.blockFakeIgInCobble,1,wild	), new AspectList().add(Aspect.EARTH,1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TAPIBlocks.blockFakeMMCobble,1,wild	), new AspectList().add(Aspect.EARTH,1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TAPIBlocks.blockFakeSedCobble,1,wild	), new AspectList().add(Aspect.EARTH,1).add(Aspect.ENTROPY,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TAPIBlocks.blockMossIgExCobble,1,wild	), new AspectList().add(Aspect.PLANT,1).add(Aspect.MAGIC,1).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TAPIBlocks.blockMossIgInCobble,1,wild	), new AspectList().add(Aspect.PLANT,1).add(Aspect.MAGIC,1).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TAPIBlocks.blockMossMMCobble,1,wild	), new AspectList().add(Aspect.PLANT,1).add(Aspect.MAGIC,1).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TAPIBlocks.blockMossSedCobble,1,wild	), new AspectList().add(Aspect.PLANT,1).add(Aspect.MAGIC,1).add(Aspect.EARTH,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.blockMagicSapling,1,wild	), new AspectList().add(Aspect.TREE,1).add(Aspect.PLANT,2).add(Aspect.MAGIC,1));
		//ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.magicBarrel,1,wild			), new AspectList().add(Aspect.TREE,2).add(Aspect.EXCHANGE,2).add(Aspect.VOID,4));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.soulsand				), new AspectList().add(Aspect.EARTH,1).add(Aspect.TRAP,1).add(Aspect.SOUL,1));
		ThaumcraftApi.registerObjectTag(new ItemStack(TTCBlocks.tilledsoulsand			), new AspectList().add(Aspect.EARTH,1).add(Aspect.TRAP,1).add(Aspect.SOUL,1).add(Aspect.HARVEST,1));
		/*
			blockCrucible;
			blockThaumiumAnvil
			blockMagicOre;
		 */
	}
	public static void addLateAspects()
	{

	}
	public static AspectList processTFCFood(AspectList aspectsIn, ItemStack stack)
	{
		AspectList aspects;

		try
		{
			aspects = aspectsIn.copy();
		}
		catch(Exception e)
		{
			return null;
		}
		if(stack.getItem() instanceof IFood && aspects !=null)
		{
			IFood foodItem = (IFood) stack.getItem();
			float foodWeight = Food.getWeight(stack);
			float foodDecay = Math.max(Food.getDecay(stack), 0f);
			float foodAmount = Food.getWeight(stack) - foodDecay;

			int foodRatio = (int)(foodAmount/10f);//food ratio is 1 aspect per 10oz
			int foodExtrasRatio = (int)(foodAmount/32f);
			int mealRatio = (int)foodAmount;//sandwiches and salads have 1 aspect per oz

			for(Aspect as: aspects.getAspects())
			{
				float aspectAmount = aspects.getAmount(as);
				aspects.remove(as);
				int currentAspectAmount = (int)((aspectAmount/160f) *foodAmount);

				if(currentAspectAmount>0)
				{
					aspects.add(as, (int)((aspectAmount/160f) *foodAmount));
				}
			}
			if(foodDecay/foodWeight> 0.20f)
			{
				aspects.add(Aspect.ENTROPY, (int)(foodDecay/16f));
			}

			if(foodExtrasRatio>0)
			{
				if(Food.isCooked(stack))
				{
					aspects.add(Aspect.CRAFT, foodExtrasRatio);
					aspects.remove(Aspect.LIFE);
					aspects.remove(Aspect.BEAST);
					aspects.remove(Aspect.EARTH);
					aspects.remove(Aspect.SLIME);
					if(foodItem.getFoodGroup() == EnumFoodGroup.Protein )
					aspects.add(Aspect.FLESH, foodRatio/2);
					aspects.add(Aspect.HUNGER, foodExtrasRatio);
				}
				if(Food.isDried(stack) || (Food.isSmoked(stack) && Food.getSmokeCounter(stack) > 0))
				{
					aspects.add(Aspect.FIRE, foodExtrasRatio);
				}
				if((Food.isBrined(stack) || Food.isDried(stack) || (Food.isSmoked(stack) && Food.getSmokeCounter(stack) > 0)) && !Food.isCooked(stack))
				{
					aspects.add(Aspect.ORDER, foodExtrasRatio);
					if(Food.isPickled(stack))
					{
						aspects.add(Aspect.ORDER, foodExtrasRatio/2);
					}
				}
				if((Food.isBrined(stack) || Food.isDried(stack) || (Food.isSmoked(stack) && Food.getSmokeCounter(stack) > 0)) && Food.isCooked(stack) && foodExtrasRatio/2 >0)
				{
					aspects.add(Aspect.ORDER, foodExtrasRatio/2);
				}
				if(Food.isSalted(stack))
				{
					aspects.add(Aspect.CRYSTAL, foodExtrasRatio/2);
				}
			}
			if(Food.isInfused(stack))
			{
				String infusionName = Food.getInfusion(stack);
				IFood food = (IFood)getFoodFromName(infusionName);
				EnumFoodGroup fg = food.getFoodGroup();// FoodRegistry.getInstance().getFoodGroup(Item.getIdFromItem(food));
				int infusionratio = foodRatio/8;
				if(infusionratio > 0)
					switch (fg)
					{
						case Dairy:break;
						case Protein:aspects.add(Aspect.FLESH, infusionratio);break;
						case Vegetable: aspects.add(Aspect.PLANT, infusionratio); break;
						case Fruit: aspects.add(Aspect.PLANT,infusionratio); break;
						case Grain: aspects.add(Aspect.CROP, infusionratio); break;
						default:break;
					}
			}
			if(stack.getItem() instanceof ItemMeal && mealRatio >0)
			{
				int[] fgArray = Food.getFoodGroups(stack);
				ArrayList<EnumFoodGroup> fgs = new ArrayList<>();
				EnumFoodGroup[] foodgroups = new EnumFoodGroup[]{EnumFoodGroup.None,EnumFoodGroup.None,EnumFoodGroup.None,EnumFoodGroup.None,EnumFoodGroup.None};
				int[] quantitySandwich = new int[] {2,3,2,2,1};
				int[] quantitySalad = new int[] {10,4,4,2};
				for(int i = 0; i < fgArray.length; i++)
				{
					foodgroups[i] = FoodRegistry.getInstance().getFoodGroup(fgArray[i]);
					//fgs.add(FoodRegistry.getInstance().getFoodGroup(fgArray[i]));
				}
				//for(EnumFoodGroup enumFoodGroup: fgs)
				for(int i = 0; i<fgArray.length; i++)
				{
					EnumFoodGroup enumFoodGroup = foodgroups[i];
					int[] quantityArray;
					if(stack.getItem() instanceof ItemSandwich)
					{
						quantityArray = quantitySandwich;
					}
					else
					{
						quantityArray = quantitySalad;
					}
					switch (enumFoodGroup)
					{
						case Dairy:aspects.add(Aspect.EXCHANGE, quantityArray[i]);continue;
						case Protein:aspects.add(Aspect.FLESH, quantityArray[i]);continue;
						case Vegetable: aspects.add(Aspect.PLANT, quantityArray[i]); continue;
						case Fruit: aspects.add(Aspect.PLANT, quantityArray[i]); continue;
						case Grain: aspects.add(Aspect.CROP, quantityArray[i]); continue;
						default:continue;
					}
				}
			}


		}
		return cleanAspects(aspects);

	}

	public static AspectList cleanAspects(AspectList aspects)
	{
		for(Aspect aspect: aspects.getAspects())
		{
			if(aspects.getAmount(aspect) <=0)
			{
				aspects.remove(aspect);
			}
		}
		return aspects;
	}
	public static Item getFoodFromName(String foodName)
	{
		return GameRegistry.findItem(Reference.MOD_ID, foodName);
	}
	public static AspectList processUnmeltedMetals(ItemStack stack)
	{
		AspectList aspects =new AspectList();
		ItemMeltedMetal meltedMetal = (ItemMeltedMetal)stack.getItem();
		float metalAmount = (100f - stack.getItemDamage())/100f;
		Metal metal = MetalRegistry.instance.getMetalFromItem(meltedMetal);
		Item ingot = metal.ingot;
		AspectList ingotAspects = ThaumcraftCraftingManager.getObjectTags(new ItemStack(ingot, 1,0));
		for(Aspect aspect: ingotAspects.getAspects())
		{
			int amount = ingotAspects.getAmount(aspect);
			aspects.add(aspect, (int)(amount * metalAmount));
		}

		return cleanAspects(aspects);
	}

}
