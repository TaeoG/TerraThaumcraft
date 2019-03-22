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

import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.asm.TAPITransformer;
import taeo.ttfcapi.reference.ReferenceTAPI;
import taeo.ttfcapi.utility.LogHelper;

import java.io.File;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ThaumcraftPatcher extends TAPITransformer{

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		//This code patches some issues with TFC. A NPE crash caused by rainfall while in other dimensions, poor reporting on ores with the propick etc
		if (
				name.equals("thaumcraft.common.lib.world.ThaumcraftWorldGenerator") ||
				name.equals("thaumcraft.common.lib.world.WorldGenGreatwoodTrees") ||
				name.equals("thaumcraft.common.lib.world.WorldGenHilltopStones") ||
				name.equals("thaumcraft.common.lib.world.WorldGenMound")

			)
		{
			return patchClassFromJar(name, basicClass, taeo.ttfcapi.asm.LoadingPlugin.location);
		}

		return basicClass;
	}

	private byte[] patchClassFromJar(String name, byte[] basicClass, File location)
	{
		try
		{

			if(!DEVENV)
			{
				InputStream zipStream;
				ZipFile zip = new ZipFile(location);
				ZipEntry entry = zip.getEntry(name.replace('.', '/') + ".class");
				if (entry == null)
				{
					LogHelper.error(ReferenceTTC.MOD_NAME,name + " not found in " + location.getName());
				} else
				{
					LogHelper.info(ReferenceTTC.MOD_NAME, "**** Patching " + name + " ****");

					zipStream = zip.getInputStream(entry);
					basicClass = new byte[(int) entry.getSize()];
					zipStream.read(basicClass);
					zipStream.close();
					zip.close();
				}


				//System.out.println("[" + "ItemProPick" + "]: " + "Class " + name + " patched!");
			}

		} catch (Exception e)
		{
			throw new RuntimeException("Error overriding " + name + " from " + location.getName(), e);
		}
		//return the new bytes
		return basicClass;

	}
}
