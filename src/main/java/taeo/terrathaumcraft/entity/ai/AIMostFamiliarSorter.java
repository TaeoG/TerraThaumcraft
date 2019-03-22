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

import java.util.Comparator;

import com.bioxx.tfc.api.Entities.IAnimal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAITarget;

public class AIMostFamiliarSorter implements Comparator{

	//private Entity theTarget;
	//final EntityAITarget theAI;
	public AIMostFamiliarSorter(){}
	public AIMostFamiliarSorter(EntityAITarget ai, Entity target)
	{
		//theTarget = target;
		//theAI = ai;
	}
	public int compareFamiliarity(Entity animalA, Entity animalB)
	{
		
		
		int aFam =((IAnimal) animalA).getFamiliarity();
		int bFam = ((IAnimal)animalB).getFamiliarity();
		return aFam < bFam ? -1 : aFam > bFam ? 1 : 0;
		
	}
	@Override
	public int compare(Object arg0, Object arg1) {
		return compareFamiliarity((Entity)arg0, (Entity)arg1);
	}

}
