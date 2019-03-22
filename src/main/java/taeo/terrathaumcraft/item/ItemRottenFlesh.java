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

import taeo.terrathaumcraft.reference.ReferenceTTC;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;

import com.bioxx.tfc.Food.ItemFoodMeat;
import com.bioxx.tfc.Render.Item.FoodItemRenderer;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

public class ItemRottenFlesh extends ItemFoodMeat{

	public ItemRottenFlesh(EnumFoodGroup fg, int sw, int so, int sa, int bi,
			int um, boolean edible, boolean usable) {
		super(fg, sw, so, sa, bi, um, edible, usable);
	//	itemIcon = null;
	}

	public void registerIcons(IIconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon("rotten_flesh");
		MinecraftForgeClient.registerItemRenderer(this, new FoodItemRenderer());
	}
	public boolean isEdible(ItemStack stack)
	{
		return Food.isSalted(stack) && Food.isDried(stack);
	}

	
	public String getUnlocalizedName(ItemStack itemStack)
    {
		String name = String.format("item.%s%s", ReferenceTTC.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
		if(Food.isSalted(itemStack) && Food.isDried(itemStack))
		{
			name += "edible";
		}
		return name;
    }
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
	

}
