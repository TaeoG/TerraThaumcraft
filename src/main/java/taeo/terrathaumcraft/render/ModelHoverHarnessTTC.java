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

import api.player.forge.PlayerAPIContainer;
import api.player.forge.PlayerAPIPlugin;
import api.player.model.ModelPlayer;
import cpw.mods.fml.client.FMLClientHandler;
import java.util.HashMap;
import java.util.Random;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.smart.moving.IEntityPlayerMP;
import net.smart.moving.SmartMovingClient;
//import net.smart.moving.render.ModelPlayer;
import net.smart.moving.render.SmartMovingRender;
import net.smart.moving.render.playerapi.SmartMoving;
import net.smart.moving.render.playerapi.SmartMovingRenderPlayerBase;
import net.smart.render.SmartRenderModel;
import net.smart.render.playerapi.SmartRender;
import net.smart.render.playerapi.SmartRenderRenderPlayerBase;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.fx.bolt.FXLightningBolt;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.items.armor.ItemHoverHarness;
import thaumcraft.common.lib.utils.BlockUtils;

@cpw.mods.fml.relauncher.SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
public class ModelHoverHarnessTTC extends ModelPlayer
{
	HashMap<Integer, Long> timingShock = new HashMap();
	private static final ResourceLocation HARNESS = new ResourceLocation("thaumcraft", "textures/models/hoverharness.obj");
	private IModelCustom modelBack;
	ModelRenderer harness;

