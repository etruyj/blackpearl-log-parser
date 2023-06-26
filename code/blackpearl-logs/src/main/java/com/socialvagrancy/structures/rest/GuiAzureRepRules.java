//===================================================================
// GuiAzureRepRules.java
// 	Description:
// 		A container class for 
// 		rest/gui_ds3_azure_data_replication_rules.json.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

import java.math.BigInteger;

public class GuiAzureRepRules
{
	public AzureRule[] data;

	//=======================================
	// Function
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
	public String targetID(int rule) { return data[rule].target_id; }

	//=======================================
	// Inncer Classes
	//=======================================
	
	public class AzureRule
	{
		public String data_policy_id;
		public String target_id;
		public String type;
		public BigInteger max_blob_part_size_in_bytes;
		public boolean replicate_deletes;
		public String state;
		public String created_at;
		public String updated_at;
		public String id;
	}
}

