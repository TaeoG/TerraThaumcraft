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

package taeo.terrathaumcraft.entity.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.bioxx.tfc.api.Entities.IAnimal;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import taeo.terrathaumcraft.utility.UtilsTTC;
import taeo.ttfcapi.utility.UtilsTAPI;
import thaumcraft.common.entities.ai.combat.AIOldestAttackableTargetSorter;
import thaumcraft.common.entities.golems.EntityGolemBase;








public class AINearestButcherTargetTTC
extends EntityAITarget
{
	EntityGolemBase theGolem;
	EntityLivingBase target;
	int targetChance;
	private final IEntitySelector entitySelector;
	private float targetDistance = 0.0F;


	private AIOldestAttackableTargetSorter theOldestAttackableTargetSorter;
	private AIMostFamiliarSorter theMostFamiliarSorter;


	public AINearestButcherTargetTTC(EntityGolemBase par1EntityLiving, int par4, boolean par5)
	{
		this(par1EntityLiving, 0.0F, par4, par5, false, (IEntitySelector)null);
	}

	public AINearestButcherTargetTTC(EntityGolemBase par1, float par3, int par4, boolean par5, boolean par6, IEntitySelector par7IEntitySelector)
	{
		super(par1, par5, par6);
		this.theGolem = par1;
		this.targetDistance = 0.0F;
		this.targetChance = par4;
		this.theOldestAttackableTargetSorter = new AIOldestAttackableTargetSorter(this, par1);
		this.entitySelector = par7IEntitySelector;
		setMutexBits(3);
	}




	public boolean shouldExecute()
	{
		this.targetDistance = this.theGolem.getRange();
		if ((this.targetChance > 0) && (this.taskOwner.getRNG().nextInt(this.targetChance) != 0))
		{
			return false;
		}
		//LogHelper.info("Golem is " + theGolem.ticksExisted + " ticks old");
		if(theGolem.ticksExisted < 200)
		{
			return false;
		}

		//Get a list of all Living entities in range
		List var5 = this.taskOwner.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, this.taskOwner.boundingBox.expand(this.targetDistance, 4.0D, this.targetDistance), this.entitySelector);

		List<IAnimal> males = new ArrayList<IAnimal>();
		List<IAnimal> females = new ArrayList<IAnimal>();
		//Split the TFC animals out of the entity list
		UtilsTAPI.splitAnimalList(var5, males, females);
		//Sort them by familiarity
		Collections.sort(males, new AIMostFamiliarSorter());
		Collections.sort(females, new AIMostFamiliarSorter());
		//Sort the vanilla animals by age
		Collections.sort(var5, this.theOldestAttackableTargetSorter);
		Iterator vanillaListIter = var5.iterator();

		//For every entity in the vanilla list
		while (vanillaListIter.hasNext())
		{
			
			Entity vanillaAnimal = (Entity)vanillaListIter.next();
			EntityLivingBase vanillaAnimalBase = (EntityLivingBase)vanillaAnimal;

			if (this.theGolem.isValidTarget(vanillaAnimal))
			{
				//set it as a target if it's valid
				this.target = vanillaAnimalBase;
				//get a list of all entities in range of the same type
				List matchingAnimals = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.target.getClass(), this.taskOwner.boundingBox.expand(this.targetDistance, 4.0D, this.targetDistance), this.entitySelector);


				Iterator matchingAnimalsIter = matchingAnimals.iterator();
				int count = 0;
				while (matchingAnimalsIter.hasNext())
				{
					//count how many exist in range
					Entity matchingAnimal = (Entity)matchingAnimalsIter.next();
					if (this.theGolem.isValidTarget(matchingAnimal)) { count++;
					}
				}
				if (count > 2) {
					//if there is more than 2 return true
					return true;
				}
			}
		}

		if(males != null)
		{
			Iterator malesIter = males.iterator();
			while (malesIter.hasNext()) 
			{
				EntityLivingBase potentialTarget = (EntityLivingBase)malesIter.next();
				if (theGolem.isValidTarget(potentialTarget)) {
					target = potentialTarget;
					List matchingSpecies = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.target.getClass(), this.taskOwner.boundingBox.expand(this.targetDistance, 4.0D, this.targetDistance), this.entitySelector);
					List matchingSpeciesMale = new ArrayList();
					List matchingSpeciesFemale = new ArrayList();
					
					UtilsTAPI.splitAnimalList(matchingSpecies, matchingSpeciesMale, matchingSpeciesFemale);
					//Collections.sort(matchingSpeciesMale, new AIMostFamiliarSorter());
					Iterator matchingSpeciesIter = matchingSpeciesMale.iterator();
					int count = 0;
					while(matchingSpeciesIter.hasNext())
					{
						Entity matchingAnimal = (Entity) matchingSpeciesIter.next();
						if(theGolem.isValidTarget(matchingAnimal))
						{
							count++;
						}
					}
					if(count >=2)
					{
						return true;
					}
				} 
			}

		}
		if(females != null)
		{
			Iterator femalesIter = females.iterator();
			while (femalesIter.hasNext()) 
			{
				EntityLivingBase potentialTarget = (EntityLivingBase)femalesIter.next();
				if (theGolem.isValidTarget(potentialTarget)) {
					target = potentialTarget;
					List matchingSpecies = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.target.getClass(), this.taskOwner.boundingBox.expand(this.targetDistance, 4.0D, this.targetDistance), this.entitySelector);
					List matchingSpeciesMale = new ArrayList();
					List matchingSpeciesFemale = new ArrayList();
					
					UtilsTAPI.splitAnimalList(matchingSpecies, matchingSpeciesMale, matchingSpeciesFemale);
					//Collections.sort(matchingSpeciesFemale, new AIMostFamiliarSorter());
					Iterator matchingSpeciesIter = matchingSpeciesFemale.iterator();
					int count = 0;
					while(matchingSpeciesIter.hasNext())
					{
						Entity matchingAnimal = (Entity) matchingSpeciesIter.next();
						if(theGolem.isValidTarget(matchingAnimal))
						{
							count++;
						}
					}
					if(count >=2)
					{
						return true;
					}
				} 
			}
		}
		
		

		//if there are no valid targets in range, or there are less than 2 of all valid targets, return false
		return false;
	}






	public void startExecuting()
	{
		this.taskOwner.setAttackTarget(this.target);
		super.startExecuting();
	}
}