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

import api.player.model.ModelPlayer;
import com.bioxx.tfc.Items.ItemTFCArmor;
import com.bioxx.tfc.api.Armor;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;
import com.bioxx.tfc.api.Interfaces.IClothing;
import com.bioxx.tfc.api.Interfaces.ISize;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.ISpecialArmor;
import taeo.terrathaumcraft.init.TTCItems;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.terrathaumcraft.render.ModelFortressArmorTTC;
import taeo.ttfcapi.utility.LogHelper;
import thaumcraft.client.renderers.models.gear.ModelFortressArmor;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.armor.ItemFortressArmor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ItemFortressArmorTTC extends ItemFortressArmor implements ISize, IClothing{

	//private ItemFortressArmor fortressArmor;
	private ItemTFCArmor tfcArmor;
	private Armor armor;
	public ItemFortressArmorTTC(Armor armor, ArmorMaterial enumarmormaterial, int j, int k) {
		super(enumarmormaterial, j, k);
		//fortressArmor = new ItemFortressArmor(enumarmormaterial,j,k);
		this.armor = armor;
		tfcArmor = new ItemTFCArmor(armor,j,k,enumarmormaterial,0,0);
	}
	/*@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister ir)
	{
		fortressArmor.registerIcons(ir);
	}
	@SideOnly(Side.CLIENT)
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
	{
		return fortressArmor.getArmorModel(entityLiving, itemStack, armorSlot);
	}
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1)
	{
		return fortressArmor.getIconFromDamage(par1);
	}

	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return fortressArmor.getArmorTexture(stack, entity, slot, type);
	}

	public EnumRarity getRarity(ItemStack itemstack)
	{
		return fortressArmor.getRarity(itemstack);
	}*/

	@Override
	public int getThermal() {
		return tfcArmor.getThermal();
	}

	@Override
	public int getBodyPart() {
		return tfcArmor.getBodyPart();
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return tfcArmor.getSize(is);
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		return tfcArmor.getWeight(is);
	}

	@Override
	public EnumItemReach getReach(ItemStack is) {
		return tfcArmor.getReach(is);
	}

	@Override
	public boolean canStack() {
		return false;
	}
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		super.addInformation(stack,player,list,par4);
		tfcArmor.addInformation(stack,player,list,par4);
	}
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return par2ItemStack.isItemEqual(new ItemStack(TTCItems.thaumiumIngot, 1, 0)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
	public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
	{
		int priority = 0;
		double ratio = this.damageReduceAmount / 25.0D;

		if(source instanceof ICausesDamage)
		{

		}
		if (source.isMagicDamage() == true) {
			priority = 1;
			ratio = this.damageReduceAmount / 35.0D;
		}
		else if ((source.isFireDamage() == true) || (source.isExplosion())) {
			priority = 1;
			ratio = this.damageReduceAmount / 20.0D;
		} else if (source.isUnblockable()) {
			priority = 0;
			ratio = 0.0D;
		}

		if ((player instanceof EntityPlayer)) {
			double set = 0.875D;
			for (int a = 1; a < 4; a++) {
				ItemStack piece = ((EntityPlayer)player).inventory.armorInventory[a];
				if ((piece != null) && ((piece.getItem() instanceof ItemFortressArmor))) {
					set += 0.125D;
					if ((piece.hasTagCompound()) && (piece.stackTagCompound.hasKey("mask"))) {
						set += 0.05D;
					}
				}
			}
			ratio *= set;
		}

		return new ISpecialArmor.ArmorProperties(priority, ratio, armor.getMaxDamage() + 1 - armor.getItemDamage());
	}

	ModelBiped model1 = null;
	ModelBiped model2 = null;
	ModelBiped model1s = null;
	ModelBiped model2s = null;
	ModelBiped model = null;


	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
	{
		boolean isPlayer = entityLiving instanceof EntityPlayer && !(entityLiving.getCommandSenderName().contains("BiblioSteve"));


		int type = ((ItemArmor)itemStack.getItem()).armorType;
		if (this.model1 == null) {
			this.model1 =  new ModelFortressArmor(1.0F);
		}
		if (this.model2 == null) {
			this.model2 =  new ModelFortressArmor(0.5F);
		}
		if(ReferenceTTC.smartMovingInstalled)
		{
			//LogHelper.info(ReferenceTTC.MOD_NAME, ModelFortressArmorTTC.class.getName());

			try {
				Constructor smartModelClass = Class.forName("taeo.terrathaumcraft.render.ModelFortressArmorTTC").getConstructor(float.class);
				if (this.model1s == null) {

						this.model1s = (ModelBiped) smartModelClass.newInstance(1.0F);// new ModelFortressArmorTTC(1.0F);
				}
				if (this.model2s == null) {
					this.model2s =  (ModelBiped) smartModelClass.newInstance(0.5f); //new ModelFortressArmorTTC(0.5F);
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				LogHelper.info(ReferenceTTC.MOD_NAME, "ModelFortressArmorTTC class doesn't appear to exist");
				//e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		}

		if ((type == 1) || (type == 3)) {
			this.model = isPlayer && ReferenceTTC.smartMovingInstalled? this.model1s : this.model1;
		} else {
			this.model = isPlayer && ReferenceTTC.smartMovingInstalled? this.model2s : this.model2;
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
