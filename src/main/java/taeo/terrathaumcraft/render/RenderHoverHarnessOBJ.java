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

import api.player.model.ModelPlayer;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;
import org.lwjgl.opengl.GL11;

public class RenderHoverHarnessOBJ extends ModelRenderer {

	private static final ResourceLocation HARNESS = new ResourceLocation("thaumcraft", "textures/models/hoverharness.obj");
	private WavefrontObject modelBack;

	public RenderHoverHarnessOBJ() {
		super(new ModelPlayer());
		modelBack = (WavefrontObject) AdvancedModelLoader.loadModel(HARNESS);
	}

	@Override
	public void render(float p_78785_1_) {
		super.render(p_78785_1_);
		GL11.glPushMatrix();
		GL11.glDisable(2896);
		GL11.glScalef(0.1F, 0.1F, 0.1F);
		GL11.glRotatef(90.0F, -1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.0F, 0.33F, -3.7F);

		FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation("thaumcraft", "textures/models/hoverharness2.png"));

		this.modelBack.renderAll();
		GL11.glEnable(2896);
		GL11.glPopMatrix();
	}
}
