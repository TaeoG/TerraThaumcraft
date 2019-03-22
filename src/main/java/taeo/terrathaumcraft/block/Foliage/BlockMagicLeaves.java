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

package taeo.terrathaumcraft.block.Foliage;

import com.bioxx.tfc.Blocks.Vanilla.BlockCustomLeaves;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.terrathaumcraft.utility.ColorTools;
import thaumcraft.common.blocks.BlockMagicalLeaves;

import java.util.Random;

public class BlockMagicLeaves extends BlockCustomLeaves {
    BlockMagicalLeaves thaumcraftLeaves = new BlockMagicalLeaves();



    @Override
    public void onNeighborBlockChange(World world, int xOrig, int yOrig, int zOrig, Block b)
    {
        if (!world.isRemote)
        {
            int var6 = world.getBlockMetadata(xOrig, yOrig, zOrig);

            byte searchRadius = 4;
            int maxDist = searchRadius + 1;
            byte searchDistance = 11;
            int center = searchDistance / 2;
            adjacentTreeBlocks = null;
            if (this.adjacentTreeBlocks == null)
                this.adjacentTreeBlocks = new int[searchDistance][searchDistance][searchDistance];

            if (world.checkChunksExist(xOrig - maxDist, yOrig - maxDist, zOrig - maxDist, xOrig + maxDist, yOrig + maxDist, zOrig + maxDist))
            {
                for (int xd = -searchRadius; xd <= searchRadius; ++xd)
                {
                    int searchY = searchRadius - Math.abs(xd);
                    for (int yd = -searchY; yd <= searchY; ++yd)
                    {
                        int searchZ = searchY - Math.abs(yd);
                        for (int zd = -searchZ; zd <= searchZ; ++zd)
                        {
                            Block block = world.getBlock(xOrig + xd, yOrig + yd, zOrig + zd);

                            if (block == TTCBlocks.blockMagicTrunk)
                                this.adjacentTreeBlocks[xd + center][yd + center][zd + center] = 0;
                            else if (block == this && var6 == world.getBlockMetadata(xOrig + xd, yOrig + yd, zOrig + zd))
                                this.adjacentTreeBlocks[xd + center][yd + center][zd + center] = -2;
                            else
                                this.adjacentTreeBlocks[xd + center][yd + center][zd + center] = -1;
                        }
                    }
                }

                for (int pass = 1; pass <= 4; ++pass)
                {
                    for (int xd = -searchRadius; xd <= searchRadius; ++xd)
                    {
                        int searchY = searchRadius - Math.abs(xd);
                        for (int yd = -searchY; yd <= searchY; ++yd)
                        {
                            int searchZ = searchY - Math.abs(yd);
                            for (int zd = -searchZ; zd <= searchZ; ++zd)
                            {
                                if (this.adjacentTreeBlocks[xd + center][yd + center][zd + center] == pass - 1)
                                {
                                    if (this.adjacentTreeBlocks[xd + center - 1][yd + center][zd + center] == -2)
                                        this.adjacentTreeBlocks[xd + center - 1][yd + center][zd + center] = pass;

                                    if (this.adjacentTreeBlocks[xd + center + 1][yd + center][zd + center] == -2)
                                        this.adjacentTreeBlocks[xd + center + 1][yd + center][zd + center] = pass;

                                    if (this.adjacentTreeBlocks[xd + center][yd + center - 1][zd + center] == -2)
                                        this.adjacentTreeBlocks[xd + center][yd + center - 1][zd + center] = pass;

                                    if (this.adjacentTreeBlocks[xd + center][yd + center + 1][zd + center] == -2)
                                        this.adjacentTreeBlocks[xd + center][yd + center + 1][zd + center] = pass;

                                    if (this.adjacentTreeBlocks[xd + center][yd + center][zd + center - 1] == -2)
                                        this.adjacentTreeBlocks[xd + center][yd + center][zd + center - 1] = pass;

                                    if (this.adjacentTreeBlocks[xd + center][yd + center][zd + center + 1] == -2)
                                        this.adjacentTreeBlocks[xd + center][yd + center][zd + center + 1] = pass;
                                }
                            }
                        }
                    }
                }
            }

            int res = this.adjacentTreeBlocks[center][center][center];

            if (res < 0)
            {
                if(world.getChunkFromBlockCoords(xOrig, zOrig) != null)
                    //this.destroyLeaves(world, xOrig, yOrig, zOrig);
                world.setBlockToAir(xOrig, yOrig, zOrig);
            }
        }
    }

