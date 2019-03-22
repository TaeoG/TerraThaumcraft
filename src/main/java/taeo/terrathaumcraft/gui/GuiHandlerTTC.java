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

package taeo.terrathaumcraft.gui;


import taeo.terrathaumcraft.container.ContainerAnvilTTC;
import taeo.terrathaumcraft.container.ContainerPlanSelectionTTC;
import taeo.terrathaumcraft.tile.TEAnvilTTC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.bioxx.tfc.Handlers.GuiHandler;

public class GuiHandlerTTC extends GuiHandler{
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te;
		try
		{
			te= world.getTileEntity(x, y, z);
		}
		catch(Exception e)
		{
			te = null;
		}

		switch(ID)
		{
		case 21:
		{
			//LogHelper.info("Called Anvil switch in GuiHandler");
			return new GuiAnvilTTC(player.inventory, (TEAnvilTTC) te, world, x, y, z);
		}
		case 24:
		{
			//LogHelper.info("Called Plan Selection Switch in GuiHandler");
			return new GuiPlanSelectionTTC(player, (TEAnvilTTC)te, world, x, y, z);
		}
		default:
			return null;
		}
	}
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te = world.getTileEntity(x, y, z);

		switch(ID)
		{
		case 21:
		{
			return new ContainerAnvilTTC(player.inventory, (TEAnvilTTC) te, world, x, y, z);
		}
		case 24:
		{
			return new ContainerPlanSelectionTTC(player, (TEAnvilTTC) te, world, x, y, z);//was metallurgy table
		}
		default:
		{
			return null;
		}
		}
	}

}
