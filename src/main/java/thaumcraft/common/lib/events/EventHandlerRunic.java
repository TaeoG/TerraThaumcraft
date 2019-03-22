

package thaumcraft.common.lib.events;

import baubles.api.BaublesApi;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import java.sql.Ref;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.reference.ReferenceTAPI;
import thaumcraft.api.IRunicArmor;
import thaumcraft.api.IWarpingGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.entities.IEldritchMob;
import thaumcraft.common.config.Config;
import thaumcraft.common.entities.monster.mods.ChampionModifier;
import thaumcraft.common.items.armor.ItemFortressArmor;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXShield;
import thaumcraft.common.lib.network.playerdata.PacketRunicCharge;
import thaumcraft.common.lib.utils.EntityUtils;

public class EventHandlerRunic
{
	public HashMap<Integer, Integer> runicCharge = new HashMap();
	private HashMap<Integer, Long> nextCycle = new HashMap();
	private HashMap<Integer, Integer> lastCharge = new HashMap();
	public HashMap<Integer, Integer[]> runicInfo = new HashMap();
	private HashMap<String, Long> upgradeCooldown = new HashMap();

	public boolean isDirty = true;
	private int rechargeDelay = 0;


	@SubscribeEvent
	public void livingTick(net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent event)
	{
		if ((!event.entity.worldObj.isRemote) && ((event.entity instanceof EntityPlayer))) {
			EntityPlayer player = (EntityPlayer)event.entity;


			if ((this.isDirty) || (player.ticksExisted % 40 == 0)) {
				int max = 0;
				int charged = 0;
				int kinetic = 0;
				int healing = 0;
				int emergency = 0;
				this.isDirty = false;

				for (int a = 0; a < 4; a++) {
					if ((player.inventory.armorItemInSlot(a) != null) && ((player.inventory.armorItemInSlot(a).getItem() instanceof IRunicArmor)))
					{
						int amount = getFinalCharge(player.inventory.armorItemInSlot(a));
						max += amount;
					}
				}

				IInventory baubles = BaublesApi.getBaubles(player);
				for (int a = 0; a < 4; a++) {
					if ((baubles.getStackInSlot(a) != null) && ((baubles.getStackInSlot(a).getItem() instanceof IRunicArmor)))
					{
						int amount = getFinalCharge(baubles.getStackInSlot(a));
						if ((baubles.getStackInSlot(a).getItem() instanceof thaumcraft.common.items.baubles.ItemRingRunic)) {
							switch (baubles.getStackInSlot(a).getItemDamage()) {
								case 2:  charged++; break;
								case 3:  healing++;
							}

						} else if (((baubles.getStackInSlot(a).getItem() instanceof thaumcraft.common.items.baubles.ItemAmuletRunic)) && (baubles.getStackInSlot(a).getItemDamage() == 1))
						{
							emergency++;
						}
						else if (((baubles.getStackInSlot(a).getItem() instanceof thaumcraft.common.items.baubles.ItemGirdleRunic)) && (baubles.getStackInSlot(a).getItemDamage() == 1))
						{
							kinetic++;
						}
						max += amount;
					}
				}

				if (max > 0) {
					this.runicInfo.put(Integer.valueOf(player.getEntityId()), new Integer[] { Integer.valueOf(max), Integer.valueOf(charged), Integer.valueOf(kinetic), Integer.valueOf(healing), Integer.valueOf(emergency) });
					if (this.runicCharge.containsKey(Integer.valueOf(player.getEntityId()))) {
						int charge = ((Integer)this.runicCharge.get(Integer.valueOf(player.getEntityId()))).intValue();
						if (charge > max) {
							this.runicCharge.put(Integer.valueOf(player.getEntityId()), Integer.valueOf(max));
							PacketHandler.INSTANCE.sendTo(new PacketRunicCharge(player, Short.valueOf((short)max), max), (EntityPlayerMP)player);
						}
					}
				} else {
					this.runicInfo.remove(Integer.valueOf(player.getEntityId()));
					this.runicCharge.put(Integer.valueOf(player.getEntityId()), Integer.valueOf(0));
					PacketHandler.INSTANCE.sendTo(new PacketRunicCharge(player, Short.valueOf((short)0), 0), (EntityPlayerMP)player);
				}
			}




			if (this.rechargeDelay > 0) {
				this.rechargeDelay -= 1;
			}
			else if (this.runicInfo.containsKey(Integer.valueOf(player.getEntityId()))) {
				if (!this.lastCharge.containsKey(Integer.valueOf(player.getEntityId()))) {
					this.lastCharge.put(Integer.valueOf(player.getEntityId()), Integer.valueOf(-1));
				}
				if (!this.runicCharge.containsKey(Integer.valueOf(player.getEntityId()))) {
					this.runicCharge.put(Integer.valueOf(player.getEntityId()), Integer.valueOf(0));
				}
				if (!this.nextCycle.containsKey(Integer.valueOf(player.getEntityId()))) {
					this.nextCycle.put(Integer.valueOf(player.getEntityId()), Long.valueOf(0L));
				}

				long time = System.currentTimeMillis();

				int charge = ((Integer)this.runicCharge.get(Integer.valueOf(player.getEntityId()))).intValue();
				if (charge > ((Integer[])this.runicInfo.get(Integer.valueOf(player.getEntityId())))[0].intValue()) {
					charge = ((Integer[])this.runicInfo.get(Integer.valueOf(player.getEntityId())))[0].intValue();
				}
				else if ((charge < ((Integer[])this.runicInfo.get(Integer.valueOf(player.getEntityId())))[0].intValue()) && (((Long)this.nextCycle.get(Integer.valueOf(player.getEntityId()))).longValue() < time) && (thaumcraft.common.items.wands.WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.AIR, Config.shieldCost).add(Aspect.EARTH, Config.shieldCost))))
				{



					long interval = Config.shieldRecharge - ((Integer[])this.runicInfo.get(Integer.valueOf(player.getEntityId())))[1].intValue() * 500;
					this.nextCycle.put(Integer.valueOf(player.getEntityId()), Long.valueOf(time + interval));
					charge++;
					this.runicCharge.put(Integer.valueOf(player.getEntityId()), Integer.valueOf(charge));
				}
				if (((Integer)this.lastCharge.get(Integer.valueOf(player.getEntityId()))).intValue() != charge)
				{
					PacketHandler.INSTANCE.sendTo(new PacketRunicCharge(player, Short.valueOf((short)charge), ((Integer[])this.runicInfo.get(Integer.valueOf(player.getEntityId())))[0].intValue()), (EntityPlayerMP)player);
					this.lastCharge.put(Integer.valueOf(player.getEntityId()), Integer.valueOf(charge));
				}
			}
		}
	}





	@SubscribeEvent
	public void entityHurt(LivingHurtEvent event)
	{
		if ((event.source.getSourceOfDamage() != null) && ((event.source.getSourceOfDamage() instanceof EntityPlayer))) {
			EntityPlayer leecher = (EntityPlayer)event.source.getSourceOfDamage();
			ItemStack helm = leecher.inventory.armorInventory[3];
			if ((helm != null) && ((helm.getItem() instanceof ItemFortressArmor)) &&
					(helm.hasTagCompound()) && (helm.stackTagCompound.hasKey("mask")) && (helm.stackTagCompound.getInteger("mask") == 2) && (leecher.worldObj.rand.nextFloat() < event.ammount / 12.0F))
			{


				leecher.heal(ReferenceTAPI.HEALMULTIPLIER);
			}
		}


		if ((event.entity instanceof EntityPlayer)) {
			long time = System.currentTimeMillis();
			EntityPlayer player = (EntityPlayer)event.entity;


			if ((event.source.getSourceOfDamage() != null) && ((event.source.getSourceOfDamage() instanceof EntityLivingBase))) {
				EntityLivingBase attacker = (EntityLivingBase)event.source.getSourceOfDamage();
				ItemStack helm = player.inventory.armorInventory[3];
				if ((helm != null) && ((helm.getItem() instanceof ItemFortressArmor)) &&
						(helm.hasTagCompound()) && (helm.stackTagCompound.hasKey("mask")) && (helm.stackTagCompound.getInteger("mask") == 1) && (player.worldObj.rand.nextFloat() < event.ammount / 10.0F))
				{
					try
					{
						attacker.addPotionEffect(new PotionEffect(Potion.wither.getId(), 80));
					}
					catch (Exception e) {}
				}
			}

			if ((event.source == DamageSource.drown) || (event.source == DamageSource.wither) || (event.source == DamageSource.outOfWorld) || (event.source == DamageSource.starve))
			{

				return;
			}
			if ((this.runicInfo.containsKey(Integer.valueOf(player.getEntityId()))) && (this.runicCharge.containsKey(Integer.valueOf(player.getEntityId()))) && (((Integer)this.runicCharge.get(Integer.valueOf(player.getEntityId()))).intValue() > 0))
			{

				int target = -1;
				if (event.source.getEntity() != null) target = event.source.getEntity().getEntityId();
				if (event.source == DamageSource.fall) target = -2;
				if (event.source == DamageSource.fallingBlock) { target = -3;
				}
				PacketHandler.INSTANCE.sendToAllAround(new PacketFXShield(event.entity.getEntityId(), target), new cpw.mods.fml.common.network.NetworkRegistry.TargetPoint(event.entity.worldObj.provider.dimensionId, event.entity.posX, event.entity.posY, event.entity.posZ, 64.0D));


//Adjusting values for TFC
				int charge = ((Integer)this.runicCharge.get(Integer.valueOf(player.getEntityId()))).intValue();
				if (charge > event.ammount/ReferenceTAPI.DAMAGEMULTIPLIER) {//TODO add different configuration to allow runic defense to work without my own damage mods in place
					charge = (int)(charge - event.ammount/ReferenceTAPI.DAMAGEMULTIPLIER);
					event.ammount = 0.0F;
				} else {
					event.ammount -= charge * ReferenceTAPI.DAMAGEMULTIPLIER;
					charge = 0;
				}

				String key = player.getEntityId() + ":" + 2;
				if ((charge <= 0) && (((Integer[])this.runicInfo.get(Integer.valueOf(player.getEntityId())))[2].intValue() > 0) && ((!this.upgradeCooldown.containsKey(key)) || (((Long)this.upgradeCooldown.get(key)).longValue() < time)))
				{
					this.upgradeCooldown.put(key, Long.valueOf(time + 20000L));
					player.worldObj.newExplosion(player, player.posX, player.posY + player.height / 2.0F, player.posZ, 1.5F + ((Integer[])this.runicInfo.get(Integer.valueOf(player.getEntityId())))[2].intValue() * 0.5F, false, false);
				}

				key = player.getEntityId() + ":" + 3;
				if ((charge <= 0) && (((Integer[])this.runicInfo.get(Integer.valueOf(player.getEntityId())))[3].intValue() > 0) && ((!this.upgradeCooldown.containsKey(key)) || (((Long)this.upgradeCooldown.get(key)).longValue() < time)))
				{
					this.upgradeCooldown.put(key, Long.valueOf(time + 20000L));
					synchronized (player) {
						try {
							player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 240, ((Integer[])this.runicInfo.get(Integer.valueOf(player.getEntityId())))[3].intValue()));
						} catch (Exception e) {}
					}
					player.worldObj.playSoundAtEntity(player, "thaumcraft:runicShieldEffect", 1.0F, 1.0F);
				}

				key = player.getEntityId() + ":" + 4;
				if ((charge <= 0) && (((Integer[])this.runicInfo.get(Integer.valueOf(player.getEntityId())))[4].intValue() > 0) && ((!this.upgradeCooldown.containsKey(key)) || (((Long)this.upgradeCooldown.get(key)).longValue() < time)))
				{
					this.upgradeCooldown.put(key, Long.valueOf(time + 60000L));
					int t = 8 * ((Integer[])this.runicInfo.get(Integer.valueOf(player.getEntityId())))[4].intValue();
					charge = Math.min(((Integer[])this.runicInfo.get(Integer.valueOf(player.getEntityId())))[0].intValue(), t);
					this.isDirty = true;
					player.worldObj.playSoundAtEntity(player, "thaumcraft:runicShieldCharge", 1.0F, 1.0F);
				}

				if (charge <= 0) { this.rechargeDelay = Config.shieldWait;
				}
				this.runicCharge.put(Integer.valueOf(player.getEntityId()), Integer.valueOf(charge));
				PacketHandler.INSTANCE.sendTo(new PacketRunicCharge(player, Short.valueOf((short)charge), ((Integer[])this.runicInfo.get(Integer.valueOf(player.getEntityId())))[0].intValue()), (EntityPlayerMP)player);
			}


		}
		else if (((event.entity instanceof EntityMob)) && ((((EntityMob)event.entity).getEntityAttribute(EntityUtils.CHAMPION_MOD).getAttributeValue() >= 0.0D) || ((event.entity instanceof IEldritchMob))))
		{

			EntityMob mob = (EntityMob)event.entity;
			int t = (int)((EntityMob)event.entity).getEntityAttribute(EntityUtils.CHAMPION_MOD).getAttributeValue();

			if (((t == 5) || ((event.entity instanceof IEldritchMob))) && (mob.getAbsorptionAmount() > 0.0F)) {
				int target = -1;
				if (event.source.getEntity() != null) {
					target = event.source.getEntity().getEntityId();
				}
				if (event.source == DamageSource.fall) target = -2;
				if (event.source == DamageSource.fallingBlock) target = -3;
				PacketHandler.INSTANCE.sendToAllAround(new PacketFXShield(mob.getEntityId(), target), new cpw.mods.fml.common.network.NetworkRegistry.TargetPoint(event.entity.worldObj.provider.dimensionId, event.entity.posX, event.entity.posY, event.entity.posZ, 32.0D));


				event.entity.worldObj.playSoundEffect(event.entity.posX, event.entity.posY, event.entity.posZ, "thaumcraft:runicShieldEffect", 0.66F, 1.1F + event.entity.worldObj.rand.nextFloat() * 0.1F);

			}
			else if ((t >= 0) && (ChampionModifier.mods[t].type == 2) && (event.source.getSourceOfDamage() != null) && ((event.source.getSourceOfDamage() instanceof EntityLivingBase)))
			{
				EntityLivingBase attacker = (EntityLivingBase)event.source.getSourceOfDamage();
				event.ammount = ChampionModifier.mods[t].effect.performEffect(mob, attacker, event.source, event.ammount);
			}
		}

		if ((event.ammount > 0.0F) && (event.source.getSourceOfDamage() != null) && ((event.entity instanceof EntityLivingBase)) && ((event.source.getSourceOfDamage() instanceof EntityMob)) && (((EntityMob)event.source.getSourceOfDamage()).getEntityAttribute(EntityUtils.CHAMPION_MOD).getAttributeValue() >= 0.0D))
		{

			EntityMob mob = (EntityMob)event.source.getSourceOfDamage();
			int t = (int)mob.getEntityAttribute(EntityUtils.CHAMPION_MOD).getAttributeValue();
			if (ChampionModifier.mods[t].type == 1) {
				event.ammount = ChampionModifier.mods[t].effect.performEffect(mob, (EntityLivingBase)event.entity, event.source, event.ammount);
			}
		}
	}

	@SubscribeEvent
	public void tooltipEvent(ItemTooltipEvent event) {
		int charge = getFinalCharge(event.itemStack);
		if (charge > 0) {
			event.toolTip.add(EnumChatFormatting.GOLD + net.minecraft.util.StatCollector.translateToLocal("item.runic.charge") + " +" + charge);
		}
		int warp = getFinalWarp(event.itemStack, event.entityPlayer);
		if (warp > 0)
			event.toolTip.add(EnumChatFormatting.DARK_PURPLE + net.minecraft.util.StatCollector.translateToLocal("item.warping") + " " + warp);
	}

	public static int getFinalCharge(ItemStack stack) {
		if (!(stack.getItem() instanceof IRunicArmor)) return 0;
		IRunicArmor armor = (IRunicArmor)stack.getItem();
		int base = armor.getRunicCharge(stack);
		if ((stack.hasTagCompound()) && (stack.stackTagCompound.hasKey("RS.HARDEN"))) {
			base += stack.stackTagCompound.getByte("RS.HARDEN");
		}
		return base;
	}

	public static int getFinalWarp(ItemStack stack, EntityPlayer player) {
		if ((stack == null) || (!(stack.getItem() instanceof IWarpingGear))) return 0;
		IWarpingGear armor = (IWarpingGear)stack.getItem();
		return armor.getWarp(stack, player);
	}

	public static int getHardening(ItemStack stack) {
		if (!(stack.getItem() instanceof IRunicArmor)) return 0;
		int base = 0;
		if ((stack.hasTagCompound()) && (stack.stackTagCompound.hasKey("RS.HARDEN"))) {
			base += stack.stackTagCompound.getByte("RS.HARDEN");
		}
		return base;
	}
}



/* Location:           D:\Taeo\Desktop\ThaumaFirmaCraft2\Thaumcraft-1.7.10-4.2.3.5.deobfnew.jar

 * Qualified Name:     thaumcraft.common.lib.events.EventHandlerRunic

 * JD-Core Version:    0.7.0.1

 */