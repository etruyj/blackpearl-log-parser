//===================================================================
// GuiBucket.java
// 	Description:
// 		Holds the inforamtion on the gui_ds3_buckets.json
// 		file.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

import java.math.BigInteger;

public class GuiBucket
{
	public Bucket[] data;

	//=======================================
	// Functions
	//=======================================

	public int bucketCount() 
	{
	       	if(data == null)
		{
			return 0;
		}	
		else
		{
			return data.length;
		}	
	}
	public String getDataPolicyID(int bucket) { return data[bucket].data_policy_id; }
	public String getID(int bucket) { return data[bucket].id; }
	public String getName(int bucket) { return data[bucket].name; }
	public String getOwner(int bucket) { return data[bucket].user_username; }
	public BigInteger getSize(int bucket) { return data[bucket].logical_used_capacity; }

	//=======================================
	// Internal Classes
	//=======================================

	public class Bucket
	{
		public String name;
		public boolean empty;
		public BigInteger logical_used_capacity;
	       	public int user_id;
		public String data_policy_id;
		public String created_at;
		public String updated_at;
		public String id;
		public String user_username;	
	}
}
