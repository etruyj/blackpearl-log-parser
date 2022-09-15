//===================================================================
// GenerateConfiguration.java
// 	Description:
// 		Populates the Configuration variable with the different
// 		component classes
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.config.BucketConfig;
import com.socialvagrancy.blackpearl.logs.structures.config.CIFSShare;
import com.socialvagrancy.blackpearl.logs.structures.config.Configuration;
import com.socialvagrancy.blackpearl.logs.structures.config.DataPolicyConfig;
import com.socialvagrancy.blackpearl.logs.structures.config.DiskPartitionConfig;
import com.socialvagrancy.blackpearl.logs.structures.config.NFSShare;
import com.socialvagrancy.blackpearl.logs.structures.config.PoolConfig;
import com.socialvagrancy.blackpearl.logs.structures.config.Share;
import com.socialvagrancy.blackpearl.logs.structures.config.StorageDomainConfig;
import com.socialvagrancy.blackpearl.logs.structures.config.StorageDomainMemberConfig;
import com.socialvagrancy.blackpearl.logs.structures.config.VolumeConfig;
import com.socialvagrancy.blackpearl.logs.structures.outputs.Bucket;
import com.socialvagrancy.blackpearl.logs.structures.outputs.DataPolicy;
import com.socialvagrancy.blackpearl.logs.structures.outputs.StorageDomain;
import com.socialvagrancy.blackpearl.logs.structures.rest.Pools;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiPoolPartitions;
import com.socialvagrancy.blackpearl.logs.structures.rest.Shares;
import com.socialvagrancy.blackpearl.logs.structures.rest.Volumes;
import com.socialvagrancy.blackpearl.logs.structures.rest.data.Pool;

import java.util.ArrayList;
import java.util.HashMap;

public class GenerateConfiguration
{
	public static Configuration buildConfig(ArrayList<Bucket> bucket_list, ArrayList<DataPolicy> policy_list, ArrayList<StorageDomain> domain_list, Pools pools, Volumes volumes, Shares shares, GuiPoolPartitions disk_partitions)
	{
		Configuration config = new Configuration();
		
		HashMap<String, Pool> pool_id_map = MapPoolIDtoPool.createMapForNAS(pools);
		HashMap<String, Volumes.Volume> vol_id_map = MapVolumeToID.createMap(volumes);
		config = addBuckets(bucket_list, config);
		config = addDataPolicies(policy_list, config);
		config = addPools(pools, config);
		config = addShares(shares, vol_id_map, config);
		config = addDiskPartitions(disk_partitions, pool_id_map, config);
		config = addStorageDomains(domain_list, config);
		config = addVolumes(volumes, pool_id_map, config);

		return config;
	}

	//=======================================
	// Functions
	//=======================================
	
	private static Configuration addBuckets(ArrayList<Bucket> bucket_list, Configuration config)
	{
		if(bucket_list == null)
		{
			System.err.println("INFO: No buckets found.");
		}
		else
		{
			BucketConfig bucket;
			for(int i=0; i < bucket_list.size(); i++)
			{
				bucket = new BucketConfig(bucket_list.get(i).name, bucket_list.get(i).dataPolicy(), bucket_list.get(i).owner);
		
				config.add(bucket);	
			}
		}

		return config;
	}

	private static Configuration addDataPolicies(ArrayList<DataPolicy> policy_list, Configuration config)
	{
		if(policy_list == null)
		{
			System.err.println("INFO: No data policies found.");
		}
		else
		{
			DataPolicyConfig policy;

			for(int i=0; i < policy_list.size(); i++)
			{
				policy = new DataPolicyConfig(policy_list.get(i).name);

				policy.setBlobbing(policy_list.get(i).blobbing_enabled);
				policy.setSpanning(policy_list.get(i).minimize_spanning);
				policy.setPriorityGet(policy_list.get(i).default_get_priority);
				policy.setPriorityPut(policy_list.get(i).default_put_priority);
				policy.setPriorityVerify(policy_list.get(i).default_verify_priority);
				policy.setPriorityRebuild(policy_list.get(i).rebuild_priority);
				policy.setChecksum(policy_list.get(i).checksum);
				policy.setEndToEndCRC(policy_list.get(i).end_to_end_crc);
				policy.setVersioning(policy_list.get(i).versioning, policy_list.get(i).versions_to_keep);
				policy.setReplicatedPuts(policy_list.get(i).replicated_puts);
			
				// Data Persistence Rules
				for(int j=0; j < policy_list.get(i).getStorageDomainCount(); j++)
				{
					policy.addPersistenceRule(policy_list.get(i).persistenceRuleName(j),
							policy_list.get(i).persistenceRuleType(j),
							policy_list.get(i).persistenceRuleIsolation(j),
							policy_list.get(i).persistenceRuleRetention(j));
				}

				// Replication Rules
				for(int k=0; k < policy_list.get(i).replicationRuleCount(); k++)
				{
					policy.addReplicationRule(policy_list.get(i).replicationTargetName(k),
							policy_list.get(i).replicationTargetType(k), 
							policy_list.get(i).replicationTargetIP(k));
				}
	
				config.add(policy);	
			}
		}

		return config;
	}

