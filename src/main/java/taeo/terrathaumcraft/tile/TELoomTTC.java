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

package taeo.terrathaumcraft.tile;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TileEntities.TELoom;
import com.bioxx.tfc.api.Constant.Global;
import net.minecraft.util.ResourceLocation;

public class TELoomTTC extends TELoom {

    @Override
    public ResourceLocation getWoodResource(){

        switch(loomType)
        {
            case 16:
            case 17: return new ResourceLocation(Reference.MOD_ID, "textures/blocks/wood/WoodSheet/"+"greatwood" + ".png");
            case 18: return new ResourceLocation(Reference.MOD_ID, "textures/blocks/wood/WoodSheet/"+ "silverwood" + ".png");
            default: return new ResourceLocation(Reference.MOD_ID, "textures/blocks/wood/WoodSheet/"+ Global.WOOD_ALL[loomType]+".png");

        }

    }
}
