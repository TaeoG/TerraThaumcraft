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

package taeo.terrathaumcraft.reference;

import com.bioxx.tfc.api.Enums.EnumTree;
import com.bioxx.tfc.api.TFCBlocks;
import net.minecraft.block.Block;
import scala.actors.threadpool.Arrays;
import scala.tools.cmd.gen.AnyVals;
import thaumcraft.api.aspects.Aspect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReferenceTTC
{
	public static final String MOD_ID = "terrathaumcraft";
	public static final String MOD_NAME= "TerraThaumcraft";
    public static final String VERSION = "a0.5";
    public static final String CLIENT_PROXY = "taeo.terrathaumcraft.proxy.ClientProxy";
	public static final String SERVER_PROXY = "taeo.terrathaumcraft.proxy.ServerProxy";
    public static final int MAGIC_TIER_LEVEL = 7;
	//public static int magicOreRarity = 20;


	/**
	 * Config options
	 */
	public static boolean oreFailureLimit = false;
	public static float oreFailureLimitMulti = 1.0f;
	public static int airOreRarity = 100;
	public static int earthOreRarity = 100;
	public static int fireOreRarity = 100;
	public static int waterOreRarity = 100;
	public static int orderOreRarity = 100;
	public static int chaosOreRarity = 100;
	public static int amberOreRarity = 100;
	public static int cinnabarOreRarity = 100;
	public static int greatwoodTreeRarity = 25;
	public static boolean greatwoodSaplingsDrop = true;
	public static boolean removeOrigRecipes = true;
	public static float balancedShardSHC = 0.35f;
	public static float balancedShardMelt = 950f;


	/**
	 * /Config options
	 */

    /*public static int DAMAGEMULTIPLIER = 50;
    public static int HEALMULTIPLIER = 50;
    public static int WEAKNESSBASE = 75;
    public static int STRENGTHBASE = 50;
    public static int ABSORPTIONBASE = 200;
    public static int HEALTHBOOSTBASE = 200;
    */
	public static int WOODTYPESTARTINDEX;
	public static String[] WOOD_EXTRA = new String[]{"greatwood", "silverwood"};
	public static boolean enableWorldGen = true;
	public static boolean smartMovingInstalled = false;
/*
    
    public static final String[] TOOL_HEADS = new String[]{
    		"axe",
        	"chisel",
        	"hammer",
        	"hoe",
        	"javelin",
        	"knife",
        	"mace",
        	"pick",
        	"propick",
        	"saw",
        	"scythe",
        	"shovel",
        	"sword"
    };
    public static int getToolHeadID(String name)
    {
    	int i = 0;
    	for (String s: TOOL_HEADS)
    	{
    		if (s.equals(name))
    		return i;
    		i++;
    	}
    	return 0;
    }

	public static final int[][] sideMap = {{0,1,0},{0,-1,0},{0,0,1},{0,0,-1},{1,0,0},{-1,0,0}};

	public static final EnumTree[] TREE_TYPES = new EnumTree[]{
			EnumTree.OAK,
			EnumTree.ASPEN,
			EnumTree.BIRCH,
			EnumTree.CHESTNUT,
			EnumTree.DOUGLASFIR,
			EnumTree.HICKORY,
			EnumTree.ASH,
			EnumTree.PINE,
			EnumTree.REDWOOD,
			EnumTree.SPRUCE,
			EnumTree.SYCAMORE,
			EnumTree.WHITECEDAR,
			EnumTree.WHITEELM,
			EnumTree.WILLOW,
			EnumTree.KAPOK,
			EnumTree.KOA



	};*/
	//public static final String[] allRockTypes = new String[]{"igneous intrusive","igneous extrusive","sedimentary","metamorphic"};
    public static int[] shardYield = new int[]{1,2,3,5};

	/*
	public static BlockInfo getTreePart(EnumTree tree, boolean log)
	{
		Block outBlock = log?TFCBlocks.logNatural: TFCBlocks.leaves;
		int outMeta = 0;
		switch(tree)
		{
			case OAK:
				outMeta = 0;
				break;
			case ASPEN:
				outMeta = 1;
				break;
			case BIRCH:
				outMeta = 2;
				break;
			case CHESTNUT:
				outMeta = 3;
				break;
			case DOUGLASFIR:
				outMeta = 4;
				break;
			case HICKORY:
				outMeta = 5;
				break;
			case MAPLE:
				outMeta = 6;
				break;
			case ASH:
				outMeta = 7;
				break;
			case PINE:
				outMeta = 8;
				break;
			case REDWOOD:
				outMeta = 9;
				break;
			case SPRUCE:
				outMeta = 10;
				break;
			case SYCAMORE:
				outMeta = 11;
				break;
			case WHITECEDAR:
				outMeta = 12;
				break;
			case WHITEELM:
				outMeta = 13;
				break;
			case WILLOW:
				outMeta = 14;
				break;
			case UTACACIA:
			case KAPOK:
			case KOA:
				outMeta = 0;
				outBlock = log? TFCBlocks.logNatural2: TFCBlocks.leaves2;
				break;
			default:
				break;
		}
		return new BlockInfo(outBlock, outMeta);
	}*/

	public static class OreMeta
	{

		public static final int AIR = 0;
		public static final int FIRE = 1;
		public static final int WATER = 2;
		public static final int EARTH = 3;
		public static final int ENTROPY = 5;
		public static final int ORDER = 4;
		public static final int CINNABAR = 6;
		public static final int AMBER = 7;
		public static final int QUARTZ = 8;
		public static int getRandomInfusedMeta(Random rand)
		{
			int[] metas = new int[]{AIR, FIRE, WATER, EARTH, ENTROPY, ORDER};
			return metas[rand.nextInt(metas.length)];
		}
		public static int getMetaFromAspect(Aspect aspect)
		{
			if(aspect == Aspect.AIR)
			{
				return AIR;
			}
			if(aspect == Aspect.WATER)
			{
				return WATER;
			}
			if(aspect == Aspect.FIRE)
			{
				return FIRE;
			}
			if(aspect == Aspect.ORDER)
			{
				return ORDER;
			}
			if(aspect == Aspect.ENTROPY)
			{
				return ENTROPY;
			}
			if(aspect == Aspect.EARTH)
			{
				return EARTH;
			}
			return -1;
		}

	}
	/*public class MetalColors
	{
		public static final int BISMUTH             = 0x4b849b;
		public static final int TIN                 = 0xABBBC5;
		public static final int ZINC                = 0xB6BAC8;
		public static final int COPPER              = 0xe04a2a;
		public static final int BRONZE              = 0x8d713a;
		public static final int BISMUTHBRONZE       = 0x4b9b5a;
		public static final int BLACKBRONZE         = 0x503049;
		public static final int BRASS               = 0xa89934;
		public static final int LEAD                = 0x4f585f;
		public static final int GOLD                = 0xffda25;
		public static final int ROSEGOLD            = 0xFF9664;
		public static final int SILVER              = 0xA4A3A4;
		public static final int STERLINGSILVER      = 0xd6b79d;
		public static final int PLATINUM            = 0xB5CDE7;
		public static final int WROUGHTIRON         = 0x999999;
		public static final int NICKEL              = 0x5B5C45;
		public static final int PIGIRON             = 0x7D666B;
		public static final int STEEL               = 0x5C5E5E;
		public static final int BLACKSTEEL          = 0x262228;
		public static final int BLUESTEEL           = 0x2b61b9;
		public static final int REDSTEEL            = 0xA10702;
		public static final int WEAKSTEEL           = 0x000000;
		public static final int WEAKBLUESTEEL       = 0x000000;
		public static final int WEAKREDSTEEL        = 0x000000;
		public static final int HIGHCARBONSTEEL     = 0x000000;
		public static final int HIGHCARBONBLACKSTEEL= 0x000000;
		public static final int HIGHCARBONBLUESTEEL = 0x000000;
		public static final int HIGHCARBONREDSTEEL  = 0x000000;
		public static final int ALUMINUM            = 0x000000;
		public static final int UNKNOWN             = 0x37322E;
	}*/
}
