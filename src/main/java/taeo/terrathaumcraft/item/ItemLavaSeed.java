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

package taeo.terrathaumcraft.item;

import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import taeo.terrathaumcraft.reference.ReferenceTTC;

public class ItemLavaSeed extends ItemTerra{

    public ItemLavaSeed()
    {
        super();
        this.hasSubtypes=true;
        metaNames = new String[] {"scoriapowder", "lavaseed"};
    }

    @Override
    public EnumSize getSize(ItemStack is)
    {
        return EnumSize.SMALL;
    }

    @Override
    public EnumWeight getWeight(ItemStack is)
    {
        return EnumWeight.LIGHT;
    }

    @Override
    public boolean canStack()
    {
        return true;
    }
    @Override
    public int getItemStackLimit(ItemStack is)
    {
        if(is.getItemDamage() == 0)
        {
            return super.getItemStackLimit(is);
        }
        else return 1;
    }
    @Override
    public void registerIcons(IIconRegister registerer)
    {
        if(this.metaNames == null)
        {
            if(this.iconString != null)
                this.itemIcon = registerer.registerIcon(ReferenceTTC.MOD_ID + ":" + this.textureFolder + this.getIconString());
            else
                this.itemIcon = registerer.registerIcon(ReferenceTTC.MOD_ID + ":" + this.textureFolder + this.getUnlocalizedName().replace("item.", ""));
        }
        else
        {
            metaIcons = new IIcon[metaNames.length];
            for(int i = 0; i < metaNames.length; i++)
            {
                metaIcons[i] = registerer.registerIcon(ReferenceTTC.MOD_ID + ":" + this.textureFolder + metaNames[i]);
            }

            //This will prevent NullPointerException errors with other mods like NEI
            this.itemIcon = metaIcons[0];
        }
    }


}

