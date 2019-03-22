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

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Items.Tools.ItemTerraTool;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;
import com.bioxx.tfc.api.Interfaces.ISize;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import taeo.terrathaumcraft.init.TTCItems;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import thaumcraft.common.items.equipment.ItemElementalAxe;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumcraft.common.lib.utils.Utils;

import java.awt.*;
import java.util.List;

public class ItemElementalAxeTTC extends ItemElementalAxe implements ISize, ICausesDamage{
	private float toolDamage;
	public ItemElementalAxeTTC() {
		super(TTCItems.ThaumiumEnhancedToolMaterial);
		this.toolDamage = TTCItems.ThaumicSteel.getDamageVsEntity();
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.LARGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		return EnumWeight.MEDIUM;
	}

	@Override
	public EnumItemReach getReach(ItemStack is) {
		return EnumItemReach.FAR;
	}

	@Override
	public boolean canStack() {
		return false;
	}
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);
		arraylist.add(EnumChatFormatting.AQUA + TFC_Core.translate(getDamageType().toString()));
		ItemTerraTool.addSmithingBonusInformation(is, arraylist);
	}

	@Override
	public EnumDamageType getDamageType() {
		return EnumDamageType.SLASHING;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player)
	{return false;}
		/**World world = player.worldObj;
		net.minecraft.block.Block bi = world.getBlock(x, y, z);

		if ((!player.isSneaking()) && (Utils.isWoodLog(world, x, y, z))) {
			if (!world.isRemote) {
				//BlockUtils.breakFurthestBlock(world, x, y, z, bi, player, true, 10);
				PacketHandler.INSTANCE.sendToAllAround(new thaumcraft.common.lib.network.fx.PacketFXBlockBubble(x, y, z, new Color(0.33F, 0.33F, 1.0F).getRGB()), new cpw.mods.fml.common.network.NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, 32.0D));


				world.playSoundEffect(x, y, z, "thaumcraft:bubble", 0.15F, 1.0F);
			}
			//itemstack.damageItem(1, player);
			//return true;
			return false;
		}

		return super.onBlockStartBreak(itemstack, x, y, z, player);
	}*/

	@Override
	public Multimap getAttributeModifiers(ItemStack is)
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", getWeaponDamage(is), 0));
		return multimap;
	}

	public double getWeaponDamage(ItemStack is)
	{
		return Math.floor(toolDamage + (toolDamage * AnvilManager.getDamageBuff(is)));
	}

	@Override
	public int getMaxDamage(ItemStack is)
	{
		return (int) Math.floor(getMaxDamage() + (getMaxDamage() * AnvilManager.getDurabilityBuff(is)));
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta)
	{
		float digSpeed = super.getDigSpeed(stack, block, meta);

		if (ForgeHooks.isToolEffective(stack, block, meta))
		{
			return digSpeed + (digSpeed * AnvilManager.getDurabilityBuff(stack));
		}
		return digSpeed;
	}
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return String.format("item.%s%s", ReferenceTTC.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}

}
