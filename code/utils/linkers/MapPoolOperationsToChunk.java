//===================================================================
// MapPoolOperationstoChunk.java
// 	Description:
// 		Creates a HashMap<String, ArrayList<PoolOperation>> to
// 		allow searching the pool operations by the job's chunk.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.operations.PoolOperation;

import java.util.ArrayList;
import java.util.HashMap;

public class MapPoolOperationsToChunk
{
	public static HashMap<String, ArrayList<PoolOperation>> createMap(ArrayList<PoolOperation> ops_list)
	{
		HashMap<String, ArrayList<PoolOperation>> ops_map = new HashMap<String, ArrayList<PoolOperation>>();
		ArrayList<PoolOperation> map_list;
		
		for(int i=0; i<ops_list.size(); i++)
		{
			for(int j=0; j < ops_list.get(i).chunkCount(); j++)
			{
				if(ops_map.get(ops_list.get(i).chunks[j]) == null)
				{
					map_list = new ArrayList<PoolOperation>();
					map_list.add(ops_list.get(i));
					
				}
				else
				{
					map_list = ops_map.get(ops_list.get(i).chunks[j]);
					map_list.add(ops_list.get(i));

				}
				
				System.err.println(ops_list.get(i).chunks[j] + " " + map_list.size());	
				ops_map.put(ops_list.get(i).chunks[j], map_list);
			}

			System.err.println(ops_list.size() + " " + ops_list.get(i).chunkCount());
		}

		return ops_map;
	}
}
