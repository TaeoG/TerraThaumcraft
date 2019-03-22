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

package taeo.terrathaumcraft.worldgen;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.WorldGen.Generators.OreSpawnData;
import com.bioxx.tfc.WorldGen.Generators.WorldGenMinable;
import com.bioxx.tfc.WorldGen.TFCWorldChunkManager;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.terrathaumcraft.reference.Names;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.reference.ReferenceTAPI;
import taeo.ttfcapi.utility.LogHelper;
import taeo.ttfcapi.worldgen.ExtendedOreGenTAPI;
import thaumcraft.api.aspects.Aspect;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class InfusedOreGenerator implements IWorldGenerator {

	OreSpawnData osd;
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId)
		{
			case 0: //Overworld
				generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
				break;
			case 1:    //Nether

				break;
			case 2: //End

				break;
		}
	}

	private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

		Aspect aspect = ClimateHandler.getRandomClimateTag(world, chunkX * 16, 150, chunkZ * 16);
		int meta;
		int miny = 5;
		int maxy = 150;
		int rarity = 200;
		if(aspect == Aspect.PLANT)
		{
			aspect = Aspect.EARTH;
		}

		meta = ReferenceTTC.OreMeta.getMetaFromAspect(aspect);

		if(aspect == Aspect.EARTH)
		{
			miny = 50;
			maxy = 80;
			rarity = ReferenceTTC.earthOreRarity;
		}
		if(aspect == Aspect.AIR)
		{
			miny = 100;
			maxy = 240;
			rarity = ReferenceTTC.airOreRarity;
		}
		if(aspect == Aspect.WATER)
		{
			miny = 60;
			rarity = ReferenceTTC.waterOreRarity;
		}
		if(aspect == Aspect.FIRE)
		{
			miny = 100;
			rarity = ReferenceTTC.fireOreRarity;
		}
		if(aspect == Aspect.ORDER)
		{
			miny = 60;
			maxy = 100;
			rarity = ReferenceTTC.orderOreRarity;
		}
		if(aspect == Aspect.ENTROPY)
		{
			maxy = 50;
			rarity = ReferenceTTC.chaosOreRarity;
		}




		osd = new OreSpawnData("default","medium",ReferenceTTC.MOD_ID + ":" + Names.Blocks.MAGIC_ORE, meta, rarity, ReferenceTAPI.allRockTypes);
		if(meta < 0)
		{
			//ExtendedOreGenTAPI.createOre(TTCBlocks.blockMagicOre, ReferenceTTC.OreMeta.getRandomInfusedMeta(random), null, 10, 20, 15, 5, 80, 80, 60, world, world.rand, chunkX * 16, chunkZ * 16, 0, 200, 0, 0);
			return;
		}
		createOre(
				osd.block,
				osd.meta,
				osd.base,
				rarity,
				30 /**vein size*/,
				30 /** vein amount*/,
				20 /**height*/,
				20 /**Diameter*/,
				80/**vdensity*/,
				60/**hdensity*/,
				world,
				world.rand,
				chunkX * 16,
				chunkZ * 16,
				miny /**miny*/,
				maxy /**maxy*/,
				0 /**mingrade*/,
				2 /**maxgrade*/);

		//ExtendedOreGenTAPI.createOre(TTCBlocks.blockMagicOre, meta, null, 10, 20, 30, 5, 20, 80, 60, world, world.rand, chunkX * 16, chunkZ * 16, 0, 200, 1, 2);
	}
	private static void createOre(Block block, int meta, Map<Block, List<Integer>> layers, int rarity, int veinSize,
								  int veinAmount, int height, int diameter, int vDensity, int hDensity, World world, Random rand, int chunkX, int chunkZ, int min, int max, int mingrade, int maxgrade)
	{
		if(world.getWorldChunkManager() instanceof TFCWorldChunkManager)
		{
			for(Block b : layers.keySet())
			{
				for(int layerMetadata : layers.get(b))
				{
					DataLayer rockLayer1 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 0);
					DataLayer rockLayer2 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 1);
					DataLayer rockLayer3 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 2);
					if (rockLayer1.block == b && (rockLayer1.data2 == layerMetadata || layerMetadata == -1) ||
							rockLayer2.block == b && (rockLayer2.data2 == layerMetadata || layerMetadata == -1) ||
							rockLayer3.block == b && (rockLayer3.data2 == layerMetadata || layerMetadata == -1))
					{
						int grade = rand.nextInt(100);
						if(grade<20)
							grade = 1;
						else if(grade <50)
							grade = 2;
						else
							grade = 0;

						if(grade > maxgrade)
						{
							grade = maxgrade;
						}else if (grade < mingrade)
						{
							grade = mingrade;
						}

						new WorldGenMinableTTC(block, meta, b, layerMetadata, rarity, veinSize, veinAmount, height, diameter, vDensity, hDensity, false, grade)
								.generate(world, rand, chunkX, chunkZ, min, max);
					}
				}
			}
		}
	}
}
