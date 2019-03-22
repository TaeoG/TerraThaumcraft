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

package taeo.terrathaumcraft.utility;

import com.bioxx.tfc.Core.ColorizerFoliageTFC;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.api.Enums.EnumTree;
import com.bioxx.tfc.api.TFCBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;
import thaumcraft.common.lib.world.biomes.BiomeGenEerie;
import thaumcraft.common.lib.world.biomes.BiomeGenMagicalForest;
import thaumcraft.common.lib.world.biomes.BiomeGenTaint;

public class ColorTools {

    public final static int[] magic_Foliage = reduceColorMagnitude(ThaumcraftWorldGenerator.biomeMagicalForest.getBiomeFoliageColor(0,0,0), 9);
    public final static int[] taint_Foliage = reduceColorMagnitude(ThaumcraftWorldGenerator.biomeTaint.getBiomeFoliageColor(0,0,0), 9);
    public final static int[] eerie_Foliage = reduceColorMagnitude(ThaumcraftWorldGenerator.biomeEerie.getBiomeFoliageColor(0,0,0), 9);
    final static int[] magic_Grass = reduceColorMagnitude(ThaumcraftWorldGenerator.biomeMagicalForest.getBiomeGrassColor(0, 0, 0), 9);
    final static int[] taint_Grass = reduceColorMagnitude(ThaumcraftWorldGenerator.biomeTaint.getBiomeGrassColor(0,0,0), 9);
    final static int[] eerie_Grass = reduceColorMagnitude(ThaumcraftWorldGenerator.biomeEerie.getBiomeGrassColor(0, 0, 0), 9);
    public final static int[] MAGICWATER = {0x00, 0x0D, 0x1A};  //0077ee
    public final static int[] EERIEWATER = {0x05, 0x09, 0x0A};  //2e535f
    public final static int[] TAINTWATER = {0x16, 0x01, 0x0F};  //cc1188
    public static World getCurrentWorld() {
        return Minecraft.getMinecraft().theWorld;
    }
    public static int grassColorMultiplier(IBlockAccess bAccess, int x, int y, int z)
    {
        if(bAccess.getBlock(x, y+1, z) == Blocks.snow || bAccess.getBlock(x, y+1, z) == Blocks.snow_layer)
        {
            int[] snowColor = {255,255,255};
            return convertRGBtoInt(snowColor);
        }
        int plainColor = TerraFirmaCraft.proxy.grassColorMultiplier(bAccess, x, y, z);
        int[] outColor = mixColors(bAccess, x, y, z, magic_Grass, taint_Grass, eerie_Grass, plainColor);
        return convertRGBtoInt(outColor);


    }
    public static int foliageColorMultiplier(IBlockAccess bAccess, int x, int y, int z) {
        int[] rgb = new int[]{0, 0, 0};
        float temperature = TFC_Climate.getHeightAdjustedTempSpecificDay(getCurrentWorld(), TFC_Time.getDayOfYear(), x, y, z);
        int meta = bAccess.getBlockMetadata(x, y, z);
        int x1;
        int z1;
        int color;
        if(bAccess.getBlock(x, y, z) == TFCBlocks.fruitTreeLeaves) {
            for(x1 = -1; x1 <= 1; ++x1) {
                for(z1 = -1; z1 <= 1; ++z1) {
                    color = TFC_Climate.getFoliageColor(getCurrentWorld(), x + x1, y, z + z1);
                    rgb = addColorVector(color, rgb);
                }
            }
            return convertRGBtoInt(reduceColorMagnitude(rgb, 9));
        } else
        {
            boolean var11;
            int[] magicCount = new int[3];
            final int MAGIC = 0;
            final int EERIE = 1;
            final int TAINT = 2;
            BiomeGenBase currentBiome;
            if (bAccess.getBlock(x, y, z) == TFCBlocks.vine)
            {
                if (TFC_Time.getSeasonAdjustedMonth(z) >= 6 && TFC_Time.getSeasonAdjustedMonth(z) < 9 && (double) TFC_Climate.getCacheManager(getCurrentWorld()).getEVTLayerAt(x, z).floatdata1 >= 0.8D && TFC_Climate.getHeightAdjustedTemp(getCurrentWorld(), x, y, z) < 30.0F)
                {
                    var11 = false;

                    color = TFC_Climate.getFoliageColor(getCurrentWorld(), x, y, z);

                    int[] outColor = mixColors(bAccess, x, y, z, magic_Foliage, taint_Foliage, eerie_Foliage, color);
                    return convertRGBtoInt(outColor);

                } else if (TFC_Time.getSeasonAdjustedMonth(z) >= 11 || TFC_Time.getSeasonAdjustedMonth(z) <= 0 && (double) TFC_Climate.getCacheManager(getCurrentWorld()).getEVTLayerAt(x, z).floatdata1 >= 0.8D && TFC_Climate.getHeightAdjustedTemp(getCurrentWorld(), x, y, z) < 30.0F)
                {
                    color = TFC_Climate.getFoliageColor(getCurrentWorld(), x, y, z);

                    int[] outColor = mixColors(bAccess, x, y, z, magic_Foliage, taint_Foliage, eerie_Foliage, color);
                    return convertRGBtoInt(outColor);
                } else if (TFC_Time.getSeasonAdjustedMonth(z) >= 9 && (double) TFC_Climate.getCacheManager(getCurrentWorld()).getEVTLayerAt(x, z).floatdata1 >= 0.8D && TFC_Climate.getHeightAdjustedTemp(getCurrentWorld(), x, y, z) < 30.0F)
                {

                            color = ColorizerFoliageTFC.getFoliageDead();

                    int[] outColor = mixColors(bAccess, x, y, z, magic_Foliage, taint_Foliage, eerie_Foliage, color);
                    return convertRGBtoInt(outColor);
                } else
                {
                    color = TFC_Climate.getFoliageColor(getCurrentWorld(), x, y, z);

                    int[] outColor = mixColors(bAccess, x, y, z, magic_Foliage, taint_Foliage, eerie_Foliage, color);
                    return convertRGBtoInt(outColor);
                }
            } else if (TFC_Time.getSeasonAdjustedMonth(z) >= 6 && EnumTree.values()[meta].isEvergreen)
            {
                color = TFC_Climate.getFoliageColorEvergreen(getCurrentWorld(), x, y, z);
                int[] outColor = mixColors(bAccess, x, y, z, magic_Foliage, taint_Foliage, eerie_Foliage, color);
                return convertRGBtoInt(outColor);
                //return (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
            } else if (temperature <= 10.0F && TFC_Time.getSeasonAdjustedMonth(z) >= 6 && TFC_Time.getSeasonAdjustedMonth(z) < 9 && (meta == 4 || meta == 7 || meta == 5 || meta == 14))
            {
                var11 = false;

                color = ColorizerFoliageTFC.getFoliageYellow();
                int[] outColor = mixColors(bAccess, x, y, z, magic_Foliage, taint_Foliage, eerie_Foliage, color);
                return convertRGBtoInt(outColor);

            } else if (temperature <= 10.0F && TFC_Time.getSeasonAdjustedMonth(z) >= 6 && TFC_Time.getSeasonAdjustedMonth(z) < 9 && meta == 6)
            {

                color = ColorizerFoliageTFC.getFoliageRed();
                int[] outColor = mixColors(bAccess, x, y, z, magic_Foliage, taint_Foliage, eerie_Foliage, color);
                return convertRGBtoInt(outColor);


            } else if (temperature <= 10.0F && TFC_Time.getSeasonAdjustedMonth(z) >= 6 && TFC_Time.getSeasonAdjustedMonth(z) < 9 && meta != 15)
            {
                color = ColorizerFoliageTFC.getFoliageOrange();
                int[] outColor = mixColors(bAccess, x, y, z, magic_Foliage, taint_Foliage, eerie_Foliage, color);
                return convertRGBtoInt(outColor);

            } else if (temperature <= 8.0F && TFC_Time.getSeasonAdjustedMonth(z) >= 6 && meta != 15)
            {
                color = ColorizerFoliageTFC.getFoliageDead();
                int[] outColor = mixColors(bAccess, x, y, z, magic_Foliage, taint_Foliage, eerie_Foliage, color);
                return convertRGBtoInt(outColor);
            } else
            {

                color = TFC_Climate.getFoliageColor(getCurrentWorld(), x, y, z);
                int[] outColor = mixColors(bAccess, x, y, z, magic_Foliage, taint_Foliage, eerie_Foliage, color);
                return convertRGBtoInt(outColor);
            }

        }
    }

