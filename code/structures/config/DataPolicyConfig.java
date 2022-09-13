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
	boolean minimize_spanning;
	String default_get_priority;
	String default_put_priority;
	String default_verify_priority;
	String rebuild_priority;
	String checksum_type;
	boolean require_end_to_end_crc;
	String versioning;
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

	public void AddPersistenceRule(String n, String s, String t)
	{
		if(data_persistence_rules == null)
		{
			data_persistence_rules = new ArrayList<PersistenceRule>();
		}

		PersistenceRule rule = new PersistenceRule();
		rule.name = n;
		rule.state = s;
		rule.type = t;

		data_persistence_rules.add(rule);
	}

	public void AddReplicationRule(String n, String s, String t)
	{
		if(data_replication_rules == null)
		{
			data_replication_rules = new ArrayList<ReplicationRule>();
		}

		ReplicationRule rule = new ReplicationRule();

		rule.name = n;
		rule.state = s;
		rule.type = t;

		data_replication_rules.add(rule);
	}

	//=======================================
	// Inner Classes
	//=======================================
	
	public class PersistenceRule
	{
		String name;
		String state;
		String type;
		String isolation_level;
	}

	public class ReplicationRule
	{
		String name;
		String state;
		String type;
		String data_policy;
	}
}
