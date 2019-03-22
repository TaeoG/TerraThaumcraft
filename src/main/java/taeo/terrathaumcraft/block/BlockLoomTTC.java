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

import com.bioxx.tfc.Blocks.Devices.BlockLoom;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.terrathaumcraft.tile.TELoomTTC;
import taeo.ttfcapi.utility.LogHelper;

import java.lang.reflect.Field;

public class BlockLoomTTC extends BlockLoom {
    public String[] woodNames;
    public BlockLoomTTC()
    {
        super();
        try
        {
            //todo see if using reflection to add silverwood and greatwood to the reference for wood names would save having to insert them into every wood item
            Field woodField = BlockLoom.class.getDeclaredField("woodNames");
            woodField.setAccessible(true);
            String[] woodFieldContents = (String[]) woodField.get(this);
            String[] tempnames = new String[woodFieldContents.length + 2];
            for(int i = 0; i<woodFieldContents.length; i++)
            {
                tempnames[i] = woodFieldContents[i];
            }
            tempnames[woodFieldContents.length] = "greatwood";
            tempnames[woodFieldContents.length+1] = "silverwood";
            woodNames = tempnames;
            woodField.set(this, woodNames);


        } catch (Exception e)
        {
            LogHelper.fatal(ReferenceTTC.MOD_NAME,"Could not alter woodNames in Loom Class");
            e.printStackTrace();
        }
    }
    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TELoomTTC();
    }
}
