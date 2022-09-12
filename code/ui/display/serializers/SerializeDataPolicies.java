//===================================================================
// SerializeDataPolicies.java
// 	Description:
// 		Converts an ArrayList<DataPolicy> to an
// 		ArrayList<OutputFormat> for display.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui.display.serializers;

import com.socialvagrancy.blackpearl.logs.structures.outputs.DataPolicy;
import com.socialvagrancy.utils.ui.structures.OutputFormat;
import java.util.ArrayList;

public class SerializeDataPolicies
{
	public static ArrayList<OutputFormat> toOutputFormat(ArrayList<DataPolicy> policy_list)
	{
		ArrayList<OutputFormat> output = new ArrayList<OutputFormat>();
		OutputFormat formatted_line;
		
		// Additional formatting is needed for tables.
		// If a data policy has fewer storage domains 
		// or replication rules than the others, blanks
		// must be inserted for the table.
		int persistence_rules = maxPersistenceRules(policy_list);
		int replication_targets = maxReplicationTargets(policy_list);
		int counter;

		for(int i=0; i< policy_list.size(); i++)
		{
			formatted_line = new OutputFormat();
			formatted_line.key = "data_policy>policy_name";
			formatted_line.value = policy_list.get(i).name;
			output.add(formatted_line);

			// Reset counter to 0 for storage domain count
			counter = 0;

			for(int j=0; j < policy_list.get(i).getStorageDomainCount(); j++)
			{
				formatted_line = new OutputFormat();
				formatted_line.key = "data_policy>storage_domain>domain_name";
				formatted_line.value = policy_list.get(i).storageDomainName(j);
				output.add(formatted_line);
				formatted_line = new OutputFormat();
				formatted_line.key = "data_policy>storage_domain>members";
				formatted_line.value = String.valueOf(policy_list.get(i).storageDomainMemberCount(j));
				output.add(formatted_line);

				counter++;	
			}

			// Add blank storage domains to the data policy if it 
			// it has fewer storage domains than the max.
			// Set indents to -1 to skip on other output formats.
		
			for(int j = counter; j < persistence_rules; j++)
			{
				formatted_line = new OutputFormat();
				formatted_line.key = "data_policy>storage_domain>domain_name";
				formatted_line.value = "";
				formatted_line.indents = -1;
				output.add(formatted_line);
				formatted_line = new OutputFormat();
				formatted_line.key = "data_policy>storage_domain>members";
				formatted_line.value = "";
				formatted_line.indents = -1;
				output.add(formatted_line);

			}

			// Reset counter for replication rules
			counter = 0;

			for(int k=0; k < policy_list.get(i).replicationRuleCount(); k++)
			{
				formatted_line = new OutputFormat();
				formatted_line.key = "data_policy>replication_rule>replication_target";
				formatted_line.value = policy_list.get(i).replicationTargetName(k);
				output.add(formatted_line);
				formatted_line = new OutputFormat();
				formatted_line.key = "data_policy>replication_rule>type";
				formatted_line.value = policy_list.get(i).replicationTargetType(k);
				output.add(formatted_line);
				formatted_line = new OutputFormat();
				formatted_line.key = "data_policy>replication_rule>ip_address";
				formatted_line.value = policy_list.get(i).replicationTargetIP(k);
				output.add(formatted_line);

				counter++;
			}

			// Add blank replication targets to allow the proper display
			// in table format.
			// Set indent to -1 to avoid using these fields in other display
			// types.

			for(int k = counter; k < replication_targets; k++)
			{
				formatted_line = new OutputFormat();
				formatted_line.key = "data_policy>replication_rule>replication_target";
				formatted_line.value = "";
				formatted_line.indents = -1;
				output.add(formatted_line);
				formatted_line = new OutputFormat();
				formatted_line.key = "data_policy>replication_rule>type";
				formatted_line.value = "";
				formatted_line.indents = -1;
				output.add(formatted_line);
				formatted_line = new OutputFormat();
				formatted_line.key = "data_policy>replication_rule>ip_address";
				formatted_line.value = "";
				formatted_line.indents = -1;
				output.add(formatted_line);
			}
		}

		return output;
	}

	//=======================================
	// Private Functions
	//=======================================
	
	private static int maxPersistenceRules(ArrayList<DataPolicy> policy_list)
	{
		int persistence_rules = 0;

		for(int i=0; i < policy_list.size(); i++)
		{
			if(policy_list.get(i).getStorageDomainCount() > persistence_rules)
			{
				persistence_rules = policy_list.get(i).getStorageDomainCount();
			}
		}

		return persistence_rules;
	}
	
	private static int maxReplicationTargets(ArrayList<DataPolicy> policy_list)
	{
		int replication_targets = 0;

		for(int i=0; i < policy_list.size(); i++)
		{
			if(policy_list.get(i).replicationRuleCount() > replication_targets)
			{
				replication_targets = policy_list.get(i).replicationRuleCount();
			}
		}

		return replication_targets;
	}
}
