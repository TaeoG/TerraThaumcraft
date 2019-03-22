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
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.terrathaumcraft.render.ModelHoverHarnessTTC;
import taeo.ttfcapi.utility.LogHelper;
import thaumcraft.api.IRepairable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.renderers.models.gear.ModelHoverHarness;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.blocks.ItemJarFilled;
import thaumcraft.common.items.armor.Hover;
import thaumcraft.common.items.armor.ItemHoverHarness;

import java.util.List;

public class ItemHoverHarnessTTC extends ItemHoverHarness{
	public ItemHoverHarnessTTC(ItemArmor.ArmorMaterial enumarmormaterial, int j, int k)
	{
		super(enumarmormaterial, j, k);
	}


	ModelBiped model = null;
	ModelBiped models = null;

	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		boolean isPlayer = entityLiving instanceof EntityPlayer && !(entityLiving.getCommandSenderName().contains("BiblioSteve"));
		if (this.model == null) this.model = new ModelHoverHarness();
		if(ReferenceTTC.smartMovingInstalled)
		{
			try{
				if(this.models == null) this.models = (ModelBiped) Class.forName("taeo.terrathaumcraft.render.ModelHoverHarnessTTC").newInstance(); //new ModelHoverHarnessTTC();
			}
			catch(ClassNotFoundException e)
			{
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		return isPlayer && ReferenceTTC.smartMovingInstalled? this.models :this.model;
	}



	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (!player.capabilities.isCreativeMode) {
			Hover.handleHoverArmor(player, player.inventory.armorItemInSlot(2));
		}
	}


	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4)
	{
		super.addInformation(is, player, list, par4);


	}

}
