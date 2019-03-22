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

import com.bioxx.tfc.Render.Blocks.RenderLoom;
import com.bioxx.tfc.TileEntities.TELoom;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import taeo.terrathaumcraft.block.BlockLoomTTC;
import thaumcraft.common.config.ConfigBlocks;

public class RenderLoomTTC extends RenderLoom {

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        TELoom te = (TELoom) world.getTileEntity(x,y,z);
        int loomType = 0;
        if(te != null)
        {
            loomType = te.loomType;
        }
        if(te != null && block instanceof BlockLoomTTC && loomType > 16)
        {
            renderer.setOverrideBlockTexture(ConfigBlocks.blockWoodenDevice.getIcon(0,loomType - 11));
        }
        boolean result = super.renderWorldBlock(world, x, y ,z, block, modelId, renderer);
        renderer.clearOverrideBlockTexture();
        return result;
    }

}
