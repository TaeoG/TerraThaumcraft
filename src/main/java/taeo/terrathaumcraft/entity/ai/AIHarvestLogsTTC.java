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

import com.bioxx.tfc.Blocks.Flora.BlockLogNatural;
import com.bioxx.tfc.Blocks.Flora.BlockLogNatural2;
import com.bioxx.tfc.api.TFCItems;
import com.mojang.authlib.GameProfile;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import thaumcraft.common.config.Config;
import thaumcraft.common.entities.golems.EntityGolemBase;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumcraft.common.lib.utils.Utils;

public class AIHarvestLogsTTC extends net.minecraft.entity.ai.EntityAIBase
{
  private EntityGolemBase theGolem;
  private int xx;
  private int yy;
  private int zz;
  private float movementSpeed;
  private float distance;
  private World theWorld;
  private Block block = net.minecraft.init.Blocks.air;
  private int blockMd = 0;
  private int delay = -1;
  private int maxDelay = 1;
  private int mod = 1;
  FakePlayer player;
  private int count = 0;
  
  public AIHarvestLogsTTC(EntityGolemBase par1EntityCreature)
  {
    this.theGolem = par1EntityCreature;
    this.theWorld = par1EntityCreature.worldObj;
    setMutexBits(3);
    this.distance = MathHelper.ceiling_float_int(this.theGolem.getRange() / 3.0F);
    if ((this.theWorld instanceof WorldServer)) {
      this.player = FakePlayerFactory.get((WorldServer)this.theWorld, new GameProfile((java.util.UUID)null, "FakeThaumcraftGolem"));
     // this.player.setItemInUse(new ItemStack(TFCItems.WroughtIronAxe, 1), 9999);
      this.player.setCurrentItemOrArmor(0, new ItemStack(TFCItems.wroughtIronAxe, 1));
    }
  }
  


  public boolean shouldExecute()
  {
    if ((this.delay >= 0) || (this.theGolem.ticksExisted % Config.golemDelay > 0) || (!this.theGolem.getNavigator().noPath())) {
      return false;
    }
    
    Vec3 var1 = findLog();
    
    if (var1 == null)
    {
      return false;
    }
    

    this.xx = ((int)var1.xCoord);
    this.yy = ((int)var1.yCoord);
    this.zz = ((int)var1.zCoord);
    
    this.block = this.theWorld.getBlock(this.xx, this.yy, this.zz);
    this.blockMd = this.theWorld.getBlockMetadata(this.xx, this.yy, this.zz);
    
    return true;
  }
  




  public boolean continueExecuting()
  {
    return (this.theWorld.getBlock(this.xx, this.yy, this.zz) == this.block) && (this.theWorld.getBlockMetadata(this.xx, this.yy, this.zz) == this.blockMd) && (this.count-- > 0) && ((this.delay > 0) || (Utils.isWoodLog(this.theWorld, this.xx, this.yy, this.zz)) || (!this.theGolem.getNavigator().noPath()));
  }
  




  public void updateTask()
  {
    double dist = this.theGolem.getDistanceSq(this.xx + 0.5D, this.yy + 0.5D, this.zz + 0.5D);
    this.theGolem.getLookHelper().setLookPosition(this.xx + 0.5D, this.yy + 0.5D, this.zz + 0.5D, 30.0F, 30.0F);
   // LogHelper.info("Delay = " + this.delay);
    if (dist <= 4.0D)
    {
      if (this.delay < 0) {
    	  if(block instanceof BlockLogNatural || block instanceof BlockLogNatural2)
    	  {
    		  this.delay = ((int)Math.max(5.0F, (20.0F - this.theGolem.getGolemStrength() * 3.0F) * this.block.getBlockHardness(this.theWorld, this.xx, this.yy, this.zz)/20));
    	      
    	  }
    	  else
    		  this.delay = ((int)Math.max(5.0F, (20.0F - this.theGolem.getGolemStrength() * 3.0F) * this.block.getBlockHardness(this.theWorld, this.xx, this.yy, this.zz)));
        this.maxDelay = this.delay;
        this.mod = (this.delay / Math.round(this.delay / 6.0F));
      }
      
      if (this.delay > 0) {
        if ((--this.delay > 0) && (this.delay % this.mod == 0) && (this.theGolem.getNavigator().noPath())) {
          this.theGolem.startActionTimer();
          this.theWorld.playSoundEffect(this.xx + 0.5F, this.yy + 0.5F, this.zz + 0.5F, this.block.stepSound.getStepResourcePath(), (this.block.stepSound.getVolume() + 0.7F) / 8.0F, this.block.stepSound.getPitch() * 0.5F);
          
          BlockUtils.destroyBlockPartially(this.theWorld, this.theGolem.getEntityId(), this.xx, this.yy, this.zz, (int)(9.0F * (1.0F - this.delay / this.maxDelay)));
        }
        

        if (this.delay == 0) {
          harvest();
          
          if (Utils.isWoodLog(this.theWorld, this.xx, this.yy, this.zz)) {
            this.delay = -1;
            this.block = this.theWorld.getBlock(this.xx, this.yy, this.zz);
            this.blockMd = this.theWorld.getBlockMetadata(this.xx, this.yy, this.zz);
            startExecuting();
          } else {
            checkAdjacent();
          }
        }
      }
    }
  }
  



