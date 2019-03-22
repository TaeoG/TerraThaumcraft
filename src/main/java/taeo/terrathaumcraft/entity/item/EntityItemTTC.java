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

package taeo.terrathaumcraft.entity.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import taeo.terrathaumcraft.events.EntityItemDeathEvent;

public class EntityItemTTC extends EntityItem {


    public EntityItemTTC(World world)
    {
        super(world);
    }

    public EntityItemTTC(World p_i1709_1_, double p_i1709_2_, double p_i1709_4_, double p_i1709_6_)
    {
        super(p_i1709_1_, p_i1709_2_, p_i1709_4_, p_i1709_6_);
    }

    public EntityItemTTC(World p_i1710_1_, double p_i1710_2_, double p_i1710_4_, double p_i1710_6_, ItemStack p_i1710_8_)
    {
        super(p_i1710_1_, p_i1710_2_, p_i1710_4_, p_i1710_6_, p_i1710_8_);
    }
    // protected void dealFireDamage(int damage)
    //{
    //    if(!isImmuneToFire)this.attackEntityFrom(DamageSource.inFire, (float) damage);
   // }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float amount)
    {
        boolean result = super.attackEntityFrom(damageSource, amount);
        if(isDead)
        {
            MinecraftForge.EVENT_BUS.post(new EntityItemDeathEvent(this, damageSource));
        }
        return result;
    }
    public static EntityItemTTC convert(EntityItem entityItem){


        EntityItemTTC newEntityItem =  new EntityItemTTC(entityItem.worldObj, entityItem.posX, entityItem.posY, entityItem.posZ, entityItem.getEntityItem());
        newEntityItem.setLocationAndAngles(entityItem.posX, entityItem.posY, entityItem.posZ,0,0);
        newEntityItem.motionX = entityItem.motionX;
        newEntityItem.motionY = entityItem.motionY;
        newEntityItem.motionZ = entityItem.motionZ;
        newEntityItem.age = entityItem.age;
        newEntityItem.delayBeforeCanPickup = entityItem.delayBeforeCanPickup;
        newEntityItem.hoverStart = entityItem.hoverStart;
        newEntityItem.lifespan = entityItem.lifespan;
        entityItem.setDead();

        return newEntityItem;
    }

}
