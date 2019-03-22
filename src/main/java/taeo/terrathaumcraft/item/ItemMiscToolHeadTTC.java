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

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import taeo.terrathaumcraft.reference.ReferenceTTC;

import com.bioxx.tfc.Items.Tools.ItemMiscToolHead;
import taeo.ttfcapi.reference.ReferenceTAPI;

public class ItemMiscToolHeadTTC extends ItemMiscToolHead{

	public IIcon[] icons = new IIcon[13];
	
	public ItemMiscToolHeadTTC(ToolMaterial mat) {
		super(mat);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		
		
	}
	@Override
	public IIcon getIconFromDamage(int meta)
	{
		if(meta>12)
		{
			meta =0;
		}
		return this.icons[meta];
	}
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < 13; i++)
		{
			this.icons[i] = registerer.registerIcon(ReferenceTTC.MOD_ID + ":toolheads/" + this.getUnlocalizedName().replace("tool" ,  ReferenceTAPI.TOOL_HEADS[i]).replace("item.", ""));
		}

	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < 13; i ++)
		{
			list.add(new ItemStack(item,1,i));
		}
	}
	
	
	
	
	public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", ReferenceTTC.MOD_ID.toLowerCase() + ":", this.getUnlocalizedName().replace("item.", "").replace("tool" ,  ReferenceTAPI.TOOL_HEADS[itemStack.getItemDamage()]));
    }

	public EnumRarity getRarity(ItemStack itemstack)
	{
		return EnumRarity.uncommon;
	}
	
}
