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

import com.bioxx.tfc.Blocks.Terrain.BlockOre;
import com.bioxx.tfc.Core.Player.SkillStats;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEOre;
import com.bioxx.tfc.api.TFCItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.terrathaumcraft.init.TTCItems;
import taeo.terrathaumcraft.proxy.ClientProxy;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.api.Prospectable;
import taeo.ttfcapi.utility.LogHelper;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Taeo on 13/08/2015.
 */
public class BlockMagicOre extends BlockOre implements ITileEntityProvider, Prospectable{


    public static final int[] colors = new int[]{16777086, 16727041, 37119, 40960, 15650047, 5592439, 16777215};
    public IIcon[] extraIcons = new IIcon[2];
    public static IIcon cinnabarIcon;
    public static IIcon amberOreIcon;
    public static IIcon[] icon= new IIcon[6];

    public BlockMagicOre()
    {
        super(Material.rock);
        this.setTickRandomly(true);
        this.setCreativeTab(null);
        this.setHardness(10F);
        this.setResistance(10F);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int meta) {
        if (meta == 6) {
            return this.icon[0];
        }
        if (meta == 7) {
            return this.icon[3];
        }
        if (meta == 15) {
            return this.icon[4];
        }
        if (meta == 8)
        {
            return this.icon[5];
        }
        return this.icon[1];
    }


    public boolean isOpaqueCube()
    {
        return true;
    }

