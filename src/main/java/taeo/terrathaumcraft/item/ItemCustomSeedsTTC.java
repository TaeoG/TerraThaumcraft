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

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.Items.ItemCustomSeeds;
import com.bioxx.tfc.TileEntities.TECrop;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.terrathaumcraft.reference.ReferenceTTC;

public class ItemCustomSeedsTTC extends ItemCustomSeeds {
    public int cropId;
    public ItemCustomSeedsTTC(int cropId)
    {
        super(cropId);
        this.cropId = cropId;
    }
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (side != 1 || world.isRemote)
            return false;
        else if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack))
        {
            Block var8 = world.getBlock(x, y, z);
            if ((var8 == TTCBlocks.tilledsoulsand) && world.isAirBlock(x, y + 1, z))
            {
                CropIndex crop = CropManager.getInstance().getCropFromId(cropId);
                if (crop.needsSunlight && !TECrop.hasSunlight(world, x, y + 1, z))
                {
                    TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("gui.seeds.failedSun"));
                    return false;
                }

                if (TFC_Climate.getHeightAdjustedTemp(world, x, y, z) <= crop.minAliveTemp && !crop.dormantInFrost)
                {
                    TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("gui.seeds.failedTemp"));
                    return false;
                }

                world.setBlock(x, y + 1, z, TTCBlocks.crops);

                TECrop te = (TECrop) world.getTileEntity(x, y + 1, z);
                te.cropId = cropId;
                world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
                world.markBlockForUpdate(x, y, z);
                --stack.stackSize;
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    @Override
    public IIcon getIconFromDamage(int i)
    {
        return super.getIconFromDamage(i);
    }
    @Override
    public void registerIcons(IIconRegister registerer)
    {
        itemIcon =registerer.registerIcon(ReferenceTTC.MOD_ID + ":" + "food/" + "seedsnetherwart");

    }
}
