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




public class ModelLeaderArmorTTC
		extends ModelPlayer
{
	ModelRenderer Helmet;
	ModelRenderer CollarF;
	ModelRenderer CollarB;
	ModelRenderer CollarR;
	ModelRenderer CollarL;
	ModelRenderer BeltR;
	ModelRenderer Mbelt;
	ModelRenderer MbeltL;
	ModelRenderer MbeltR;
	ModelRenderer BeltL;
	ModelRenderer CloakTL;
	ModelRenderer Cloak3;
	ModelRenderer CloakTR;
	ModelRenderer Cloak1;
	ModelRenderer Cloak2;
	ModelRenderer Chestplate;
	ModelRenderer ChestOrnament;
	ModelRenderer ChestClothL;
	ModelRenderer ChestClothR;
	ModelRenderer Backplate;
	ModelRenderer GauntletstrapR1;
	ModelRenderer GauntletstrapR2;
	ModelRenderer ShoulderR;
	ModelRenderer ShoulderR1;
	ModelRenderer ShoulderR2;
	ModelRenderer ShoulderR5;
	ModelRenderer ShoulderR3;
	ModelRenderer ShoulderR4;
	ModelRenderer GauntletR2;
	ModelRenderer GauntletR;
	ModelRenderer GauntletL2;
	ModelRenderer GauntletstrapL1;
	ModelRenderer GauntletstrapL2;
	ModelRenderer ShoulderL;
	ModelRenderer ShoulderL1;
	ModelRenderer ShoulderL2;
	ModelRenderer ShoulderL3;
	ModelRenderer ShoulderL5;
	ModelRenderer ShoulderL4;
	ModelRenderer GauntletL;
	ModelRenderer LegClothR;
	ModelRenderer BackpanelR2;
	ModelRenderer BackpanelR3;
	ModelRenderer BackpanelR4;
	ModelRenderer LegClothL;
	ModelRenderer BackpanelL4;
	ModelRenderer BackpanelL2;
	ModelRenderer BackpanelL3;
	ModelRenderer BackpanelL1;
	ModelRenderer BackpanelR1;

	public ModelLeaderArmorTTC(float f)
	{
		super(f, 0.0F, 128, 64);
		this.textureWidth = 128;
		this.textureHeight = 64;

		this.Helmet = new ModelRenderer(this, 41, 8);
		this.Helmet.addBox(-4.5F, -9.0F, -4.5F, 9, 9, 9);
		this.Helmet.setTextureSize(128, 64);
		setRotation(this.Helmet, 0.0F, 0.0F, 0.0F);

		this.CollarF = new ModelRenderer(this, 17, 31);
		this.CollarF.addBox(-4.5F, -1.5F, -3.0F, 9, 4, 1);
		this.CollarF.setRotationPoint(0.0F, 0.0F, -2.5F);
		setRotation(this.CollarF, 0.226893F, 0.0F, 0.0F);
		this.CollarB = new ModelRenderer(this, 17, 26);
		this.CollarB.addBox(-4.5F, -1.5F, 7.0F, 9, 4, 1);
		this.CollarB.setRotationPoint(0.0F, 0.0F, -2.5F);
		setRotation(this.CollarB, 0.226893F, 0.0F, 0.0F);
		this.CollarR = new ModelRenderer(this, 17, 11);
		this.CollarR.addBox(-5.5F, -1.5F, -3.0F, 1, 4, 11);
		this.CollarR.setRotationPoint(0.0F, 0.0F, -2.5F);
		setRotation(this.CollarR, 0.226893F, 0.0F, 0.0F);
		this.CollarL = new ModelRenderer(this, 17, 11);
		this.CollarL.addBox(4.5F, -1.5F, -3.0F, 1, 4, 11);
		this.CollarL.setRotationPoint(0.0F, 0.0F, -2.5F);
		setRotation(this.CollarL, 0.226893F, 0.0F, 0.0F);

		this.BeltR = new ModelRenderer(this, 76, 44);
		this.BeltR.addBox(-5.0F, 4.0F, -3.0F, 1, 3, 6);
		this.Mbelt = new ModelRenderer(this, 56, 55);
		this.Mbelt.addBox(-4.0F, 8.0F, -3.0F, 8, 4, 1);
		this.MbeltL = new ModelRenderer(this, 76, 44);
		this.MbeltL.addBox(4.0F, 8.0F, -3.0F, 1, 3, 6);
		this.MbeltR = new ModelRenderer(this, 76, 44);
		this.MbeltR.addBox(-5.0F, 8.0F, -3.0F, 1, 3, 6);
		this.BeltL = new ModelRenderer(this, 76, 44);
		this.BeltL.addBox(4.0F, 4.0F, -3.0F, 1, 3, 6);

		this.CloakTL = new ModelRenderer(this, 0, 43);
		this.CloakTL.addBox(2.5F, 1.0F, -1.0F, 2, 1, 3);
		this.CloakTL.setRotationPoint(0.0F, 0.0F, 3.0F);
		setRotation(this.CloakTL, 0.1396263F, 0.0F, 0.0F);
		this.Cloak3 = new ModelRenderer(this, 0, 59);
		this.Cloak3.addBox(-4.5F, 17.0F, -3.7F, 9, 4, 1);
		this.Cloak3.setRotationPoint(0.0F, 0.0F, 3.0F);
		setRotation(this.Cloak3, 0.4465716F, 0.0F, 0.0F);
		this.CloakTR = new ModelRenderer(this, 0, 43);
		this.CloakTR.addBox(-4.5F, 1.0F, -1.0F, 2, 1, 3);
		this.CloakTR.setRotationPoint(0.0F, 0.0F, 3.0F);
		setRotation(this.CloakTR, 0.1396263F, 0.0F, 0.0F);
		this.Cloak1 = new ModelRenderer(this, 0, 47);
		this.Cloak1.addBox(-4.5F, 2.0F, 1.0F, 9, 12, 1);
		this.Cloak1.setRotationPoint(0.0F, 0.0F, 3.0F);
		setRotation(this.Cloak1, 0.1396263F, 0.0F, 0.0F);
		this.Cloak2 = new ModelRenderer(this, 0, 59);
		this.Cloak2.addBox(-4.5F, 14.0F, -1.3F, 9, 4, 1);
		this.Cloak2.setRotationPoint(0.0F, 0.0F, 3.0F);
		setRotation(this.Cloak2, 0.306945F, 0.0F, 0.0F);

		this.Chestplate = new ModelRenderer(this, 56, 45);
		this.Chestplate.addBox(-4.0F, 1.0F, -3.8F, 8, 7, 2);
		this.ChestOrnament = new ModelRenderer(this, 76, 53);
		this.ChestOrnament.addBox(-2.5F, 3.0F, -4.8F, 5, 5, 1);
		this.ChestClothL = new ModelRenderer(this, 20, 47);
		this.ChestClothL.mirror = true;
		this.ChestClothL.addBox(1.5F, 1.2F, -4.5F, 3, 9, 1);
		setRotation(this.ChestClothL, 0.0663225F, 0.0F, 0.0F);
		this.ChestClothR = new ModelRenderer(this, 20, 47);
		this.ChestClothR.addBox(-4.5F, 1.2F, -4.5F, 3, 9, 1);
		setRotation(this.ChestClothR, 0.0663225F, 0.0F, 0.0F);
		this.Backplate = new ModelRenderer(this, 36, 45);
		this.Backplate.addBox(-4.0F, 1.0F, 2.0F, 8, 11, 2);

		this.GauntletR = new ModelRenderer(this, 100, 26);
		this.GauntletR.addBox(-3.5F, 3.5F, -2.5F, 2, 6, 5);
		this.GauntletL = new ModelRenderer(this, 114, 26);
		this.GauntletL.addBox(1.5F, 3.5F, -2.5F, 2, 6, 5);
		this.GauntletstrapL1 = new ModelRenderer(this, 84, 31);
		this.GauntletstrapL1.mirror = true;
		this.GauntletstrapL1.addBox(-1.5F, 3.5F, -2.5F, 3, 1, 5);
		this.GauntletstrapL2 = new ModelRenderer(this, 84, 31);
		this.GauntletstrapL2.mirror = true;
		this.GauntletstrapL2.addBox(-1.5F, 6.5F, -2.5F, 3, 1, 5);
		this.GauntletstrapR1 = new ModelRenderer(this, 84, 31);
		this.GauntletstrapR1.addBox(-1.5F, 3.5F, -2.5F, 3, 1, 5);
		this.GauntletstrapR2 = new ModelRenderer(this, 84, 31);
		this.GauntletstrapR2.addBox(-1.5F, 6.5F, -2.5F, 3, 1, 5);
		this.GauntletR2 = new ModelRenderer(this, 102, 37);
		this.GauntletR2.addBox(-5.0F, 3.5F, -2.0F, 1, 5, 4);
		setRotation(this.GauntletR2, 0.0F, 0.0F, -0.1675516F);
		this.GauntletL2 = new ModelRenderer(this, 102, 37);
		this.GauntletL2.addBox(4.0F, 3.5F, -2.0F, 1, 5, 4);
		setRotation(this.GauntletL2, 0.0F, 0.0F, 0.1675516F);

		this.ShoulderR = new ModelRenderer(this, 56, 35);
		this.ShoulderR.addBox(-3.5F, -2.5F, -2.5F, 5, 5, 5);
		this.ShoulderR1 = new ModelRenderer(this, 0, 0);
		this.ShoulderR1.addBox(-4.3F, -1.5F, -3.0F, 3, 5, 6);
		setRotation(this.ShoulderR1, 0.0F, 0.0F, 0.7853982F);
		this.ShoulderR2 = new ModelRenderer(this, 0, 19);
		this.ShoulderR2.addBox(-3.3F, 3.5F, -2.5F, 1, 1, 5);
		setRotation(this.ShoulderR2, 0.0F, 0.0F, 0.7853982F);
		this.ShoulderR5 = new ModelRenderer(this, 18, 4);
		this.ShoulderR5.addBox(-2.3F, -1.5F, 3.0F, 1, 6, 1);
		setRotation(this.ShoulderR5, 0.0F, 0.0F, 0.7853982F);
		this.ShoulderR3 = new ModelRenderer(this, 0, 11);
		this.ShoulderR3.addBox(-2.3F, 3.5F, -3.0F, 1, 2, 6);
		setRotation(this.ShoulderR3, 0.0F, 0.0F, 0.7853982F);
		this.ShoulderR4 = new ModelRenderer(this, 18, 4);
		this.ShoulderR4.addBox(-2.3F, -1.5F, -4.0F, 1, 6, 1);
		setRotation(this.ShoulderR4, 0.0F, 0.0F, 0.7853982F);

		this.ShoulderL = new ModelRenderer(this, 56, 35);
		this.ShoulderL.addBox(-1.5F, -2.5F, -2.5F, 5, 5, 5);
		this.ShoulderL1 = new ModelRenderer(this, 0, 0);
		this.ShoulderL1.addBox(1.3F, -1.5F, -3.0F, 3, 5, 6);
		setRotation(this.ShoulderL1, 0.0F, 0.0F, -0.7853982F);
		this.ShoulderL2 = new ModelRenderer(this, 0, 19);
		this.ShoulderL2.mirror = true;
		this.ShoulderL2.addBox(2.3F, 3.5F, -2.5F, 1, 1, 5);
		setRotation(this.ShoulderL2, 0.0F, 0.0F, -0.7853982F);
		this.ShoulderL3 = new ModelRenderer(this, 0, 11);
		this.ShoulderL3.addBox(1.3F, 3.5F, -3.0F, 1, 2, 6);
		setRotation(this.ShoulderL3, 0.0F, 0.0F, -0.7853982F);
		this.ShoulderL5 = new ModelRenderer(this, 18, 4);
		this.ShoulderL5.addBox(1.3F, -1.5F, 3.0F, 1, 6, 1);
		this.ShoulderL5.setTextureSize(128, 64);
		setRotation(this.ShoulderL5, 0.0F, 0.0F, -0.7853982F);
		this.ShoulderL4 = new ModelRenderer(this, 18, 4);
		this.ShoulderL4.addBox(1.3F, -1.5F, -4.0F, 1, 6, 1);
		setRotation(this.ShoulderL4, 0.0F, 0.0F, -0.7853982F);

		this.LegClothR = new ModelRenderer(this, 20, 55);
		this.LegClothR.addBox(0.0F, 0.0F, 0.0F, 3, 8, 1);
		this.LegClothR.setRotationPoint(-4.5F, 10.4F, -3.9F);
		setRotation(this.LegClothR, -0.034907F, 0.0F, 0.0F);
		this.LegClothL = new ModelRenderer(this, 20, 55);
		this.LegClothL.mirror = true;
		this.LegClothL.addBox(0.0F, 0.0F, 0.0F, 3, 8, 1);
		this.LegClothL.setRotationPoint(1.5F, 10.4F, -3.9F);
		setRotation(this.LegClothL, -0.034907F, 0.0F, 0.0F);

		this.BackpanelR1 = new ModelRenderer(this, 0, 25);
		this.BackpanelR1.addBox(-3.0F, -0.5F, 2.5F, 5, 7, 1);
		setRotation(this.BackpanelR1, 0.0698132F, 0.0F, 0.0F);
		this.BackpanelR2 = new ModelRenderer(this, 96, 14);
		this.BackpanelR2.addBox(-3.0F, -0.5F, -2.5F, 5, 3, 5);
		setRotation(this.BackpanelR2, 0.0F, 0.0F, 0.1396263F);
		this.BackpanelR3 = new ModelRenderer(this, 116, 13);
		this.BackpanelR3.addBox(-3.0F, 2.5F, -2.5F, 1, 4, 5);
		setRotation(this.BackpanelR3, 0.0F, 0.0F, 0.1396263F);
		this.BackpanelR4 = new ModelRenderer(this, 0, 25);
		this.BackpanelR4.mirror = true;
		this.BackpanelR4.addBox(-3.0F, -0.5F, -3.5F, 5, 7, 1);
		setRotation(this.BackpanelR4, -0.034907F, 0.0F, 0.0F);

		this.BackpanelL1 = new ModelRenderer(this, 0, 25);
		this.BackpanelL1.addBox(-2.0F, -0.5F, 2.5F, 5, 7, 1);
		setRotation(this.BackpanelL1, 0.0698132F, 0.0F, 0.0F);
		this.BackpanelL4 = new ModelRenderer(this, 0, 25);
		this.BackpanelL4.addBox(-2.0F, -0.5F, -3.5F, 5, 7, 1);
		setRotation(this.BackpanelL4, -0.034907F, 0.0F, 0.0F);
		this.BackpanelL2 = new ModelRenderer(this, 96, 14);
		this.BackpanelL2.addBox(-2.0F, -0.5F, -2.5F, 5, 3, 5);
		setRotation(this.BackpanelL2, 0.0F, 0.0F, -0.1396263F);
		this.BackpanelL3 = new ModelRenderer(this, 116, 13);
		this.BackpanelL3.addBox(2.0F, 2.5F, -2.5F, 1, 4, 5);
		setRotation(this.BackpanelL3, 0.0F, 0.0F, -0.1396263F);

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

			this.bipedBody.addChild(this.BeltL);
			this.bipedBody.addChild(this.BeltR);
			this.bipedBody.addChild(this.Chestplate);
			this.bipedBody.addChild(this.ChestOrnament);
			this.bipedBody.addChild(this.ChestClothR);
			this.bipedBody.addChild(this.ChestClothL);
			this.bipedBody.addChild(this.LegClothR);
			this.bipedBody.addChild(this.LegClothL);
			this.bipedBody.addChild(this.Backplate);
			this.bipedBody.addChild(this.CollarB);
			this.bipedBody.addChild(this.CollarR);
			this.bipedBody.addChild(this.CollarL);
			this.bipedBody.addChild(this.CollarF);
			this.bipedBody.addChild(this.Cloak1);
			this.bipedBody.addChild(this.Cloak2);
			this.bipedBody.addChild(this.Cloak3);
			this.bipedBody.addChild(this.CloakTL);
			this.bipedBody.addChild(this.CloakTR);
		}

		this.bipedRightArm.cubeList.clear();
		this.bipedRightArm.addChild(this.ShoulderR);
		this.bipedRightArm.addChild(this.ShoulderR1);
		this.bipedRightArm.addChild(this.ShoulderR2);
		this.bipedRightArm.addChild(this.ShoulderR3);
		this.bipedRightArm.addChild(this.ShoulderR4);
		this.bipedRightArm.addChild(this.ShoulderR5);
		this.bipedRightArm.addChild(this.GauntletR);
		this.bipedRightArm.addChild(this.GauntletR2);
		this.bipedRightArm.addChild(this.GauntletstrapR1);
		this.bipedRightArm.addChild(this.GauntletstrapR2);

		this.bipedLeftArm.cubeList.clear();
		this.bipedLeftArm.addChild(this.ShoulderL);
		this.bipedLeftArm.addChild(this.ShoulderL1);
		this.bipedLeftArm.addChild(this.ShoulderL2);
		this.bipedLeftArm.addChild(this.ShoulderL3);
		this.bipedLeftArm.addChild(this.ShoulderL4);
		this.bipedLeftArm.addChild(this.ShoulderL5);
		this.bipedLeftArm.addChild(this.GauntletL);
		this.bipedLeftArm.addChild(this.GauntletL2);
		this.bipedLeftArm.addChild(this.GauntletstrapL1);
		this.bipedLeftArm.addChild(this.GauntletstrapL2);

		this.bipedRightLeg.addChild(this.BackpanelR1);
		this.bipedRightLeg.addChild(this.BackpanelR2);
		this.bipedRightLeg.addChild(this.BackpanelR3);
		this.bipedRightLeg.addChild(this.BackpanelR4);

		this.bipedLeftLeg.addChild(this.BackpanelL1);
		this.bipedLeftLeg.addChild(this.BackpanelL2);
		this.bipedLeftLeg.addChild(this.BackpanelL3);
		this.bipedLeftLeg.addChild(this.BackpanelL4);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

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




		float a = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		float b = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
		float c = Math.min(a, b);

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

		if(entity.isSneaking())
		{
			a = a - 0.6f;
			b = b - 0.6f;
			this.LegClothR.offsetZ = this.LegClothL.offsetZ = -.05f;
			this.LegClothR.offsetY = this.LegClothL.offsetY = -.15f;

		}
		else
		{
			this.LegClothR.offsetZ = this.LegClothL.offsetZ = 0f;
			this.LegClothR.offsetY = this.LegClothL.offsetY = 0f;
		}
		if(isCrawl || isSlide)
		{
			a = b = 0;
			c = c/20f;
		}
		if(isFly)
		{
			c = (c + 0.5f)/5f;
			a= a/40f;
			b = b /40f;
		}
		if(isDive)
		{
			c = c/20f;
			a= a/40f;
			b = b /40f;
		}

		this.LegClothR.rotateAngleX = (a - 0.10472F);
		this.LegClothL.rotateAngleX = (b - 0.10472F);

		this.Cloak1.rotateAngleX = (-c / 2.0F + 0.1396263F);
		this.Cloak2.rotateAngleX = (-c / 2.0F + 0.306945F);
		this.Cloak3.rotateAngleX = (-c / 2.0F + 0.4465716F);


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

 * Qualified Name:     thaumcraft.client.renderers.models.gear.ModelLeaderArmor

 * JD-Core Version:    0.7.0.1

 */