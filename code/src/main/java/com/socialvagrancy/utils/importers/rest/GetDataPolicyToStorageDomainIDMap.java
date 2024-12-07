//===================================================================
// GetDataPolicyToStorageDomainIDMap.java
// 	Description:
// 		Imports the data persistence rules and turns it into 
// 		a HashMap<String, ArrayList<String>> of data policy id 
// 		to an ArrayList of storage domain ids. This will allow
// 		the storage domains 
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers.rest;

import com.socialvagrancy.blackpearl.logs.structures.rest.GuiDataPersistenceRules;

import java.util.ArrayList;
import java.util.HashMap;

public class GetDataPolicyToStorageDomainIDMap
{
	public static HashMap<String, ArrayList<String>> fromJson(String file_path)
	{
		GuiDataPersistenceRules rules = GetDataPersistenceRules.fromJson(file_path);
		HashMap<String, ArrayList<String>> dp_to_sd_id_map = new HashMap<String, ArrayList<String>>();
		ArrayList<String> domain_id_list;

		for(int i=0; i<rules.getRuleCount(); i++)
		{
			domain_id_list = getDomainIDs(rules.getDataPolicyID(i), dp_to_sd_id_map);
			
			domain_id_list.add(rules.getStorageDomainID(i));

			dp_to_sd_id_map.put(rules.getDataPolicyID(i), domain_id_list);			
		}

		return dp_to_sd_id_map;
	}

	//=======================================
	// Inner Functions
	//=======================================

	private static ArrayList<String> getDomainIDs(String data_policy_id, HashMap<String, ArrayList<String>> dp_to_sd_id_map)
	{
		if(dp_to_sd_id_map == null)
		{
			return new ArrayList<String>();
		}
		else
		{
			if(dp_to_sd_id_map.get(data_policy_id) == null)
			{
				return new ArrayList<String>();
			}
			else
			{
				return dp_to_sd_id_map.get(data_policy_id);
			}
		}
	}
}
