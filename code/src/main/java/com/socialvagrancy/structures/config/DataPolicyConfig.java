//===================================================================
// DataPolicyConfig.java
// 	Description:
// 		A pair-down version fo the DataPolicy variable for
// 		easy display of the system configuraiton.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.config;

import java.util.ArrayList;

public class DataPolicyConfig
{
	String name;
	boolean blobbing_enabled;
	boolean minimize_spanning;
	String default_get_priority;
	String default_put_priority;
	String default_verify_priority;
	String rebuild_priority;
	String checksum_type;
	boolean require_end_to_end_crc;
	String versioning;
	int versions_to_keep;
	boolean always_accept_replicated_puts;
	ArrayList<PersistenceRule> data_persistence_rules;
	ArrayList<ReplicationRule> data_replication_rules;

	public DataPolicyConfig(String n)
	{
		name = n;
	}

	//=======================================
	// Getters
	//=======================================

	//=======================================
	// Setters
	//=======================================

	public void addPersistenceRule(String n, String t, String isolation, int days)
	{
		if(data_persistence_rules == null)
		{
			data_persistence_rules = new ArrayList<PersistenceRule>();
		}

		PersistenceRule rule = new PersistenceRule();
		rule.name = n;
		rule.type = t;
		rule.isolation_level = isolation;
		rule.days_to_retain = days;

		data_persistence_rules.add(rule);
	}

	public void addReplicationRule(String n, String t, String ip)
	{
		if(data_replication_rules == null)
		{
			data_replication_rules = new ArrayList<ReplicationRule>();
		}

		ReplicationRule rule = new ReplicationRule();

		rule.name = n;
		rule.type = t;
		rule.ip_address = ip;

		data_replication_rules.add(rule);
	}

	public void setBlobbing(boolean b) { blobbing_enabled = b; }
	public void setChecksum(String c) { checksum_type = c; }
	public void setEndToEndCRC(boolean c) { require_end_to_end_crc = c; }
	public void setName(String n) { name = n; }
	public void setPriorityGet(String p) { default_get_priority = p; }
	public void setPriorityPut(String p) { default_put_priority = p; }
	public void setPriorityRebuild(String p) { rebuild_priority = p; }
	public void setPriorityVerify(String p) { default_verify_priority = p; }
	public void setReplicatedPuts(boolean r) { always_accept_replicated_puts = r; }
	public void setSpanning(boolean s) { minimize_spanning = s; }
	public void setVersioning(String ver, int copies)
	{
		versioning = ver;
		versions_to_keep = copies;
	}

	//=======================================
	// Inner Classes
	//=======================================
	
	public class PersistenceRule
	{
		String name;
		String type;
		String isolation_level;
		int days_to_retain;
	}

	public class ReplicationRule
	{
		String name;
		String type;
		String ip_address;
		String data_policy;
	}
}
