//===================================================================
// BucketConfig.java
// 	Description:
// 		A paired-down version of the bucket to hold necessary
// 		information for the configuration.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.config;

public class BucketConfig
{
	public String name;
	public String data_policy;
	public String owner;

	public BucketConfig(String n, String d, String o)
	{
		name = n;
		data_policy = d;
		owner = o;
	}
}
