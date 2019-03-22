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

package taeo.terrathaumcraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import taeo.terrathaumcraft.init.TTCBlocks;
import thaumcraft.api.wands.IWandTriggerManager;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.items.wands.ItemWandCasting;

public class WandManagerTTC implements IWandTriggerManager{

	@Override
	public boolean performTrigger(World world, ItemStack wand,
			EntityPlayer player, int x, int y, int z, int side, int event) {
		switch(event)
		{
		case 0:
			return createCrucible(wand, player, world, x, y, z);
		}
		
		return false;
	}

	public static boolean createCrucible(ItemStack is, EntityPlayer player, World world, int x, int y, int z)
	{
		ItemWandCasting wand = (ItemWandCasting)is.getItem();
		if (!world.isRemote)
		{
			world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
			world.setBlockToAir(x, y, z);
			world.setBlock(x, y, z, TTCBlocks.blockCrucible, 0, 3);
			world.notifyBlocksOfNeighborChange(x, y, z, world.getBlock(x, y, z));
			world.markBlockForUpdate(x, y, z);
			world.addBlockEvent(x, y, z, ConfigBlocks.blockMetalDevice, 1, 1);
			return true;
		}
		return false;
   }
}