    public boolean canRenderInPass(int pass)
    {
        ClientProxy.renderPass = pass;
        return true;

    }
    public int getRenderBlockPass()
    {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        this.icon[0] = ir.registerIcon("terrathaumcraft:cinnibartransparency");
        this.icon[1] = ir.registerIcon("terrathaumcraft:magicore");
        this.icon[2] = ir.registerIcon("thaumcraft:infusedore");
        this.icon[3] = ir.registerIcon("terrathaumcraft:ambertransparency");
        this.icon[4] = ir.registerIcon("thaumcraft:frostshard");
        //this.icon[5] = ir.registerIcon("terrathaumcraft:quartz_ore");
        cinnabarIcon = ir.registerIcon("terrathaumcraft:cinnibar");
        amberOreIcon = ir.registerIcon("terrathaumcraft:amberore");
    }
    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int meta)
    {
       // LogHelper.info("Placed new ore TE");
        return new TEOre();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
    {
        //super.onBlockActivated(world,x,y,z,entityplayer,par6,par7,par8,par9);
        world.markBlockForUpdate(x, y, z);
        return false;
    }
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    @Override
    public int getRenderType() {
        return TTCBlocks.magicOreRenderID;
    }

    /*@Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {

    }*/
    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
    {
        //Intentionally empty so that mining ore blocks cannot trigger a cave in.
    }
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int md, int fortune) {
        ArrayList result = new ArrayList();
        TEOre teMO = (TEOre)world.getTileEntity(x,y,z);
        int quality = getOreGrade(teMO);

        if(md <= 5) {
            result.add(new ItemStack(TTCItems.roughShard, 1, (quality + 1) * 6 + md));
			//TODO check whether this actually produces the right shards
        } else if(md == ReferenceTTC.OreMeta.AMBER) {
            result.add(new ItemStack(ConfigItems.itemResource, 1 + world.rand.nextInt(fortune + 1), 6));
        } else if(md == ReferenceTTC.OreMeta.CINNABAR)
        {
            result.add(new ItemStack(ConfigBlocks.blockCustomOre, 1, 0));
        }
        /*else if (md == ReferenceTTC.OreMeta.QUARTZ)
        {
            result.add(new ItemStack(Items.quartz, 1, 0));
        }*/
        /*if(md == 0) {
            ret.add(new ItemStack(ConfigBlocks.blockCustomOre, 1, 0));
        } else if(md == 7) {
            ret.add(new ItemStack(ConfigItems.itemResource, 1 + world.rand.nextInt(fortune + 1), 6));
        } else
        {
            ret.add(new ItemStack(TTCItems.roughShard, 1, (quality+1)*6  + md - 1));
        }*/

        return result;

    }
    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
    {
        if(!world.isRemote)
        {
            int meta = world.getBlockMetadata(x, y, z);
            TEOre teMO = (TEOre)world.getTileEntity(x, y, z);
            int ore = getOreGrade(teMO);
            int fortune = 0;
            ItemStack tool = player.getCurrentEquippedItem();
            if(tool != null)
            {
                NBTTagList enchants = tool.getEnchantmentTagList();
                if(enchants != null)
                {
                    fortune = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, tool);
                }
            }

            ArrayList<ItemStack> drops = getDrops(world, x, y, z, meta, fortune);

            if(player != null)
            {
                TFC_Core.addPlayerExhaustion(player, 0.001f);
                player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
            }

            for(ItemStack stack : drops)
            {
                dropBlockAsItem(world, x, y, z, stack);
            }


        }
        return world.setBlockToAir(x, y, z);
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
        par3List.add(new ItemStack(par1, 1, 4));
        par3List.add(new ItemStack(par1, 1, 5));
        par3List.add(new ItemStack(par1, 1, 6));
        par3List.add(new ItemStack(par1, 1, 7));
        par3List.add(new ItemStack(par1, 1, 8));
    }

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion exp) {
		if(!world.isRemote)
		{
			TEOre te = (TEOre)world.getTileEntity(x, y, z);
			ItemStack itemstack = null;
			int meta = world.getBlockMetadata(x, y, z);
			int ore = getOreGrade(te);

			//itemstack = new ItemStack(TFCItems.oreChunk, 1, ore);


			if(meta <= 5) {
				itemstack = new ItemStack(TTCItems.roughShard, 1, (ore + 1) *6 + meta);
			} else if(meta == ReferenceTTC.OreMeta.AMBER) {
				itemstack = new ItemStack(ConfigItems.itemResource,1, 6);
			} else if(meta == ReferenceTTC.OreMeta.CINNABAR)
			{
				itemstack = new ItemStack(ConfigBlocks.blockCustomOre, 1, 0);
			}

			if(itemstack != null) {
				dropBlockAsItem(world, x, y, z, itemstack);
			}
			onBlockDestroyedByExplosion(world, x, y, z, exp);
		}
	}

	@SideOnly(Side.CLIENT)
    public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
    {

        int md = worldObj.getBlockMetadata(target.blockX, target.blockY, target.blockZ) + 1;
        if ((md != 0) && (md < 6))
            UtilsFX.infusedStoneSparkle(worldObj, target.blockX, target.blockY, target.blockZ, md);
        return super.addHitEffects(worldObj, target, effectRenderer);
    }
    public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
    {
        return super.addDestroyEffects(world, x, y, z, meta, effectRenderer);
    }

    public int getOreGrade(TEOre te)
    {
        if(te != null)
        {
            return te.extraData % 8;

        }
        LogHelper.error(ReferenceTTC.MOD_NAME,"Ore Tile Entity No Longer Exists");
        return 0;
    }
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if (!world.isRemote)
            scanVisible(world, x, y, z);
    }

    public void scanVisible(World world, int x, int y, int z)
    {
        if (!world.isRemote)
        {
            TEOre te = (TEOre)world.getTileEntity(x, y, z);
            if((te.extraData & 8) == 0 && y < 255 && y > 0)
            {
                if(world.blockExists(x, y-1, z) && world.blockExists(x, y+1, z) && world.blockExists(x-1, y, z) && world.blockExists(x+1, y, z) &&
                        world.blockExists(x, y, z-1) && world.blockExists(x, y, z+1))
                    if(!world.getBlock(x, y - 1, z).isOpaqueCube() || !world.getBlock(x, y + 1, z).isOpaqueCube() ||
                            !world.getBlock(x - 1, y, z).isOpaqueCube() || !world.getBlock(x + 1, y, z).isOpaqueCube() ||
                            !world.getBlock(x, y, z - 1).isOpaqueCube() || !world.getBlock(x, y, z + 1).isOpaqueCube())
                    {
                        te.setVisible();
                    }
            }
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
    {
        if(!world.isRemote)
        {
            scanVisible(world, x, y, z);
        }
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {

    }
	//@Override
   /* public Item getDroppedItem(int meta)
    {
        if(meta >5)
        {
            return null;
        }
        else
            return(TTCItems.roughShard);
    }*/
	@Override
	public ItemStack getCoreSample(World world, double xCoord, double yCoord, double zCoord)
	{
		int meta = world.getBlockMetadata((int)xCoord, (int)yCoord, (int)zCoord);
		switch (meta)
		{
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5: return new ItemStack(TTCItems.roughShard,1,meta);
			case ReferenceTTC.OreMeta.AMBER: return new ItemStack(ConfigItems.itemResource, 1, 6);
			case ReferenceTTC.OreMeta.CINNABAR: return null;
			//case ReferenceTTC.OreMeta.QUARTZ: return new ItemStack(Items.quartz, 1, 0);
			default: return null;
		}

	}

	@Override
	public ItemStack getProPickResult(World world, double xCoord, double yCoord, double zCoord, EntityPlayer player) {
		return getCoreSample(world, xCoord, yCoord, zCoord);
	}


}
