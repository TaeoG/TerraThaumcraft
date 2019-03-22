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

package taeo.terrathaumcraft.entity.ai;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodMeat;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCItems;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;
import taeo.terrathaumcraft.utility.UtilsTTC;
import taeo.ttfcapi.utility.UtilsTAPI;
import thaumcraft.common.entities.golems.EntityGolemBase;
import thaumcraft.common.entities.golems.EntityGolemBobber;

public class AIFishTTC extends net.minecraft.entity.ai.EntityAIBase
{
  private EntityGolemBase theGolem;
  private float quality;
  private float distance;
  private World theWorld;
  private int maxDelay = 1;
  private int mod = 1;
  private int count = 0;
  private Vec3 target = null;
  private EntityGolemBobber bobber = null;
  
  public AIFishTTC(EntityGolemBase par1EntityCreature)
  {
    this.theGolem = par1EntityCreature;
    this.theWorld = par1EntityCreature.worldObj;
    setMutexBits(3);
    this.distance = MathHelper.ceiling_float_int(this.theGolem.getRange() / 2.0F);
  }
  



  public boolean shouldExecute()
  {
    if ((this.target != null) || (this.count > 0) || (this.theGolem.ticksExisted % thaumcraft.common.config.Config.golemDelay > 0) || (!this.theGolem.getNavigator().noPath()))
    {
      return false;
    }
    
    if (this.bobber != null) {
      this.bobber.setDead();
    }
    
    Vec3 vv = findWater();
    
    if (vv == null)
    {
      return false;
    }
    

    this.target = Vec3.createVectorHelper(vv.xCoord, vv.yCoord, vv.zCoord);
    this.quality = 0.0F;
    int x = (int)this.target.xCoord;
    int y = (int)this.target.yCoord;
    int z = (int)this.target.zCoord;
    for (int a = 2; a <= 5; a++) {
      ForgeDirection dir = ForgeDirection.getOrientation(a);
      if ((this.theWorld.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).getMaterial() == Material.water) && (this.theWorld.isAirBlock(x + dir.offsetX, y + 1 + dir.offsetY, z + dir.offsetZ)))
      {
        this.quality += 3.0E-005F;
        if (this.theWorld.canBlockSeeTheSky(x + dir.offsetX, y + 1 + dir.offsetY, z + dir.offsetZ)) {
          this.quality += 3.0E-005F;
        }
        for (int depth = 1; depth <= 3; depth++) {
          if (this.theWorld.getBlock(x + dir.offsetX, y - depth + dir.offsetY, z + dir.offsetZ).getMaterial() == Material.water) {
            this.quality += 1.5E-005F;
          }
        }
      }
    }
    this.theWorld.playSoundAtEntity(this.theGolem, "random.bow", 0.5F, 0.4F / (this.theWorld.rand.nextFloat() * 0.4F + 0.8F));
    this.bobber = new EntityGolemBobber(this.theWorld, this.theGolem, x, y, z);
    return this.theWorld.spawnEntityInWorld(this.bobber);
  }
  




  public boolean continueExecuting()
  {
    return (this.bobber != null) && (!this.bobber.isDead) && (this.target != null) && (this.count-- > 0);
  }
  
  public void updateTask()
  {
    if (this.target != null) {
      this.theGolem.getLookHelper().setLookPosition(this.target.xCoord + 0.5D, this.target.yCoord + 1.0D, this.target.zCoord + 0.5D, 30.0F, 30.0F);
      
      float chance = this.quality + this.theGolem.getGolemStrength() * 0.00015F;
      
      if (this.theWorld.rand.nextFloat() < chance) {
        this.theGolem.startRightArmTimer();
        
        int qq = 1;
        
        if ((this.theGolem.getUpgradeAmount(0) > 0) && 
          (this.theWorld.rand.nextInt(10) < this.theGolem.getUpgradeAmount(0))) {
          qq++;
        }
        

        for (int a = 0; a < qq; a++) {
          ItemStack fs = getFishingResult();
          
          if (this.theGolem.getUpgradeAmount(2) > 0 && fs != null && fs.getItem() instanceof ItemFoodMeat) {
        	  Food.setCooked(fs, 840);
            //ItemStack sr = FurnaceRecipes.smelting().func_151395_a(fs);
//            if (sr != null)
//            {
//              fs = sr.copy();
//            }
          }

          if (fs != null) {
        	  EntityItem entityitem = new EntityItem(this.theWorld,
        			  this.target.xCoord + 0.5D, this.target.yCoord + 1.0D,
        			  this.target.zCoord + 0.5D, fs);
        	  if (this.theGolem.getUpgradeAmount(2) > 0) {
        		  entityitem.setFire(2);
        	  }
        	  entityitem.delayBeforeCanPickup = 20;
        	  double d1 = this.theGolem.posX + this.theWorld.rand.nextFloat()
        			  - this.theWorld.rand.nextFloat() - this.target.xCoord
        			  + 0.5D;
        	  double d3 = this.theGolem.posY - this.target.yCoord + 1.0D;
        	  double d5 = this.theGolem.posZ + this.theWorld.rand.nextFloat()
        			  - this.theWorld.rand.nextFloat() - this.target.zCoord
        			  + 0.5D;
        	  double d7 = MathHelper.sqrt_double(d1 * d1 + d3 * d3 + d5 * d5);
        	  double d9 = 0.1D;
        	  entityitem.motionX = (d1 * d9);
        	  entityitem.motionY = (d3 * d9 + MathHelper.sqrt_double(d7) * 0.08D);
        	  entityitem.motionZ = (d5 * d9);
        	  this.theWorld.spawnEntityInWorld(entityitem);
          }
        }

        if (this.bobber != null) {
          this.bobber.playSound("random.splash", 0.15F, 1.0F + (this.theWorld.rand.nextFloat() - this.theWorld.rand.nextFloat()) * 0.4F);
          ((WorldServer)this.theWorld).func_147487_a("splash", this.bobber.posX, this.bobber.posY + 0.5D, this.bobber.posZ, 20 + this.theWorld.rand.nextInt(20), 0.1000000014901161D, 0.0D, 0.1000000014901161D, 0.0D);
          

          this.bobber.setDead();
        }
        this.target = null;
      }
    }
  }
  

  public void resetTask()
  {
    if (this.bobber != null) this.bobber.setDead();
    this.target = null;
    this.count = -1;
  }
  



  public void startExecuting()
  {
    this.count = (300 + this.theWorld.rand.nextInt(200));
    this.theGolem.startRightArmTimer();
  }
  

  private Vec3 findWater()
  {
    Random rand = this.theGolem.getRNG();
    
    for (int var2 = 0; var2 < this.distance * 2.0F; var2++)
    {
      int x = (int)(this.theGolem.getHomePosition().posX + rand.nextInt((int)(1.0F + this.distance * 2.0F)) - this.distance);
      int y = (int)(this.theGolem.getHomePosition().posY + rand.nextInt((int)(1.0F + this.distance)) - this.distance / 2.0F);
      int z = (int)(this.theGolem.getHomePosition().posZ + rand.nextInt((int)(1.0F + this.distance * 2.0F)) - this.distance);
      if ((this.theWorld.getBlock(x, y, z).getMaterial() == Material.water) && (this.theWorld.isAirBlock(x, y + 1, z)))
      {
        Vec3 v = Vec3.createVectorHelper(x, y, z);
        return v;
      }
    }
    return null;
  }
  
  private static final List LOOTCRAP = Arrays.asList(new WeightedRandomFishable[] { new WeightedRandomFishable(new ItemStack(TFCItems.leatherBoots), 10).func_150709_a(0.9F), new WeightedRandomFishable(new ItemStack(TFCItems.soakedHide), 10), new WeightedRandomFishable(new ItemStack(Items.bone), 10),/* new WeightedRandomFishable(new ItemStack(Items.potionitem), 10),*/ new WeightedRandomFishable(new ItemStack(Items.string), 5), new WeightedRandomFishable(new ItemStack(TFCItems.fishingRod), 2).func_150709_a(0.9F), new WeightedRandomFishable(new ItemStack(TFCItems.potteryBowl), 10), new WeightedRandomFishable(new ItemStack(TFCItems.stick), 5),/* new WeightedRandomFishable(new ItemStack(Items.dye, 10, 0), 5), new WeightedRandomFishable(new ItemStack(Blocks.tripwire_hook), 10),*/ new WeightedRandomFishable(new ItemStack(Items.rotten_flesh), 10) });
  










  private static final List LOOTRARE = Arrays.asList(new WeightedRandomFishable[] { /*new WeightedRandomFishable(new ItemStack(Blocks.waterlily), 1), new WeightedRandomFishable(new ItemStack(Items.name_tag), 1),*/ new WeightedRandomFishable(new ItemStack(Items.saddle), 1), new WeightedRandomFishable(new ItemStack(TFCItems.bow), 1).func_150709_a(0.25F).func_150707_a(), new WeightedRandomFishable(new ItemStack(TFCItems.fishingRod), 1).func_150709_a(0.25F).func_150707_a()/*, new WeightedRandomFishable(new ItemStack(Items.book), 1).func_150707_a()*/ });
  





  private static final List LOOTFISH = Arrays.asList(new WeightedRandomFishable[] { new WeightedRandomFishable(new ItemStack(TFCItems.fishRaw, 1), 60)});
  





  private ItemStack getFishingResult()
  {
    float f = this.theWorld.rand.nextFloat();
    float f1 = 0.1F - this.theGolem.getUpgradeAmount(5) * 0.025F;
    float f2 = 0.05F + this.theGolem.getUpgradeAmount(4) * 0.0125F;
    
    int x = (int)this.target.xCoord;
    int y = (int)this.target.yCoord;
    int z = (int)this.target.zCoord;
    for (int a = 2; a <= 5; a++) {
      ForgeDirection dir = ForgeDirection.getOrientation(a);
      if ((this.theWorld.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).getMaterial() == Material.water) && (this.theWorld.isAirBlock(x + dir.offsetX, y + 1 + dir.offsetY, z + dir.offsetZ)))
      {
        f1 -= 0.005F;
        f2 += 0.00125F;
        if (this.theWorld.canBlockSeeTheSky(x + dir.offsetX, y + 1 + dir.offsetY, z + dir.offsetZ)) {
          f1 -= 0.005F;
          f2 += 0.00125F;
        }
        for (int depth = 1; depth <= 3; depth++) {
          if (this.theWorld.getBlock(x + dir.offsetX, y - depth + dir.offsetY, z + dir.offsetZ).getMaterial() == Material.water) {
            f2 += 0.001F;
          }
        }
      }
    }
    f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
    f2 = MathHelper.clamp_float(f2, 0.0F, 1.0F);
    
    if (f < f1)
    {
      return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.theWorld.rand, LOOTCRAP)).func_150708_a(this.theWorld.rand);
    }
    


    f -= f1;
    
    if (f < f2)
    {
      return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.theWorld.rand, LOOTRARE)).func_150708_a(this.theWorld.rand);
    }
    


    float f3 = f - f2;
    ItemStack foodStack = null;
    if(UtilsTAPI.attemptToCatch(theGolem))
    {
    	int lastChunkX = (((int)Math.floor(theGolem.posX)) >> 4);
    	int lastChunkZ = (((int)Math.floor(theGolem.posZ)) >> 4);
    	int maxChunksVisitable = 20;
    	TFC_Core.getCDM(theGolem.worldObj).catchFish(lastChunkX, lastChunkZ);
    	int chunksVisited = 1;
    	for(int radius = 1; radius < 5 && chunksVisited < maxChunksVisitable; radius++){
    		for(int i = -radius; i <= radius; i++)
    		{
    			for(int k = -radius; k <= radius; k+=(Math.abs(i)==radius?1:radius*2))
    			{
    				if(TFC_Core.getCDM(theGolem.worldObj).catchFish(lastChunkX + i, lastChunkZ + k)){
    					chunksVisited++;
    				}
    			}
    		}
    	}
        ItemStack fishStack = ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.theWorld.rand, LOOTFISH)).func_150708_a(this.theWorld.rand);
        //Item fish = fishStack.getItem();
        Random r;
        float foodWeight = (0.1f+theGolem.worldObj.rand.nextFloat()) * (3f * (float)theGolem.golemType.strength);
        foodStack = ItemFoodTFC.createTag(fishStack, foodWeight);
    	r = new Random();
    	Food.adjustFlavor(foodStack, r);
    }
    
    return foodStack;
    
    //return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.theWorld.rand, LOOTFISH)).func_150708_a(this.theWorld.rand);
  }
}


