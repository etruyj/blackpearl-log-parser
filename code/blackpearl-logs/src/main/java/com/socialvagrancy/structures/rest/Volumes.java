//===================================================================
// Volumes.java
// 	Description:
// 		Container class for rest/volumes.json.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

import java.math.BigInteger;

public class Volumes
{
	Volume[] data;

	//=======================================
	// Getters
	//=======================================
	
	public int volumeCount() { return data.length; }
	public String id(int vol) { return data[vol].id; }
	public String name(int vol) { return data[vol].name; }
	public String poolID(int vol) { return data[vol].pool_id; }
	public boolean caseSensitive(int vol) { return data[vol].case_sensitive; }
	public boolean compression(int vol) { return data[vol].compression; }
	public boolean accessTime(int vol) { return data[vol].atime; }
	public Volume volumeData(int vol) { return data[vol]; }
	//=======================================
	// Inner Classes
	//=======================================
	
	public class Volume
	{
		String id;
		boolean atime;
		BigInteger available;
		boolean compression;
		String dataset_id;
		boolean deduplication;
		String mount_point;
		public String name; // Needed for map function
		String pool_id;
		String quota;
		boolean read_only;
		String reservation;
		String type;
		BigInteger used;
		BigInteger used_by_snapshots;
		String created_at;
		String updated_at;
		String bpowenrid;
		boolean replicated;
		boolean case_sensitive;
		String[] transactions;
		String zfs_name;
		boolean nfi_volume_policy_enabled;
		int nfi_volume_policy_nfi_system_id;
		String nfi_volume_policy_bucket_id;
		String nfi_volume_policy;
		int nfi_volume_policy_days_to_keep;
		String nfi_volume_policy_cron_string;
		boolean nfi_volume_policy_start_when_in_cache;
		String[] nfi_volume_policy_ds3_job_ids;
		boolean replication_volume_policy_enabled;
		String replication_volume_policy_replication_system_id;
		String replication_volume_policy_destination_pool_name;
		String replication_volume_policy_destination_volume_name;
		String replication_volume_policy_cron_string;
		boolean replication_volume_policy_initial_validation;
		String replication_volume_policy_error_message_id;
		String replication_volume_policy_status;


	}
}
