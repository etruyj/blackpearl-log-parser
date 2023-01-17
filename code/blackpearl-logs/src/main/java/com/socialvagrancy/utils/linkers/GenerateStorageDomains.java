//===================================================================
// GenerageStorageDomainMembers.java
// 	Description:
// 		Takes the rest/GuiStorageDomains and 
// 		rest/GuiStorageDomainMembers classes and joins them
// 		in outputs/StorageDomains
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.outputs.StorageDomain;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiPoolPartitions;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiStorageDomains;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiStorageDomainMembers;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiTapeLibraryPartitions;
import com.socialvagrancy.blackpearl.logs.structures.rest.data.Pool;
import com.socialvagrancy.blackpearl.logs.structures.rest.data.StorageDomainMember;
import com.socialvagrancy.blackpearl.logs.utils.linkers.MapPoolIDtoPool;
import com.socialvagrancy.blackpearl.logs.utils.linkers.MapTapePartitionIDtoPartition;

import java.util.ArrayList;
import java.util.HashMap;

public class GenerateStorageDomains
{
	public static ArrayList<StorageDomain> createList(GuiStorageDomains domains, GuiStorageDomainMembers members, GuiPoolPartitions disk_pools, GuiTapeLibraryPartitions tape_partitions)
	{
		ArrayList<StorageDomain> domain_list = new ArrayList<StorageDomain>();
		HashMap<String, GuiPoolPartitions.PoolPartition> pool_id_map = MapPoolPartitionToID.createMap(disk_pools);
		HashMap<String, GuiTapeLibraryPartitions.TapePartition> partition_id_map = MapTapePartitionIDtoPartition.createMap(tape_partitions);

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
			domain.days_to_verify = domains.data[i].maximum_auto_verification_frequency_in_days;
			domain.media_ejection_upon_cron = domains.data[i].auto_eject_upon_cron;
			domain.ltfs_file_naming = domains.data[i].ltfs_file_naming;
			domain.media_ejection_allowed = domains.data[i].media_ejection_allowed;
			domain.auto_eject_upon_completion = domains.data[i].auto_eject_upon_completion;
			domain.auto_eject_upon_cancellation = domains.data[i].auto_eject_upon_cancellation;
			domain.auto_eject_upon_media_full = domains.data[i].auto_eject_upon_media_full;
			domain.verify_prior_to_auto_eject = domains.data[i].verify_prior_to_auto_eject;

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
		String id;
		String name;

		for(int i=0; i<members.getMemberCount(); i++)
		{
			d = sd_id_index_map.get(members.getStorageDomainID(i));
			
			// Determine the storage domain member name
			// See if we're working with a pool copy or 
			// tape copy and then use the corresponding map to determine
			// the name.
			if(members.getMemberType(i).equals("pool"))
			{
				id = members.getMemberID(i);
				
				try 
				{ 
					name = pool_id_map.get(id).name(); 
				}
				catch(Exception e)
				{
					System.err.println("WARN: Cannot find pool with id " + id);
					name = "[unknown]";
				}
				members.setMemberName(i, name);
			}
			else
			{
				id = members.getMemberID(i);
				name = partition_id_map.get(id).name;
				members.setMemberName(i, name);
			}

			domain_list.get(d).addMember(members.getMember(i));

		}

		return domain_list;
	}	
}
