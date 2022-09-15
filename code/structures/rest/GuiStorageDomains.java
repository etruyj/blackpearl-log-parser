//===================================================================
// GuiStorageDomains.java
// 	Description:
// 		A container for the rest/gui_ds3_storage_domains.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

public class GuiStorageDomains
{
	public StorageDomain[] data;

	//=======================================
	// Functions
	//=======================================
	
	public int getDomainCount()
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

	//=======================================
	// Inner Classes
	//=======================================

	public class StorageDomain
	{
		public String name;
		public String write_optimization;
		public boolean secure_media_allocation;
		public boolean media_ejection_allowed;
		public String auto_eject_upon_cron;
		public boolean auto_eject_upon_completion;
		public boolean auto_eject_upon_cancellation;
		public boolean auto_eject_upon_media_full;
		public int maximum_auto_verification_frequency_in_days;
		public String ltfs_file_naming;
		public boolean verify_prior_to_auto_eject;
		public String created_at;
		public String updated_at;
		public String id;
	}
}	
