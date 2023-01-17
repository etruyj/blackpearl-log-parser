//===================================================================
// StorageDomainMemberConfig.java
// 	Description:
// 		A class to hold values necessary for configuring the
// 		blackpearl.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.config;

import java.util.ArrayList;

public class StorageDomainConfig
{
	String name;
	int days_to_verify;
	String write_optimization;
	String ltfs_file_naming;
	boolean media_ejection_allowed;
	boolean auto_eject_upon_completion;
	boolean auto_eject_upon_cancellation;
	boolean auto_eject_upon_media_full;
	String media_ejection_upon_cron;
	String verify_prior_to_auto_eject;
	ArrayList<StorageDomainMemberConfig> members;

	public StorageDomainConfig(String n, int verify, String optimization, String file_naming,
			boolean eject_allowed, boolean eject_on_complete, boolean eject_on_cancel, boolean eject_on_full,
			String scheduled_eject, String verify_before_eject)
	{
		name = n;
		days_to_verify = verify;
		write_optimization = optimization;
		ltfs_file_naming = file_naming;
		media_ejection_allowed = eject_allowed;
		auto_eject_upon_completion = eject_on_complete;
		auto_eject_upon_cancellation = eject_on_cancel;
		auto_eject_upon_media_full = eject_on_full;
		media_ejection_upon_cron = scheduled_eject;
		verify_prior_to_auto_eject = verify_before_eject;
	}


	//=======================================
	// Functions
	//=======================================

	public void addMember(String par, String type, int compaction, String preference)
	{
		StorageDomainMemberConfig member = new StorageDomainMemberConfig(par, type, compaction, preference);

		if(members == null)
		{
			members = new ArrayList<StorageDomainMemberConfig>();
		}

		members.add(member);
	}
	
	public void addMember(String par, String preference)
	{
		StorageDomainMemberConfig member = new StorageDomainMemberConfig(par, preference);

		if(members == null)
		{
			members = new ArrayList<StorageDomainMemberConfig>();
		}

		members.add(member);
	}
	
}
