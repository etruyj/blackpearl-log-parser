//===================================================================
// MapDataPolicyToID.java
// 	Description:
// 		Creates a 
// 		HashMap<String, DataPolicy> to map the data policy
// 		id to the data policy. This will allow linking the 
// 		data policy to the buckets.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.outputs.DataPolicy;

import java.util.ArrayList;
import java.util.HashMap;

public class MapDataPolicyToID
{
	public static HashMap<String, DataPolicy> createMap(ArrayList<DataPolicy> policy_list)
	{
		HashMap<String, DataPolicy> policy_map = new HashMap<String, DataPolicy>();
	
		for(int i=0; i<policy_list.size(); i++)
		{
			policy_map.put(policy_list.get(i).getID(), policy_list.get(i));
		}

		return policy_map;
	}

	public static HashMap<String, Integer> createIndexMap(ArrayList<DataPolicy> policy_list)
	{
		HashMap<String, Integer> policy_map = new HashMap<String, Integer>();

		for(int i=0; i<policy_list.size(); i++)
		{
			policy_map.put(policy_list.get(i).getID(), i);
		}

		return policy_map;
	}
}
