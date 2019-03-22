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

package taeo.terrathaumcraft.init;

import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.Handlers.TFCFuelHandler;
import com.bioxx.tfc.Items.ItemBlocks.ItemWoodDoor;
import com.bioxx.tfc.Items.ItemIngot;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.api.*;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;
import taeo.terrathaumcraft.item.*;
import taeo.terrathaumcraft.item.equipment.*;
import taeo.ttfcapi.api.ChestExceptions;
import taeo.ttfcapi.item.ItemIngotTAPI;
import taeo.ttfcapi.item.ItemMeltedMetalTAPI;
import thaumcraft.api.ThaumcraftApi;

import com.bioxx.tfc.Core.Metal.Alloy;
import com.bioxx.tfc.Core.Metal.MetalRegistry;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

import cpw.mods.fml.common.registry.GameRegistry;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.armor.ItemVoidRobeArmor;

import java.util.ArrayList;

public class TTCItems {
	

	public static Item thaumiumUnshaped;
	public static Item thaumiumIngot;
	public static Item thaumiumIngot2x;
	public static Item thaumiumSheet;
	public static Item thaumiumSheet2x;
	
	public static Item thaumiumAxe;
	public static Item elementalAxe;
	public static Item oldElementalAxe;
	public static Item thaumiumShovel;
	public static Item thaumiumPickaxe;
	public static Item thaumiumHoe;
	public static Item thaumiumChisel;
	public static Item thaumiumScythe;
	public static Item thaumiumMace;
	public static Item thaumiumProPick;
	public static Item thaumiumHammer;
	public static Item thaumiumKnife;
	public static Item thaumiumSaw;
	public static Item thaumiumJavelin;
	
	public static Item thaumiumToolHead;
	public static Item thaumiumSword;
	
	public static Item thaumiumGreaves;
	public static Item thaumiumChest;
	public static Item thaumiumBoots;
	public static Item thaumiumHelm;
	public static Item thaumiumUnfinishedGreaves;
	public static Item thaumiumUnfinishedChest;
	public static Item thaumiumUnfinishedBoots;
	public static Item thaumiumUnfinishedHelm;

	public static Item fortressHelmet;
	public static Item fortressChest;
	public static Item fortressGreaves;

	public static Item transmutedOre;

    public static Item rottenMeatRaw;

    public static Item roughShard;
    public static Item shardDust;

    public static Item magiclogs;
    //public static Item logsTTC;

    public static Item debugstick;


    public static Item netherwartSeeds;

    public static Item greatwoodDoor;
    public static Item silverwoodDoor;
    public static Item lavaSeed;

	//public static Item singlePlank;

	//public static ItemBlock itemCrucibleBlock;
	
	public static ToolMaterial ThaumiumEnhancedToolMaterial;//= EnumHelper.addToolMaterial("THAUMIUMENHANCED", 3, (int)(TFCItems.WroughtIronUses * 1.60), 14F, 135, 22);
	public static ToolMaterial ThaumicSteel;
	public static Metal thaumium;


	public static HeatRaw ThaumiumRaw = new HeatRaw(0.35D, 1400.0D);
    public static int netherwartCropID = 99;

	public static Item oldHoverHarness;
	public static Item oldHelmetCultistRobes;
	public static Item oldChestCultistRobes;
	public static Item oldLegsCultistRobes;

	public static Item oldHelmetVoidRobes;
	public static Item oldChestVoidRobes;
	public static Item oldLegsVoidRobes;

	public static Item oldHelmetCultistLeaderPlate;
	public static Item oldChestCultistLeaderPlate;
	public static Item oldLegsCultistLeaderPlate;

	public static Item oldHelmetCultistPlate;
	public static Item oldChestCultistPlate;
	public static Item oldLegsCultistPlate;


