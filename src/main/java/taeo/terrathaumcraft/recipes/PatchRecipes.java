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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.init.Blocks;

import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.utility.LogHelper;
import thaumcraft.api.wands.WandTriggerRegistry;

public class PatchRecipes {

	private static HashMap<String,HashMap<List,List>> wandTriggersRegistry;
	private static Field wandTriggers;
	public static void patchWandTriggers()
	{
		try {
			wandTriggers = WandTriggerRegistry.class.getDeclaredField("triggers");
			wandTriggers.setAccessible(true);
			wandTriggersRegistry = (HashMap<String, HashMap<List, List>>) wandTriggers.get(null);
		} catch (NoSuchFieldException e) {
			LogHelper.error(ReferenceTTC.MOD_NAME,"'triggers' in WandTriggerRegistry does not exist");
			e.printStackTrace();
		} catch (SecurityException e) {
			LogHelper.error(ReferenceTTC.MOD_NAME,"Security settings prohibit reflection");
			e.printStackTrace();
		} catch (Exception e) {
			LogHelper.error(ReferenceTTC.MOD_NAME,"Unable to access wand Triggers");
			e.printStackTrace();
		}
		Object[][] oldTriggers = new Object[][] {{Blocks.cauldron, -1}};//, {Blocks.bookshelf, 0}};

		HashMap<List,List> thaumcraftTriggers = wandTriggersRegistry.get("Thaumcraft");
		if(thaumcraftTriggers != null)
		{
			for(Object[] block : oldTriggers)
			{
				List blockTrigger = thaumcraftTriggers.get(Arrays.asList(block));
				if(blockTrigger != null)
				{
					thaumcraftTriggers.remove(Arrays.asList(block));
				}
			}
			
			wandTriggersRegistry.put("Thaumcraft", thaumcraftTriggers);

			try {
				wandTriggers.set(null, wandTriggersRegistry);
			} catch (Exception e) {
				LogHelper.error(ReferenceTTC.MOD_NAME,"Unable to modify wand Triggers");
				e.printStackTrace();
			}
			//System.out.println(TerraThaumcraftMod.wandManager.toString());


		}
			
	}
}
