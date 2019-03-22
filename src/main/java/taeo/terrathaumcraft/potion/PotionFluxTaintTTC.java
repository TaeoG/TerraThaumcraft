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
import net.minecraft.entity.player.EntityPlayer;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.reference.ReferenceTAPI;
import thaumcraft.api.damagesource.DamageSourceThaumcraft;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.api.potions.PotionFluxTaint;
import thaumcraft.common.config.Config;

public class PotionFluxTaintTTC extends PotionFluxTaint {
    public PotionFluxTaintTTC()
    {
        super(Config.potionTaintPoisonID, true, 6697847);
    }
    @Override
    public void performEffect(EntityLivingBase target, int par2) {
        if (target instanceof ITaintedMob) {
            target.heal(ReferenceTAPI.HEALMULTIPLIER);
        } else
        if (!target.isEntityUndead() && !(target instanceof EntityPlayer))
        {
            target.attackEntityFrom(DamageSourceThaumcraft.taint, 1);
        }
        else
        if (!target.isEntityUndead() && (target.getMaxHealth() > 1 || (target instanceof EntityPlayer)))
        {
            target.attackEntityFrom(DamageSourceThaumcraft.taint, 1);
        }
    }
}
