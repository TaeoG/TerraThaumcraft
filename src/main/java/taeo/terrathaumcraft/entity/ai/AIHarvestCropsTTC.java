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

import com.bioxx.tfc.Items.ItemCustomSeeds;
import com.bioxx.tfc.TileEntities.TEBerryBush;
import com.bioxx.tfc.TileEntities.TEFruitLeaves;
import com.mojang.authlib.GameProfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockLog;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.ForgeDirection;
import taeo.terrathaumcraft.utility.CropUtilsTTC;
import thaumcraft.api.BlockCoordinates;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.golems.EntityGolemBase;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumcraft.common.lib.utils.CropUtils;
import thaumcraft.common.lib.utils.EntityUtils;

public class AIHarvestCropsTTC extends net.minecraft.entity.ai.EntityAIBase
{
	private EntityGolemBase theGolem;
	private int xx;
	private int yy;
	private int zz;
	private float movementSpeed;
	private float distance;
	private World theWorld;
	private Block block = Blocks.air;
	private int blockMd = 0;
	private int delay = -1;
	private int maxDelay = 1;
	private int mod = 1;
	private int count = 0;

	public AIHarvestCropsTTC(EntityGolemBase par1EntityCreature)
	{
		this.theGolem = par1EntityCreature;
		this.theWorld = par1EntityCreature.worldObj;
		setMutexBits(3);
		this.distance = MathHelper.ceiling_float_int(this.theGolem.getRange() / 4.0F);
	}




	public boolean shouldExecute()
	{
		if ((this.delay >= 0) || (this.theGolem.ticksExisted % Config.golemDelay > 0) || (!this.theGolem.getNavigator().noPath())) {
			return false;
		}

		Vec3 var1 = findGrownCrop();

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
		return (this.theWorld.getBlock(this.xx, this.yy, this.zz) == this.block) && (this.theWorld.getBlockMetadata(this.xx, this.yy, this.zz) == this.blockMd) && (this.count-- > 0) && ((this.delay > 0) || (!this.theGolem.getNavigator().noPath()));
	}



