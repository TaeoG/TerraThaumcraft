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

package taeo.terrathaumcraft.item;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import taeo.terrathaumcraft.init.TTCItems;
import net.minecraft.item.Item;

import com.bioxx.tfc.Core.Recipes;

public class ItemSetup extends TTCItems{

	public static void Setup()
	{
		
	}
	public static void addToolstoTFCRecipeArray()
	{
        OreDictionary.registerOre("itemAxe", new ItemStack(thaumiumAxe, 1, OreDictionary.WILDCARD_VALUE));
        //TODO find how many of these edits are still necessary
		ArrayList<Item> Axes = new ArrayList<Item>(Arrays.asList(Recipes.axes));
		Axes.add(thaumiumAxe);
		Recipes.axes = Axes.toArray(new Item[Axes.size()]);
		
		ArrayList<Item> Chisels = new ArrayList<Item>(Arrays.asList(Recipes.chisels));
		Chisels.add(thaumiumChisel);
		Recipes.chisels = Chisels.toArray(new Item[Chisels.size()]);
		
		ArrayList<Item> Saws = new ArrayList<Item>(Arrays.asList(Recipes.saws));
		Saws.add(thaumiumSaw);
		Recipes.saws = Saws.toArray(new Item[Saws.size()]);
		
		ArrayList<Item> Scythes = new ArrayList<Item>(Arrays.asList(Recipes.scythes));
		Scythes.add(thaumiumScythe);
		Recipes.scythes = Scythes.toArray(new Item[Scythes.size()]);

		ArrayList<Item> Knives = new ArrayList<Item>(Arrays.asList(Recipes.knives));
		Knives.add(thaumiumKnife);
		Recipes.knives = Knives.toArray(new Item[Knives.size()]);
		
		ArrayList<Item> Hammers = new ArrayList<Item>(Arrays.asList(Recipes.hammers));
		Hammers.add(thaumiumHammer);
		Recipes.hammers = Hammers.toArray(new Item[Hammers.size()]);
	}
}
