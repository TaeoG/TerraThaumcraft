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

package taeo.terrathaumcraft.recipes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bioxx.tfc.api.*;
import com.bioxx.tfc.api.Crafting.*;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import taeo.terrathaumcraft.TerraThaumcraftMod;
import taeo.terrathaumcraft.crafting.MagicAnvilRecipe;
import taeo.terrathaumcraft.fluid.TTCFluids;
import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.terrathaumcraft.init.TTCItems;
import taeo.terrathaumcraft.reference.ReferenceTTC;

import taeo.ttfcapi.reference.ReferenceTAPI;
import taeo.ttfcapi.utility.ItemStackComparator;
import taeo.ttfcmat.init.TMatItems;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategoryList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.wands.WandTriggerRegistry;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.config.ConfigResearch;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.RuleEnum;

import cpw.mods.fml.common.registry.GameRegistry;

public class TTCRecipes {
 
	public static boolean clientRegistered = false;

	public static AnvilRecipe ironWandCaps; 
	public static AnvilRecipe thaumiumAxeHead;
	public static AnvilRecipe thaumiumChiselHead;
	public static AnvilRecipe thaumiumHammerHead;
	public static AnvilRecipe thaumiumHoeHead;
	public static AnvilRecipe thaumiumJavelinHead;
	public static AnvilRecipe thaumiumKnifeHead;
	public static AnvilRecipe thaumiumMaceHead;
	public static AnvilRecipe thaumiumPickHead;
	public static AnvilRecipe thaumiumProPickHead;
	public static AnvilRecipe thaumiumSawHead;
	public static AnvilRecipe thaumiumScytheHead;
	public static AnvilRecipe thaumiumShovelHead;
	public static AnvilRecipe thaumiumSwordHead;
	public static AnvilRecipe weldThaumiumIngots;
	public static AnvilRecipe weldThaumiumSheets;
	public static AnvilRecipe thaumiumSheet;
	public static AnvilRecipe weldThaumiumUnfinishedGreaves;
	public static AnvilRecipe weldThaumiumUnfinishedChest;
	public static AnvilRecipe weldThaumiumUnfinishedBoots;
	public static AnvilRecipe weldThaumiumUnfinishedHelm;
	public static AnvilRecipe thaumiumUnfinishedGreaves;
	public static AnvilRecipe thaumiumUnfinishedChest;
	public static AnvilRecipe thaumiumUnfinishedBoots;
	public static AnvilRecipe thaumiumUnfinishedHelm;
	public static AnvilRecipe thaumiumGreaves;
	public static AnvilRecipe thaumiumChest;
	public static AnvilRecipe thaumiumBoots;
	public static AnvilRecipe thaumiumHelm;
	public static AnvilRecipe copperWandCaps;
	public static AnvilRecipe silverWandCaps;
	public static AnvilRecipe goldWandCaps;
	public static AnvilRecipe thaumiumWandCaps;

    public static CrucibleRecipe thaumiumIngotRecipe;
    public static CrucibleRecipe thaumiumHammerHeadRecipe;

    public static IRecipe thaumcraftTableRecipe;

	public static String wandcapPlanName = "wandcap";
    public static final String researchNetherManu = "NETHERMANUFACTURE";


    public static ShapedArcaneRecipe arcaneStone;
    public static ShapedArcaneRecipe enchantedFabric;
    public static InfusionRecipe bootsTraveller;
    public static ShapedArcaneRecipe wardedJar;
    public static IRecipe thaumometer;
    public static ShapedArcaneRecipe nodeStabilizer;
    public static CrucibleRecipe alumentum;
    public static CrucibleRecipe bitumAlumentum;

	public static void removeRecipe(Item output)
	{
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		Iterator<IRecipe> creeper = recipes.iterator();
		while (creeper.hasNext())
		{
			ItemStack result = creeper.next().getRecipeOutput();
			if(result != null && result.getItem() == output)
			{
				creeper.remove();
			}
		}
	}
	public static void removeRecipe(ItemStack stack)
	{
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		Iterator<IRecipe> creeper = recipes.iterator();
		while (creeper.hasNext())
		{
			ItemStack result = creeper.next().getRecipeOutput();
			if(result != null && result.getItem() == stack.getItem() && result.getItemDamage() == stack.getItemDamage() && result.stackSize == stack.stackSize)
			{
				creeper.remove();
			}
		}
	}


	public static boolean removeCrucibleRecipe(String key) throws Exception
	{
		Field recipeList;
		ArrayList theList;
		boolean foundRecipe = false;

		recipeList = ThaumcraftApi.class.getDeclaredField("craftingRecipes");
		recipeList.setAccessible(true);
		theList = (ArrayList) recipeList.get(null);
		if(theList != null)
		{
			Iterator listIter = theList.listIterator();
			while(listIter.hasNext())
			{
				Object listItem = listIter.next();
				if(listItem instanceof CrucibleRecipe)
				{
					CrucibleRecipe recipe = (CrucibleRecipe)listItem;
					if(recipe.key.equals(key))
					{
						listIter.remove();
						foundRecipe = true;
						break;
					}
				}
			}
			recipeList.set(null, theList);
		}
		return foundRecipe;

	}

