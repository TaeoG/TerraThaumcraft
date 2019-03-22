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

package taeo.terrathaumcraft.tile;

import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.TileEntities.TEWorldItem;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.TFCOptions;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.utility.LogHelper;

public class TECropTTC extends TECrop {




    public void killCrop(CropIndex crop)
    {
        ItemStack is = crop.getSeed();
        is.stackSize = 1;
        if (worldObj.getBlock(xCoord, yCoord - 1, zCoord) == TTCBlocks.tilledsoulsand && TFCOptions.enableSeedDrops)
        {
            if(worldObj.setBlock(xCoord, yCoord, zCoord, TTCBlocks.worldItem))
            {
                TEWorldItem te = (TEWorldItem) worldObj.getTileEntity(xCoord, yCoord, zCoord);
                try
                {
                    te.storage[0] = is;
                }
                catch(NullPointerException e)
                {
                    if(is == null)
                    LogHelper.error(ReferenceTTC.MOD_NAME,"the item stack appears to be null");
                    else
                        LogHelper.error(ReferenceTTC.MOD_NAME,"the tile entity appears to be null");
                }
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
        else
        {
            worldObj.setBlockToAir(xCoord, yCoord, zCoord);
        }
    }
    public void onHarvest(World world, EntityPlayer player, boolean isBreaking)
    {
        if(!world.isRemote)
        {
            CropIndex crop = CropManager.getInstance().getCropFromId(cropId);

            if (crop != null && growth >= crop.numGrowthStages - 1)
            {
                ItemStack is1 = crop.getOutput1(this);
                ItemStack is2 = crop.getOutput2(this);
                ItemStack seedStack = crop.getSeed();

                is1.setTagCompound(null);
                is1.stackSize = world.rand.nextInt(3)+1;
                if(is1 != null)
                    world.spawnEntityInWorld(new EntityItem(world, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, is1));

                if(is2 != null)
                    world.spawnEntityInWorld(new EntityItem(world, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, is2));

                int skill = 20 - (int) (20 * TFC_Core.getSkillStats(player).getSkillMultiplier(Global.SKILL_AGRICULTURE));

                seedStack.stackSize = 1 + (world.rand.nextInt(1 + skill) == 0 ? 1 : 0);
                if (isBreaking)
                    world.spawnEntityInWorld(new EntityItem(world, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, seedStack));

                TFC_Core.getSkillStats(player).increaseSkill(Global.SKILL_AGRICULTURE, 1);

                if (TFC_Core.isSoil(world.getBlock(xCoord, yCoord - 1, zCoord)))
                    player.addStat(TFC_Achievements.achWildVegetable, 1);
            }
            else if (crop != null)
            {
                ItemStack is = crop.getSeed();
                is.stackSize = 1;
                world.spawnEntityInWorld(new EntityItem(world, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, is));
            }
        }
    }


}
