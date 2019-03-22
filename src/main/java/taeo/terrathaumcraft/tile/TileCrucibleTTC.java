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

package taeo.terrathaumcraft.tile;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.utility.LogHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.tiles.TileCrucible;

public class TileCrucibleTTC extends TileCrucible{

	private long counter = -100L;
	int bellows;
	Field bellowsField;
	TileEntity heatSource;
	boolean heatedByTFC = false;
	
	public TileCrucibleTTC()
	{
		this.heat = 0;
		this.tank = new FluidTank(TFCFluids.FRESHWATER, 0, 1000);
	}
	
	
	
	
	public void updateEntity()
	/*  76:    */   {
						reflectBellows();
	/*  77: 81 */     this.counter += 1L;
	/*  78: 82 */     int prevheat = this.heat;
	/*  79: 83 */     if (!this.worldObj.isRemote)
	/*  80:    */     {
	/*  81: 85 */       if (this.bellows < 0) {
	/*  82: 85 */         getBellows();
	//System.out.println("Bellows " + bellows);
	/*  83:    */       }
	/*  84: 88 */       if (this.tank.getFluidAmount() > 0)
	/*  85:    */       {
	/*  86: 89 */         Material mat = this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord).getMaterial();
	/*  87: 90 */         Block bi = this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord);
	/*  88: 91 */         int md = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord - 1, this.zCoord);
						heatSource = worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
	if(heatSource instanceof TEFireEntity && ((TEFireEntity) heatSource).fuelTimeLeft > 0)
	{
		heatedByTFC = true;
	}
	else
	{
		heatedByTFC = false;
	}
	/*  89: 92 */         if ((mat == Material.lava) || (mat == Material.fire) || ((bi == ConfigBlocks.blockAiry) && (md == 1)) || heatedByTFC)
	/*  90:    */         {
		

							if (this.heat < 200)
							{
								this.heat = ((short)(this.heat + (1 + this.bellows * 2)));
								if ((prevheat < 151) && (this.heat >= 151))
								{
									markDirty();//onInventoryChanged();
	               					this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	             				}
	           				}
	/* 100:    */         }
	/* 101:100 */         else if (this.heat > 0)
	/* 102:    */         {
	/* 103:101 */           this.heat = ((short)(this.heat - 1));
	/* 104:102 */           if (this.heat == 149)
	/* 105:    */           {
		markDirty();
	/* 106:103 */             //onInventoryChanged();
	/* 107:104 */             this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	/* 108:    */           }
	/* 109:    */         }
	/* 110:    */       }
	/* 111:107 */       else if (this.heat > 0)
	/* 112:    */       {
	/* 113:108 */         this.heat = ((short)(this.heat - 1));
	/* 114:    */       }
	/* 115:112 */       if ((tagAmount() > 100) && (this.counter % 5L == 0L))
	/* 116:    */       {
	/* 117:113 */         AspectList tt = takeRandomFromSource();
	/* 118:114 */         spill();
	/* 119:    */       }
	/* 120:118 */       if ((this.counter > 100L) && (this.heat > 150))
	/* 121:    */       {
	/* 122:119 */         this.counter = 0L;
	/* 123:120 */         if (tagAmount() > 0)
	/* 124:    */         {
	/* 125:121 */           int s = this.aspects.getAspects().length;
	/* 126:122 */           Aspect a = this.aspects.getAspects()[this.worldObj.rand.nextInt(s)];
	/* 127:123 */           if (a.isPrimal()) {
	/* 128:124 */             a = this.aspects.getAspects()[this.worldObj.rand.nextInt(s)];
	/* 129:    */           }
	/* 130:126 */           this.tank.drain(2, true);
	/* 131:    */           
	/* 132:128 */           this.aspects.remove(a, 1);
	/* 133:131 */           if (!a.isPrimal())
	/* 134:    */           {
	/* 135:132 */             if (this.worldObj.rand.nextBoolean()) {
	/* 136:133 */               this.aspects.add(a.getComponents()[0], 1);
	/* 137:    */             } else {
	/* 138:135 */               this.aspects.add(a.getComponents()[1], 1);
	/* 139:    */             }
	/* 140:    */           }
	/* 141:    */           else {
	/* 142:138 */             spill();
	/* 143:    */           }
	/* 144:    */         }
	/* 145:141 */         markDirty(); //onInventoryChanged();
	/* 146:142 */         this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	/* 147:    */       }
	/* 148:    */     }
	/* 149:148 */     else if (this.tank.getFluidAmount() > 0)
	/* 150:    */     {
	/* 151:149 */       drawEffectsTTC();
	/* 152:    */     }
	/* 153:153 */     if ((this.worldObj.isRemote) && (prevheat < 151) && (this.heat >= 151)) {
	/* 154:154 */       this.heat = ((short)(this.heat + 1));
	/* 155:    */     }
	/* 156:    */   }
	public void drawEffectsTTC()
	{
		try {
			Method drawmethod = getClass().getSuperclass().getDeclaredMethod("drawEffects");
			drawmethod.setAccessible(true);
			drawmethod.invoke(this, (Object[])null);
		} catch (NoSuchMethodException e) {
			LogHelper.error(ReferenceTTC.MOD_NAME,"drawEffects in TileCrucible does not exist");
			e.printStackTrace();
		} catch (SecurityException e) {
			LogHelper.error(ReferenceTTC.MOD_NAME,"Security settings do not allow reflection");
			e.printStackTrace();
		} catch (Exception e) {
			LogHelper.error(ReferenceTTC.MOD_NAME, "Cannot access drawEffects in TileCrucible");
			e.printStackTrace();
		}
	}
	public void reflectBellows()
	{
		try {
			bellowsField = getClass().getSuperclass().getDeclaredField("bellows");
			bellowsField.setAccessible(true);
			bellows = bellowsField.getInt(this);
		} catch (NoSuchFieldException e) {
			LogHelper.error(ReferenceTTC.MOD_NAME,"bellows in TileCrucible cannot be accessed");
			e.printStackTrace();
		} catch (SecurityException e) {
			LogHelper.error(ReferenceTTC.MOD_NAME,"Security settings disallow reflection");
			e.printStackTrace();
		} catch (Exception e) {
			LogHelper.error(ReferenceTTC.MOD_NAME,"Cannot access bellows in TileCrucible");
			e.printStackTrace();
		}
		
	}
	public boolean canFill(ForgeDirection from, Fluid fluid)
	/* 461:    */   {
						System.out.println("Can Fill was called");
	/* 462:469 */     return (fluid != null) && (fluid.getID() == FluidRegistry.getFluidID(TFCFluids.FRESHWATER));
	/* 463:    */   }
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	/* 430:    */   {
		//System.out.println("Attempting to fill with " + resource.getLocalizedName() + " with fluid ID " + resource.getFluidID());
	/* 431:436 */     if ((resource != null) && (resource.getFluidID() != FluidRegistry.getFluidID(TFCFluids.FRESHWATER))) {
	/* 432:436 */       
		System.out.println("Apparently that isn't the same as " + FluidRegistry.getFluidID(TFCFluids.FRESHWATER));
		return 0;
	/* 433:    */     }
	/* 434:438 */     if (doFill)
	/* 435:    */     {
	/* 436:439 */       markDirty();
	/* 437:440 */       this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	/* 438:    */     }
	/* 439:443 */     return this.tank.fill(resource, doFill);
	/* 440:    */   }

	
}
