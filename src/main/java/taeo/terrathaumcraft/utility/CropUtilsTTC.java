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

package taeo.terrathaumcraft.utility;

import java.util.ArrayList;

import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.TileEntities.TEBerryBush;
import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.TileEntities.TEFruitLeaves;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockStem;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.common.lib.utils.CropUtils;

public class CropUtilsTTC extends CropUtils{
	public static ArrayList<String> TFCCrops = new ArrayList();
	public static void addTFCCrop()
	{
		
	}
	public static boolean isGrownCrop(World world, int x, int y, int z)
	{
		//LogHelper.info("Checking for Grown Crops");
		if (world.isAirBlock(x, y, z)) {
			return false;
		}
		boolean found = false;
		Block bi = world.getBlock(x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		for (int a = 0; a < 16; a++) {
			if ((standardCrops.contains(bi.getUnlocalizedName() + a)) || (clickableCrops.contains(bi.getUnlocalizedName() + a)) || (stackedCrops.contains(bi.getUnlocalizedName() + a)))
			{
				found = true;
				break;
			}
			if(te !=null && (te instanceof TECrop || te instanceof TEFruitLeaves))
			{
				//LogHelper.info("Golem found TFC Crop");
				found = true;
				break;


			}
		}
		Block biA = world.getBlock(x, y + 1, z);
		Block biB = world.getBlock(x, y - 1, z);
		int md = world.getBlockMetadata(x, y, z);
		if(te != null && te instanceof TECrop)
		{
			TECrop teC = (TECrop) te;
			if(!world.isRemote)
			{
				CropIndex crop = CropManager.getInstance().getCropFromId(teC.cropId);
				if (crop != null && teC.growth >= crop.numGrowthStages)
				{
//					LogHelper.info("found grown crop " + crop.cropName);
//					LogHelper.info("Has " + crop.numGrowthStages + " Growth Stages");
//					LogHelper.info("Current growth phase : " + teC.growth);
					return true;

				}
				else
					return false;
			}
		}
		if(te != null && te instanceof TEFruitLeaves)
		{
			TEFruitLeaves teF = (TEFruitLeaves) te;
			if(teF.hasFruit)
			{
				return true;
			}
			else
				return false;
		}
		if(te != null && te instanceof TEBerryBush)
		{
			TEBerryBush teF = (TEBerryBush) te;
			if(teF.hasFruit)
			{
				return true;
			}
			else
				return false;
		}

		if ((((bi instanceof IGrowable)) && (!((IGrowable)bi).func_149851_a(world, x, y, z, world.isRemote)) && (!(bi instanceof BlockStem))) || (((bi instanceof BlockCrops)) && (md == 7) && (!found)) || ((bi == Blocks.nether_wart) && (md >= 3)) || ((bi == Blocks.cocoa) && ((md & 0xC) >> 2 >= 2)) || (standardCrops.contains(bi.getUnlocalizedName() + md)) || (clickableCrops.contains(bi.getUnlocalizedName() + md)) || ((stackedCrops.contains(bi.getUnlocalizedName() + md)) && (biB == bi))) {
			return true;
		}
		return false;
	}
	
	
	
	

	public static void golemHarvestTTC(World theWorld, EntityPlayer player, int x, int y, int z)
	{
		TileEntity te = theWorld.getTileEntity(x, y, z);
		if(te != null&& te instanceof TECrop)
		{
			TECrop tec = (TECrop) te;
			tec.onHarvest(theWorld, player, true);
		}
	}
	
}
