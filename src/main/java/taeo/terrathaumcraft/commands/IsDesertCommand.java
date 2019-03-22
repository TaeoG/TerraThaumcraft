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

package taeo.terrathaumcraft.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import sun.rmi.log.LogHandler;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.utility.LogHelper;
import taeo.ttfcapi.worldgen.SpawnRequirement;

public class IsDesertCommand extends CommandBase {
	@Override
	public String getCommandName() {
		return "isDesert";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params) {
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		World world = player.worldObj;

		SpawnRequirement.Desert desert = new SpawnRequirement.Desert();
		boolean isDesert = desert.isValid(world, (int)player.posX/16, (int)player.posY, (int)player.posZ/16);
		sender.addChatMessage(new ChatComponentText(""+isDesert));
		LogHelper.info(ReferenceTTC.MOD_NAME, "Is current location of " + player.posX + " " + player.posY + " "+ player.posZ + " a desert?: "+ isDesert);
	}
}
