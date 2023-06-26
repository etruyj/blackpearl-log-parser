//===================================================================
// GuiS3RepRules.java
// 	Description:
// 		A container class for 
// 		rest/gui_ds3_s3_data_replication_rule.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

import java.math.BigInteger;

public class GuiS3RepRules
{
	public S3RepRule[] data;

	//=======================================
	// Functions
	//=======================================
	
	public int ruleCount()
	{
		if(data==null)
		{
			return 0;
		}
		else
		{
			return data.length;
		}
	}

	public String dataPolicyID(int rule) { return data[rule].data_policy_id; }
	public String id(int rule) { return data[rule].id; }
	public String state(int rule) { return data[rule].state; }
	public String targetID(int rule) { return data[rule].target_id; }
	

	//=======================================
	// Inner Classes
	//=======================================

	public class S3RepRule
	{
		public String data_policy_id;
		public String target_id;
		public String type;
		public String initial_data_placement;
		public BigInteger max_blob_part_size_in_bytes;
		public boolean replicate_deletes;
		public String state;
		public String created_at;
		public String updated_at;
		public String id;
	}
}
