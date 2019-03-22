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

package taeo.terrathaumcraft.item.equipment;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.terrathaumcraft.render.ModelRobeTTC;
import taeo.terrathaumcraft.render.ModelVoidRobeTTC;
import thaumcraft.client.renderers.models.gear.ModelRobe;
import thaumcraft.common.items.armor.ItemVoidRobeArmor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ItemVoidRobeArmorTTC extends ItemVoidRobeArmor {
	public ItemVoidRobeArmorTTC(ArmorMaterial enumarmormaterial, int j, int k) {
		super(enumarmormaterial, j, k);
	}
	ModelBiped model1 = null;
	ModelBiped model2 = null;
	ModelBiped model = null;
	ModelBiped model1smart = null;
	ModelBiped model2smart = null;
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
	{
		int type = ((ItemArmor)itemStack.getItem()).armorType;

		if (this.model1 == null) {
			this.model1 = new ModelRobe(1.0F);
		}
		if (this.model2 == null) {
			this.model2 = new ModelRobe(0.5F);
		}


		boolean isPlayer = entityLiving instanceof EntityPlayer && !(entityLiving.getCommandSenderName().contains("BiblioSteve"));

		try {
			if (ReferenceTTC.smartMovingInstalled) {
				Constructor smartModel = Class.forName("taeo.terrathaumcraft.render.ModelRobeTTC").getConstructor(float.class);
				if (this.model1smart == null) {
					this.model1smart = (ModelBiped) smartModel.newInstance(1.0F); // new ModelRobeTTC(1.0F);
				}
				if (this.model2smart == null) {
					this.model2smart = (ModelBiped) smartModel.newInstance(0.5F); //new ModelRobeTTC(0.5F);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}


		if ((type == 1) || (type == 3)) {
			this.model = isPlayer && ReferenceTTC.smartMovingInstalled? this.model1smart: this.model1;
		} else {
			this.model = isPlayer && ReferenceTTC.smartMovingInstalled? this.model2smart : this.model2;
		}

		if (this.model != null) {
			this.model.bipedHead.showModel = (armorSlot == 0);
			this.model.bipedHeadwear.showModel = (armorSlot == 0);
			this.model.bipedBody.showModel = ((armorSlot == 1) || (armorSlot == 2));
			this.model.bipedRightArm.showModel = (armorSlot == 1);
			this.model.bipedLeftArm.showModel = (armorSlot == 1);
			this.model.bipedRightLeg.showModel = (armorSlot == 2);
			this.model.bipedLeftLeg.showModel = (armorSlot == 2);
			this.model.isSneak = entityLiving.isSneaking();

			this.model.isRiding = entityLiving.isRiding();
			this.model.isChild = entityLiving.isChild();
			this.model.aimedBow = false;
			this.model.heldItemRight = (entityLiving.getHeldItem() != null ? 1 : 0);
			if ((entityLiving instanceof EntityPlayer))
			{
				if (((EntityPlayer)entityLiving).getItemInUseDuration() > 0)
				{
					EnumAction enumaction = ((EntityPlayer)entityLiving).getItemInUse().getItemUseAction();

					if (enumaction == EnumAction.block)
					{
						this.model.heldItemRight = 3;
					}
					else if (enumaction == EnumAction.bow)
					{
						this.model.aimedBow = true;
					}
				}
			}
		}


		return this.model;
	}

}
