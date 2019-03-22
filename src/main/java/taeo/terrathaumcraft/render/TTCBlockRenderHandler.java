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

import com.bioxx.tfc.Handlers.Client.BlockRenderHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import taeo.terrathaumcraft.init.TTCBlocks;

public class TTCBlockRenderHandler extends BlockRenderHandler {

    public static RenderBlocks wrappedRender;

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if(wrappedRender == null || wrappedRender != renderer)
        {
            wrappedRender = new RendererWrapper(renderer);
        }
        if(modelId == TTCBlocks.magicBarrelRenderId)
        {
            return false;
        }

        /*if (modelId == TFCBlocks.grassRenderId)
        {
            return RenderGrass.render(block, x, y, z, wrappedRender);
        }
        else if (modelId == TFCBlocks.clayGrassRenderId)
        {
            return RenderGrass.renderClay(block, x, y, z, wrappedRender);
        }
        else if (modelId == TFCBlocks.peatGrassRenderId)
        {
            return RenderGrass.renderPeat(block, x, y, z, wrappedRender);
        }
        /*else if (modelId == TFCBlocks.waterPlantRenderId)
        {
            return RenderFloraTTC.renderSeaPlant(block, x, y, z, renderer);
        }*/
        //else
            return super.renderWorldBlock(world, x, y, z, block, modelId, renderer);
    }

}
