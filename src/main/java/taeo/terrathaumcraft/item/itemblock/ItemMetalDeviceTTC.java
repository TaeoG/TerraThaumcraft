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

package taeo.terrathaumcraft.item.itemblock;

import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISize;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import thaumcraft.common.blocks.BlockMetalDeviceItem;

import java.util.List;

public class ItemMetalDeviceTTC extends BlockMetalDeviceItem implements ISize{
	public ItemMetalDeviceTTC(Block par1) {
		super(par1);
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		int meta = is.getItemDamage();
		switch (meta)
		{
			case 0: 	return EnumSize.HUGE;
			case 1: 	return EnumSize.VERYLARGE;
			case 2: 	return EnumSize.LARGE;
			case 3: 	return EnumSize.HUGE;
			case 4: 	return EnumSize.HUGE;
			case 5: 	return EnumSize.VERYLARGE;
			case 6: 	return EnumSize.HUGE;
			case 7: 	return EnumSize.VERYLARGE;
			case 8: 	return EnumSize.VERYLARGE;
			case 9: 	return EnumSize.HUGE;
			case 10: 	return EnumSize.HUGE;
			case 11: 	return EnumSize.HUGE;
			case 12: 	return EnumSize.MEDIUM;
			case 13: 	return EnumSize.VERYLARGE;
			case 14: 	return EnumSize.MEDIUM;
			case 15: 	return EnumSize.HUGE;
			default: 	return EnumSize.HUGE;
		}
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		int meta = is.getItemDamage();
		switch (meta)
		{
			case 0:		 return EnumWeight.HEAVY;
			case 1:		 return EnumWeight.HEAVY;
			case 2:		 return EnumWeight.MEDIUM;
			case 3:		 return EnumWeight.HEAVY;
			case 4:		 return EnumWeight.HEAVY;
			case 5:		 return EnumWeight.HEAVY;
			case 6:		 return EnumWeight.HEAVY;
			case 7:		 return EnumWeight.HEAVY;
			case 8:		 return EnumWeight.HEAVY;
			case 9:		 return EnumWeight.HEAVY;
			case 10:	 return EnumWeight.HEAVY;
			case 11:	 return EnumWeight.HEAVY;
			case 12:	 return EnumWeight.MEDIUM;
			case 13:	 return EnumWeight.HEAVY;
			case 14:	 return EnumWeight.LIGHT;
			case 15:	 return EnumWeight.HEAVY;
			default: return EnumWeight.HEAVY;
		}
	}

	@Override
	public EnumItemReach getReach(ItemStack is) {
		return EnumItemReach.SHORT;
	}

	@Override
	public boolean canStack() {
		return false;
	}
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);
	}
}
