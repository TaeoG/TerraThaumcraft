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

import com.bioxx.tfc.Blocks.Vanilla.BlockCustomDoor;
import com.bioxx.tfc.api.TFCBlocks;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.init.Blocks;
import org.apache.commons.lang3.ArrayUtils;
import taeo.terrathaumcraft.block.*;
import taeo.terrathaumcraft.block.BlockCrucible;
import taeo.terrathaumcraft.block.Foliage.*;
import taeo.terrathaumcraft.item.itemblock.*;
import taeo.terrathaumcraft.reference.Names;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import net.minecraft.block.Block;
import thaumcraft.common.config.ConfigBlocks;

public class TTCBlocks {

	public static Block blockCrucible;
	public static Block blockThaumiumAnvil;
	public static Block blockMagicOre;
	public static Block blockMagicTrunk;



	//public static ItemBlock itemCrucible;
	public static Block blockMagicLeaves;

    public static Block blockMagicLog;
    public static Block blockMagicSapling;

	//public static Block fence;
	//public static Block fence2;
	//public static Block fenceGate2;

	//public static Block woodSupportV2;
	//public static Block woodSupportH2;

	//public static Block armorStand2;
	//public static Block planks2;

	//public static Block loom;
	//public static Block magicBarrel;
    //public static Block toolRack;


    public static Block soulsand;
    public static Block tilledsoulsand;
    public static Block crops;
    public static Block worldItem;
    //public static Block chest;
    public static Block[] magicDoors;

	public static Block blockMetalDevice;

    public static int chestRenderId;
	public static int anvilRenderID;
	public static int magicOreRenderID;
	public static int cropRenderId;
	public static int tilledsoulsandRenderId;
	public static int magicBarrelRenderId;




