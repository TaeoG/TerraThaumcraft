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

import com.bioxx.tfc.Items.Tools.ItemProPick;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import taeo.terrathaumcraft.init.TTCItems;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import thaumcraft.api.IRepairable;

public class ItemThaumiumProPickTTC extends ItemProPick implements IRepairable{
	
	public ItemThaumiumProPickTTC()
	{
		this.setMaxDamage(TTCItems.ThaumiumEnhancedToolMaterial.getMaxUses());
	}
	public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", ReferenceTTC.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
	public EnumRarity getRarity(ItemStack itemstack)
	{
		return EnumRarity.uncommon;
	}
	public void registerIcons(IIconRegister registerer)
	{
			itemIcon = registerer.registerIcon(ReferenceTTC.MOD_ID + ":tools/" + this.getUnlocalizedName().replace("item.",""));
	}

}
