
package thaumcraft.common.entities.golems;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import org.apache.logging.log4j.Level;

import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;

import taeo.terrathaumcraft.entity.ai.AIFishTTC;
import taeo.terrathaumcraft.entity.ai.AIHarvestCropsTTC;
import taeo.terrathaumcraft.entity.ai.AIHarvestLogsTTC;
import taeo.terrathaumcraft.entity.ai.AINearestButcherTargetTTC;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.InventoryMob;
import thaumcraft.common.entities.ai.combat.AIAvoidCreeperSwell;
import thaumcraft.common.entities.ai.combat.AIDartAttack;
import thaumcraft.common.entities.ai.combat.AIGolemAttackOnCollide;
import thaumcraft.common.entities.ai.combat.AIHurtByTarget;
import thaumcraft.common.entities.ai.combat.AINearestAttackableTarget;
import thaumcraft.common.entities.ai.fluid.AIEssentiaEmpty;
import thaumcraft.common.entities.ai.fluid.AIEssentiaGather;
import thaumcraft.common.entities.ai.fluid.AIEssentiaGoto;
import thaumcraft.common.entities.ai.fluid.AILiquidEmpty;
import thaumcraft.common.entities.ai.fluid.AILiquidGoto;
import thaumcraft.common.entities.ai.interact.AIUseItem;
import thaumcraft.common.entities.ai.inventory.AIEmptyDrop;
import thaumcraft.common.entities.ai.inventory.AIEmptyGoto;
import thaumcraft.common.entities.ai.inventory.AIFillGoto;
import thaumcraft.common.entities.ai.inventory.AIFillTake;
import thaumcraft.common.entities.ai.inventory.AIHomeDrop;
import thaumcraft.common.entities.ai.inventory.AIHomePlace;
import thaumcraft.common.entities.ai.inventory.AIHomeReplace;
import thaumcraft.common.entities.ai.inventory.AIHomeTake;
import thaumcraft.common.entities.ai.inventory.AIHomeTakeSorting;
import thaumcraft.common.entities.ai.inventory.AIItemPickup;
import thaumcraft.common.entities.ai.inventory.AISortingGoto;
import thaumcraft.common.entities.ai.inventory.AISortingPlace;
import thaumcraft.common.entities.ai.misc.AIOpenDoor;
import thaumcraft.common.entities.projectile.EntityDart;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.utils.EntityUtils;
import thaumcraft.common.lib.utils.InventoryUtils;
import thaumcraft.common.lib.utils.Utils;

public class EntityGolemBase extends EntityGolem implements cpw.mods.fml.common.registry.IEntityAdditionalSpawnData, ICausesDamage
{
  public InventoryMob inventory = new InventoryMob(this, 1);
  public ItemStack itemCarried;
  public FluidStack fluidCarried;
  public ItemStack itemWatched = null;
  
  public Aspect essentia;
  public int essentiaAmount;
  public boolean advanced = false;
  public int homeFacing = 0;
  public boolean paused = false;
  public boolean inactive = false;
  public boolean flag = false;
  
  public byte[] colors = null;
  public byte[] upgrades = null;
  public String decoration = "";
  public float bootup = -1.0F;
  
  public EnumGolemType golemType = EnumGolemType.WOOD;
  
