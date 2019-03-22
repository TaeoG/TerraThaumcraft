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

package taeo.terrathaumcraft.handler;

import java.awt.*;
import java.util.*;
import java.util.List;

import baubles.client.gui.GuiEvents;
import com.bioxx.tfc.Entities.Mobs.*;
import com.bioxx.tfc.Handlers.CraftingHandler;
import com.bioxx.tfc.api.TFCBlocks;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.oredict.OreDictionary;
import taeo.terrathaumcraft.entity.boss.*;
import taeo.terrathaumcraft.entity.item.EntityItemTTC;
import taeo.terrathaumcraft.entity.monster.*;
import taeo.terrathaumcraft.events.EntityItemDeathEvent;
import taeo.terrathaumcraft.init.TTCItems;
import taeo.terrathaumcraft.recipes.TTCRecipes;
import taeo.terrathaumcraft.reference.LogToPlank;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.terrathaumcraft.utility.UtilsTTC;
import taeo.terrathaumcraft.worldgen.MagicBiomePlacer;
import taeo.ttfcapi.utility.BlockEx;
import taeo.ttfcapi.utility.LogHelper;
import taeo.ttfcapi.reference.ReferenceTAPI;
import taeo.ttfcapi.utility.UtilsTAPI;
import taeo.ttfcapi.worldgen.BlockCache;
import taeo.ttfcapi.worldgen.WorldWrapper;
import thaumcraft.api.damagesource.DamageSourceThaumcraft;
import thaumcraft.common.entities.golems.EntityGolemBase;

import com.bioxx.tfc.Blocks.BlockCrop;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.SkillStats;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.mojang.authlib.GameProfile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.common.entities.monster.EntityTaintacle;
import thaumcraft.common.items.equipment.ItemElementalAxe;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.utils.Utils;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;
import thaumcraft.common.lib.world.biomes.BiomeGenMagicalForest;

public class EventHandlerTTC {
	
	public static HashMap<String, HashMap<String, HashMap<Integer, ItemStack>>> boundItems;
	public static HashMap<Integer, ItemStack> vanillaItems;
	public static HashMap<Integer, ItemStack> baubleItems;
	public static HashMap<Integer, ItemStack> extraBoundItems;
	public static boolean clientLoadedRecipes = false;
	
