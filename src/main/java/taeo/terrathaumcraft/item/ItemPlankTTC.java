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

import com.bioxx.tfc.Items.ItemPlank;
import com.bioxx.tfc.api.Constant.Global;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.apache.commons.lang3.ArrayUtils;
import taeo.terrathaumcraft.reference.ReferenceTTC;

import java.util.List;

public class ItemPlankTTC extends ItemPlank {

    public ItemPlankTTC()
    {
        super();
        String[] tempnames = ArrayUtils.addAll(metaNames, new String[]{"greatwood", "silverwood"});
        metaNames = tempnames;
    }
    public IIcon[] extraIcons = new IIcon[2];
    @Override
    public void registerIcons(IIconRegister registerer)
    {
        super.registerIcons(registerer);
        extraIcons[0] = registerer.registerIcon(ReferenceTTC.MOD_ID + ":" + "wood/"+"greatwoodplank");
        extraIcons[1] = registerer.registerIcon(ReferenceTTC.MOD_ID + ":" + "wood/"+"silverwoodplank");

    }
    @Override
    public IIcon getIconFromDamage(int meta)
    {
        if(meta < Global.WOOD_ALL.length)
        {
            return super.getIconFromDamage(meta);
        }
        else
        {
            return extraIcons[meta-Global.WOOD_ALL.length];
        }
    }

    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
    {
        for(int i = 0; i < metaNames.length; i++) {
            list.add(new ItemStack(this,1,i));
        }
    }
}
