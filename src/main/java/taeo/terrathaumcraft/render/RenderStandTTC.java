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

import com.bioxx.tfc.Render.Blocks.RenderStand;
import com.bioxx.tfc.api.TFCBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import taeo.terrathaumcraft.block.BlockStand2E;
import thaumcraft.common.config.ConfigBlocks;

public class RenderStandTTC extends RenderStand {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        Block blockToRender = block == TFCBlocks.armorStand ? TFCBlocks.planks : TFCBlocks.planks2;
        Block woodblock = block == TFCBlocks.armorStand ? TFCBlocks.woodSupportH : TFCBlocks.woodSupportH2;

        float yScale = 0.7f;
        float blockScale = 0.5f;

        //Arms of the Stand
        renderer.setRenderBounds(0.44F * blockScale, 1.45F * yScale * blockScale, 0.2F * blockScale, 0.56F * blockScale, 1.55F * yScale * blockScale, 0.8F * blockScale);
        renderInvBlock(woodblock, metadata, renderer);

        renderer.setRenderBounds(0.45F * blockScale, 0.201F * yScale * blockScale, 0.35F * blockScale, 0.55F * blockScale, 1.45F * yScale * blockScale, 0.45F * blockScale);
        renderInvBlock(woodblock, metadata, renderer);

        renderer.setRenderBounds(0.45F * blockScale, 0.201F * yScale * blockScale, 0.55F * blockScale, 0.55F * blockScale, 1.45F * yScale * blockScale, 0.65F * blockScale);
        renderInvBlock(woodblock, metadata, renderer);

        //Base of the stand
        if(block instanceof BlockStand2E && metadata > 0)
        renderer.setOverrideBlockTexture(ConfigBlocks.blockWoodenDevice.getIcon(0, metadata +6));
        renderer.setRenderBounds(0.2F, 0F, 0.2F, 0.8F, 0.2*yScale, 0.8F);
        renderInvBlock(blockToRender, metadata, renderer, false);
        renderer.clearOverrideBlockTexture();

        //Main post of the stand
        renderer.setRenderBounds(0.45F * blockScale, 1.45F * yScale * blockScale, 0.45F * blockScale, 0.55F * blockScale, 1.9F * yScale * blockScale, 0.55F * blockScale);
        renderInvBlock(woodblock, metadata, renderer);
    }
}
