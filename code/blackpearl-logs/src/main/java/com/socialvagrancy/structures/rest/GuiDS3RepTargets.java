//===================================================================
// GuiDS3RepTarget.java
// 	Description:
// 		A container for the rest/gui_ds3_ds3_targets.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

public class GuiDS3RepTargets
{

	public DS3Target[] data;

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

	public String dataPath(int target) { return data[target].data_path_end_point; }
	public String id(int target) { return data[target].id; }
	public String name(int target) { return data[target].name; }
	public String state(int target) { return data[target].state; }

	//=======================================
	// Inner Classes
	//=======================================

	public class DS3Target
	{
		public String admin_auth_id;
		public String data_path_end_point;
		public String name;
		public String access_control_replication;
		public int data_path_port;
		public String data_path_proxy;
		public String read_preference;
		public String quiesced;
		public String replicated_user_default_data_policy;
		public String state;
		public boolean data_path_https;
		public String created_at;
		public String updated_at;
		public String id;
	}
}
