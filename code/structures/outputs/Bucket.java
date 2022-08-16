//===================================================================
// Bucket.java
// 	Description:
// 		A container class for all the Advanced Bucket Management
// 		information attached to a bucket.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.outputs;

import java.math.BigInteger;

public class Bucket
{
	public String name;
	public String id;
	public String owner;
	public String size_human;
	public DataPolicy data_policy;

	//=======================================
	// Functions
	//=======================================
	
	public void attachPolicy(DataPolicy policy) { data_policy = policy; }
	public int copyCount() { return data_policy.dataCopies(); }
	public int localCopies() { return data_policy.getStorageDomainCount(); }
	public int remoteCopies() { return data_policy.replicationRuleCount(); }
	public String dataPolicy() { return data_policy.name; }
	public String id() { return id; }
}


