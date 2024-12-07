//===================================================================
// FetchConfig.java
// 	Description:
// 		Grabs configuration information from the BlackPearl.
//
// 		Info Provided:
// 			- Pools
// 			- Volumes
// 			- Shares (CIFS, NFS, and Vail)
// 			- Storage Domains
// 			- Data Policies
// 			- Buckets
//===================================================================

package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.config.Configuration;
import com.socialvagrancy.blackpearl.logs.structures.outputs.Bucket;
import com.socialvagrancy.blackpearl.logs.structures.outputs.DataPolicy;
import com.socialvagrancy.blackpearl.logs.structures.outputs.StorageDomain;
import com.socialvagrancy.blackpearl.logs.structures.rest.ActivationKeys;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiPoolPartitions;
import com.socialvagrancy.blackpearl.logs.structures.rest.Pools;
import com.socialvagrancy.blackpearl.logs.structures.rest.Shares;
import com.socialvagrancy.blackpearl.logs.structures.rest.Volumes;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetActivationKeys;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetPoolData;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetPoolPartitionData;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetShareData;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetVolumeData;
import com.socialvagrancy.blackpearl.logs.utils.linkers.GenerateConfiguration;
import java.util.ArrayList;

public class FetchConfig
{
	public static ArrayList<Configuration> fromRest(String dir_path)
	{
		ArrayList<Configuration> configuration = new ArrayList<Configuration>(); // ArrayList to standardize output

		// System Info
		ActivationKeys keys = GetActivationKeys.fromRest(dir_path);

		// Pool Info
		Pools pools = GetPoolData.fromRest(dir_path);
		Shares shares = GetShareData.fromRest(dir_path);
		Volumes volumes = GetVolumeData.fromRest(dir_path);

		if(pools==null)
		{
			System.err.println("ERR: No pools imported.");
		}

		// Advanced Bucket Management Info
		GuiPoolPartitions disk_partitions = GetPoolPartitionData.fromRest(dir_path);
		ArrayList<Bucket> bucket_list = ListBuckets.fromRest(dir_path);
		ArrayList<DataPolicy> policy_list = ListDataPolicies.fromRest(dir_path);
		ArrayList<StorageDomain> domain_list = ListStorageDomains.fromRest(dir_path);

		Configuration config = GenerateConfiguration.buildConfig(keys, bucket_list, policy_list, domain_list, pools, volumes, shares, disk_partitions);

		configuration.add(config);

		return configuration;
	}
}
