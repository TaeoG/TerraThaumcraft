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

package taeo.terrathaumcraft.asm;

import scala.actors.threadpool.Arrays;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TTCDummy extends cpw.mods.fml.common.DummyModContainer{
	
	public TTCDummy()
	{
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = ReferenceTTC.MOD_ID + "Core";
		meta.name = ReferenceTTC.MOD_NAME + "Core";
		meta.version = "@VERSION@";
		meta.credits = "wat";
		meta.authorList = Arrays.asList(new String[] {"taeo"});
		meta.description = "";
		meta.url = "";
		meta.updateUrl = "";
		meta.screenshots = new String[0];
		meta.logoFile = "";
		
		
	}
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);
		return true;
	}
	@SubscribeEvent
	public void modConstruction(FMLConstructionEvent evt)
	{
		
	}

	@SubscribeEvent
	public void preInit(FMLPreInitializationEvent evt) {

	}

	@SubscribeEvent
	public void init(FMLInitializationEvent evt) {

	}


	@SubscribeEvent
	public void postInit(FMLPostInitializationEvent evt) {

	}

}
