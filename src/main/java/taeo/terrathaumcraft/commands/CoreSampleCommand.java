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

import com.bioxx.tfc.Blocks.Terrain.BlockOre;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.TileEntities.TEWorldItem;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import taeo.terrathaumcraft.block.BlockMagicOre;
import taeo.terrathaumcraft.init.TTCBlocks;

import java.util.ArrayList;

public class CoreSampleCommand extends CommandBase {

    public CoreSampleCommand()
    {}

    @Override
    public String getCommandName()
    {
        return "coresample";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_)
    {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] params)
    {

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);
        World world = player.worldObj;

        MovingObjectPosition mop = Minecraft.getMinecraft().renderViewEntity.rayTrace(200, 1.0F);
        if(mop != null)
        {
            int blockHitSide = mop.sideHit;
            Block blockLookingAt = world.getBlock(mop.blockX, mop.blockY, mop.blockZ) ;
        }
        /*Vec3 posVec = Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);
                //world.getWorldVec3Pool().getVecFromPool(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        LogHelper.info("Position Vector is " + posVec);
        Vec3 lookVec = player.getLookVec();
        LogHelper.info("look vector is " + lookVec);
        MovingObjectPosition mop = world.rayTraceBlocks(posVec, lookVec);
        if (mop == null)
        {
            LogHelper.error("MovingObjectPosition doesn't exist");
        }*/

        int i = mop.blockX;
        int j = mop.blockY;
        int k = mop.blockZ;
        if(world.setBlock(i, j+1, k, TFCBlocks.worldItem, 0, 2))
        {
            TEWorldItem te =(TEWorldItem)world.getTileEntity(i, j + 1, k);
            ItemStack sample = getCoreSample(world, i, j, k);
            if(world.rand.nextInt(3) == 0 && sample != null)
            {
                te.storage[0] = sample;
            }
            else
            {
                DataLayer dl = TFC_Climate.getRockLayer(world, i, j, k, 0);
                //BlockMeta rockLayer = new BlockMeta(dl.block, dl.data2);
                te.storage[0] = new ItemStack(TFCItems.looseRock, 1, dl.data1);
            }
        }

    }

	private ItemStack getCoreSample(World world, int xCoord, int yCoord, int zCoord)
	{
		return null;
	}
}
