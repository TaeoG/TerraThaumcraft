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

package taeo.terrathaumcraft.utility;

import java.awt.*;
import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.ttfcapi.utility.BlockEx;
import thaumcraft.api.BlockCoordinates;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.tiles.TileTubeFilter;
import thaumcraft.common.tiles.TileTubeOneway;
import thaumcraft.common.tiles.TileTubeValve;

public class UtilsTTC {


	
	public static ArrayList<TileEntity> getConnectedSources(TileEntity beginning)
	{
		HashMap<BlockCoordinates, TileEntity> pipeConstruct = new HashMap<BlockCoordinates, TileEntity>();
		ArrayList<TileEntity> essentiaSources = new ArrayList<TileEntity>();
		HashMap<BlockCoordinates, TileEntity> stubs = new HashMap<BlockCoordinates, TileEntity>();
		ArrayList<TileEntity> essentiaHolders = new ArrayList<TileEntity>();
		
		TileEntity targetTe = beginning;
		//IEssentiaTransport target = (IEssentiaTransport) targetTe;
		while(true)
		{
			
			HashMap<BlockCoordinates, TileEntity> connectedTransport = getConnectedTransport(targetTe);
			for(BlockCoordinates bc : connectedTransport.keySet())
			{	//add all connected essentia transports with lower suction to the stubs list
				if (!pipeConstruct.containsKey(bc) && !stubs.containsKey(bc))
				{
					stubs.put(bc, connectedTransport.get(bc));
				}
			}
			if(stubs.isEmpty())
			{
				break;
			}
			BlockCoordinates[] stubkeys = (BlockCoordinates[]) stubs.keySet().toArray(new BlockCoordinates[stubs.size()]); 
			pipeConstruct.put(stubkeys[0], stubs.get(stubkeys[0]));
			targetTe = stubs.get(stubkeys[0]);
			//target = (IEssentiaTransport) targetTe;
			//LogHelper.info("Found valid transport " + targetTe.toString() + " at " + targetTe.xCoord +" " + targetTe.yCoord +" " +targetTe.zCoord);
			if (targetTe != null && targetTe instanceof IAspectContainer && ((IAspectContainer) targetTe).doesContainerContainAmount(Aspect.PLANT, 1))
			{
				//LogHelper.info("its a source");
				essentiaSources.add(targetTe);
			}
			
			stubs.remove(stubkeys[0]);
		}
		essentiaHolders.addAll(pipeConstruct.values());
		return essentiaHolders;
	}
	
