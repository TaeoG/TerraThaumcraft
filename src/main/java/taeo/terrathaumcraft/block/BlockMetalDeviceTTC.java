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

import com.bioxx.tfc.api.Interfaces.ISize;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import taeo.terrathaumcraft.tile.TileCrucibleTTC;
import thaumcraft.common.blocks.BlockMetalDevice;
import thaumcraft.common.tiles.*;

public class BlockMetalDeviceTTC extends BlockMetalDevice {


	public TileEntity createTileEntity(World world, int metadata)
	{
		if (metadata == 0) return new TileCrucibleTTC();
		if (metadata == 5) return new TileGrate();
		if (metadata == 6) return new TileGrate();
		if (metadata == 1) return new TileAlembic();
		if (metadata == 7) return new TileArcaneLamp();
		if (metadata == 8) { return new TileArcaneLampGrowth();
		}
		if (metadata == 10) return new TileThaumatorium();
		if (metadata == 11) return new TileThaumatoriumTop();
		if (metadata == 12) { return new TileBrainbox();
		}
		if (metadata == 13) { return new TileArcaneLampFertility();
		}
		if (metadata == 14) return new TileVisRelay();
		if (metadata == 2) return new TileMagicWorkbenchCharger();
		return super.createTileEntity(world, metadata);
	}
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		int metadata = world.getBlockMetadata(x, y, z);

		if ((metadata == 0) && (!world.isRemote)) {
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
				return heatSourceBlock.onBlockActivated(world, x, y-1, z, player, side, hitX, hitY, hitZ);
			}

		}
		return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
	}
}
