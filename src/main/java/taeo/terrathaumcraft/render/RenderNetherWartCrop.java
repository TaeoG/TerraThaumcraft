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

import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.TileEntities.TECrop;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.terrathaumcraft.init.TTCItems;
import thaumcraft.client.renderers.block.BlockRenderer;

public class RenderNetherWartCrop extends BlockRenderer implements ISimpleBlockRenderingHandler {
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        IBlockAccess blockaccess = renderer.blockAccess;
        TECrop cropTE = (TECrop)blockaccess.getTileEntity(x, y, z);

        if(cropTE != null)
            CropManager.getInstance().getCropFromId(cropTE.cropId);
        else
            return false;

        Tessellator var9 = Tessellator.instance;
        var9.setBrightness(block.getMixedBrightnessForBlock(blockaccess, x, y, z));
        if (cropTE.cropId == TTCItems.netherwartCropID)
        {
            renderBlockCropsImpl(block, x, y, z, renderer, 0.45, 1.0);
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return false;
    }

    @Override
    public int getRenderId()
    {
        return TTCBlocks.cropRenderId;
    }
    private static void renderBlockCropsImpl(Block block, double i, double j, double k, RenderBlocks renderblocks, double width, double height)
    {
        Tessellator tess = Tessellator.instance;
        GL11.glColor3f(1, 1, 1);
        int brightness = block.getMixedBrightnessForBlock(renderblocks.blockAccess, (int)i, (int)j, (int)k);
        tess.setBrightness(brightness);
        tess.setColorOpaque_F(1, 1, 1);

        IIcon icon = block.getIcon(renderblocks.blockAccess, (int)i, (int)j, (int)k, renderblocks.blockAccess.getBlockMetadata((int)i, (int)j, (int)k));
        if (renderblocks.hasOverrideBlockTexture())
            icon = renderblocks.overrideBlockTexture;

        if(icon != null)
        {
            if(((int)i & 1) > 0)
            {
                k+=0.0001;
            }
            if(((int)k & 1) > 0)
            {
                i+=0.0001;
            }

            double minU = icon.getMinU();
            double maxU = icon.getMaxU();
            double minV = icon.getMinV();
            double maxV = icon.getMaxV();
            double minX = i + 0.25D;
            double maxX = i + 0.75D;
            double minZ = k + 0.5D - width;
            double maxZ = k + 0.5D + width;
            double y = j;

            tess.addVertexWithUV(minX, y+height, minZ, minU, minV);
            tess.addVertexWithUV(minX, y, minZ, minU, maxV);
            tess.addVertexWithUV(minX, y, maxZ, maxU, maxV);
            tess.addVertexWithUV(minX, y+height, maxZ, maxU, minV);
            tess.addVertexWithUV(minX, y+height, maxZ, minU, minV);
            tess.addVertexWithUV(minX, y, maxZ, minU, maxV);
            tess.addVertexWithUV(minX, y, minZ, maxU, maxV);
            tess.addVertexWithUV(minX, y+height, minZ, maxU, minV);
            tess.addVertexWithUV(maxX, y+height, maxZ, minU, minV);
            tess.addVertexWithUV(maxX, y, maxZ, minU, maxV);
            tess.addVertexWithUV(maxX, y, minZ, maxU, maxV);
            tess.addVertexWithUV(maxX, y+height, minZ, maxU, minV);
            tess.addVertexWithUV(maxX, y+height, minZ, minU, minV);
            tess.addVertexWithUV(maxX, y, minZ, minU, maxV);
            tess.addVertexWithUV(maxX, y, maxZ, maxU, maxV);
            tess.addVertexWithUV(maxX, y+height, maxZ, maxU, minV);
            minX = i + 0.5D - width;
            maxX = i + 0.5D + width;
            minZ = k + 0.5D - 0.25D;
            maxZ = k + 0.5D + 0.25D;
            tess.addVertexWithUV(minX, y+height, minZ, minU, minV);
            tess.addVertexWithUV(minX, y, minZ, minU, maxV);
            tess.addVertexWithUV(maxX, y, minZ, maxU, maxV);
            tess.addVertexWithUV(maxX, y+height, minZ, maxU, minV);
            tess.addVertexWithUV(maxX, y+height, minZ, minU, minV);
            tess.addVertexWithUV(maxX, y, minZ, minU, maxV);
            tess.addVertexWithUV(minX, y, minZ, maxU, maxV);
            tess.addVertexWithUV(minX, y+height, minZ, maxU, minV);
            tess.addVertexWithUV(maxX, y+height, maxZ, minU, minV);
            tess.addVertexWithUV(maxX, y, maxZ, minU, maxV);
            tess.addVertexWithUV(minX, y, maxZ, maxU, maxV);
            tess.addVertexWithUV(minX, y+height, maxZ, maxU, minV);
            tess.addVertexWithUV(minX, y+height, maxZ, minU, minV);
            tess.addVertexWithUV(minX, y, maxZ, minU, maxV);
            tess.addVertexWithUV(maxX, y, maxZ, maxU, maxV);
            tess.addVertexWithUV(maxX, y+height, maxZ, maxU, minV);
        }
    }
}