  public EntityGolemBase(World par1World)
  {
    super(par1World);
    this.dataWatcher.addObject(30, Byte.valueOf((byte)(int)getMaxHealth()));
    this.stepHeight = 1.0F;
    this.colors = new byte[] { -1 };
    this.upgrades = new byte[] { -1 };
    setSize(0.4F, 0.95F);
    

    getNavigator().setBreakDoors(true);
    getNavigator().setEnterDoors(true);
    getNavigator().setCanSwim(true);
    func_110163_bv();
  }
  

  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
    getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6D);
    getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
  }
  

  public EntityGolemBase(World par0World, EnumGolemType type, boolean adv)
  {
    this(par0World);
    this.golemType = type;
    this.advanced = adv;
    this.upgrades = new byte[this.golemType.upgrades + (this.advanced ? 1 : 0)];
    for (int a = 0; a < this.upgrades.length; a++) this.upgrades[a] = -1;
  }
  
  public boolean setupGolemInventory() {
    ItemStack core = null;
    if (!ItemGolemCore.hasInventory(getCore())) { return false;
    }
    if (getCore() > -1) {
      int invSize = 0;
      switch (getCore()) {
      default: 
        invSize = 6;
        invSize += getUpgradeAmount(2) * 6;
        break;
      case 5: 
        invSize = 1;
        invSize += getUpgradeAmount(2);
        break;
      }
      
      
      InventoryMob inventory2 = new InventoryMob(this, invSize);
      for (int a = 0; a < this.inventory.inventory.length; a++) {
        inventory2.inventory[a] = this.inventory.inventory[a];
      }
      this.inventory = inventory2;
    }
    
    byte[] oldcolors = this.colors;
    this.colors = new byte[this.inventory.slotCount];
    for (int a = 0; a < this.inventory.slotCount; a++) {
      this.colors[a] = -1;
      if (a < oldcolors.length) { this.colors[a] = oldcolors[a];
      }
    }
    return true;
  }
  
  public boolean setupGolem()
  {
    if (!this.worldObj.isRemote) { this.dataWatcher.updateObject(19, Byte.valueOf((byte)this.golemType.ordinal()));
    }
    if ((getGolemType() == EnumGolemType.STONE) || (getGolemType() == EnumGolemType.IRON) || (getGolemType() == EnumGolemType.THAUMIUM))
    {

      getNavigator().setAvoidsWater(false);
    } else {
      getNavigator().setAvoidsWater(true);
    }
    
    if (getGolemType().fireResist) {
      this.isImmuneToFire = true;
    }
    
    int bonus = 0;
    try { bonus = getGolemDecoration().contains("H") ? 5 : 0; } catch (Exception e) {}
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getGolemType().health + bonus);
    
    //int damage = 2 + getGolemStrength() + getUpgradeAmount(1);
    int damage = 40 + getGolemStrength()*20 + getUpgradeAmount(1)*20;
    //try { if (getGolemDecoration().contains("M")) damage += 2; } catch (Exception e) {}
    try { if (getGolemDecoration().contains("M")) damage += 40; } catch (Exception e) {}
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(damage);
    

    this.tasks.taskEntries.clear();
    


    if (getCore() > -1) {
      this.tasks.addTask(0, new AIAvoidCreeperSwell(this));
    }
    

    switch (getCore()) {
    case 0: 
      this.tasks.addTask(0, new AIHomeReplace(this));
      this.tasks.addTask(1, new AIHomePlace(this));
      this.tasks.addTask(2, new AIHomeDrop(this));
      this.tasks.addTask(3, new AIFillTake(this));
      this.tasks.addTask(4, new AIFillGoto(this));
      break;
    case 1: 
      this.tasks.addTask(0, new AIHomeReplace(this));
      this.tasks.addTask(1, new thaumcraft.common.entities.ai.inventory.AIEmptyPlace(this));
      this.tasks.addTask(2, new AIEmptyDrop(this));
      this.tasks.addTask(3, new AIEmptyGoto(this));
      this.tasks.addTask(4, new AIHomeTake(this));
      break;
    case 2: 
      this.tasks.addTask(0, new AIHomeReplace(this));
      this.tasks.addTask(1, new AIHomePlace(this));
      this.tasks.addTask(2, new AIItemPickup(this));
      break;
    case 3: 
      this.tasks.addTask(2, new AIHarvestCropsTTC(this));
      break;
    case 4: 
      if (this.decoration.contains("R")) this.tasks.addTask(2, new AIDartAttack(this));
      this.tasks.addTask(3, new AIGolemAttackOnCollide(this));
      this.targetTasks.addTask(1, new AIHurtByTarget(this, false));
      this.targetTasks.addTask(2, new AINearestAttackableTarget(this, 0, true));
      break;
    case 5: 
      this.tasks.addTask(1, new AILiquidEmpty(this));
      this.tasks.addTask(2, new thaumcraft.common.entities.ai.fluid.AILiquidGather(this));
      this.tasks.addTask(3, new AILiquidGoto(this));
      break;
    case 6: 
      this.tasks.addTask(1, new AIEssentiaEmpty(this));
      this.tasks.addTask(2, new AIEssentiaGather(this));
      this.tasks.addTask(3, new AIEssentiaGoto(this));
      break;
    case 7: 
      this.tasks.addTask(2, new AIHarvestLogsTTC(this));
      break;
    case 8: 
      this.tasks.addTask(0, new AIHomeReplace(this));
      this.tasks.addTask(0, new AIUseItem(this));
      this.tasks.addTask(4, new AIHomeTake(this));
      break;
    case 9: 
      if (this.decoration.contains("R")) this.tasks.addTask(2, new AIDartAttack(this));
      this.tasks.addTask(3, new AIGolemAttackOnCollide(this));
      this.targetTasks.addTask(1, new AINearestButcherTargetTTC(this, 0, true));
      break;
    case 10: 
      this.tasks.addTask(0, new AIHomeReplace(this));
      this.tasks.addTask(1, new AISortingPlace(this));
      this.tasks.addTask(3, new AISortingGoto(this));
      this.tasks.addTask(4, new AIHomeTakeSorting(this));
      break;
    case 11: 
      this.tasks.addTask(2, new AIFishTTC(this));
    }
    
    
    if (getCore() > -1) {
      this.tasks.addTask(5, new AIOpenDoor(this, true));
      this.tasks.addTask(6, new thaumcraft.common.entities.ai.misc.AIReturnHome(this));
      this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.tasks.addTask(8, new EntityAILookIdle(this));
    }
    
    return true;
  }
  
  public int getCarryLimit() {
    int base = this.golemType.carry;
    if (this.worldObj.isRemote) base = getGolemType().carry;
    base += Math.min(16, Math.max(4, base)) * getUpgradeAmount(1);
    return base;
  }
  
  public int getFluidCarryLimit() {
    return MathHelper.floor_double(Math.sqrt(getCarryLimit())) * 1000;
  }
  
  public float getAIMoveSpeed()
  {
    if ((this.paused) || (this.inactive)) return 0.0F;
    float speed = this.golemType.speed * (this.decoration.contains("B") ? 1.1F : 1.0F);
    if (this.decoration.contains("P")) speed *= 0.88F;
    speed *= (1.0F + getUpgradeAmount(0) * 0.15F);
    if (this.advanced) { speed *= 1.1F;
    }
    if ((isInWater()) && ((getGolemType() == EnumGolemType.STONE) || (getGolemType() == EnumGolemType.IRON) || (getGolemType() == EnumGolemType.THAUMIUM)))
    {

      speed *= 2.0F;
    }
    
    return speed;
  }
  
  public void setup(int side)
  {
    this.homeFacing = side;
    
    setupGolem();
    setupGolemInventory();
  }
  

  protected void entityInit()
  {
    super.entityInit();
    getDataWatcher().addObjectByDataType(16, 5);
    this.dataWatcher.addObject(17, "");
    this.dataWatcher.addObject(18, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(19, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(20, String.valueOf(""));
    this.dataWatcher.addObject(21, Byte.valueOf((byte)-1));
    this.dataWatcher.addObject(22, String.valueOf(""));
    this.dataWatcher.addObject(23, String.valueOf(""));
  }
  





  public boolean isAIEnabled()
  {
    return (!this.paused) && (!this.inactive);
  }
  

  public void onLivingUpdate()
  {
    super.onLivingUpdate();
    if (this.action > 0) this.action -= 1;
    if (this.leftArm > 0) this.leftArm -= 1;
    if (this.rightArm > 0) this.rightArm -= 1;
    if (this.healing > 0) { this.healing -= 1;
    }
    int xx = MathHelper.floor_double(this.posX);
    int yy = MathHelper.floor_double(this.posY);
    int zz = MathHelper.floor_double(this.posZ);
    if ((yy > 0) && (this.worldObj.getBlock(xx, yy - 1, zz) == ConfigBlocks.blockCosmeticSolid) && (this.worldObj.getBlockMetadata(xx, yy - 1, zz) == 10))
    {
      this.inactive = true;
    } else {
      this.inactive = false;
    }
    
    if (!this.worldObj.isRemote)
    {
      if (this.regenTimer > 0) {
        this.regenTimer -= 1;
      } else {
        this.regenTimer = this.golemType.regenDelay;
        if (this.decoration.contains("F")) this.regenTimer = ((int)(this.regenTimer * 0.66F));
        if ((!this.worldObj.isRemote) && (getHealth() < getMaxHealth())) {
          this.worldObj.setEntityState(this, (byte)5);
          heal(1.0F);
        }
      }
      
      if ((getDistanceSq(getHomePosition().posX, getHomePosition().posY, getHomePosition().posZ) >= 2304.0D) || (isEntityInsideOpaqueBlock()))
      {

        int var1 = MathHelper.floor_double(getHomePosition().posX);
        int var2 = MathHelper.floor_double(getHomePosition().posZ);
        int var3 = MathHelper.floor_double(getHomePosition().posY);
        for (int var0 = 1; var0 >= -1; var0--)
        {
          for (int var4 = -1; var4 <= 1; var4++)
          {
            for (int var5 = -1; var5 <= 1; var5++)
            {
              if ((World.doesBlockHaveSolidTopSurface(this.worldObj, var1 + var4, var3 - 1 + var0, var2 + var5)) && (!this.worldObj.isBlockNormalCubeDefault(var1 + var4, var3 + var0, var2 + var5, false)))
              {

                setLocationAndAngles(var1 + var4 + 0.5F, var3 + var0, var2 + var5 + 0.5F, this.rotationYaw, this.rotationPitch);
                
                getNavigator().clearPathEntity();
                return;
              }
              
            }
          }
        }
      }
    }
    else if ((this.bootup > 0.0F) && (getCore() > -1)) {
      this.bootup *= this.bootup / 33.099998F;
      this.worldObj.playSound(this.posX, this.posY, this.posZ, "thaumcraft:cameraticks", this.bootup * 0.2F, 1.0F * this.bootup, false);
    }
  }
  

  public int regenTimer = 0;
  
  public float getRange() {
    float dmod = 16 + getUpgradeAmount(3) * 4;
    if (this.decoration.contains("G")) dmod += Math.max(dmod * 0.1F, 1.0F);
    if (this.advanced) dmod += Math.max(dmod * 0.2F, 2.0F);
    return dmod;
  }
  



  public boolean isWithinHomeDistance(int par1, int par2, int par3)
  {
    float dmod = getRange();
    return getHomePosition().getDistanceSquared(par1, par2, par3) < dmod * dmod;
  }
  

  protected void updateEntityActionState()
  {
    this.entityAge += 1;
    despawnEntity();
    
    boolean vara = isInWater();
    boolean varb = handleLavaMovement();
    
    if ((vara) || (varb))
    {
      this.isJumping = (this.rand.nextFloat() < 0.8F);
    }
  }
  



  public void onDeath(DamageSource ds)
  {
    if (!this.worldObj.isRemote) {
      FMLCommonHandler.instance().getFMLLogger().log(Level.INFO, "[Thaumcraft] " + this + " was killed by " + ds.getSourceOfDamage() + " (" + ds.getDamageType() + ")");
    }
    
    super.onDeath(ds);
  }
  
  public void setFire(int par1)
  {
    if (!this.golemType.fireResist) {
      super.setFire(par1);
    }
  }
  

  protected boolean canDespawn()
  {
    return false;
  }
  



  protected void despawnEntity() {}
  



  public int decreaseAirSupply(int par1)
  {
    return par1;
  }
  

  public short getColors(int slot)
  {
    char[] chars = this.dataWatcher.getWatchableObjectString(22).toCharArray();
    if (slot < chars.length) {
      if (("" + chars[slot]).equals("h")) {
        return -1;
      }
      return Short.parseShort("" + chars[slot], 16);
    }
    return -1;
  }
  
  public void setColors(int slot, int color)
  {
    this.colors[slot] = ((byte)color);
    String s = "";
    for (byte c : this.colors) {
      if (c == -1) {
        s = s + "h";
      } else
        s = s + Integer.toHexString(c);
    }
    this.dataWatcher.updateObject(22, String.valueOf(s));
  }
  
  public byte getUpgrade(int slot)
  {
    char[] chars = this.dataWatcher.getWatchableObjectString(23).toCharArray();
    if (slot < chars.length) {
      byte t = Byte.parseByte("" + chars[slot], 16);
      if (t == 15) return -1;
      return t;
    }
    return -1;
  }
  
  public int getUpgradeAmount(int type)
  {
    int a = 0;
    for (byte b : this.upgrades) {
      if (type == b) a++;
    }
    return a;
  }
  
  public void setUpgrade(int slot, byte upgrade)
  {
    this.upgrades[slot] = upgrade;
    String s = "";
    for (byte c : this.upgrades) s = s + Integer.toHexString(c);
    this.dataWatcher.updateObject(23, String.valueOf(s));
  }
  
  public ArrayList<Byte> getColorsMatching(ItemStack match) {
    ArrayList<Byte> l = new ArrayList();
    
    if ((this.inventory.inventory != null) && (this.inventory.inventory.length > 0)) {
      boolean allNull = true;
      for (int a = 0; a < this.inventory.inventory.length; a++) {
        if(this.inventory.getStackInSlot(a) != null) allNull = false;
        if (InventoryUtils.areItemStacksEqual(this.inventory.getStackInSlot(a), match, checkOreDict(), ignoreDamage(), ignoreNBT())) {
          l.add(Byte.valueOf(this.colors[a]));
        }
      }
      if (allNull) {
        for (int a = 0; a < this.inventory.inventory.length; a++)
          l.add(Byte.valueOf(this.colors[a]));
      }
    }
    return l;
  }
  



  public void writeEntityToNBT(NBTTagCompound nbt)
  {
    super.writeEntityToNBT(nbt);
    
    nbt.setInteger("HomeX", getHomePosition().posX);
    nbt.setInteger("HomeY", getHomePosition().posY);
    nbt.setInteger("HomeZ", getHomePosition().posZ);
    nbt.setByte("HomeFacing", (byte)this.homeFacing);
    nbt.setByte("GolemType", (byte)this.golemType.ordinal());
    nbt.setByte("Core", getCore());
    nbt.setString("Decoration", this.decoration);
    nbt.setByte("toggles", getTogglesValue());
    nbt.setBoolean("advanced", this.advanced);
    nbt.setByteArray("colors", this.colors);
    nbt.setByteArray("upgrades", this.upgrades);
    
    if ((getCore() == 5) && (this.fluidCarried != null)) {
      this.fluidCarried.writeToNBT(nbt);
    }
    
    if ((getCore() == 6) && (this.essentia != null) && (this.essentiaAmount > 0)) {
      nbt.setString("essentia", this.essentia.getTag());
      nbt.setByte("essentiaAmount", (byte)this.essentiaAmount);
    }
    
    NBTTagCompound var4 = new NBTTagCompound();
    if (this.itemCarried != null)
    {
      this.itemCarried.writeToNBT(var4);
    }
    nbt.setTag("ItemCarried", var4);
    
    if (getOwnerName() == null)
    {
      nbt.setString("Owner", "");
    }
    else
    {
      nbt.setString("Owner", getOwnerName());
    }
    

    NBTTagList tl = new NBTTagList();
    for (Marker l : this.markers) {
      NBTTagCompound nbtc = new NBTTagCompound();
      nbtc.setInteger("x", l.x);
      nbtc.setInteger("y", l.y);
      nbtc.setInteger("z", l.z);
      nbtc.setInteger("dim", l.dim);
      nbtc.setByte("side", l.side);
      nbtc.setByte("color", l.color);
      tl.appendTag(nbtc);
    }
    nbt.setTag("Markers", tl);
    
    nbt.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
  }
  




  public void readEntityFromNBT(NBTTagCompound nbt)
  {
    super.readEntityFromNBT(nbt);
    
    int hx = nbt.getInteger("HomeX");
    int hy = nbt.getInteger("HomeY");
    int hz = nbt.getInteger("HomeZ");
    this.homeFacing = nbt.getByte("HomeFacing");
    setHomeArea(hx, hy, hz, 32);
    
    this.advanced = nbt.getBoolean("advanced");
    this.golemType = EnumGolemType.getType(nbt.getByte("GolemType"));
    
    setCore(nbt.getByte("Core"));
    
    if (getCore() == 5) {
      this.fluidCarried = FluidStack.loadFluidStackFromNBT(nbt);
    }
    
    if (getCore() == 6) {
      String s = nbt.getString("essentia");
      if (s != null) {
        this.essentia = Aspect.getAspect(s);
        if (this.essentia != null) {
          this.essentiaAmount = nbt.getByte("essentiaAmount");
        }
      }
    }
    
    setTogglesValue(nbt.getByte("toggles"));
    
    NBTTagCompound var4 = nbt.getCompoundTag("ItemCarried");
    this.itemCarried = ItemStack.loadItemStackFromNBT(var4);
    updateCarried();
    
    this.decoration = nbt.getString("Decoration");
    setGolemDecoration(this.decoration);
    
    String var2 = nbt.getString("Owner");
    
    if (var2.length() > 0)
    {
      setOwner(var2);
    }
    
    this.dataWatcher.updateObject(30, Byte.valueOf((byte)(int)getHealth()));
    
    NBTTagList nbttaglist = nbt.getTagList("Markers", 10);
    for (int i = 0; i < nbttaglist.tagCount(); i++)
    {
      NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
      int x = nbttagcompound1.getInteger("x");
      int y = nbttagcompound1.getInteger("y");
      int z = nbttagcompound1.getInteger("z");
      int dim = nbttagcompound1.getInteger("dim");
      byte s = nbttagcompound1.getByte("side");
      byte c = nbttagcompound1.getByte("color");
      this.markers.add(new Marker(x, y, z, (byte)dim, s, c));
    }
    
    this.upgrades = new byte[this.golemType.upgrades + (this.advanced ? 1 : 0)];
    int ul = this.upgrades.length;
    this.upgrades = nbt.getByteArray("upgrades");
    if (ul != this.upgrades.length) {
      byte[] tt = new byte[ul];
      for (int a = 0; a < ul; a++) tt[a] = -1;
      for (int a = 0; a < this.upgrades.length; a++) {
        if (a < ul) tt[a] = this.upgrades[a];
      }
      this.upgrades = tt;
    }
    

    String st = "";
    for (byte c : this.upgrades) st = st + Integer.toHexString(c);
    this.dataWatcher.updateObject(23, String.valueOf(st));
    
    setupGolem();
    setupGolemInventory();
    
    NBTTagList nbttaglist2 = nbt.getTagList("Inventory", 10);
    this.inventory.readFromNBT(nbttaglist2);
    
    this.colors = nbt.getByteArray("colors");
    
    byte[] oldcolors = this.colors;
    this.colors = new byte[this.inventory.slotCount];
    for (int a = 0; a < this.inventory.slotCount; a++) {
      this.colors[a] = -1;
      if (a < oldcolors.length) { this.colors[a] = oldcolors[a];
      }
    }
    st = "";
    for (byte c : this.colors) {
      if (c == -1) {
        st = st + "h";
      } else {
        st = st + Integer.toHexString(c);
      }
    }
    this.dataWatcher.updateObject(22, String.valueOf(st));
  }
  


  public String getOwnerName()
  {
    return this.dataWatcher.getWatchableObjectString(17);
  }
  
  public void setOwner(String par1Str)
  {
    this.dataWatcher.updateObject(17, par1Str);
  }
  
  public void setMarkers(ArrayList<Marker> markers)
  {
    this.markers = markers;
  }
  
  public ArrayList<Marker> getMarkers()
  {
    validateMarkers();
    return this.markers;
  }
  
  protected void validateMarkers() {
    ArrayList<Marker> newMarkers = new ArrayList();
    for (Marker marker : this.markers) {
      if (marker.dim == this.worldObj.provider.dimensionId)
      {



        newMarkers.add(marker);
      }
    }
    this.markers = newMarkers;
  }
  
  protected ArrayList<Marker> markers = new ArrayList();
  
  public EntityLivingBase getOwner()
  {
    return this.worldObj.getPlayerEntityByName(getOwnerName());
  }
  



  protected void damageEntity(DamageSource ds, float par2)
  {
    if ((ds.isFireDamage()) && (this.golemType.fireResist)) {
      return;
    }
    if ((ds == DamageSource.inWall) || (ds == DamageSource.outOfWorld)) {
      setLocationAndAngles(getHomePosition().posX + 0.5D, getHomePosition().posY + 0.5D, getHomePosition().posZ + 0.5D, 0.0F, 0.0F);
    }
    super.damageEntity(ds, par2);
    if (!this.worldObj.isRemote) this.dataWatcher.updateObject(30, Byte.valueOf((byte)(int)getHealth()));
  }
  
  public void heal(float par1)
  {
    super.heal(par1);
    try {
      if (!this.worldObj.isRemote) this.dataWatcher.updateObject(30, Byte.valueOf((byte)(int)getHealth()));
    }
    catch (Exception e) {}
  }
  
  public void setHealth(float par1)
  {
    super.setHealth(par1);
    try {
      if (!this.worldObj.isRemote) this.dataWatcher.updateObject(30, Byte.valueOf((byte)(int)getHealth()));
    } catch (Exception e) {}
  }
  
  public float getHealthPercentage() {
    return this.dataWatcher.getWatchableObjectByte(30) / getMaxHealth();
  }
  
  public void setCarried(ItemStack stack)
  {
    this.itemCarried = stack;
    updateCarried();
  }
  
  public boolean hasSomething() {
    return this.inventory.hasSomething();
  }
  
  public ItemStack getCarried()
  {
    if ((this.itemCarried != null) && (this.itemCarried.stackSize <= 0)) {
      setCarried(null);
    }
    return this.itemCarried;
  }
  
  public int getCarrySpace() {
    if (this.itemCarried == null) return getCarryLimit();
    return Math.min(getCarryLimit() - this.itemCarried.stackSize, this.itemCarried.getMaxStackSize() - this.itemCarried.stackSize);
  }
  






  public boolean[] getToggles()
  {
    return Utils.unpack(this.dataWatcher.getWatchableObjectByte(18));
  }
  
  public byte getTogglesValue()
  {
    return this.dataWatcher.getWatchableObjectByte(18);
  }
  
  public void setToggle(int index, boolean tog)
  {
    boolean[] fz = getToggles();
    fz[index] = tog;
    this.dataWatcher.updateObject(18, Byte.valueOf(Utils.pack(fz)));
  }
  
  public void setTogglesValue(byte tog)
  {
    this.dataWatcher.updateObject(18, Byte.valueOf(tog));
  }
  
  public boolean canAttackHostiles() {
    return getToggles()[1] == false;
  }
  
  public boolean canAttackAnimals() {
    return getToggles()[2] == false;
  }
  
  public boolean canAttackPlayers() {
    return getToggles()[3] == false;
  }
  
  public boolean canAttackCreepers() {
    return getToggles()[4] == false;
  }
  
  public boolean checkOreDict() {
    return getToggles()[5];
  }
  
  public boolean ignoreDamage() {
    return getToggles()[6];
  }
  
  public boolean ignoreNBT() {
    return getToggles()[7];
  }
  
  public EnumGolemType getGolemType() {
    return EnumGolemType.getType(this.dataWatcher.getWatchableObjectByte(19));
  }
  
  public int getGolemStrength() {
    return getGolemType().strength + getUpgradeAmount(1);
  }
  
  public void setCore(byte core)
  {
    this.dataWatcher.updateObject(21, Byte.valueOf(core));
  }
  
  public byte getCore() {
    return this.dataWatcher.getWatchableObjectByte(21);
  }
  
  public String getGolemDecoration() {
    return this.dataWatcher.getWatchableObjectString(20);
  }
  
  public void setGolemDecoration(String string) {
    this.dataWatcher.updateObject(20, String.valueOf(this.decoration));
  }
  
  public ItemStack getCarriedForDisplay()
  {
    if (this.dataWatcher.getWatchableObjectItemStack(16) != null)
      return this.dataWatcher.getWatchableObjectItemStack(16);
    return null;
  }
  
  public void updateCarried()
  {
    if (this.itemCarried != null) {
      getDataWatcher().updateObject(16, this.itemCarried.copy());
      getDataWatcher().setObjectWatched(16);
    }
    else if ((getCore() == 5) && (this.fluidCarried != null)) {
      getDataWatcher().updateObject(16, new ItemStack(Item.getItemById(this.fluidCarried.getFluidID()), 1, this.fluidCarried.amount));
      getDataWatcher().setObjectWatched(16);
    }
    else if (getCore() == 6) {
      ItemStack disp = new ItemStack(ConfigItems.itemJarFilled);
      int amt = (int)(64.0F * (this.essentiaAmount / getCarryLimit()));
      if ((this.essentia != null) && (this.essentiaAmount > 0)) {
        ((IEssentiaContainerItem)disp.getItem()).setAspects(disp, new AspectList().add(this.essentia, amt));
      }
      
      getDataWatcher().updateObject(16, disp);
      getDataWatcher().setObjectWatched(16);
    } else {
      getDataWatcher().addObjectByDataType(16, 5);
      getDataWatcher().setObjectWatched(16);
    }
  }
  

  protected float getSoundVolume()
  {
    return 0.1F;
  }
  
  boolean pdw = false;
  





  protected void dropFewItems(boolean par1, int par2)
  {
    dropStuff();
  }
  

  public void dropStuff()
  {
    if ((!this.worldObj.isRemote) && (this.itemCarried != null)) {
      entityDropItem(this.itemCarried, 0.5F);
    }
  }
  
  protected boolean addDecoration(String type, ItemStack itemStack)
  {
    if (this.decoration.contains(type)) return false;
    if (((type.equals("F")) || (type.equals("H"))) && ((this.decoration.contains("F")) || (this.decoration.contains("H")))) return false;
    if (((type.equals("G")) || (type.equals("V"))) && ((this.decoration.contains("G")) || (this.decoration.contains("V")))) return false;
    if (((type.equals("B")) || (type.equals("P"))) && ((this.decoration.contains("P")) || (this.decoration.contains("B")))) { return false;
    }
    this.decoration += type;
    
    if (!this.worldObj.isRemote) {
      setGolemDecoration(this.decoration);
      itemStack.stackSize -= 1;
      this.worldObj.playSoundAtEntity(this, "thaumcraft:cameraclack", 1.0F, 1.0F);
    }
    
    setupGolem();
    
    return true;
  }
  
  public boolean customInteraction(EntityPlayer player) {
    if ((player.inventory.getCurrentItem() != null) && (player.inventory.getCurrentItem().getItem() == ConfigItems.itemGolemBell))
    {
      return false;
    }
    if ((player.inventory.getCurrentItem() != null) && (player.inventory.getCurrentItem().getItem() == ConfigItems.itemGolemDecoration))
    {
      addDecoration(ItemGolemDecoration.getDecoChar(player.inventory.getCurrentItem().getItemDamage()), player.inventory.getCurrentItem());
      

      player.swingItem();
      return false;
    }
    if ((player.inventory.getCurrentItem() != null) && (player.inventory.getCurrentItem().getItem() == Items.cookie)) {
      player.inventory.consumeInventoryItem(Items.cookie);
      player.swingItem();
      
      for (int var3 = 0; var3 < 3; var3++)
      {
        double var4 = this.rand.nextGaussian() * 0.02D;
        double var6 = this.rand.nextGaussian() * 0.02D;
        double var8 = this.rand.nextGaussian() * 0.02D;
        this.worldObj.spawnParticle("heart", this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var4, var6, var8);
        this.worldObj.playSoundAtEntity(this, "random.eat", 0.3F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        int duration = 600;
        if (this.worldObj.isRemote) {
          if ((getActivePotionEffect(Potion.moveSpeed) != null) && (getActivePotionEffect(Potion.moveSpeed).getDuration() < 2400)) duration += getActivePotionEffect(Potion.moveSpeed).getDuration();
          addPotionEffect(new PotionEffect(Potion.moveSpeed.id, duration, 0));
        }
      }
      heal(5.0F);
      return false;
    }
    if ((getCore() > -1) && (ItemGolemCore.hasGUI(getCore())) && ((player.inventory.getCurrentItem() == null) || (!(player.inventory.getCurrentItem().getItem() instanceof ItemWandCasting))) && (!this.worldObj.isRemote))
    {
      player.openGui(Thaumcraft.instance, 0, this.worldObj, getEntityId(), 0, 0);
      return false;
    }
    
    return false;
  }
  



  public boolean interact(EntityPlayer player)
  {
    if (player.isSneaking()) { return false;
    }
    ItemStack itemstack = player.inventory.getCurrentItem();
    
    if ((getCore() > -1) && (itemstack != null) && (itemstack.getItem() == ConfigItems.itemGolemBell)) {
      return false;
    }
    if ((getCore() == -1) && (itemstack != null) && (itemstack.getItem() == ConfigItems.itemGolemCore) && (itemstack.getItemDamage() != 100))
    {

      setCore((byte)itemstack.getItemDamage());
      setupGolem();
      setupGolemInventory();
      itemstack.stackSize -= 1;
      if (itemstack.stackSize <= 0)
      {
        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
      }
      this.worldObj.playSoundAtEntity(this, "thaumcraft:upgrade", 0.5F, 1.0F);
      player.swingItem();
      this.worldObj.setEntityState(this, (byte)7);
      return true;
    }
    if ((itemstack != null) && (itemstack.getItem() == ConfigItems.itemGolemUpgrade))
    {
      for (int a = 0; a < this.upgrades.length; a++) {
        if ((getUpgrade(a) == -1) && (getUpgradeAmount(itemstack.getItemDamage()) < 2)) {
          setUpgrade(a, (byte)itemstack.getItemDamage());
          setupGolem();
          setupGolemInventory();
          itemstack.stackSize -= 1;
          if (itemstack.stackSize <= 0)
          {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
          }
          this.worldObj.playSoundAtEntity(this, "thaumcraft:upgrade", 0.5F, 1.0F);
          player.swingItem();
          return true;
        }
      }
      return false;
    }
    
    return customInteraction(player);
  }
  


  public int action = 0;
  public int leftArm = 0;
  public int rightArm = 0;
  public int healing = 0;
  
  public int getActionTimer() {
    return 3 - Math.abs(this.action - 3);
  }
  
  public void startActionTimer() {
    if (this.action == 0) {
      this.action = 6;
      this.worldObj.setEntityState(this, (byte)4);
    }
  }
  
  public void startLeftArmTimer() {
    if (this.leftArm == 0) {
      this.leftArm = 5;
      this.worldObj.setEntityState(this, (byte)6);
    }
  }
  
  public void startRightArmTimer() {
    if (this.rightArm == 0) {
      this.rightArm = 5;
      this.worldObj.setEntityState(this, (byte)8);
    }
  }
  
  @SideOnly(Side.CLIENT)
  public void handleHealthUpdate(byte par1)
  {
    if (par1 == 4)
    {
      this.action = 6;
    }
    else if (par1 == 5)
    {
      this.healing = 5;
      
      int bonus = 0;
      try { bonus = getGolemDecoration().contains("H") ? 5 : 0; } catch (Exception e) {}
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getGolemType().health + bonus);

    }
    else if (par1 == 6)
    {
      this.leftArm = 5;
    }
    else if (par1 == 8)
    {
      this.rightArm = 5;
    }
    else if (par1 == 7)
    {
      this.bootup = 33.0F;
    }
    else
    {
      super.handleHealthUpdate(par1);
    }
  }
  

  protected void updateFallState(double par1, boolean par3)
  {
    if ((par3) && (this.fallDistance > 0.0F))
    {
      int var4 = MathHelper.floor_double(this.posX);
      int var5 = MathHelper.floor_double(this.posY - 0.2000000029802322D - this.yOffset);
      int var6 = MathHelper.floor_double(this.posZ);
      Block var7 = this.worldObj.getBlock(var4, var5, var6);
      
      if ((this.worldObj.isAirBlock(var4, var5, var6)) && (this.worldObj.getBlock(var4, var5 - 1, var6) == net.minecraft.init.Blocks.fence))
      {
        var7 = this.worldObj.getBlock(var4, var5 - 1, var6);
      }
    }
    

    if (par3)
    {
      if (this.fallDistance > 0.0F)
      {
        fall(this.fallDistance);
        this.fallDistance = 0.0F;
      }
    }
    else if (par1 < 0.0D)
    {
      this.fallDistance = ((float)(this.fallDistance - par1));
    }
  }
  

  public EntityLivingBase getAttackTarget()
  {
    EntityLivingBase e = super.getAttackTarget();
    if ((e != null) && (!e.isEntityAlive())) e = null;
    return e;
  }
  

  public int getTotalArmorValue()
  {
    int var1 = super.getTotalArmorValue() + this.golemType.armor;
    
    if (this.decoration.contains("V")) var1++;
    if (this.decoration.contains("P")) { var1 += 4;
    }
    if (var1 > 20)
    {
      var1 = 20;
    }
    
    return var1;
  }
  


  public boolean attackEntityAsMob(Entity par1Entity)
  {
    float f = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
    int i = 0;
    
    if ((par1Entity instanceof EntityLivingBase))
    {
      f += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)par1Entity);
      i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)par1Entity);
    }
    
    boolean flag = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), f);
    
    if (flag)
    {
      if (this.decoration.contains("V")) {
        EntityUtils.setRecentlyHit((EntityLivingBase)par1Entity, 100);
      }
      
      if (i > 0)
      {
        par1Entity.addVelocity(-MathHelper.sin(this.rotationYaw * 3.141593F / 180.0F) * i * 0.5F, 0.1D, MathHelper.cos(this.rotationYaw * 3.141593F / 180.0F) * i * 0.5F);
        this.motionX *= 0.6D;
        this.motionZ *= 0.6D;
      }
      
      int j = EnchantmentHelper.getFireAspectModifier(this) + getUpgradeAmount(2);
      
      if (j > 0)
      {
        par1Entity.setFire(j * 4);
      }
      
      if ((par1Entity instanceof EntityLivingBase))
      {
        EnchantmentHelper.func_151384_a((EntityLivingBase)par1Entity, this);
      }
      
      EnchantmentHelper.func_151385_b(this, par1Entity);
    }
    
    return flag;
  }
  



  public boolean attackEntityFrom(DamageSource ds, float par2)
  {
    this.paused = false;
    if (ds == DamageSource.cactus) { return false;
    }
    if ((getGolemType() == EnumGolemType.THAUMIUM) && (ds == DamageSource.magic)) { par2 *= 0.5F;
    }
    if ((ds.getSourceOfDamage() != null) && (getUpgradeAmount(5) > 0) && (ds.getSourceOfDamage().getEntityId() != getEntityId()))
    {
      ds.getSourceOfDamage().attackEntityFrom(DamageSource.causeThornsDamage(this), getUpgradeAmount(5) * 2 + this.rand.nextInt(2 * getUpgradeAmount(5)));
      

      ds.getSourceOfDamage().playSound("damage.thorns", 0.5F, 1.0F);
    }
    
    return super.attackEntityFrom(ds, par2);
  }
  

  public boolean canAttackClass(Class par1Class)
  {
    return (EntityVillager.class != par1Class) && (EntityGolemBase.class != par1Class) && (EntityBat.class != par1Class);
  }
  


  public boolean isValidTarget(Entity target)
  {
    if (!target.isEntityAlive()) { return false;
    }
    if (((target instanceof EntityPlayer)) && (((EntityPlayer)target).getCommandSenderName().equals(getOwnerName()))) {
      return false;
    }
    if (!isWithinHomeDistance(MathHelper.floor_double(target.posX), MathHelper.floor_double(target.posY), MathHelper.floor_double(target.posZ)))
    {

      return false;
    }
    if (getCore() == 9) {
      if ((((target instanceof EntityAnimal)) || ((target instanceof IAnimals))) && (!(target instanceof IMob)) && ((!(target instanceof EntityTameable)) || (!((EntityTameable)target).isTamed())) && (!(target instanceof EntityGolem)))
      {
    	  //TODO added this clause
    	  if(target instanceof IAnimal)
    	  {
    		  IAnimal targetTFCAnimal = (IAnimal) target;
    		  if(targetTFCAnimal.isPregnant() || !targetTFCAnimal.isAdult())
    		  {
    			  return false;
    		  }
    	  }


        if (((target instanceof EntityAnimal)) && (((EntityAnimal)target).isChild())) { return false;
        }
        return true;
      }
    }
    else {
      if ((canAttackCreepers()) && (getUpgradeAmount(4) > 0) && ((target instanceof EntityCreeper)))
      {
        return true;
      }
      if ((canAttackHostiles()) && (((target instanceof net.minecraft.entity.monster.EntityMob)) || ((target instanceof IMob))) && (!(target instanceof EntityCreeper)))
      {

        return true;
      }
      if ((canAttackAnimals()) && (getUpgradeAmount(4) > 0) && (((target instanceof EntityAnimal)) || ((target instanceof IAnimals))) && (!(target instanceof IMob)) && ((!(target instanceof EntityTameable)) || (!((EntityTameable)target).isTamed())) && (!(target instanceof EntityGolem)))
      {



        return true;
      }
      if ((canAttackPlayers()) && (getUpgradeAmount(4) > 0) && ((target instanceof EntityPlayer)))
      {
        return true;
      }
    }
    return false;
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLiving)
  {
    EntityDart var2 = new EntityDart(this.worldObj, this, par1EntityLiving, 1.6F, 7.0F - getUpgradeAmount(3) * 1.75F);
    float f = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
    var2.setDamage(f * 0.4F);
    playSound("thaumcraft:golemironshoot", 0.5F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.6F));
    this.worldObj.spawnEntityInWorld(var2);
    startLeftArmTimer();
  }
  
  public int getAttackSpeed() {
    return 20 - (this.advanced ? 2 : 0);
  }
  


  protected String getLivingSound()
  {
    return "thaumcraft:cameraclack";
  }
  

  protected String getHurtSound()
  {
    return "thaumcraft:cameraclack";
  }
  

  protected String getDeathSound()
  {
    return "thaumcraft:cameraclack";
  }
  

  public void writeSpawnData(ByteBuf data)
  {
    data.writeByte(getCore());
    data.writeBoolean(this.advanced);
    data.writeByte(this.inventory.slotCount);
    data.writeByte(this.upgrades.length);
    for (byte b : this.upgrades) data.writeByte(b);
  }
  
  public void readSpawnData(ByteBuf data)
  {
    try {
      setCore(data.readByte());
      this.advanced = data.readBoolean();
      if (getCore() >= 0) this.bootup = 0.0F;
      this.inventory = new InventoryMob(this, data.readByte());
      
      this.colors = new byte[this.inventory.slotCount];
      for (int a = 0; a < this.inventory.slotCount; a++) { this.colors[a] = -1;
      }
      this.upgrades = new byte[data.readByte()];
      for (int a = 0; a < this.upgrades.length; a++) { this.upgrades[a] = data.readByte();
      }
      
      int bonus = 0;
      try {
        bonus = getGolemDecoration().contains("H") ? 5 : 0;
      }
      catch (Exception e) {}
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getGolemType().health + bonus);
    }
    catch (Exception e) {}
  }
  

  public String getCommandSenderName()
  {
    if (hasCustomNameTag()) {
      return getCustomNameTag();
    }
    return StatCollector.translateToLocal("item.ItemGolemPlacer." + getGolemType().ordinal() + ".name");
  }


@Override
public EnumDamageType getDamageType() {
	// TODO Auto-generated method stub
	return EnumDamageType.GENERIC;
}
}



/* Location:           D:\Taeo\Desktop\ThaumaFirmaCraft2\Thaumcraft-1.7.10-4.2.3.5.deobfnew.jar

 * Qualified Name:     thaumcraft.common.entities.golems.EntityGolemBase

 * JD-Core Version:    0.7.0.1

 */