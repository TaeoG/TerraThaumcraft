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

import com.bioxx.tfc.TileEntities.TEOre;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import taeo.ttfcapi.reference.ReferenceTAPI;

/**
 * Created by Taeo on 15/08/2015.
 */
public class ItemMagicOre extends ItemBlock{
    public ItemMagicOre(Block p_i45328_1_)
    {
        super(p_i45328_1_);
        setHasSubtypes(true);
    }

    public int getMetadata(int par1) {
        return par1;
    }

    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + stack.getItemDamage();
    }
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
        if(super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata))
        {
            Block block = player.worldObj.getBlock(x + ReferenceTAPI.sideMap[side][0],y + ReferenceTAPI.sideMap[side][1], z + ReferenceTAPI.sideMap[side][2]);
            int meta = player.worldObj.getBlockMetadata(x + ReferenceTAPI.sideMap[side][0], y + ReferenceTAPI.sideMap[side][1], z + ReferenceTAPI.sideMap[side][2]);
            TEOre teo = (TEOre)player.worldObj.getTileEntity(x,y,z);
            TEOre teoTarget = (TEOre)player.worldObj.getTileEntity(x + ReferenceTAPI.sideMap[side][0],y + ReferenceTAPI.sideMap[side][1], z + ReferenceTAPI.sideMap[side][2]);
            if(teo != null)
            {
                if(teoTarget != null)
                {
                    teo.baseBlockID = teoTarget.baseBlockID;
                    teo.baseBlockMeta = teoTarget.baseBlockMeta;
                }
                else
                {
                    teo.baseBlockID = Block.getIdFromBlock(block);
                    teo.baseBlockMeta = meta;
                }
                teo.setVisible();
            }
            return true;
        }
        return false;
    }
}
