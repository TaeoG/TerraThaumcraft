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

package taeo.terrathaumcraft.item.equipment;

import taeo.terrathaumcraft.reference.ReferenceTTC;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Items.ItemUnfinishedArmor;

public class ItemUnfinishedArmorTTC extends ItemUnfinishedArmor{


	public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", ReferenceTTC.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
	public void registerIcons(IIconRegister registerer)
	{
			itemIcon = registerer.registerIcon(ReferenceTTC.MOD_ID + ":armor/" + this.getUnlocalizedName().replace("item.","").replace("Stage2 ", ""));
	}
}