	private static HashMap<BlockCoordinates, TileEntity> getConnectedTransport(TileEntity target)
	{
		HashMap output = new HashMap<BlockCoordinates, TileEntity>();
		IEssentiaTransport targetInterface = (IEssentiaTransport) target;
		for(ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
		{
			TileEntity subTarget = ThaumcraftApiHelper.getConnectableTile(target.getWorldObj(), target.xCoord, target.yCoord, target.zCoord, direction);
			if (subTarget != null)
			{
				IEssentiaTransport subTargetInterface = (IEssentiaTransport) subTarget;
				if(/*subTargetInterface.getSuctionAmount(direction.getOpposite()) <= targetInterface.getSuctionAmount(direction) && */subTargetInterface.canOutputTo(direction.getOpposite()) && targetInterface.canInputFrom(direction))
				{
					if(		(subTarget instanceof TileTubeValve && !((TileTubeValve)subTarget).allowFlow)
						||	(subTarget instanceof TileTubeFilter && ((TileTubeFilter)subTarget).getAspects() != null && !((TileTubeFilter)subTarget).getAspects().aspects.containsKey(Aspect.PLANT))
						||	(subTarget instanceof TileTubeOneway && !((TileTubeOneway)subTarget).facing.equals(direction))
							)
					{
						//do nothing
					
					}
					else
					{
						output.put(new BlockCoordinates(subTarget.xCoord, subTarget.yCoord, subTarget.zCoord), subTarget);
					}
				}
			}
		}
		return output;
		
	}
	public static boolean sameLog(Block block, int targetMeta, int currentMeta)
	{
		if(block == TTCBlocks.blockMagicTrunk)
		{
			switch(currentMeta%4)
			{
				case 0:
				case 3:
					currentMeta = 0;
					break;
				case 1:
				case 2:
					currentMeta = 1;
					break;
				default:
					currentMeta = 0;
					break;
			}
			switch(targetMeta%4)
			{
				case 0:
				case 3:
					targetMeta = 0;
					break;
				case 1:
				case 2:
					targetMeta = 1;
					break;
				default:
					targetMeta = 0;
					break;
			}
		}
		if(currentMeta == targetMeta)
			return true;
		else return false;

	}
	public static int processTree(World world, int x, int y, int z, int maxResults, int remainingUses)
	{
		HashSet<Triple> trunkNodes = new HashSet<>();
		TieredTriples tieredTrunkNodes = new TieredTriples();
		HashSet<Triple> emptyNodes = new HashSet<>();
		Queue<Triple>	targetNodes = new LinkedList<>();
		BlockEx targetBlock = new BlockEx(world.getBlock(x,y,z), world.getBlockMetadata(x,y,z));
		targetNodes.add(new Triple(x,y,z));
		//prevent tree tracing algorithm from searching down initially
		emptyNodes.add(new Triple(x, y-1, z));
		emptyNodes.add(new Triple(x-1, y-1, z));
		emptyNodes.add(new Triple(x+1, y-1, z));
		emptyNodes.add(new Triple(x, y-1, z-1));
		emptyNodes.add(new Triple(x-1, y-1, z-1));
		emptyNodes.add(new Triple(x+1, y-1, z-1));
		emptyNodes.add(new Triple(x, y-1, z+1));
		emptyNodes.add(new Triple(x-1, y-1, z+1));
		emptyNodes.add(new Triple(x+1, y-1, z+1));

		Triple currentTarget;
		while(!targetNodes.isEmpty() && trunkNodes.size() <= maxResults)
		{
			currentTarget = targetNodes.poll();
			if(currentTarget != null)
			{
				Block currentBlock = world.getBlock(currentTarget.x, currentTarget.y, currentTarget.z);
				int currentMeta = world.getBlockMetadata(currentTarget.x, currentTarget.y, currentTarget.z);


				if ( currentBlock == targetBlock.block && sameLog(targetBlock.block, targetBlock.meta, currentMeta))
				{
					if(!trunkNodes.contains(currentTarget))
					//trunkNodes.add(currentTarget);
					tieredTrunkNodes.add(currentTarget);

					for(int xo = -1; xo < 2; xo++)
					{
						for(int yo = -1; yo< 2; yo++)
						{
							for(int zo = -1; zo<2; zo++)
							{
								if(!(xo == yo && yo == zo && xo == 0))
								{
									Triple tempNode = new Triple(currentTarget.x+xo, currentTarget.y+ yo, currentTarget.z + zo);
									if(!emptyNodes.contains(tempNode) && !tieredTrunkNodes.contains(tempNode) && !targetNodes.contains(tempNode))
									{
										targetNodes.add(tempNode);
									}
								}
							}
						}
					}
				}
				else
				{
					emptyNodes.add(currentTarget);
				}
			}

		}

		//int output = trunkNodes.size();
		/*for(Triple target : trunkNodes)
		{
			world.setBlockToAir(target.x, target.y, target.z);
			tieredTrunkNodes.remove();
		}*/
		Triple target;
		for(int i = 0; i < remainingUses; i++)
		{
			target = tieredTrunkNodes.remove();
			if(target == null)
			{
				return i;
			}
			world.setBlockToAir(target.x, target.y, target.z);
			if(!world.isRemote && world.rand.nextInt(20) == 0)
			{
				PacketHandler.INSTANCE.sendToAllAround(new thaumcraft.common.lib.network.fx.PacketFXBlockBubble(target.x, target.y, target.z, new Color(0.33F, 0.33F, 1.0F).getRGB()), new cpw.mods.fml.common.network.NetworkRegistry.TargetPoint(world.provider.dimensionId, target.x, target.y, target.z, 32.0D));

			}
		}
		return remainingUses;
	}

	

	
}