	public void updateTask()
	{
		double dist = this.theGolem.getDistanceSq(this.xx + 0.5D, this.yy + 0.5D, this.zz + 0.5D);
		this.theGolem.getLookHelper().setLookPosition(this.xx + 0.5D, this.yy + 0.5D, this.zz + 0.5D, 30.0F, 30.0F);
		if (dist <= 4.0D)
		{
			if (this.delay < 0) {
				this.delay = ((int)Math.max(10.0F, (20.0F - this.theGolem.getGolemStrength() * 2.0F) * this.block.getBlockHardness(this.theWorld, this.xx, this.yy, this.zz)));
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
					checkAdjacent();
				}
			}
		}
	}




	private void checkAdjacent()
	{
		for (int x2 = -2; x2 <= 2; x2++) {
			for (int z2 = -2; z2 <= 2; z2++) {
				for (int y2 = -1; y2 <= 1; y2++) {
					int x = this.xx + x2;
					int y = this.yy + y2;
					int z = this.zz + z2;

					if ((Math.abs(this.theGolem.getHomePosition().posX - x) <= this.distance) && (Math.abs(this.theGolem.getHomePosition().posY - y) <= this.distance) && (Math.abs(this.theGolem.getHomePosition().posZ - z) <= this.distance))
					{



						if (CropUtilsTTC.isGrownCrop(this.theWorld, x, y, z))
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

	ArrayList<BlockCoordinates> checklist = new ArrayList();


	private Vec3 findGrownCrop()
	{
		Random rand = this.theGolem.getRNG();

		if (this.checklist.size() == 0) {
			for (int a = (int)-this.distance; a <= this.distance; a++) {
				for (int b = (int)-this.distance; b <= this.distance; b++)
					this.checklist.add(new BlockCoordinates(this.theGolem.getHomePosition().posX + a, 0, this.theGolem.getHomePosition().posZ + b));
			}
			Collections.shuffle(this.checklist, rand);
		}

		int x = ((BlockCoordinates)this.checklist.get(0)).x;
		int z = ((BlockCoordinates)this.checklist.get(0)).z;
		this.checklist.remove(0);

		for (int y = this.theGolem.getHomePosition().posY - 3; 
				y <= this.theGolem.getHomePosition().posY + 3; y++)
		{
			if (CropUtilsTTC.isGrownCrop(this.theWorld, x, y, z))
			{




				return Vec3.createVectorHelper(x, y, z);
			}
		}

		return null;
	}

	void harvest()
	{
		this.count = 200;
		int md = this.blockMd;
		FakePlayer fp = FakePlayerFactory.get((WorldServer)this.theWorld, new GameProfile((UUID)null, "FakeThaumcraftGolem"));
		//fp.func_70107_b(this.theGolem.posX, this.theGolem.posY, this.theGolem.posZ);
		fp.moveEntity(this.theGolem.posX, this.theGolem.posY, this.theGolem.posZ);
		
		TileEntity te = theWorld.getTileEntity(xx, yy, zz);
		
		if (CropUtils.clickableCrops.contains(this.block.getUnlocalizedName() + md) || (te != null && (te instanceof TEFruitLeaves || te instanceof TEBerryBush))) {
			this.block.onBlockActivated(this.theWorld, this.xx, this.yy, this.zz, fp, 0, 0.0F, 0.0F, 0.0F);
		}
		else {
			CropUtilsTTC.golemHarvestTTC(theWorld, fp, xx, yy, zz);
			this.theWorld.func_147480_a(this.xx, this.yy, this.zz, true);


			if (this.theGolem.getUpgradeAmount(4) > 0) {
				ArrayList<ItemStack> items = new ArrayList();
				ArrayList<Entity> drops = EntityUtils.getEntitiesInRange(this.theWorld, this.theGolem.posX, this.theGolem.posY, this.theGolem.posZ, this.theGolem, EntityItem.class, 6.0D);
				if (drops.size() > 0) {
					for (Entity e : drops) {
						if ((e instanceof EntityItem)) {
							if (e.ticksExisted < 2) {
								Vec3 v = Vec3.createVectorHelper(e.posX - this.theGolem.posX, e.posY - this.theGolem.posY, e.posZ - this.theGolem.posZ);
								v = v.normalize();
								e.motionX = (-v.xCoord / 4.0D);
								e.motionY = 0.075D;
								e.motionZ = (-v.zCoord / 4.0D);
							}

							boolean done = false;
							EntityItem item = (EntityItem)e;
							ItemStack st = item.getEntityItem();

							if ((st.getItem() != null) && (st.getItem() == Items.dye) && (st.getItemDamage() == 3)) {
								// int var5 = BlockDirectional.func_149895_l(this.blockMd);
								int var5 = BlockDirectional.getDirection(this.blockMd);
								int par2 = this.xx + net.minecraft.util.Direction.offsetX[var5];
								int par4 = this.zz + net.minecraft.util.Direction.offsetZ[var5];
								Block var6 = this.theWorld.getBlock(par2, this.yy, par4);
								if ((var6 == Blocks.log) && (BlockLog.func_150165_c(this.theWorld.getBlockMetadata(par2, this.yy, par4)) == 3)) {
									st.stackSize -= 1;
									//this.theWorld.setBlock(this.xx, this.yy, this.zz, Blocks.cocoa, BlockDirectional.func_149895_l(this.blockMd), 3);
									this.theWorld.setBlock(this.xx, this.yy, this.zz, Blocks.cocoa, BlockDirectional.getDirection(this.blockMd), 3);
								}
								done = true;
							}
							else if ((st.getItem() != null) && (st.getItem() == ConfigItems.itemManaBean)) {
								if (this.block.canPlaceBlockOnSide(this.theWorld, this.xx, this.yy, this.zz, 0)) {
									st.stackSize -= 1;
									if (!st.getItem().onItemUse(st.copy(), fp, this.theWorld, this.xx, this.yy + 1, this.zz, 0, 0.5F, 0.5F, 0.5F)) {
										this.theWorld.setBlock(this.xx, this.yy, this.zz, ConfigBlocks.blockManaPod, 0, 3);
									}
								}
								done = true;



							}
							else
							{


								int[] xm = { 0, 0, 1, 1, -1, 0, -1, -1, 1 };
								int[] zm = { 0, 1, 0, 1, 0, -1, -1, 1, -1 };
								int count = 0;
								while ((st != null) && (st.stackSize > 0) && (count < 9)) {
									if ((st.getItem() != null) && (((st.getItem() instanceof net.minecraftforge.common.IPlantable)) || ((st.getItem() instanceof ItemSeedFood)) || (st.getItem() instanceof ItemCustomSeeds)))
									{

										if (st.getItem().onItemUse(st.copy(), fp, this.theWorld, this.xx + xm[count], this.yy - 1, this.zz + zm[count], ForgeDirection.UP.ordinal(), 0.5F, 0.5F, 0.5F)) {
											st.stackSize -= 1;
										}
									}
									count++;
								}
							}

							if (st.stackSize <= 0) {
								item.setDead();
							} else {
								item.setEntityItemStack(st);
							}

							if (done)
								break;
						}
					}
				}
			}
		}
		//fp.func_70106_y();
		fp.setDead();
		this.theGolem.startActionTimer();
	}
}

