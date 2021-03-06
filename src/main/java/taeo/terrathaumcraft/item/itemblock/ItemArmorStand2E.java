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

import com.bioxx.tfc.Items.ItemBlocks.ItemArmourStand2;
import com.bioxx.tfc.api.Constant.Global;
import net.minecraft.block.Block;

public class ItemArmorStand2E extends ItemArmourStand2 {
    public ItemArmorStand2E(Block par1)
    {
        super(par1);
        metaNames = new String[Global.WOOD_ALL.length - 14];
        System.arraycopy(Global.WOOD_ALL, 16, metaNames, 0, Global.WOOD_ALL.length - 16);
        metaNames[metaNames.length-2] = "greatwood";
        metaNames[metaNames.length-1] = "silverwood";
    }
}
