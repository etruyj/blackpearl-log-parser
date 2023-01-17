//===================================================================
// MapPoolPartitiontoID.java
// 	Description:
// 		Creates a HashMap of PoolPartitionID to PoolPartitionData.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.rest.GuiPoolPartitions;

import java.util.HashMap;

public class MapPoolPartitionToID
{
	public static HashMap<String, GuiPoolPartitions.PoolPartition> createMap(GuiPoolPartitions pars)
	{
		HashMap<String, GuiPoolPartitions.PoolPartition> pars_map = new HashMap<String, GuiPoolPartitions.PoolPartition>();
		
		if(pars != null)
		{
			for(int i=0; i< pars.count(); i++)
			{
				pars_map.put(pars.id(i), pars.partitionData(i));
			}
		}

		return pars_map;
	}
}