  private void checkAdjacent()
  {
    for (int x2 = -1; x2 <= 1; x2++) {
      for (int z2 = -1; z2 <= 1; z2++) {
        for (int y2 = -1; y2 <= 1; y2++) {
          int x = this.xx + x2;
          int y = this.yy + y2;
          int z = this.zz + z2;
          
          if ((Math.abs(this.theGolem.getHomePosition().posX - x) <= this.distance) && (Math.abs(this.theGolem.getHomePosition().posY - y) <= this.distance) && (Math.abs(this.theGolem.getHomePosition().posZ - z) <= this.distance))
          {



            if (Utils.isWoodLog(this.theWorld, x, y, z))
            {
              Vec3 var1 = Vec3.createVectorHelper(x, y, z);
              
              if (var1 != null)
              {
                this.xx = ((int)var1.xCoord);
                this.yy = ((int)var1.yCoord);
                this.zz = ((int)var1.zCoord);
                
                this.block = this.theWorld.getBlock(this.xx, this.yy, this.zz);
                this.blockMd = this.theWorld.getBlockMetadata(this.xx, this.yy, this.zz);
                
                this.delay = -1;
                
                startExecuting();
                return;
              }
            } }
        }
      }
    }
  }
  
  public void resetTask() {
    BlockUtils.destroyBlockPartially(this.theWorld, this.theGolem.getEntityId(), this.xx, this.yy, this.zz, -1);
    this.delay = -1;
  }
  



  public void startExecuting()
  {
    this.count = 200;
    this.theGolem.getNavigator().tryMoveToXYZ(this.xx + 0.5D, this.yy + 0.5D, this.zz + 0.5D, this.theGolem.getAIMoveSpeed());
  }
  
  void harvest() {
    this.count = 200;
    this.theWorld.playAuxSFX(2001, this.xx, this.yy, this.zz, Block.getIdFromBlock(this.block) + (this.blockMd << 12));
    BlockUtils.breakFurthestBlock(this.theWorld, this.xx, this.yy, this.zz, this.block, this.player);
    this.theGolem.startActionTimer();
  }
  

  private Vec3 findLog()
  {
    Random rand = this.theGolem.getRNG();
    
    for (int var2 = 0; var2 < this.distance * 4.0F; var2++)
    {
      int x = (int)(this.theGolem.getHomePosition().posX + rand.nextInt((int)(1.0F + this.distance * 2.0F)) - this.distance);
      int y = (int)(this.theGolem.getHomePosition().posY + rand.nextInt((int)(1.0F + this.distance)) - this.distance / 2.0F);
      int z = (int)(this.theGolem.getHomePosition().posZ + rand.nextInt((int)(1.0F + this.distance * 2.0F)) - this.distance);
      if (Utils.isWoodLog(this.theWorld, x, y, z)) {
        Vec3 v = Vec3.createVectorHelper(x, y, z);
        double dist = this.theGolem.getDistanceSq(x + 0.5D, y + 0.5D, z + 0.5D);
        int yy = 1;
        while ((Utils.isWoodLog(this.theWorld, x, y - yy, z)) && (this.theGolem.getDistanceSq(x + 0.5D, y - yy + 0.5D, z + 0.5D) < dist)) {
          v = Vec3.createVectorHelper(x, y - yy, z);
          dist = this.theGolem.getDistanceSq(x + 0.5D, y - yy + 0.5D, z + 0.5D);
          yy++;
        }
        
        return v;
      }
    }
    return null;
  }
}
