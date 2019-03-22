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

import java.util.List;

import api.player.model.ModelPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.smart.moving.render.SmartMovingRender;
import net.smart.moving.render.playerapi.SmartMoving;
import net.smart.moving.render.playerapi.SmartMovingRenderPlayerBase;
import org.lwjgl.opengl.GL11;










public class ModelKnightArmorTTC
		extends ModelPlayer
{
	ModelRenderer Frontcloth1;
	ModelRenderer Helmet;
	ModelRenderer BeltR;
	ModelRenderer Mbelt;
	ModelRenderer MbeltL;
	ModelRenderer MbeltR;
	ModelRenderer BeltL;
	ModelRenderer Chestplate;
	ModelRenderer CloakAtL;
	ModelRenderer Backplate;
	ModelRenderer Cloak3;
	ModelRenderer CloakAtR;
	ModelRenderer Tabbard;
	ModelRenderer Cloak1;
	ModelRenderer Cloak2;
	ModelRenderer ShoulderR1;
	ModelRenderer GauntletR;
	ModelRenderer GauntletstrapR1;
	ModelRenderer GauntletstrapR2;
	ModelRenderer ShoulderR;
	ModelRenderer ShoulderR0;
	ModelRenderer ShoulderR2;
	ModelRenderer ShoulderL1;
	ModelRenderer GauntletL;
	ModelRenderer GauntletstrapL1;
	ModelRenderer GauntletstrapL2;
	ModelRenderer ShoulderL;
	ModelRenderer ShoulderL0;
	ModelRenderer ShoulderL2;
	ModelRenderer SidepanelR3;
	ModelRenderer SidepanelR2;
	ModelRenderer SidepanelL2;
	ModelRenderer SidepanelR0;
	ModelRenderer SidepanelL0;
	ModelRenderer SidepanelR1;
	ModelRenderer SidepanelL3;
	ModelRenderer Frontcloth2;
	ModelRenderer SidepanelL1;

	public ModelKnightArmorTTC(float f)
	{
		super(f, 0.0F, 128, 64);
		this.textureWidth = 128;
		this.textureHeight = 64;



		this.Helmet = new ModelRenderer(this, 41, 8);
		this.Helmet.addBox(-4.5F, -9.0F, -4.5F, 9, 9, 9);
		this.Helmet.setTextureSize(128, 64);
		setRotation(this.Helmet, 0.0F, 0.0F, 0.0F);

		this.BeltR = new ModelRenderer(this, 76, 44);
		this.BeltR.addBox(-5.0F, 4.0F, -3.0F, 1, 3, 6);
		this.BeltR.setTextureSize(128, 64);
		setRotation(this.BeltR, 0.0F, 0.0F, 0.0F);

		this.Mbelt = new ModelRenderer(this, 56, 55);
		this.Mbelt.addBox(-4.0F, 8.0F, -3.0F, 8, 4, 1);
		this.Mbelt.setTextureSize(128, 64);
		setRotation(this.Mbelt, 0.0F, 0.0F, 0.0F);

		this.MbeltL = new ModelRenderer(this, 76, 44);
		this.MbeltL.addBox(4.0F, 8.0F, -3.0F, 1, 3, 6);
		this.MbeltL.setTextureSize(128, 64);
		setRotation(this.MbeltL, 0.0F, 0.0F, 0.0F);

		this.MbeltR = new ModelRenderer(this, 76, 44);
		this.MbeltR.addBox(-5.0F, 8.0F, -3.0F, 1, 3, 6);
		this.MbeltR.setTextureSize(128, 64);
		setRotation(this.MbeltR, 0.0F, 0.0F, 0.0F);

		this.BeltL = new ModelRenderer(this, 76, 44);
		this.BeltL.addBox(4.0F, 4.0F, -3.0F, 1, 3, 6);
		this.BeltL.setTextureSize(128, 64);
		setRotation(this.BeltL, 0.0F, 0.0F, 0.0F);

		this.Tabbard = new ModelRenderer(this, 114, 52);
		this.Tabbard.addBox(-3.0F, 1.2F, -3.5F, 6, 10, 1);
		this.Tabbard.setTextureSize(128, 64);
		setRotation(this.Tabbard, 0.0F, 0.0F, 0.0F);

		this.CloakAtL = new ModelRenderer(this, 0, 43);
		this.CloakAtL.addBox(2.5F, 1.0F, 2.0F, 2, 1, 3);
		this.CloakAtL.setTextureSize(128, 64);
		setRotation(this.CloakAtL, 0.1396263F, 0.0F, 0.0F);

		this.Backplate = new ModelRenderer(this, 36, 45);
		this.Backplate.addBox(-4.0F, 1.0F, 2.0F, 8, 11, 2);
		this.Backplate.setTextureSize(128, 64);
		setRotation(this.Backplate, 0.0F, 0.0F, 0.0F);

		this.Cloak1 = new ModelRenderer(this, 0, 47);
		this.Cloak1.addBox(0.0F, 0.0F, 0.0F, 9, 12, 1);
		this.Cloak1.setRotationPoint(-4.5F, 1.3F, 4.2F);
		this.Cloak1.setTextureSize(128, 64);
		setRotation(this.Cloak1, 0.1396263F, 0.0F, 0.0F);
		this.Cloak2 = new ModelRenderer(this, 0, 59);
		this.Cloak2.addBox(0.0F, 11.7F, -2.0F, 9, 4, 1);
		this.Cloak2.setRotationPoint(-4.5F, 1.3F, 4.2F);
		this.Cloak2.setTextureSize(128, 64);
		setRotation(this.Cloak2, 0.306945F, 0.0F, 0.0F);
		this.Cloak3 = new ModelRenderer(this, 0, 59);
		this.Cloak3.addBox(0.0F, 15.2F, -4.2F, 9, 4, 1);
		this.Cloak3.setRotationPoint(-4.5F, 1.3F, 4.2F);
		this.Cloak3.setTextureSize(128, 64);
		setRotation(this.Cloak3, 0.4465716F, 0.0F, 0.0F);

		this.CloakAtR = new ModelRenderer(this, 0, 43);
		this.CloakAtR.addBox(-4.5F, 1.0F, 2.0F, 2, 1, 3);
		this.CloakAtR.setTextureSize(128, 64);
		setRotation(this.CloakAtR, 0.1396263F, 0.0F, 0.0F);

		this.Chestplate = new ModelRenderer(this, 56, 45);
		this.Chestplate.addBox(-4.0F, 1.0F, -3.0F, 8, 7, 1);
		this.Chestplate.setTextureSize(128, 64);
		setRotation(this.Chestplate, 0.0F, 0.0F, 0.0F);





		this.ShoulderR1 = new ModelRenderer(this, 0, 19);
		this.ShoulderR1.addBox(-3.3F, 3.5F, -2.5F, 1, 1, 5);

		this.ShoulderR1.setTextureSize(128, 64);
		setRotation(this.ShoulderR1, 0.0F, 0.0F, 0.7853982F);

		this.GauntletR = new ModelRenderer(this, 100, 26);
		this.GauntletR.addBox(-3.5F, 3.5F, -2.5F, 2, 6, 5);

		this.GauntletR.setTextureSize(128, 64);
		this.GauntletR.mirror = true;
		setRotation(this.GauntletR, 0.0F, 0.0F, 0.0F);

		this.GauntletstrapR1 = new ModelRenderer(this, 84, 31);
		this.GauntletstrapR1.addBox(-1.5F, 3.5F, -2.5F, 3, 1, 5);

		this.GauntletstrapR1.setTextureSize(128, 64);
		this.GauntletstrapR1.mirror = true;
		setRotation(this.GauntletstrapR1, 0.0F, 0.0F, 0.0F);

		this.GauntletstrapR2 = new ModelRenderer(this, 84, 31);
		this.GauntletstrapR2.addBox(-1.5F, 6.5F, -2.5F, 3, 1, 5);

		this.GauntletstrapR2.setTextureSize(128, 64);
		this.GauntletstrapR2.mirror = true;
		setRotation(this.GauntletstrapR2, 0.0F, 0.0F, 0.0F);

		this.ShoulderR = new ModelRenderer(this, 56, 35);
		this.ShoulderR.addBox(-3.5F, -2.5F, -2.5F, 5, 5, 5);

		this.ShoulderR.setTextureSize(128, 64);
		this.ShoulderR.mirror = true;
		setRotation(this.ShoulderR, 0.0F, 0.0F, 0.0F);

		this.ShoulderR0 = new ModelRenderer(this, 0, 0);
		this.ShoulderR0.addBox(-4.3F, -1.5F, -3.0F, 3, 5, 6);

		this.ShoulderR0.setTextureSize(128, 64);
		this.ShoulderR0.mirror = true;
		setRotation(this.ShoulderR0, 0.0F, 0.0F, 0.7853982F);

		this.ShoulderR2 = new ModelRenderer(this, 0, 11);
		this.ShoulderR2.addBox(-2.3F, 3.5F, -3.0F, 1, 2, 6);

		this.ShoulderR2.setTextureSize(128, 64);
		this.ShoulderR2.mirror = true;
		setRotation(this.ShoulderR2, 0.0F, 0.0F, 0.7853982F);

		this.ShoulderL1 = new ModelRenderer(this, 0, 19);
		this.ShoulderL1.mirror = true;
		this.ShoulderL1.addBox(2.3F, 3.5F, -2.5F, 1, 1, 5);

		this.ShoulderL1.setTextureSize(128, 64);

		setRotation(this.ShoulderL1, 0.0F, 0.0F, -0.7853982F);

		this.GauntletL = new ModelRenderer(this, 114, 26);
		this.GauntletL.addBox(1.5F, 3.5F, -2.5F, 2, 6, 5);

		this.GauntletL.setTextureSize(128, 64);
		setRotation(this.GauntletL, 0.0F, 0.0F, 0.0F);

		this.GauntletstrapL1 = new ModelRenderer(this, 84, 31);
		this.GauntletstrapL1.mirror = true;
		this.GauntletstrapL1.addBox(-1.5F, 3.5F, -2.5F, 3, 1, 5);

		this.GauntletstrapL1.setTextureSize(128, 64);
		setRotation(this.GauntletstrapL1, 0.0F, 0.0F, 0.0F);

		this.GauntletstrapL2 = new ModelRenderer(this, 84, 31);
		this.GauntletstrapL2.mirror = true;
		this.GauntletstrapL2.addBox(-1.5F, 6.5F, -2.5F, 3, 1, 5);

		this.GauntletstrapL2.setTextureSize(128, 64);
		setRotation(this.GauntletstrapL2, 0.0F, 0.0F, 0.0F);

		this.ShoulderL = new ModelRenderer(this, 56, 35);
		this.ShoulderL.addBox(-1.5F, -2.5F, -2.5F, 5, 5, 5);

		this.ShoulderL.setTextureSize(128, 64);
		setRotation(this.ShoulderL, 0.0F, 0.0F, 0.0F);

		this.ShoulderL0 = new ModelRenderer(this, 0, 0);
		this.ShoulderL0.addBox(1.3F, -1.5F, -3.0F, 3, 5, 6);

		this.ShoulderL0.setTextureSize(128, 64);
		setRotation(this.ShoulderL0, 0.0F, 0.0F, -0.7853982F);

		this.ShoulderL2 = new ModelRenderer(this, 0, 11);
		this.ShoulderL2.addBox(1.3F, 3.5F, -3.0F, 1, 2, 6);

		this.ShoulderL2.setTextureSize(128, 64);
		setRotation(this.ShoulderL2, 0.0F, 0.0F, -0.7853982F);

		this.SidepanelR3 = new ModelRenderer(this, 116, 13);
		this.SidepanelR3.addBox(-3.0F, 2.5F, -2.5F, 1, 4, 5);

		this.SidepanelR3.setTextureSize(128, 64);
		setRotation(this.SidepanelR3, 0.0F, 0.0F, 0.1396263F);


		this.SidepanelR2 = new ModelRenderer(this, 114, 5);
		this.SidepanelR2.mirror = true;
		this.SidepanelR2.addBox(-2.0F, 2.5F, -2.5F, 2, 3, 5);

		this.SidepanelR2.setTextureSize(128, 64);
		setRotation(this.SidepanelR2, 0.0F, 0.0F, 0.1396263F);

		this.SidepanelL2 = new ModelRenderer(this, 114, 5);
		this.SidepanelL2.addBox(0.0F, 2.5F, -2.5F, 2, 3, 5);

		this.SidepanelL2.setTextureSize(128, 64);
		setRotation(this.SidepanelL2, 0.0F, 0.0F, -0.1396263F);

		this.SidepanelR0 = new ModelRenderer(this, 96, 14);
		this.SidepanelR0.addBox(-3.0F, -0.5F, -2.5F, 5, 3, 5);

		this.SidepanelR0.setTextureSize(128, 64);
		setRotation(this.SidepanelR0, 0.0F, 0.0F, 0.1396263F);

		this.SidepanelL0 = new ModelRenderer(this, 96, 14);
		this.SidepanelL0.addBox(-2.0F, -0.5F, -2.5F, 5, 3, 5);

		this.SidepanelL0.setTextureSize(128, 64);
		setRotation(this.SidepanelL0, 0.0F, 0.0F, -0.1396263F);

		this.SidepanelR1 = new ModelRenderer(this, 96, 7);
		this.SidepanelR1.mirror = true;
		this.SidepanelR1.addBox(0.0F, 2.5F, -2.5F, 2, 2, 5);

		this.SidepanelR1.setTextureSize(128, 64);
		setRotation(this.SidepanelR1, 0.0F, 0.0F, 0.1396263F);

		this.SidepanelL3 = new ModelRenderer(this, 116, 13);
		this.SidepanelL3.addBox(2.0F, 2.5F, -2.5F, 1, 4, 5);

		this.SidepanelL3.setTextureSize(128, 64);
		setRotation(this.SidepanelL3, 0.0F, 0.0F, -0.1396263F);

		this.SidepanelL1 = new ModelRenderer(this, 96, 7);
		this.SidepanelL1.addBox(-2.0F, 2.5F, -2.5F, 2, 2, 5);

		this.SidepanelL1.setTextureSize(128, 64);
		setRotation(this.SidepanelL1, 0.0F, 0.0F, -0.1396263F);

		this.Frontcloth1 = new ModelRenderer(this, 120, 39);
		this.Frontcloth1.addBox(0.0F, 0.0F, 0.0F, 6, 8, 1);
		this.Frontcloth1.setRotationPoint(-3.0F, 11.0F, -3.5F);
		this.Frontcloth1.setTextureSize(128, 64);
		setRotation(this.Frontcloth1, -0.10472F, 0.0F, 0.0F);

		this.Frontcloth2 = new ModelRenderer(this, 100, 37);
		this.Frontcloth2.addBox(0.0F, 7.5F, 1.8F, 6, 3, 1);
		this.Frontcloth2.setRotationPoint(-3.0F, 11.0F, -3.5F);
		this.Frontcloth2.setTextureSize(128, 64);
		setRotation(this.Frontcloth2, -0.3316126F, 0.0F, 0.0F);

		this.bipedHeadwear.cubeList.clear();
		this.bipedHead.cubeList.clear();
		this.bipedHead.addChild(this.Helmet);

		this.bipedBody.cubeList.clear();
		this.bipedRightLeg.cubeList.clear();
		this.bipedLeftLeg.cubeList.clear();

		this.bipedBody.addChild(this.Mbelt);
		this.bipedBody.addChild(this.MbeltL);
		this.bipedBody.addChild(this.MbeltR);

		if (f >= 1.0F)
		{

			this.bipedBody.addChild(this.Chestplate);
			this.bipedBody.addChild(this.Frontcloth1);
			this.bipedBody.addChild(this.Frontcloth2);
			this.bipedBody.addChild(this.Tabbard);
			this.bipedBody.addChild(this.Backplate);
			this.bipedBody.addChild(this.Cloak1);
			this.bipedBody.addChild(this.Cloak2);
			this.bipedBody.addChild(this.Cloak3);
			this.bipedBody.addChild(this.CloakAtL);
			this.bipedBody.addChild(this.CloakAtR);
		}

		this.bipedRightArm.cubeList.clear();
		this.bipedRightArm.addChild(this.ShoulderR);
		this.bipedRightArm.addChild(this.ShoulderR0);
		this.bipedRightArm.addChild(this.ShoulderR1);
		this.bipedRightArm.addChild(this.ShoulderR2);
		this.bipedRightArm.addChild(this.GauntletR);
		this.bipedRightArm.addChild(this.GauntletstrapR1);
		this.bipedRightArm.addChild(this.GauntletstrapR2);

		this.bipedLeftArm.cubeList.clear();
		this.bipedLeftArm.addChild(this.ShoulderL);
		this.bipedLeftArm.addChild(this.ShoulderL0);
		this.bipedLeftArm.addChild(this.ShoulderL1);
		this.bipedLeftArm.addChild(this.ShoulderL2);
		this.bipedLeftArm.addChild(this.GauntletL);
		this.bipedLeftArm.addChild(this.GauntletstrapL1);
		this.bipedLeftArm.addChild(this.GauntletstrapL2);

		this.bipedRightLeg.addChild(this.SidepanelR0);
		this.bipedRightLeg.addChild(this.SidepanelR1);
		this.bipedRightLeg.addChild(this.SidepanelR2);
		this.bipedRightLeg.addChild(this.SidepanelR3);

		this.bipedLeftLeg.addChild(this.SidepanelL0);
		this.bipedLeftLeg.addChild(this.SidepanelL1);
		this.bipedLeftLeg.addChild(this.SidepanelL2);
		this.bipedLeftLeg.addChild(this.SidepanelL3);
	}


	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{

		if(entity instanceof EntityPlayer)
		{
			super.render(entity, f, f1, f2, f3, f4, f5);
		}
		else
		{
			if (((entity instanceof EntitySkeleton)) || ((entity instanceof EntityZombie))) {
				setRotationAnglesZombie(f, f1, f2, f3, f4, f5, entity);
			} else {
				setRotationAngles(f, f1, f2, f3, f4, f5, entity);
			}
			if (this.isChild) {
				float f6 = 2.0F;
				GL11.glPushMatrix();
				GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
				GL11.glTranslatef(0.0F, 16.0F * f5, 0.0F);
				this.bipedHead.render(f5);
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
				GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
				this.bipedBody.render(f5);
				this.bipedRightArm.render(f5);
				this.bipedLeftArm.render(f5);
				this.bipedRightLeg.render(f5);
				this.bipedLeftLeg.render(f5);
				this.bipedHeadwear.render(f5);
				GL11.glPopMatrix();
			} else {
				GL11.glPushMatrix();
				GL11.glScalef(1.01F, 1.01F, 1.01F);
				this.bipedHead.render(f5);
				GL11.glPopMatrix();
				this.bipedBody.render(f5);
				this.bipedRightArm.render(f5);
				this.bipedLeftArm.render(f5);
				this.bipedRightLeg.render(f5);
				this.bipedLeftLeg.render(f5);
				this.bipedHeadwear.render(f5);
			}
		}
		boolean isCrawl = false;
		boolean isSlide = false;
		boolean isFly = false;
		boolean isDive = false;
		if(entity != null && entity instanceof EntityPlayer)
		{
			SmartMovingRenderPlayerBase smrpBase = SmartMoving.getPlayerBase((RenderPlayer) RenderManager.instance.getEntityRenderObject(entity));
			SmartMovingRender smRender = smrpBase.getRenderModel();
			isCrawl = smRender.modelBipedMain.isCrawl;
			isSlide = smRender.modelBipedMain.isSlide;
			isFly = smRender.modelBipedMain.isFlying;
			isDive = smRender.modelBipedMain.isDive;
		}



		float a = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		float b = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
		float c = Math.min(a, b);
		float frontC = c;
		float backC = c;

		if(entity.isSneaking())
		{
			frontC = c - 0.6f;;
			this.Frontcloth1.offsetZ = this.Frontcloth2.offsetZ = -.05f;
			this.Frontcloth1.offsetY = this.Frontcloth2.offsetY = -.15f;

		}
		else
		{
			this.Frontcloth1.offsetZ = this.Frontcloth2.offsetZ = 0f;
			this.Frontcloth1.offsetY = this.Frontcloth2.offsetY = 0f;
		}
		if(isCrawl || isSlide)
		{
			frontC = 0;
			backC = c/20f;
		}
		if(isFly)
		{
			backC = (c + 0.5f)/5f;
			frontC = c/40f;;
		}
		if(isDive)
		{
			backC = c/20f;
			frontC= c/40f;
		}

		this.Frontcloth1.rotateAngleX = (frontC - 0.10472F);
		this.Frontcloth2.rotateAngleX = (frontC - 0.3316126F);

		this.Cloak1.rotateAngleX = (-backC / 2.0F + 0.1396263F);
		this.Cloak2.rotateAngleX = (-backC / 2.0F + 0.306945F);
		this.Cloak3.rotateAngleX = (-backC / 2.0F + 0.4465716F);


	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}


	public void setRotationAnglesZombie(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
	{
		super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);

		float f6 = MathHelper.sin(this.onGround * 3.141593F);
		float f7 = MathHelper.sin((1.0F - (1.0F - this.onGround) * (1.0F - this.onGround)) * 3.141593F);


		this.bipedRightArm.rotateAngleZ = 0.0F;
		this.bipedLeftArm.rotateAngleZ = 0.0F;
		this.bipedRightArm.rotateAngleY = (-(0.1F - f6 * 0.6F));
		this.bipedLeftArm.rotateAngleY = (0.1F - f6 * 0.6F);
		this.bipedRightArm.rotateAngleX = -1.570796F;
		this.bipedLeftArm.rotateAngleX = -1.570796F;
		this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
		this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
		this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
		this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
		this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
		this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
	}
}



/* Location:           D:\Taeo\Desktop\ThaumaFirmaCraft2\Thaumcraft-1.7.10-4.2.3.5.deobfnew.jar

 * Qualified Name:     thaumcraft.client.renderers.models.gear.ModelKnightArmor

 * JD-Core Version:    0.7.0.1

 */