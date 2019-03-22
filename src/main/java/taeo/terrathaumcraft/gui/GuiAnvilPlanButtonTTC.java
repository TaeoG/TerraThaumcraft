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

package taeo.terrathaumcraft.gui;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

public class GuiAnvilPlanButtonTTC extends GuiButton{

	GuiAnvilTTC screen;
	protected static final RenderItem itemRenderer = new RenderItem();

	public GuiAnvilPlanButtonTTC(int index, int xPos, int yPos, int width, int height, GuiAnvilTTC gui, String s)
	{
		super(index, xPos, yPos, width, height, s);
		screen = gui;
	}

	@Override
	public void drawButton(Minecraft mc, int x, int y)
	{
		if (this.visible)
		{
			int k = this.getHoverState(this.field_146123_n)-1;

			TFC_Core.bindTexture(GuiAnvilTTC.texture);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.zLevel = 300f;
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 205 + k*18, 18, 18);
			this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			//TFC_Core.bindTexture(TextureMap.locationItemsTexture);

			if (screen.anvilTE != null && !screen.anvilTE.craftingPlan.equals("") && screen.anvilTE.workRecipe != null)
				renderInventorySlot(screen.anvilTE.workRecipe.getCraftingResult(),this.xPosition+1, this.yPosition+1);
			else
				renderInventorySlot(new ItemStack(TFCItems.blueprint),this.xPosition+1, this.yPosition+1);

			this.zLevel = 0;
			this.mouseDragged(mc, x, y);

			if(field_146123_n)
			{
				screen.drawTooltip(x, y, this.displayString);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
		}
	}

	protected void renderInventorySlot(ItemStack is, int x, int y)
	{
		if (is != null)
		{
			itemRenderer.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), is, x, y);
		}
	}
}
