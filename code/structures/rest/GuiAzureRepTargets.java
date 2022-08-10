//===================================================================
// GuiAzureRepTargets.java
// 	Description:
// 		A container for rest/gui_ds3_azure_targets.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

public class GuiAzureRepTargets
{
	public AzureTarget[] data;

	//=======================================
	// Functions
	//=======================================
	
	public int targetCount()
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

	public String id(int target) { return data[target].id; }
	public String name(int target) { return data[target].name; }
	public String state(int target) { return data[target].state; }
	public String dataPath() { return "MS Azure"; }

	//=======================================
	// Inner Classes
	//=======================================

	public class AzureTarget
	{
		public String name;
		public String account_name;
		public int auto_verify_frequency_in_days;
		public String cloud_bucket_prefix;
		public String cloud_bucket_suffix;
		public String default_read_preference;
		public boolean https;
		public boolean permit_going_out_of_sync;
		public String quiesced;
		public String state;
		public String last_fully_verified;
		public String created_at;
		public String updated_at;
		public String id;
	}
}
