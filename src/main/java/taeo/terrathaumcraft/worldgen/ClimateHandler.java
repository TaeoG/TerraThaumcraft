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
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.WorldGen.TFCBiome;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import scala.tools.nsc.backend.jvm.BCodeSkelBuilder;
import taeo.ttfcapi.worldgen.SpawnRequirement;
import taeo.ttfcapi.worldgen.WorldWrapper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;
import thaumcraft.common.lib.world.biomes.BiomeHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClimateHandler {

	public static ArrayList<BiomeDictionary.Type> getClimateTags(World world, int xCoord, int yCoord, int zCoord)
	{
		if(world instanceof WorldWrapper)
		{
			world = ((WorldWrapper)world).getWrappedWorld();
		}
		Set<BiomeDictionary.Type> output = new HashSet<>();
		SpawnRequirement.Desert desert = new SpawnRequirement.Desert();
		SpawnRequirement.AcaciaJungle aJungle = new SpawnRequirement.AcaciaJungle();
		SpawnRequirement.KapokJungle kJungle = new SpawnRequirement.KapokJungle();
		SpawnRequirement.Forest forest = new SpawnRequirement.Forest();
		SpawnRequirement.Volcanic volcanic = new SpawnRequirement.Volcanic();
		float temperatureAvg = TFC_Climate.getBioTemperature(world, xCoord, zCoord);
		float evt = TFC_Climate.getCacheManager(world).getEVTLayerAt(xCoord + 8, zCoord + 8).floatdata1;
		float rainfall = TFC_Climate.getRainfall(world, xCoord, 0, zCoord);
		int xChunk = xCoord/16;
		int zChunk = zCoord/16;
		BiomeGenBase biome = world.provider.getBiomeGenForCoords(xCoord,zCoord);

		if(desert.isValid(world, xChunk, yCoord, zChunk))
		{
			output.add(BiomeDictionary.Type.SANDY);
			output.add(BiomeDictionary.Type.SPARSE);
		}
		if(aJungle.isValid(world, xChunk, yCoord, zChunk) || kJungle.isValid(world, xChunk, yCoord, zChunk))
		{
			output.add(BiomeDictionary.Type.JUNGLE);
			output.add(BiomeDictionary.Type.FOREST);
			output.add(BiomeDictionary.Type.DENSE);
		}
		if(forest.isValid(world, xChunk, yCoord, zChunk))
		{
			output.add(BiomeDictionary.Type.FOREST);
		}else
		{
			output.add(BiomeDictionary.Type.SPARSE);
		}
		if(volcanic.isValid(world,xChunk,yCoord,zChunk))
		{
			output.add(BiomeDictionary.Type.NETHER);
		}

		if(temperatureAvg <-15)
		{
			output.add(BiomeDictionary.Type.WASTELAND);
		}

		if(rainfall < 500)
		{
			output.add(BiomeDictionary.Type.DRY);
		}
		if(rainfall > 1200)
		{
			output.add(BiomeDictionary.Type.WET);
		}
		if(TFC_Core.isBeachBiome(biome.biomeID))
		{
			output.add(BiomeDictionary.Type.BEACH);
		}
		if(TFC_Core.isMountainBiome(biome.biomeID))
		{
			output.add(BiomeDictionary.Type.MOUNTAIN);
		}
		if(biome == TFCBiome.SWAMPLAND || TFC_Climate.isSwamp(world, xCoord,150,zCoord))
		{
			output.add(BiomeDictionary.Type.SWAMP);
		}
		if(TFC_Core.isWaterBiome(biome))
		{
			output.remove(BiomeDictionary.Type.FOREST);
			output.add(BiomeDictionary.Type.WATER);
		}
		if(TFC_Core.isOceanicBiome(biome.biomeID))
		{
			output.clear();
			output.add(BiomeDictionary.Type.OCEAN);
		}

		if(temperatureAvg > 25)
		{
			output.add(BiomeDictionary.Type.HOT);
		}
		if(temperatureAvg<0)
		{
			output.add(BiomeDictionary.Type.COLD);
		}
		if(biome == TFCBiome.RIVER)
		{
			output.add(BiomeDictionary.Type.RIVER);
		}
		if(biome == TFCBiome.ROLLING_HILLS || biome == TFCBiome.HIGH_HILLS || biome == TFCBiome.HIGH_HILLS_EDGE)
		{
			output.add(BiomeDictionary.Type.HILLS);
		}
		if(biome == TFCBiome.HIGH_PLAINS || biome == TFCBiome.PLAINS)
		{
			output.add(BiomeDictionary.Type.PLAINS);
		}

		if(biome == ThaumcraftWorldGenerator.biomeTaint)
		{
			output.add(BiomeDictionary.Type.MAGICAL);
			output.add(BiomeDictionary.Type.WASTELAND);
		}
		if(biome == ThaumcraftWorldGenerator.biomeEerie)
		{
			output.add(BiomeDictionary.Type.SPOOKY);
			output.add(BiomeDictionary.Type.MAGICAL);
		}
		if(biome == ThaumcraftWorldGenerator.biomeMagicalForest)
		{
			output.add(BiomeDictionary.Type.MAGICAL);
			output.add(BiomeDictionary.Type.FOREST);
			output.remove(BiomeDictionary.Type.SPARSE);
		}
		if(biome == ThaumcraftWorldGenerator.biomeEldritchLands)
		{
			output.add(BiomeDictionary.Type.MAGICAL);
			output.add(BiomeDictionary.Type.SPOOKY);
			output.add(BiomeDictionary.Type.END);
		}

		ArrayList list = new ArrayList();
		list.addAll(output);
		return list;
	}
public static Aspect getRandomClimateTag(World world, int xCoord, int yCoord, int zCoord)
{
	ArrayList<BiomeDictionary.Type> types = getClimateTags(world, xCoord,yCoord,zCoord);
	if(types.isEmpty())
	{
		return Aspect.EARTH;
	}
	BiomeDictionary.Type type = types.get(world.rand.nextInt(types.size()));
	return (Aspect) BiomeHandler.biomeInfo.get(type).get(1);
}
	public static int getClimateAura(World world, int xCoord, int yCoord, int zCoord) {
		try {
			ArrayList<BiomeDictionary.Type> types = getClimateTags(world, xCoord, yCoord, zCoord);
			int average = 0;
			int count = 0;
			for (BiomeDictionary.Type type : types) {
				average += ((Integer)(BiomeHandler.biomeInfo.get(type)).get(0)).intValue();
				count++;
			}
			return average / count;
		} catch (Exception e) {}
		return 100;
	}
	public static float getClimateSupportsGreatwood(World world, int xCoord, int yCoord, int zCoord) {
		try {
			ArrayList<BiomeDictionary.Type> types = getClimateTags(world, xCoord, yCoord, zCoord);
			float max = 0f;
			for (BiomeDictionary.Type type : types) {
				if (((boolean)((BiomeHandler.biomeInfo.get(type)).get(2))))
				{
					float chance = (float) BiomeHandler.biomeInfo.get(type).get(3);
					if(max < chance)
					{
						max = chance;
					}
				}
			}
			return max;
		} catch (Exception e) {}
		return 0.0F;
	}


}
