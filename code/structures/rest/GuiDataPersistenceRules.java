//===================================================================
// GuiDataPersistenceRules.java
// 	Description:
// 		A container for the rest/gui_ds3_data_persistence_rule.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

public class GuiDataPersistenceRules
{
	public DataPersistenceRule[] data;
	//=======================================
	// Functions
	//=======================================
	
	public int getRuleCount()
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

	public String getDataPolicyID(int rule) { return data[rule].data_policy_id; }
	public String getStorageDomainID(int rule) { return data[rule].storage_domain_id; }

	//=======================================
	// Inner Classes
	//=======================================
	
	public class DataPersistenceRule
	{
		public String data_policy_id;
		public String isolation_level;
		public String storage_domain_id;
		public String type;
		public int minimum_days_to_retain;
		public String state;
		public String created_at;
		public String update_at;
		public String id;
	}
}
