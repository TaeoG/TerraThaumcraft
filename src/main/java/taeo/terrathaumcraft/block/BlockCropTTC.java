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

import com.bioxx.tfc.Blocks.BlockCrop;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.TileEntities.TECrop;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.terrathaumcraft.tile.TECropTTC;

public class BlockCropTTC extends BlockCrop {

    public IIcon[] icons;
    public BlockCropTTC()
    {

    }
    public void setupCrops()
    {
        //CropIndex netherwartIndex = new CropIndex()
    }
    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon("nether_wart_stage_0");
        icons = new IIcon[3];
        for (int i = 0; i <=2; i++)
        {
            icons[i] = register.registerIcon("nether_wart_stage_" + i);

        }
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess access, int x, int y, int z, int meta)
    {
        TECrop te = (TECrop) access.getTileEntity(x, y, z);
        CropIndex crop = CropManager.getInstance().getCropFromId(te.cropId);

        int stage = (int) Math.floor(te.growth);
        if(stage > crop.numGrowthStages)
            stage = crop.numGrowthStages;

        return icons[stage];
    }
    @Override
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        return world.getBlock(x,y-1,z) == TTCBlocks.tilledsoulsand;
    }

    @Override
    public int getRenderType()
    {
        return TTCBlocks.cropRenderId;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TECropTTC();
    }
}
