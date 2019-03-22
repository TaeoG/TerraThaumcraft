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

package taeo.terrathaumcraft.events;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.item.ItemEvent;

public class EntityItemDeathEvent extends ItemEvent {
    /**
     * Creates a new event for an EntityItem.
     *
     * @param itemEntity The EntityItem for this event
     */

    public DamageSource causeOfDeath;
    public double x;
    public double y;
    public double z;

    public EntityItemDeathEvent(EntityItem itemEntity, DamageSource damageSource)
    {
        super(itemEntity);
        causeOfDeath = damageSource;
        x = itemEntity.posX;
        y = itemEntity.posY;
        z = itemEntity.posZ;
    }
}
