//===================================================================
// ListDataPolicies.java
// 	Description:
// 		Parses the rest/ directory for information on the 
// 		data policy, data persistence rules, and storage domain
// 		members.
//===================================================================

package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.outputs.DataPolicy;
import com.socialvagrancy.blackpearl.logs.structures.outputs.StorageDomain;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiDataPolicy;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiDataPersistenceRules;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetDataPolicies;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetDataPolicyToStorageDomainIDMap;
import com.socialvagrancy.blackpearl.logs.utils.linkers.GenerateDataPolicy;

import java.util.ArrayList;
import java.util.HashMap;

public class ListDataPolicies
{
	public static ArrayList<DataPolicy> fromRest(String dir_path)
	{
		GuiDataPolicy policies = GetDataPolicies.fromJson(dir_path + "/rest/gui_ds3_data_policies.json");
		HashMap<String, ArrayList<String>> dp_to_sd_id_map  = GetDataPolicyToStorageDomainIDMap.fromJson(dir_path + "/rest/gui_ds3_data_persistence_rules.json");
		ArrayList<StorageDomain> domain_list = ListStorageDomains.fromRest(dir_path);
		ArrayList<DataPolicy> policy_list = GenerateDataPolicy.fromRest(policies, dp_to_sd_id_map, domain_list);
		
		return policy_list;
	}

	public static void testPrint(ArrayList<DataPolicy> policy_list)
	{
		for(int i=0; i<policy_list.size(); i++)
		{
			System.out.println(policy_list.get(i).name + ":");

			for(int j=0; j < policy_list.get(i).getStorageDomainCount(); j++)
			{
				System.out.println("\t- " + policy_list.get(i).domains_list.get(j).name 
						+ " rules: " + policy_list.get(i).domains_list.get(j).getMemberCount());
			}
		}
	}
}
