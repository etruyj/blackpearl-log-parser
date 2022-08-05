//===================================================================
// GuiDataPolicy.java
// 	Description:
// 		A container class for information in the 
// 		rest/gui_ds3_data_policies.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

import java.math.BigInteger;

public class GuiDataPolicy
{
	public DataPolicy[] data;

	//=======================================
	// Functions
	//=======================================

	public int policyCount()
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

	public String getName(int policy) { return data[policy].name; }
	public String getID(int policy) { return data[policy].id; }

	//=======================================
	// Inner Classes
	//=======================================
	
	public class DataPolicy
	{
		public String name;
		public boolean always_force_put_job_creation;
		public boolean always_minimize_spanning_across_media;
		public boolean blobbing_enabled;
		public String checksum_type;
		public BigInteger default_blob_size;
		public String default_get_job_priority;
		public String default_put_job_priority;
		public String default_verify_job_priority;
		public boolean default_verify_after_write;
		public boolean end_to_end_crc_required;
		public String rebuild_priority;
		public String versioning;
		public BigInteger max_versions_to_keep;
		public String created_at;
		public String updated_at;
		public String id;
	}
}