	public static void init()
	{
		blockMetalDevice = new BlockMetalDeviceTTC().setBlockName("blockMetalDevice");
		GameRegistry.registerBlock(blockMetalDevice, ItemMetalDeviceTTC.class, "blockMetalDeviceTTC");
		ConfigBlocks.blockMetalDevice = blockMetalDevice;

		blockCrucible = new BlockCrucible().setBlockName("Crucible");
		GameRegistry.registerBlock(blockCrucible, ItemCrucibleTTC.class, "crucible");
		
		
		blockThaumiumAnvil = new BlockThaumiumAnvil().setBlockName(ReferenceTTC.MOD_ID + ":thaumiumanvil").setHardness(3).setResistance(100F);
		GameRegistry.registerBlock(blockThaumiumAnvil, ItemAnvilTTC.class, "thaumiumanvil");

		blockMagicOre = new BlockMagicOre().setBlockName(ReferenceTTC.MOD_ID + ":" + Names.Blocks.MAGIC_ORE);
		GameRegistry.registerBlock(blockMagicOre, ItemMagicOre.class, Names.Blocks.MAGIC_ORE);

		blockMagicTrunk = new BlockMagicTrunk().setBlockName(ReferenceTTC.MOD_ID + ":" + "magictrunk");
		GameRegistry.registerBlock(blockMagicTrunk, ItemMagicLog.class, "magictrunk");

        blockMagicLog = new BlockMagicLog().setBlockName(ReferenceTTC.MOD_ID + ":" + "magiclog");
        GameRegistry.registerBlock(blockMagicLog, ItemMagicLog.class, "magiclog");

		blockMagicLeaves = new BlockMagicLeaves().setBlockName(ReferenceTTC.MOD_ID + ":" + "magicleaves");
		GameRegistry.registerBlock(blockMagicLeaves, ItemMagicLeaves.class,"magicleaves");

        blockMagicSapling = new BlockMagicSapling().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("magicsapling");
        GameRegistry.registerBlock(blockMagicSapling, ItemMagicSapling.class, "magicsapling");

        soulsand = new BlockSoulSand().setHardness(2F).setStepSound(Block.soundTypeSand).setBlockName("hellsand").setBlockTextureName("soul_sand");
        GameRegistry.registerBlock(soulsand, soulsand.getUnlocalizedName());

        tilledsoulsand = new BlockSoulFarmland().setHardness(2F).setStepSound(Block.soundTypeSand).setBlockName("soulfarmland");
        GameRegistry.registerBlock(tilledsoulsand, tilledsoulsand.getUnlocalizedName());

        worldItem = new BlockWorldItemTTC().setHardness(0.05F).setResistance(1F).setBlockName("worlditem");
        GameRegistry.registerBlock(worldItem,worldItem.getUnlocalizedName());

        crops = new BlockCropTTC().setHardness(0.3F).setBlockName("cropsTTC");
        GameRegistry.registerBlock(crops, crops.getUnlocalizedName());

/*
		fence = new BlockTFCFenceE("Fence", Material.wood).setBlockName("FenceTFC").setHardness(2);
		fence2 = new BlockTFCFence2E("Fence2", Material.wood).setBlockName("FenceTFC").setHardness(2);
		GameRegistry.registerBlock(fence,ItemFence.class, "fence");
		GameRegistry.registerBlock(fence2,ItemFence2E.class, "fence2");

		fenceGate2 = new BlockFenceGate2E().setBlockName("FenceGateTFC").setHardness(2);
		GameRegistry.registerBlock(fenceGate2, ItemFenceGate2E.class, "FenceGate2");

		woodSupportV2 = new BlockWoodSupport2E(Material.wood).setBlockName("WoodSupportV2").setHardness(0.5F).setResistance(1F);
		woodSupportH2 = new BlockWoodSupport2E(Material.wood).setBlockName("WoodSupportH2").setHardness(0.5F).setResistance(1F);
		GameRegistry.registerBlock(woodSupportV2, ItemWoodSupport2E.class, "WoodSupportV2");
		GameRegistry.registerBlock(woodSupportH2, ItemWoodSupport2E.class, "WoodSupportH2");

		magicBarrel = new BlockMagicBarrel().setBlockName("Barrel").setHardness(2);
		GameRegistry.registerBlock(magicBarrel, ItemMagicBarrels.class, "Barrel");
		Blocks.fire.setFireInfo(magicBarrel, 5, 20);

		armorStand2 = new BlockStand2E().setBlockName("ArmourStand").setHardness(2);
		GameRegistry.registerBlock(armorStand2, ItemArmorStand2E.class, "ArmourStand2");

		planks2 = new BlockPlanks2E(Material.wood).setHardness(4.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("wood2");
		GameRegistry.registerBlock(planks2, ItemCustomWood2E.class, "planks2");

		loom = new BlockLoomTTC().setBlockName("Loom").setHardness(2);
		GameRegistry.registerBlock(loom, ItemLoomsTTC.class, "Loom");

        toolRack = new BlockToolRackTTC().setHardness(3F).setBlockName("Toolrack");
        GameRegistry.registerBlock(toolRack, ItemToolRackTTC.class, "ToolRack");

        TFCBlocks.woodConstruct = new BlockWoodConstructTTC().setHardness(4F).setStepSound(Block.soundTypeWood).setBlockName("WoodConstruct");
        GameRegistry.registerBlock(TFCBlocks.woodConstruct, "WoodConstruct");

        chest = new BlockChestTTC().setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("Chest");

        GameRegistry.registerBlock(chest, ItemChestTTC.class, "Chest TFC");
        TFCBlocks.chest = chest;

        TFCBlocks.fence = fence;
		TFCBlocks.fence2 = fence2;
		TFCBlocks.fenceGate2 = fenceGate2;
		TFCBlocks.woodSupportV2 = woodSupportV2;
		TFCBlocks.woodSupportH2 = woodSupportH2;
		TFCBlocks.armorStand2 = armorStand2;
		TFCBlocks.planks2 = planks2;
		TFCBlocks.loom = loom;
        TFCBlocks.toolRack = toolRack;
		*/

        magicDoors = new Block[2];
        magicDoors[0] = new BlockCustomDoor(34).setBlockName("Door Greatwood");
        magicDoors[1] = new BlockCustomDoor(36).setBlockName("Door Silverwood");

        for(int i = 9; i < magicDoors.length; i++)
        {
            Blocks.fire.setFireInfo(magicDoors[i], 5, 20);
        }
        for (int i=0; i < magicDoors.length; i++)
            GameRegistry.registerBlock(magicDoors[i], "Door" + magicDoors[i].getUnlocalizedName());

        TFCBlocks.doors = ArrayUtils.addAll(TFCBlocks.doors, magicDoors);








    }
}
