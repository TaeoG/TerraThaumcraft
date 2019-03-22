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
import com.bioxx.tfc.api.TFCItems;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityWitherSkeletonTTC extends EntitySkeleton implements ICausesDamage, IInnateArmor{
    private static final float[] ARMOR_PROBABILITY = new float[] {0.0F, 0.5F, 0.10F, 0.15F};

    public EntityWitherSkeletonTTC(World p_i1741_1_)
    {
        super(p_i1741_1_);
        this.setSkeletonType(1);
        this.setCombatTask();
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.SKELETON_HEALTH);//MaxHealth
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }
    @Override
    public EnumDamageType getDamageType()
    {
        return EnumDamageType.SLASHING;
    }

    @Override
    public int getCrushArmor() {
        return 10;
    }
    @Override
    public int getSlashArmor() {
        return 500000;
    }
    @Override
    public int getPierceArmor() {
        return 500000;
    }

    @Override
    protected void addRandomArmor()
    {
        superAddRandomArmor();
        if(this.getSkeletonType() == 0)
        {
            this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.bow));
        }
        else if(this.getSkeletonType() == 1)
        {
            int i = this.rand.nextInt(2);
            if (this.rand.nextFloat() < 0.095F)
                ++i;
            if (this.rand.nextFloat() < 0.095F)
                ++i;
            if (this.rand.nextFloat() < 0.095F)
                ++i;

            if(i == 0)
                this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.sedStoneJavelin));
            else if(i == 1)
                this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.igExStoneJavelin));
            else if(i == 2)
                this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.copperJavelin));
            else if(i == 3)
                this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.bronzeJavelin));
            else if(i == 4)
                this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.wroughtIronJavelin));
        }
    }
    private void superAddRandomArmor()
    {
        if (this.rand.nextFloat() < ARMOR_PROBABILITY[this.worldObj.difficultySetting.getDifficultyId()])
        {
            int i = this.rand.nextInt(2);
            float f = this.worldObj.difficultySetting == EnumDifficulty.HARD ? 0.1F : 0.25F;

            if (this.rand.nextFloat() < 0.095F)
                ++i;
            if (this.rand.nextFloat() < 0.095F)
                ++i;
            if (this.rand.nextFloat() < 0.095F)
                ++i;

            for (int j = 3; j >= 0; --j)
            {
                ItemStack itemstack = this.getEquipmentInSlot(j);
                if (j < 3 && this.rand.nextFloat() < f)
                    break;

                if (itemstack == null)
                {
                    Item item = getArmorItemForSlot(j + 1, i);
                    if (item != null)
                        this.setCurrentItemOrArmor(j + 1, new ItemStack(item));
                }
            }
        }
    }
    public static Item getArmorItemForSlot(int par0, int par1)
    {
        switch (par0)
        {
            case 4:
                if (par1 == 0)
                    return TFCItems.leatherHelmet;
                else if (par1 == 1)
                    return TFCItems.copperHelmet;
                else if (par1 == 2)
                    return TFCItems.bronzeHelmet;
                else if (par1 == 3)
                    return TFCItems.wroughtIronHelmet;
                else if (par1 == 4)
                    return TFCItems.steelHelmet;
                break;
            case 3:
                if (par1 == 0)
                    return TFCItems.leatherChestplate;
                else if (par1 == 1)
                    return TFCItems.copperChestplate;
                else if (par1 == 2)
                    return TFCItems.bronzeChestplate;
                else if (par1 == 3)
                    return TFCItems.wroughtIronChestplate;
                else if (par1 == 4)
                    return TFCItems.steelChestplate;
                break;
            case 2:
                if (par1 == 0)
                    return TFCItems.leatherLeggings;
                else if (par1 == 1)
                    return TFCItems.copperGreaves;
                else if (par1 == 2)
                    return TFCItems.bronzeGreaves;
                else if (par1 == 3)
                    return TFCItems.wroughtIronGreaves;
                else if (par1 == 4)
                    return TFCItems.steelGreaves;
                break;
            case 1:
                if (par1 == 0)
                    return TFCItems.leatherBoots;
                else if (par1 == 1)
                    return TFCItems.copperBoots;
                else if (par1 == 2)
                    return TFCItems.bronzeBoots;
                else if (par1 == 3)
                    return TFCItems.wroughtIronBoots;
                else if (par1 == 4)
                    return TFCItems.steelBoots;
                break;
            default:
                return null;
        }

        return null;
    }
}
