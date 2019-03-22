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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import taeo.terrathaumcraft.worldgen.ClimateHandler;
import thaumcraft.common.lib.world.biomes.BiomeHandler;

import java.util.ArrayList;

public class GetClimateTagsCommand extends CommandBase {
	@Override
	public String getCommandName() {
		return "climatetags";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] p_71515_2_) {
		World world = sender.getEntityWorld();
		ChunkCoordinates coords = sender.getPlayerCoordinates();

		ArrayList<BiomeDictionary.Type> types = ClimateHandler.getClimateTags(sender.getEntityWorld(), coords.posX, coords.posY, coords.posZ);
		sender.addChatMessage(new ChatComponentText("Climate Tags:"));
		for(BiomeDictionary.Type type : types)
		{
			sender.addChatMessage(new ChatComponentText(type.name()));
		}
		BiomeDictionary.Type[] types2 = BiomeDictionary.getTypesForBiome(world.getBiomeGenForCoords(coords.posX,coords.posZ));
		sender.addChatMessage(new ChatComponentText("Biome Tags:"));
		for (BiomeDictionary.Type type : types2)
		{
			sender.addChatMessage(new ChatComponentText(type.name()));
		}
	}
}
