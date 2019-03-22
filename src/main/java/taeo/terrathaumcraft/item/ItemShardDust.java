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
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISize;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import taeo.terrathaumcraft.reference.ReferenceTTC;

public class ItemShardDust extends ItemTerra implements ISize {

    public ItemShardDust()
    {
        super();
        setMaxDamage(0);
        setHasSubtypes(true);
        metaNames = new String[]{"air", "fire", "water", "earth", "order", "entropy"};
        setFolder("shards/");
    }
    @Override
    public IIcon getIconFromDamage(int meta)
    {
        //return this.metaIcons[meta%metaIcons.length];
        return itemIcon;
    }

    @Override
    public void registerIcons(IIconRegister registerer)
    {
        itemIcon = registerer.registerIcon(ReferenceTTC.MOD_ID + ":" + textureFolder + "sharddust");
    }
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int par2)
    {
        if (stack.getItemDamage() == 6) {
            return super.getColorFromItemStack(stack, par2);
        }
        return thaumcraft.common.blocks.BlockCustomOreItem.colors[(stack.getItemDamage() + 1)];
    }

    @Override
    public EnumSize getSize(ItemStack is)
    {
        return EnumSize.TINY;
    }

    @Override
    public EnumWeight getWeight(ItemStack is)
    {
        return EnumWeight.LIGHT;
    }

    @Override
    public EnumItemReach getReach(ItemStack is)
    {
        return EnumItemReach.SHORT;
    }

    @Override
    public boolean canStack()
    {
        return true;
    }
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s%s", ReferenceTTC.MOD_ID.toLowerCase() + ":", metaNames[itemStack.getItemDamage()%metaNames.length], getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }
    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}
