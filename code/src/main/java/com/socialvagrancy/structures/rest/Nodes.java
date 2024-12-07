//===================================================================
// Nodes.java
// 	Description:
// 		Holds the information in rest/nodes.json.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

import java.math.BigInteger;

public class Nodes
{
	public NodeInfo[] data;

	public class NodeInfo
	{
		public String name;
		public String part_number;
		public String serial_number;
		public String software_version;
		public boolean safe_mode;
		public boolean cod_restore_in_progress;
		public boolean manufacturing_mode;
		public String run_state;
		public boolean pending_shutdonw;
		public BigInteger memory;
		public PCICards[] expansion_cards;
		public HotPairInfo hotpair_status;
		public String created_at;
		public String updated_at;
		public String id;
	}

	public class PCICards
	{
		public int slot;
		public String type;
		public int[] address;
		public String devname;
		public int unit;
		public String[] devices;
	}

	public class HotPairInfo
	{
		public String active_hostid;
		public boolean hotpair;
		public String local_serial;
		public String local_state;
		public String peer_serial;
		public String active_zfs_hostid;
		public String peer_state;
	}

	//=======================================
	// GETTERS
	//=======================================

	public String getName() { return data[0].name; }
	public String getSerial() { return data[0].serial_number; }
	public String getVersion() { return data[0].software_version; }

}
