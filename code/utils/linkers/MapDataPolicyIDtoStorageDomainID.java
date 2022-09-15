//===================================================================
// MapDataPolicyIDtoStorageDomainID.java
// 	Description:
// 		Creates a HashMap of data policy id to storage domain
// 		ids using the GuiDataPersistenceRule infomration.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.rest.GuiDataPersistenceRules;

import java.util.ArrayList;
import java.util.HashMap;

public class MapDataPolicyIDtoStorageDomainID
{
	public static HashMap<String, ArrayList<String>> createMap(GuiDataPersistenceRules rules)
	{
		HashMap<String, ArrayList<String>> id_map = new HashMap<String, ArrayList<String>>();
		ArrayList<String> domain_list;

		for(int i=0; i< rules.getRuleCount(); i++)
		{
			domain_list = getList(id_map, rules.getDataPolicyID(i));
			
			domain_list.add(rules.getStorageDomainID(i));

			id_map.put(rules.getDataPolicyID(i), domain_list);
		}

		return id_map;
	}

	private static ArrayList<String> getList(HashMap<String, ArrayList<String>> id_map, String id)
	{
		if(id_map.get(id) == null)
		{
			return new ArrayList<String>();
		}
		else
		{
			return id_map.get(id);
		}
	}
}
