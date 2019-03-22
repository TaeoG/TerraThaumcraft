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

package taeo.terrathaumcraft;


import com.bioxx.tfc.TerraFirmaCraft;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import taeo.terrathaumcraft.commands.CoreSampleCommand;
import taeo.terrathaumcraft.commands.GetClimateTagsCommand;
import taeo.terrathaumcraft.commands.IsDesertCommand;
import taeo.terrathaumcraft.commands.FindInfusedCommand;
import taeo.terrathaumcraft.fluid.TTCFluids;
import taeo.terrathaumcraft.handler.ConfigurationHandler;
import taeo.terrathaumcraft.handler.EventHandlerTTC;
import taeo.terrathaumcraft.packets.InitClientWorldPacketTTC;
import taeo.ttfcapi.TTFCAPI;
import taeo.terrathaumcraft.init.*;
import taeo.terrathaumcraft.item.ItemSetup;
import taeo.terrathaumcraft.item.WandManagerTTC;
import taeo.ttfcapi.api.arrows.CustomAmmoRegistry;
import taeo.ttfcapi.utility.BlockConverter;
import taeo.ttfcapi.proxy.IProxy;
import taeo.terrathaumcraft.recipes.TTCRecipes;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.utility.LogHelper;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;

import taeo.ttfcapi.api.Woods;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.projectile.EntityPrimalArrow;

@Mod(modid = ReferenceTTC.MOD_ID, name = ReferenceTTC.MOD_NAME, /*version = ReferenceTTC.VERSION,*/ dependencies = "required-after:terrafirmacraft;required-after: Thaumcraft; required-after: ttfcapi")
public class TerraThaumcraftMod {


    
    @Mod.Instance("taeo/terrathaumcraft")
    public static TerraThaumcraftMod instance;
    
    @SidedProxy(clientSide= ReferenceTTC.CLIENT_PROXY, serverSide= ReferenceTTC.SERVER_PROXY)
    public static IProxy proxy;
    public static WandManagerTTC wandManager = new WandManagerTTC();
    public static SimpleNetworkWrapper chan;
   // public static BlockConverter blockConverter = new BlockConverter();

	boolean addedWood = false;
	@EventHandler
	public void prepreinit(FMLConstructionEvent event)
	{
		if(!addedWood)
		{
			ReferenceTTC.WOODTYPESTARTINDEX = Woods.addWood(ReferenceTTC.WOOD_EXTRA);
			addedWood = true;
		}
	}

    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
		instance = this;
		Woods.addWoodToDoors(ReferenceTTC.WOOD_EXTRA, ReferenceTTC.WOODTYPESTARTINDEX);

    	ConfigurationHandler.init(event.getSuggestedConfigurationFile());
    	FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
    	
    	if(Loader.isModLoaded("Baubles"))
    	{
    	}
    	else
    	{
    		LogHelper.info(ReferenceTTC.MOD_NAME,"Baubles not found");
    	}
		if(!Loader.isModLoaded("terrafirmacraft"))
		{
			LogHelper.info(ReferenceTTC.MOD_NAME,"terrafirmacraft isn't loaded yet");
		}
    	ReferenceTTC.smartMovingInstalled = Loader.isModLoaded("SmartMoving");
    	//proxy.registerKeyBinding();
 

    	TTCItems.init();

    	TTCBlocks.init();


		TTCFluids.registerFluids();

    	proxy.initTileEntities();
    	proxy.initGuiHandler();
    	TTCEntities.init();
		proxy.initWorldGen();
		proxy.initOres();


		BlockConverter.getInstance().addConversion(ConfigBlocks.blockMagicalLeaves, TTCBlocks.blockMagicLeaves);
		BlockConverter.getInstance().addConversion(ConfigBlocks.blockMagicalLog, TTCBlocks.blockMagicTrunk);
    	//chan = NetworkRegistry.INSTANCE.newSimpleChannel("terrathaumcraft");
    }

    
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		
		//System.out.println("Testing for null");
        ModContainer TFC = FMLCommonHandler.instance().findContainerFor(TerraFirmaCraft.instance);
        ModContainer TTC = FMLCommonHandler.instance().findContainerFor(instance);
        //System.out.println("TerraFirmaCraft is " + TFC + " and my mod instance is " + TTC);

        //FMLCommonHandler.instance().bus().register(new PlayerTrackerTAPI());
		//TerraFirmaCraft.PACKET_PIPELINE.registerPacket(InitClientWorldPacketTAPI.class);
		TTFCAPI.addRecipePacket(new InitClientWorldPacketTTC());



	}
	@EventHandler//( = EventPriority.LOWEST)
	public void postinit(FMLPostInitializationEvent event)
	{
        TTCAspects.registerEntities();
        TTCAspects.registerItems();
        TTCAspects.registerBlocks();
		//PatchRecipes.patchWandTriggers();
		EventHandlerTTC evHandler = new EventHandlerTTC();
		MinecraftForge.EVENT_BUS.register(evHandler);
		FMLCommonHandler.instance().bus().register(evHandler);
		ItemSetup.addToolstoTFCRecipeArray();
		TTCRecipes.registerEarlyRecipes();
		proxy.initOres();
        proxy.initWorldGen();
		proxy.initRenderers();
		proxy.initEntities();
        proxy.initToolClasses();
        proxy.initPotions();

		CustomAmmoRegistry.addAmmo(new ItemStack(ConfigItems.itemPrimalArrow,1,OreDictionary.WILDCARD_VALUE), CustomAmmoRegistry.ARROW, EntityPrimalArrow.class);
		CustomAmmoRegistry.addRangedWeapon(new ItemStack(ConfigItems.itemBowBone,1, OreDictionary.WILDCARD_VALUE));

		/*int[] eerieColor = ColorTools.convertIntToRGB(ThaumcraftWorldGenerator.biomeEerie.getBiomeFoliageColor(0,0,0));
		int[] magicColor = ColorTools.convertIntToRGB(ThaumcraftWorldGenerator.biomeMagicalForest.getBiomeFoliageColor(0,0,0));
		int[] taintColor = ColorTools.convertIntToRGB(ThaumcraftWorldGenerator.biomeTaint.getBiomeFoliageColor(0,0,0));
		LogHelper.info("Eerie biome colour is {" + eerieColor[0] +"," + eerieColor[1] + "," + eerieColor[2]+"}");
		LogHelper.info("Magic biome colour is {" + magicColor[0] +"," + magicColor[1] + "," + magicColor[2]+"}");
		LogHelper.info("Taint biome colour is {" + taintColor[0] +"," + taintColor[1] + "," + taintColor[2]+"}");*/
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		// register server commands

		//event.registerServerCommand(new CoreSampleCommand());
		//event.registerServerCommand(new IsDesertCommand());
		//event.registerServerCommand(new FindInfusedCommand());
		event.registerServerCommand(new GetClimateTagsCommand());
	}

}

