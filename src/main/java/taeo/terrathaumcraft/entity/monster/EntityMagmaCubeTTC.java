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

package taeo.terrathaumcraft.entity.monster;

import com.bioxx.tfc.Core.TFC_MobData;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;
import com.bioxx.tfc.api.Interfaces.IInnateArmor;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMagmaCubeTTC extends EntityMagmaCube implements ICausesDamage, IInnateArmor{
    public EntityMagmaCubeTTC(World p_i1737_1_)
    {
        super(p_i1737_1_);
    }

    @Override
    public EnumDamageType getDamageType()
    {
        return EnumDamageType.CRUSHING;
    }
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
       // this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(TFC_MobData.SLIME_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.SLIME_HEALTH);//MaxHealth
    }
    @Override
    protected void setSlimeSize(int par1)
    {
        this.dataWatcher.updateObject(16, new Byte((byte)par1));
        this.setSize(0.6F * (float)par1, 0.6F * (float)par1);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(par1 * TFC_MobData.SLIME_HEALTH);
        this.setHealth(this.getMaxHealth());
    }
    @Override
    public void setDead()
    {
        int var1 = this.getSlimeSize();
        if (!this.worldObj.isRemote && var1 > 1 && this.getHealth() <= 0)
        {
            int var2 = 2 + this.rand.nextInt(3);
            for (int var3 = 0; var3 < var2; ++var3)
            {
                float var4 = (var3 % 2 - 0.5F) * var1 / 4.0F;
                float var5 = (var3 / 2 - 0.5F) * var1 / 4.0F;
                EntityMagmaCubeTTC var6 = this.createInstance();
                var6.setSlimeSize(var1 / 2);
                var6.setLocationAndAngles(this.posX + var4, this.posY + 0.5D, this.posZ + var5, this.rand.nextFloat() * 360.0F, 0.0F);
                this.worldObj.spawnEntityInWorld(var6);
            }
        }
        super.setDead();
    }

    @Override
    protected EntityMagmaCubeTTC createInstance()
    {
        return new EntityMagmaCubeTTC(this.worldObj);
    }

    @Override
    public int getCrushArmor()
    {
        return 0;
    }

    @Override
    public int getSlashArmor()
    {
        return 0;
    }

    @Override
    public int getPierceArmor()
    {
        return 0;
    }
    @Override
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
    {
        if (this.canDamagePlayer())
        {
            int var2 = this.getSlimeSize();
            if (this.canEntityBeSeen(par1EntityPlayer) && this.getDistanceSqToEntity(par1EntityPlayer) < 0.6D * var2 * 0.6D * var2 && par1EntityPlayer.attackEntityFrom(DamageSource.causeMobDamage(this), this.getAttackStrength()))
                this.worldObj.playSoundAtEntity(this, "mob.slime.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        }
    }

    /**
     * Indicates weather the slime is able to damage the player (based upon the slime's size)
     */
    @Override
    protected boolean canDamagePlayer()
    {
        return this.getSlimeSize() > 1;
    }

    /**
     * Gets the amount of damage dealt to the player when "attacked" by the slime.
     */
    @Override
    protected int getAttackStrength()
    {
        return this.getSlimeSize() * TFC_MobData.SLIME_DAMAGE;
    }
}
