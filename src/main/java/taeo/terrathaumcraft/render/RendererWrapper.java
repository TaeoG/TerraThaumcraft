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

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import taeo.ttfcapi.utility.BlockConverter;
import taeo.terrathaumcraft.utility.ColorTools;

public class RendererWrapper extends RenderBlocks {

    private final RenderBlocks _wrapee;

    public RendererWrapper(RenderBlocks wrapee)
    {
        super();
        _wrapee = wrapee;
        this.blockAccess = _wrapee.blockAccess;
    }
    @Override
    public boolean renderStandardBlock(Block inBlock, int x, int y, int z)
    {
        Block vanillaBlock = BlockConverter.getInstance().convertToVanilla(inBlock);
        //p_147751_1_.colorMultiplier(_wrapee.blockAccess,x,y,z);
        //int l = vanillaBlock.colorMultiplier(_wrapee.blockAccess, x, y, z);
        int l = ColorTools.grassColorMultiplier(_wrapee.blockAccess,x,y,z);
        float red = (float)(l >> 16 & 255) / 255.0F;
        float green = (float)(l >> 8 & 255) / 255.0F;
        float blue = (float)(l & 255) / 255.0F;

       // int l = p_147784_1_.colorMultiplier(this.blockAccess, p_147784_2_, p_147784_3_, p_147784_4_);
        //float f = (float)(l >> 16 & 255) / 255.0F;
       // float f1 = (float)(l >> 8 & 255) / 255.0F;
      //  float f2 = (float)(l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float f3 = (red * 30.0F + green * 59.0F + blue * 11.0F) / 100.0F;
            float f4 = (red * 30.0F + green * 70.0F) / 100.0F;
            float f5 = (red * 30.0F + blue * 70.0F) / 100.0F;
            red = f3;
            green = f4;
            blue = f5;
        }

        return Minecraft.isAmbientOcclusionEnabled() && inBlock.getLightValue() == 0 ? (_wrapee.partialRenderBounds ? _wrapee.renderStandardBlockWithAmbientOcclusionPartial(inBlock, x, y, z, red, green, blue) : _wrapee.renderStandardBlockWithAmbientOcclusion(inBlock, x, y, z, red, green, blue)) : _wrapee.renderStandardBlockWithColorMultiplier(inBlock, x, y, z, red, green, blue);

        //_wrapee.renderStandardBlockWithAmbientOcclusion(inBlock, x, y, z, red, green, blue);
    }

    @Override
    public boolean renderStandardBlockWithAmbientOcclusion(Block block , int x, int y, int z, float r, float g, float b)
    {
        return _wrapee.renderStandardBlockWithAmbientOcclusion(block, x, y, z, r, g, b);
    }
}
