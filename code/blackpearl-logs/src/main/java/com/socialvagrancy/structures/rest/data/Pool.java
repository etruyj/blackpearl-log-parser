//===================================================================
// Pool.java
// 	Description:
// 		A container for the rest/pools.json values.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest.data;

import java.math.BigInteger;

public class Pool
{
	public String id;
	public String name;
	public String protection;
	public int stripes;
	public String health;
	public String raw_size;
	public RebuildStatus rebuild_status;
	public ScrubStatus scrub_status;
	public String status;
	public String available;
	public String overhead;
	public String used;
	public String zfs_status;
	public String high_water_mark;
	public String last_import_time;
	public String created_at;
	public String updated_at;
	public boolean globalhotspace;
	public boolean autoreplace;
	public String power_saving_mode;
	public int special_available;
	public int special_used;
	public String[] disk_ids;
	public Topology[] topology;
	public Topology[] log;
	public String[] zil_drives;
	public String[] special;
	public String[] special_drives;
	public String[] special_disk_ids;
	public String type;
	public String ds3_pool_health;
	public String ds3_pool_type;
	public String partition_id;
	public BigInteger total_capacity;
	public boolean assigned_to_storage_domain;
	public String storage_domain_member_id;
	public String quiesced;
	
	public class RebuildStatus
	{
		String state;
		String start_time;
		int minutes_left;
		int minutes_taken;
		BigInteger bytes_to_scan;
		BigInteger bytes_scanned;
		int errors;
		String end_time;
		int percent_complete;
		String eta;
	}

	public class ScrubStatus
	{
		String state;
		String start_time;
		int minutes_left;
		int minutes_take;
		BigInteger bytes_to_scan;
		BigInteger bytes_scanned;
		int errors;
		String end_time;
		int percent_complete;
		int eta;
	}

	public class Topology
	{
		public String type;
		public String health;
		public TopoChild[] children;
	}

	public class TopoChild extends Topology
	{
		public String id;
		public String disk_id;
		public BigInteger size;
	}
}