    public static void init()
	{
		ThaumiumEnhancedToolMaterial= EnumHelper.addToolMaterial("THAUMIUMENHANCED", 3, (int) (TFCItems.wroughtIronUses * 1.60), 14F, 135, 22);
		//											(Armor armor, int renderIndex, int armorSlot, ArmorMaterial armorMat, int thermal, int type) {
		ThaumicSteel = EnumHelper.addToolMaterial("THAUMICSTEEL", 4, (int) (TFCItems.blueSteelUses * 1.25), TFCItems.blueSteelEff + 2F, 240, 22);
		thaumiumUnfinishedGreaves = new ItemUnfinishedArmorTTC().setMetal("Thaumium", 2).setUnlocalizedName("thaumiumunfinishedgreaves");
		GameRegistry.registerItem(thaumiumUnfinishedGreaves, thaumiumUnfinishedGreaves.getUnlocalizedName());
		thaumiumGreaves = new ItemThaumiumArmorTTC(ArmorMetalsTTC.Thaumium, 0, 2, ThaumcraftApi.armorMatThaumium, 50, 2).setUnlocalizedName("thaumiumgreaves");
		GameRegistry.registerItem(thaumiumGreaves, thaumiumGreaves.getUnlocalizedName());
		
		thaumiumUnfinishedChest = new ItemUnfinishedArmorTTC().setMetal("Thaumium", 1).setUnlocalizedName("thaumiumunfinishedchest");
		GameRegistry.registerItem(thaumiumUnfinishedChest, thaumiumUnfinishedChest.getUnlocalizedName());
		thaumiumChest = new ItemThaumiumArmorTTC(ArmorMetalsTTC.Thaumium, 0, 1, ThaumcraftApi.armorMatThaumium, 50, 1).setUnlocalizedName("thaumiumchest");
		GameRegistry.registerItem(thaumiumChest, thaumiumChest.getUnlocalizedName());
		
		thaumiumUnfinishedBoots = new ItemUnfinishedArmorTTC().setMetal("Thaumium", 3).setUnlocalizedName("thaumiumunfinishedboots");
		GameRegistry.registerItem(thaumiumUnfinishedBoots, thaumiumUnfinishedBoots.getUnlocalizedName());
		thaumiumBoots = new ItemThaumiumArmorTTC(ArmorMetalsTTC.Thaumium, 0, 3, ThaumcraftApi.armorMatThaumium, 50, 3).setUnlocalizedName("thaumiumboots");
		GameRegistry.registerItem(thaumiumBoots, thaumiumBoots.getUnlocalizedName());
		
		thaumiumUnfinishedHelm = new ItemUnfinishedArmorTTC().setMetal("Thaumium", 0).setUnlocalizedName("thaumiumunfinishedhelm");
		GameRegistry.registerItem(thaumiumUnfinishedHelm, thaumiumUnfinishedHelm.getUnlocalizedName());
		thaumiumHelm = new ItemThaumiumArmorTTC(ArmorMetalsTTC.Thaumium, 0, 0, ThaumcraftApi.armorMatThaumium, 50, 0).setUnlocalizedName("thaumiumhelm");
		GameRegistry.registerItem(thaumiumHelm, thaumiumHelm.getUnlocalizedName());

		
		
		thaumiumUnshaped = new ItemMeltedMetalTTC().setUnlocalizedName("thaumiumunshaped");
		GameRegistry.registerItem(thaumiumUnshaped, thaumiumUnshaped.getUnlocalizedName());
		
		thaumiumIngot = new ItemIngotTTC().setUnlocalizedName("thaumiumingot");
		GameRegistry.registerItem(thaumiumIngot, thaumiumIngot.getUnlocalizedName());
		ChestExceptions.getInstance().addException(thaumiumIngot);

		thaumiumIngot2x = new ItemIngotTTC().setSize(EnumSize.LARGE).setMetal("Thaumium", 200).setUnlocalizedName("thaumiumdoubleingot");
		GameRegistry.registerItem(thaumiumIngot2x, thaumiumIngot2x.getUnlocalizedName());

		thaumiumAxe = new ItemThaumiumAxeTTC().setMaxDamage(ThaumiumEnhancedToolMaterial.getMaxUses());
		GameRegistry.registerItem(thaumiumAxe, thaumiumAxe.getUnlocalizedName());
		//elementalAxe = new ItemElementalAxeTTC().setMaxDamage(ThaumicSteel.getMaxUses()).setUnlocalizedName("elementalaxe");
		//GameRegistry.registerItem(elementalAxe, elementalAxe.getUnlocalizedName());
		oldElementalAxe = ConfigItems.itemAxeElemental;
		ConfigItems.itemAxeElemental = new ItemElementalAxeTTC().setMaxDamage(ThaumicSteel.getMaxUses()).setUnlocalizedName("elementalaxe");
		GameRegistry.registerItem(ConfigItems.itemAxeElemental, ConfigItems.itemAxeElemental.getUnlocalizedName());
		OreDictionary.registerOre("itemAxe", new ItemStack(ConfigItems.itemAxeElemental, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("itemSaw", new ItemStack(ConfigItems.itemAxeElemental, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addRecipe(new ItemStack(ConfigItems.itemAxeElemental), "A", 'A', new ItemStack(oldElementalAxe, 1, OreDictionary.WILDCARD_VALUE));

		oldHoverHarness = ConfigItems.itemHoverHarness;
		ConfigItems.itemHoverHarness = new ItemHoverHarnessTTC(ThaumcraftApi.armorMatSpecial, 1, 1).setUnlocalizedName("HoverHarness");
		GameRegistry.registerItem(ConfigItems.itemHoverHarness, "HoverHarness", "Thaumcraft");
		GameRegistry.addRecipe(new ItemStack(ConfigItems.itemHoverHarness), "A", 'A', new ItemStack(oldHoverHarness, 1, OreDictionary.WILDCARD_VALUE));

		oldHelmetCultistRobes = ConfigItems.itemHelmetCultistRobe;
		oldChestCultistRobes = ConfigItems.itemChestCultistRobe;
		oldLegsCultistRobes = ConfigItems.itemLegsCultistRobe;
		ConfigItems.itemHelmetCultistRobe = new ItemCultistRobeArmorTTC(ItemArmor.ArmorMaterial.IRON, 4, 0).setUnlocalizedName("ItemHelmetCultistRobe");
		GameRegistry.registerItem(ConfigItems.itemHelmetCultistRobe, "ItemHelmetCultistRobe", "Thaumcraft");
		ConfigItems.itemChestCultistRobe = new ItemCultistRobeArmorTTC(ItemArmor.ArmorMaterial.IRON, 4, 1).setUnlocalizedName("ItemChestplateCultistRobe");
		GameRegistry.registerItem(ConfigItems.itemChestCultistRobe, "ItemChestplateCultistRobe", "Thaumcraft");
		ConfigItems.itemLegsCultistRobe = new ItemCultistRobeArmorTTC(ItemArmor.ArmorMaterial.IRON, 4, 2).setUnlocalizedName("ItemLeggingsCultistRobe");
		GameRegistry.registerItem(ConfigItems.itemLegsCultistRobe, "ItemLeggingsCultistRobe", "Thaumcraft");

		GameRegistry.addRecipe(new ItemStack(ConfigItems.itemHelmetCultistRobe), "A", 'A', new ItemStack(oldHelmetCultistRobes, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addRecipe(new ItemStack(ConfigItems.itemChestCultistRobe), "A", 'A', new ItemStack(oldChestCultistRobes, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addRecipe(new ItemStack(ConfigItems.itemLegsCultistRobe), "A", 'A', new ItemStack(oldLegsCultistRobes, 1, OreDictionary.WILDCARD_VALUE));

		oldHelmetVoidRobes = ConfigItems.itemHelmetVoidRobe;
		oldChestVoidRobes = ConfigItems.itemChestVoidRobe;
		oldLegsVoidRobes = ConfigItems.itemLegsVoidRobe;

		ConfigItems.itemHelmetVoidRobe = new ItemVoidRobeArmorTTC(ThaumcraftApi.armorMatVoid, 4, 0).setUnlocalizedName("ItemHelmetVoidRobe");
		GameRegistry.registerItem(ConfigItems.itemHelmetVoidRobe, "ItemHelmetVoidFortress", "Thaumcraft");
		ConfigItems.itemChestVoidRobe = new ItemVoidRobeArmorTTC(ThaumcraftApi.armorMatVoid, 4, 1).setUnlocalizedName("ItemChestplateVoidRobe");
		GameRegistry.registerItem(ConfigItems.itemChestVoidRobe, "ItemChestplateVoidFortress", "Thaumcraft");
		ConfigItems.itemLegsVoidRobe = new ItemVoidRobeArmorTTC(ThaumcraftApi.armorMatVoid, 4, 2).setUnlocalizedName("ItemLeggingsVoidRobe");
		GameRegistry.registerItem(ConfigItems.itemLegsVoidRobe, "ItemLeggingsVoidFortress", "Thaumcraft");

		GameRegistry.addRecipe(new ItemStack(ConfigItems.itemHelmetVoidRobe), "A", 'A', new ItemStack(oldHelmetVoidRobes, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addRecipe(new ItemStack(ConfigItems.itemChestVoidRobe), "A", 'A', new ItemStack(oldChestVoidRobes, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addRecipe(new ItemStack(ConfigItems.itemLegsVoidRobe), "A", 'A', new ItemStack(oldLegsVoidRobes, 1, OreDictionary.WILDCARD_VALUE));


		oldHelmetCultistLeaderPlate = ConfigItems.itemHelmetCultistLeaderPlate;
		oldChestCultistLeaderPlate = ConfigItems.itemChestCultistLeaderPlate;
		oldLegsCultistLeaderPlate = ConfigItems.itemLegsCultistLeaderPlate;

		ConfigItems.itemHelmetCultistLeaderPlate = new ItemCultistLeaderArmorTTC(ThaumcraftApi.armorMatThaumiumFortress, 4, 0).setUnlocalizedName("ItemHelmetCultistLeaderPlate");
		GameRegistry.registerItem(ConfigItems.itemHelmetCultistLeaderPlate, "ItemHelmetCultistLeaderPlate", "Thaumcraft");
		ConfigItems.itemChestCultistLeaderPlate = new ItemCultistLeaderArmorTTC(ThaumcraftApi.armorMatThaumiumFortress, 4, 1).setUnlocalizedName("ItemChestplateCultistLeaderPlate");
		GameRegistry.registerItem(ConfigItems.itemChestCultistLeaderPlate, "ItemChestplateCultistLeaderPlate", "Thaumcraft");
		ConfigItems.itemLegsCultistLeaderPlate = new ItemCultistLeaderArmorTTC(ThaumcraftApi.armorMatThaumiumFortress, 4, 2).setUnlocalizedName("ItemLeggingsCultistLeaderPlate");
		GameRegistry.registerItem(ConfigItems.itemLegsCultistLeaderPlate, "ItemLeggingsCultistLeaderPlate", "Thaumcraft");

		GameRegistry.addRecipe(new ItemStack(ConfigItems.itemHelmetCultistLeaderPlate), "A", 'A', new ItemStack(oldHelmetCultistLeaderPlate, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addRecipe(new ItemStack(ConfigItems.itemChestCultistLeaderPlate), "A", 'A', new ItemStack(oldChestCultistLeaderPlate, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addRecipe(new ItemStack(ConfigItems.itemLegsCultistLeaderPlate), "A", 'A', new ItemStack(oldLegsCultistLeaderPlate, 1, OreDictionary.WILDCARD_VALUE));

		oldHelmetCultistPlate = ConfigItems.itemHelmetCultistPlate;
		oldChestCultistPlate = ConfigItems.itemChestCultistPlate;
		oldLegsCultistPlate = ConfigItems.itemLegsCultistPlate;

		ConfigItems.itemHelmetCultistPlate = new ItemCultistPlateArmorTTC(ItemArmor.ArmorMaterial.IRON, 4, 0).setUnlocalizedName("ItemHelmetCultistPlate");
		GameRegistry.registerItem(ConfigItems.itemHelmetCultistPlate, "ItemHelmetCultistPlate", "Thaumcraft");
		ConfigItems.itemChestCultistPlate = new ItemCultistPlateArmorTTC(ItemArmor.ArmorMaterial.IRON, 4, 1).setUnlocalizedName("ItemChestplateCultistPlate");
		GameRegistry.registerItem(ConfigItems.itemChestCultistPlate, "ItemChestplateCultistPlate", "Thaumcraft");
		ConfigItems.itemLegsCultistPlate = new ItemCultistPlateArmorTTC(ItemArmor.ArmorMaterial.IRON, 4, 2).setUnlocalizedName("ItemLeggingsCultistPlate");
		GameRegistry.registerItem(ConfigItems.itemLegsCultistPlate, "ItemLeggingsCultistPlate", "Thaumcraft");

		GameRegistry.addRecipe(new ItemStack(ConfigItems.itemHelmetCultistPlate), "A", 'A', new ItemStack(oldHelmetCultistPlate, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addRecipe(new ItemStack(ConfigItems.itemChestCultistPlate), "A", 'A', new ItemStack(oldChestCultistPlate, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addRecipe(new ItemStack(ConfigItems.itemLegsCultistPlate), "A", 'A', new ItemStack(oldLegsCultistPlate, 1, OreDictionary.WILDCARD_VALUE));


		thaumiumShovel = new ItemThaumiumShovelTTC().setMaxDamage(ThaumiumEnhancedToolMaterial.getMaxUses());
		GameRegistry.registerItem(thaumiumShovel, thaumiumShovel.getUnlocalizedName());
		thaumiumPickaxe = new ItemThaumiumPickaxeTTC().setMaxDamage(ThaumiumEnhancedToolMaterial.getMaxUses());
		GameRegistry.registerItem(thaumiumPickaxe, thaumiumPickaxe.getUnlocalizedName());
		thaumiumHoe = new ItemThaumiumHoeTTC().setMaxDamage(ThaumiumEnhancedToolMaterial.getMaxUses());
		GameRegistry.registerItem(thaumiumHoe, thaumiumHoe.getUnlocalizedName());
		thaumiumScythe = new ItemThaumiumScytheTTC().setUnlocalizedName("thaumiumscythe");
		GameRegistry.registerItem(thaumiumScythe, thaumiumScythe.getUnlocalizedName());
		thaumiumChisel = new ItemThaumiumChiselTTC().setUnlocalizedName("thaumiumchisel").setMaxDamage(ThaumiumEnhancedToolMaterial.getMaxUses());
//		OreDictionary.registerOre("itemChisel", new ItemStack(thaumiumChisel, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.registerItem(thaumiumChisel, thaumiumChisel.getUnlocalizedName());
		thaumiumMace = new ItemThaumiumMaceTTC().setUnlocalizedName("thaumiummace");
		GameRegistry.registerItem(thaumiumMace, thaumiumMace.getUnlocalizedName());
		thaumiumJavelin = new ItemThaumiumJavelinTTC().setUnlocalizedName("thaumiumjavelin");
		GameRegistry.registerItem(thaumiumJavelin, thaumiumJavelin.getUnlocalizedName());
		thaumiumProPick = new ItemThaumiumProPickTTC().setUnlocalizedName("thaumiumpropick").setMaxDamage(ThaumiumEnhancedToolMaterial.getMaxUses() / 3);
		GameRegistry.registerItem(thaumiumProPick, thaumiumProPick.getUnlocalizedName());
		thaumiumHammer = new ItemThaumiumHammerTTC().setUnlocalizedName("thaumiumhammer");
		thaumiumHammer.setHarvestLevel("hammer", 3);
		GameRegistry.registerItem(thaumiumHammer, thaumiumHammer.getUnlocalizedName());
		OreDictionary.registerOre("itemHammer", new ItemStack(thaumiumHammer, 1, OreDictionary.WILDCARD_VALUE));
		thaumiumKnife = new ItemThaumiumKnifeTTC().setUnlocalizedName("thaumiumknife");
		GameRegistry.registerItem(thaumiumKnife, thaumiumKnife.getUnlocalizedName());
		OreDictionary.registerOre("itemKnife", new ItemStack(thaumiumKnife, 1, OreDictionary.WILDCARD_VALUE));
		thaumiumSaw = new ItemThaumiumSawTTC().setUnlocalizedName("thaumiumsaw").setMaxDamage(ThaumiumEnhancedToolMaterial.getMaxUses());
		GameRegistry.registerItem(thaumiumSaw, thaumiumSaw.getUnlocalizedName());
		OreDictionary.registerOre("itemSaw", new ItemStack(thaumiumSaw, 1, OreDictionary.WILDCARD_VALUE));
		
		thaumiumToolHead = new ItemMiscToolHeadTTC(ThaumiumEnhancedToolMaterial).setUnlocalizedName("thaumiumtoolhead");
		GameRegistry.registerItem(thaumiumToolHead, thaumiumToolHead.getUnlocalizedName());
		
		thaumiumSword = new ItemThaumiumSwordTTC();
		GameRegistry.registerItem(thaumiumSword, thaumiumSword.getUnlocalizedName());
		
		thaumiumSheet = new ItemMetalSheetTTC().setMetal("Thaumium", 200).setUnlocalizedName("thaumiumsheet");
		GameRegistry.registerItem(thaumiumSheet, thaumiumSheet.getUnlocalizedName());

		fortressHelmet = new ItemFortressArmorTTC(ArmorMetalsTTC.Thaumium, ThaumcraftApi.armorMatThaumiumFortress,4,0).setUnlocalizedName("ItemHelmetFortress");
		GameRegistry.registerItem(fortressHelmet, fortressHelmet.getUnlocalizedName());
		fortressChest = new ItemFortressArmorTTC(ArmorMetalsTTC.Thaumium, ThaumcraftApi.armorMatThaumiumFortress,4,1).setUnlocalizedName("ItemChestplateFortress");
		GameRegistry.registerItem(fortressChest, fortressChest.getUnlocalizedName());
		fortressGreaves = new ItemFortressArmorTTC(ArmorMetalsTTC.Thaumium, ThaumcraftApi.armorMatThaumiumFortress, 4,2).setUnlocalizedName("ItemLeggingsFortress");
		GameRegistry.registerItem(fortressGreaves, fortressGreaves.getUnlocalizedName());


		thaumiumSheet2x = new ItemMetalSheetTTC().setMetal("Thaumium", 400).setWeight(EnumWeight.HEAVY).setUnlocalizedName("thaumiumdoublesheet");
		GameRegistry.registerItem(thaumiumSheet2x, thaumiumSheet2x.getUnlocalizedName());

		roughShard = new ItemRoughShard().setUnlocalizedName("roughshard");
		GameRegistry.registerItem(roughShard, roughShard.getUnlocalizedName());

		shardDust = new ItemShardDust().setUnlocalizedName("sharddust");
		GameRegistry.registerItem(shardDust, shardDust.getUnlocalizedName());

		//magiclogs = new ItemLogsTTC().setUnlocalizedName("magiclogs");
		//GameRegistry.registerItem(magiclogs, magiclogs.getUnlocalizedName());

        //logsTTC = new ItemLogsTTC2().setUnlocalizedName("logs");
        //GameRegistry.registerItem(logsTTC, logsTTC.getUnlocalizedName());

        //singlePlank = new ItemPlankTTC().setUnlocalizedName("SinglePlank");
        //GameRegistry.registerItem(singlePlank, TFCItems.singlePlank.getUnlocalizedName());

		//TFCItems.singlePlank = singlePlank;



		debugstick = new ItemDebugStick();
		GameRegistry.registerItem(debugstick, debugstick.getUnlocalizedName());

		transmutedOre = new ItemTransmutedOre().setUnlocalizedName("transmutedore");
		GameRegistry.registerItem(transmutedOre, transmutedOre.getUnlocalizedName());



        greatwoodDoor = new ItemWoodDoor(17).setUnlocalizedName("Greatwood Door");
        silverwoodDoor = new ItemWoodDoor(18).setUnlocalizedName("Silverwood Door");

        Recipes.doors = ArrayUtils.addAll(Recipes.doors, new Item[]{greatwoodDoor, silverwoodDoor});
        for(int l = 17; l < Recipes.doors.length; l++)
            TFCFuelHandler.registerFuel(Recipes.doors[l], 300);
        GameRegistry.registerItem(greatwoodDoor, greatwoodDoor.getUnlocalizedName());
        GameRegistry.registerItem(silverwoodDoor, silverwoodDoor.getUnlocalizedName());

        lavaSeed = new ItemLavaSeed().setUnlocalizedName("lavaseed");
        GameRegistry.registerItem(lavaSeed, lavaSeed.getUnlocalizedName());

		initMetals();
		initFood();

		
		
		
	}
	public static void initMetals()
	{
		thaumium = new Metal("Thaumium", thaumiumUnshaped, thaumiumIngot);
		MetalRegistry.instance.addMetal(thaumium, Alloy.EnumTier.TierI);



		HeatRegistry.getInstance().addIndex(new HeatIndex(new ItemStack(thaumiumIngot, 1), ThaumiumRaw, new ItemStack(thaumiumUnshaped, 1)));
		HeatRegistry.getInstance().addIndex(new HeatIndex(new ItemStack(thaumiumUnshaped, 1), ThaumiumRaw, new ItemStack(thaumiumUnshaped, 1)));
		HeatRegistry.getInstance().addIndex(new HeatIndex(new ItemStack(thaumiumIngot2x, 1), ThaumiumRaw, new ItemStack(thaumiumUnshaped, 2, 0)));
		HeatRegistry.getInstance().addIndex(new HeatIndex(new ItemStack(thaumiumSheet, 1), ThaumiumRaw, new ItemStack(thaumiumUnshaped, 2, 0)));
		HeatRegistry.getInstance().addIndex(new HeatIndex(new ItemStack(thaumiumSheet2x, 1), ThaumiumRaw, new ItemStack(thaumiumUnshaped, 4, 0)));
		HeatRegistry.getInstance().addIndex(new HeatIndex(new ItemStack(thaumiumUnfinishedGreaves, 1, 0), ThaumiumRaw, new ItemStack(thaumiumUnshaped, 4)));
		HeatRegistry.getInstance().addIndex(new HeatIndex(new ItemStack(thaumiumUnfinishedGreaves, 1, 1), ThaumiumRaw, new ItemStack(thaumiumUnshaped, 6)));
		HeatRegistry.getInstance().addIndex(new HeatIndex(new ItemStack(thaumiumUnfinishedBoots, 1, 0), ThaumiumRaw, new ItemStack(thaumiumUnshaped, 2)));
		HeatRegistry.getInstance().addIndex(new HeatIndex(new ItemStack(thaumiumUnfinishedBoots, 1, 1), ThaumiumRaw, new ItemStack(thaumiumUnshaped, 4)));
		HeatRegistry.getInstance().addIndex(new HeatIndex(new ItemStack(thaumiumUnfinishedChest, 1, 0), ThaumiumRaw, new ItemStack(thaumiumUnshaped, 4)));
		HeatRegistry.getInstance().addIndex(new HeatIndex(new ItemStack(thaumiumUnfinishedChest, 1, 1), ThaumiumRaw, new ItemStack(thaumiumUnshaped, 8)));
		HeatRegistry.getInstance().addIndex(new HeatIndex(new ItemStack(thaumiumUnfinishedHelm, 1, 0), ThaumiumRaw, new ItemStack(thaumiumUnshaped, 2)));
		HeatRegistry.getInstance().addIndex(new HeatIndex(new ItemStack(thaumiumUnfinishedHelm, 1, 1), ThaumiumRaw, new ItemStack(thaumiumUnshaped, 4)));




	}
	public static void initFood()
	{
		rottenMeatRaw = new ItemRottenFlesh(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, false).setDecayRate(5F).setCanSmoke().setSmokeAbsorbMultiplier(1.0F).setUnlocalizedName("rottenmeat");
		GameRegistry.registerItem(rottenMeatRaw, rottenMeatRaw.getUnlocalizedName());
		HeatRegistry.getInstance().addIndex(new HeatIndex(new ItemStack(rottenMeatRaw, 1), 1, 1200, null));
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(rottenMeatRaw, 1), 0), new Object[]{new ItemStack(rottenMeatRaw, 1), new ItemStack(TFCItems.powder, 1, 9)});

        netherwartSeeds = new ItemCustomSeedsTTC(netherwartCropID).setUnlocalizedName("netherwartseeds");
        GameRegistry.registerItem(netherwartSeeds, netherwartSeeds.getUnlocalizedName());

        CropManager.getInstance().addIndex(new CropIndex(/*ID*/netherwartCropID, /*Name*/"netherwart", /*type*/2, /*time*/96, /*stages*/2, /*minGTemp*/18, /*minATemp*/12, /*nutrientUsage*/0.25f, netherwartSeeds).setOutput1(Items.nether_wart, 8));



        //netherwartSeeds = new ItemCustomSeeds()



	}
	
}
