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

package taeo.terrathaumcraft.asm;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import taeo.ttfcapi.asm.LogPileTransformer;
import taeo.ttfcapi.asm.SpawnerDisableTransformer;
import taeo.ttfcapi.asm.TFCPatcher;

@TransformerExclusions({ "taeo.terrathaumcraft.asm" })
@IFMLLoadingPlugin.SortingIndex(1002)
public class LoadingPlugin implements IFMLLoadingPlugin{


	@Override
	public String[] getASMTransformerClass() {
		//System.out.println("SOMETHING ACCESSED THE PLUGIN");
		return new String[]{
				NodeJarTransformer.class.getName(),
				WaterRenderTransformer.class.getName(),
				FoliageRenderTransformer.class.getName(),
				GrassRenderTransformer.class.getName(),
				ThaumcraftPatcher.class.getName(),
				//CoreSampleTransformer.class.getName(),
				//SpawnerDisableTransformer.class.getName(),
				//SpawnCheckTransformer.class.getName(),
				ThaumcraftMobInterfaceTransformer.class.getName(),
                //LogPileTransformer.class.getName(),
				//TFCPatcher.class.getName(),
				/*ItemGolemPlacerTransformer.class.getName(),*/
				/*EntityGolemBaseTransformer.class.getName(),*/
				UtilsTransformer.class.getName()/*,
				ThaumcraftWorldGenKiller.class.getName()*/};
	}

	@Override
	public String getModContainerClass() {
		return null;//TTCCore.class.getName();
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	public static File location;

	@Override
	public void injectData(Map<String, Object> data) {
		location = (File) data.get("coremodLocation");
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
