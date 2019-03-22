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

package taeo.terrathaumcraft.item.equipment;

import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISize;
import net.minecraft.item.ItemStack;
import thaumcraft.common.items.equipment.ItemElementalPickaxe;

/**
 * Created by Taeo on 12/08/2015.
 */
public class ItemElementalPickaxeTTC extends ItemElementalPickaxe implements ISize{

    public ItemElementalPickaxeTTC(ToolMaterial toolMaterial)
    {
        super(toolMaterial);
    }

    @Override
    public EnumSize getSize(ItemStack itemStack) {
        return EnumSize.LARGE;
    }

    @Override
    public EnumWeight getWeight(ItemStack itemStack) {
        return EnumWeight.MEDIUM;
    }

    @Override
    public EnumItemReach getReach(ItemStack itemStack) {
        return EnumItemReach.MEDIUM;
    }

    @Override
    public boolean canStack() {
        return false;
    }
}