	private static Configuration addDiskPartitions(GuiPoolPartitions disk_partitions, HashMap<String, Pool> pool_id_map, Configuration config)
	{
		if(disk_partitions == null)
		{
			System.err.println("INFO: No disk partitions found.");
		}
		else
		{
			DiskPartitionConfig disk_par;
			String pool_name;

			for(int i=0; i < disk_partitions.count(); i++)
			{
				disk_par = new DiskPartitionConfig(disk_partitions.name(i), disk_partitions.type(i));

				for(int j=0; j < disk_partitions.poolCount(i); j++)
				{
					if(pool_id_map.get(disk_partitions.poolID(i,j)) == null)
					{
						System.err.println("WARN: Unable to locate pool with id " + disk_partitions.poolID(i, j));
						pool_name = "[UNKNOWN]";
					}
					else
					{
						pool_name = pool_id_map.get(disk_partitions.poolID(i, j)).name;
					}

					disk_par.addMember(pool_name);
				}

				config.add(disk_par);
			}
		}

		return config;
	}

	private static Configuration addPools(Pools pools, Configuration config)
	{
		if(pools == null)
		{
			System.err.println("INFO: No pools found.");
		}
		else
		{
			PoolConfig pool;

			for(int i=0; i < pools.poolCount(); i++)
			{
				pool = new PoolConfig(pools.name(i), pools.highWater(i),
						pools.powerSavingMode(i), pools.driveCount(i), pools.protection(i));

				config.add(pool);
			}
		}

		return config;
	}

	private static Configuration addShares(Shares shares, HashMap<String, Volumes.Volume> vol_id_map, Configuration config)
	{
		if(shares == null)
		{
			System.err.println("INFO: No shares found.");
		}
		else
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
		}

		return config;
	}

	private static Configuration addStorageDomains(ArrayList<StorageDomain> domain_list, Configuration config)
	{
		if(domain_list == null)
		{
			System.err.println("INFO: No storage domains found.");
		}
		else
		{
			StorageDomainConfig domain;
			StorageDomainMemberConfig member;

			for(int i=0; i < domain_list.size(); i++)
			{
				domain = new StorageDomainConfig(domain_list.get(i).name(),
						domain_list.get(i).daysToVerify(), domain_list.get(i).writeOptimization(),
						domain_list.get(i).ltfsNaming(), domain_list.get(i).ejectAllowed(),
						domain_list.get(i).ejectOnComplete(), domain_list.get(i).ejectOnCancel(),
						domain_list.get(i).ejectOnFull(), 
						domain_list.get(i).scheduledEject(), domain_list.get(i).verifyBeforeEject());

	
				for(int j=0; j < domain_list.get(i).memberCount(); j++)
				{
					if(domain_list.get(i).memberType(j).equals("pool"))
					{
						domain.addMember(domain_list.get(i).memberName(j),
								domain_list.get(i).memberWritePreference(j));

					}
					else
					{
						domain.addMember(domain_list.get(i).memberName(j),
								domain_list.get(i).memberTapeType(j), domain_list.get(i).memberCompactionThreshold(j),
								domain_list.get(i).memberWritePreference(j));
					}

				}
	
				config.add(domain);
			}
		}

		return config;
	}

	private static Configuration addVolumes(Volumes vols, HashMap<String, Pool> pool_id_map, Configuration config)
	{
		if(vols == null)
		{
			System.err.println("INFO: No volumes found.");
		}
		else
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
		}

		return config;
	}
}
