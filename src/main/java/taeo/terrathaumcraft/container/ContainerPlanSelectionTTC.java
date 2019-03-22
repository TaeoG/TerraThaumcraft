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

package taeo.terrathaumcraft.container;

import taeo.terrathaumcraft.tile.TEAnvilTTC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.ContainerTFC;

public class ContainerPlanSelectionTTC extends ContainerTFC{

	TEAnvilTTC anvil;
	World world;
	EntityPlayer player;
	String plan = "";
	public ContainerPlanSelectionTTC(EntityPlayer p, TEAnvilTTC a, World w, int x, int y, int z)
	{
		anvil = a;
		world = w;
		player = p;
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		if(anvil.craftingPlan != plan)
			plan = anvil.craftingPlan;
	}
}
