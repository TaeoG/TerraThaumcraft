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

import java.lang.reflect.Field;
import java.util.Random;

import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.utility.LogHelper;
import thaumcraft.api.aspects.AspectList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Crafting.AnvilRecipe;
import com.bioxx.tfc.api.Crafting.AnvilReq;
import com.bioxx.tfc.api.Crafting.PlanRecipe;

public class MagicAnvilRecipe extends AnvilRecipe {
	ItemStack result;
	String plan = "";
	ItemStack input1;
	ItemStack input2;
	boolean flux;
	int craftingValue;
	int anvilreq;
	boolean inheritsDamage;
	public String research = "";
	private static int craftingBoundDefault = 50;
	public AspectList aspects;
			
	public MagicAnvilRecipe(ItemStack inA, ItemStack inB, String inplan, ItemStack out) {
		super(inA, inB, inplan, AnvilReq.WROUGHTIRON, out);
		//super(inA, inB, inplan, calculateCraftingValue(inA, out), false, ReferenceTTC.MAGIC_TIER_LEVEL, out);
		super.skillsList.remove(0);
		result = out;
		input1 = inA;
		input2 = inB;
		flux = false;
		anvilreq = ReferenceTTC.MAGIC_TIER_LEVEL;
		setAnvilReqinSuper();
		plan = inplan;
		craftingValue = super.getCraftingValue();
		aspects = null;
		
	}
	private void setAnvilReqinSuper()
	{
		try {
			Field superAR = AnvilRecipe.class.getDeclaredField("anvilreq");
			superAR.setAccessible(true);
			superAR.set(this, ReferenceTTC.MAGIC_TIER_LEVEL);
		} catch (Exception e) {
			LogHelper.error(ReferenceTTC.MOD_NAME,"Unable to change the anvil requirement in recipe");
			e.printStackTrace();
		} 
	}
	public int getAnvilreq()
	{
		return anvilreq;
	}
	public MagicAnvilRecipe(ItemStack inA, ItemStack inB, String plan, ItemStack out, AspectList aspects, String research)
	{
		super(inA, inB, plan, AnvilReq.WROUGHTIRON, out);
		//super(inA, inB, plan, calculateCraftingValue(inA, out), false, ReferenceTTC.MAGIC_TIER_LEVEL, out);
		result = out;
		input1 = inA;
		input2 = inB;
		flux = false;
		anvilreq = ReferenceTTC.MAGIC_TIER_LEVEL;
		setAnvilReqinSuper();
		this.plan = plan;
		craftingValue = super.getCraftingValue();
		this.aspects = aspects;
		this.research = research;
	}
	public MagicAnvilRecipe(ItemStack inA, ItemStack inB, String craftingPlan,
			int itemCraftingValue) {
		super(inA, inB, craftingPlan, itemCraftingValue, false, ReferenceTTC.MAGIC_TIER_LEVEL, null);
		input1 = inA;
		input2 = inB;
		flux = false;
		anvilreq = ReferenceTTC.MAGIC_TIER_LEVEL;
		setAnvilReqinSuper();
		plan = craftingPlan;
		craftingValue = itemCraftingValue;
	}
	public MagicAnvilRecipe(ItemStack inA, ItemStack inB, ItemStack out)
	{
		super(inA, inB, "", 0, true, ReferenceTTC.MAGIC_TIER_LEVEL, out);
		input1 = inA;
		input2 = inB;
		flux = true;
		anvilreq = ReferenceTTC.MAGIC_TIER_LEVEL;
		setAnvilReqinSuper();
		inheritsDamage=false;
	}
	public MagicAnvilRecipe(ItemStack inA, ItemStack inB, boolean flux, ItemStack out)
	{
		super(inA, inB, "", 0, true, ReferenceTTC.MAGIC_TIER_LEVEL, out);
		input1 = inA;
		input2 = inB;
		this.flux = flux;
		anvilreq = ReferenceTTC.MAGIC_TIER_LEVEL;
		setAnvilReqinSuper();
		inheritsDamage=false;
	}
	
	
	
	
	
	
	/*public AnvilRecipe toAnvilRecipe()
	{
		try {
			return (AnvilRecipe) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}*/
	@Override
	public int getCraftingValue()
	{
		return this.craftingValue;
	}
	
	private static int calculateCraftingValue(ItemStack inA, ItemStack out)
	{
		int craftingValue = 70 + new Random(TFC_Core.getSuperSeed(AnvilManager.world)+(inA != null ? Item.getIdFromItem(inA.getItem()) : 0) + (out != null ? Item.getIdFromItem(out.getItem()) : 0)).nextInt(craftingBoundDefault);
		return craftingValue;
	}
	public boolean matches(MagicAnvilRecipe A)
	{   
		if(     areItemStacksEqual(input1, A.getInput1()) && 
				areItemStacksEqual(input2, A.getInput2()) &&
				plan.equals(A.getPlan()) &&
				A.getAnvilreq() == ReferenceTTC.MAGIC_TIER_LEVEL)
		{
			if(this.flux && !A.flux)
				return false;
			return true;
		}
		return false;
	}
	private boolean areItemStacksEqual(ItemStack is1, ItemStack is2)
	{
		if(is1 == null && is2 == null)
			return true;

		if((is1 == null && is2 != null) || (is1 != null && is2 == null)) 
			return false;

		if(is1.getItem() != is2.getItem())
			return false;

		if(is1.getItemDamage() != 32767 && is1.getItemDamage() != is2.getItemDamage())
			return false;

		return true;
	}
	public boolean isComplete(MagicAnvilManager am, MagicAnvilRecipe A, int[] rules)
	{
		LogHelper.info(ReferenceTTC.MOD_NAME,"Magic Anvil Recipe checked for completeness");
		PlanRecipe pr = am.getPlan(A.plan);
		if(     areItemStacksEqual(input1, A.input1) && 
				areItemStacksEqual(input2, A.input2) &&
				plan.equals(A.plan) &&
				pr.rules[0].matches(rules, 0) && pr.rules[1].matches(rules, 1) && pr.rules[2].matches(rules, 2) && 
				craftingValue == A.craftingValue)// && A.anvilreq == ReferenceTTC.MAGIC_TIER_LEVEL)//AnvilReq.matches(anvilreq, A.anvilreq))
			if(this.flux && A.flux)
				return true;
			else if (!this.flux)
				return true;
		return false;
	}
//	public AnvilRecipe toAnvilRecipe()
//	{
//		AnvilRecipe out = new AnvilRecipe(input1, input2, plan,calculateCraftingValue(input1, result),false, ReferenceTTC.MAGIC_TIER_LEVEL,result);
//		return out;
//	}

	public String toString()
	{
		String i1;
		String i2;
		String o;
		try
		{
			i1 = input1.getDisplayName();
		}
		catch(Exception e)
		{
			i1 = "null";
		}
		try
		{
			i2 = input2.getDisplayName();
		}
		catch(Exception e)
		{
			i2 = "null";
		}
		try
		{
			o = result.getDisplayName();
		}
		catch(Exception e)
		{
			o = "null";
		}
		
		
		
		return i1 + " + " + i2 + " = " + o + " :Plan " + plan;
	}

}
