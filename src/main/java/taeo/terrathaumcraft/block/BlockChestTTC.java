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

package taeo.terrathaumcraft.block;

import com.bioxx.tfc.Blocks.Devices.BlockChestTFC;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.apache.commons.lang3.ArrayUtils;
import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.terrathaumcraft.tile.TEChestTTC;

import java.lang.reflect.Field;

public class BlockChestTTC extends BlockChestTFC {
    public String[] woodNames;
    public BlockChestTTC()
    {
        super();

        try
        {
            Field woodField = BlockChestTFC.class.getDeclaredField("woodNames");
            woodField.setAccessible(true);
            String[] woodFieldContents = (String[])woodField.get(this);
            woodNames = woodFieldContents;
            String[] tempnames = ArrayUtils.addAll(woodNames, new String[]{"greatwood", "silverwood"});
            woodNames = tempnames;
            woodField.set(this, woodNames);

        } catch (NoSuchFieldException e)
        {
            //System.exit(0);
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int var2)
    {
        return new TEChestTTC();
    }
    @Override
    public int getRenderType()
    {
        return TTCBlocks.chestRenderId;
    }
}
