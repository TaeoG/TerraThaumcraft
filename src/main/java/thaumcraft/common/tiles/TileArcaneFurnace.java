
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package thaumcraft.common.tiles;

import com.bioxx.tfc.Core.ItemHeat;
import com.bioxx.tfc.Core.Metal.MetalRegistry;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.api.*;
import com.bioxx.tfc.api.Interfaces.ICookableFood;
import com.bioxx.tfc.api.Interfaces.ISmeltable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.visnet.VisNetHandler;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.tiles.TileBellows;

import java.util.Random;

public class TileArcaneFurnace extends TileThaumcraft {
    private ItemStack[] furnaceItemStacks = new ItemStack[32];
    public int furnaceCookTime = 0;
    public int furnaceMaxCookTime = 0;
    public int speedyTime = 0;
    public int facingX = -5;
    public int facingZ = -5;

    public TileArcaneFurnace() {
    }

    public int getSizeInventory() {
        return this.furnaceItemStacks.length;
    }

    public ItemStack getStackInSlot(int i) {
        return this.furnaceItemStacks[i];
    }

    public ItemStack decrStackSize(int i, int j) {
        if(this.furnaceItemStacks[i] != null) {
            ItemStack itemstack1;
            if(this.furnaceItemStacks[i].stackSize <= j) {
                itemstack1 = this.furnaceItemStacks[i];
                this.furnaceItemStacks[i] = null;
                this.markDirty();
                return itemstack1;
            } else {
                itemstack1 = this.furnaceItemStacks[i].splitStack(j);
                if(this.furnaceItemStacks[i].stackSize == 0) {
                    this.furnaceItemStacks[i] = null;
                }

                this.markDirty();
                return itemstack1;
            }
        } else {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.furnaceItemStacks[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    private int getInventoryStackLimit() {
        return 64;
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for(int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if(byte0 >= 0 && byte0 < this.furnaceItemStacks.length) {
                this.furnaceItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.furnaceCookTime = nbttagcompound.getShort("CookTime");
        this.speedyTime = nbttagcompound.getShort("SpeedyTime");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort("CookTime", (short)this.furnaceCookTime);
        nbttagcompound.setShort("SpeedyTime", (short)this.speedyTime);
        NBTTagList nbttaglist = new NBTTagList();

        for(int i = 0; i < this.furnaceItemStacks.length; ++i) {
            if(this.furnaceItemStacks[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.furnaceItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }

    public void updateEntity() {
        super.updateEntity();
        if(this.facingX == -5) {
            this.getFacing();
        }

        if(!this.worldObj.isRemote) {
            boolean cookedflag = false;
            if(this.furnaceCookTime > 0) {
                --this.furnaceCookTime;
                cookedflag = true;
            }

            if(cookedflag && this.speedyTime > 0) {
                --this.speedyTime;
            }

            if(this.speedyTime <= 0) {
                this.speedyTime = VisNetHandler.drainVis(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Aspect.FIRE, 5);
            }

            if(this.furnaceMaxCookTime == 0) {
                this.furnaceMaxCookTime = this.calcCookTime();
            }

            if(this.furnaceCookTime > this.furnaceMaxCookTime) {
                this.furnaceCookTime = this.furnaceMaxCookTime;
            }

            int a;
            if(this.furnaceCookTime == 0 && cookedflag) {
                for(a = 0; a < this.getSizeInventory(); ++a) {
                    boolean smeltable = false;
                    if(this.furnaceItemStacks[a] != null) {
                        ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[a]);
                        ItemStack tempstack = null;
                        if(itemstack != null) {
                            tempstack = itemstack.copy();
                            smeltable = true;
                        }
                        else if(this.furnaceItemStacks[a].getItem() instanceof ISmeltable)
                        {
                            tempstack = this.furnaceItemStacks[a].copy();
                            float meltingpoint = HeatRegistry.getInstance().getMeltingPoint(tempstack);
                            TFC_ItemHeat.setTemp(tempstack, meltingpoint -1);
                            smeltable = true;
                        }
                        else if(this.furnaceItemStacks[a].getItem() instanceof ICookableFood)
                        {
                            tempstack = this.furnaceItemStacks[a].copy();
                            if(!Food.isCooked(tempstack))
                            {
								if(tempstack.getItem() == TFCItems.egg)
								{
									float weight = Food.getWeight(tempstack);
									float decay = Food.getDecay(tempstack);
									tempstack = new ItemStack(TFCItems.eggCooked);
									ItemFoodTFC.createTag(tempstack, weight,decay);
								}
                                int[] cookedProfile = new int[5];
                                int[] fuelProfile = new int[5];
                                int[] taste = new int[5];
                                Random randy = this.worldObj.rand;
                                int maxX2 = 200;
                                int maxAdjust = maxX2/2;
                                for(int i = 0; i < cookedProfile.length; i ++)
                                {
                                    cookedProfile[i] = randy.nextInt(maxX2) - maxAdjust;
                                    fuelProfile[i] = randy.nextInt(maxX2) - maxAdjust;
                                    taste[i] = randy.nextInt(maxX2) - maxAdjust;
                                }
                                Food.setCooked(tempstack, randy.nextInt(700) + 600);
                                Food.setCookedProfile(tempstack, cookedProfile);
                                Food.setFuelProfile(tempstack, fuelProfile);
                                Food.setBitterMod(tempstack, taste[0]);
                                Food.setSavoryMod(tempstack, taste[1]);
                                Food.setSaltyMod(tempstack, taste[2]);
                                Food.setSourMod(tempstack, taste[3]);
                                Food.setSweetMod(tempstack, taste[4]);


                            }
                            smeltable = true;
                        }
                        else if(this.furnaceItemStacks[a].getItem() instanceof ItemMeltedMetal)
                        {
                            tempstack = this.furnaceItemStacks[a].copy();
                            Metal metal = MetalRegistry.instance.getMetalFromItem(tempstack.getItem());
                            TFC_ItemHeat.setTemp(tempstack, Math.max(2200,HeatRegistry.getInstance().getMeltingPoint(new ItemStack(metal.ingot))+300));
                            smeltable = true;
                        }

                        if(smeltable)
                        {
                            tempstack.stackSize = 1;
                            this.ejectItem(tempstack.copy(), this.furnaceItemStacks[a]);
                            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, ConfigBlocks.blockArcaneFurnace, 3, 0);
                            --this.furnaceItemStacks[a].stackSize;
                            if(this.furnaceItemStacks[a].stackSize <= 0) {
                                this.furnaceItemStacks[a] = null;
                            }
                            break;
                        }

                    }
                }
            }

            if(this.furnaceCookTime == 0 && !cookedflag) {
                for(a = 0; a < this.getSizeInventory(); ++a) {
                    if(this.furnaceItemStacks[a] != null && this.canSmelt(a)) {
                        this.furnaceMaxCookTime = this.calcCookTime();
                        this.furnaceCookTime = this.furnaceMaxCookTime;
                        break;
                    }
                }
            }
        }

    }

    private int getBellows() {
        int bellows = 0;
        ForgeDirection[] arr$ = ForgeDirection.VALID_DIRECTIONS;
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            ForgeDirection dir = arr$[i$];
            if(dir != ForgeDirection.UP) {
                int xx = this.xCoord + dir.offsetX * 2;
                int yy = this.yCoord + dir.offsetY * 2;
                int zz = this.zCoord + dir.offsetZ * 2;
                TileEntity tile = this.worldObj.getTileEntity(xx, yy, zz);
                if(tile != null && tile instanceof TileBellows && ((TileBellows)tile).orientation == dir.getOpposite().ordinal() && !this.worldObj.isBlockIndirectlyGettingPowered(xx, yy, zz)) {
                    ++bellows;
                }
            }
        }

        return Math.min(3, bellows);
    }

    private int calcCookTime() {
        return (this.speedyTime > 0?80:140) - 20 * this.getBellows();
    }

    public boolean addItemsToInventory(ItemStack items) {
        for(int a = 0; a < this.getSizeInventory(); ++a) {
            if(this.furnaceItemStacks[a] != null && this.furnaceItemStacks[a].isItemEqual(items) && this.furnaceItemStacks[a].stackSize + items.stackSize <= items.getMaxStackSize()) {
                this.furnaceItemStacks[a].stackSize += items.stackSize;
                if(!this.canSmelt(a)) {
                    this.destroyItem(a);
                }

                this.markDirty();
                return true;
            }

            if(this.furnaceItemStacks[a] == null) {
                this.setInventorySlotContents(a, items);
                if(!this.canSmelt(a)) {
                    this.destroyItem(a);
                }

                this.markDirty();
                return true;
            }
        }

        return false;
    }

    private void destroyItem(int slot) {
        this.furnaceItemStacks[slot] = null;
        this.worldObj.playSound((double)((float)this.xCoord + 0.5F), (double)((float)this.yCoord + 0.5F), (double)((float)this.zCoord + 0.5F), "random.fizz", 0.3F, 2.6F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.8F, false);
        double var21 = (double)((float)this.xCoord + this.worldObj.rand.nextFloat());
        double var22 = (double)(this.yCoord + 1);
        double var23 = (double)((float)this.zCoord + this.worldObj.rand.nextFloat());
        this.worldObj.spawnParticle("lava", var21, var22, var23, 0.0D, 0.0D, 0.0D);
    }

    private void getFacing() {
        this.facingX = 0;
        this.facingZ = 0;
        if(this.worldObj.getBlock(this.xCoord - 1, this.yCoord, this.zCoord) == ConfigBlocks.blockArcaneFurnace && this.worldObj.getBlockMetadata(this.xCoord - 1, this.yCoord, this.zCoord) == 10) {
            this.facingX = -1;
        } else if(this.worldObj.getBlock(this.xCoord + 1, this.yCoord, this.zCoord) == ConfigBlocks.blockArcaneFurnace && this.worldObj.getBlockMetadata(this.xCoord + 1, this.yCoord, this.zCoord) == 10) {
            this.facingX = 1;
        } else if(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord - 1) == ConfigBlocks.blockArcaneFurnace && this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord - 1) == 10) {
            this.facingZ = -1;
        } else {
            this.facingZ = 1;
        }

    }

    public void ejectItem(ItemStack items, ItemStack furnaceItemStack) {
        if(items != null) {
            ItemStack bit = items.copy();
            int bellows = this.getBellows();
            float lx = 0.5F;
            lx += (float)this.facingX * 1.2F;
            float lz = 0.5F;
            lz += (float)this.facingZ * 1.2F;
            float mx = this.facingX == 0?(this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.03F:(float)this.facingX * 0.13F;
            float mz = this.facingZ == 0?(this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.03F:(float)this.facingZ * 0.13F;
            EntityItem entityitem = new EntityItem(this.worldObj, (double)((float)this.xCoord + lx), (double)((float)this.yCoord + 0.4F), (double)((float)this.zCoord + lz), items);
            entityitem.motionX = (double)mx;
            entityitem.motionZ = (double)mz;
            entityitem.motionY = 0.0D;
            this.worldObj.spawnEntityInWorld(entityitem);
            if(ThaumcraftApi.getSmeltingBonus(furnaceItemStack) != null) {
                ItemStack var2 = ThaumcraftApi.getSmeltingBonus(furnaceItemStack).copy();
                if(var2 != null) {
                    if(bellows == 0) {
                        if(this.worldObj.rand.nextInt(4) == 0) {
                            ++var2.stackSize;
                        }
                    } else {
                        for(int var3 = 0; var3 < bellows; ++var3) {
                            if(this.worldObj.rand.nextFloat() < 0.44F) {
                                ++var2.stackSize;
                            }
                        }
                    }
                }

                if(var2 != null && var2.stackSize > 0) {
                    mx = this.facingX == 0?(this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.03F:(float)this.facingX * 0.13F;
                    mz = this.facingZ == 0?(this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.03F:(float)this.facingZ * 0.13F;
                    EntityItem var15 = new EntityItem(this.worldObj, (double)((float)this.xCoord + lx), (double)((float)this.yCoord + 0.4F), (double)((float)this.zCoord + lz), var2);
                    var15.motionX = (double)mx;
                    var15.motionZ = (double)mz;
                    var15.motionY = 0.0D;
                    this.worldObj.spawnEntityInWorld(var15);
                }
            }

            int var14 = items.stackSize;
            float var16 = FurnaceRecipes.smelting().func_151398_b(bit);
            int var4;
            if(var16 == 0.0F) {
                var14 = 0;
            } else if(var16 < 1.0F) {
                var4 = MathHelper.floor_float((float)var14 * var16);
                if(var4 < MathHelper.ceiling_float_int((float)var14 * var16) && (float)Math.random() < (float)var14 * var16 - (float)var4) {
                    ++var4;
                }

                var14 = var4;
            }

            while(var14 > 0) {
                var4 = EntityXPOrb.getXPSplit(var14);
                var14 -= var4;
                EntityXPOrb xp = new EntityXPOrb(this.worldObj, (double)((float)this.xCoord + lx), (double)((float)this.yCoord + 0.4F), (double)((float)this.zCoord + lz), var4);
                mx = this.facingX == 0?(this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.025F:(float)this.facingX * 0.13F;
                mz = this.facingZ == 0?(this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.025F:(float)this.facingZ * 0.13F;
                xp.motionX = (double)mx;
                xp.motionZ = (double)mz;
                xp.motionY = 0.0D;
                this.worldObj.spawnEntityInWorld(xp);
            }

        }
    }
    //Changed for TTC
    private boolean canSmelt(int slotIn) {
        ItemStack smeltee = this.furnaceItemStacks[slotIn];
        if(this.furnaceItemStacks[slotIn] == null) {
            return false;
        } else if(FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[slotIn]) != null
                || smeltee.getItem() instanceof ISmeltable
                || smeltee.getItem() instanceof ICookableFood
                || smeltee.getItem() instanceof ItemMeltedMetal)
        {
            return true;
        } else return false;
    }

    public boolean receiveClientEvent(int i, int j) {
        if(i != 3) {
            return super.receiveClientEvent(i, j);
        } else {
            if(this.worldObj.isRemote) {
                for(int a = 0; a < 5; ++a) {
                    Thaumcraft.proxy.furnaceLavaFx(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.facingX, this.facingZ);
                    this.worldObj.playSound((double)((float)this.xCoord + 0.5F), (double)((float)this.yCoord + 0.5F), (double)((float)this.zCoord + 0.5F), "liquid.lavapop", 0.1F + this.worldObj.rand.nextFloat() * 0.1F, 0.9F + this.worldObj.rand.nextFloat() * 0.15F, false);
                }
            }

            return true;
        }
    }
}
