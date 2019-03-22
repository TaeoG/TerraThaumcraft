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

package taeo.terrathaumcraft.container;

import thaumcraft.api.ItemApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAnvilWand extends Slot{

	public SlotAnvilWand(IInventory inventory, int i, int j,
			int k) {
		super(inventory, i, j, k);
		
	}
	public SlotAnvilWand(EntityPlayer entityplayer, IInventory inventory, int i, int j, int k)
	   {
	     super(inventory, i, j, k);
	}
	public boolean isItemValid(ItemStack stack)
	{
		ItemStack wand = ItemApi.getItem("itemWandCasting", 0);
		if (stack.getItem() == wand.getItem()) {
			return true;
		}
		return false;
	}

}
