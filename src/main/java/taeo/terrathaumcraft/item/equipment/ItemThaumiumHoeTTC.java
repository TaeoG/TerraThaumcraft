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

import com.bioxx.tfc.TileEntities.TEFarmland;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.terrathaumcraft.init.TTCItems;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import thaumcraft.api.IRepairable;

import com.bioxx.tfc.Items.Tools.ItemCustomHoe;

public class ItemThaumiumHoeTTC extends ItemCustomHoe implements IRepairable{

	public ItemThaumiumHoeTTC() {
		super(TTCItems.ThaumiumEnhancedToolMaterial);
		this.setUnlocalizedName("thaumiumhoe");
	}
	public int getItemEnchantability()
	{
		return 5;
	}

	public EnumRarity getRarity(ItemStack itemstack)
	{
		return EnumRarity.uncommon;
	}
	public void registerIcons(IIconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon("thaumcraft:thaumiumhoe");
	}
	public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", ReferenceTTC.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if(!world.isRemote)
        {
            Block block = world.getBlock(x, y, z);
            Block blockAbove = world.getBlock(x, y + 1, z);
            if (side == 1 && blockAbove.isAir(world, x, y + 1, z) && (block == TTCBlocks.soulsand || block == Blocks.soul_sand))
            {

                world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), block.stepSound.getStepResourcePath(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                if (world.isRemote)
                {
                    return true;
                }

                world.setBlock(x, y, z, TTCBlocks.tilledsoulsand, 0, 2);
                world.markBlockForUpdate(x, y, z);
                stack.damageItem(1, player);

                TEFarmland te = (TEFarmland) world.getTileEntity(x, y, z);
                te.nutrients[0] = 50;
                te.nutrients[1] = 50;
                te.nutrients[2] = 50;


                return true;

            }
        }

        /*if(!super.onItemUseFirst(stack, player, world, x,y,z,side,hitX,hitY,hitZ))
        {
            Block block = world.getBlock(x, y, z);
            Block blockAbove = world.getBlock(x, y + 1, z);
            if (side == 1 && blockAbove.isAir(world, x, y + 1, z) && (block == TTCBlocks.soulsand || block == Blocks.soul_sand))
            {

                world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), block.stepSound.getStepResourcePath(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                if (world.isRemote)
                {
                    return true;
                }

                world.setBlock(x, y, z, TTCBlocks.tilledsoulsand, 0, 2);
                world.markBlockForUpdate(x, y, z);
                stack.damageItem(1, player);

                //TEFarmland te = (TEFarmland) world.getTileEntity(x, y, z);
                //te.nutrients[0] = 50;
               // te.nutrients[1] = 50;
                //te.nutrients[2] = 50;


                return true;

            }
            return false;
        }*/
        return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }

}
