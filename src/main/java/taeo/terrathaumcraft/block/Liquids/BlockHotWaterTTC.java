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

package taeo.terrathaumcraft.block.Liquids;

import com.bioxx.tfc.Blocks.Liquids.BlockHotWater;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.Fluid;
import taeo.terrathaumcraft.utility.ColorTools;

public class BlockHotWaterTTC extends BlockHotWater{

    public BlockHotWaterTTC(Fluid fluid)
    {
        super(fluid);
    }

    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        return ColorTools.convertRGBtoInt(ColorTools.mixWaterColors(par1IBlockAccess, par2, par3, par4, this.fluidType.getColor()));
    }
}
