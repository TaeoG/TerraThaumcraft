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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.TileEntities.TEFarmland;
import com.bioxx.tfc.api.TFCOptions;

import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.utility.LogHelper;
import taeo.ttfcapi.utility.UtilsTAPI;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.tiles.TileArcaneLampGrowth;
import thaumcraft.common.tiles.TileTube;

public class ArcaneGrowthHelper {
	
	private static HashMap<TileArcaneLampGrowth, ArrayList<TECrop>> crops = new HashMap<TileArcaneLampGrowth, ArrayList<TECrop>>();
	private static HashMap<TileArcaneLampGrowth, ArrayList<TileEntity>> sources = new HashMap<TileArcaneLampGrowth, ArrayList<TileEntity>>();
	private static ArrayList<TECrop> grownCrops = new ArrayList<TECrop>();
	private static int arcaneLampRange = 6;
	//private static HashMap<TileArcaneLampGrowth, ArrayList<TEFarmland>> farms;
	
	public static void setupLamp(TileArcaneLampGrowth lamp)
	{
		sources.put(lamp, UtilsTTC.getConnectedSources(lamp));
		crops.put(lamp, UtilsTAPI.getTECropInRange(lamp.getWorldObj(), lamp.xCoord, lamp.yCoord, lamp.zCoord, arcaneLampRange));
		//farms.put(lamp, getLand(crops.get(lamp)));
	}
	
	public static ArrayList<TECrop> getCrops(TileArcaneLampGrowth lamp)
	{
		return crops.get(lamp);
	}
	
	public static ArrayList<TileEntity> getSources(TileArcaneLampGrowth lamp)
	{
		return sources.get(lamp);
	}
	/*public static ArrayList<TEFarmland> getFarmland(TileArcaneLampGrowth lamp)
	{
		return farms.get(lamp);
	}*/
	
	public static boolean isTracked(TileArcaneLampGrowth lamp)
	{
		
			return (sources.containsKey(lamp) || crops.containsKey(lamp));
		
	}
	
	public static int getFuelAmount(TileArcaneLampGrowth lamp)
	{
		int total = 0;
		for(TileEntity containerte : sources.get(lamp))
		{
			if(containerte instanceof IAspectContainer)
			{
				IAspectContainer container = (IAspectContainer) containerte;
				AspectList aspects = container.getAspects();
				total += aspects.getAmount(Aspect.PLANT);
			}
			else
			{
				IEssentiaTransport transport = (IEssentiaTransport) containerte;
				if (transport.getEssentiaType(ForgeDirection.UNKNOWN) == Aspect.PLANT)
				total += transport.getEssentiaAmount(ForgeDirection.UNKNOWN);
			}
		}
		return total;
	}
	
