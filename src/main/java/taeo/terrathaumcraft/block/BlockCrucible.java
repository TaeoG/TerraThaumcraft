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

package taeo.terrathaumcraft.block;

import java.util.List;

import taeo.terrathaumcraft.tile.TileCrucibleTTC;

import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;

import thaumcraft.common.blocks.BlockMetalDevice;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BlockCrucible extends BlockMetalDevice{

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		
	}
	
	public TileEntity createTileEntity(World world, int metadata)
	{
		return new TileCrucibleTTC();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ)
	{
		ItemStack inHand = player.getCurrentEquippedItem();
		FluidStack bucketowater = new FluidStack(TFCFluids.FRESHWATER, 1000);
		if(inHand !=null && inHand.getItem() != null && FluidContainerRegistry.containsFluid(inHand, bucketowater))
		{
			TileCrucibleTTC entity = (TileCrucibleTTC) world.getTileEntity(x, y, z);
			entity.fill(ForgeDirection.UNKNOWN, bucketowater, true);
			//System.out.println("Attempting to drain fluids out of " + inHand.getItem().getUnlocalizedName());
			player.setCurrentItemOrArmor(0, FluidContainerRegistry.drainFluidContainer(inHand));
			return true;
		}
		TileEntity heatSource = world.getTileEntity(x, y-1, z);
		Block heatSourceBlock = world.getBlock(x, y-1, z);
		if(heatSource != null && heatSource instanceof TEFireEntity && hitY < 0.2f)
		{
			return heatSourceBlock.onBlockActivated(world, x, y-1, z, player, meta, hitX, hitY, hitZ);
		}
		return super.onBlockActivated(world, x, y, z, player, meta, hitX, hitY, hitZ);
	}
	
	

}