	public static boolean removeArcaneWorktableRecipe(String key)
	{
		IArcaneRecipe recipe = (IArcaneRecipe) ConfigResearch.recipes.get(key);

		Field craftedRecipeField = null;
		ArrayList<IArcaneRecipe> craftedRecipes = null;
		try {
			craftedRecipeField = ThaumcraftApi.class.getDeclaredField("craftingRecipes");
			craftedRecipeField.setAccessible(true);
			craftedRecipes = (ArrayList) craftedRecipeField.get(null);

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		if(craftedRecipes == null)
		{
			return false;
		}

		ArrayList<IArcaneRecipe> removalList = new ArrayList<>();
		for(int i = 0; i < craftedRecipes.size(); i++)//IArcaneRecipe storedRecipe: craftedRecipes)
		{
			if(craftedRecipes.get(i) instanceof IArcaneRecipe)
			{
				if(recipe.getRecipeOutput() == craftedRecipes.get(i).getRecipeOutput())
				{
					removalList.add(craftedRecipes.get(i));
				}
			}

		}

		craftedRecipes.removeAll(removalList);

		ConfigResearch.recipes.remove(key);
		return true;

	}

	public static void replaceRecipeInResearch(String researchKey, ResearchPage alteredPage)
	{
		ResearchItem resItem = ResearchCategories.getResearch(researchKey);
		ResearchPage[] pages = resItem.getPages();

		if(alteredPage.recipeOutput == null)
		{
			return;
		}

		for(int i = 0; i < pages.length; i++)//ResearchPage page : pages)
		{
			if(pages[i].type == alteredPage.type)
			{
				ItemStackComparator comp = new ItemStackComparator();
				if(comp.compare(pages[i].recipeOutput, alteredPage.recipeOutput) == 0)
				{
					//LogHelper.info(ReferenceTTC.MOD_NAME, "recipe outputs match");
					pages[i] = alteredPage;
				}
			}
		}

	}

	public static void replaceResearch(ResearchItem alteredResearch)
	{
		ResearchCategoryList list = ResearchCategories.getResearchList(alteredResearch.category);
		list.research.remove(alteredResearch.key);
		ResearchCategories.researchCategories.put(alteredResearch.category, list);
		alteredResearch.registerResearchItem();
	//.registerResearchItem()	
	}
	
	public static void updateResearch(String key, String category)
	{
		ResearchCategoryList list = ResearchCategories.getResearchList(category);
		ResearchItem updatedResearch = list.research.get(key);
		list.research.remove(key);
		ResearchCategories.researchCategories.put(category, list);
		updatedResearch.registerResearchItem();
	}
    public static void registerCrucibleRecipe(CrucibleRecipe recipe)
    {
        ThaumcraftApi.addCrucibleRecipe(recipe.key, recipe.getRecipeOutput(), recipe.catalyst, recipe.aspects);
    }

    public static void registerEarlyRecipes()
	{





		thaumiumIngotRecipe = new CrucibleRecipe("THAUMIUM", new ItemStack(TTCItems.thaumiumIngot), new ItemStack(TFCItems.wroughtIronIngot), new AspectList().merge(Aspect.MAGIC, 4));
        registerCrucibleRecipe(thaumiumIngotRecipe);
        thaumiumHammerHeadRecipe = new CrucibleRecipe("THAUMIUM", new ItemStack(TTCItems.thaumiumToolHead,1, ReferenceTAPI.getToolHeadID("hammer")), new ItemStack(TFCItems.wroughtIronHammerHead), new AspectList().merge(Aspect.MAGIC, 4));
        registerCrucibleRecipe(thaumiumHammerHeadRecipe);
        //BarrelManager barrelMan = BarrelManager.getInstance();
		//Create Saltwater from fresh water and Salt
		//barrelMan.addRecipe(new BarrelRecipe(new ItemStack(TFCItems.Powder, 1, 9), new FluidStack(TFCFluids.FRESHWATER, 250), null, new FluidStack(TFCFluids.SALTWATER, 250), 0).setMinTechLevel(0).setSealedRecipe(false).setRemovesLiquid(false).setAllowAnyStack(false));
		//ThaumcraftApi.addCrucibleRecipe("THAUMIUM", new ItemStack(TTCItems.thaumiumIngot), new ItemStack(TFCItems.wroughtIronIngot), new AspectList().merge(Aspect.MAGIC, 4));
		//ThaumcraftApi.addCrucibleRecipe("THAUMIUM", new ItemStack(TTCItems.thaumiumToolHead,1, ReferenceTTC.getToolHeadID("hammer")), new ItemStack(TFCItems.wroughtIronHammerHead), new AspectList().merge(Aspect.MAGIC, 4));
		//Thaumcraft Table
        thaumcraftTableRecipe = new ShapedOreRecipe(ConfigBlocks.blockTable, new Object[]{"LLL","LLL","P P",Character.valueOf('L'), "woodLumber", Character.valueOf('P') , "plankWood"});
        GameRegistry.addRecipe(thaumcraftTableRecipe);
		//GameRegistry.addRecipe(new ShapedOreRecipe(ConfigBlocks.blockTable, new Object[]{"LLL","LLL","P P",Character.valueOf('L'), "woodLumber", Character.valueOf('P') , "plankWood"}));
		//Scribing tools with TFC glass bottles and ink

		GameRegistry.addShapelessRecipe(new ItemStack(ConfigItems.itemInkwell), new Object[]{ TFCItems.glassBottle, Items.feather, new ItemStack(TFCItems.dye, 1, 0)});

		//Books but no bookshelf? Really?
		//GameRegistry.addRecipe(new ShapedOreRecipe(TFCBlocks.bookshelf, new Object[]{"PPP","BBB","PPP", 'P', "plankWood", 'B', Items.book} ));
		//Thaumometer recipe doesn't use the oreDic, strange.

		//GameRegistry.addRecipe(new ShapedOreRecipe(ConfigItems.itemThaumometer, " S ", "IGI", " S ", 'S', new ItemStack(ConfigItems.itemShard, 1, OreDictionary.WILDCARD_VALUE), 'I', TFCItems.GoldSheet, 'G', "blockGlassColorless" ));

		//ResearchItem newThaumo = new ResearchItem("THAUMOMETER", "ARTIFICE", new AspectList(), 2, 1, 0, new ItemStack(ConfigItems.itemThaumometer)).setStub().setAutoUnlock().setRound().setPages(new ResearchPage[] { new ResearchPage("tc.research_page.THAUMOMETER.1"), new ResearchPage((IRecipe)ConfigResearch.recipes.get("Thaumometer")) });
		//replaceResearch(newThaumo);
		//updateResearch("THAUMOMETER", "ARTIFICE");
		//replaceResearch(new ResearchItem("RESEARCH", "BASICS", new AspectList(), 2, 0, 0, new ItemStack(ConfigItems.itemInkwell)).setPages(new ResearchPage[]{new ResearchPage("tc.research_page.RESEARCH.1"), new ResearchPage("tc.research_page.RESEARCH.2"), new ResearchPage((IRecipe) ConfigResearch.recipes.get("Thaumometer")), new ResearchPage("tc.research_page.RESEARCH.3"), new ResearchPage("tc.research_page.RESEARCH.4"), new ResearchPage((IRecipe) ConfigResearch.recipes.get("Scribe1")), new ResearchPage((IRecipe) ConfigResearch.recipes.get("Scribe2")), new ResearchPage((IRecipe) ConfigResearch.recipes.get("Scribe3")), new ResearchPage("tc.research_page.RESEARCH.5"), new ResearchPage("tc.research_page.RESEARCH.6"), new ResearchPage("tc.research_page.RESEARCH.7"), new ResearchPage("tc.research_page.RESEARCH.8"), new ResearchPage("tc.research_page.RESEARCH.9"), new ResearchPage("tc.research_page.RESEARCH.10"), new ResearchPage("tc.research_page.RESEARCH.11"), new ResearchPage("tc.research_page.RESEARCH.12")}).setAutoUnlock().setStub().setRound());
		
		
		GameRegistry.addRecipe(new ShapedOreRecipe(TTCItems.thaumiumAxe, "H", "S", 'H', new ItemStack(TTCItems.thaumiumToolHead, 1, ReferenceTAPI.getToolHeadID("axe")), 'S', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(TTCItems.thaumiumShovel, "H", "S", 'H', new ItemStack(TTCItems.thaumiumToolHead,1,ReferenceTAPI.getToolHeadID("shovel")), 'S', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(TTCItems.thaumiumSword, "H", "S", 'H', new ItemStack(TTCItems.thaumiumToolHead,1,ReferenceTAPI.getToolHeadID("sword")), 'S', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(TTCItems.thaumiumPickaxe, "H", "S", 'H', new ItemStack(TTCItems.thaumiumToolHead,1,ReferenceTAPI.getToolHeadID("pick")), 'S', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(TTCItems.thaumiumScythe, "H", "S", 'H', new ItemStack(TTCItems.thaumiumToolHead,1,ReferenceTAPI.getToolHeadID("scythe")), 'S', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(TTCItems.thaumiumChisel, "H", "S", 'H', new ItemStack(TTCItems.thaumiumToolHead,1,ReferenceTAPI.getToolHeadID("chisel")), 'S', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(TTCItems.thaumiumMace, "H", "S", 'H', new ItemStack(TTCItems.thaumiumToolHead,1,ReferenceTAPI.getToolHeadID("mace")), 'S', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(TTCItems.thaumiumJavelin, "H", "S", 'H', new ItemStack(TTCItems.thaumiumToolHead,1,ReferenceTAPI.getToolHeadID("javelin")), 'S', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(TTCItems.thaumiumProPick, "H", "S", 'H', new ItemStack(TTCItems.thaumiumToolHead,1,ReferenceTAPI.getToolHeadID("propick")), 'S', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(TTCItems.thaumiumHammer, "H", "S", 'H', new ItemStack(TTCItems.thaumiumToolHead,1,ReferenceTAPI.getToolHeadID("hammer")), 'S', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(TTCItems.thaumiumKnife, "H", "S", 'H', new ItemStack(TTCItems.thaumiumToolHead,1,ReferenceTAPI.getToolHeadID("knife")), 'S', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(TTCItems.thaumiumSaw, "H", "S", 'H', new ItemStack(TTCItems.thaumiumToolHead,1,ReferenceTAPI.getToolHeadID("saw")), 'S', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(TTCItems.thaumiumHoe, "H", "S", 'H', new ItemStack(TTCItems.thaumiumToolHead, 1, ReferenceTAPI.getToolHeadID("hoe")), 'S', "stickWood"));

		GameRegistry.addRecipe(new ItemStack(TTCBlocks.blockThaumiumAnvil), "DDD", " D ", "DDD", 'D', new ItemStack(TTCItems.thaumiumIngot2x, 1));
		
		WandTriggerRegistry.registerWandBlockTrigger(TerraThaumcraftMod.wandManager, 0, Blocks.cauldron, -1, ReferenceTTC.MOD_ID);
		WandTriggerRegistry.registerWandBlockTrigger(Thaumcraft.proxy.wandManager, 0, TFCBlocks.bookshelf, 0, "Thaumcraft");

		
		GameRegistry.addShapelessRecipe(new ItemStack(TTCItems.thaumiumUnshaped, 1, 0),
                new Object[]{Recipes.getStackNoTemp(new ItemStack(TTCItems.thaumiumIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TTCItems.thaumiumIngot, 1, 0),
                new Object[]{Recipes.getStackNoTemp(new ItemStack(TTCItems.thaumiumUnshaped, 1))});



		QuernManager queman = QuernManager.getInstance();

		for(int quality = 0; quality < ReferenceTTC.shardYield.length; quality++)
		{
			for(int type = 0; type < 6; type ++)
			{
				queman.addRecipe(new QuernRecipe(new ItemStack(TTCItems.roughShard, 1, (quality * 6)+type), new ItemStack(TTCItems.shardDust, ReferenceTTC.shardYield[quality], type)));
				if(quality >0)
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TTCItems.roughShard, ReferenceTTC.shardYield[quality], type), new ItemStack(TTCItems.roughShard, 1, (quality * 6)+type), "itemHammer"));
			}
		}

		BarrelManager barman = BarrelManager.getInstance();

		barman.addRecipe(new BarrelRecipe(new ItemStack(TTCItems.shardDust, 1, 0), new FluidStack(TFCFluids.FRESHWATER, 156), null, new FluidStack(TTCFluids.AIRSLURRY, 156), 0).setMinTechLevel(0).setSealedRecipe(false).setRemovesLiquid(false).setAllowAnyStack(false));
		barman.addRecipe(new BarrelRecipe(new ItemStack(TTCItems.shardDust, 1, 1), new FluidStack(TFCFluids.FRESHWATER, 156), null, new FluidStack(TTCFluids.FIRESLURRY, 156), 0).setMinTechLevel(0).setSealedRecipe(false).setRemovesLiquid(false).setAllowAnyStack(false));
		barman.addRecipe(new BarrelRecipe(new ItemStack(TTCItems.shardDust, 1, 2), new FluidStack(TFCFluids.FRESHWATER, 156), null, new FluidStack(TTCFluids.WATERSLURRY, 156), 0).setMinTechLevel(0).setSealedRecipe(false).setRemovesLiquid(false).setAllowAnyStack(false));
		barman.addRecipe(new BarrelRecipe(new ItemStack(TTCItems.shardDust, 1, 3), new FluidStack(TFCFluids.FRESHWATER, 156), null, new FluidStack(TTCFluids.EARTHSLURRY, 156), 0).setMinTechLevel(0).setSealedRecipe(false).setRemovesLiquid(false).setAllowAnyStack(false));
		barman.addRecipe(new BarrelRecipe(new ItemStack(TTCItems.shardDust, 1, 4), new FluidStack(TFCFluids.FRESHWATER, 156), null, new FluidStack(TTCFluids.ORDERSLURRY, 156), 0).setMinTechLevel(0).setSealedRecipe(false).setRemovesLiquid(false).setAllowAnyStack(false));
		barman.addRecipe(new BarrelRecipe(new ItemStack(TTCItems.shardDust, 1, 5), new FluidStack(TFCFluids.FRESHWATER, 156), null, new FluidStack(TTCFluids.ENTROPYSLURRY, 156), 0).setMinTechLevel(0).setSealedRecipe(false).setRemovesLiquid(false).setAllowAnyStack(false));

        for (int i = 0; i<24;i++)
        {
            Fluid slurrytype;
            switch(i%6)
            {
                case 1: slurrytype = TTCFluids.FIRESLURRY;
                    break;
                case 2: slurrytype = TTCFluids.WATERSLURRY;
                    break;
                case 3: slurrytype = TTCFluids.EARTHSLURRY;
                    break;
                case 4: slurrytype = TTCFluids.ORDERSLURRY;
                    break;
                case 5: slurrytype = TTCFluids.ENTROPYSLURRY;
                    break;
                default: slurrytype = TTCFluids.AIRSLURRY;
            }
            barman.addRecipe(new BarrelRecipe(new ItemStack(TTCItems.roughShard,1,i), new FluidStack(slurrytype, 5000), new ItemStack(ConfigItems.itemShard,5,i%6),new FluidStack(slurrytype, 5000)).setMinTechLevel(0).setSealedRecipe(true).setRemovesLiquid(true).setAllowAnyStack(true));

            if(i<6)
            {
                barman.addRecipe(new BarrelRecipe(new ItemStack(ConfigItems.itemShard,1,i), new FluidStack(slurrytype, 5000), new ItemStack(ConfigItems.itemShard,5,i),new FluidStack(slurrytype, 5000)).setMinTechLevel(0).setSealedRecipe(true).setRemovesLiquid(true).setAllowAnyStack(true));

            }
        }

		registerWoodItems();

        setupNetherItems();

		GameRegistry.addSmelting(TFCBlocks.sand, new ItemStack(Blocks.glass), 0.1f);
		GameRegistry.addSmelting(TFCBlocks.sand2, new ItemStack(Blocks.glass), 0.1f);











    }
    private static void setupNetherItems()
    {
        removeRecipe(Item.getItemFromBlock(Blocks.nether_brick));
        GameRegistry.addRecipe(new ItemStack(Blocks.nether_brick, 4), "PXP", "XPX", "PXP", 'P', new ItemStack(Items.netherbrick), 'X', new ItemStack(TFCItems.mortar, 1));

        CrucibleRecipe netherBrickRecipe = new CrucibleRecipe(researchNetherManu, new ItemStack(Items.netherbrick), new ItemStack(TFCItems.fireBrick,1,1), new AspectList().add(Aspect.FIRE, 1));
        ThaumcraftApi.getCraftingRecipes().add(netherBrickRecipe);

        //Setting Up Glowstone
        CrucibleRecipe glowStoneRecipe = new CrucibleRecipe(researchNetherManu, new ItemStack(Items.glowstone_dust,2), new ItemStack(TTCItems.shardDust,1,OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.LIGHT,1).add(Aspect.SENSES, 1));
        ThaumcraftApi.getCraftingRecipes().add(glowStoneRecipe);

        CrucibleRecipe[] soulsandRecipes =
                {
                        new CrucibleRecipe(researchNetherManu, new ItemStack(TTCBlocks.soulsand), new ItemStack(TFCBlocks.sand,1,OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.SOUL,1).add(Aspect.UNDEAD, 1)),
                        new CrucibleRecipe(researchNetherManu, new ItemStack(TTCBlocks.soulsand), new ItemStack(TFCBlocks.sand2,1,OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.SOUL,1).add(Aspect.UNDEAD, 1))
                };
        for(CrucibleRecipe recipe : soulsandRecipes)
        {
            ThaumcraftApi.getCraftingRecipes().add(recipe);
        }

        Field[] seedFields = TFCItems.class.getFields();
        ArrayList<CrucibleRecipe> seedRecipes = new ArrayList<>();
        for(Field field : seedFields)
        {
            if(field.getName().contains("seeds"))
            {
                try
                {
                    if(field.get(null) instanceof Item)
                    {

                        seedRecipes.add(new CrucibleRecipe(researchNetherManu, new ItemStack(TTCItems.netherwartSeeds), new ItemStack(((Item)field.get(null))), new AspectList().add(Aspect.MAGIC, 1)));

                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }

        CrucibleRecipe[] seedRecipeArray = seedRecipes.toArray(new CrucibleRecipe[0]);
        for(CrucibleRecipe recipe : seedRecipeArray)
        {
            ThaumcraftApi.getCraftingRecipes().add(recipe);
        }

        CrucibleRecipe netherseedsRecipe = new CrucibleRecipe(researchNetherManu, new ItemStack(TTCItems.netherwartSeeds), new ItemStack(Blocks.brown_mushroom), new AspectList().add(Aspect.MAGIC,1));
        CrucibleRecipe netherseedsRecipe2 = new CrucibleRecipe(researchNetherManu, new ItemStack(TTCItems.netherwartSeeds), new ItemStack(Blocks.red_mushroom), new AspectList().add(Aspect.MAGIC,1));
        ThaumcraftApi.getCraftingRecipes().add(netherseedsRecipe);
        ThaumcraftApi.getCraftingRecipes().add(netherseedsRecipe2);
        GameRegistry.addShapelessRecipe(new ItemStack(TTCItems.lavaSeed), new ItemStack(TFCItems.powder, 1, 3), new ItemStack(TTCItems.shardDust, 1, 1));
        CrucibleRecipe lavaSeedRecipe = new CrucibleRecipe(researchNetherManu, new ItemStack(TTCItems.lavaSeed,1,1), new ItemStack(TTCItems.lavaSeed,1,0), new AspectList().add(Aspect.LIFE,5));

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Setting up the research for Nether Manufacturing                                        //
        ////////////////////////////////////////////////////////////////////////////////////////////

        ResearchPage[] extraManuPages = new ResearchPage[]{
                new ResearchPage("researchpage.nethermanu"),
                new ResearchPage(netherBrickRecipe),
                new ResearchPage(glowStoneRecipe),
                new ResearchPage(soulsandRecipes),
                new ResearchPage("researchpage.netherfarming"),
                new ResearchPage(seedRecipeArray),
                new ResearchPage("researchpage.lavafarming"),
                new ResearchPage(lavaSeedRecipe)

        };



        new ResearchItem(
                researchNetherManu,
                "ALCHEMY",
                new AspectList().add(Aspect.EARTH, 1).add(Aspect.FIRE, 1).add(Aspect.MAGIC,1), -7, -3, 1,
                new ItemStack(Items.netherbrick))
                .setPages(extraManuPages)
                .setParents("ALCHEMICALMANUFACTURE")
                        //.setHidden().setItemTriggers(new ItemStack(TFCItems.fireBrick, 1, 1), new ItemStack(TFCItems.stoneBrick, 1, OreDictionary.WILDCARD_VALUE))
                .setConcealed()
                .setSecondary()
                .registerResearchItem();
        ThaumcraftApi.addWarpToResearch("NETHERMANUFACTURE", 3);
        ThaumcraftApi.addWarpToItem(new ItemStack(TTCItems.netherwartSeeds), 1);
        ThaumcraftApi.addWarpToItem(new ItemStack(TTCBlocks.soulsand), 1);
        ThaumcraftApi.addWarpToItem(new ItemStack(Items.glowstone_dust), 1);
        ThaumcraftApi.addWarpToItem(new ItemStack(Items.netherbrick), 1);






    }
    private static void registerWoodItems()
    {

		for (int i = 0; i < ReferenceTTC.WOOD_EXTRA.length; i ++)
		{
			removeRecipe(new ItemStack(TFCItems.logs,1,ReferenceTTC.WOODTYPESTARTINDEX+i));
			removeRecipe(new ItemStack(TFCBlocks.planks2,1,i +1));
			removeRecipe(new ItemStack(TFCBlocks.armorStand2, 1, i +1));
			//cutting logs into planks
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.singlePlank, 8, ReferenceTTC.WOODTYPESTARTINDEX + i), new ItemStack(TFCItems.logs, 1, ReferenceTTC.WOODTYPESTARTINDEX + i), "itemSaw"));
			//cutting planks into single plank
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.singlePlank, 4, ReferenceTTC.WOODTYPESTARTINDEX + i), new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6 + i), "itemSaw"));
			//make planks from single plank
			GameRegistry.addRecipe(new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6+i), "11","11", '1', new ItemStack(TFCItems.singlePlank, 4, ReferenceTTC.WOODTYPESTARTINDEX + i));
			//fence gates
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.fenceGate2, 2, 1 + i), "LPL", "LPL", 'L', new ItemStack(TFCItems.singlePlank, 4, ReferenceTTC.WOODTYPESTARTINDEX + i), 'P', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6+i));
			//armor stands
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.armorStand2, 1, i+11), "###", " # ", "%%%", '#', new ItemStack(TFCItems.singlePlank, 4, ReferenceTTC.WOODTYPESTARTINDEX + i), '%', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6+i));

		}
        //cut logs into single planks
        //GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.singlePlank, 8, 17), new ItemStack(TTCItems.magiclogs, 1, 0), "itemSaw"));
        //GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.singlePlank, 8, 18), new ItemStack(TTCItems.magiclogs, 1, 1), "itemSaw"));

        //Cut planks into single planks
        //GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.singlePlank, 4, 17), new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6), "itemSaw"));
        //GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.singlePlank, 4, 18), new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 7), "itemSaw"));

        //make planks from single plank
        //GameRegistry.addRecipe(new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6), "11","11", '1', new ItemStack(TFCItems.singlePlank, 4, 17));
        //GameRegistry.addRecipe(new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 7), "11","11", '1', new ItemStack(TFCItems.singlePlank, 4, 18));

        //Fences
        //GameRegistry.addRecipe(new ItemStack(TFCBlocks.fence2, 6, 1), "LPL", "LPL", 'L', new ItemStack(TTCItems.magiclogs, 1, 0), 'P', new ItemStack(TFCItems.singlePlank, 4, 17));
        //GameRegistry.addRecipe(new ItemStack(TFCBlocks.fence2, 6, 2), "LPL", "LPL", 'L', new ItemStack(TTCItems.magiclogs, 1, 1), 'P', new ItemStack(TFCItems.singlePlank, 4, 18));

        //FenceGates
        //GameRegistry.addRecipe(new ItemStack(TTCBlocks.fenceGate2, 2, 1), "LPL", "LPL", 'L', new ItemStack(TFCItems.singlePlank, 4, 17), 'P', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6));
        //GameRegistry.addRecipe(new ItemStack(TTCBlocks.fenceGate2, 2, 2), "LPL", "LPL", 'L', new ItemStack(TFCItems.singlePlank, 4, 18), 'P', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 7));

		//GameRegistry.addRecipe(new ItemStack(TFCBlocks.fenceGate2, 2, 17), "LPL", "LPL", 'L', new ItemStack(TFCItems.singlePlank, 4, 17), 'P', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6));
		//GameRegistry.addRecipe(new ItemStack(TFCBlocks.fenceGate2, 2, 18), "LPL", "LPL", 'L', new ItemStack(TFCItems.singlePlank, 4, 18), 'P', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 7));

        //Wooden supports
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.woodSupportV2, 8, 1), "A2", " 2", '2', new ItemStack(TTCItems.magiclogs, 1, 0), 'A', "itemSaw"));
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.woodSupportV2, 8, 2), "A2", " 2", '2', new ItemStack(TTCItems.magiclogs, 1, 1), 'A', "itemSaw"));

        //Barrels
       // GameRegistry.addRecipe(new ItemStack(TTCBlocks.magicBarrel, 1, 0), "# #", "# #", "###", '#', new ItemStack(TFCItems.singlePlank, 4, 17));
        //GameRegistry.addRecipe(new ItemStack(TTCBlocks.magicBarrel, 1, 1), "# #", "# #", "###", '#', new ItemStack(TFCItems.singlePlank, 4, 18));

		//GameRegistry.addRecipe(new ItemStack(TFCBlocks.barrel, 1, 17), "# #", "# #", "###", '#', new ItemStack(TFCItems.singlePlank, 4, 17));
		//GameRegistry.addRecipe(new ItemStack(TFCBlocks.barrel, 1, 18), "# #", "# #", "###", '#', new ItemStack(TFCItems.singlePlank, 4, 18));

        //ArmorStands
        //GameRegistry.addRecipe(new ItemStack(TFCBlocks.armorStand2, 1, 1), "###", " # ", "%%%", '#', new ItemStack(TFCItems.singlePlank, 4, 17), '%', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6));
        //GameRegistry.addRecipe(new ItemStack(TFCBlocks.armorStand2, 1, 2), "###", " # ", "%%%", '#', new ItemStack(TFCItems.singlePlank, 4, 18), '%', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 7));

        //Looms
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.loom, 1, 17), "LLL", "LSL", "L L", 'L', new ItemStack(TFCItems.singlePlank, 4, 17), 'S', "stickWood"));
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.loom, 1, 18), "LLL", "LSL", "L L", 'L', new ItemStack(TFCItems.singlePlank, 4, 18), 'S', "stickWood"));

        //ToolRacks
        //GameRegistry.addRecipe(new ItemStack(TFCBlocks.toolRack, 1, 17), "###", "   ", "###", '#', new ItemStack(TFCItems.singlePlank, 4, 17));
        //GameRegistry.addRecipe(new ItemStack(TFCBlocks.toolRack, 1, 18), "###", "   ", "###", '#', new ItemStack(TFCItems.singlePlank, 4, 18));

        //Chests
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.chest, 1, 17), "###", "# #", "###", '#', new ItemStack(TFCItems.singlePlank, 1, 17)));
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.chest, 1, 18), "###", "# #", "###", '#', new ItemStack(TFCItems.singlePlank, 1, 18)));

        //Doors
        //GameRegistry.addRecipe(new ItemStack(TTCItems.greatwoodDoor), "WW", "WW", "WW", 'W', new ItemStack(TFCItems.singlePlank, 1, 17));
        //GameRegistry.addRecipe(new ItemStack(TTCItems.silverwoodDoor), "WW", "WW", "WW", 'W', new ItemStack(TFCItems.singlePlank, 1, 18));
    }
	public static void registerNuggets()
	{


		//Remove previous transmutation recipes to avoid getting wrong output
		ConfigResearch.recipes.remove("TransIron");
		ConfigResearch.recipes.remove("TransGold");
		ConfigResearch.recipes.remove("TransCopper");
		ConfigResearch.recipes.remove("TransTin");
		ConfigResearch.recipes.remove("TransSilver");
		ConfigResearch.recipes.remove("TransLead");
		try
		{
			removeCrucibleRecipe("TRANSIRON");
			removeCrucibleRecipe("TRANSGOLD");
			removeCrucibleRecipe("TRANSCOPPER");
			removeCrucibleRecipe("TRANSTIN");
			removeCrucibleRecipe("TRANSSILVER");
			removeCrucibleRecipe("TRANSLEAD");
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		//add cauldron recipes to convert metals.

		ConfigResearch.recipes.put("TransIron", ThaumcraftApi.addCrucibleRecipe("TRANSIRON", new ItemStack(TFCItems.rawBloom, 1, 30), "nuggetIron", (new AspectList()).merge(Aspect.METAL, 2)));
		ConfigResearch.recipes.put("TransGold", ThaumcraftApi.addCrucibleRecipe("TRANSGOLD", new ItemStack(TMatItems.metalNuggets, 3, 9), "nuggetGold", (new AspectList()).merge(Aspect.METAL, 2).merge(Aspect.GREED, 1)));
		ConfigResearch.recipes.put("TransCopper", ThaumcraftApi.addCrucibleRecipe("TRANSCOPPER", new ItemStack(TMatItems.metalNuggets, 3, 8), "nuggetCopper", (new AspectList()).merge(Aspect.METAL, 2).merge(Aspect.EXCHANGE, 1)));
		ConfigResearch.recipes.put("TransTin", ThaumcraftApi.addCrucibleRecipe("TRANSTIN", new ItemStack(TMatItems.metalNuggets, 3, 1), "nuggetTin", (new AspectList()).merge(Aspect.METAL, 2).merge(Aspect.CRYSTAL, 1)));
		ConfigResearch.recipes.put("TransSilver", ThaumcraftApi.addCrucibleRecipe("TRANSSILVER", new ItemStack(TMatItems.metalNuggets, 3, 11), "nuggetSilver", (new AspectList()).merge(Aspect.METAL, 2).merge(Aspect.GREED, 1)));
		ConfigResearch.recipes.put("TransLead", ThaumcraftApi.addCrucibleRecipe("TRANSLEAD", new ItemStack(TMatItems.metalNuggets, 3, 8), "nuggetLead", (new AspectList()).merge(Aspect.METAL, 2).merge(Aspect.POISON, 1)));




	}


	
	public static void registerLateRecipes() {
		registerNuggets();
		AnvilManager anvilMan = AnvilManager.getInstance();

		ironWandCaps = new AnvilRecipe(new ItemStack(TFCItems.wroughtIronSheet, 1), null, wandcapPlanName, AnvilReq.WROUGHTIRON, new ItemStack(ConfigItems.itemWandCap, 2, 0)).addRecipeSkill(Global.SKILL_TOOLSMITH).setCraftingXP(3);
		copperWandCaps =new MagicAnvilRecipe(new ItemStack(TFCItems.copperSheet, 1), null, wandcapPlanName, new ItemStack(ConfigItems.itemWandCap, 2, 3), new AspectList().merge(Aspect.ORDER, 2).merge(Aspect.FIRE, 2).merge(Aspect.AIR, 2), "CAP_copper");
		silverWandCaps = new MagicAnvilRecipe(new ItemStack(TFCItems.silverSheet, 1), null, wandcapPlanName, new ItemStack(ConfigItems.itemWandCap, 2, 5), new AspectList().merge(Aspect.ORDER, 4).merge(Aspect.FIRE, 4).merge(Aspect.AIR, 4), "CAP_silver");
		goldWandCaps =new MagicAnvilRecipe(new ItemStack(TFCItems.goldSheet, 1), null, wandcapPlanName, new ItemStack(ConfigItems.itemWandCap, 2, 1), new AspectList().merge(Aspect.ORDER, 3).merge(Aspect.FIRE, 3).merge(Aspect.AIR, 3), "CAP_gold");
		thaumiumWandCaps = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumSheet, 1), null, wandcapPlanName, new ItemStack(ConfigItems.itemWandCap, 2, 6), new AspectList().merge(Aspect.ORDER, 6).merge(Aspect.FIRE, 6).merge(Aspect.AIR, 6), "CAP_thaumium");
		
		
		//LogHelper.info("Iron Wand Caps have a crafting value of " + ironWandCaps.getCraftingValue());

		thaumiumAxeHead = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumIngot, 1), null, "axe", new ItemStack(TTCItems.thaumiumToolHead, 1, ReferenceTAPI.getToolHeadID("axe")),null, "THAUMIUM").addRecipeSkill(Global.SKILL_TOOLSMITH).setCraftingXP(3);
		thaumiumChiselHead = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumIngot, 1), null, "chisel", new ItemStack(TTCItems.thaumiumToolHead, 1, ReferenceTAPI.getToolHeadID("chisel")),null, "THAUMIUM").addRecipeSkill(Global.SKILL_TOOLSMITH).setCraftingXP(3);
		thaumiumHammerHead = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumIngot, 1), null, "hammer", new ItemStack(TTCItems.thaumiumToolHead, 1, ReferenceTAPI.getToolHeadID("hammer")),null, "THAUMIUM").addRecipeSkill(Global.SKILL_TOOLSMITH).setCraftingXP(3);
		thaumiumHoeHead = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumIngot, 1), null, "hoe", new ItemStack(TTCItems.thaumiumToolHead, 1, ReferenceTAPI.getToolHeadID("hoe")),null, "THAUMIUM").addRecipeSkill(Global.SKILL_TOOLSMITH).setCraftingXP(3);
		thaumiumJavelinHead = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumIngot, 1), null, "javelin", new ItemStack(TTCItems.thaumiumToolHead, 1, ReferenceTAPI.getToolHeadID("javelin")),null, "THAUMIUM").addRecipeSkill(Global.SKILL_WEAPONSMITH).setCraftingXP(3);
		thaumiumKnifeHead = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumIngot, 1), null, "knife", new ItemStack(TTCItems.thaumiumToolHead, 1, ReferenceTAPI.getToolHeadID("knife")),null, "THAUMIUM").addRecipeSkill(Global.SKILL_TOOLSMITH).setCraftingXP(3);
		thaumiumMaceHead = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumIngot2x, 1), null, "mace", new ItemStack(TTCItems.thaumiumToolHead, 1, ReferenceTAPI.getToolHeadID("mace")),null, "THAUMIUM").addRecipeSkill(Global.SKILL_WEAPONSMITH).setCraftingXP(3);
		thaumiumPickHead = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumIngot, 1), null, "pick", new ItemStack(TTCItems.thaumiumToolHead, 1, ReferenceTAPI.getToolHeadID("pick")),null, "THAUMIUM").addRecipeSkill(Global.SKILL_TOOLSMITH).setCraftingXP(3);
		thaumiumProPickHead = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumIngot, 1), null, "propick", new ItemStack(TTCItems.thaumiumToolHead, 1, ReferenceTAPI.getToolHeadID("propick")),null, "THAUMIUM").addRecipeSkill(Global.SKILL_TOOLSMITH).setCraftingXP(3);
		thaumiumSawHead = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumIngot, 1), null, "saw",  new ItemStack(TTCItems.thaumiumToolHead, 1, ReferenceTAPI.getToolHeadID("saw")),null, "THAUMIUM").addRecipeSkill(Global.SKILL_TOOLSMITH).setCraftingXP(3);
		thaumiumScytheHead = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumIngot, 1), null, "scythe", new ItemStack(TTCItems.thaumiumToolHead, 1, ReferenceTAPI.getToolHeadID("scythe")),null, "THAUMIUM").addRecipeSkill(Global.SKILL_TOOLSMITH).setCraftingXP(3);
		thaumiumShovelHead = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumIngot, 1), null, "shovel", new ItemStack(TTCItems.thaumiumToolHead, 1, ReferenceTAPI.getToolHeadID("shovel")),null, "THAUMIUM").addRecipeSkill(Global.SKILL_TOOLSMITH).setCraftingXP(3);
		thaumiumSwordHead = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumIngot2x, 1), null, "sword", new ItemStack(TTCItems.thaumiumToolHead, 1, ReferenceTAPI.getToolHeadID("sword")),null, "THAUMIUM").addRecipeSkill(Global.SKILL_WEAPONSMITH).setCraftingXP(3);
		
		weldThaumiumIngots = new AnvilRecipe(new ItemStack(TTCItems.thaumiumIngot),new ItemStack(TTCItems.thaumiumIngot),AnvilReq.WROUGHTIRON, new ItemStack(TTCItems.thaumiumIngot2x, 1));
		
		
		thaumiumSheet = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumIngot2x, 1), null, "sheet", new ItemStack(TTCItems.thaumiumSheet, 1),null, "THAUMIUM");
		weldThaumiumSheets = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumSheet),new ItemStack(TTCItems.thaumiumSheet), new ItemStack(TTCItems.thaumiumSheet2x, 1));
		
		weldThaumiumUnfinishedGreaves = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumUnfinishedGreaves,1,0),new ItemStack(TTCItems.thaumiumSheet), new ItemStack(TTCItems.thaumiumUnfinishedGreaves,1, 1));
		weldThaumiumUnfinishedChest = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumUnfinishedChest,1,0),new ItemStack(TTCItems.thaumiumSheet2x), new ItemStack(TTCItems.thaumiumUnfinishedChest,1, 1));
		weldThaumiumUnfinishedBoots = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumUnfinishedBoots,1,0),new ItemStack(TTCItems.thaumiumSheet), new ItemStack(TTCItems.thaumiumUnfinishedBoots,1, 1));
		weldThaumiumUnfinishedHelm = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumUnfinishedHelm,1,0),new ItemStack(TTCItems.thaumiumSheet), new ItemStack(TTCItems.thaumiumUnfinishedHelm,1, 1));
		
		thaumiumUnfinishedGreaves = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumSheet2x,1), null, "legsplate", new ItemStack(TTCItems.thaumiumUnfinishedGreaves, 1, 0),null, "THAUMIUM").addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3);
		thaumiumGreaves = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumUnfinishedGreaves,1, 1), null, "legsplate", new ItemStack(TTCItems.thaumiumGreaves, 1),null, "THAUMIUM").addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3);

		thaumiumUnfinishedChest = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumSheet2x,1), null, "chestplate", new ItemStack(TTCItems.thaumiumUnfinishedChest, 1, 0),null, "THAUMIUM").addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3);
		thaumiumChest = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumUnfinishedChest,1, 1), null, "chestplate", new ItemStack(TTCItems.thaumiumChest, 1), null, "THAUMIUM").addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3);

		thaumiumUnfinishedBoots = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumSheet,1), null, "bootsplate", new ItemStack(TTCItems.thaumiumUnfinishedBoots, 1, 0),null, "THAUMIUM").addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3);
		thaumiumBoots = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumUnfinishedBoots,1, 1), null, "bootsplate", new ItemStack(TTCItems.thaumiumBoots, 1),null, "THAUMIUM").addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3);

		thaumiumUnfinishedHelm = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumSheet,1), null, "helmplate", new ItemStack(TTCItems.thaumiumUnfinishedHelm, 1, 0),null, "THAUMIUM").addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3);
		thaumiumHelm = new MagicAnvilRecipe(new ItemStack(TTCItems.thaumiumUnfinishedHelm,1, 1), null, "helmplate", new ItemStack(TTCItems.thaumiumHelm, 1),null, "THAUMIUM").addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3);

		

		anvilMan.addPlan(wandcapPlanName, new PlanRecipe(new RuleEnum[]{RuleEnum.BENDLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.BENDTHIRDFROMLAST}));
		anvilMan.addRecipe(ironWandCaps);
		anvilMan.addRecipe(copperWandCaps);
		anvilMan.addRecipe(silverWandCaps);
		anvilMan.addRecipe(goldWandCaps);
		anvilMan.addRecipe(thaumiumWandCaps);
		anvilMan.addRecipe(thaumiumAxeHead);
		anvilMan.addRecipe(thaumiumChiselHead);
		anvilMan.addRecipe(thaumiumHammerHead);
		anvilMan.addRecipe(thaumiumHoeHead);
		anvilMan.addRecipe(thaumiumJavelinHead);
		anvilMan.addRecipe(thaumiumKnifeHead);
		anvilMan.addRecipe(thaumiumMaceHead);
		anvilMan.addRecipe(thaumiumPickHead);
		anvilMan.addRecipe(thaumiumProPickHead);
		anvilMan.addRecipe(thaumiumSawHead);
		anvilMan.addRecipe(thaumiumScytheHead);
		anvilMan.addRecipe(thaumiumShovelHead);
		anvilMan.addRecipe(thaumiumSwordHead);
		anvilMan.addRecipe(thaumiumSheet);
		anvilMan.addWeldRecipe(weldThaumiumSheets);
		anvilMan.addWeldRecipe(weldThaumiumIngots);
		anvilMan.addWeldRecipe(weldThaumiumUnfinishedGreaves);
		anvilMan.addWeldRecipe(weldThaumiumUnfinishedChest);
		anvilMan.addWeldRecipe(weldThaumiumUnfinishedBoots);
		anvilMan.addWeldRecipe(weldThaumiumUnfinishedHelm);
		anvilMan.addRecipe(thaumiumUnfinishedGreaves);
		anvilMan.addRecipe(thaumiumGreaves);
		anvilMan.addRecipe(thaumiumUnfinishedChest);
		anvilMan.addRecipe(thaumiumChest);
		anvilMan.addRecipe(thaumiumUnfinishedBoots);
		anvilMan.addRecipe(thaumiumBoots);
		anvilMan.addRecipe(thaumiumUnfinishedHelm);
		anvilMan.addRecipe(thaumiumHelm);
		
		
		List<AnvilRecipe> recipes = anvilMan.getRecipeList();
		for(AnvilRecipe recipe : recipes)
		{
			if(recipe.getCraftingResult().getItem() == TTCItems.thaumiumBoots)
			{
				//LogHelper.info(ReferenceTTC.MOD_NAME,recipe);
			}
		}
		arcaneStone = ThaumcraftApi.addArcaneCraftingRecipe(
				"ARCANESTONE",
				new ItemStack(ConfigBlocks.blockCosmeticSolid, 9, 6),
				new AspectList().add(Aspect.EARTH, 1).add(Aspect.FIRE, 1),
				new Object[] {
						"SSS",
						"SCS",
						"SSS",
						Character.valueOf('S'), "stoneSmooth",
						Character.valueOf('C'), new ItemStack(ConfigItems.itemShard, 1, 32767)
				});
		enchantedFabric = ThaumcraftApi.addArcaneCraftingRecipe(
				"ENCHFABRIC",
				new ItemStack(ConfigItems.itemResource, 1, 7),
				new AspectList().add(Aspect.AIR, 1).add(Aspect.EARTH, 1).add(Aspect.FIRE, 1).add(Aspect.WATER, 1).add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1),
				new Object[] {
						" S ",
						"SCS",
						" S ",
						Character.valueOf('S'), new ItemStack(TFCItems.woolYarn, 1, 0),
						Character.valueOf('C'), new ItemStack(TFCItems.woolCloth, 1, 0)
				});
		bootsTraveller = ThaumcraftApi.addInfusionCraftingRecipe(
				"BOOTSTRAVELLER",
				new ItemStack(ConfigItems.itemBootsTraveller),
				1,
				new AspectList().add(Aspect.FLIGHT, 25).add(Aspect.TRAVEL, 25),
				new ItemStack(TFCItems.leatherBoots),
				new ItemStack[]{
						new ItemStack(ConfigItems.itemShard, 1, 0),
						new ItemStack(ConfigItems.itemShard, 1, 0),
						new ItemStack(ConfigItems.itemResource, 1, 7),
						new ItemStack(ConfigItems.itemResource, 1, 7),
						new ItemStack(Items.feather),
						new ItemStack(TFCItems.fishRaw, 1, 0)
				});
		wardedJar = ThaumcraftApi.addArcaneCraftingRecipe(
				"DISTILESSENTIA",
				new ItemStack(ConfigBlocks.blockJar, 1, 0),
				new AspectList().add(Aspect.WATER, 1),
				new Object[] {
						"GWG",
						"G G",
						"GGG",
						Character.valueOf('W'), "plankWood",
						Character.valueOf('G'), Blocks.glass_pane
				});

		thaumometer = oreDictRecipe(
				new ItemStack(ConfigItems.itemThaumometer),
				new Object[] {
						" 1 ",
						"IGI",
						" 1 ",
						Character.valueOf('I'), TFCItems.goldSheet,
						Character.valueOf('G'), Blocks.glass,
						Character.valueOf('1'), new ItemStack(ConfigItems.itemShard, 1, 32767)
				});

		ConfigResearch.recipes.put("Alumentum", ThaumcraftApi.addCrucibleRecipe("ALUMENTUM", new ItemStack(ConfigItems.itemResource, 1, 0), new ItemStack(Items.coal, 1, 32767), new AspectList().merge(Aspect.ENERGY, 3).merge(Aspect.FIRE, 3).merge(Aspect.ENTROPY, 3)));

		alumentum = new CrucibleRecipe(
				"ALUMENTUM",
				new ItemStack(ConfigItems.itemResource,1,0),
				new ItemStack(TFCItems.coal, 1, 32767),
				new AspectList().merge(Aspect.ENERGY, 3).merge(Aspect.FIRE, 3).merge(Aspect.ENTROPY, 3));
		bitumAlumentum = new CrucibleRecipe(
				"ALUMENTUM",
				new ItemStack(ConfigItems.itemResource,1,0),
				new ItemStack(TFCItems.oreChunk, 1, 14),
				new AspectList().merge(Aspect.ENERGY, 3).merge(Aspect.FIRE, 3).merge(Aspect.ENTROPY, 3));

		registerCrucibleRecipe(alumentum);
		registerCrucibleRecipe(bitumAlumentum);

		ResearchPage alumentumPage = new ResearchPage(new CrucibleRecipe[]{alumentum,bitumAlumentum});
		alumentumPage.recipeOutput = alumentum.getRecipeOutput();
		ConfigResearch.recipes.put("Alumentum", alumentum);
		ConfigResearch.recipes.put("Alumentum1", bitumAlumentum);
		replaceRecipeInResearch("ALUMENTUM", alumentumPage);


		removeArcaneWorktableRecipe("ArcaneStone1");
		removeArcaneWorktableRecipe("EnchantedFabric");
		removeRecipe(ConfigItems.itemThaumometer);

		ConfigResearch.recipes.put("ArcaneStone1", arcaneStone );
		ConfigResearch.recipes.put("EnchantedFabric", enchantedFabric);
		ConfigResearch.recipes.put("BootsTraveller", bootsTraveller);
		ConfigResearch.recipes.put("WardedJar", wardedJar);
		ConfigResearch.recipes.put("Thaumometer", thaumometer);


		replaceRecipeInResearch("ARCANESTONE", new ResearchPage(arcaneStone));
		replaceRecipeInResearch("ENCHFABRIC", new ResearchPage(enchantedFabric));
		replaceRecipeInResearch("THAUMOMETER", new ResearchPage(thaumometer));
		replaceRecipeInResearch("RESEARCH",new ResearchPage(thaumometer));

		removeArcaneWorktableRecipe("NodeStabilizer");
		nodeStabilizer = ThaumcraftApi.addArcaneCraftingRecipe(
				"NODESTABILIZER",
				new ItemStack(ConfigBlocks.blockStoneDevice, 1, 9),
				new AspectList().add(Aspect.WATER, 32).add(Aspect.EARTH, 32).add(Aspect.ORDER, 32),
				new Object[] {
						" G ",
						"QPQ",
						"SNS",
						Character.valueOf('S'), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 7),
						Character.valueOf('G'), "ingotGold", Character.valueOf('P'), new ItemStack(Blocks.piston),
						Character.valueOf('Q'), new ItemStack(Blocks.quartz_block),
						Character.valueOf('N'), new ItemStack(ConfigItems.itemResource, 1, 1)
				});

		ConfigResearch.recipes.put("NodeStabilizer", nodeStabilizer);

		replaceRecipeInResearch("NODESTABILIZER",new ResearchPage(nodeStabilizer));

		HeatRegistry.getInstance().addIndex(
				new HeatIndex(
						new ItemStack(ConfigItems.itemShard, 1, 6),
						ReferenceTTC.balancedShardSHC,
						ReferenceTTC.balancedShardMelt,
						new ItemStack(ConfigItems.itemResource, 1, 14)
				).setMinMax(14,0)
		);

	}
	static IRecipe oreDictRecipe(ItemStack res, Object[] params)
	{
		IRecipe rec = new ShapedOreRecipe(res, params);
		CraftingManager.getInstance().getRecipeList().add(rec);
		return rec;
	}
}
