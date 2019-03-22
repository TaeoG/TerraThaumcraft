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

package taeo.terrathaumcraft.render;

import com.bioxx.tfc.TileEntities.TEChest;
import com.bioxx.tfc.api.Constant.Global;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class TESRChestHelperTTC {

    public static TESRChestHelperTTC instance = new TESRChestHelperTTC();
    private TEChest[] chestTypes;

    public TESRChestHelperTTC()
    {
        chestTypes = new TEChest[(Global.WOOD_ALL.length+2)*2];
        for(int i = 0; i < Global.WOOD_ALL.length+2; i++)
        {
            chestTypes[i] = new TEChest(i, false);
            chestTypes[Global.WOOD_ALL.length+2+i] = new TEChest(i, true);
        }
    }
    /**
     * Renders a chest at 0,0,0 - used for item rendering
     */
    public void renderChest(Block block, int meta, float modelID)
    {
        TileEntityRendererDispatcher.instance.renderTileEntityAt(chestTypes[meta], 0.0D, 0.0D, 0.0D, 0.0F);
    }
}
