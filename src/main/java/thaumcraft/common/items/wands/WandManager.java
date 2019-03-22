
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package thaumcraft.common.items.wands;

import baubles.api.BaublesApi;
import com.bioxx.tfc.BlockSetup;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.IArchitect;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.IWandTriggerManager;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.EntitySpecialItem;
import thaumcraft.common.items.baubles.ItemAmuletVis;
import thaumcraft.common.items.wands.ItemFocusPouch;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.items.wands.foci.ItemFocusTrade;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXBlockSparkle;
import thaumcraft.common.lib.research.ResearchManager;
import thaumcraft.common.tiles.TileEldritchAltar;
import thaumcraft.common.tiles.TileInfusionMatrix;
import thaumcraft.common.tiles.TileInfusionPillar;
import thaumcraft.common.tiles.TileJarNode;
import thaumcraft.common.tiles.TileNode;
import thaumcraft.common.tiles.TilePedestal;
import thaumcraft.common.tiles.TileThaumatorium;

public class WandManager implements IWandTriggerManager {
    static HashMap<Integer, Long> cooldownServer = new HashMap();
    static HashMap<Integer, Long> cooldownClient = new HashMap();

    public WandManager() {
    }

    public static float getTotalVisDiscount(EntityPlayer player, Aspect aspect) {
        int total = 0;
        if(player == null) {
            return 0.0F;
        } else {
            IInventory baubles = BaublesApi.getBaubles(player);

            int level1;
            for(level1 = 0; level1 < 4; ++level1) {
                if(baubles.getStackInSlot(level1) != null && baubles.getStackInSlot(level1).getItem() instanceof IVisDiscountGear) {
                    total += ((IVisDiscountGear)baubles.getStackInSlot(level1).getItem()).getVisDiscount(baubles.getStackInSlot(level1), player, aspect);
                }
            }

            for(level1 = 0; level1 < 4; ++level1) {
                if(player.inventory.armorItemInSlot(level1) != null && player.inventory.armorItemInSlot(level1).getItem() instanceof IVisDiscountGear) {
                    total += ((IVisDiscountGear)player.inventory.armorItemInSlot(level1).getItem()).getVisDiscount(player.inventory.armorItemInSlot(level1), player, aspect);
                }
            }

            if(player.isPotionActive(Config.potionVisExhaustID) || player.isPotionActive(Config.potionInfVisExhaustID)) {
                level1 = 0;
                int level2 = 0;
                if(player.isPotionActive(Config.potionVisExhaustID)) {
                    level1 = player.getActivePotionEffect(Potion.potionTypes[Config.potionVisExhaustID]).getAmplifier();
                }

                if(player.isPotionActive(Config.potionInfVisExhaustID)) {
                    level2 = player.getActivePotionEffect(Potion.potionTypes[Config.potionInfVisExhaustID]).getAmplifier();
                }

                total -= (Math.max(level1, level2) + 1) * 10;
            }

            return (float)total / 100.0F;
        }
    }

