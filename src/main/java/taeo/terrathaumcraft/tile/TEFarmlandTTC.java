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

import com.bioxx.tfc.Entities.Mobs.EntityGhastTFC;
import com.bioxx.tfc.TileEntities.TEFarmland;

public class TEFarmlandTTC extends TEFarmland {

    int tickspassed = 0;
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        try
        {
            tickspassed++;
        }
        catch(Exception e)
        {
            tickspassed = 0;
        }

        if(tickspassed % 2000 == 0 && this.worldObj.rand.nextInt(5) == 1)
        {
            EntityGhastTFC ghast = new EntityGhastTFC(this.worldObj);
            ghast.setLocationAndAngles(this.xCoord, this.yCoord + 10, this.zCoord, 0,0);
            if(ghast.getCanSpawnHere() && !this.worldObj.isRemote)
            {
                this.worldObj.spawnEntityInWorld(ghast);
            }
        }
    }
}
