
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package thaumcraft.common.lib.world;

import com.bioxx.tfc.api.Constant.Global;
import cpw.mods.fml.common.IWorldGenerator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import jdk.nashorn.internal.ir.annotations.Ignore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.terrathaumcraft.utility.UtilsTTC;
import taeo.terrathaumcraft.worldgen.ClimateHandler;
import taeo.ttfcapi.utility.LogHelper;
import taeo.ttfcapi.utility.UtilsTAPI;
import taeo.ttfcapi.worldgen.ExtendedOreSpawnData;
import taeo.ttfcapi.worldgen.SpawnRequirement;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.lib.utils.Utils;
import thaumcraft.common.lib.world.biomes.BiomeGenTaint;
import thaumcraft.common.lib.world.biomes.BiomeHandler;
import thaumcraft.common.lib.world.dim.MazeHandler;
import thaumcraft.common.lib.world.dim.MazeThread;
import thaumcraft.common.tiles.TileNode;

public class ThaumcraftWorldGenerator implements IWorldGenerator {
    public static BiomeGenBase biomeTaint;
    public static BiomeGenBase biomeEerie;
    public static BiomeGenBase biomeMagicalForest;
    public static BiomeGenBase biomeEldritchLands;
    static Collection<Aspect> c;
    static ArrayList<Aspect> basicAspects;
    static ArrayList<Aspect> complexAspects;
    public static HashMap<Integer, Integer> dimensionBlacklist;
    public static HashMap<Integer, Integer> biomeBlacklist;
    HashMap<Integer, Boolean> structureNode = new HashMap();


    public ThaumcraftWorldGenerator() {
    }

    public static int getFirstFreeBiomeSlot(int old) {
        for(int a = 0; a < BiomeGenBase.getBiomeGenArray().length; ++a) {
            if(BiomeGenBase.getBiomeGenArray()[a] == null) {
                Thaumcraft.log.warn("Biome slot " + old + " already occupied. Using first free biome slot at " + a);
                return a;
            }
        }

        return -1;
    }

