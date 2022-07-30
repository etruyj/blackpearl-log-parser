//===================================================================
// MapDS3OperationstoChunk.java
// 	Description:
// 		Creates a HashMap<String, ArrayList<DS3Operation>> to
// 		allow searching the ds3 operations by the job's chunk.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.operations.DS3Operation;

import java.util.ArrayList;
import java.util.HashMap;

public class MapDS3OperationsToChunk
{
	public static HashMap<String, ArrayList<DS3Operation>> createMap(ArrayList<DS3Operation> ops_list)
	{
		HashMap<String, ArrayList<DS3Operation>> ops_map = new HashMap<String, ArrayList<DS3Operation>>();
		ArrayList<DS3Operation> map_list;
		
		for(int i=0; i<ops_list.size(); i++)
		{
			for(int j=0; j < ops_list.get(i).chunkCount(); j++)
			{
				if(ops_map.get(ops_list.get(i).chunks[j]) == null)
				{
					map_list = new ArrayList<DS3Operation>();
					map_list.add(ops_list.get(i));
					
				}
				else
				{
					map_list = ops_map.get(ops_list.get(i).chunks[j]);
					map_list.add(ops_list.get(i));

				}
				
				ops_map.put(ops_list.get(i).chunks[j], map_list);
			}

		}

		return ops_map;
	}
}
