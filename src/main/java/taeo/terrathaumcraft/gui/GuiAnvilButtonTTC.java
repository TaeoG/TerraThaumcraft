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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Crafting.PlanRecipe;
import com.bioxx.tfc.api.Enums.RuleEnum;

public class GuiAnvilButtonTTC extends GuiButton{

	//private static ResourceLocation texture = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "anvilicons.png");
	public IIcon icon;
	public int bX = 0;
	public int bY = 0;
	public int bW = 0;
	public int bH = 0;
	public int ruleIndex = 0;
	GuiAnvilTTC screen;
	byte red = (byte)255;
	byte blue = (byte)255;
	byte green = (byte)255;

	public GuiAnvilButtonTTC(int index, int xPos, int yPos, int width, int height, IIcon ico, 
			int buttonX, int buttonY, int buttonW, int buttonH, GuiAnvilTTC gui, String s)
	{
		super(index, xPos, yPos, width, height, s);
		icon = ico;
		bX = buttonX;
		bY = buttonY;
		bW = buttonW;
		bH = buttonH;
		screen = gui;
	}

	public GuiAnvilButtonTTC(int index, int xPos, int yPos, int width, int height, 
			int buttonX, int buttonY, int buttonW, int buttonH, GuiAnvilTTC gui, int i, byte r, byte g, byte b)
	{
		super(index, xPos, yPos, width, height, "");
		bX = buttonX;
		bY = buttonY;
		bW = buttonW;
		bH = buttonH;
		screen = gui;
		ruleIndex = i;
		red = r;
		green = g;
		blue = b;
	}


	@Override
	public void drawButton(Minecraft mc, int x, int y)
	{
		if (this.visible)
		{
			int k = this.getHoverState(this.field_146123_n)-1;
			if(icon == null)
			{
				k = 0;
				if(screen.anvilTE != null && screen.anvilTE.workRecipe != null)
				{
					PlanRecipe p = AnvilManager.getInstance().getPlan(screen.anvilTE.craftingPlan);
					if(p == null) return;
					RuleEnum[] Rules = p.rules;
					//int[] ItemRules = screen.anvilTE.getItemRules();
					this.displayString = TFC_Core.translate(Rules[ruleIndex].Name);
				}
			}

			TFC_Core.bindTexture(GuiAnvilTTC.texture);
			GL11.glColor4ub(red, green, blue, (byte)255);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, this.bX + k * 16, this.bY + (ruleIndex * 22), this.bW, this.bH);

			this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			if(icon != null) 
			{
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				this.drawTexturedModelRectFromIcon(this.xPosition, this.yPosition, icon, this.width, this.height);
			}

			this.mouseDragged(mc, x, y);

			if(field_146123_n)
			{
				screen.drawTooltip(x, y, this.displayString);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
		}
	}

	/*private boolean isPointInRegion(int mouseX, int mouseY)
	{
		int k1 = 0;//screen.getGuiLeft();
		int l1 = 0;//screen.getGuiTop();
		mouseX -= k1;
		mouseY -= l1;
		return mouseX >= xPosition - 1 && mouseX < xPosition + width + 1 && mouseY >= yPosition - 1 && mouseY < yPosition + height + 1;
	}*/
}
