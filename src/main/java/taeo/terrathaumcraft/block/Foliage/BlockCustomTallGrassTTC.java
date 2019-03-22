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

import com.bioxx.tfc.Blocks.Vanilla.BlockCustomTallGrass;
import net.minecraft.world.IBlockAccess;
import taeo.terrathaumcraft.utility.ColorTools;

public class BlockCustomTallGrassTTC extends BlockCustomTallGrass {

    public BlockCustomTallGrassTTC(){
        super();
    }
    @Override
    public int colorMultiplier(IBlockAccess bAccess, int x, int y, int z) {
        return ColorTools.grassColorMultiplier(bAccess,x,y,z);
    }

}
