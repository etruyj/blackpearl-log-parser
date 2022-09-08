//===================================================================
// ListStorageDomains.java
// 	Description:
// 		Queries the logs to build out a list of storage domains
//===================================================================

package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.outputs.StorageDomain;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiStorageDomains;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiStorageDomainMembers;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiTapeLibraryPartitions;
import com.socialvagrancy.blackpearl.logs.structures.rest.Pools;
import com.socialvagrancy.blackpearl.logs.utils.linkers.GenerateStorageDomains;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetPoolData;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetStorageDomains;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetStorageDomainMembers;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetTapePartitions;

import java.util.ArrayList;

public class ListStorageDomains
{
	public static ArrayList<StorageDomain> fromRest(String dir_path)
	{
		GuiStorageDomains domains = GetStorageDomains.fromJson(dir_path + "/rest/gui_ds3_storage_domains.json");
		GuiStorageDomainMembers members = GetStorageDomainMembers.fromJson(dir_path + "/rest/gui_ds3_storage_domain_members.json");
		Pools disk_pools = GetPoolData.fromRest(dir_path);
		GuiTapeLibraryPartitions tape_partitions = GetTapePartitions.fromRest(dir_path);
		ArrayList<StorageDomain> domain_list = GenerateStorageDomains.createList(domains, members, disk_pools, tape_partitions);

		return domain_list;
	}

	public static void testPrint(ArrayList<StorageDomain> domain_list)
	{
		for(int i=0; i<domain_list.size(); i++)
		{
			System.out.println(domain_list.get(i).name + " write_optimization: " + domain_list.get(i).write_optimization);

			for(int j=0; j<domain_list.get(i).getMemberCount(); j++)
			{
				if(domain_list.get(i).getMemberType(j).equals("pool"))
				{
					System.out.println("\t- " + domain_list.get(i).getMemberType(j) + ":" + domain_list.get(i).getMemberName(j));

				}
				else if(domain_list.get(i).getMemberType(j).equals("tape"))
				{
					System.out.println("\t- " + domain_list.get(i).getMemberType(j) + ":" + domain_list.get(i).getMemberName(j) + " (" + domain_list.get(i).getTapeType(j) + ")");
				}
			}
		}
	}
}
