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

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import taeo.terrathaumcraft.block.BlockSoulFarmland;
import taeo.terrathaumcraft.init.TTCBlocks;
import thaumcraft.client.renderers.block.BlockRenderer;

public class RenderSoulFarm extends BlockRenderer implements ISimpleBlockRenderingHandler {
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);
        drawFaces(renderer, block, (block).getIcon(1, metadata),false);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);

        renderer.setRenderBoundsFromBlock(block);
       Tessellator tess = Tessellator.instance;
        int bb = setBrightness(world, x, y, z, block);
        tess.setBrightness(Math.max(bb, 160));


        renderer.renderStandardBlock(block, x, y, z);
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9385F, 1.0F);

        renderer.setRenderBoundsFromBlock(block);
        renderer.renderFaceYPos(block, x, y, z, BlockSoulFarmland.tilloverlay);
        return true;
    }

    public void addVertexes(Tessellator tess)
    {
        for(int vx = 0; vx < 2; vx++)
        {
            for(int vy = 0; vy<2;vy++)
            {
                for(int vz = 0; vz <2; vz++)
                {
                    tess.addVertex(vx,vy,vz);
                }
            }
        }
    }


    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return false;
    }

    @Override
    public int getRenderId()
    {
        return TTCBlocks.tilledsoulsandRenderId;
    }

}
