//===================================================================
// GuiDS3RepRules.java
// 	Description:
// 		A container class for the 
// 		rest/gui_ds3_ds3_replication_rules.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

public class GuiDS3RepRules
{
	public ReplicationRule[] data;

	//=======================================
	// Functions
	//=======================================
	
	public int ruleCount()
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

	public String dataPolicyID(int rule) { return data[rule].data_policy_id; }
	public String targetID(int rule) { return data[rule].target_id; }
	public String id(int rule) { return data[rule].id; }

	//=======================================
	// Inner Classes
	//=======================================
	
	public class ReplicationRule
	{
		public String data_policy_id;
		public String target_id;
		public String type;
		public boolean replicate_deletes;
		public String target_data_policy;
		public String state;
		public String created_at;
		public String updated_at;
		public String id;
	}
}