    public static boolean consumeVisFromInventory(EntityPlayer player, AspectList cost) {
        IInventory baubles = BaublesApi.getBaubles(player);

        int a;
        for(a = 0; a < 4; ++a) {
            if(baubles.getStackInSlot(a) != null && baubles.getStackInSlot(a).getItem() instanceof ItemAmuletVis) {
                boolean item = ((ItemAmuletVis)baubles.getStackInSlot(a).getItem()).consumeAllVis(baubles.getStackInSlot(a), player, cost, true, true);
                if(item) {
                    return true;
                }
            }
        }

        for(a = player.inventory.mainInventory.length - 1; a >= 0; --a) {
            ItemStack var6 = player.inventory.mainInventory[a];
            if(var6 != null && var6.getItem() instanceof ItemWandCasting) {
                boolean done = ((ItemWandCasting)var6.getItem()).consumeAllVis(var6, player, cost, true, true);
                if(done) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean createCrucible(ItemStack is, EntityPlayer player, World world, int x, int y, int z) {
        ItemWandCasting wand = (ItemWandCasting)is.getItem();
        if(!world.isRemote) {
            world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
            world.setBlockToAir(x, y, z);
            world.setBlock(x, y, z, ConfigBlocks.blockMetalDevice, 0, 3);
            world.notifyBlocksOfNeighborChange(x, y, z, world.getBlock(x, y, z));
            world.markBlockForUpdate(x, y, z);
            world.addBlockEvent(x, y, z, ConfigBlocks.blockMetalDevice, 1, 1);
            return true;
        } else {
            return false;
        }
    }

    public static boolean createInfusionAltar(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z) {
        ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();

        for(int xx = x - 2; xx <= x; ++xx) {
            for(int yy = y - 2; yy <= y; ++yy) {
                for(int zz = z - 2; zz <= z; ++zz) {
                    if(fitInfusionAltar(world, xx, yy, zz) && wand.consumeAllVisCrafting(itemstack, player, (new AspectList()).add(Aspect.FIRE, 25).add(Aspect.EARTH, 25).add(Aspect.ORDER, 25).add(Aspect.AIR, 25).add(Aspect.ENTROPY, 25).add(Aspect.WATER, 25), true)) {
                        if(!world.isRemote) {
                            replaceInfusionAltar(world, xx, yy, zz);
                            return true;
                        }

                        return false;
                    }
                }
            }
        }

        return false;
    }

    public static boolean fitInfusionAltar(World world, int x, int y, int z) {
        ItemStack br1 = new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6);
        ItemStack br2 = new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 7);
        ItemStack bs = new ItemStack(ConfigBlocks.blockStoneDevice, 1, 2);
        new ItemStack(ConfigBlocks.blockStoneDevice, 1, 1);
        ItemStack[][][] blueprint = new ItemStack[][][]{{{null, null, null}, {null, bs, null}, {null, null, null}}, {{br1, null, br1}, {null, null, null}, {br1, null, br1}}, {{br2, null, br2}, {null, null, null}, {br2, null, br2}}};

        for(int yy = 0; yy < 3; ++yy) {
            for(int xx = 0; xx < 3; ++xx) {
                for(int zz = 0; zz < 3; ++zz) {
                    if(blueprint[yy][xx][zz] == null) {
                        if(xx == 1 && zz == 1 && yy == 2) {
                            TileEntity var14 = world.getTileEntity(x + xx, y - yy + 2, z + zz);
                            if(var14 == null || !(var14 instanceof TilePedestal)) {
                                return false;
                            }
                        } else if(!world.isAirBlock(x + xx, y - yy + 2, z + zz)) {
                            return false;
                        }
                    } else {
                        Block block = world.getBlock(x + xx, y - yy + 2, z + zz);
                        int md = world.getBlockMetadata(x + xx, y - yy + 2, z + zz);
                        if(!(new ItemStack(block, 1, md)).isItemEqual(blueprint[yy][xx][zz])) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public static void replaceInfusionAltar(World world, int x, int y, int z) {
        int[][][] blueprint = new int[][][]{{{0, 0, 0}, {0, 9, 0}, {0, 0, 0}}, {{1, 0, 1}, {0, 0, 0}, {1, 0, 1}}, {{2, 0, 3}, {0, 0, 0}, {4, 0, 5}}};

        for(int yy = 0; yy < 3; ++yy) {
            for(int xx = 0; xx < 3; ++xx) {
                for(int zz = 0; zz < 3; ++zz) {
                    if(blueprint[yy][xx][zz] != 0) {
                        if(blueprint[yy][xx][zz] == 1) {
                            world.setBlock(x + xx, y - yy + 2, z + zz, ConfigBlocks.blockStoneDevice, 4, 3);
                            world.addBlockEvent(x + xx, y - yy + 2, z + zz, ConfigBlocks.blockStoneDevice, 1, 0);
                        }

                        if(blueprint[yy][xx][zz] > 1 && blueprint[yy][xx][zz] < 9) {
                            world.setBlock(x + xx, y - yy + 2, z + zz, ConfigBlocks.blockStoneDevice, 3, 3);
                            TileInfusionPillar tis = (TileInfusionPillar)world.getTileEntity(x + xx, y - yy + 2, z + zz);
                            tis.orientation = (byte)blueprint[yy][xx][zz];
                            world.markBlockForUpdate(x + xx, y - yy + 2, z + zz);
                            world.addBlockEvent(x + xx, y - yy + 2, z + zz, ConfigBlocks.blockStoneDevice, 1, 0);
                        }

                        if(blueprint[yy][xx][zz] == 9) {
                            TileInfusionMatrix var9 = (TileInfusionMatrix)world.getTileEntity(x + xx, y - yy + 2, z + zz);
                            var9.active = true;
                            world.markBlockForUpdate(x + xx, y - yy + 2, z + zz);
                        }
                    }
                }
            }
        }

        world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
    }

    public static boolean createNodeJar(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z) {
        ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();

        for(int xx = x - 2; xx <= x; ++xx) {
            for(int yy = y - 3; yy <= y; ++yy) {
                for(int zz = z - 2; zz <= z; ++zz) {
                    if(fitNodeJar(world, xx, yy, zz) && wand.consumeAllVisCrafting(itemstack, player, (new AspectList()).add(Aspect.FIRE, 70).add(Aspect.EARTH, 70).add(Aspect.ORDER, 70).add(Aspect.AIR, 70).add(Aspect.ENTROPY, 70).add(Aspect.WATER, 70), true)) {
                        if(!world.isRemote) {
                            replaceNodeJar(world, xx, yy, zz);
                            return true;
                        }

                        return false;
                    }
                }
            }
        }

        return false;
    }

    public static boolean createThaumatorium(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side) {
        ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();
        if(world.getBlock(x, y + 1, z) != ConfigBlocks.blockMetalDevice || world.getBlockMetadata(x, y + 1, z) != 9 || world.getBlock(x, y - 1, z) != ConfigBlocks.blockMetalDevice || world.getBlockMetadata(x, y - 1, z) != 0) {
            if(world.getBlock(x, y - 1, z) != ConfigBlocks.blockMetalDevice || world.getBlockMetadata(x, y - 1, z) != 9 || world.getBlock(x, y - 2, z) != ConfigBlocks.blockMetalDevice || world.getBlockMetadata(x, y - 2, z) != 0) {
                return false;
            }

            --y;
        }

        if(wand.consumeAllVisCrafting(itemstack, player, (new AspectList()).add(Aspect.FIRE, 15).add(Aspect.ORDER, 30).add(Aspect.WATER, 30), true) && !world.isRemote) {
            world.setBlock(x, y, z, ConfigBlocks.blockMetalDevice, 10, 0);
            world.setBlock(x, y + 1, z, ConfigBlocks.blockMetalDevice, 11, 0);
            TileEntity tile = world.getTileEntity(x, y, z);
            if(tile != null && tile instanceof TileThaumatorium) {
                ((TileThaumatorium)tile).facing = ForgeDirection.getOrientation(side);
            }

            world.markBlockForUpdate(x, y, z);
            world.markBlockForUpdate(x, y + 1, z);
            world.notifyBlockChange(x, y, z, ConfigBlocks.blockMetalDevice);
            world.notifyBlockChange(x, y + 1, z, ConfigBlocks.blockMetalDevice);
            PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x, y, z, -9999), new TargetPoint(world.provider.dimensionId, (double)x, (double)y, (double)z, 32.0D));
            PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x, y + 1, z, -9999), new TargetPoint(world.provider.dimensionId, (double)x, (double)y, (double)z, 32.0D));
            world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
            return true;
        } else {
            return false;
        }
    }

    static boolean containsMatch(boolean strict, List<ItemStack> inputs, ItemStack... targets) {
        Iterator i$ = inputs.iterator();

        while(i$.hasNext()) {
            ItemStack input = (ItemStack)i$.next();
            ItemStack[] arr$ = targets;
            int len$ = targets.length;

            for(int i$1 = 0; i$1 < len$; ++i$1) {
                ItemStack target = arr$[i$1];
                if(OreDictionary.itemMatches(input, target, strict)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean fitNodeJar(World world, int x, int y, int z) {
        int[][][] blueprint = new int[][][]{{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}, {{2, 2, 2}, {2, 2, 2}, {2, 2, 2}}, {{2, 2, 2}, {2, 3, 2}, {2, 2, 2}}, {{2, 2, 2}, {2, 2, 2}, {2, 2, 2}}};

        for(int yy = 0; yy < 4; ++yy) {
            for(int xx = 0; xx < 3; ++xx) {
                for(int zz = 0; zz < 3; ++zz) {
                    Block block = world.getBlock(x + xx, y - yy + 2, z + zz);
                    int md = world.getBlockMetadata(x + xx, y - yy + 2, z + zz);
                    if(blueprint[yy][xx][zz] == 1 && !containsMatch(false, OreDictionary.getOres("slabWood"), new ItemStack[]{new ItemStack(block, 1, md)})) {
                        return false;
                    }

                    if(blueprint[yy][xx][zz] == 2 && block != Blocks.glass) {
                        return false;
                    }

                    if(blueprint[yy][xx][zz] == 3) {
                        TileEntity tile = world.getTileEntity(x + xx, y - yy + 2, z + zz);
                        if(tile == null || !(tile instanceof INode) || tile instanceof TileJarNode) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public static void replaceNodeJar(World world, int x, int y, int z) {
        if(!world.isRemote) {
            int[][][] blueprint = new int[][][]{{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}, {{2, 2, 2}, {2, 2, 2}, {2, 2, 2}}, {{2, 2, 2}, {2, 3, 2}, {2, 2, 2}}, {{2, 2, 2}, {2, 2, 2}, {2, 2, 2}}};

            for(int yy = 0; yy < 4; ++yy) {
                for(int xx = 0; xx < 3; ++xx) {
                    for(int zz = 0; zz < 3; ++zz) {
                        if(blueprint[yy][xx][zz] == 3) {
                            TileEntity tile = world.getTileEntity(x + xx, y - yy + 2, z + zz);
                            INode node = (INode)tile;
                            AspectList na = node.getAspects().copy();
                            int nt = node.getNodeType().ordinal();
                            int nm = -1;
                            if(node.getNodeModifier() != null) {
                                nm = node.getNodeModifier().ordinal();
                            }

                            if(world.rand.nextFloat() < 0.75F) {
                                if(node.getNodeModifier() == null) {
                                    nm = NodeModifier.PALE.ordinal();
                                } else if(node.getNodeModifier() == NodeModifier.BRIGHT) {
                                    nm = -1;
                                } else if(node.getNodeModifier() == NodeModifier.PALE) {
                                    nm = NodeModifier.FADING.ordinal();
                                }
                            }

                            String nid = node.getId();
                            node.setAspects(new AspectList());
                            world.removeTileEntity(x + xx, y - yy + 2, z + zz);
                            world.setBlock(x + xx, y - yy + 2, z + zz, ConfigBlocks.blockJar, 2, 3);
                            tile = world.getTileEntity(x + xx, y - yy + 2, z + zz);
                            TileJarNode jar = (TileJarNode)tile;
                            jar.setAspects(na);
                            if(nm >= 0) {
                                jar.setNodeModifier(NodeModifier.values()[nm]);
                            }

                            jar.setNodeType(NodeType.values()[nt]);
                            jar.setId(nid);
                            world.addBlockEvent(x + xx, y - yy + 2, z + zz, ConfigBlocks.blockJar, 9, 0);
                        } else {
                            world.setBlockToAir(x + xx, y - yy + 2, z + zz);
                        }
                    }
                }
            }

            world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
        }
    }

    public static boolean createArcaneFurnace(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z) {
        ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();

        for(int xx = x - 2; xx <= x; ++xx) {
            for(int yy = y - 2; yy <= y; ++yy) {
                for(int zz = z - 2; zz <= z; ++zz) {
                    if(fitArcaneFurnace(world, xx, yy, zz) && wand.consumeAllVisCrafting(itemstack, player, (new AspectList()).add(Aspect.FIRE, 50).add(Aspect.EARTH, 50), true)) {
                        if(!world.isRemote) {
                            replaceArcaneFurnace(world, xx, yy, zz);
                            return true;
                        }

                        return false;
                    }
                }
            }
        }

        return false;
    }

    public static boolean fitArcaneFurnace(World world, int x, int y, int z) {
        Block bo = Blocks.obsidian;
        Block bn = Blocks.nether_brick;
        Block bf = Blocks.iron_bars;
        Block bl = BlockSetup.lavaStationary;
        Block[][][] blueprint = new Block[][][]{{{bn, bo, bn}, {bo, Blocks.air, bo}, {bn, bo, bn}}, {{bn, bo, bn}, {bo, bl, bo}, {bn, bo, bn}}, {{bn, bo, bn}, {bo, bo, bo}, {bn, bo, bn}}};
        boolean fencefound = false;

        for(int yy = 0; yy < 3; ++yy) {
            for(int xx = 0; xx < 3; ++xx) {
                for(int zz = 0; zz < 3; ++zz) {
                    Block block = world.getBlock(x + xx, y - yy + 2, z + zz);
                    if(world.isAirBlock(x + xx, y - yy + 2, z + zz)) {
                        block = Blocks.air;
                    }

                    if(block != blueprint[yy][xx][zz]) {
                        if(yy != 1 || fencefound || block != bf || xx == zz || xx != 1 && zz != 1) {
                            return false;
                        }

                        fencefound = true;
                    }
                }
            }
        }

        return fencefound;
    }

    public static boolean replaceArcaneFurnace(World world, int x, int y, int z) {
        boolean fencefound = false;

        int yy;
        int zz;
        int xx;
        for(yy = 0; yy < 3; ++yy) {
            zz = 1;

            for(xx = 0; xx < 3; ++xx) {
                for(int xx1 = 0; xx1 < 3; ++xx1) {
                    int md = zz;
                    if(world.getBlock(x + xx1, y + yy, z + xx) == BlockSetup.lavaStationary || world.getBlock(x + xx1, y + yy, z + xx) == BlockSetup.lava) {
                        md = 0;
                    }

                    if(world.getBlock(x + xx1, y + yy, z + xx) == Blocks.iron_bars) {
                        md = 10;
                    }

                    if(!world.isAirBlock(x + xx1, y + yy, z + xx)) {
                        world.setBlock(x + xx1, y + yy, z + xx, ConfigBlocks.blockArcaneFurnace, md, 0);
                        world.addBlockEvent(x + xx1, y + yy, z + xx, ConfigBlocks.blockArcaneFurnace, 1, 4);
                    }

                    ++zz;
                }
            }
        }

        for(yy = 0; yy < 3; ++yy) {
            for(zz = 0; zz < 3; ++zz) {
                for(xx = 0; xx < 3; ++xx) {
                    world.markBlockForUpdate(x + xx, y + yy, z + zz);
                }
            }
        }

        world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
        return fencefound;
    }

    public static boolean createThaumonomicon(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z) {
        if(!world.isRemote) {
            ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();
            if(wand.getFocus(itemstack) != null) {
                return false;
            } else {
                world.setBlockToAir(x, y, z);
                EntitySpecialItem entityItem = new EntitySpecialItem(world, (double)((float)x + 0.5F), (double)((float)y + 0.3F), (double)((float)z + 0.5F), new ItemStack(ConfigItems.itemThaumonomicon));
                entityItem.motionY = 0.0D;
                entityItem.motionX = 0.0D;
                entityItem.motionZ = 0.0D;
                world.spawnEntityInWorld(entityItem);
                PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x, y, z, -9999), new TargetPoint(world.provider.dimensionId, (double)x, (double)y, (double)z, 32.0D));
                world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
                return true;
            }
        } else {
            return false;
        }
    }

    private static boolean createOculus(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side) {
        if(!world.isRemote) {
            TileEntity tile = world.getTileEntity(x, y, z);
            TileEntity node = world.getTileEntity(x, y + 1, z);
            if(tile != null && node != null && tile instanceof TileEldritchAltar && ((TileEldritchAltar)tile).getEyes() == 4 && !((TileEldritchAltar)tile).isOpen() && node instanceof TileNode && ((TileNode)node).getNodeType() == NodeType.DARK && ((TileEldritchAltar)tile).checkForMaze()) {
                ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();
                if(wand.consumeAllVisCrafting(itemstack, player, (new AspectList()).add(Aspect.AIR, 100).add(Aspect.FIRE, 100).add(Aspect.EARTH, 100).add(Aspect.WATER, 100).add(Aspect.ORDER, 100).add(Aspect.ENTROPY, 100), true)) {
                    world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
                    ((TileEldritchAltar)tile).setOpen(true);
                    world.setBlockToAir(x, y + 1, z);
                    world.setBlock(x, y + 1, z, ConfigBlocks.blockEldritchPortal);
                    tile.markDirty();
                    world.markBlockForUpdate(x, y, z);
                }
            }
        }

        return false;
    }

    public static void changeFocus(ItemStack is, World w, EntityPlayer player, String focus) {
        ItemWandCasting wand = (ItemWandCasting)is.getItem();
        TreeMap foci = new TreeMap();
        HashMap pouches = new HashMap();
        int pouchcount = 0;
        ItemStack item = null;
        IInventory baubles = BaublesApi.getBaubles(player);

        int newkey;
        ItemStack[] pid;
        int pouchslot;
        for(newkey = 0; newkey < 4; ++newkey) {
            if(baubles.getStackInSlot(newkey) != null && baubles.getStackInSlot(newkey).getItem() instanceof ItemFocusPouch) {
                ++pouchcount;
                item = baubles.getStackInSlot(newkey);
                pouches.put(Integer.valueOf(pouchcount), Integer.valueOf(newkey - 4));
                pid = ((ItemFocusPouch)item.getItem()).getInventory(item);

                for(pouchslot = 0; pouchslot < pid.length; ++pouchslot) {
                    item = pid[pouchslot];
                    if(item != null && item.getItem() instanceof ItemFocusBasic) {
                        foci.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), Integer.valueOf(pouchslot + pouchcount * 1000));
                    }
                }
            }
        }

        for(newkey = 0; newkey < 36; ++newkey) {
            item = player.inventory.mainInventory[newkey];
            if(item != null && item.getItem() instanceof ItemFocusBasic) {
                foci.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), Integer.valueOf(newkey));
            }

            if(item != null && item.getItem() instanceof ItemFocusPouch) {
                ++pouchcount;
                pouches.put(Integer.valueOf(pouchcount), Integer.valueOf(newkey));
                pid = ((ItemFocusPouch)item.getItem()).getInventory(item);

                for(pouchslot = 0; pouchslot < pid.length; ++pouchslot) {
                    item = pid[pouchslot];
                    if(item != null && item.getItem() instanceof ItemFocusBasic) {
                        foci.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), Integer.valueOf(pouchslot + pouchcount * 1000));
                    }
                }
            }
        }

        if(!focus.equals("REMOVE") && foci.size() != 0) {
            if(foci != null && foci.size() > 0 && focus != null) {
                String var15 = focus;
                if(foci.get(focus) == null) {
                    var15 = (String)foci.higherKey(focus);
                }

                if(var15 == null || foci.get(var15) == null) {
                    var15 = (String)foci.firstKey();
                }

                if(((Integer)foci.get(var15)).intValue() < 1000 && ((Integer)foci.get(var15)).intValue() >= 0) {
                    item = player.inventory.mainInventory[((Integer)foci.get(var15)).intValue()].copy();
                } else {
                    int var16 = ((Integer)foci.get(var15)).intValue() / 1000;
                    if(pouches.containsKey(Integer.valueOf(var16))) {
                        pouchslot = ((Integer)pouches.get(Integer.valueOf(var16))).intValue();
                        int focusslot = ((Integer)foci.get(var15)).intValue() - var16 * 1000;
                        ItemStack tmp = null;
                        if(pouchslot >= 0) {
                            tmp = player.inventory.mainInventory[pouchslot].copy();
                        } else {
                            tmp = baubles.getStackInSlot(pouchslot + 4).copy();
                        }

                        item = fetchFocusFromPouch(player, focusslot, tmp, pouchslot);
                    }
                }

                if(item == null) {
                    return;
                }

                if(((Integer)foci.get(var15)).intValue() < 1000 && ((Integer)foci.get(var15)).intValue() >= 0) {
                    player.inventory.setInventorySlotContents(((Integer)foci.get(var15)).intValue(), (ItemStack)null);
                }

                w.playSoundAtEntity(player, "thaumcraft:cameraticks", 0.3F, 1.0F);
                if(wand.getFocus(is) != null && (addFocusToPouch(player, wand.getFocusItem(is).copy(), pouches) || player.inventory.addItemStackToInventory(wand.getFocusItem(is).copy()))) {
                    wand.setFocus(is, (ItemStack)null);
                }

                if(wand.getFocus(is) == null) {
                    wand.setFocus(is, item);
                } else if(!addFocusToPouch(player, item, pouches)) {
                    player.inventory.addItemStackToInventory(item);
                }
            }

        } else {
            if(wand.getFocus(is) != null && (addFocusToPouch(player, wand.getFocusItem(is).copy(), pouches) || player.inventory.addItemStackToInventory(wand.getFocusItem(is).copy()))) {
                wand.setFocus(is, (ItemStack)null);
                w.playSoundAtEntity(player, "thaumcraft:cameraticks", 0.3F, 0.9F);
            }

        }
    }

    private static ItemStack fetchFocusFromPouch(EntityPlayer player, int focusid, ItemStack pouch, int pouchslot) {
        ItemStack focus = null;
        ItemStack[] inv = ((ItemFocusPouch)pouch.getItem()).getInventory(pouch);
        ItemStack contents = inv[focusid];
        if(contents != null && contents.getItem() instanceof ItemFocusBasic) {
            focus = contents.copy();
            inv[focusid] = null;
            ((ItemFocusPouch)pouch.getItem()).setInventory(pouch, inv);
            if(pouchslot >= 0) {
                player.inventory.setInventorySlotContents(pouchslot, pouch);
                player.inventory.markDirty();
            } else {
                IInventory baubles = BaublesApi.getBaubles(player);
                baubles.setInventorySlotContents(pouchslot + 4, pouch);
                baubles.markDirty();
            }
        }

        return focus;
    }

    private static boolean addFocusToPouch(EntityPlayer player, ItemStack focus, HashMap<Integer, Integer> pouches) {
        IInventory baubles = BaublesApi.getBaubles(player);
        Iterator i$ = pouches.values().iterator();

        while(i$.hasNext()) {
            Integer pouchslot = (Integer)i$.next();
            ItemStack pouch;
            if(pouchslot.intValue() >= 0) {
                pouch = player.inventory.mainInventory[pouchslot.intValue()];
            } else {
                pouch = baubles.getStackInSlot(pouchslot.intValue() + 4);
            }

            ItemStack[] inv = ((ItemFocusPouch)pouch.getItem()).getInventory(pouch);

            for(int q = 0; q < inv.length; ++q) {
                ItemStack contents = inv[q];
                if(contents == null) {
                    inv[q] = focus.copy();
                    ((ItemFocusPouch)pouch.getItem()).setInventory(pouch, inv);
                    if(pouchslot.intValue() >= 0) {
                        player.inventory.setInventorySlotContents(pouchslot.intValue(), pouch);
                        player.inventory.markDirty();
                    } else {
                        baubles.setInventorySlotContents(pouchslot.intValue() + 4, pouch);
                        baubles.markDirty();
                    }

                    return true;
                }
            }
        }

        return false;
    }

    public static void toggleMisc(ItemStack itemstack, World world, EntityPlayer player) {
        if(itemstack.getItem() instanceof ItemWandCasting) {
            ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();
            if(wand.getFocus(itemstack) != null && wand.getFocus(itemstack) instanceof IArchitect && wand.getFocus(itemstack).isUpgradedWith(wand.getFocusItem(itemstack), FocusUpgradeType.architect)) {
                int dim = getAreaDim(itemstack);
                IArchitect fa = (IArchitect)wand.getFocus(itemstack);
                if(player.isSneaking()) {
                    ++dim;
                    if(dim > (wand.getFocusItem(itemstack).getItem() instanceof ItemFocusTrade?2:3)) {
                        dim = 0;
                    }

                    setAreaDim(itemstack, dim);
                } else {
                    int areax = getAreaX(itemstack);
                    int areay = getAreaY(itemstack);
                    int areaz = getAreaZ(itemstack);
                    if(dim == 0) {
                        ++areax;
                        ++areaz;
                        ++areay;
                    } else if(dim == 1) {
                        ++areax;
                    } else if(dim == 2) {
                        ++areaz;
                    } else if(dim == 3) {
                        ++areay;
                    }

                    if(areax > wand.getFocus(itemstack).getMaxAreaSize(wand.getFocusItem(itemstack))) {
                        areax = 0;
                    }

                    if(areaz > wand.getFocus(itemstack).getMaxAreaSize(wand.getFocusItem(itemstack))) {
                        areaz = 0;
                    }

                    if(areay > wand.getFocus(itemstack).getMaxAreaSize(wand.getFocusItem(itemstack))) {
                        areay = 0;
                    }

                    setAreaX(itemstack, areax);
                    setAreaY(itemstack, areay);
                    setAreaZ(itemstack, areaz);
                }
            }

        }
    }

    public static int getAreaDim(ItemStack stack) {
        return stack.hasTagCompound() && stack.stackTagCompound.hasKey("aread")?stack.stackTagCompound.getInteger("aread"):0;
    }

    public static int getAreaX(ItemStack stack) {
        ItemWandCasting wand = (ItemWandCasting)stack.getItem();
        if(stack.hasTagCompound() && stack.stackTagCompound.hasKey("areax")) {
            int a = stack.stackTagCompound.getInteger("areax");
            if(a > wand.getFocus(stack).getMaxAreaSize(wand.getFocusItem(stack))) {
                a = wand.getFocus(stack).getMaxAreaSize(wand.getFocusItem(stack));
            }

            return a;
        } else {
            return wand.getFocus(stack).getMaxAreaSize(wand.getFocusItem(stack));
        }
    }

    public static int getAreaY(ItemStack stack) {
        ItemWandCasting wand = (ItemWandCasting)stack.getItem();
        if(stack.hasTagCompound() && stack.stackTagCompound.hasKey("areay")) {
            int a = stack.stackTagCompound.getInteger("areay");
            if(a > wand.getFocus(stack).getMaxAreaSize(wand.getFocusItem(stack))) {
                a = wand.getFocus(stack).getMaxAreaSize(wand.getFocusItem(stack));
            }

            return a;
        } else {
            return wand.getFocus(stack).getMaxAreaSize(wand.getFocusItem(stack));
        }
    }

    public static int getAreaZ(ItemStack stack) {
        ItemWandCasting wand = (ItemWandCasting)stack.getItem();
        if(stack.hasTagCompound() && stack.stackTagCompound.hasKey("areaz")) {
            int a = stack.stackTagCompound.getInteger("areaz");
            if(a > wand.getFocus(stack).getMaxAreaSize(wand.getFocusItem(stack))) {
                a = wand.getFocus(stack).getMaxAreaSize(wand.getFocusItem(stack));
            }

            return a;
        } else {
            return wand.getFocus(stack).getMaxAreaSize(wand.getFocusItem(stack));
        }
    }

    public static void setAreaX(ItemStack stack, int area) {
        if(stack.hasTagCompound()) {
            stack.stackTagCompound.setInteger("areax", area);
        }

    }

    public static void setAreaY(ItemStack stack, int area) {
        if(stack.hasTagCompound()) {
            stack.stackTagCompound.setInteger("areay", area);
        }

    }

    public static void setAreaZ(ItemStack stack, int area) {
        if(stack.hasTagCompound()) {
            stack.stackTagCompound.setInteger("areaz", area);
        }

    }

    public static void setAreaDim(ItemStack stack, int dim) {
        if(stack.hasTagCompound()) {
            stack.stackTagCompound.setInteger("aread", dim);
        }

    }

    static boolean isOnCooldown(EntityLivingBase entityLiving) {
        return entityLiving.worldObj.isRemote && cooldownClient.containsKey(Integer.valueOf(entityLiving.getEntityId()))?((Long)cooldownClient.get(Integer.valueOf(entityLiving.getEntityId()))).longValue() > System.currentTimeMillis():(!entityLiving.worldObj.isRemote && cooldownServer.containsKey(Integer.valueOf(entityLiving.getEntityId()))?((Long)cooldownServer.get(Integer.valueOf(entityLiving.getEntityId()))).longValue() > System.currentTimeMillis():false);
    }

    public static float getCooldown(EntityLivingBase entityLiving) {
        return entityLiving.worldObj.isRemote && cooldownClient.containsKey(Integer.valueOf(entityLiving.getEntityId()))?(float)(((Long)cooldownClient.get(Integer.valueOf(entityLiving.getEntityId()))).longValue() - System.currentTimeMillis()) / 1000.0F:0.0F;
    }

    public static void setCooldown(EntityLivingBase entityLiving, int cd) {
        if(cd == 0) {
            cooldownClient.remove(Integer.valueOf(entityLiving.getEntityId()));
            cooldownServer.remove(Integer.valueOf(entityLiving.getEntityId()));
        } else if(entityLiving.worldObj.isRemote) {
            cooldownClient.put(Integer.valueOf(entityLiving.getEntityId()), Long.valueOf(System.currentTimeMillis() + (long)cd));
        } else {
            cooldownServer.put(Integer.valueOf(entityLiving.getEntityId()), Long.valueOf(System.currentTimeMillis() + (long)cd));
        }

    }

    public boolean performTrigger(World world, ItemStack wand, EntityPlayer player, int x, int y, int z, int side, int event) {
        switch(event) {
            case 0:
                return createThaumonomicon(wand, player, world, x, y, z);
            case 1:
                return createCrucible(wand, player, world, x, y, z);
            case 2:
                if(ResearchManager.isResearchComplete(player.getCommandSenderName(), "INFERNALFURNACE")) {
                    return createArcaneFurnace(wand, player, world, x, y, z);
                }
                break;
            case 3:
                if(ResearchManager.isResearchComplete(player.getCommandSenderName(), "INFUSION")) {
                    return createInfusionAltar(wand, player, world, x, y, z);
                }
                break;
            case 4:
                if(ResearchManager.isResearchComplete(player.getCommandSenderName(), "NODEJAR")) {
                    return createNodeJar(wand, player, world, x, y, z);
                }
                break;
            case 5:
                if(ResearchManager.isResearchComplete(player.getCommandSenderName(), "THAUMATORIUM")) {
                    return createThaumatorium(wand, player, world, x, y, z, side);
                }
                break;
            case 6:
                if(ResearchManager.isResearchComplete(player.getCommandSenderName(), "OCULUS")) {
                    return createOculus(wand, player, world, x, y, z, side);
                }
                break;
            case 7:
                if(ResearchManager.isResearchComplete(player.getCommandSenderName(), "ADVALCHEMYFURNACE")) {
                    return createAdvancedAlchemicalFurnace(wand, player, world, x, y, z, side);
                }
        }

        return false;
    }

    private static boolean createAdvancedAlchemicalFurnace(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side) {
        if(world.isRemote) {
            return false;
        } else {
            int[][][] blueprint = new int[][][]{{{4, 4, 4}, {4, 3, 4}, {4, 4, 4}}, {{1, 2, 1}, {2, 0, 2}, {1, 2, 1}}};

            for(int a = -1; a <= 1; ++a) {
                for(int b = -1; b <= 1; ++b) {
                    for(int c = -1; c <= 1; ++c) {
                        if(world.getBlock(x + a, y + b, z + c) == ConfigBlocks.blockStoneDevice && world.getBlockMetadata(x + a, y + b, z + c) == 0) {
                            int aa;
                            int bb;
                            for(int wand = -1; wand <= 1; ++wand) {
                                for(aa = 0; aa <= 1; ++aa) {
                                    bb = -1;

                                    while(bb <= 1) {
                                        if(blueprint[aa][wand + 1][bb + 1] != 1 || world.getBlock(x + a + wand, y + b + aa, z + c + bb) == ConfigBlocks.blockMetalDevice && world.getBlockMetadata(x + a + wand, y + b + aa, z + c + bb) == 1) {
                                            if(blueprint[aa][wand + 1][bb + 1] == 2 && (world.getBlock(x + a + wand, y + b + aa, z + c + bb) != ConfigBlocks.blockMetalDevice || world.getBlockMetadata(x + a + wand, y + b + aa, z + c + bb) != 9)) {
                                                return false;
                                            }

                                            if(blueprint[aa][wand + 1][bb + 1] == 4 && (world.getBlock(x + a + wand, y + b + aa, z + c + bb) != ConfigBlocks.blockMetalDevice || world.getBlockMetadata(x + a + wand, y + b + aa, z + c + bb) != 3)) {
                                                return false;
                                            }

                                            if(blueprint[aa][wand + 1][bb + 1] != 3 || world.getBlock(x + a + wand, y + b + aa, z + c + bb) == ConfigBlocks.blockStoneDevice && world.getBlockMetadata(x + a + wand, y + b + aa, z + c + bb) == 0) {
                                                ++bb;
                                                continue;
                                            }

                                            return false;
                                        }

                                        return false;
                                    }
                                }
                            }

                            ItemWandCasting var15 = (ItemWandCasting)itemstack.getItem();
                            if(!var15.consumeAllVisCrafting(itemstack, player, (new AspectList()).add(Aspect.FIRE, 50).add(Aspect.WATER, 50).add(Aspect.ORDER, 50), true)) {
                                return false;
                            }

                            world.setBlock(x + a, y + b, z + c, ConfigBlocks.blockAlchemyFurnace);
                            world.setBlock(x + a - 1, y + b, z + c, ConfigBlocks.blockAlchemyFurnace, 1, 3);
                            world.setBlock(x + a + 1, y + b, z + c, ConfigBlocks.blockAlchemyFurnace, 1, 3);
                            world.setBlock(x + a, y + b, z + c - 1, ConfigBlocks.blockAlchemyFurnace, 1, 3);
                            world.setBlock(x + a, y + b, z + c + 1, ConfigBlocks.blockAlchemyFurnace, 1, 3);
                            world.setBlock(x + a - 1, y + b, z + c - 1, ConfigBlocks.blockAlchemyFurnace, 4, 3);
                            world.setBlock(x + a + 1, y + b, z + c + 1, ConfigBlocks.blockAlchemyFurnace, 4, 3);
                            world.setBlock(x + a + 1, y + b, z + c - 1, ConfigBlocks.blockAlchemyFurnace, 4, 3);
                            world.setBlock(x + a - 1, y + b, z + c + 1, ConfigBlocks.blockAlchemyFurnace, 4, 3);
                            world.setBlock(x + a - 1, y + b + 1, z + c, ConfigBlocks.blockAlchemyFurnace, 3, 3);
                            world.setBlock(x + a + 1, y + b + 1, z + c, ConfigBlocks.blockAlchemyFurnace, 3, 3);
                            world.setBlock(x + a, y + b + 1, z + c - 1, ConfigBlocks.blockAlchemyFurnace, 3, 3);
                            world.setBlock(x + a, y + b + 1, z + c + 1, ConfigBlocks.blockAlchemyFurnace, 3, 3);
                            world.setBlock(x + a - 1, y + b + 1, z + c - 1, ConfigBlocks.blockAlchemyFurnace, 2, 3);
                            world.setBlock(x + a + 1, y + b + 1, z + c + 1, ConfigBlocks.blockAlchemyFurnace, 2, 3);
                            world.setBlock(x + a + 1, y + b + 1, z + c - 1, ConfigBlocks.blockAlchemyFurnace, 2, 3);
                            world.setBlock(x + a - 1, y + b + 1, z + c + 1, ConfigBlocks.blockAlchemyFurnace, 2, 3);
                            world.playSoundEffect((double)(x + a) + 0.5D, (double)(y + b) + 0.5D, (double)(z + c) + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);

                            for(aa = -1; aa <= 1; ++aa) {
                                for(bb = 0; bb <= 1; ++bb) {
                                    for(int cc = -1; cc <= 1; ++cc) {
                                        PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x + a + aa, y + b + bb, z + c + cc, -9999), new TargetPoint(world.provider.dimensionId, (double)(x + a), (double)(y + b), (double)(z + c), 32.0D));
                                    }
                                }
                            }

                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }
}
