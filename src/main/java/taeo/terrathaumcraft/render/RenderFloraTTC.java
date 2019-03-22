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

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Render.Blocks.RenderFlora;
import com.bioxx.tfc.TileEntities.TEWaterPlant;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import taeo.terrathaumcraft.utility.ColorTools;

public class RenderFloraTTC extends RenderFlora {
    public static boolean renderSeaPlant(Block par1Block, int par2, int par3, int par4, RenderBlocks renderblocks) {
        boolean substrateRender = false;
        boolean plantRender = false;
        TileEntity te = renderblocks.blockAccess.getTileEntity(par2, par3, par4);
        int[] foliageColor = ColorTools.mixColors(renderblocks.blockAccess, par2, par3, par4, ColorTools.magic_Foliage, ColorTools.taint_Foliage, ColorTools.eerie_Foliage, 0x1c1c1c);
        if(te instanceof TEWaterPlant) {
            TEWaterPlant wp = (TEWaterPlant)te;
            if(wp.getBlockFromType() != null) {
                substrateRender = renderblocks.renderStandardBlockWithColorMultiplier(wp.getBlockFromType(), par2, par3, par4, foliageColor[0], foliageColor[1], foliageColor[2]);
                plantRender = render(par1Block, par2, par3, par4, renderblocks);
            }
        }

        return substrateRender && plantRender;
    }

        public static boolean render(Block block, int x, int y, int z, RenderBlocks renderer) {
            Block blockDirectlyAbove = renderer.blockAccess.getBlock(x, y + 1, z);
            boolean hasAirTwoAbove = renderer.blockAccess.getBlock(x, y + 2, z).isAir(renderer.blockAccess, x, y + 2, z);
            if(TFC_Core.isWater(blockDirectlyAbove)) {
                if(TFC_Core.isFreshWater(blockDirectlyAbove)) {
                    if(hasAirTwoAbove) {
                        renderCatTails(block, x, y + 1, z, renderer);
                    } else {
                        renderShortWaterPlant(block, x, y + 1, z, renderer, 1);
                    }
                } else if(TFC_Core.isSaltWater(blockDirectlyAbove)) {
                    renderShortWaterPlant(block, x, y + 1, z, renderer, 0);
                }
            }

            return true;
        }

        public static boolean renderShortWaterPlant(Block block, int x, int y, int z, RenderBlocks renderer, int plantType) {
            Tessellator tessellator = Tessellator.instance;
            tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
            //int l = block.colorMultiplier(renderer.blockAccess, x, y, z);
            int l = ColorTools.convertRGBtoInt(ColorTools.mixColors(renderer.blockAccess, x, y, z, ColorTools.magic_Foliage, ColorTools.taint_Foliage, ColorTools.eerie_Foliage, 0xffffff));
            float f = (float)(l >> 16 & 255) / 255.0F;
            float f1 = (float)(l >> 8 & 255) / 255.0F;
            float f2 = (float)(l & 255) / 255.0F;
            if(EntityRenderer.anaglyphEnable) {
                float d1 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
                float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
                float d2 = (f * 30.0F + f2 * 70.0F) / 100.0F;
                f = d1;
                f1 = f4;
                f2 = d2;
            }

            tessellator.setColorOpaque_F(f, f1, f2);
            double d11 = (double)x;
            double d21 = (double)y;
            double d0 = (double)z;
            long i1;
            if(block == Blocks.tallgrass) {
                i1 = (long)(x * 3129871) ^ (long)z * 116129781L ^ (long)y;
                i1 = i1 * i1 * 42317861L + i1 * 11L;
                d11 += ((double)((float)(i1 >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
                d21 += ((double)((float)(i1 >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
                d0 += ((double)((float)(i1 >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
            } else if(block == Blocks.red_flower || block == Blocks.yellow_flower) {
                i1 = (long)(x * 3129871) ^ (long)z * 116129781L ^ (long)y;
                i1 = i1 * i1 * 42317861L + i1 * 11L;
                d11 += ((double)((float)(i1 >> 16 & 15L) / 15.0F) - 0.5D) * 0.3D;
                d0 += ((double)((float)(i1 >> 24 & 15L) / 15.0F) - 0.5D) * 0.3D;
            }

            IIcon iicon = block.getIcon(0, plantType);
            renderer.drawCrossedSquares(iicon, d11, d21, d0, 1.0F);
            return true;
        }

        public static void renderCatTails(Block block, int x, int y, int z, RenderBlocks renderer) {
            Tessellator tessellator = Tessellator.instance;
            tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
            //int l = block.colorMultiplier(renderer.blockAccess, x, y, z);
            int l = ColorTools.convertRGBtoInt(ColorTools.mixColors(renderer.blockAccess, x, y, z, ColorTools.magic_Foliage, ColorTools.taint_Foliage, ColorTools.eerie_Foliage, 0x1c1c1c));
            float f = (float)(l >> 16 & 255) / 255.0F;
            float f1 = (float)(l >> 8 & 255) / 255.0F;
            float f2 = (float)(l & 255) / 255.0F;
            if(EntityRenderer.anaglyphEnable) {
                float d1 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
                float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
                float d2 = (f * 30.0F + f2 * 70.0F) / 100.0F;
                f = d1;
                f1 = f4;
                f2 = d2;
            }

            tessellator.setColorOpaque_F(f, f1, f2);

            IIcon icon = block.getIcon(0, 2);
            renderer.drawCrossedSquares(icon, (double)x, (double)y, (double)z, 2.0F);
        }


}
