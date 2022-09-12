//===================================================================
// Configuration.java
// 	Description:
// 		This holds the full configuration information for the
// 		BlackPearl. The goal would be to use this with a
// 		second script to automate the configuration.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.outputs;

import java.util.ArrayList;

public class Configuration
{
	ArrayList<Pool> pools;
	ArrayList<Volume> volumes;
	ArrayList<CIFSShare> cifs_shares;
	ArrayList<NFSShare> nfs_shares;
	ArrayList<Share> vail_shares;
	ArrayList<StorageDomain> storage_domains;
	ArrayList<DataPolicy> data_policies;
	ArrayList<Bucket> buckets;

	public Configuration()
	{
		pools = new ArrayList<Pool>();
		volumes = new ArrayList<Volume>();
		cifs_shares = new ArrayList<CIFSShare>();
		nfs_shares = new ArrayList<NFSShare>();
		vail_shares = new ArrayList<Share>();
		storage_domains = new ArrayList<StorageDomain>();
		data_policies = new ArrayList<DataPolicy>();
		buckets = new ArrayList<Bucket>();
	}
}
