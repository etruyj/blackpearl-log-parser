//===================================================================
// GenerateConfiguration.java
// 	Description:
// 		Populates the Configuration variable with the different
// 		component classes
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.config.CIFSShare;
import com.socialvagrancy.blackpearl.logs.structures.config.Configuration;
import com.socialvagrancy.blackpearl.logs.structures.config.DataPolicyConfig;
import com.socialvagrancy.blackpearl.logs.structures.config.NFSShare;
import com.socialvagrancy.blackpearl.logs.structures.config.PoolConfig;
import com.socialvagrancy.blackpearl.logs.structures.config.Share;
import com.socialvagrancy.blackpearl.logs.structures.config.VolumeConfig;
import com.socialvagrancy.blackpearl.logs.structures.outputs.Bucket;
import com.socialvagrancy.blackpearl.logs.structures.outputs.DataPolicy;
import com.socialvagrancy.blackpearl.logs.structures.outputs.StorageDomain;
import com.socialvagrancy.blackpearl.logs.structures.rest.Pools;
import com.socialvagrancy.blackpearl.logs.structures.rest.Shares;
import com.socialvagrancy.blackpearl.logs.structures.rest.Volumes;
import com.socialvagrancy.blackpearl.logs.structures.rest.data.Pool;

import java.util.ArrayList;
import java.util.HashMap;

public class GenerateConfiguration
{
	public static Configuration buildConfig(ArrayList<Bucket> bucket_list, ArrayList<DataPolicy> policy_list, ArrayList<StorageDomain> domain_list, Pools pools, Volumes volumes, Shares shares)
	{
		Configuration config = new Configuration();
		
		HashMap<String, Pool> pool_id_map = MapPoolIDtoPool.createMapForNAS(pools);
		HashMap<String, Volumes.Volume> vol_id_map = MapVolumeToID.createMap(volumes);
		config = addBuckets(bucket_list, config);
		//config.addDataPolicies(policy_list);
		config = addPools(pools, config);
		config = addShares(shares, vol_id_map, config);
		config.addStorageDomains(domain_list);
		config = addVolumes(volumes, pool_id_map, config);

		return config;
	}

	//=======================================
	// Functions
	//=======================================
	
	private static Configuration addBuckets(ArrayList<Bucket> bucket_list, Configuration config)
	{
		for(int i=0; i < bucket_list.size(); i++)
		{
			config.addBucket(bucket_list.get(i).name, bucket_list.get(i).dataPolicy(), bucket_list.get(i).owner);
		}

		return config;
	}

	private static Configuration addDataPolicies(ArrayList<DataPolicy> policy_list, Configuration config)
	{
/*		DataPolicyConfig policy;

		for(int i=0; i < policy_list.size(); i++)
		{
//			policy = new DataPolicyConfig(policy_list.get(i).name);
			
			// Load the Storage Domains
			for(int j=0; j < policy_list.get(i).storageDomainCount(); j++)
			{
				//policy.addPersistenceRule(policy_list.get(i).storageDomainName(j), 
			}

			// Load Replication Targets
			for(int k=0; k < policy_list.get(i).replicationRuleCount(); k++)
			{
				//policy.addReplicationRule(policy_list.get(i).replicationTargetName(k), 
				//		policy_list.get(i).replicationTarget(
			}

			//config.addDataPolicy(policy);
		}
*/
		return config;
	}

	private static Configuration addPools(Pools pools, Configuration config)
	{
		PoolConfig pool;

		for(int i=0; i < pools.poolCount(); i++)
		{
			pool = new PoolConfig(pools.name(i), pools.highWater(i),
					pools.powerSavingMode(i), pools.driveCount(i), pools.protection(i));

			config.add(pool);
		}

		return config;
	}

	private static Configuration addShares(Shares shares, HashMap<String, Volumes.Volume> vol_id_map, Configuration config)
	{
		CIFSShare cifs;
		NFSShare nfs;
		Share vail;

		String vol_name;

		for(int i=0; i<shares.count(); i++)
		{
			if(vol_id_map.get(shares.volumeID(i)) == null)
			{
				System.err.println("WARN: Unabled to associate volume id " + shares.volumeID(i) + " with a volume.");
				vol_name = "[UNKNOWN]";
			}
			else
			{
				vol_name = vol_id_map.get(shares.volumeID(i)).name;
			}


			if(shares.type(i).equals("Cifs"))
			{
				cifs = new CIFSShare();

				cifs.name = shares.name(i);
				cifs.mount_point = shares.mountPoint(i);
				cifs.volume_name = vol_name;
				cifs.read_only = shares.readOnly(i);

				config.addCIFS(cifs);
			}
			else if(shares.type(i).equals("Nfs"))
			{
				nfs = new NFSShare();

				nfs.name = shares.name(i);
				nfs.mount_point = shares.mountPoint(i);
				nfs.volume_name = vol_name;

				nfs.comment = shares.comment(i);
				nfs.access_control = shares.accessControl(i);

				config.addNFS(nfs);
			}
			else if(shares.type(i).equals("Vail"))
			{
				vail = new Share();

				vail.name = shares.name(i);
				vail.volume_name = vol_name;
				vail.mount_point = shares.mountPoint(i);

				config.addVAIL(vail);
			}
			else
			{
				System.err.println("WARN: Share type " + shares.type(i) + " is unknown.");
			}
		}

		return config;
	}

	private static Configuration addVolumes(Volumes vols, HashMap<String, Pool> pool_id_map, Configuration config)
	{
		VolumeConfig volume;
		String pool_name;

		for(int i=0; i < vols.volumeCount(); i++)
		{
			if(pool_id_map.get(vols.poolID(i)) == null)
			{
				System.err.println("WARN: Unable to find pool with id " + vols.poolID(i));
				pool_name = "[UNKOWN]";
			}
			else
			{
				pool_name = pool_id_map.get(vols.poolID(i)).name;
			}
			
			volume = new VolumeConfig(vols.name(i), pool_name,
					vols.caseSensitive(i), vols.compression(i), vols.accessTime(i));

			config.add(volume);
		}

		return config;
	}
}
