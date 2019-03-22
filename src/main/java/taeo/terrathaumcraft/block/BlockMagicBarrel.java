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

import com.bioxx.tfc.Blocks.Devices.BlockBarrel;
import com.bioxx.tfc.Core.TFC_Textures;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.terrathaumcraft.tile.TEMagicBarrel;
import taeo.ttfcapi.utility.LogHelper;
import thaumcraft.common.config.ConfigBlocks;

import java.lang.reflect.Field;

public class BlockMagicBarrel extends BlockBarrel {

    public String[] woodNames;
    public BlockMagicBarrel()
    {
        //todo TFCPATCH set barrel metanames to public
        super();
        try
        {
            Field woodField = BlockBarrel.class.getDeclaredField("woodNames");
            woodField.setAccessible(true);
            woodNames = new String[] {"greatwood", "silverwood"};
            woodField.set(this, woodNames);


        } catch (Exception e)
        {
            LogHelper.fatal(ReferenceTTC.MOD_NAME,"Could not alter woodNames in magicBarrel Class");
            e.printStackTrace();
        }


    }
    @Override
    public IIcon getIcon(int side, int meta)
    {
        //When doing inventory item render, we increase the side by 10 so that we can draw the hoops instead of planks
        if(side >= 10)
        {
            side-=10;
            if(side == 0 || side == 1)
                return TFC_Textures.invisibleTexture;
            else
                return blockIcon;
        }
        switch(meta)
        {
            case 0: return ConfigBlocks.blockWoodenDevice.getIcon(side, 6);
            case 1: return ConfigBlocks.blockWoodenDevice.getIcon(side, 7);
            default: return TFC_Textures.invisibleTexture;
        }


    }
    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        //LogHelper.info("placing modified Tile Entity");
        return new TEMagicBarrel();
    }
    @Override
    public int getRenderType()
    {
        return TTCBlocks.magicBarrelRenderId;
    }
}
