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

package taeo.terrathaumcraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.reference.ReferenceTAPI;
import thaumcraft.common.config.Config;
import thaumcraft.common.lib.potions.PotionSunScorned;

public class PotionSunScornedTTC extends PotionSunScorned {
    public PotionSunScornedTTC()
    {
        super(Config.potionSunScornedID, true, 16308330);
    }
    public void performEffect(EntityLivingBase target, int par2)
    {
        if (!target.worldObj.isRemote)
        {
            float f = target.getBrightness(1.0F);
            if ((f > 0.5F) && (target.worldObj.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) && (target.worldObj.canBlockSeeTheSky(MathHelper.floor_double(target.posX), MathHelper.floor_double(target.posY), MathHelper.floor_double(target.posZ))))
            {




                target.setFire(4);
            }
            else if ((f < 0.25F) && (target.worldObj.rand.nextFloat() > f * 2.0F))
            {
                target.heal(ReferenceTAPI.HEALMULTIPLIER);
            }
        }
    }
}
