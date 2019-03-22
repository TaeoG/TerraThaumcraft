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

package taeo.terrathaumcraft.render;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Render.TESR.TESRChest;
import com.bioxx.tfc.api.Constant.Global;
import net.minecraft.util.ResourceLocation;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.utility.LogHelper;

import java.lang.reflect.Field;

public class TESRChestTTC extends TESRChest {

    public static ResourceLocation[] texNormal;
    public static ResourceLocation[] texNormalDouble;

    public TESRChestTTC()
    {

        try
        {
            Field texNormalField = TESRChest.class.getDeclaredField("texNormal");
            Field texNormalDoubleField = TESRChest.class.getDeclaredField("texNormalDouble");

            texNormalField.setAccessible(true);
            texNormalDoubleField.setAccessible(true);

            //texNormal = (ResourceLocation[])texNormalField.get(null);
            //texNormalDouble = (ResourceLocation[])texNormalDoubleField.get(null);

            if(texNormal == null)
            {
                texNormal = new ResourceLocation[Global.WOOD_ALL.length + 2];
                texNormalDouble = new ResourceLocation[Global.WOOD_ALL.length + 2];
                for(int i = 0; i < Global.WOOD_ALL.length; i++)
                {
                    texNormal[i] = new ResourceLocation(Reference.MOD_ID+":textures/models/chest/normal_" + Global.WOOD_ALL[i] + ".png");
                    texNormalDouble[i] = new ResourceLocation(Reference.MOD_ID+":textures/models/chest/normal_double_" + Global.WOOD_ALL[i] + ".png");
                }
                texNormal[17] = new ResourceLocation(Reference.MOD_ID+":textures/models/chest/normal_" + "greatwood" + ".png");
                texNormalDouble[17] = new ResourceLocation(Reference.MOD_ID+":textures/models/chest/normal_double_" + "greatwood" + ".png");
                texNormal[18] = new ResourceLocation(Reference.MOD_ID+":textures/models/chest/normal_" + "silverwood" + ".png");
                texNormalDouble[18] = new ResourceLocation(Reference.MOD_ID+":textures/models/chest/normal_double_" + "silverwood" + ".png");
            }

            texNormalField.set(null, texNormal);
            texNormalDoubleField.set(null, texNormalDouble);
        }
        catch(Exception e)
        {
            LogHelper.fatal(ReferenceTTC.MOD_NAME,"TESRChest reflection failed!");
            LogHelper.error(ReferenceTTC.MOD_NAME,e.getStackTrace());
        }




    }
}
