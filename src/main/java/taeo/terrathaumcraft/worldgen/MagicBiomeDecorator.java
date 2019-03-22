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

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.terrathaumcraft.utility.UtilsTTC;
import taeo.ttfcapi.utility.LogHelper;
import taeo.ttfcapi.utility.UtilsTAPI;
import thaumcraft.common.lib.utils.Utils;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;
import thaumcraft.common.lib.world.WorldGenManaPods;
import thaumcraft.common.lib.world.biomes.BiomeGenMagicalForest;
import thaumcraft.common.lib.world.biomes.BiomeGenTaint;

import java.util.ArrayList;
import java.util.Random;

public class MagicBiomeDecorator implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
		World wrappedWorld = UtilsTAPI.getWrappedWorld(world);
		BiomeGenBase biome = world.getBiomeGenForCoords(chunkX*16,chunkZ*16);
        if(biome == ThaumcraftWorldGenerator.biomeTaint)
        {

            BiomeGenTaint taintBiome = (BiomeGenTaint) ThaumcraftWorldGenerator.biomeTaint;
			taintBiome.theBiomeDecorator.currentWorld = null;
            taintBiome.decorateSpecial(wrappedWorld, random, chunkX * 16, chunkZ * 16);
        }
		else if(biome == ThaumcraftWorldGenerator.biomeMagicalForest)
        {
			try
			{
				BiomeGenMagicalForest magicBiome = (BiomeGenMagicalForest) ThaumcraftWorldGenerator.biomeMagicalForest;
				magicBiome.theBiomeDecorator.currentWorld = null;
				magicBiome.decorate(wrappedWorld, random, chunkX * 16, chunkZ * 16);
			}
			catch(Exception e)
			{
				LogHelper.error(ReferenceTTC.MOD_NAME,e);
				LogHelper.error(ReferenceTTC.MOD_NAME,e.getStackTrace());
				e.printStackTrace();
			}
		}
    }
}
