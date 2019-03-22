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

package taeo.terrathaumcraft.proxy;


import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import taeo.terrathaumcraft.TerraThaumcraftMod;
import taeo.terrathaumcraft.init.TTCEntities;
import taeo.terrathaumcraft.init.TTCItems;
import taeo.terrathaumcraft.item.WandManagerTTC;
import taeo.terrathaumcraft.potion.*;
import taeo.terrathaumcraft.reference.Names;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.terrathaumcraft.tile.*;
import taeo.terrathaumcraft.worldgen.InfusedOreGenerator;
import taeo.terrathaumcraft.worldgen.MagicBiomeDecorator;
import taeo.terrathaumcraft.worldgen.MagicBiomePlacer;
import taeo.ttfcapi.worldgen.ExtendedOreGenTAPI;
import taeo.ttfcapi.worldgen.ExtendedOreSpawnData;

import taeo.ttfcapi.proxy.IProxy;
import taeo.ttfcapi.reference.ReferenceTAPI;
import thaumcraft.api.potions.PotionFluxTaint;
import thaumcraft.common.lib.potions.PotionSunScorned;
import thaumcraft.common.lib.potions.PotionUnnaturalHunger;

import java.util.ArrayList;


public class CommonProxy implements IProxy
{
	public static WandManagerTTC wandManager = new WandManagerTTC();

	@Override
	public void initKeyBinding()
	{
		//Do Nothing
	}

	@Override
	public void initRenderers()
	{
		//Do Nothing
	}

	public void initGuiHandler() {
		NetworkRegistry.INSTANCE.registerGuiHandler(TerraThaumcraftMod.instance, new taeo.terrathaumcraft.gui.GuiHandlerTTC());

	}

    @Override
    public void initTileEntities()
    {
        this.registerTileEntities(true);
    }


    public void registerTileEntities(boolean isServer){
        if(isServer)
        {
            GameRegistry.registerTileEntity(TEAnvilTTC.class, "TileAnvilTTC");
            GameRegistry.registerTileEntity(TileCrucibleTTC.class, "TileCrucibleTTC");
            GameRegistry.registerTileEntity(TEChestTTC.class, "chestTTC");
        }

        GameRegistry.registerTileEntity(TEFarmlandTTC.class, "TileFarmlandTTC");
		GameRegistry.registerTileEntity(TEMagicOre.class, Names.TileEntities.MAGIC_ORE);
		GameRegistry.registerTileEntity(TEMagicBarrel.class, "TileMagicBarrelTTC");
		GameRegistry.registerTileEntity(TELoomTTC.class, "TileLoomTTC");
        GameRegistry.registerTileEntity(TECropTTC.class, "TileCropTTC");
	}

	@Override
	public void initToolClasses()
	{
        TTCItems.thaumiumAxe.setHarvestLevel("axe", 3);
	}

