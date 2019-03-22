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

import com.bioxx.tfc.Items.ItemOre;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Interfaces.ISmeltable;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.reference.ReferenceTAPI;

public class ItemTransmutedOre extends ItemOre implements ISmeltable {


    /*public ItemTransmutedOre()
    {
        super();
        metaNames = new String[]{
                "Native Copper", "Native Gold", "Native Platinum", "Hematite",
                "Native Silver", "Cassiterite", "Galena", "Bismuthinite", "Garnierite",
                "Malachite", "Magnetite", "Limonite", "Sphalerite", "Tetrahedrite"};
    }
    public String getUnlocalizedName(ItemStack itemStack)
    {
        //return "item."+ ReferenceTTC.MOD_ID.toLowerCase() + ":"
        return String.format("item.%s%s%s", ReferenceTTC.MOD_ID.toLowerCase() + ":", metaNames[itemStack.getItemDamage()%metaNames.length], getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }
    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }*/

   /*@Override
    public Metal getMetalType(ItemStack is)
    {
        int meta = is.getItemDamage();
        switch(meta)
        {
            case 0: return Global.COPPER;
            case 1: return Global.GOLD;
            case 2: return Global.PLATINUM;
            case 3: return Global.PIGIRON;
            case 4: return Global.SILVER;
            case 5: return Global.TIN;
            case 6: return Global.LEAD;
            case 7: return Global.BISMUTH;
            case 8: return Global.NICKEL;
            case 9: return Global.COPPER;
            case 10: return Global.PIGIRON;
            case 11: return Global.PIGIRON;
            case 12: return Global.ZINC;
            case 13: return Global.COPPER;
                default: return Global.UNKNOWN;

        }
    }*/

    @Override
    public short getMetalReturnAmount(ItemStack is)
    {
        return (short) (((ItemOre)TFCItems.oreChunk).getMetalReturnAmount(is)*2);
    }

    @Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int renderPass)
	{
		Metal metalType = getMetalType(stack);
		if(metalType == null)
		{
			return 0x000000;
		}
		switch(getMetalType(stack).name)
		{
			case "Bismuth":			return ReferenceTAPI.MetalColors.BISMUTH;
			case "Bismuth Bronze":	return ReferenceTAPI.MetalColors.BISMUTHBRONZE;
			case "Black Bronze":	return ReferenceTAPI.MetalColors.BLACKBRONZE;
			case "Black Steel":		return ReferenceTAPI.MetalColors.BLACKSTEEL;
			case "Blue Steel":		return ReferenceTAPI.MetalColors.BLUESTEEL;
			case "Brass":			return ReferenceTAPI.MetalColors.BRASS;
			case "Bronze":			return ReferenceTAPI.MetalColors.BRONZE;
			case "Copper":			return ReferenceTAPI.MetalColors.COPPER;
			case "Gold":			return ReferenceTAPI.MetalColors.GOLD;
			case "Wrought Iron":	return ReferenceTAPI.MetalColors.WROUGHTIRON;
			case "Lead":			return ReferenceTAPI.MetalColors.LEAD;
			case "Nickel":			return ReferenceTAPI.MetalColors.NICKEL;
			case "Pig Iron":		return ReferenceTAPI.MetalColors.PIGIRON;
			case "Platinum":		return ReferenceTAPI.MetalColors.PLATINUM;
			case "Red Steel":		return ReferenceTAPI.MetalColors.REDSTEEL;
			case "Rose Gold":		return ReferenceTAPI.MetalColors.ROSEGOLD;
			case "Silver":			return ReferenceTAPI.MetalColors.SILVER;
			case "Steel":			return ReferenceTAPI.MetalColors.STEEL;
			case "Sterling Silver":	return ReferenceTAPI.MetalColors.STERLINGSILVER;
			case "Tin":				return ReferenceTAPI.MetalColors.TIN;
			case "Zinc":			return ReferenceTAPI.MetalColors.ZINC;
			case "Weak Steel":		return ReferenceTAPI.MetalColors.WEAKSTEEL;
			case "HC Black Steel":	return ReferenceTAPI.MetalColors.HIGHCARBONBLACKSTEEL;
			case "Weak Red Steel":	return ReferenceTAPI.MetalColors.WEAKREDSTEEL;
			case "HC Red Steel":	return ReferenceTAPI.MetalColors.HIGHCARBONREDSTEEL;
			case "Weak Blue Steel":	return ReferenceTAPI.MetalColors.WEAKBLUESTEEL;
			case "HC Blue Steel":	return ReferenceTAPI.MetalColors.HIGHCARBONBLUESTEEL;
			case "Unknown":			return ReferenceTAPI.MetalColors.UNKNOWN;
			default:				return 0xFF0000;
		}
	}
    /*@Override
    public boolean isSmeltable(ItemStack is)
    {
        int meta = is.getItemDamage();
        return meta <14? true: false;
    }*/

    /*@Override
    public EnumTier getSmeltTier(ItemStack is)
    {
        int meta = is.getItemDamage();
        switch(meta)
        {
            case 0:
                return EnumTier.TierI;
            case 1:
                return EnumTier.TierI;
            case 2:
                return EnumTier.TierIV;
            case 3:
                return EnumTier.TierIII;
            case 4:
                return EnumTier.TierI;
            case 5:
                return EnumTier.TierI;
            case 6:
                return EnumTier.TierI;
            case 7:
                return EnumTier.TierI;
            case 8:
                return EnumTier.TierIII;
            case 9:
                return EnumTier.TierI;
            case 10:
                return EnumTier.TierIII;
            case 11:
                return EnumTier.TierIII;
            case 12:
                return EnumTier.TierI;
            case 13:
                return EnumTier.TierI;
            default:
                return null;
        }
    }*/
}
