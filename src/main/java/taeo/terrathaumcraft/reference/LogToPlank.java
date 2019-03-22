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

package taeo.terrathaumcraft.reference;

import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.ttfcapi.utility.BlockEx;

import java.util.HashMap;

public class LogToPlank {

	static LogToPlank instance;
	HashMap<BlockEx, ItemStack> container;

	private LogToPlank()
	{
		container = new HashMap<>();

		for(int i = 0; i <= 16; i++)
		{
			Block logBlock = i <16? TFCBlocks.logNatural : TFCBlocks.logNatural2;
			int logMeta = i%16;
			addLog(new BlockEx(logBlock, logMeta), new ItemStack(TFCItems.singlePlank, 8, i));
		}
		addLog(new BlockEx(TTCBlocks.blockMagicTrunk, 0), new ItemStack(TFCItems.singlePlank, 8, ReferenceTTC.WOODTYPESTARTINDEX));
		addLog(new BlockEx(TTCBlocks.blockMagicTrunk, 4), new ItemStack(TFCItems.singlePlank, 8, ReferenceTTC.WOODTYPESTARTINDEX));
		addLog(new BlockEx(TTCBlocks.blockMagicTrunk, 8), new ItemStack(TFCItems.singlePlank, 8, ReferenceTTC.WOODTYPESTARTINDEX));

		addLog(new BlockEx(TTCBlocks.blockMagicTrunk, 1), new ItemStack(TFCItems.singlePlank, 8, ReferenceTTC.WOODTYPESTARTINDEX+1));
		addLog(new BlockEx(TTCBlocks.blockMagicTrunk, 5), new ItemStack(TFCItems.singlePlank, 8, ReferenceTTC.WOODTYPESTARTINDEX+1));
		addLog(new BlockEx(TTCBlocks.blockMagicTrunk, 9), new ItemStack(TFCItems.singlePlank, 8, ReferenceTTC.WOODTYPESTARTINDEX+1));

	}

	public static LogToPlank getInstance()
	{
		if(instance == null)
		{
			instance = new LogToPlank();
		}
		return instance;
	}

	public void addLog(BlockEx log, ItemStack planks)
	{
		container.put(log, planks);
	}

	public ItemStack getPlanks(BlockEx log)
	{
		return container.get(log);
	}
}
