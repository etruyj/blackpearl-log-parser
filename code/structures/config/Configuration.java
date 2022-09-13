//===================================================================
// Configuration.java
// 	Description:
// 		This holds the full configuration information for the
// 		BlackPearl. The goal would be to use this with a
// 		second script to automate the configuration.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.config;

import com.socialvagrancy.blackpearl.logs.structures.outputs.StorageDomain;
import java.util.ArrayList;

public class Configuration
{
	ArrayList<PoolConfig> pools;
	ArrayList<VolumeConfig> volumes;
	ShareData shares;
	ArrayList<StorageDomain> storage_domains;
	ArrayList<DataPolicyConfig> data_policies;
	ArrayList<BucketConfig> buckets;

	//=======================================
	// Getters
	//=======================================
	
	public int storageDomainCount() { return storage_domains.size(); }
	public int volumeCount() { return volumes.size(); }

	//=======================================
	// Setters
	//=======================================

	public void addBucket(String bname, String bpolicy, String bowner)
	{
		if(buckets == null)
		{
			buckets = new ArrayList<BucketConfig>();
		}
	
		BucketConfig bucket = new BucketConfig(bname, bpolicy, bowner);

		buckets.add(bucket);
	
	}

	public void add(DataPolicyConfig policy)
	{
		if(data_policies == null)
		{
			data_policies = new ArrayList<DataPolicyConfig>();
		}
	
		data_policies.add(policy);
	}

	public void add(PoolConfig pool)
	{
		if(pools == null)
		{
			pools = new ArrayList<PoolConfig>();
		}

		pools.add(pool);
	}

	// Shares by Type
	public void addCIFS(CIFSShare cifs)
	{
		createShareData();

		if(shares.cifs_shares == null)
		{
			shares.cifs_shares = new ArrayList<CIFSShare>();
		}

		shares.cifs_shares.add(cifs);
	}

	public void addNFS(NFSShare nfs)
	{
		createShareData();

		if(shares.nfs_shares == null)
		{
			shares.nfs_shares = new ArrayList<NFSShare>();
		}

		shares.nfs_shares.add(nfs);
	}

	public void addVAIL(Share vail)
	{
		createShareData();

		if(shares.vail_shares == null)
		{
			shares.vail_shares = new ArrayList<Share>();
		}

		shares.vail_shares.add(vail);
	}

	public void add(VolumeConfig volume)
	{
		if(volumes == null)
		{
			volumes = new ArrayList<VolumeConfig>();
		}

		volumes.add(volume);
	}

	public void addStorageDomains(ArrayList<StorageDomain> domains)
	{
		if(storage_domains == null)
		{
			storage_domains = domains;
		}
		else
		{
			storage_domains.addAll(domains);
		}
	}

	private void createShareData()
	{
		// Check to see if share data exists
		// and if not generate it.
		// Use this for each add share command
		// to keep the json output clean.
		
		if(shares == null)
		{
			shares = new ShareData();
		}
	}

/*	
	public static void addStorageDomains(ArrayList<StorageDomain> domain_list)
	{
		
		if(storage_domains == null)
		{
			orig_size = 0;
		}
		else
		{
			orig_size = storage_domains.length;
		}

		int new_size = orig_size = orig_size + domain_list.size();
		StorageDomain[] domains = new StorageDomain[new_size];

		// Copy over the old list;
		for(int i=0; i < orig_size; i++)
		{
			domains[i] = storage_domains[i];
		}

		// Add new values
		// Iterate through the ArrayList<StorageDomain> starting at 0
		// but fill in the Array starting where the last Array
		// left off (orig_size)
		for(int i = 0; i < (new_size - orig_size); i++)
		{
			domains[orig_size + i] = domains.get(i);
		}

		storage_domains = domains;
	}
	*/

	//=======================================
	// Inner Classes
	//=======================================
	
	public class ShareData
	{
		ArrayList<CIFSShare> cifs_shares;
		ArrayList<NFSShare> nfs_shares;
		ArrayList<Share> vail_shares;	
	}
}
