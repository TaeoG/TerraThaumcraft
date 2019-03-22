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

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Crafting.AnvilRecipe;
import com.bioxx.tfc.api.Crafting.AnvilReq;

import taeo.terrathaumcraft.container.ContainerPlanSelectionTTC;
import taeo.terrathaumcraft.crafting.MagicAnvilManager;
import taeo.terrathaumcraft.crafting.MagicAnvilRecipe;
import taeo.terrathaumcraft.item.equipment.ItemThaumiumHammerTTC;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.terrathaumcraft.tile.TEAnvilTTC;
import taeo.ttfcapi.utility.LogHelper;
import thaumcraft.api.ThaumcraftApiHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;



public class GuiPlanSelectionTTC extends GuiContainerTTC {

	public TEAnvilTTC anvilTE;
	public EntityPlayer player;
	public World world;
	public ArrayList<Object[]> plans;
	int x, y, z;
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_plan.png");

	
	
	
	public GuiPlanSelectionTTC(EntityPlayer p, TEAnvilTTC te, World w, int x, int y, int z) {

		super(new ContainerPlanSelectionTTC(p, te, w, x, y, z), 176, 130);
		//System.out.println("Accessing my Constructor");
		//GuiContainerTFC gc = new GuiContainerTFC(new ContainerPlanSelection(p, te, w, x, y, z), 176, 130);
		anvilTE = te;
		player = p;
		world = w;
		this.drawInventory = false;
		this.x = x;
		this.y = y;
		this.z = z;
		
	}
	
	public void initGui()
	{
		//System.out.println("Accessing my initGui");
		super.initGui();

		buttonList.clear();
		plans = getRecipes();
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		int xOffset = 5;
		int yOffset = 14;
		int index = plans.size() - 1;
		for (Object[] o : plans)
		{
			String p = (String) o[0];
			AnvilRecipe a = (AnvilRecipe) o[1];
			buttonList.add(0, new GuiPlanButtonTTC(plans.size() - 1 - index, guiLeft + xOffset, guiTop + yOffset, 16, 16, a.getCraftingResult(), this, TFC_Core.translate("gui.plans." + p)));
			index--;
			if (xOffset + 36 < xSize)
				xOffset += 18;
			else
			{
				xOffset = 5;
				yOffset += 18;
			}
		}
	}
	
	
	private ArrayList<Object[]> getRecipes()
	{
		AnvilManager manager = AnvilManager.getInstance();
		MagicAnvilManager mManager = MagicAnvilManager.getInstance();
		Object[] plans = manager.getPlans().keySet().toArray();
		ArrayList planList = new ArrayList();
		for (Object p : plans)
		{
			AnvilRecipe ar = manager.findMatchingRecipe(new AnvilRecipe(anvilTE.anvilItemStacks[TEAnvilTTC.INPUT1_SLOT], anvilTE.anvilItemStacks[TEAnvilTTC.INPUT2_SLOT], (String) p, AnvilReq.getReqFromInt(anvilTE.AnvilTier), null));

			ar = handleMatchingRecipe(ar);
			if (ar != null)
				planList.add(new Object[]
				{ (String) p, ar });
		}
		/*LogHelper.info("---------------------------------------");
		LogHelper.info("Searching for Magic Recipe matches");
		LogHelper.info("Current Magic Recipes in list:");
		for (AnvilRecipe av : AnvilManager.getInstance().getRecipeList())
		{
			if (av.getAnvilreq() == Reference.MAGIC_TIER_LEVEL)
			{
				LogHelper.info(av.toString());
			}
		}
		LogHelper.info("---------------------------------------");*/
		for (Object p : plans)
		{
			MagicAnvilRecipe mr= new MagicAnvilRecipe(anvilTE.anvilItemStacks[TEAnvilTTC.INPUT1_SLOT], anvilTE.anvilItemStacks[TEAnvilTTC.INPUT2_SLOT], (String) p, null);
			//LogHelper.info("Searching " + mr.toString().replace(" = null", ""));
			MagicAnvilRecipe ar = mManager.findMatchingRecipe(mr);
			ar = handleMatchingRecipe(ar);
			if (ar != null 
					&& ar.research != "" 
					&& ThaumcraftApiHelper.isResearchComplete(player.getDisplayName(), ar.research)){}
			else ar = null;
			if (ar != null && anvilTE.anvilItemStacks[TEAnvilTTC.HAMMER_SLOT] != null&& anvilTE.anvilItemStacks[TEAnvilTTC.HAMMER_SLOT].getItem() instanceof ItemThaumiumHammerTTC)
				planList.add(new Object[]
				{ (String) p, ar });
		}
		return planList;

	}
	public MagicAnvilRecipe handleMatchingRecipe(MagicAnvilRecipe ar)
	{
		if (ar != null)
			if (anvilTE.anvilItemStacks[TEAnvilTTC.INPUT1_SLOT] != null && anvilTE.anvilItemStacks[TEAnvilTTC.INPUT1_SLOT].getItem() == TFCItems.bloom && ar.getCraftingResult().getItem() == TFCItems.bloom)
			{
				if (anvilTE.anvilItemStacks[TEAnvilTTC.INPUT1_SLOT].getItemDamage() <= 100)
					return null;
			}
		return ar;
	}
	public AnvilRecipe handleMatchingRecipe(AnvilRecipe ar)
	{
		if (ar != null)
			if (anvilTE.anvilItemStacks[TEAnvilTTC.INPUT1_SLOT] != null && anvilTE.anvilItemStacks[TEAnvilTTC.INPUT1_SLOT].getItem() == TFCItems.bloom && ar.getCraftingResult().getItem() == TFCItems.bloom)
			{
				if (anvilTE.anvilItemStacks[TEAnvilTTC.INPUT1_SLOT].getItemDamage() <= 100)
					return null;
			}
		return ar;
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		Object[] p = (Object[]) plans.toArray()[guibutton.id];
		LogHelper.info(ReferenceTTC.MOD_NAME,"Setting plan to " + p[0]);
		anvilTE.setPlan((String) p[0]);
	}
	@Override
	public void drawTooltip(int mx, int my, String text)
	{
		List<String> list = new ArrayList<String>();
		list.add(text);
		this.drawHoveringTextZLevel(list, mx, my + 15, this.fontRendererObj, 400);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		drawGui(texture);
		if (anvilTE.getStackInSlot(TEAnvilTTC.INPUT1_SLOT) != null)
			drawCenteredString(this.fontRendererObj, "Plans: " + anvilTE.getStackInSlot(TEAnvilTTC.INPUT1_SLOT).getDisplayName(), guiLeft + xSize / 2, guiTop + 5, 0x000000);
	}
}