    public static int[] mixWaterColors(IBlockAccess blockAccess, int x, int y, int z, int waterColor)
    {
        return mixColors(blockAccess,x,y,z,MAGICWATER,TAINTWATER,EERIEWATER,waterColor);
    }
    public static int[] mixColors(IBlockAccess bAccess, int x, int y, int z, int[] magicColor, int[] taintColor, int[] eerieColor, int seasonColor)
    {
        int[] magicCount = new int[3];
        final int MAGIC = 0;
        final int EERIE = 1;
        final int TAINT = 2;
        BiomeGenBase currentBiome;
        int x1;
        int z1;
        int[] out = {0, 0, 0};
        int[] seasonColors = reduceColorMagnitude(convertIntToRGB(seasonColor),9);

        for(x1 = -1; x1 <= 1; ++x1) {
            for(z1 = -1; z1 <= 1; ++z1) {
                currentBiome = bAccess.getBiomeGenForCoords(x +x1, z+z1);
                if(currentBiome instanceof BiomeGenTaint)
                {
                    magicCount[TAINT] = magicCount[TAINT]+1;
                }
                else if(currentBiome instanceof BiomeGenEerie)
                {
                    magicCount[EERIE] = magicCount[EERIE]+1;
                }
                else if(currentBiome instanceof BiomeGenMagicalForest)
                {
                    magicCount[MAGIC] = magicCount[MAGIC]+1;
                }
            }
        }
        int[] outMagic = {0,0,0};
        int[] outEerie = {0,0,0};
        int[] outTaint = {0,0,0};
        int[] outSeason = {0,0,0};
        for(int m = 0; m<3;m++)
        {
            outMagic[m] = magicColor[m] * magicCount[MAGIC];
        }
        for(int t = 0; t<3; t++)
        {
            outTaint[t] = taintColor[t] * magicCount[TAINT];
        }
        for(int e = 0; e<3; e++)
        {
            outEerie[e] = eerieColor[e] * magicCount[EERIE];
        }
        int lastbits = Math.max(9-(magicCount[MAGIC] + magicCount[EERIE] + magicCount[TAINT]), 0);
        for(int s=0; s<3; s++)
        {
            outSeason[s] = seasonColors[s] * lastbits;
        }

        return addColorVector(addColorVector(outEerie,outMagic), addColorVector(outSeason, outTaint));

    }
    private static int[] addColorVector(int color, int[] rgb) {
        return addColorVector(convertIntToRGB(color), rgb);
    }
    private static int[] addColorVector(int color1, int color2)
    {
        return addColorVector(convertIntToRGB(color1), convertIntToRGB(color2));
    }
    private static int [] addColorVector(int[] rgb1, int[] rgb2)
    {
        int[] output = new int[3];
        for(int i = 0; i<3; i++)
        {
            output[i] = rgb1[i] + rgb2[i];
        }
        return output;
    }
    public static int[] convertIntToRGB(int color)
    {
        int r= (color & 16711680) >> 16;
        int g= (color & 65280) >> 8;
        int b= color & 255;
        int[] output = {r,g,b};
        return output;
    }
    public static int convertRGBtoInt(int[] rgb)
    {
        return (rgb[0] & 255) << 16 | (rgb[1] & 255) << 8 | rgb[2] & 255;
    }
    public static int[] reduceColorMagnitude(int color, int factor)
    {
        return reduceColorMagnitude(convertIntToRGB(color), factor);
    }
    public static int[] reduceColorMagnitude(int[]rgb, int factor)
    {
        for(int i =0; i<3; i++)
        {
            rgb[i] = rgb[i]/factor;
        }
        return rgb;
    }
}
