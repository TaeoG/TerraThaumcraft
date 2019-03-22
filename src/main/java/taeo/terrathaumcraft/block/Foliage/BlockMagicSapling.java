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

import com.bioxx.tfc.Blocks.Flora.BlockSapling;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TileEntities.TESapling;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import taeo.terrathaumcraft.utility.UtilsTTC;
import taeo.ttfcapi.utility.UtilsTAPI;
import taeo.ttfcapi.worldgen.WorldWrapper;
import thaumcraft.common.lib.world.WorldGenGreatwoodTrees;
import thaumcraft.common.lib.world.WorldGenSilverwoodTrees;

import java.util.Random;

public class BlockMagicSapling extends BlockSapling {


    public BlockMagicSapling()
    {
        super();
        this.woodNames = new String[] {"greatwood","silverwood"};
        this.icons = new IIcon[woodNames.length];
    }
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        this.icons[0] = ir.registerIcon("thaumcraft:greatwoodsapling");
        this.icons[1] = ir.registerIcon("thaumcraft:silverwoodsapling");

    }
    @Override
    public void growTree(World world, int i, int j, int k, Random rand, long timestamp)
    {
        int meta = world.getBlockMetadata(i, j, k);
        world.setBlockToAir(i, j, k);
        WorldGenerator worldGen = meta == 1? new WorldGenSilverwoodTrees(false, 7, 5): new WorldGenGreatwoodTrees(false);
		World wrappedWorld = UtilsTAPI.getWrappedWorld(world);

        if (worldGen != null && !worldGen.generate(wrappedWorld, rand, i, j, k))
        {
            world.setBlock(i, j, k, this, meta, 3);
            if (world.getTileEntity(i, j, k) instanceof TESapling)
            {
                TESapling te = (TESapling) world.getTileEntity(i, j, k);
                te.growTime = timestamp;
                te.enoughSpace = false;
                te.markDirty();
            }
        }
    }
    @Override
    public void updateTick(World world, int i, int j, int k, Random rand)
    {
        if (!world.isRemote)
        {
            //super.updateTick(world, i, j, k, rand);

            if (world.getTileEntity(i, j, k) instanceof TESapling)
            {
                long timestamp = ((TESapling) world.getTileEntity(i, j, k)).growTime;

                if (world.getBlockLightValue(i, j + 1, k) >= 9 && TFC_Time.getTotalTicks() > timestamp)
                {
                    growTree(world, i, j, k, rand, timestamp);
                }
            }
        }
    }


}
