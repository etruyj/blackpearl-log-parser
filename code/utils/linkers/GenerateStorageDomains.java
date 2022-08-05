//===================================================================
// GenerageStorageDomainMembers.java
// 	Description:
// 		Takes the rest/GuiStorageDomains and 
// 		rest/GuiStorageDomainMembers classes and joins them
// 		in outputs/StorageDomains
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.outputs.StorageDomain;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiStorageDomains;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiStorageDomainMembers;
import com.socialvagrancy.blackpearl.logs.structures.rest.data.StorageDomainMember;

import java.util.ArrayList;
import java.util.HashMap;

public class GenerateStorageDomains
{
	public static ArrayList<StorageDomain> createList(GuiStorageDomains domains, GuiStorageDomainMembers members)
	{
		ArrayList<StorageDomain> domain_list = new ArrayList<StorageDomain>();
		StorageDomain domain;

		HashMap<String, Integer> sd_id_index_map = new HashMap<String, Integer>();
		int d; // Storage Domain index counter.

		// Create the basic Storage Domain list
		for(int i=0; i<domains.getDomainCount(); i++)
		{
			domain = new StorageDomain();

			domain.id = domains.data[i].id;
			domain.name = domains.data[i].name;
			domain.write_optimization = domains.data[i].write_optimization;
			
			domain_list.add(domain);
		}

		// Create a map of the list
		// HashMap<StorageDomainID, ArrayListIndex>

		for(int i=0; i<domain_list.size(); i++)
		{
			sd_id_index_map.put(domain_list.get(i).id, i);
		}

		// Scan through the GuiStorageDomainMembers
		// add them to the to the domain list
		
		for(int i=0; i<members.getMemberCount(); i++)
		{
			d = sd_id_index_map.get(members.getStorageDomainID(i));
			
			domain_list.get(d).addMember(members.getMember(i));

		}

		return domain_list;
	}	
}
