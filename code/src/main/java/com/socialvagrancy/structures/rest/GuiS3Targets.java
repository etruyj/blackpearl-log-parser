//===================================================================
// GuiS3Targets.java
// 	Description:
// 		A container class for the rest/gui_ds3_s3_targets.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

public class GuiS3Targets
{
	public S3Target[] data;

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

	public String bucketName(int target) 
	{ 
		return data[target].cloud_bucket_prefix 
			+ data[target].name 
			+ data[target].cloud_bucket_suffix;
	}

	public String id(int target) { return data[target].id; }
	public String region(int target) { return data[target].region; }
	public String state(int target) { return data[target].state; }
	public String dataPath(int target) { return data[target].data_path_end_point; }


	//=======================================
	// Inner Classes
	//=======================================

	public class S3Target
	{
		public String name;
		public String access_key;
		public int auto_verify_frequency_in_days;
		public String cloud_bucket_prefix;
		public String cloud_bucket_suffix;
		public String data_path_end_point;
		public String default_read_preference;
		public boolean https;
		public boolean permit_going_out_of_sync;
		public String proxy_host;
		public String proxy_domain;
		public String proxy_port;
		public String proxy_username;
		public String quiesced;
		public String region;
		public int staged_data_expiration_in_days;
		public String naming_mode;
		public String state;
		public String last_fully_verified;
		public String created_at;
		public String updated_at;
		public String id;
	}
}
