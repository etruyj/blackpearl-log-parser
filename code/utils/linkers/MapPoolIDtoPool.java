//===================================================================
// MapPoolIDtoPool.java
// 	Description:
// 		Creates a HashMap of PoolID to PoolData.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.rest.Pools;

import java.util.ArrayList;
import java.util.HashMap;

public class MapPoolIDtoPool
{
	public static HashMap<String, Pools.PoolData> createMap(Pools pool_info)
	{
		HashMap<String, Pools.PoolData> pool_map = new HashMap<String, Pools.PoolData>();

		for(int i=0; i<pool_info.data.length; i++)
		{
			pool_map.put(pool_info.data[i].id, pool_info.data[i]);
		}

		return pool_map;
	}
}
