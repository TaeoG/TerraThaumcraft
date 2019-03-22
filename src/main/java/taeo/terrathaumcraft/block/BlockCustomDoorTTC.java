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

package taeo.terrathaumcraft.block;

import com.bioxx.tfc.Blocks.Vanilla.BlockCustomDoor;
import net.minecraft.util.IIcon;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;

public class BlockCustomDoorTTC extends BlockCustomDoor {
    public IIcon[] icons;
    public String[] woodNames;
    public BlockCustomDoorTTC(int woodId)
    {
        super(woodId);
        try
        {
            Field woodNamesField = BlockCustomDoor.class.getDeclaredField("woodNames");
            woodNamesField.setAccessible(true);
            String[] oldWoodNames = (String[])woodNamesField.get(this);
            woodNames = new String[] {"Greatwood Door Lower", "Greatwood Door Upper", "Silverwood Door Lower", "Silverwood Door Upper" };
            woodNames = ArrayUtils.addAll(oldWoodNames, woodNames);
            woodNamesField.set(this, woodNames);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
