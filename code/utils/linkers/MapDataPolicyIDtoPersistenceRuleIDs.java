//===================================================================
// MapDataPolicyIDtoPersistenceRuleIDs.java
// 	Description:
// 		Creates a HashMap of data policy id to an 
// 		ArrayList<String> of data persistence rule ids.
// 		This allows matching of data persistence rules to the
// 		storage domain.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.rest.GuiDataPersistenceRules;

import java.util.ArrayList;
import java.util.HashMap;

public class MapDataPolicyIDtoPersistenceRuleIDs
{
	public static HashMap<String, ArrayList<String>> createMap(GuiDataPersistenceRules rules)
	{
		HashMap<String, ArrayList<String>> id_map = new HashMap<String, ArrayList<String>>();
		ArrayList<String> id_list;

		if(rules != null)
		{
			for(int i=0; i< rules.getRuleCount(); i++)
			{
				id_list = getList(id_map, rules.getDataPolicyID(i));
				
				id_list.add(rules.id(i));

				id_map.put(rules.getDataPolicyID(i), id_list);
			}
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
