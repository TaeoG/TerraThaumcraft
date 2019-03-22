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

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package taeo.terrathaumcraft.render;

import com.bioxx.tfc.TileEntities.TEOre;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import taeo.terrathaumcraft.block.BlockMagicOre;
import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.terrathaumcraft.proxy.ClientProxy;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.block.BlockRenderer;
import thaumcraft.client.renderers.tile.TileNodeRenderer;
import thaumcraft.common.config.ConfigItems;

public class RenderMagicOre extends BlockRenderer implements ISimpleBlockRenderingHandler {
    public RenderMagicOre() {
    }

    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        /*block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);
        if(metadata == 0) {
            drawFaces(renderer, block, ((BlockCustomOre)block).icon[0], false);
        } else if(metadata == 7) {
            drawFaces(renderer, block, ((BlockCustomOre)block).icon[3], false);
        } else if(metadata < 7) {
            drawFaces(renderer, block, ((BlockCustomOre)block).icon[1], false);
            Color c = new Color(BlockCustomOreItem.colors[metadata]);
            float r = (float)c.getRed() / 255.0F;
            float g = (float)c.getGreen() / 255.0F;
            float b = (float)c.getBlue() / 255.0F;
            GL11.glColor3f(r, g, b);
            block.setBlockBounds(0.005F, 0.005F, 0.005F, 0.995F, 0.995F, 0.995F);
            renderer.setRenderBoundsFromBlock(block);
            drawFaces(renderer, block, ((BlockCustomOre)block).icon[2], false);
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
        }*/

    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        int bb = setBrightness(world, x, y, z, block);
        int metadata = world.getBlockMetadata(x, y, z);
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);
        Tessellator tess = Tessellator.instance;
        //renderer.renderStandardBlock(TFCBlocks.StoneIgExBrick, x, y, z);
        //if((((TEOre)world.getTileEntity(x,y,z)).extraData & 8) != 0)
        if (ClientProxy.renderPass == 0)
        {
            renderer.overrideBlockTexture = getRockTexture(world, x, y, z);
            renderer.renderStandardBlock(block, x, y, z);
            renderer.clearOverrideBlockTexture();
            //renderer.renderStandardBlock(block, x, y, z);

            /*if (Minecraft.getMinecraft().gameSettings.anisotropicFiltering > 1) {
                block.setBlockBounds(0.005F, 0.005F, 0.005F, 0.995F, 0.995F, 0.995F);
                renderer.setRenderBoundsFromBlock(block);
                t.setBrightness(bb);
                renderAllSides(world, x, y, z, block, renderer, Blocks.stone.getIcon(0, 0), false);
            }*/
            if(metadata == ReferenceTTC.OreMeta.CINNABAR /*cinnibar*/)
            {
                renderer.overrideBlockTexture = BlockMagicOre.cinnabarIcon;
                renderer.renderStandardBlock(block, x, y, z);
                renderer.clearOverrideBlockTexture();
            }
            if(metadata == ReferenceTTC.OreMeta.AMBER /*amberore*/)
            {
                renderer.overrideBlockTexture = BlockMagicOre.amberOreIcon;
                renderer.renderStandardBlock(block, x, y, z);
                renderer.clearOverrideBlockTexture();
            }
            if(metadata == ReferenceTTC.OreMeta.QUARTZ)
            {
                //renderer.overrideBlockTexture = BlockMagicOre.amberOreIcon;
                renderer.renderStandardBlock(block, x, y, z);
                renderer.clearOverrideBlockTexture();
            }
            if(metadata <=5) {

                tess.setColorOpaque_I(BlockMagicOre.colors[metadata]);
                tess.setBrightness(Math.max(bb, 160));
                renderAllSides(world, x, y, z, block, renderer, ((BlockMagicOre)block).icon[2], false);
                if(Minecraft.getMinecraft().gameSettings.anisotropicFiltering > 1) {
                    block.setBlockBounds(0.005F, 0.005F, 0.005F, 0.995F, 0.995F, 0.995F);
                    renderer.setRenderBoundsFromBlock(block);
                    tess.setBrightness(bb);
                    renderAllSides(world, x, y, z, block, renderer, Blocks.stone.getIcon(0, 0), false);
                }
                return true;
            }

            renderer.clearOverrideBlockTexture();
        } else
        {
           // if(metadata != 0 &&  metadata < 7)
            //{
            for(int i = 0; i<8; i++)
            {
                tess.addVertex(0, 0, 0);
            }

            renderer.renderStandardBlock(block, x, y, z);
            return true;

            //}

        }
        //block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        //renderer.setRenderBoundsFromBlock(block);
        return false;
    }

    public static IIcon getRockTexture(IBlockAccess world, int xCoord, int yCoord, int zCoord)
    {
        TEOre te = (TEOre)world.getTileEntity(xCoord, yCoord, zCoord);
        if(te!= null && te.baseBlockID > 0)
        {
            return Block.getBlockById(te.baseBlockID).getIcon(5, te.baseBlockMeta);
        }
        return Blocks.stone.getIcon(5, 0);
    }


    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    public int getRenderId() {
       return TTCBlocks.magicOreRenderID;
    }
}
