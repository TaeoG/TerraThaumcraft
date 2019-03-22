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

package taeo.terrathaumcraft.crafting;

//import java.util.List;

import net.minecraft.item.ItemStack;

import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Crafting.AnvilRecipe;
import com.bioxx.tfc.api.Crafting.PlanRecipe;
import java.util.List;

public class MagicAnvilManager{

	private List<AnvilRecipe> recipes;
	private List<AnvilRecipe> recipesWeld;
	private static final MagicAnvilManager instance = new MagicAnvilManager();
	public static final MagicAnvilManager getInstance()
	{
		return instance;
	}
	
	public MagicAnvilManager()
	{
		recipes = AnvilManager.getInstance().getRecipeList();
		recipesWeld = AnvilManager.getInstance().getWeldRecipeList();
	}
	
	public MagicAnvilRecipe findMatchingRecipe(MagicAnvilRecipe recipe)
	{
		
		for (int k = 0; k < recipes.size(); k++)
		{
			AnvilRecipe irecipe = recipes.get(k);
			if (irecipe != null && recipe.matches(irecipe) && irecipe instanceof MagicAnvilRecipe)
				return (MagicAnvilRecipe)irecipe;
		}

		return null;
	}
	public Object[] findCompleteRecipe(MagicAnvilRecipe recipe, int[] rules)
	{
		for (int k = 0; k < recipes.size(); k++)
		{
			AnvilRecipe irecipe = recipes.get(k);
			if(irecipe instanceof MagicAnvilRecipe)
			{
				MagicAnvilRecipe mrecipe = (MagicAnvilRecipe) irecipe;
				if (mrecipe != null && mrecipe.isComplete(instance, recipe, rules))
					return new Object[] {mrecipe, mrecipe.getCraftingResult(recipe.input1)};
			}
		}

		return null;
	}
	public AnvilRecipe findMatchingWeldRecipe(MagicAnvilRecipe recipe)
	{
		for (int k = 0; k < recipesWeld.size(); k++)
		{
			AnvilRecipe irecipe = recipesWeld.get(k);
			if (irecipe instanceof MagicAnvilRecipe)
			{
				MagicAnvilRecipe mrecipe = (MagicAnvilRecipe) irecipe;
				if (mrecipe != null && mrecipe.matches(recipe))
					return mrecipe;
			}
			
		}

		return null;
	}
	public ItemStack findCompleteWeldRecipe(MagicAnvilRecipe recipe)
	{
		for (int k = 0; k < recipesWeld.size(); k++)
		{
			AnvilRecipe irecipe = recipesWeld.get(k);
			if(irecipe instanceof MagicAnvilRecipe)
			{
				MagicAnvilRecipe mrecipe = (MagicAnvilRecipe) irecipe;
				if (mrecipe != null && mrecipe.matches(recipe))
					return mrecipe.getCraftingResult(recipe.input1);
			}
			
		}

		return null;
	}
	
	
	
	
	public PlanRecipe getPlan(String s)
	{
		return AnvilManager.getInstance().getPlan(s);
	}
}
