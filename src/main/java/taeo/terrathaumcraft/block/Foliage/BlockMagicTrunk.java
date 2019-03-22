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

import com.bioxx.tfc.Blocks.Flora.BlockLogNatural2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import taeo.ttfcapi.utility.UtilsTAPI;
import thaumcraft.common.blocks.BlockMagicalLog;

import java.util.List;
import java.util.Random;

public class BlockMagicTrunk extends BlockLogNatural2 {
BlockMagicalLog thaumcraftlog = new BlockMagicalLog();

public BlockMagicTrunk()
{
    setHardness(50F);
    setStepSound(Block.soundTypeWood);
}
    //TODO make aura nodes in knots drop essense when destroyed
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir)
    {
        thaumcraftlog.registerBlockIcons(ir);
    }
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        thaumcraftlog.getSubBlocks(par1, par2CreativeTabs, par3List);
    }

    public int getRenderType()
    {
        return thaumcraftlog.getRenderType();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2)
    {
        return thaumcraftlog.getIcon(par1, par2);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
    {
        World wrappedWorld = UtilsTAPI.getWrappedWorld(world);
         //= new WorldWrapper(world);
        //LogHelper.info("harvesting with wrapped world");
        int outMeta;
        switch(meta%4)
        {
            case 0:
            case 3:
                outMeta = 0;
                break;
            case 1:
            case 2:
                outMeta = 1;
                break;
            default:
                outMeta = 0;
                break;
        }

        super.harvestBlock(wrappedWorld, entityplayer, x, y, z, outMeta);
    }
@Override
    public int onBlockPlaced(World world, int X, int Y, int Z, int side, float hitX, float hitY, float hitZ, int meta)
    {
        return thaumcraftlog.onBlockPlaced(world, X, Y, Z, side, hitX, hitY, hitZ, meta);
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return thaumcraftlog.hasTileEntity(metadata);
    }
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return thaumcraftlog.createTileEntity(world, metadata);
    }
    @Override
    public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
        return thaumcraftlog.addDestroyEffects(world, x, y, z, meta, effectRenderer);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if (!world.isRemote)
        {
            int meta = world.getBlockMetadata(x,y,z);
            if(thaumcraftlog.limitToValidMetadata(meta)!=2)
            {
                super.updateTick(world, x,y,z,rand);
            }

        }
    }

}
