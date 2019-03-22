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

package taeo.terrathaumcraft.item.equipment;

import java.lang.reflect.Constructor;

import com.bioxx.tfc.api.Armor;

public class ArmorMetalsTTC{

	public static Armor Thaumium = armorhack(11, 800, 800, 528, "Thaumium");	
	
	private static Armor armorhack(int id, int ARP, int ARS, int ARC, String material)
	{
		try{
		Constructor<Armor> constructor;
		constructor = (Constructor<Armor>) Armor.class.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		Thaumium = constructor.newInstance(id, ARP, ARS, ARC, "Thaumium");
		return Thaumium;}
		catch (Exception e)
		{
			System.out.println(e);
			System.exit(0);
			return null;
		}
		
	}

}
