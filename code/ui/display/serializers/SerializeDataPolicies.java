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

		for(int i=0; i< policy_list.size(); i++)
		{
			formatted_line = new OutputFormat();
			formatted_line.key = "data_policy>policy_name";
			formatted_line.value = policy_list.get(i).name;
			output.add(formatted_line);

			for(int j=0; j < policy_list.get(i).getStorageDomainCount(); j++)
			{
				formatted_line = new OutputFormat();
				formatted_line.key = "data_policy>storage_domain>domain_name";
				formatted_line.value = policy_list.get(i).domains_list.get(j).name;
				output.add(formatted_line);
				formatted_line = new OutputFormat();
				formatted_line.key = "data_policy>storage_domain>domain_rules";
				formatted_line.value = policy_list.get(i).domains_list.get(j).name;
				output.add(formatted_line);	
			}

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
			}
		}

		return output;
	}
}
