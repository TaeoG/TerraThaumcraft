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

package taeo.terrathaumcraft.proxy;

import com.bioxx.tfc.api.TFCBlocks;
import taeo.terrathaumcraft.TerraThaumcraftMod;
import taeo.terrathaumcraft.init.TTCBlocks;
import taeo.terrathaumcraft.item.WandManagerTTC;
import taeo.terrathaumcraft.render.*;
import taeo.terrathaumcraft.tile.*;
import taeo.ttfcapi.reference.ReferenceTAPI;
import taeo.ttfcapi.render.RenderMossStone;
import thaumcraft.client.renderers.tile.TileCrucibleRenderer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy{
	public static int renderPass;
	public static WandManagerTTC wandManager = new WandManagerTTC();
	@Override
	public void initKeyBinding() {
		
	}

	@Override
	public void initTileEntities() {
		this.registerTileEntities();
	}

	public void initRenderers()
	{
		RenderingRegistry.registerBlockHandler(TTCBlocks.anvilRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderThaumiumAnvil());
		RenderingRegistry.registerBlockHandler(TTCBlocks.magicOreRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderMagicOre());
		RenderingRegistry.registerBlockHandler(ReferenceTAPI.mossStoneRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderMossStone());
		RenderingRegistry.registerBlockHandler(TTCBlocks.magicBarrelRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderMagicBarrel());
		RenderingRegistry.registerBlockHandler(TFCBlocks.loomRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderLoomTTC());
        RenderingRegistry.registerBlockHandler(TTCBlocks.tilledsoulsandRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderSoulFarm());
        RenderingRegistry.registerBlockHandler(TTCBlocks.cropRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderNetherWartCrop());
        RenderingRegistry.registerBlockHandler(TTCBlocks.chestRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderChestTTC());
		//RenderingRegistry.registerBlockHandler(TFCBlocks.standRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderStandTTC());
		//RenderingRegistry.registerBlockHandler(TFCBlocks.grassRenderId = RenderingRegistry.getNextAvailableRenderId(), new TTCBlockRenderHandler());
		//RenderingRegistry.registerBlockHandler(TFCBlocks.clayGrassRenderId = RenderingRegistry.getNextAvailableRenderId(), new TTCBlockRenderHandler());
		//RenderingRegistry.registerBlockHandler(TFCBlocks.peatGrassRenderId = RenderingRegistry.getNextAvailableRenderId(), new TTCBlockRenderHandler());
		//RenderingRegistry.registerBlockHandler(TFCBlocks.waterPlantRenderId = RenderingRegistry.getNextAvailableRenderId(), new TTCBlockRenderHandler());

	}
	public void registerTileEntities()
	{
        super.registerTileEntities(false);
		ClientRegistry.registerTileEntity(TEAnvilTTC.class, "TileAnvilTTC", new TESRThaumiumAnvil());
		ClientRegistry.registerTileEntity(TileCrucibleTTC.class, "TileCrucibleTTC", new TileCrucibleRenderer());
        ClientRegistry.registerTileEntity(TEChestTTC.class, "chestTTC", new TESRChestTTC());



    }
	@Override
	public void initGuiHandler()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(TerraThaumcraftMod.instance, new taeo.terrathaumcraft.gui.GuiHandlerTTC());
		// Register Gui Event Handler
		MinecraftForge.EVENT_BUS.register(new taeo.terrathaumcraft.gui.GuiHandlerTTC());
	}


}
