//===================================================================
// GenerateDataPolicy.java
// 	Description:
// 		Takes the Data Policy and Data Persistence Rule information
// 		along with an ArrayList<StorageDomain>, which is the
// 		output of the commands/ListStorageDomains.java in order
// 		to build a full picture of the data policy from the
// 		distinct parts.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.outputs.DataPolicy;
import com.socialvagrancy.blackpearl.logs.structures.outputs.StorageDomain;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiDataPolicy;

import java.util.ArrayList;
import java.util.HashMap;

public class GenerateDataPolicy
{
	public static ArrayList<DataPolicy> fromRest(GuiDataPolicy policies, HashMap<String, ArrayList<String>> dp_to_sd_id_map, ArrayList<StorageDomain> domains_list)
	{
		ArrayList<DataPolicy> policy_list = new ArrayList<DataPolicy>();
		HashMap<String, StorageDomain> domain_map = MapStorageDomainToID.createMap(domains_list);
		DataPolicy data_policy;
		String storage_domain_id;

		for(int i=0; i < policies.policyCount(); i++)
		{
			data_policy = new DataPolicy();

			data_policy.name = policies.getName(i);
			data_policy.id = policies.getID(i);

			// Quick check for null values
			// It is possible for data policies not to have persistence rules assigned.

			if(dp_to_sd_id_map.get(data_policy.id) != null)
			{
				for(int j=0; j < dp_to_sd_id_map.get(data_policy.id).size(); j++)
				{
					storage_domain_id = dp_to_sd_id_map.get(data_policy.id).get(j);

					data_policy.addPersistenceRule(domain_map.get(storage_domain_id));	
				}
			}

			policy_list.add(data_policy);
		}

		return policy_list;
	}
}
