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

import com.bioxx.tfc.Blocks.Liquids.BlockLiquidStatic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.Fluid;
import taeo.terrathaumcraft.utility.ColorTools;

public class BlockLiquidStaticTTC extends BlockLiquidStatic {
    public BlockLiquidStaticTTC(Fluid fluid, Material material, Block f)
    {
        super(fluid, material, f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        if(super.blockMaterial != Material.water)
        {
            return 16777215;
        }
        return ColorTools.convertRGBtoInt(ColorTools.mixWaterColors(par1IBlockAccess, par2, par3, par4, 0x354D35));

        //return this.blockMaterial != Material.water?16777215:3493173;
    }
}