	public void initOres()
	{
		/*ArrayList<SpawnRequirement> firereqs = new ArrayList<SpawnRequirement>();
		ArrayList<SpawnRequirement> firereqs2 = new ArrayList<SpawnRequirement>();

		firereqs.add(new SpawnRequirement.Volcanic());
		firereqs2.add(new SpawnRequirement.Desert());
*/
		//ExtendedOreGenTTC.addToOreList(new ExtendedOreSpawnData("default", "small", ReferenceTTC.MOD_ID + ":" + Names.Blocks.MAGIC_ORE,/*meta*/ ReferenceTTC.OreMeta.FIRE, /*rarity*/75, ReferenceTAPI.allRockTypes).addRequirements(firereqs).setMinGrade(1), 0);
		//ExtendedOreGenTTC.addToOreList(new ExtendedOreSpawnData("default", "small", ReferenceTTC.MOD_ID + ":" + Names.Blocks.MAGIC_ORE,/*meta*/ ReferenceTTC.OreMeta.FIRE, /*rarity*/75, ReferenceTAPI.allRockTypes).addRequirements(firereqs2).setMinGrade(1), 0);
/*
		ArrayList<SpawnRequirement> waterreqs = new ArrayList<SpawnRequirement>();

		ArrayList<BiomeGenBase> waterbiomes = new ArrayList();
		waterbiomes.add(TFCBiome.LAKE);
		waterbiomes.add(TFCBiome.DEEP_OCEAN);
		waterbiomes.add(TFCBiome.BEACH);
		waterbiomes.add(TFCBiome.GRAVEL_BEACH);
		waterbiomes.add(TFCBiome.OCEAN);

		SpawnRequirement.Biomes waterspawn = new SpawnRequirement.Biomes();
		waterspawn.include(waterbiomes);
		waterreqs.add(waterspawn);
*/
		//ExtendedOreGenTTC.addToOreList(new ExtendedOreSpawnData("default", "small", ReferenceTTC.MOD_ID + ":" + Names.Blocks.MAGIC_ORE, ReferenceTTC.OreMeta.WATER, 75, ReferenceTAPI.allRockTypes).addRequirements(waterreqs).setMinGrade(1), 0);

		//ExtendedOreGenTTC.addToOreList(new ExtendedOreSpawnData("default", "small", ReferenceTTC.MOD_ID + ":" + Names.Blocks.MAGIC_ORE,/*meta*/ ReferenceTTC.OreMeta.AIR, /*rarity*/250, ReferenceTAPI.allRockTypes).setMaxGrade(0));
		//ExtendedOreGenTTC.addToOreList(new ExtendedOreSpawnData("default", "small", ReferenceTTC.MOD_ID + ":" + Names.Blocks.MAGIC_ORE,/*meta*/ ReferenceTTC.OreMeta.EARTH, /*rarity*/250, ReferenceTAPI.allRockTypes).setMaxGrade(0));
		//ExtendedOreGenTTC.addToOreList(new ExtendedOreSpawnData("default", "small", ReferenceTTC.MOD_ID + ":" + Names.Blocks.MAGIC_ORE,/*meta*/ ReferenceTTC.OreMeta.FIRE, /*rarity*/250, ReferenceTAPI.allRockTypes).setMaxGrade(0));
		//ExtendedOreGenTTC.addToOreList(new ExtendedOreSpawnData("default", "small", ReferenceTTC.MOD_ID + ":" + Names.Blocks.MAGIC_ORE,/*meta*/ ReferenceTTC.OreMeta.WATER, /*rarity*/250, ReferenceTAPI.allRockTypes).setMaxGrade(0));
		//ExtendedOreGenTTC.addToOreList(new ExtendedOreSpawnData("default", "small", ReferenceTTC.MOD_ID + ":" + Names.Blocks.MAGIC_ORE,/*meta*/ ReferenceTTC.OreMeta.ENTROPY, /*rarity*/250, ReferenceTAPI.allRockTypes).setMaxGrade(0));
		//ExtendedOreGenTTC.addToOreList(new ExtendedOreSpawnData("default", "small", ReferenceTTC.MOD_ID + ":" + Names.Blocks.MAGIC_ORE,/*meta*/ ReferenceTTC.OreMeta.ORDER, /*rarity*/250, ReferenceTAPI.allRockTypes).setMaxGrade(0));

		if(ReferenceTTC.enableWorldGen)
		{
			ExtendedOreGenTAPI.addToOreList(new ExtendedOreSpawnData("default", "small", ReferenceTTC.MOD_ID + ":" + Names.Blocks.MAGIC_ORE,/*meta*/ ReferenceTTC.OreMeta.AMBER, /*rarity*/ReferenceTTC.amberOreRarity, ReferenceTAPI.allRockTypes));
			ExtendedOreGenTAPI.addToOreList(new ExtendedOreSpawnData("default", "small", ReferenceTTC.MOD_ID + ":" + Names.Blocks.MAGIC_ORE,/*meta*/ ReferenceTTC.OreMeta.CINNABAR, /*rarity*/ReferenceTTC.cinnabarOreRarity, ReferenceTAPI.allRockTypes));
		}

		//WorldGenOre.oreList.put("NetherQuartz", new OreSpawnData("veins", "small", ReferenceTTC.MOD_ID + ":" + Names.Blocks.MAGIC_ORE, ReferenceTTC.OreMeta.QUARTZ, 100, new String[]{"granite", "shale", "schist", "gneiss", "quartzite"}, 5, 128, 80, 60));
		//TODO remember to add netherQuartz to API
		//WorldGenOre.OreList.put("air", new OreSpawnData("veins", "large", ReferenceTTC.MOD_ID + ":" +Names.Blocks.MAGIC_ORE, 1, 200, new String[]{"igneous intrusive", "igneous extrusive", "sedimentary", "metamorphic"} ,5,128,80,60));
	}

	@Override
	public void initWorldGen()
	{
		//GameRegistry.registerWorldGenerator(new MagicBiomePlacer(), 5);
		//GameRegistry.registerWorldGenerator(new MagicBiomeDecorator(), 6);
		if(ReferenceTTC.enableWorldGen)
		{
			GameRegistry.registerWorldGenerator(new MagicBiomePlacer(), 2);
			GameRegistry.registerWorldGenerator(new MagicBiomeDecorator(), 4);
			GameRegistry.registerWorldGenerator(new InfusedOreGenerator(), 3);
		}

	}

	@Override
	public void initEntities()
	{
		TTCEntities.init();
	}

    @Override
    public void initPotions()
    {
		PotionUnnaturalHunger.instance = new PotionUnnaturalHungerTTC();
        PotionUnnaturalHungerTTC.init();

        PotionFluxTaint.instance = new PotionFluxTaintTTC();
        PotionFluxTaintTTC.init();

        PotionSunScorned.instance = new PotionSunScornedTTC();
        PotionSunScornedTTC.init();
    }

	@Override
	public void initBlocks() {

	}

	@Override
	public void initItems() {

	}

	@Override
	public void initRecipes() {

	}

	@Override
	public void initLateRecipes() {

	}

	@Override
	public void initEventHandlers() {

	}

	@Override
	public void initFluids() {

	}
}
