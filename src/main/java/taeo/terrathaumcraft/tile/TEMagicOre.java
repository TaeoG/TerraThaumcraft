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

package taeo.terrathaumcraft.tile;

import com.bioxx.tfc.TileEntities.TEOre;
import net.minecraft.nbt.NBTTagCompound;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.utility.LogHelper;

/**
 * Created by Taeo on 14/08/2015.
 */
public class TEMagicOre extends TEOre {

    public TEMagicOre()
    {
        super();
        //this.setVisible();
    }
    @Override
    public void handleInitPacket(NBTTagCompound nbt) {
        super.handleInitPacket(nbt);//readFromNBT(nbt);
        //LogHelper.info(ReferenceTTC.MOD_NAME,"initializing teore at " + xCoord + " " + yCoord + " " + zCoord);
    }
   /* public int baseBlockID;
    public int baseBlockMeta;

    private final String baseBlockIDName = "baseBlockID";
    private final String baseBlockMetaName = "baseBlockMeta";


    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger(baseBlockIDName, baseBlockID);
        nbt.setInteger(baseBlockMetaName, baseBlockMeta);
        //LogHelper.info("======================== Saving Ore Entity");
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        baseBlockID = nbt.getInteger(baseBlockIDName);
        baseBlockMeta = nbt.getInteger(baseBlockMetaName);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void handleInitPacket(NBTTagCompound nbt) {
        readFromNBT(nbt);
    }

    @Override
    public void createInitNBT(NBTTagCompound nbt) {
        writeToNBT(nbt);
    }*/
}