	public static boolean useFuel(TileArcaneLampGrowth lamp, float fuelNeeded)
	{
		int fuel = (int) Math.floor(fuelNeeded);
		for(TileEntity containerte: sources.get(lamp))
		{
			if (containerte instanceof IAspectContainer) {
				IAspectContainer container = (IAspectContainer) containerte;
				int contents = container.getAspects().getAmount(Aspect.PLANT);
				if (fuel > contents) {
					container.takeFromContainer(Aspect.PLANT, contents);
					containerte.getWorldObj().scheduleBlockUpdate(
							containerte.xCoord, containerte.yCoord,
							containerte.zCoord, containerte.getBlockType(), 1);

					fuel -= contents;
				} else {
					container.takeFromContainer(Aspect.PLANT, fuel);
					containerte.getWorldObj().scheduleBlockUpdate(
							containerte.xCoord, containerte.yCoord,
							containerte.zCoord, containerte.getBlockType(), 1);

					return true;
				}
			}
			else
			{
				try {
					IEssentiaTransport transport = (IEssentiaTransport) containerte;
					int contents = 0;
					if(transport.getEssentiaType(ForgeDirection.UNKNOWN) == Aspect.PLANT)
						contents = transport.getEssentiaAmount(ForgeDirection.UNKNOWN);
					Field essentia;

					essentia = TileTube.class.getDeclaredField("essentiaAmount");
					essentia.setAccessible(true);

					if(fuel> contents)
					{
						essentia.setInt(transport, 0);
						fuel -= contents;
					}
					else
					{
						essentia.setInt(transport, contents - fuel);
						return true;
					}
				} catch (Exception e) {
					LogHelper.error(ReferenceTTC.MOD_NAME,"Cannot access essentiaAmount in TileTube");
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public static boolean clearData(TileArcaneLampGrowth lamp)
	{
		sources.remove(lamp);
		crops.remove(lamp);
		return true;
	}
			
	public static void processCropsOverDay(TileArcaneLampGrowth lamp, float growthMult, boolean growCrop)
	{
		processCropsOverInterval(lamp, growthMult, growCrop, TFC_Time.DAY_LENGTH);
	}
	public static void processCrop(TECrop crop)
	{
		//LogHelper.info("Crop Growth Begin " + crop.growth);
		processCropOverInterval(crop, 1, true, 169);
		//LogHelper.info("Crop Growth End " + crop.growth);
		
	}
	public static void processCropsOverInterval(TileArcaneLampGrowth lamp, float growthMult, boolean growCrop, float tickInterval)
	{
		for(TECrop inCrop: getCrops(lamp))
		{
			//TODO check if the crop is still present
			processCropOverInterval(inCrop, growthMult, growCrop, tickInterval);
			removeGrownCrops();
		}
	}
	
	private static void processCropOverInterval(TECrop crop,  float growthMult, boolean growCrop, float ticks)
	{
		float timeMultiplier = 360 / TFC_Time.daysInYear;
		CropIndex cropIndex;
		TEFarmland farmland;
		int nutriType, nutri, fert, soilMax;
		
		cropIndex = CropManager.getInstance().getCropFromId(crop.cropId);
		farmland = (TEFarmland) crop.getWorldObj().getTileEntity(crop.xCoord, crop.yCoord -1, crop.zCoord);
		nutriType = cropIndex.cycleType;
		nutri = farmland.nutrients[nutriType];
		fert = farmland.nutrients[3];
		soilMax = farmland.getSoilMax();

		nutri = Math.min(nutri + fert, (int)(soilMax * 1.25f));
		float nutriMult = (0.2f + ((float)nutri/(float)soilMax) * 0.5f); 		
		float growthRate = ((((cropIndex.numGrowthStages / (cropIndex.growthTime * TFC_Time.timeRatio96))) * nutriMult) * timeMultiplier) * TFCOptions.cropGrowthMultiplier;

		int oldGrowth = (int) Math.floor(crop.growth);
		if(growCrop)
		crop.growth += ((growthRate*growthMult*0.1f)/(TFC_Time.DAY_LENGTH))*ticks;
		if(oldGrowth < (int) Math.floor(crop.growth))
		{
			crop.broadcastPacketInRange();
		}
		if(crop.growth >= cropIndex.numGrowthStages)
		{
			markForRemoval(crop);
		}
		
		

		if(growthMult < 1)
		{

			farmland.drainNutrients(nutriType, cropIndex.nutrientUsageMult * (1-growthMult));
			farmland.drainNutrients(3, cropIndex.nutrientUsageMult * (1-growthMult));
		}
		else
		{
			farmland.nutrients[nutriType] = soilMax;
			farmland.nutrients[3] = soilMax;
		}
	}
	
	
	private static void markForRemoval(TECrop crop)
	{
		grownCrops.add(crop);
	}
	
	private static void removeGrownCrops()
	{
		for(TECrop grown : grownCrops)
		{
			Iterator cropListIter = crops.values().iterator();
			while(cropListIter.hasNext())
			{
				Iterator cropIter = ((ArrayList<TECrop>)cropListIter.next()).iterator();
				while (cropIter.hasNext())
				{
					//LogHelper.info("Finding Crop to remove");
					
					//LogHelper.info("Grown Crop " + grown);
					//LogHelper.info("Testing Crop" + test);
					if(grown == cropIter.next())
					{
						LogHelper.info(ReferenceTTC.MOD_NAME,"Removing Grown Crop");
						cropIter.remove();
						break;
					}
				}
				
			}
		}
		grownCrops.clear();
	}
	/*private static ArrayList<TEFarmland> getLand(ArrayList<TECrop> validCrops)
	{
		ArrayList<TEFarmland> output = new ArrayList<TEFarmland>();
		for(TECrop crop : validCrops)
		{
			output.add((TEFarmland)crop.getWorldObj().getTileEntity(crop.xCoord, crop.yCoord -1, crop.zCoord));
		}
		return output;
	}
*/
}
