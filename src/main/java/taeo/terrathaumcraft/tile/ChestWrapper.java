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

package taeo.terrathaumcraft.tile;

import com.bioxx.tfc.TileEntities.TEChest;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

import java.util.HashMap;

public class ChestWrapper extends TileEntityChest {

    private TEChest wrappedChest;
    private HashMap<Item, ItemStack> conversionMap = new HashMap<>();

    public ChestWrapper(TEChest chest)
    {
        wrappedChest = chest;
        initConversionMap();
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item)
    {
        if(item.getItem() == null)
        {

        }
        if (item != null && item.stackSize < wrappedChest.getInventoryStackLimit())
        {
            if(slot > 17)
            {
                slot = findFirstAvailableSlot();
            }
            if(conversionMap.containsKey(item.getItem()))
            {
                ItemStack outStack = conversionMap.get(item.getItem()).copy();
                outStack.stackSize = item.stackSize;
                wrappedChest.setInventorySlotContents(slot%18,outStack);
            }
            else
            {
                wrappedChest.setInventorySlotContents(slot%18, item);
            }


        }

    }
    private int findFirstAvailableSlot()
    {
        for(int i=0; i<wrappedChest.getSizeInventory(); i++)
        {
            if(wrappedChest.getStackInSlot(i) == null)
            {
                return i;
            }
        }
        return 0;
    }

    private void initConversionMap()
    {

        conversionMap.put(Items.iron_ingot, new ItemStack(TFCItems.wroughtIronIngot));
        conversionMap.put(Items.gold_ingot, new ItemStack(TFCItems.goldIngot));
        conversionMap.put(Items.redstone, new ItemStack(TFCItems.oreChunk, 1, 27));
        conversionMap.put(Items.dye, new ItemStack(TFCItems.dye, 1, 4));
        conversionMap.put(Items.diamond, new ItemStack(TFCItems.gemDiamond));
        conversionMap.put(Items.coal, new ItemStack(TFCItems.coal));
        conversionMap.put(Items.bread, new ItemStack(TFCItems.fertilizer));
        conversionMap.put(Items.iron_pickaxe, new ItemStack(TFCItems.wroughtIronPick));
        conversionMap.put(Item.getItemFromBlock(Blocks.rail), new ItemStack(Item.getItemFromBlock(Blocks.rail)));
        conversionMap.put(Items.melon_seeds, new ItemStack(TFCItems.seedsSugarcane));
        conversionMap.put(Items.pumpkin_seeds, new ItemStack(TFCItems.seedsSquash));
        conversionMap.put(Items.saddle, new ItemStack(Items.saddle));
        conversionMap.put(Items.iron_horse_armor, new ItemStack(Items.iron_horse_armor));
        conversionMap.put(Items.diamond_horse_armor, new ItemStack(Items.diamond_horse_armor));
        conversionMap.put(Items.golden_horse_armor, new ItemStack(Items.golden_horse_armor));
        conversionMap.put(Items.emerald, new ItemStack(TFCItems.gemEmerald));
        conversionMap.put(Items.bone, new ItemStack(Items.bone));
        conversionMap.put(Items.rotten_flesh, new ItemStack(Items.rotten_flesh));
        conversionMap.put(Items.arrow, new ItemStack(TFCItems.arrow));
        conversionMap.put(Items.ender_pearl, new ItemStack(Items.ender_pearl));
        conversionMap.put(Items.apple, new ItemStack(TFCItems.fruitTreeSapling));
        conversionMap.put(Items.iron_sword, new ItemStack(TFCItems.wroughtIronSword));
        conversionMap.put(Items.iron_chestplate, new ItemStack(TFCItems.wroughtIronChestplate));
        conversionMap.put(Items.iron_helmet, new ItemStack(TFCItems.wroughtIronHelmet));
        conversionMap.put(Items.iron_leggings, new ItemStack(TFCItems.wroughtIronGreaves));
        conversionMap.put(Items.iron_boots, new ItemStack(TFCItems.wroughtIronBoots));
        conversionMap.put(Items.golden_apple, new ItemStack(TFCItems.fruitTreeSapling, 1, 3));
        conversionMap.put(Items.book, new ItemStack(Items.book));
        conversionMap.put(Items.paper, new ItemStack(Items.paper));
        conversionMap.put(Items.map, new ItemStack(Items.map));
        conversionMap.put(Items.compass, new ItemStack(Items.compass));
        conversionMap.put(Item.getItemFromBlock(Blocks.obsidian), new ItemStack(Item.getItemFromBlock(Blocks.obsidian)));
        conversionMap.put(Item.getItemFromBlock(Blocks.sapling), new ItemStack(Item.getItemFromBlock(TFCBlocks.sapling), 1, 15));
        conversionMap.put(Items.stick, new ItemStack(TFCItems.stick));
        conversionMap.put(Item.getItemFromBlock(Blocks.planks), new ItemStack(Item.getItemFromBlock(TFCBlocks.planks), 1, 15));
        conversionMap.put(Item.getItemFromBlock(Blocks.log), new ItemStack(TFCItems.logs, 1, 15));
        conversionMap.put(Items.stone_axe, new ItemStack(TFCItems.igExAxe));
        conversionMap.put(Items.wooden_axe, new ItemStack(TFCItems.sedAxe));
        conversionMap.put(Items.stone_pickaxe, new ItemStack(TFCItems.copperPick));
        conversionMap.put(Items.wooden_pickaxe, new ItemStack(TFCItems.proPickCopper));
        conversionMap.put(Item.getItemFromBlock(Blocks.log2), new ItemStack(TFCItems.logs, 1, 16));
        conversionMap.put(Items.wheat, new ItemStack(TFCItems.seedsWheat));
        conversionMap.put(Items.gunpowder, new ItemStack(Items.gunpowder));
        conversionMap.put(Items.string, new ItemStack(Items.string));
        conversionMap.put(Items.bucket, new ItemStack(TFCItems.woodenBucketEmpty));
        conversionMap.put(Items.record_13, new ItemStack(Items.record_13));
        conversionMap.put(Items.record_cat, new ItemStack(Items.record_cat));
        conversionMap.put(Items.name_tag, new ItemStack(Items.name_tag));
        //TODO add actual thaumcraft loot (thaumium swords, etc)
}

}
