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

package taeo.terrathaumcraft.block.Foliage;

import com.bioxx.tfc.Blocks.Flora.BlockLogVert;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCItems;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.terrathaumcraft.init.TTCItems;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.reference.ReferenceTAPI;
import thaumcraft.common.blocks.BlockMagicalLog;

public class BlockMagicLog extends BlockMagicalLog {
    private BlockLogVert vertLog;

    public BlockMagicLog()
    {
        super();
        setHardness(20);
        setResistance(15F);
        setStepSound(Block.soundTypeWood);
    }

    public boolean placeBlockAt(World world, int x, int y, int z, int side, int meta)
    {
        x = x - ReferenceTAPI.sideMap[side][0];
        y = y - ReferenceTAPI.sideMap[side][1];
        z = z - ReferenceTAPI.sideMap[side][2];




        if(canPlaceBlockAt(world, x, y, z))
        {
            world.setBlock(x,y,z, TTCBlocks.blockMagicLog, meta + getMetaOffset(side), 3);
            return true;
        }
        return false;
    }
    public int getMetaOffset(int sidePlaced)
    {
        switch(sidePlaced/2)
        {
            case 0:
                return 0;
            case 1:
                return 8;
            case 2:
                return 4;
            default:
                return 0;
        }
    }
    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
    {
        //we need to make sure the player has the correct tool out
        boolean isAxeorSaw = false;
        boolean isHammer = false;
        ItemStack equip = entityplayer.getCurrentEquippedItem();
        if (equip != null)
        {
            int[] equipIDs = OreDictionary.getOreIDs(equip);
            for (int id : equipIDs)
            {
                String name = OreDictionary.getOreName(id);
                if (name.startsWith("itemAxe") || name.startsWith("itemSaw"))
                {
                    isAxeorSaw = true;
                    break;
                }
                else if (name.startsWith("itemHammer"))
                {
                    isHammer = true;
                    break;
                }
            }

            if (isAxeorSaw)
            {
                world.spawnEntityInWorld(new EntityItem(world,x,y,z, new ItemStack(TTCItems.magiclogs,1,meta%4)));
				//world.spawnEntityInWorld(new EntityItem(world,x,y,z, new ItemStack(TFCItems.logs,1,meta%4 + ReferenceTTC.WOODTYPESTARTINDEX)));
                TFC_Core.addPlayerExhaustion(entityplayer, 0.001f);
            }
            else if (isHammer)
            {
                EntityItem item = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(TFCItems.stick, 1 + world.rand.nextInt(3)));
                world.spawnEntityInWorld(item);
            }
        }
        else
        {
            world.setBlock(x, y, z, this, meta, 0x2);
        }
    }
}