    @Override
    public IIcon getIcon(int par1, int par2) {
        return thaumcraftLeaves.getIcon(par1, par2);
    }

    @Override
    public int getBlockColor()
    {
        return thaumcraftLeaves.getBlockColor();
    }
    @Override
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int meta = par1IBlockAccess.getBlockMetadata(par2,par3,par4);
        //
        if (meta == 0 || meta == 4)
        {
            return ColorTools.foliageColorMultiplier(par1IBlockAccess,par2,par3,par4);
        }
        return thaumcraftLeaves.colorMultiplier(par1IBlockAccess, par2, par3, par4);
    }
    @Override
    public void registerBlockIcons(IIconRegister ir) {
        thaumcraftLeaves.registerBlockIcons(ir);
    }
    @Override
    public int getRenderColor(int par1) {
        return thaumcraftLeaves.getRenderColor(par1);
    }
    @Override
    protected void dropSapling(World world, int x, int y, int z, int meta)
    {
        dropBlockAsItem(world, x, y, z, new ItemStack(this.getItemDropped(0, null, 0), 1, meta%2));

    }
    @Override
    public Item getItemDropped(int i, Random rand, int j)
    {
        return Item.getItemFromBlock(TTCBlocks.blockMagicSapling);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int meta)
    {
        if (!world.isRemote)
        {
            ItemStack itemstack = entityplayer.inventory.getCurrentItem();
            int[] equipIDs = OreDictionary.getOreIDs(itemstack);
            for (int id : equipIDs)
            {
                String name = OreDictionary.getOreName(id);
                if (name.startsWith("itemScythe"))
                {
                    for (int x = -1; x < 2; x++)
                    {
                        for (int z = -1; z < 2; z++)
                        {
                            for (int y = -1; y < 2; y++)
                            {
                                if (world.getBlock(i + x, j + y, k + z).getMaterial() == Material.leaves &&
                                        entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem) != null)
                                {
                                    entityplayer.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
                                    entityplayer.addExhaustion(0.045F);
                                    if (world.rand.nextInt(100) < 11)
                                        dropBlockAsItem(world, i + x, j + y, k + z, new ItemStack(TFCItems.stick, 1));
                                    else if (world.rand.nextInt(getChanceOfSapling(meta)) ==0 && TFCOptions.enableSaplingDrops)
									{
										if(!(!ReferenceTTC.greatwoodSaplingsDrop && meta == 0))
										dropSapling(world, i + x, j + y, k + z, meta);
									}

                                    removeLeaves(world, i + x, j + y, k + z);
                                    super.harvestBlock(world, entityplayer, i + x, j + y, k + z, meta);

                                    itemstack.damageItem(1, entityplayer);
                                    if (itemstack.stackSize == 0)
                                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                                }
                            }
                        }
                    }
                    return;
                }
            }

            // Only executes if scythe wasn't found
            entityplayer.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
            entityplayer.addExhaustion(0.025F);
            if (world.rand.nextInt(100) < 28) //28
                dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.stick, 1));
            else if (world.rand.nextInt(getChanceOfSapling(meta)) < 1 && TFCOptions.enableSaplingDrops)
			{
				if(!(!ReferenceTTC.greatwoodSaplingsDrop && meta == 0))
				dropSapling(world, i, j, k, meta);
			}


            super.harvestBlock(world, entityplayer, i, j, k, meta);

        }
    }
    private int getChanceOfSapling(int meta)
    {
        meta = meta%2;
        if(meta == 0)
        {
            return 200;
        }
        else return 250;
    }
    private void removeLeaves(World world, int x, int y, int z)
    {
        dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        if(world.rand.nextInt(100) < 30)
            dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.stick, 1));
        world.setBlockToAir(x, y, z);
    }
}
