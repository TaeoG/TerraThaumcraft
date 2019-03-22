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

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import taeo.terrathaumcraft.tile.TEAnvilTTC;

import com.bioxx.tfc.Blocks.Devices.BlockAnvil;
import com.bioxx.tfc.Render.TESR.TESRBase;

public class TESRThaumiumAnvil extends TESRBase{

	public TESRThaumiumAnvil()
	{
		super();
	}

	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderAt(TEAnvilTTC te, double x, double y, double z, float f)
	{
		if (te.getWorldObj() != null)
		{
			int dir = BlockAnvil.getDirectionFromMetadata(te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord, te.zCoord));

			EntityItem customitem = new EntityItem(field_147501_a.field_147550_f); //tileEntityRenderer.worldObj
			customitem.hoverStart = 0f;
			float blockScale = 1.0F;

			drawItem(te, x, y, z, dir, customitem, blockScale, TEAnvilTTC.HAMMER_SLOT);
			drawItem(te, x, y, z, dir, customitem, blockScale, TEAnvilTTC.INPUT1_SLOT);
			drawItem(te, x, y, z, dir, customitem, blockScale, TEAnvilTTC.WAND_SLOT);
		}
	}

	private void drawItem(TEAnvilTTC te, double x, double y, double z, int dir, EntityItem customitem, float blockScale, int i)
	{
		if (te.getStackInSlot(i) != null)
		{
			float[] pos = getLocation(dir, i, false);
			//if(Block.getBlockById(te.stonePair[0]) != Blocks.air)
			//	pos[1] += 0.3f;
			GL11.glPushMatrix(); //start
			{
				GL11.glTranslatef((float)x + pos[0], (float)y + pos[1], (float)z + pos[2]);
				if (RenderManager.instance.options.fancyGraphics)
					GL11.glRotatef(pos[3], pos[4], pos[5], pos[6]);
				GL11.glScalef(pos[7], pos[8], pos[9]);
				customitem.setEntityItemStack(te.getStackInSlot(i));
				itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
			}
			GL11.glPopMatrix(); //end
		}
	}

	public float[] getLocation(int dir, int slot, boolean isStone)
	{
		float[] out = new float[10];
		out[7] = 1f;
		out[8] = 1f;
		out[9] = 1f;

		if(dir == 0)
		{
			out[3] = 90f;
			out[4] = 1f;
			out[5] = 0f;
			out[6] = 0f;
			if(slot == TEAnvilTTC.HAMMER_SLOT)
			{
				out[0] = 0.55f;
				out[1] = 0.61f;
				out[2] = 0.45f;
			}
			else if(slot == TEAnvilTTC.INPUT1_SLOT)
			{
				out[0] = 0.55f;
				out[1] = 0.61f;
				out[2] = 0.05f;

				out[7] = 0.8f;
				out[8] = 0.8f;
				out[9] = 0.8f;
			}
			else if(slot == TEAnvilTTC.WAND_SLOT)
			{
				out[0] = 0.45F;
				out[1] = 0.15F;
				out[2] = 0.0F;
				out[3] = 10F;
				out[4] = 1F;
				out[5] = 0.0F;
				out[6] = 0F;

				out[7] = 0.8F;
				out[8] = 0.8F;
				out[9] = 0.8F;

			}
		}
		else if(dir == 1)
		{
			out[3] = 90f;
			out[4] = 1f;
			out[5] = 0f;
			out[6] = 0f;
			if(slot == TEAnvilTTC.HAMMER_SLOT)
			{
				out[0] = 0.25f;
				out[1] = 0.61f;
				out[2] = 0.25f;
			}
			else if(slot == TEAnvilTTC.INPUT1_SLOT)
			{
				out[0] = 0.75f;
				out[1] = 0.61f;
				out[2] = 0.25f;

				out[7] = 0.8f;
				out[8] = 0.8f;
				out[9] = 0.8f;
			}
			else if(slot == TEAnvilTTC.WAND_SLOT)
			{

				out[0] = 1.F;
				out[1] = 0.15F;
				out[2] = 0.65F;
				out[3] = 10F;
				out[4] = 0F;
				out[5] = 0.0F;
				out[6] = 1F;

				out[7] = 0.8F;
				out[8] = 0.8F;
				out[9] = 0.8F;

				
			}
		}
		return out;
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double xDis, double yDis, double zDis, float f)
	{
		this.renderAt((TEAnvilTTC)te, xDis, yDis, zDis, f);
	}
}
