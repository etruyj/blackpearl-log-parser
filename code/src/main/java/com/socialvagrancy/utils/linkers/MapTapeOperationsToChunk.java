//===================================================================
// MapTapeOperationToChunk.java
// 	Description:
// 		Creates a HashMap<String, ArrayList<TapeOperation> to
// 		allow the attachment of the TapeOperations to jobs.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.operations.TapeOperation;

import java.util.ArrayList;
import java.util.HashMap;

public class MapTapeOperationsToChunk
{
	public static HashMap<String, ArrayList<TapeOperation>> createMap(ArrayList<TapeOperation> ops_list)
	{
		HashMap<String, ArrayList<TapeOperation>> ops_map = new HashMap<String, ArrayList<TapeOperation>>();
		ArrayList<TapeOperation> map_list;

		for(int i=0; i<ops_list.size(); i++)
		{
			for(int j=0; j<ops_list.get(i).chunk_id.length; j++)
			{
				if(ops_map.get(ops_list.get(i).chunk_id[j]) == null)
				{
					// Create a new mapping
					map_list = new ArrayList<TapeOperation>();

					map_list.add(ops_list.get(i));

					ops_map.put(ops_list.get(i).chunk_id[j], map_list);
				
				}
				else
				{
					map_list = ops_map.get(ops_list.get(i).chunk_id[j]);
					map_list.add(ops_list.get(i));
					ops_map.put(ops_list.get(i).chunk_id[j], map_list);
				}
			}

		}

		return ops_map;
	}
}
