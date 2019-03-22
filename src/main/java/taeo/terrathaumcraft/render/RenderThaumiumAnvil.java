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

import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.terrathaumcraft.tile.TEAnvilTTC;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import com.bioxx.tfc.Blocks.Devices.BlockAnvil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderThaumiumAnvil implements ISimpleBlockRenderingHandler {

	public static boolean renderAnvil(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;

		int meta = blockAccess.getBlockMetadata(i, j, k);
		int direction = BlockAnvil.getDirectionFromMetadata(meta);
		boolean breaking = false;
		if(renderblocks.overrideBlockTexture != null)
			breaking = true;

		TEAnvilTTC te = (TEAnvilTTC)blockAccess.getTileEntity(i, j, k);

		if(direction == 0)//x
		{
			//top
			renderblocks.setRenderBounds(0.3F, 0.4F, 0.1F, 0.7F, 0.6F, 0.9F);
			renderblocks.renderStandardBlock(block, i, j, k);

			//core
			renderblocks.setRenderBounds(0.35F, 0.0F, 0.15F, 0.65F, 0.4F, 0.85F);
			renderblocks.renderStandardBlock(block, i, j, k);

			//feet
			renderblocks.setRenderBounds(0.25F, 0.0F, 0.1F, 0.75F, 0.2F, 0.90F);
			renderblocks.renderStandardBlock(block, i, j, k);
			renderblocks.setRenderBounds(0.20F, 0.0F, 0.0F, 0.80F, 0.1F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);

			//block.setBlockBounds(0.2F, 0.0F, 0.0F, 0.80F, 0.6F, 1.0F);
		}
		else if(direction == 1)//z
		{
			//top
			renderblocks.setRenderBounds(0.1F, 0.4F, 0.3F, 0.9F, 0.6F, 0.7F);
			renderblocks.renderStandardBlock(block, i, j, k);

			//core
			renderblocks.setRenderBounds(0.15F, 0.0F, 0.35F, 0.85F, 0.4F, 0.65F);
			renderblocks.renderStandardBlock(block, i, j, k);

			//feet
			renderblocks.setRenderBounds(0.1F, 0.0F, 0.25F, 0.90F, 0.2F, 0.75F);
			renderblocks.renderStandardBlock(block, i, j, k);
			renderblocks.setRenderBounds(0.0F, 0.0F, 0.20F, 1.00F, 0.1F, 0.80F);
			renderblocks.renderStandardBlock(block, i, j, k);

			//block.setBlockBounds(0.0F, 0.0F, 0.20F, 1.0F, 0.6F, 0.8F);
		}
		
		
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		if(modelId == TTCBlocks.anvilRenderID)
		{
			renderer.setRenderBounds(0.3F, 0.4F, 0.1F, 0.7F, 0.6F, 0.9F);
			renderInvBlock(block, metadata, renderer);

			//core
			renderer.setRenderBounds(0.35F, 0.0F, 0.15F, 0.65F, 0.4F, 0.85F);
			renderInvBlock(block, metadata, renderer);

			//feet
			renderer.setRenderBounds(0.25F, 0.0F, 0.1F, 0.75F, 0.2F, 0.90F);
			renderInvBlock(block, metadata, renderer);
			renderer.setRenderBounds(0.20F, 0.0F, 0.0F, 0.80F, 0.1F, 1.0F);
			renderInvBlock(block, metadata, renderer);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if(modelId == TTCBlocks.anvilRenderID)
			return renderAnvil(block,x,y,z,renderer);
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return 0;
	}

	public static void renderInvBlock(Block block, int m, RenderBlocks renderer)
	{
		Tessellator var14 = Tessellator.instance;
		int meta = m;
		if(meta >=8)
			meta-=8;

		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
		var14.draw();
	}
}
