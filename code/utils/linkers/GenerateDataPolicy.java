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
import com.socialvagrancy.blackpearl.logs.structures.outputs.ReplicationRule;
import com.socialvagrancy.blackpearl.logs.structures.outputs.StorageDomain;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiDataPolicy;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiDS3RepRules;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiDS3RepTargets;

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
	
	public static ArrayList<DataPolicy> withReplication(GuiDataPolicy policies, HashMap<String, ArrayList<String>> dp_to_sd_id_map, ArrayList<StorageDomain> domains_list, GuiDS3RepRules ds3_rep_rules, GuiDS3RepTargets ds3_targets)
	{
		// The replication rule free version was coded first. To avoid
		// rejiggering it, this function was put just above it.
		ArrayList<DataPolicy> policy_list = fromRest(policies, dp_to_sd_id_map, domains_list);
		
		HashMap<String, Integer> policy_map = MapDataPolicyToID.createIndexMap(policy_list);

		ArrayList<ReplicationRule> ds3_reps = GenerateReplicationRules.ds3Rules(ds3_rep_rules, ds3_targets);

		// Attach DS3 Replication Rules
		int index;

		for(int i=0; i < ds3_reps.size(); i++)
		{
			if(policy_map.get(ds3_reps.get(i).data_policy_id) != null)
			{
				index = policy_map.get(ds3_reps.get(i).data_policy_id);

				policy_list.get(index).addReplicationRule(ds3_reps.get(i));
			}
		}

		return policy_list;
	}
}
