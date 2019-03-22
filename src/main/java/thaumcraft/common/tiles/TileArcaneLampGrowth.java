
package thaumcraft.common.tiles;

import cpw.mods.fml.common.network.NetworkRegistry;

import java.util.ArrayList;
import java.util.Collections;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.TileEntities.TEFarmland;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.util.ForgeDirection;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.terrathaumcraft.utility.ArcaneGrowthHelper;
import taeo.terrathaumcraft.utility.CropUtilsTTC;
import taeo.ttfcapi.utility.LogHelper;
import thaumcraft.api.BlockCoordinates;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXBlockSparkle;
import thaumcraft.common.lib.utils.CropUtils;

public class TileArcaneLampGrowth extends TileThaumcraft implements IEssentiaTransport
{
	public ForgeDirection facing = ForgeDirection.getOrientation(0);
	private boolean reserve = false;
	public int charges = -1;

	public boolean canUpdate()
	{
		return true;
	}

	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		super.onDataPacket(net, pkt);
		if ((this.worldObj != null) && 
				(this.worldObj.isRemote)) {
			this.worldObj.updateLightByType(EnumSkyBlock.Block, this.xCoord, this.yCoord, this.zCoord);
		}
	}


	public void updateEntity()
	{
		if (!this.worldObj.isRemote) {
			if (this.charges <= 0) {
				if (this.reserve) {
					this.charges += 100;
					this.reserve = false;
					this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
				}
				else if (drawEssentia()) {
					this.charges += 100;
					this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
				}
			}

			if ((!this.reserve) && 
					(drawEssentia())) {
				this.reserve = true;
			}


			if (this.charges == 0) {
				this.charges = -1;
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			}

			if (this.charges > 0) {
				updatePlant();
			}
			lastTick++;
			//LogHelper.info("Ticked");
		}
	}


	boolean isPlant(int x, int y, int z)
	{
		boolean flag = this.worldObj.getBlock(x, y, z) instanceof IGrowable;
		Material mat = this.worldObj.getBlock(x, y, z).getMaterial();
		return ((flag) || (mat == Material.cactus) || (mat == Material.plants)) && (mat != Material.grass);
	}

	int lx = 0;
	int ly = 0;
	int lz = 0;
	Block lid = Blocks.air;
	int lmd = 0;
	long lastTick = 0;




	ArrayList<BlockCoordinates> checklist = new ArrayList();

	private void updatePlant()
	{
		if ((this.lid != this.worldObj.getBlock(this.lx, this.ly, this.lz)) || (this.lmd != this.worldObj.getBlockMetadata(this.lx, this.ly, this.lz))) {
			EntityPlayer p = this.worldObj.getClosestPlayer(this.lx, this.ly, this.lz, 32.0D);
			if (p != null) {
				PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(this.lx, this.ly, this.lz, 4259648), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, this.lx, this.ly, this.lz, 32.0D));
			}



			this.lid = this.worldObj.getBlock(this.lx, this.ly, this.lz);
			this.lmd = this.worldObj.getBlockMetadata(this.lx, this.ly, this.lz);
		}

		int distance = 6;

		if (this.checklist.size() == 0) {
			for (int a = -distance; a <= distance; a++) {
				for (int b = -distance; b <= distance; b++)
					this.checklist.add(new BlockCoordinates(this.xCoord + a, this.yCoord + distance, this.zCoord + b));
			}
			Collections.shuffle(this.checklist, this.worldObj.rand);
		}

		int x = ((BlockCoordinates)this.checklist.get(0)).x;
		int y = ((BlockCoordinates)this.checklist.get(0)).y;
		int z = ((BlockCoordinates)this.checklist.get(0)).z;
		this.checklist.remove(0);
		//Begin
		long time = TFC_Time.getTotalTicks();

		if(lastTick == 0)
		{
			ArcaneGrowthHelper.setupLamp(this);
			ArcaneGrowthHelper.processCropsOverInterval(this, 1, false, 1);
			lastTick = time;// + 24 * TFC_Time.hourLength;
			ArcaneGrowthHelper.clearData(this);
		}
		if(Math.abs(time - lastTick) >= 10)
		{
			//If the ticks aren't synced
			LogHelper.info(ReferenceTTC.MOD_NAME,"Last tick: " + lastTick + " Time: " + time + " deltaT: " + (time-lastTick));
			
			//make a snapshot of the crops/essentia sources
			if(!ArcaneGrowthHelper.isTracked(this))
			{
				LogHelper.info(ReferenceTTC.MOD_NAME,"Scanning for sources/crops");
				ArcaneGrowthHelper.setupLamp(this);
			}
			
			
			//Find the number of ticks passed, up to a day
			float timePassed = Math.min(time - lastTick, TFC_Time.DAY_LENGTH);
			//Find the number of Crops in range
			//TODO make sure to remove crops that became fully grown the day before
			int numCrops = ArcaneGrowthHelper.getCrops(this).size();
			
			//The lamp uses 1 charge per crop every 169 ticks, and has 100 charges
			float fuelNeeded = ((timePassed/169)*numCrops)/100;
			
			
			int fuelAvailable = ArcaneGrowthHelper.getFuelAmount(this);
			//LogHelper.info(ReferenceTTC.MOD_NAME,"Fuel Available " + fuelAvailable);
			//LogHelper.info(ReferenceTTC.MOD_NAME,"Fuel Needed for " +timePassed+" ticks for "+numCrops+" crops:" + fuelNeeded);
			
			//The multiplier (between zero and 1) representing the portion of the day that the lamp was running
			float percentTimeRunning;
			
			boolean drainedFuel = false;
			if(fuelAvailable > fuelNeeded)
			{//if there is more than enough fuel, use what is needed, and set the entire day as running  
				ArcaneGrowthHelper.useFuel(this, fuelNeeded);
				percentTimeRunning = 1;
			}
			else
			{
				//if there isn't enough, or just exactly enough, drain it all and set the time running accordingly
				//also clear out any fuel in the lamp
				//TODO there is potential for there to be 2 full herba in the lamp itself, should be taken into account when calculating available fuel
				ArcaneGrowthHelper.useFuel(this, fuelAvailable);
				percentTimeRunning = (float)fuelAvailable/(float)fuelNeeded;
				this.charges = 0;
				reserve = false;
				drainedFuel = true;
			}
			
			//iterate over the crops, increasing their growth and altering the nutrient level accordingly
			ArcaneGrowthHelper.processCropsOverInterval(this, percentTimeRunning, true, timePassed);

			
			lastTick += 24 * TFC_Time.HOUR_LENGTH;
			if(lastTick >= time || drainedFuel)
			{	//if the ticks caught up, or the fuel was drained on the day being processd
				//clear out the snapshot and rexync the ticks
				//LogHelper.info(ReferenceTTC.MOD_NAME,"Caught up, timeStamping.");
				ArcaneGrowthHelper.clearData(this);
				lastTick = time;
			}
			return;
		}
		else
		{
			lastTick=time;
		}
		
		//End
		for (; 
				y >= this.yCoord - distance; y--) {
			if ((!this.worldObj.isAirBlock(x, y, z)) && (isPlant(x, y, z)) && (getDistanceFrom(x + 0.5D, y + 0.5D, z + 0.5D) < distance * distance) && (!CropUtilsTTC.isGrownCrop(this.worldObj, x, y, z)) && (CropUtils.doesLampGrow(this.worldObj, x, y, z)))
			{

				this.charges -= 1;



				this.lx = x;
				this.ly = y;
				this.lz = z;
				this.lid = this.worldObj.getBlock(x, y, z);
				this.lmd = this.worldObj.getBlockMetadata(x, y, z);

				if(this.worldObj.getTileEntity(x, y, z) instanceof TECrop)
				{
					if(this.worldObj.getTileEntity(x, y-1, z) instanceof TEFarmland)
					{
						
						ArcaneGrowthHelper.processCrop((TECrop) this.worldObj.getTileEntity(x, y, z));

						if(worldObj.rand.nextInt(10) == 0)
						{
							EntityPlayer p = this.worldObj.getClosestPlayer(this.lx, this.ly, this.lz, 32.0D);
							if (p != null) {
								PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(this.lx, this.ly, this.lz, 4259648), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, this.lx, this.ly, this.lz, 32.0D));
							}
						}
					}
				}
				//lastTick++;
				this.worldObj.scheduleBlockUpdate(x, y, z, this.worldObj.getBlock(x, y, z), 1);
				return;
			}
		}
	}
	/*	public List<TECrop> getTECropInRange()
	{
		ArrayList<TECrop> output= new ArrayList();
		int distance = 6;
		checklist.clear();
		for (int a = -distance; a <= distance; a++) {
			for (int b = -distance; b <= distance; b++)
			{
				this.checklist.add(new BlockCoordinates(this.xCoord + a, this.yCoord + distance, this.zCoord + b));
				TileEntity te = worldObj.getTileEntity(xCoord + a, yCoord + distance, zCoord + b);
				if(te != null && te instanceof TECrop)
				{
					TileEntity tef = worldObj.getTileEntity(te.xCoord, te.yCoord -1, te.zCoord);
					if(tef != null && te instanceof TEFarmland)
					{
						output.add((TECrop)te);
					}
				}
			}
		}
		Collections.shuffle(this.checklist, this.worldObj.rand);
		return output;
	}*/

	/*public List<IAspectContainer> getConnectedSources()
	{
		HashMap<BlockCoordinates, TileEntity> pipeConstruct = new HashMap<BlockCoordinates, TileEntity>();
		ArrayList<IAspectContainer> essentiaSources = new ArrayList<IAspectContainer>();
		HashMap<BlockCoordinates, TileEntity> stubs = new HashMap<BlockCoordinates, TileEntity>();

		TileEntity targetTe = this;
		IEssentiaTransport target = (IEssentiaTransport) targetTe;
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
			target = (IEssentiaTransport) targetTe;
			LogHelper.info("Found valid transport " + targetTe.toString() + " at " + targetTe.xCoord +" " + targetTe.yCoord +" " +targetTe.zCoord);
			if (targetTe != null && targetTe instanceof IAspectContainer && ((IAspectContainer) targetTe).doesContainerContainAmount(Aspect.PLANT, 1))
			{
				LogHelper.info("its a source");
				essentiaSources.add((IAspectContainer)targetTe);
			}

			stubs.remove(stubkeys[0]);
		}
		return essentiaSources;

	}

	private HashMap<BlockCoordinates, TileEntity> getConnectedTransport(TileEntity target)
	{
		HashMap output = new HashMap<BlockCoordinates, TileEntity>();
		IEssentiaTransport targetInterface = (IEssentiaTransport) target;
		for(ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
		{
			TileEntity subTarget = ThaumcraftApiHelper.getConnectableTile(worldObj, target.xCoord, target.yCoord, target.zCoord, direction);
			if (subTarget != null)
			{
				IEssentiaTransport subTargetInterface = (IEssentiaTransport) subTarget;
				if(subTargetInterface.getSuctionAmount(direction.getOpposite()) <= targetInterface.getSuctionAmount(direction) && subTargetInterface.canOutputTo(direction.getOpposite()) && targetInterface.canInputFrom(direction))
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

	}*/


	public void readCustomNBT(NBTTagCompound nbttagcompound)
	{
		this.facing = ForgeDirection.getOrientation(nbttagcompound.getInteger("orientation"));
		this.reserve = nbttagcompound.getBoolean("reserve");
		this.charges = nbttagcompound.getInteger("charges");
		this.lastTick = nbttagcompound.getLong("lastTick");
	}


	public void writeCustomNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setInteger("orientation", this.facing.ordinal());
		nbttagcompound.setBoolean("reserve", this.reserve);
		nbttagcompound.setInteger("charges", this.charges);
		nbttagcompound.setLong("lastTick", this.lastTick);
	}



	int drawDelay = 0;

	boolean drawEssentia() {
		if (++this.drawDelay % 5 != 0) return false;
		TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.facing);
		if (te != null) {
			IEssentiaTransport ic = (IEssentiaTransport)te;
			if (!ic.canOutputTo(this.facing.getOpposite())) return false;
			if ((ic.getSuctionAmount(this.facing.getOpposite()) < getSuctionAmount(this.facing)) && (ic.takeEssentia(Aspect.PLANT, 1, this.facing.getOpposite()) == 1))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isConnectable(ForgeDirection face)
	{
		return face == this.facing;
	}

	public boolean canInputFrom(ForgeDirection face)
	{
		return face == this.facing;
	}

	public boolean canOutputTo(ForgeDirection face)
	{
		return false;
	}


	public void setSuction(Aspect aspect, int amount) {}


	public boolean renderExtendedTube()
	{
		return false;
	}

	public int getMinimumSuction()
	{
		return 0;
	}

	public Aspect getSuctionType(ForgeDirection face)
	{
		return Aspect.PLANT;
	}

	public int getSuctionAmount(ForgeDirection face)
	{
		return (face == this.facing) && ((!this.reserve) || (this.charges <= 0)) ? 128 : 0;
	}

	public Aspect getEssentiaType(ForgeDirection loc)
	{
		return null;
	}

	public int getEssentiaAmount(ForgeDirection loc)
	{
		return 0;
	}

	public int takeEssentia(Aspect aspect, int amount, ForgeDirection loc)
	{
		return 0;
	}

	public int addEssentia(Aspect aspect, int amount, ForgeDirection loc)
	{
		return 0;
	}
}



/* Location:           D:\Taeo\Desktop\ThaumaFirmaCraft2\Thaumcraft-1.7.10-4.2.3.5.deobfnew.jar

 * Qualified Name:     thaumcraft.common.tiles.TileArcaneLampGrowth

 * JD-Core Version:    0.7.0.1

 */