	public ModelHoverHarnessTTC()
	{
		super();
		//this.getRenderModel().bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.6F);
		//this.bipedBody = new ModelRenderer(this, 16, 16);
		//this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.6F);

		this.harness = new ModelRenderer(this, 16,16);
		this.harness.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.6F);
		this.bipedBody.cubeList.clear();
		this.bipedBody.addChild(harness);
		this.modelBack = net.minecraftforge.client.model.AdvancedModelLoader.loadModel(HARNESS);
	}




	public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		//super.render(entity, par2, par3, par4, par5, par6, par7);
		SmartRenderRenderPlayerBase base;
		SmartRenderModel body;
		SmartMovingRender smRender;
		SmartMovingRenderPlayerBase smrpBase;
		boolean isCrawl;
		boolean isSlide;
		float rotationX;
		float rotationY;
		if(entity != null && entity instanceof EntityPlayer)
		{
			base = SmartRender.getPlayerBase((RenderPlayer) RenderManager.instance.getEntityRenderObject(entity));
			body = base.getRenderRender().modelBipedMain;
			smrpBase = SmartMoving.getPlayerBase((RenderPlayer) RenderManager.instance.getEntityRenderObject(entity));
			smRender = smrpBase.getRenderModel();
			isCrawl = smRender.modelBipedMain.isCrawl;
			isSlide = smRender.modelBipedMain.isSlide;
			rotationX = body.bipedOuter.rotateAngleX;
			rotationY = body.bipedOuter.rotateAngleY;
		}
		else
		{
			base = null;
			body = null;
			smrpBase = null;
			smRender = null;
			isCrawl = false;
			isSlide = false;
			if(entity instanceof EntityLiving)
			{
				rotationX = 0;//entity.rotationYaw;
			}
			else
			{
				rotationX = 0;
			}
			rotationY = 0;
		}
		GL11.glPushMatrix();

		GL11.glPushMatrix();
		/*if ((entity != null)) {
			if(entity.isSneaking())
			{
				GL11.glRotatef(28.64789F, 1.0F, 0.0F, 0.0F);
				//GL11.glRotatef(entity.rotationPitch, 0f, 1f, 0f);
			}
		}*/
		//super.render(entity, par2, par3, par4, par5, par6, par7);
		GL11.glRotatef((float) (rotationY / Math.PI) * 180f, 0.0F, 1.0F, 0.0F);

		if (entity != null) {
			if(entity.isSneaking())
			{
				GL11.glRotatef(28.64789F, 1.0F, 0.0F, 0.0F);
			}
			else if(isCrawl || isSlide)
			{
				GL11.glRotatef(80f, 1f, 0f, 0f);
				GL11.glTranslatef(0.0F, 0f, -0.2f);// .37F);


			}
			else
			{
				GL11.glRotatef((float) (rotationX / Math.PI) * 180f, 1.0f, 0.0f, 0.0f);
			}

		}
		//super.render(entity, par2, par3, par4, par5, par6, par7);
		this.bipedBody.render(par7);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glScalef(0.1F, 0.1F, 0.1F);
		GL11.glDisable(2896);
		GL11.glRotatef(90.0F, -1.0F, 0.0F, 0.0F);

		GL11.glRotatef((float) (rotationY / Math.PI) * 180f, 0.0F, 0.0F, 1.0F);

		if (entity != null) {
			if(entity.isSneaking())
			{
				GL11.glRotatef(28.64789F, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.0F, 0.33F, -3.7F);
			}
			else if(isCrawl || isSlide)
			{
				GL11.glTranslatef(0.0F, 2f, 3.7F);
				GL11.glRotatef(90f, 1f, 0f, 0f);

			}
			else
			{
				GL11.glRotatef((float) (rotationX / Math.PI) * 180f, 1.0f, 0.0f, 0.0f);
				GL11.glTranslatef(0.0F, 0.33F, -3.7F);
			}

		}


		FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation("thaumcraft", "textures/models/hoverharness2.png"));



		//super.render(entity, par2, par3, par4, par5, par6, par7);

		this.modelBack.renderAll();
		GL11.glEnable(2896);
		GL11.glPopMatrix();

		//super.render(entity, par2, par3, par4, par5, par6, par7);

		if ((entity != null) && ((entity instanceof EntityPlayer)) && (!GL11.glIsEnabled(3042)) && (GL11.glGetInteger(2976) == 5888) && (((EntityPlayer)entity).inventory.armorItemInSlot(2).hasTagCompound()) && (((EntityPlayer)entity).inventory.armorItemInSlot(2).stackTagCompound.hasKey("hover")) && (((EntityPlayer)entity).inventory.armorItemInSlot(2).stackTagCompound.getByte("hover") == 1))
		{




			long currenttime = System.currentTimeMillis();
			long timeShock = 0L;
			if (this.timingShock.get(Integer.valueOf(entity.getEntityId())) != null) { timeShock = ((Long)this.timingShock.get(Integer.valueOf(entity.getEntityId()))).longValue();
			}
			GL11.glPushMatrix();
			float mod = 0.0F;
			//float mod = 0.075F;

			if (entity.isSneaking()) {
				GL11.glRotatef(28.64789F, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.0F, 0.075F, -0.05F);
				mod = 0.075F;
			}

			GL11.glRotatef((float) (rotationY / Math.PI) * 180f, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef((float) (rotationX / Math.PI) * 180f, 1.0f, 0.0f, 0.0f);
			GL11.glTranslatef(0.0F, 0.2F, 0.55F);
			GL11.glPushMatrix();
			UtilsFX.renderQuadCenteredFromIcon(false, ((ItemHoverHarness) ((EntityPlayer) entity).inventory.armorItemInSlot(2).getItem()).iconLightningRing, 2.5F, 1.0F, 1.0F, 1.0F, 230, 1, 1.0F);


			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, 0.0F, 0.03F);


			UtilsFX.renderQuadCenteredFromIcon(false, ((ItemHoverHarness) ((EntityPlayer) entity).inventory.armorItemInSlot(2).getItem()).iconLightningRing, 1.5F, 1.0F, 0.5F, 1.0F, 230, 1, 1.0F);


			GL11.glPopMatrix();
			GL11.glPopMatrix();


			if (timeShock < currenttime) {
				timeShock = currenttime + 50L + entity.worldObj.rand.nextInt(50);
				this.timingShock.put(Integer.valueOf(entity.getEntityId()), Long.valueOf(timeShock));

				MovingObjectPosition mop = BlockUtils.getTargetBlock(entity.worldObj, entity.posX, entity.posY - 0.449999988079071D - mod, entity.posZ, ((EntityPlayer)entity).renderYawOffset - 90.0F - entity.worldObj.rand.nextInt(180), -80 + entity.worldObj.rand.nextInt(160), false, 6.0D);



				if (mop != null)
				{
					double px = mop.hitVec.xCoord;
					double py = mop.hitVec.yCoord;
					double pz = mop.hitVec.zCoord;

					FXLightningBolt bolt = new FXLightningBolt(entity.worldObj, entity.posX - MathHelper.cos((((EntityPlayer)entity).renderYawOffset + 90.0F) / 180.0F * 3.141593F) * 0.5F, entity.posY - 0.449999988079071D - mod, entity.posZ - MathHelper.sin((((EntityPlayer)entity).renderYawOffset + 90.0F) / 180.0F * 3.141593F) * 0.5F, px, py, pz, entity.worldObj.rand.nextLong(), 1, 2.0F, 3);





					bolt.defaultFractal();
					bolt.setType(6);
					bolt.setWidth(0.015F);
					bolt.finalizeBolt();
				}
			}
		}


		GL11.glPopMatrix();
	}
}



/* Location:           D:\Taeo\Desktop\ThaumaFirmaCraft2\Thaumcraft-1.7.10-4.2.3.5.deobfnew.jar

 * Qualified Name:     thaumcraft.client.renderers.models.gear.ModelHoverHarness

 * JD-Core Version:    0.7.0.1

 */