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

package taeo.terrathaumcraft.block;

import java.util.List;

import taeo.terrathaumcraft.TerraThaumcraftMod;
import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.terrathaumcraft.tile.TEAnvilTTC;
import taeo.ttfcapi.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Blocks.Devices.BlockAnvil;
import com.bioxx.tfc.api.Crafting.AnvilReq;

public class BlockThaumiumAnvil extends BlockTerraContainer implements ITileEntityProvider
{
	public IIcon textureMapTop;
	public IIcon textureMapSide;
	
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List){}

	public void registerBlockIcons(IIconRegister registerer)
	{
		textureMapTop = registerer.registerIcon(ReferenceTTC.MOD_ID + ":" + "devices/thaumiumanviltop");
		textureMapSide = registerer.registerIcon(ReferenceTTC.MOD_ID + ":" + "devices/thaumiumanvilside");
	}
	public IIcon getIcon(int i, int j)
	{
		if(i == 0 || i == 1)
			return textureMapTop;
		else
			return textureMapSide;
	}
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess bAccess, int x, int y, int z)
	{
		int meta = bAccess.getBlockMetadata(x, y, z);
		int direction = BlockAnvil.getDirectionFromMetadata(meta);
		TEAnvilTTC te = (TEAnvilTTC)bAccess.getTileEntity(x, y, z);

		if(te.AnvilTier != AnvilReq.STONE.Tier)
		{
			if(direction == 0)
				this.setBlockBounds(0.2f, 0, 0, 0.8f, 0.6f, 1);
			else
				this.setBlockBounds(0, 0, 0.2f, 1, 0.6f, 0.8f);
		}
		else
		{
			this.setBlockBounds(0, 0, 0, 1, 0.9F, 1);
		}
	}
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		int direction = BlockAnvil.getDirectionFromMetadata(meta);
		TEAnvilTTC te = (TEAnvilTTC)world.getTileEntity(x, y, z);

		if(te.AnvilTier != AnvilReq.STONE.Tier)
		{
			if(direction == 0)
			{
				this.setBlockBounds(0.2f, 0, 0, 0.8f, 0.6f, 1);
				return AxisAlignedBB.getBoundingBox(x + 0.2, (double)y + 0, (double)z + 0, x + 0.8, y + 0.6, (double)z + 1);
			}
			else
			{
				this.setBlockBounds(0, 0, 0.2f, 1, 0.6f, 0.8f);
				return AxisAlignedBB.getBoundingBox((double)x + 0, (double)y + 0, z + 0.2, (double)x + 1, y + 0.6, z + 0.8);
			}
		}
		else
		{
			this.setBlockBounds(0, 0, 0, 1, 0.9F, 1);
			return AxisAlignedBB.getBoundingBox((double)x + 0, (double)y + 0, (double)z + 0, (double)x + 1, (double)y + 0.9F, (double)z + 1);
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEAnvilTTC();
	}
	@Override
	public int getRenderType()
	{
		return TTCBlocks.anvilRenderID;
	}
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote)
		{
			//TTCRecipes.registerLateRecipes(AnvilManager.getInstance());
			return true;
		}
		else
		{
			/*if (world.getTileEntity(i, j, k) instanceof TEAnvilTTC)
			{
				LogHelper.info(ReferenceTTC.MOD_NAME,"Tile Entity is your anvil");
			}
			else if (world.getTileEntity(i, j, k) instanceof TEAnvilTTC)
			{
				LogHelper.info(ReferenceTTC.MOD_NAME,"Tile Entity is a TFC anvil");
			}
			else 
				LogHelper.info(ReferenceTTC.MOD_NAME,"Strange entity detected");*/
			if((TEAnvilTTC)world.getTileEntity(i, j, k)!=null)
			{
				/*TileAnvilTTC TileAnvilTTC;
				TileAnvilTTC = (TileAnvilTTC)world.getTileEntity(i, j, k);
				ItemStack is = entityplayer.getCurrentEquippedItem();*/
				entityplayer.openGui(TerraThaumcraftMod.instance, 21, world, i, j, k);
			}
			return true;
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is)
	{
		int meta = world.getBlockMetadata(i, j, k);
		int l = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
		byte byte0 = 0;
		if(l == 0)//+z
			byte0 = 8;
		if(l == 1)//-x
			byte0 = 0;
		if(l == 2)//-z
			byte0 = 8;
		if(l == 3)//+x
			byte0 = 0;
		byte0 += meta;

		world.setBlockMetadataWithNotify(i, j, k, byte0, 3);

		TEAnvilTTC te = (TEAnvilTTC)world.getTileEntity(i, j, k);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		TEAnvilTTC var5 = (TEAnvilTTC) world.getTileEntity(x, y, z);

		if (var5 != null)
		{
			for (int var6 = 0; var6 < var5.getSizeInventory(); ++var6)
			{
				ItemStack var7 = var5.getStackInSlot(var6);

				if (var7 != null)
				{
					float var8 = world.rand.nextFloat() * 0.8F + 0.1F;
					float var9 = world.rand.nextFloat() * 0.8F + 0.1F;
					EntityItem var12;

					for (float var10 = world.rand.nextFloat() * 0.8F + 0.1F; var7.stackSize > 0; world.spawnEntityInWorld(var12))
					{
						int var11 = world.rand.nextInt(21) + 10;

						if (var11 > var7.stackSize)
							var11 = var7.stackSize;
						var7.stackSize -= var11;
						var12 = new EntityItem(world, x + var8, y + var9, z + var10, new ItemStack(var7.getItem(), var11, var7.getItemDamage()));
						float var13 = 0.05F;
						var12.motionX = (float)world.rand.nextGaussian() * var13;
						var12.motionY = (float)world.rand.nextGaussian() * var13 + 0.2F;
						var12.motionZ = (float)world.rand.nextGaussian() * var13;
						if (var7.hasTagCompound())
							var12.getEntityItem().setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
					}
				}
			}
		}
		super.breakBlock(world, x, y, z, block, metadata);
	}
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
}
