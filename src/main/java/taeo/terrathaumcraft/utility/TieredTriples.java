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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;

public class TieredTriples {
	private TreeMap<Integer, HashSet<Triple>> container;

	public TieredTriples()
	{
		container = new TreeMap<Integer, HashSet<Triple>>();
	}

	public void add(Triple triple)
	{
		if(!container.containsKey(triple.y))
		{
			container.put(triple.y, new HashSet<Triple>());
		}
		HashSet<Triple> tempSet = container.get(triple.y);
		tempSet.add(triple);
	}

	public boolean contains(Triple triple)
	{
		if(!container.containsKey(triple.y))
		{
			return false;
		}
		return container.get(triple.y).contains(triple);
	}

	public Triple remove()
	{
		Boolean found = false;
		Triple output = null;

		while(!found)
		{
			if(container.isEmpty())
			{
				return null;
			}
			HashSet<Triple> topLayer = container.get(container.lastKey());
			if(topLayer.isEmpty())
			{
				container.remove(container.lastKey());
			}
			else
			{
				Iterator<Triple> layerIter = topLayer.iterator();
				output = layerIter.next();
				layerIter.remove();
				if(topLayer.isEmpty())
				{
					container.remove(container.lastKey());
				}
				found = true;
			}
		}
		return output;



	}


}