	public EventHandlerTTC()
	{
		boundItems = new HashMap();
	}

	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event)
	{
	
	}
	@SubscribeEvent
	public void afterPlayerDeath(LivingDropsEvent event)
	{
	
	}

	//@SubscribeEvent
	public void onChunkPopulation(PopulateChunkEvent.Pre event)
	{
		if(!event.world.isRemote && WorldWrapper.cachedBlocks != null && WorldWrapper.cachedBlocks.containsKey(event.world))
		{
			BlockCache cache = WorldWrapper.cachedBlocks.get(event.world);
			if(cache.chunkHasCache(event.chunkX,event.chunkZ))
			{
				cache.processChunk(event.world, event.chunkX, event.chunkZ);
			}
		}
		/*if(MagicBiomePlacer.isMagicBiome(event.world, event.chunkX, event.chunkZ))
		{
			World wrappedWorld = UtilsTAPI.getWrappedWorld(event.world);
			BiomeGenMagicalForest magicBiome = (BiomeGenMagicalForest) ThaumcraftWorldGenerator.biomeMagicalForest;
			magicBiome.decorate(wrappedWorld, event.world.rand, event.chunkX * 16, event.chunkZ * 16);
		}*/
	}
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onDamaged(LivingHurtEvent event) {
		if (event.source == DamageSourceThaumcraft.taint && event.ammount < ReferenceTAPI.DAMAGEMULTIPLIER) {
			event.ammount = event.ammount * ReferenceTAPI.DAMAGEMULTIPLIER;
		}
	}

    @SubscribeEvent
    public void onLavaSeedInLava(EntityItemDeathEvent event)
    {
        if(event.entityItem.getEntityItem().getItem() == TTCItems.lavaSeed && event.entityItem.getEntityItem().getItemDamage() == 1 && (event.causeOfDeath == DamageSource.inFire || event.causeOfDeath == DamageSource.onFire || event.causeOfDeath == DamageSource.lava))
        {
            boolean inLava = false;
            if(event.causeOfDeath == DamageSource.inFire || event.causeOfDeath == DamageSource.onFire)
            {
                int x = (int) Math.floor(event.x);
                int y = (int) Math.floor(event.y);
                int z = (int) Math.floor(event.z);
                for(int i = -1; i <= 1; i++)
                {
                    Block testBlock = event.entityItem.worldObj.getBlock(x,y+i,z);
                    if(testBlock == TFCBlocks.lava || testBlock == TFCBlocks.lavaStationary || testBlock.getMaterial() == Material.lava)
                    {
                        inLava = true;
                        break;
                    }

                }
            }
            if(inLava || event.causeOfDeath == DamageSource.lava)
            {
                //Explosion  exp = event.entityItem.worldObj.createExplosion(event.entityItem, event.x, event.y+1, event.z, 10.0f, false);
               // exp.doExplosionA();

               // LogHelper.info("lava seed destroyed, spawning entities");
                Random rand = event.entityItem.worldObj.rand;
                World world = event.entityItem.worldObj;
                EntityLiving[] spawns = new EntityLiving[rand.nextInt(5) +5];
                for(int i = 0; i < spawns.length; i ++)
                {
                    if(rand.nextBoolean())
                    {
                        spawns[i] = new EntityBlazeTFC(world);
                    }
                    else
                    {
                        spawns[i] = new EntityMagmaCubeTTC(world);
                    }
                    //spawns[i].posX = event.x + rand.nextInt(1);
                    //spawns[i].posY = event.y+1 + rand.nextInt(1);
                    //spawns[i].posZ = event.z + rand.nextInt(1);
                    //spawns[i].motionY = rand.nextInt(2)+2;
                    spawns[i].setLocationAndAngles(event.x, event.y+1, event.z, 0,0);
                   // spawns[i].motionX = rand.nextInt(1)+2;

                  //  spawns[i].motionZ = rand.nextInt(1)+2;
                }
                for(EntityLiving entity : spawns)
                {
                    if(!world.isRemote)
                    {
                        world.spawnEntityInWorld(entity);

                    }
                }



                //LogHelper.info("lava seed died to lava");
            }
            //LogHelper.info("lava seed died of fire or lava damage away from lava");
        }
        //LogHelper.info("lava seed (probably) died to something random: " + event.causeOfDeath.getDamageType());
    }
    @SubscribeEvent
    public void onLavaSeedDrop(ItemTossEvent event)
    {
        if(event.entityItem.getEntityItem().getItem() == TTCItems.lavaSeed)
        {
            EntityItemTTC newEntity = EntityItemTTC.convert(event.entityItem);
            event.setCanceled(true);
            event.player.worldObj.spawnEntityInWorld(newEntity);
          //  LogHelper.info("replacing lava seed entity");
        }

    }

    //@SubscribeEvent
    public void onNetherwartPlanted(PlayerInteractEvent event)
    {
        try{
            if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && event.entityPlayer.getCurrentEquippedItem().getItem() == Items.nether_wart && event.world.getBlock(event.x,event.y,event.z) == Blocks.soul_sand)
            {
                event.setCanceled(true);
            }
        }
        catch(NullPointerException e)
        {
            //players hand is empty, do nothing.
        }

    }


	@SubscribeEvent
	public void onCrafting(PlayerEvent.ItemCraftedEvent e)
	{
		EntityPlayer player = e.player;
		ItemStack itemstack = e.crafting;
		Item item = itemstack.getItem();
		int itemDamage = itemstack.getItemDamage();
		IInventory iinventory = e.craftMatrix;

		if(iinventory != null)
		{
			if(item == TTCItems.roughShard)
			{
				List<ItemStack> hammers = OreDictionary.getOres("itemHammer", false);
				//List<ItemStack> saws = OreDictionary.getOres("itemSaw", false);
				CraftingHandler.handleItem(player, iinventory, hammers);
				//CraftingHandler.handleItem(player, iinventory, saws);
			}
		}
	}
	//@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void thaumcraftEntitySpawned(LivingSpawnEvent event)
	{
		World world = event.world;
		double x = event.x;
		double y = event.y;
		double z = event.z;
		if(!world.isRemote && isThaumcraftMob(event.entityLiving))
		{
			//LogHelper.info("Thaumcraft Entity Spawning at " + x + " " + y + " " + z);
			EntityLiving entity = convertEntity((EntityLiving) event.entityLiving);
			entity.setPosition(x,y+1,z);
			//LivingSpawnEvent outEvent = new LivingSpawnEvent(entity, event.world, event.x, event.y, event.z);
			//MinecraftForge.EVENT_BUS.post(outEvent);
			world.spawnEntityInWorld(entity);
			event.setResult(Event.Result.DENY);
		}



	}
	public boolean isVanillaMob(EntityLivingBase mob)
	{
      		switch(mob.getClass().getName()){
			case "net.minecraft.entity.monster.EntityZombie":
			case "net.minecraft.entity.monster.EntityBlaze":
			case "net.minecraft.entity.monster.EntityCaveSpider":
			case "net.minecraft.entity.monster.EntityCreeper":
			case "net.minecraft.entity.monster.EntityEnderman":
			case "net.minecraft.entity.monster.EntitySkeleton":
			case "net.minecraft.entity.monster.EntitySpider":
				return true;
			default:
				return false;
		}
	}
	public boolean isThaumcraftMob(EntityLivingBase mob)
	{
		switch(mob.getClass().getName())
		{
			case "thaumcraft.common.entities.monster.EntityBrainyZombie":
			case "thaumcraft.common.entities.monster.EntityCultist":
			case "thaumcraft.common.entities.monster.EntityCultistCleric":
			case "thaumcraft.common.entities.monster.EntityCultistKnight":
			case "thaumcraft.common.entities.monster.EntityEldritchCrab":
			case "thaumcraft.common.entities.monster.EntityEldritchGuardian":
			case "thaumcraft.common.entities.monster.EntityFireBat":
			case "thaumcraft.common.entities.monster.EntityGiantBrainyZombie":
			case "thaumcraft.common.entities.monster.EntityInhabitedZombie":
			case "thaumcraft.common.entities.monster.EntityMindSpider":
			case "thaumcraft.common.entities.monster.EntityPech":
			case "thaumcraft.common.entities.monster.EntityTaintacle":
			case "thaumcraft.common.entities.monster.EntityTaintacleSmall":
			case "thaumcraft.common.entities.monster.EntityTaintChicken":
			case "thaumcraft.common.entities.monster.EntityTaintCow":
			case "thaumcraft.common.entities.monster.EntityTaintCreeper":
			case "thaumcraft.common.entities.monster.EntityTaintPig":
			case "thaumcraft.common.entities.monster.EntityTaintSheep":
			case "thaumcraft.common.entities.monster.EntityTaintSpider":
			case "thaumcraft.common.entities.monster.EntityTaintSpore":
			case "thaumcraft.common.entities.monster.EntityTaintSporeSwarmer":
			case "thaumcraft.common.entities.monster.EntityTaintSwarm":
			case "thaumcraft.common.entities.monster.EntityTaintVillager":
			case "thaumcraft.common.entities.monster.EntityThaumicSlime":
			case "thaumcraft.common.entities.monster.EntityWatcher":
			case "thaumcraft.common.entities.monster.EntityWisp":
			case "thaumcraft.common.entities.monster.boss.EntityCultistLeader":
			case "thaumcraft.common.entities.monster.boss.EntityCultistPortal":
			case "thaumcraft.common.entities.monster.boss.EntityEldritchGolem":
			case "thaumcraft.common.entities.monster.boss.EntityEldritchWarden":
			case "thaumcraft.common.entities.monster.boss.EntityTaintacleGiant":
			case "thaumcraft.common.entities.monster.boss.EntityThaumcraftBoss":
				//LogHelper.info(ReferenceTTC.MOD_NAME,"Thaumcraft Monster Confirmed");
				return true;
			default:
				return false;
		}
	}

	public EntityLiving convertEntity(EntityLiving inEntity)
	{
		EntityLiving outEntity = inEntity;
		double x = inEntity.posX;
		double y = inEntity.posY;
		double z = inEntity.posZ;
		World world = inEntity.worldObj;
		switch(inEntity.getClass().getName())
		{
			case "thaumcraft.common.entities.monster.EntityBrainyZombie":
				outEntity = new EntityBrainyZombieTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityCultist":
				outEntity = new EntityCultistTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityCultistCleric":
				outEntity = new EntityCultistClericTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityCultistKnight":
				outEntity = new EntityCultistKnightTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityEldritchCrab":
				outEntity = new EntityEldritchCrabTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityEldritchGuardian":
				outEntity = new EntityEldritchGuardianTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityFireBat":
				outEntity = new EntityFireBatTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityGiantBrainyZombie":
				outEntity = new EntityGiantBrainyZombieTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityInhabitedZombie":
				outEntity = new EntityInhabitedZombieTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityMindSpider":
				outEntity = new EntityMindSpiderTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityPech":
				outEntity = new EntityPechTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityTaintacle":
				outEntity = new EntityTaintacle(world);
				break;
			case "thaumcraft.common.entities.monster.EntityTaintacleSmall":
				outEntity = new EntityTaintacleSmallTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityTaintChicken":
				outEntity = new EntityTaintChickenTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityTaintCow":
				outEntity = new EntityTaintCowTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityTaintCreeper":
				outEntity = new EntityTaintCreeperTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityTaintPig":
				outEntity = new EntityTaintPigTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityTaintSheep":
				outEntity = new EntityTaintSheepTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityTaintSpider":
				outEntity = new EntityTaintSpiderTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityTaintSpore":
				outEntity = new EntityTaintSporeTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityTaintSporeSwarmer":
				outEntity = new EntityTaintSporeSwarmerTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityTaintSwarm":
				outEntity = new EntityTaintSwarmTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityTaintVillager":
				outEntity = new EntityTaintVillagerTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityThaumicSlime":
				outEntity = new EntityThaumicSlimeTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityWatcher":
				outEntity = new EntityWatcherTTC(world);
				break;
			case "thaumcraft.common.entities.monster.EntityWisp":
				outEntity = new EntityWispTTC(world);
				break;
			case "thaumcraft.common.entities.monster.boss.EntityCultistLeader":
				outEntity = new EntityCultistLeaderTTC(world);
				break;
			case "thaumcraft.common.entities.monster.boss.EntityCultistPortal":
				outEntity = new EntityCultistPortalTTC(world);
				break;
			case "thaumcraft.common.entities.monster.boss.EntityEldritchGolem":
				outEntity = new EntityEldritchGolemTTC(world);
				break;
			case "thaumcraft.common.entities.monster.boss.EntityEldritchWarden":
				outEntity = new EntityEldritchWardenTTC(world);
				break;
			case "thaumcraft.common.entities.monster.boss.EntityTaintacleGiant":
				outEntity = new EntityTaintacleGiantTTC(world);
				break;
			case "thaumcraft.common.entities.monster.boss.EntityThaumcraftBoss":
				outEntity = new EntityBrainyZombieTTC(world);
				break;
			case "net.minecraft.entity.monster.EntityZombie":
				outEntity = new EntityZombieTFC(world);
				break;
			case "net.minecraft.entity.monster.EntityBlaze":
				outEntity = new EntityBlazeTFC(world);
				break;
			case "net.minecraft.entity.monster.EntityCaveSpider":
				outEntity = new EntityCaveSpiderTFC(world);
				break;
			case "net.minecraft.entity.monster.EntityCreeper":
				outEntity = new EntityCreeperTFC(world);
				break;
			case "net.minecraft.entity.monster.EntityEnderman":
				outEntity = new EntityEndermanTFC(world);
				break;
			case "net.minecraft.entity.monster.EntitySkeleton":
				outEntity = new EntitySkeletonTFC(world);
				break;
			case "net.minecraft.entity.monster.EntitySpider":
                outEntity = new EntitySpiderTFC(world);
                break;
			default:
				outEntity = new EntityChicken(world);
				break;
		}
		outEntity.copyDataFrom(inEntity, true);
		outEntity.copyLocationAndAnglesFrom(inEntity);
		//outEntity.setPosition(x,y,z);
		return outEntity;
	}
	
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void onLoadWorld(WorldEvent.Load event)
	{
		//absolutely no idea why this has to be here and not in RecipesTTC, but if it's not it crashes.
		AnvilManager anvilMan = AnvilManager.getInstance();
		if(event.world.provider.dimensionId == 0 && !event.world.isRemote) //&& AnvilManager.getInstance().getRecipeList().size() == 0
		{
			LogHelper.info(ReferenceTTC.MOD_NAME,"****  Registering TTC Recipes ServerSide **** ");
			TTCRecipes.registerLateRecipes();
		}
		
	}
	//@SubscribeEvent
	public void onCropHarvest(BlockEvent.BreakEvent event)
	{
		if (event.block instanceof BlockCrop)
		{
			LogHelper.info(ReferenceTTC.MOD_NAME, "Crop broken by " + event.getPlayer().getDisplayName());
		}
	}
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event)
	{
		if(event.getPlayer() != null && event.getPlayer().getCurrentEquippedItem() != null)
		{
			if(event.getPlayer().getCurrentEquippedItem().getItem() instanceof ItemElementalAxe) {
				if (Utils.isWoodLog(event.world, event.x, event.y, event.z)) {//check for natural logs instead
					if (!event.getPlayer().isSneaking()) {
						event.setCanceled(true);
						if(!event.world.isRemote)
						{
							PacketHandler.INSTANCE.sendToAllAround(new thaumcraft.common.lib.network.fx.PacketFXBlockBubble(event.x, event.y, event.z, new Color(0.33F, 0.33F, 1.0F).getRGB()), new cpw.mods.fml.common.network.NetworkRegistry.TargetPoint(event.world.provider.dimensionId, event.x, event.y, event.z, 32.0D));
							event.world.playSoundEffect(event.x, event.y, event.z, "thaumcraft:bubble", 0.15F, 1.0F);
							ItemStack planks = LogToPlank.getInstance().getPlanks(new BlockEx(event.world.getBlock(event.x, event.y, event.z), event.world.getBlockMetadata(event.x,event.y,event.z)));
							Item plankItem;
							int plankMeta;
							int plankAmount;
							int maxPlanksPerStack;
							if(planks == null)
							{
								plankItem = Items.stick;
								plankMeta = 0;
								plankAmount = 1;
								maxPlanksPerStack =  64;
							}
							else
							{
								plankItem = planks.getItem();
								plankMeta = planks.getItemDamage();
								plankAmount = planks.stackSize;
								maxPlanksPerStack = planks.getMaxStackSize();
							}


							ItemStack currentEquipped = event.getPlayer().getCurrentEquippedItem();
							int remainingUses = currentEquipped.getMaxDamage() - currentEquipped.getItemDamage();
							int numLogs = UtilsTTC.processTree(event.world, event.x, event.y, event.z, 512, currentEquipped.getMaxDamage() - currentEquipped.getItemDamage());

							event.getPlayer().getCurrentEquippedItem().damageItem(numLogs, event.getPlayer());
							if(numLogs >= remainingUses)
							{
								event.getPlayer().destroyCurrentEquippedItem();
							}

							int numPlanks = numLogs * plankAmount;
							while(numPlanks >0)
							{
								if(numPlanks > maxPlanksPerStack)
								{
									EntityItem item = new EntityItem(event.world, event.x + 0.5, event.y + 0.5, event.z + 0.5, new ItemStack(plankItem, maxPlanksPerStack, plankMeta));
									event.world.spawnEntityInWorld(item);
									numPlanks -= maxPlanksPerStack;
								}
								else
								{
									EntityItem item = new EntityItem(event.world, event.x + 0.5, event.y + 0.5, event.z + 0.5, new ItemStack(plankItem, numPlanks, plankMeta));
									event.world.spawnEntityInWorld(item);
									numPlanks = 0;
								}

							}

						}

					}
				}
			}
		}

	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public void replaceMeat(LivingDropsEvent event)
	{
		/*ArrayList<EntityItem> droplist = new ArrayList<EntityItem>();
		if (event.entity instanceof EntityZombie)
		{
			for(EntityItem ei : event.drops)
			{
				if(ei.getEntityItem().getItem() == Items.rotten_flesh)
				{
					ItemStack is = ItemFoodTFC.createTag(new ItemStack(TTCItems.rottenMeatRaw, 1), 80.0f);
					droplist.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, is));
				}
				else
				{
					droplist.add(ei);
				}
			}
			event.drops.clear();
			event.drops.addAll(droplist);
		}*/
		
		if(event.entity instanceof IAnimal)
		{
			if(event.source.getSourceOfDamage() instanceof EntityGolemBase)
			{
				FakePlayer fp = FakePlayerFactory.get((WorldServer)event.entity.worldObj, new GameProfile((UUID)null, "FakeThaumcraftGolem"));
				SkillStats skills = new SkillStats(fp);
				skills.setSkill(Global.SKILL_BUTCHERING, 1);
				TFC_Core.setSkillStats(fp, skills);
				ArrayList<EntityItem> eventdrops = new ArrayList<EntityItem>();
				eventdrops.addAll(event.drops);
				LivingDropsEvent eventModded = new LivingDropsEvent(event.entityLiving, new EntityDamageSource("player", fp), eventdrops, event.lootingLevel, true, 100);
				if(!MinecraftForge.EVENT_BUS.post(eventModded))
				{
					for (EntityItem item : eventModded.drops)
					{
						eventModded.entity.entityDropItem(item.getEntityItem(), 1);
					}  
					eventModded.source.getSourceOfDamage().setDead();
				}
				//event.drops.clear();
				event.setCanceled(true);
			}
			
			/*//LogHelper.info(event.source.getSourceOfDamage());
			if(event.source.getSourceOfDamage() instanceof EntityGolemBase)
			{
				EntityGolemBase golem = (EntityGolemBase) event.source.getSourceOfDamage();
				int golemStrength = golem.golemType.strength + golem.getUpgradeAmount(1);
				ArrayList<EntityItem> drop = new ArrayList<EntityItem>();
				for(EntityItem ei : event.drops)
				{
					if(ei.getEntityItem().getItem() instanceof IFood)
					{
						float oldWeight = Food.getWeight(ei.getEntityItem());
						Food.setWeight(ei.getEntityItem(), 0);
						float newWeight = oldWeight * (event.entity.worldObj.rand.nextFloat() * ((float)golemStrength * 0.08f )) ;
						while (newWeight >= Global.FOOD_MIN_DROP_WEIGHT)
						{
							float fw = Helper.roundNumber(Math.min(Global.FOOD_MAX_WEIGHT, newWeight), 10);
							if (fw < Global.FOOD_MAX_WEIGHT)
								newWeight = 0;
							newWeight -= fw;
							ItemStack is = ItemFoodTFC.createTag(new ItemStack(ei.getEntityItem().getItem(), 1), fw);
							drop.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, is));
						}
					}
					else
					{
						drop.add(ei);
					}
				}
				event.setCanceled(true);
				for(EntityItem ei : drop)
				{
					event.entity.worldObj.spawnEntityInWorld(ei);
				}
			}*/
		}
	}


}
