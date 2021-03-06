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

import com.bioxx.tfc.Items.ItemBlocks.ItemFenceGate2;
import net.minecraft.block.Block;

public class ItemFenceGate2E extends ItemFenceGate2{
    public ItemFenceGate2E(Block par1)
    {
        super(par1);
        String[] tempnames = new String[metaNames.length + 2];
        for(int i = 0; i<metaNames.length; i++)
        {
            tempnames[i] = metaNames[i];
        }
        tempnames[metaNames.length] = "greatwood";
        tempnames[metaNames.length+1] = "silverwood";
        metaNames = tempnames;
    }
}
