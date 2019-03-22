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

package taeo.terrathaumcraft.worldgen;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import taeo.ttfcapi.utility.OpenSimplexNoise;
import thaumcraft.common.lib.utils.Utils;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MagicBiomePlacer implements IWorldGenerator {
    private static boolean test = true;
    static double total;
    static double max;
    static double min;
    static BufferedImage magicMap; //= new BufferedImage(1000,1000,BufferedImage.TYPE_INT_RGB);
    static BufferedImage taintMap; //= new BufferedImage(1000,1000,BufferedImage.TYPE_INT_RGB);

    private static OpenSimplexNoise simpnoise;// = new OpenSimplexNoise(1);
    private static OpenSimplexNoise simpnoise2;// = new OpenSimplexNoise(2);


    public static boolean isTaintedBiome(World world, int chunkX, int chunkZ)
    {
        if(simpnoise == null)
        {
            simpnoise = new OpenSimplexNoise(world.getSeed());
            simpnoise2 = new OpenSimplexNoise(world.getSeed()+1);
        }
        double calculatedNoise = simpnoise.eval(chunkX/32D, chunkZ/32D) * simpnoise2.eval(chunkX/32D, chunkZ/32D);
        if(test)
        {
            total += calculatedNoise;
            if(calculatedNoise < min)
            {
                min = calculatedNoise;
            }
            if(calculatedNoise > max)
            {
                max = calculatedNoise;
            }
            int color = (int)(calculatedNoise * 128D) + 128;
            color = Math.max(color, 0);
            color = Math.min(color,255);
            taintMap.setRGB(chunkX, chunkZ, new Color(color, color, color).getRGB());
        }




        if(calculatedNoise >0.48D)
        {
            return true;
        }
        return false;

    }
    public static boolean isMagicBiome(World world, int chunkX, int chunkZ)
    {


		if(false)
			return true;

        if(simpnoise == null)
        {
            simpnoise = new OpenSimplexNoise(world.getSeed());
            simpnoise2 = new OpenSimplexNoise(world.getSeed()+1);
        }
        double calculatedNoise = simpnoise.eval(chunkX/64D, chunkZ/64D) * simpnoise2.eval(chunkX/64D, chunkZ/64D);
        if(test)
        {
            total += calculatedNoise;
            if(calculatedNoise < min)
            {
                min = calculatedNoise;
            }
            if(calculatedNoise > max)
            {
                max = calculatedNoise;
            }
            int color = (int)(calculatedNoise * 128D) + 128;
            color = Math.max(color, 0);
            color = Math.min(color,255);
            magicMap.setRGB(chunkX, chunkZ, new Color(color, color, color).getRGB());
        }
        if(calculatedNoise <-0.46D)
        {
            return true;
        }
        return false;
    }
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
       if(test)
       {


           //Graphics2D graph = (Graphics2D) taintMap.getGraphics();

           total = 0;
           max = 0;
           min = 0;

		   int xmin = -1000;
		   int xmax = 1000;

		   int zmin = -15000;
		   int zmax = -13000;

		   magicMap = new BufferedImage(xmax - xmin,zmax - zmin,BufferedImage.TYPE_INT_RGB);
		   taintMap = new BufferedImage(xmax - xmin,zmax - zmin,BufferedImage.TYPE_INT_RGB);
           for (double x = xmin; x < xmax; x++)
           {
               for (double z = zmin; z < zmax; z++)
               {
                   isTaintedBiome(world, (int) x - xmin, (int) z - zmin);
                   isMagicBiome(world, (int)x - xmin, (int)z - zmin);
                  /* if(isTaintedBiome(world, (int)x,(int)y))
                   {
                       taintMap.setRGB((int)x, (int)y, Color.white.getRGB());
                   }
                   else
                   {
                       taintMap.setRGB((int)x, (int)y, Color.black.getRGB());
                   }*/
               }

           }
           File taintedfile = new File("tainted.png");
           File magicfile = new File("magic.png");
           try
           {
               ImageIO.write(taintMap, "png", taintedfile);
               ImageIO.write(magicMap, "png", magicfile);
           } catch (IOException e)
           {
               e.printStackTrace();
           }
           //LogHelper.info("average noise value is " + total / (2000 * 2000) + " and maximum value is " + max);
           test = false;
       }

        if(isTaintedBiome(world, chunkX, chunkZ))
        {
            //TODO round of sharp corners of magic biomes
            for(int xOffset = 0; xOffset <=15; xOffset++)
            {
                for (int zOffset = 0; zOffset <= 15; zOffset++)
                {
                    int xCoord = chunkX*16 + xOffset;
                    int zCoord = chunkZ*16 + zOffset;
                    Utils.setBiomeAt(world,xCoord ,zCoord, ThaumcraftWorldGenerator.biomeTaint);
                }
            }
        }
        else if(isMagicBiome(world, chunkX,chunkZ))
        {
            //TODO round of sharp corners of magic biomes
            for(int xOffset = 0; xOffset <=15; xOffset++)
            {
                for (int zOffset = 0; zOffset <= 15; zOffset++)
                {
                    int xCoord = chunkX*16 + xOffset;
                    int zCoord = chunkZ*16 + zOffset;
                    Utils.setBiomeAt(world,xCoord ,zCoord, ThaumcraftWorldGenerator.biomeMagicalForest);
                }
            }
        }
    }
}