    public void initialize() {
        BiomeGenTaint.blobs = new WorldGenBlockBlob(ConfigBlocks.blockTaint, 0);
        BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(biomeMagicalForest, Config.biomeMagicalForestWeight));
        BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(biomeMagicalForest, Config.biomeMagicalForestWeight));
        BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(biomeTaint, Config.biomeTaintWeight));
        BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(biomeTaint, Config.biomeTaintWeight));
    }

    public static void addDimBlacklist(int dim, int level) {
        dimensionBlacklist.put(Integer.valueOf(dim), Integer.valueOf(level));
    }

    public static int getDimBlacklist(int dim) {
        return !dimensionBlacklist.containsKey(Integer.valueOf(dim))?-1:((Integer)dimensionBlacklist.get(Integer.valueOf(dim))).intValue();
    }

    public static void addBiomeBlacklist(int biome, int level) {
        biomeBlacklist.put(Integer.valueOf(biome), Integer.valueOf(level));
    }

    public static int getBiomeBlacklist(int biome) {
        return !biomeBlacklist.containsKey(Integer.valueOf(biome))?-1:((Integer)biomeBlacklist.get(Integer.valueOf(biome))).intValue();
    }

    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        this.worldGeneration(random, chunkX, chunkZ, world, true);
    }

    public void worldGeneration(Random random, int chunkX, int chunkZ, World unwrappedWorld, boolean newGen) {

        World world = UtilsTAPI.getWrappedWorld(unwrappedWorld);


        if(world.provider.dimensionId == Config.dimensionOuterId) {
            MazeHandler.generateEldritch(world, random, chunkX, chunkZ);
            world.getChunkFromChunkCoords(chunkX, chunkZ).setChunkModified();
        } else {
            switch(world.provider.dimensionId) {
                case -1:
                    this.generateNether(world, random, chunkX, chunkZ, newGen);
                case 1:
                    break;
                default:
                    this.generateSurface(world, random, chunkX, chunkZ, newGen);
            }

            if(!newGen) {
                world.getChunkFromChunkCoords(chunkX, chunkZ).setChunkModified();
            }
        }

    }

    private boolean generateTotem(World world, Random random, int chunkX, int chunkZ, boolean auraGen, boolean newGen) {
        if(Config.genStructure && (world.provider.dimensionId == 0 || world.provider.dimensionId == 1) && newGen && !auraGen && random.nextInt(Config.nodeRarity * 10) == 0) {
            int x = chunkX * 16 + random.nextInt(16);
            int z = chunkZ * 16 + random.nextInt(16);
            int topy = world.provider.dimensionId == -1?Utils.getFirstUncoveredY(world, x, z) - 1:world.getHeightValue(x, z) - 1;
            if(topy > world.getActualHeight()) {
                return false;
            }

            if(world.getBlock(x, topy, z) != null && world.getBlock(x, topy, z).isLeaves(world, x, topy, z)) {
                do {
                    --topy;
                } while(world.getBlock(x, topy, z) != Blocks.grass && topy > 40);
            }

            if(world.getBlock(x, topy, z) == Blocks.snow_layer || world.getBlock(x, topy, z) == Blocks.tallgrass) {
                --topy;
            }

            if(world.getBlock(x, topy, z) == Blocks.grass || world.getBlock(x, topy, z) == Blocks.sand || world.getBlock(x, topy, z) == Blocks.dirt || world.getBlock(x, topy, z) == Blocks.stone || world.getBlock(x, topy, z) == Blocks.netherrack) {
                int count;
                for(count = 1; (world.isAirBlock(x, topy + count, z) || world.getBlock(x, topy + count, z) == Blocks.snow_layer || world.getBlock(x, topy + count, z) == Blocks.tallgrass) && count < 3; ++count) {
                    ;
                }

                if(count >= 2) {
                    world.setBlock(x, topy, z, ConfigBlocks.blockCosmeticSolid, 1, 3);
                    count = 1;

                    while((world.isAirBlock(x, topy + count, z) || world.getBlock(x, topy + count, z) == Blocks.snow_layer || world.getBlock(x, topy + count, z) == Blocks.tallgrass) && count < 5) {
                        world.setBlock(x, topy + count, z, ConfigBlocks.blockCosmeticSolid, 0, 3);
                        if(count > 1 && random.nextInt(4) == 0) {
                            world.setBlock(x, topy + count, z, ConfigBlocks.blockCosmeticSolid, 8, 3);
                            createRandomNodeAt(world, x, topy + count, z, random, false, true, false);
                            count = 5;
                            auraGen = true;
                        }

                        ++count;
                        if(count >= 5 && !auraGen) {
                            world.setBlock(x, topy + 5, z, ConfigBlocks.blockCosmeticSolid, 8, 3);
                            createRandomNodeAt(world, x, topy + 5, z, random, false, true, false);
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean generateWildNodes(World world, Random random, int chunkX, int chunkZ, boolean auraGen, boolean newGen) {
        if(Config.genAura && random.nextInt(Config.nodeRarity) == 0 && !auraGen) {
            int x = chunkX * 16 + random.nextInt(16);
            int z = chunkZ * 16 + random.nextInt(16);
            int q = Utils.getFirstUncoveredY(world, x, z);
            if(q < 2) {
                q = world.provider.getAverageGroundLevel() + random.nextInt(64) - 32 + Utils.getFirstUncoveredY(world, x, z);
            }

            if(q < 2) {
                q = 32 + random.nextInt(64);
            }

            if(world.isAirBlock(x, q + 1, z)) {
                ++q;
            }

            int p = random.nextInt(4);
            Block b = world.getBlock(x, q + p, z);
            if(world.isAirBlock(x, q + p, z) || b.isReplaceable(world, x, q + p, z)) {
                q += p;
            }

            if(q > world.getActualHeight()) {
                return false;
            } else {
                createRandomNodeAt(world, x, q, z, random, false, false, false);
                return true;
            }
        } else {
            return false;
        }
    }

    public static void createRandomNodeAt(World world, int x, int y, int z, Random random, boolean silverwood, boolean eerie, boolean small) {
        if(basicAspects.size() == 0) {
            Iterator type = c.iterator();

            while(type.hasNext()) {
                Aspect modifier = (Aspect)type.next();
                if(modifier.getComponents() != null) {
                    complexAspects.add(modifier);
                } else {
                    basicAspects.add(modifier);
                }
            }
        }

        NodeType nodeType = NodeType.NORMAL;
        if(silverwood) {
            nodeType = NodeType.PURE;
        } else if(eerie) {
            nodeType = NodeType.DARK;
        } else if(random.nextInt(Config.specialNodeRarity) == 0) {
            switch(random.nextInt(10)) {
                case 0:
                case 1:
                case 2:
                    nodeType = NodeType.DARK;
                    break;
                case 3:
                case 4:
                case 5:
                    nodeType = NodeType.UNSTABLE;
                    break;
                case 6:
                case 7:
                case 8:
                    nodeType = NodeType.PURE;
                    break;
                case 9:
                    nodeType = NodeType.HUNGRY;
            }
        }

        NodeModifier nodeMod = null;
        if(random.nextInt(Config.specialNodeRarity / 2) == 0) {
            switch(random.nextInt(3)) {
                case 0:
                    nodeMod = NodeModifier.BRIGHT;
                    break;
                case 1:
                    nodeMod = NodeModifier.PALE;
                    break;
                case 2:
                    nodeMod = NodeModifier.FADING;
            }
        }

        BiomeGenBase bg = world.getBiomeGenForCoords(x, z);
        //int baura = BiomeHandler.getBiomeAura(bg);
		int baura = ClimateHandler.getClimateAura(world,x,y,z);
		if(nodeType != NodeType.PURE && bg.biomeID == biomeTaint.biomeID) {
            baura = (int)((float)baura * 1.5F);
            if(random.nextBoolean()) {
                nodeType = NodeType.TAINTED;
                baura = (int)((float)baura * 1.5F);
            }
        }

        if(silverwood || small) {
            baura /= 4;
        }

        int value = random.nextInt(baura / 2) + baura / 2;
        Aspect ra = ClimateHandler.getRandomClimateTag(world,x,y,z);//BiomeHandler.getRandomBiomeTag(bg.biomeID, random);
        AspectList al = new AspectList();
        if(ra != null) {
            al.add(ra, 2);
        } else {
            Aspect water = (Aspect)complexAspects.get(random.nextInt(complexAspects.size()));
            al.add(water, 1);
            water = (Aspect)basicAspects.get(random.nextInt(basicAspects.size()));
            al.add(water, 1);
        }

        int var27;
        for(var27 = 0; var27 < 3; ++var27) {
            if(random.nextBoolean()) {
                Aspect lava;
                if(random.nextInt(Config.specialNodeRarity) == 0) {
                    lava = (Aspect)complexAspects.get(random.nextInt(complexAspects.size()));
                    al.merge(lava, 1);
                } else {
                    lava = (Aspect)basicAspects.get(random.nextInt(basicAspects.size()));
                    al.merge(lava, 1);
                }
            }
        }

        if(nodeType == NodeType.HUNGRY) {
            al.merge(Aspect.HUNGER, 2);
            if(random.nextBoolean()) {
                al.merge(Aspect.GREED, 1);
            }
        } else if(nodeType == NodeType.PURE) {
            if(random.nextBoolean()) {
                al.merge(Aspect.LIFE, 2);
            } else {
                al.merge(Aspect.ORDER, 2);
            }
        } else if(nodeType == NodeType.DARK) {
            if(random.nextBoolean()) {
                al.merge(Aspect.DEATH, 1);
            }

            if(random.nextBoolean()) {
                al.merge(Aspect.UNDEAD, 1);
            }

            if(random.nextBoolean()) {
                al.merge(Aspect.ENTROPY, 1);
            }

            if(random.nextBoolean()) {
                al.merge(Aspect.DARKNESS, 1);
            }
        }

        var27 = 0;
        int var28 = 0;
        int stone = 0;
        int foliage = 0;

        int a;
        try {
            for(int spread = -5; spread <= 5; ++spread) {
                for(int total = -5; total <= 5; ++total) {
                    for(a = -5; a <= 5; ++a) {
                        try {
                            Block e = world.getBlock(x + spread, y + total, z + a);
                            if(e.getMaterial() == Material.water) {
                                ++var27;
                            } else if(e.getMaterial() == Material.lava) {
                                ++var28;
                            } else if(e == Blocks.stone) {
                                ++stone;
                            }

                            if(e.isFoliage(world, x + spread, y + total, z + a)) {
                                ++foliage;
                            }
                        } catch (Exception var23) {
                            ;
                        }
                    }
                }
            }
        } catch (Exception var24) {
            ;
        }

        if(var27 > 100) {
            al.merge(Aspect.WATER, 1);
        }

        if(var28 > 100) {
            al.merge(Aspect.FIRE, 1);
            al.merge(Aspect.EARTH, 1);
        }

        if(stone > 500) {
            al.merge(Aspect.EARTH, 1);
        }

        if(foliage > 100) {
            al.merge(Aspect.PLANT, 1);
        }

        int[] var29 = new int[al.size()];
        float var30 = 0.0F;

        for(a = 0; a < var29.length; ++a) {
            if(al.getAmount(al.getAspectsSorted()[a]) == 2) {
                var29[a] = 50 + random.nextInt(25);
            } else {
                var29[a] = 25 + random.nextInt(50);
            }

            var30 += (float)var29[a];
        }

        for(a = 0; a < var29.length; ++a) {
            al.merge(al.getAspectsSorted()[a], (int)((float)var29[a] / var30 * (float)value));
        }

        createNodeAt(world, x, y, z, nodeType, nodeMod, al);
    }

    public static void createNodeAt(World world, int x, int y, int z, NodeType nt, NodeModifier nm, AspectList al) {
        if(world.isAirBlock(x, y, z)) {
            world.setBlock(x, y, z, ConfigBlocks.blockAiry, 0, 0);
        }

        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof TileNode) {
            ((TileNode)te).setNodeType(nt);
            ((TileNode)te).setNodeModifier(nm);
            ((TileNode)te).setAspects(al);
        }

        world.markBlockForUpdate(x, y, z);
    }

    private void generateSurface(World world, Random random, int chunkX, int chunkZ, boolean newGen) {
        boolean auraGen = false;
        int blacklist = getDimBlacklist(world.provider.dimensionId);
        if(blacklist == -1 && Config.genTrees && !world.getWorldInfo().getTerrainType().getWorldTypeName().startsWith("flat") && (newGen || Config.regenTrees)) {
            this.generateVegetation(world, random, chunkX, chunkZ, newGen);
        }

        if(blacklist != 0 && blacklist != 2) {
           // this.generateOres(world, random, chunkX, chunkZ, newGen);
        }

        if(blacklist != 0 && blacklist != 2 && Config.genAura && (newGen || Config.regenAura)) {
            ChunkPosition randPosX = (new MapGenScatteredFeature()).func_151545_a(world, chunkX * 16 + 8, world.getHeightValue(chunkX * 16 + 8, chunkZ * 16 + 8), chunkZ * 16 + 8);
            if(randPosX != null && !this.structureNode.containsKey(Integer.valueOf(randPosX.hashCode()))) {
                auraGen = true;
                this.structureNode.put(Integer.valueOf(randPosX.hashCode()), Boolean.valueOf(true));
                createRandomNodeAt(world, randPosX.chunkPosX, world.getHeightValue(randPosX.chunkPosX, randPosX.chunkPosZ) + 3, randPosX.chunkPosZ, random, false, false, false);
            }

            auraGen = this.generateWildNodes(world, random, chunkX, chunkZ, auraGen, newGen);
        }

        if(blacklist == -1 && Config.genStructure && world.provider.dimensionId == 0 && !world.getWorldInfo().getTerrainType().getWorldTypeName().startsWith("flat") && (newGen || Config.regenStructure)) {
            int randPosX1 = chunkX * 16 + random.nextInt(16);
            int randPosZ = chunkZ * 16 + random.nextInt(16);
            int randPosY = world.getHeightValue(randPosX1, randPosZ) - 9;
            if(randPosY < world.getActualHeight()) {
                world.getChunkFromBlockCoords(MathHelper.floor_double((double)randPosX1), MathHelper.floor_double((double)randPosZ));
                WorldGenMound mound = new WorldGenMound();
                if(random.nextInt(150) == 0) {
                    if(mound.generate(world, random, randPosX1, randPosY, randPosZ)) {
                        auraGen = true;
                        int hilltopStones = random.nextInt(200) + 400;
                        createRandomNodeAt(world, randPosX1 + 9, randPosY + 8, randPosZ + 9, random, false, true, false);
                    }
                } else if(random.nextInt(66) == 0) {
                    WorldGenEldritchRing hilltopStones1 = new WorldGenEldritchRing();
                    randPosY += 8;
                    int w = 11 + random.nextInt(6) * 2;
                    int h = 11 + random.nextInt(6) * 2;
                    hilltopStones1.chunkX = chunkX;
                    hilltopStones1.chunkZ = chunkZ;
                    hilltopStones1.width = w;
                    hilltopStones1.height = h;
                    if(hilltopStones1.generate(world, random, randPosX1, randPosY, randPosZ)) {
                        auraGen = true;
                        createRandomNodeAt(world, randPosX1, randPosY + 2, randPosZ, random, false, true, false);
                        Thread t = new Thread(new MazeThread(chunkX, chunkZ, w, h, random.nextLong()));
                        t.start();
                    }
                } else if(random.nextInt(40) == 0) {
                    randPosY += 9;
                    WorldGenHilltopStones hilltopStones2 = new WorldGenHilltopStones();
                    if(hilltopStones2.generate(world, random, randPosX1, randPosY, randPosZ)) {
                        auraGen = true;
                        createRandomNodeAt(world, randPosX1, randPosY + 5, randPosZ, random, false, true, false);
                    }
                }
            }

            this.generateTotem(world, random, chunkX, chunkZ, auraGen, newGen);
        }

    }

    private void generateVegetation(World world, Random random, int chunkX, int chunkZ, boolean newGen) {
        BiomeGenBase bgb = world.getBiomeGenForCoords(chunkX * 16 + 8, chunkZ * 16 + 8);
        if(getBiomeBlacklist(bgb.biomeID) == -1) {
            if(random.nextInt(60) == 3) {
			//if(random.nextInt(2) == 0){
                generateSilverwood(world, random, chunkX, chunkZ);
            }

            if(random.nextInt(ReferenceTTC.greatwoodTreeRarity) == 7) {
                generateGreatwood(world, random, chunkX, chunkZ);
            }

            int randPosX = chunkX * 16 + random.nextInt(16);
            int randPosZ = chunkZ * 16 + random.nextInt(16);
            int randPosY = world.getHeightValue(randPosX, randPosZ);
            if(randPosY <= world.getActualHeight()) {
				SpawnRequirement.Desert desert = new SpawnRequirement.Desert();
				if(desert.isValid(world, chunkX, randPosY, chunkZ)&& random.nextInt(30) == 0){
                //if(world.getBiomeGenForCoords(randPosX, randPosZ).topBlock == Blocks.sand && world.getBiomeGenForCoords(randPosX, randPosZ).temperature > 1.0F && random.nextInt(30) == 0) {
                    generateFlowers(world, random, randPosX, randPosY, randPosZ, 3);
                }
				//TODO add check for desert to generate cinderpearls. Also make cinderpearls work on TFC sand
            }
        }
    }

    private void generateOres(World world, Random random, int chunkX, int chunkZ, boolean newGen) {
        BiomeGenBase bgb = world.getBiomeGenForCoords(chunkX * 16 + 8, chunkZ * 16 + 8);
        if(getBiomeBlacklist(bgb.biomeID) != 0 && getBiomeBlacklist(bgb.biomeID) != 2) {
            int i;
            int randPosX;
            int randPosZ;
            int randPosY;
            Block md;
            if(Config.genCinnibar && (newGen || Config.regenCinnibar)) {
                for(i = 0; i < 18; ++i) {
                    randPosX = chunkX * 16 + random.nextInt(16);
                    randPosZ = random.nextInt(world.getHeight() / 5);
                    randPosY = chunkZ * 16 + random.nextInt(16);
                    md = world.getBlock(randPosX, randPosZ, randPosY);
                    if(md != null && md.isReplaceableOreGen(world, randPosX, randPosZ, randPosY, Blocks.stone)) {
                        world.setBlock(randPosX, randPosZ, randPosY, ConfigBlocks.blockCustomOre, 0, 0);
                    }
                }
            }

            if(Config.genAmber && (newGen || Config.regenAmber)) {
                for(i = 0; i < 20; ++i) {
                    randPosX = chunkX * 16 + random.nextInt(16);
                    randPosZ = chunkZ * 16 + random.nextInt(16);
                    randPosY = world.getHeightValue(randPosX, randPosZ) - random.nextInt(25);
                    md = world.getBlock(randPosX, randPosY, randPosZ);
                    if(md != null && md.isReplaceableOreGen(world, randPosX, randPosY, randPosZ, Blocks.stone)) {
                        world.setBlock(randPosX, randPosY, randPosZ, ConfigBlocks.blockCustomOre, 7, 2);
                    }
                }
            }

            if(Config.genInfusedStone && (newGen || Config.regenInfusedStone)) {
                for(i = 0; i < 8; ++i) {
                    randPosX = chunkX * 16 + random.nextInt(16);
                    randPosZ = chunkZ * 16 + random.nextInt(16);
                    randPosY = random.nextInt(Math.max(5, world.getHeightValue(randPosX, randPosZ) - 5));
                    int var14 = random.nextInt(6) + 1;
                    if(random.nextInt(3) == 0) {
                        Aspect e = BiomeHandler.getRandomBiomeTag(world.getBiomeGenForCoords(randPosX, randPosZ).biomeID, random);
                        if(e == null) {
                            var14 = 1 + random.nextInt(6);
                        } else if(e == Aspect.AIR) {
                            var14 = 1;
                        } else if(e == Aspect.FIRE) {
                            var14 = 2;
                        } else if(e == Aspect.WATER) {
                            var14 = 3;
                        } else if(e == Aspect.EARTH) {
                            var14 = 4;
                        } else if(e == Aspect.ORDER) {
                            var14 = 5;
                        } else if(e == Aspect.ENTROPY) {
                            var14 = 6;
                        }
                    }

                    try {
                        (new WorldGenMinable(ConfigBlocks.blockCustomOre, var14, 6, Blocks.stone)).generate(world, random, randPosX, randPosY, randPosZ);
                    } catch (Exception var13) {
                        var13.printStackTrace();
                    }
                }
            }

        }
    }

    private void generateNether(World world, Random random, int chunkX, int chunkZ, boolean newGen) {
        boolean auraGen = false;
        if(!world.getWorldInfo().getTerrainType().getWorldTypeName().startsWith("flat") && (newGen || Config.regenStructure)) {
            this.generateTotem(world, random, chunkX, chunkZ, auraGen, newGen);
        }

        if(newGen || Config.regenAura) {
            this.generateWildNodes(world, random, chunkX, chunkZ, auraGen, newGen);
        }

    }

    public static boolean generateFlowers(World world, Random random, int x, int y, int z, int flower) {
        WorldGenCustomFlowers flowers = new WorldGenCustomFlowers(ConfigBlocks.blockCustomPlant, flower);
        return flowers.generate(world, random, x, y, z);
    }

    public static boolean generateGreatwood(World world, Random random, int chunkX, int chunkZ) {
        int x = chunkX * 16 + random.nextInt(16);
        int z = chunkZ * 16 + random.nextInt(16);
        int y = world.getHeightValue(x, z);
        int bio = world.getBiomeGenForCoords(x, z).biomeID;
        //if(BiomeHandler.getBiomeSupportsGreatwood(bio) > random.nextFloat()) {
		if(ClimateHandler.getClimateSupportsGreatwood(world, chunkX * 16, 150, chunkZ * 16) > random.nextFloat()){
            boolean t = (new WorldGenGreatwoodTrees(false)).generate(world, random, x, y, z, random.nextInt(8) == 0);
            return t;
        } else {
            return false;
        }
    }

    public static boolean generateSilverwood(World world, Random random, int chunkX, int chunkZ) {
        int x = chunkX * 16 + random.nextInt(16);
        int z = chunkZ * 16 + random.nextInt(16);
        int y = world.getHeightValue(x, z);
        BiomeGenBase bio = world.getBiomeGenForCoords(x, z);
		SpawnRequirement.AcaciaJungle jungle = new SpawnRequirement.AcaciaJungle();
		if(jungle.isValid(world,chunkX, Global.SEALEVEL,chunkZ))
		{
			boolean t = (new WorldGenSilverwoodTrees(false, 7, 4)).generate(world, random, x, y, z);
			return t;
		}

        if(bio.equals(biomeMagicalForest) || bio.equals(biomeTaint) || !BiomeDictionary.isBiomeOfType(bio, Type.MAGICAL) && bio.biomeID != BiomeGenBase.forestHills.biomeID && bio.biomeID != BiomeGenBase.birchForestHills.biomeID) {
            return false;
        } else {
            boolean t = (new WorldGenSilverwoodTrees(false, 7, 4)).generate(world, random, x, y, z);
            return t;
        }
    }

    static {
        c = Aspect.aspects.values();
        basicAspects = new ArrayList();
        complexAspects = new ArrayList();
        dimensionBlacklist = new HashMap();
        biomeBlacklist = new HashMap();
    }
}
