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

package taeo.terrathaumcraft.entity.boss;

import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;
import com.bioxx.tfc.api.Interfaces.IInnateArmor;
import net.minecraft.world.World;
import thaumcraft.common.entities.monster.boss.EntityTaintacleGiant;

public class EntityTaintacleGiantTTC extends EntityTaintacleGiant implements ICausesDamage, IInnateArmor {
    public EntityTaintacleGiantTTC(World par1World)
    {
        super(par1World);
    }

    @Override
    public EnumDamageType getDamageType()
    {
        return null;
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
}
