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

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.Items.ItemTFCArmor;
import com.bioxx.tfc.api.Armor;

import taeo.terrathaumcraft.reference.ReferenceTTC;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IRunicArmor;

public class ItemThaumiumArmorTTC extends ItemTFCArmor implements IRunicArmor, IRepairable{

	private IIcon iconHelm;
	private IIcon iconChest;
	private IIcon iconLegs;
	private IIcon iconBoots;
	
	
	public ItemThaumiumArmorTTC(Armor armor, int renderIndex, int armorSlot, int thermal, int type) {
		super(armor, renderIndex, armorSlot, thermal, type);
	}

	public ItemThaumiumArmorTTC(Armor armor, int renderIndex, int armorSlot, ArmorMaterial armorMat, int thermal, int type) {
		super(armor, renderIndex, armorSlot, armorMat, thermal, type);
	}
	
	public void registerIcons(IIconRegister iconRegister)
	{
		this.iconHelm = iconRegister.registerIcon("thaumcraft:thaumiumhelm");
		this.iconChest = iconRegister.registerIcon("thaumcraft:thaumiumchest");
		this.iconLegs = iconRegister.registerIcon("thaumcraft:thaumiumlegs");
		this.iconBoots = iconRegister.registerIcon("thaumcraft:thaumiumboots");
	}
	public IIcon getIconFromDamage(int par1)
	{
		return this.armorType == 2 ? this.iconLegs : this.armorType == 1 ? this.iconChest : this.armorType == 0 ? this.iconHelm : this.iconBoots;	
	}
	
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return this.armorType == 2 ? "thaumcraft:textures/models/thaumium_2.png" : "thaumcraft:textures/models/thaumium_1.png";

	}

	public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", ReferenceTTC.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

	@Override
	public int getRunicCharge(ItemStack paramItemStack) {
		// TODO Auto-generated method stub
		return 0;
	}
	public EnumRarity getRarity(ItemStack itemstack)
	{
		return EnumRarity.uncommon;
	}

}
