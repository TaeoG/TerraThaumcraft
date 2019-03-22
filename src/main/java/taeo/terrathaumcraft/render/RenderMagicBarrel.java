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

import com.bioxx.tfc.Render.Blocks.RenderBarrel;
import com.bioxx.tfc.TileEntities.TEBarrel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import taeo.terrathaumcraft.init.TTCBlocks;
import thaumcraft.common.config.ConfigBlocks;

public class RenderMagicBarrel extends RenderBarrel {

    private static final float MIN = 0.1F;
    private static final float MAX = 0.9F;

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        /*TEBarrel te = (TEBarrel) world.getTileEntity(x, y, z);
        Block planksBlock;
        Block lidBlock;

        int meta = te.barrelType;

        planksBlock = ConfigBlocks.blockWoodenDevice;
        lidBlock = TTCBlocks.woodSupportH2;

        renderer.renderAllFaces = true;

        if((te.rotation & -128) == 0)
        {
            if(te.getSealed())
            {
                renderer.setRenderBounds(MIN+0.05F, MIN, MIN+0.05F, MAX-0.05F, 0.95F, MAX-0.05F);
                renderer.setOverrideBlockTexture(TTCBlocks.woodSupportH2.getIcon(0, meta + 1));
                renderer.renderStandardBlock(lidBlock, x, y, z);
                renderer.clearOverrideBlockTexture();
            }
            else
            {
                renderer.setRenderBounds(MIN+0.05F, MIN, MIN+0.05F, MAX-0.05F, MIN+0.05F, MAX-0.05F);
                renderer.setOverrideBlockTexture(TTCBlocks.woodSupportH2.getIcon(0, meta + 1));
                renderer.renderStandardBlock(lidBlock, x, y, z);
                renderer.clearOverrideBlockTexture();

                if(te.fluid != null && renderer.overrideBlockTexture == null)
                {
                    int color = te.fluid.getFluid().getColor(te.fluid);
                    float f = (color >> 16 & 255) / 255.0F;
                    float f1 = (color >> 8 & 255) / 255.0F;
                    float f2 = (color & 255) / 255.0F;
                    float h = 0.75f*(te.fluid.amount/10000f);
                    renderer.setRenderBounds(MIN+0.05F, MIN+0.05, MIN+0.05F, MAX-0.05F, MIN+0.05f+h, MAX-0.05F);
                    IIcon still = te.fluid.getFluid().getStillIcon();
                    renderer.setOverrideBlockTexture(still);
                    renderer.renderStandardBlockWithColorMultiplier(lidBlock, x, y, z, f, f1, f2);
                    renderer.clearOverrideBlockTexture();
                }
            }
            renderer.setOverrideBlockTexture(ConfigBlocks.blockWoodenDevice.getIcon(2, meta + 6));
            renderer.setRenderBounds(MIN, 0F, MIN + 0.05F, MIN + 0.05F, 1F, MAX - 0.05F);
            rotate(renderer, 3);
            renderer.renderStandardBlock(planksBlock, x, y, z);

            renderer.setRenderBounds(MAX - 0.05F, 0F, MIN + 0.05F, MAX, 1F, MAX - 0.05F);
            rotate(renderer, 3);
            renderer.renderStandardBlock(planksBlock, x, y, z);

            renderer.setRenderBounds(MIN, 0F, MIN, MAX, 1F, MIN + 0.05F);
            rotate(renderer, 3);
            renderer.renderStandardBlock(planksBlock, x, y, z);

            renderer.setRenderBounds(MIN, 0F, MAX - 0.05F, MAX, 1F, MAX);
            rotate(renderer, 3);
            renderer.renderStandardBlock(planksBlock, x, y, z);
            renderer.clearOverrideBlockTexture();

            //renderer.setOverrideBlockTexture(ConfigBlocks.blockWoodenDevice.getIcon(0, meta + 6));
            renderer.setRenderBounds(MIN - 0.001, 0F, MIN-0.001, MAX+0.001, 1F, MAX + 0.001);
            rotate(renderer, 0);
            renderer.renderStandardBlock(block, x, y, z);
            //renderer.clearOverrideBlockTexture();
        }
        else
        {
            if((te.rotation & 3) == 0)
            {
                renderer.setRenderBounds(MIN, MIN, MIN+0.05F, 0.95F, MIN+0.05F, MAX-0.05F);
                renderer.renderStandardBlock(lidBlock, x, y, z);
            }
            if((te.rotation & 3) == 1)
            {
                renderer.setRenderBounds(MIN+0.05F, MIN, MIN,MAX-0.05F, MIN+0.05F, 0.95F);
                renderer.renderStandardBlock(lidBlock, x, y, z);
            }
        }


        renderer.renderAllFaces = false;
*/
        return true;
    }
}
