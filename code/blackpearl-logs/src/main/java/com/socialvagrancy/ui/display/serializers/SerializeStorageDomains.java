//===================================================================
// SerializeStorageDomains.java
// 	Description:
// 		Converts an ArrayList<StorageDomain> to 
// 		ArrayList<OutputFormat> for display.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui.display.serializers;

import com.socialvagrancy.blackpearl.logs.structures.outputs.StorageDomain;
import com.socialvagrancy.utils.ui.structures.OutputFormat;
import java.util.ArrayList;

public class SerializeStorageDomains
{
	public static ArrayList<OutputFormat> toOutputFormat(ArrayList<StorageDomain> domain_list)
	{
		ArrayList<OutputFormat> output = new ArrayList<OutputFormat>();
		OutputFormat formatted_line;

		// These variables are for table formatting. The maximum number
		// of domain members needs to be accounted for in order to accurately
		// display the table information.
		// indent is set to -1 to skip for non-table format.
		int members = maxMemberCount(domain_list);
		int counter;

		for(int i=0; i< domain_list.size(); i++)
		{
			formatted_line = new OutputFormat();
			formatted_line.key = "storage_domain>domain_name";
			formatted_line.value = domain_list.get(i).name();
			output.add(formatted_line);
			formatted_line = new OutputFormat();
			formatted_line.key = "storage_domain>write_optimization";
			formatted_line.value = domain_list.get(i).writeOptimization();
			output.add(formatted_line);

			counter = 0;

			for(int j=0; j < domain_list.get(i).memberCount(); j++)
			{
				formatted_line = new OutputFormat();
				formatted_line.key = "storage_domain>storage_domain_member>type";
				formatted_line.value = domain_list.get(i).memberType(j);
				output.add(formatted_line);
				formatted_line = new OutputFormat();
				formatted_line.key = "storage_domain>storage_domain_member>partition";
				formatted_line.value = domain_list.get(i).memberName(j);
				output.add(formatted_line);
				formatted_line = new OutputFormat();
				formatted_line.key = "storage_domain>storage_domain_member>tape_type";
				formatted_line.value = domain_list.get(i).memberTapeType(j);
				output.add(formatted_line);
				formatted_line = new OutputFormat();
				formatted_line.key = "storage_domain>storage_domain_member>state";
				formatted_line.value = domain_list.get(i).memberState(j);
				output.add(formatted_line);
				formatted_line = new OutputFormat();
				formatted_line.key = "storage_domain>storage_domain_member>write_optimization";
				formatted_line.value = domain_list.get(i).memberWritePreference(j);
				output.add(formatted_line);

				counter++;
			}

			// Add blank values if necessary to square out the table.
			for(int j=counter; j<members; j++)
			{
				formatted_line = new OutputFormat();
				formatted_line.key = "storage_domain>storage_domain_member>type";
				formatted_line.value = "";
				formatted_line.indents = -1;
				output.add(formatted_line);
				formatted_line = new OutputFormat();
				formatted_line.key = "storage_domain>storage_domain_member>partition";
				formatted_line.value = "";
				output.add(formatted_line);
				formatted_line.indents = -1;
				formatted_line = new OutputFormat();
				formatted_line.key = "storage_domain>storage_domain_member>tape_type";
				formatted_line.value = "";
				formatted_line.indents = -1;
				output.add(formatted_line);
				formatted_line = new OutputFormat();
				formatted_line.key = "storage_domain>storage_domain_member>state";
				formatted_line.value = "";
				formatted_line.indents = -1;
				output.add(formatted_line);
				formatted_line = new OutputFormat();
				formatted_line.key = "storage_domain>storage_domain_member>write_optimization";
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
	
	private static int maxMemberCount(ArrayList<StorageDomain> domain_list)
	{
		int memberCount = 0;

		for(int i=0; i < domain_list.size(); i++)
		{
			if(domain_list.get(i).memberCount() > memberCount)
			{
				memberCount = domain_list.get(i).memberCount();
			}
		}

		return memberCount;
	}
}
