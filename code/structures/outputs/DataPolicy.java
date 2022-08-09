//===================================================================
// DataPolicy.java
// 	Description:
// 		A container class for data policies and their
// 		data persistence rules.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.outputs;

import java.util.ArrayList;

public class DataPolicy
{
	public String name;
	public String id;
	public String checksum;
	public ArrayList<StorageDomain> domains_list;
	public ArrayList<ReplicationRule> replication_list;

	public DataPolicy()
	{
		domains_list = new ArrayList<StorageDomain>();
		replication_list = new ArrayList<ReplicationRule>();
	}

	//=======================================
	// Functions
	//=======================================

	public String getID() { return id; }

	public int dataCopies() { return domains_list.size() + replication_list.size(); }
	public int replicationRuleCount() { return replication_list.size(); }
	public int getStorageDomainCount() { return domains_list.size(); }

	// Get

	public String replicationTargetID(int i) { return replication_list.get(i).target_id; }
	public String replicationTargetIP(int i) { return replication_list.get(i).target_ip; }
	public String replicationTargetName(int i) { return replication_list.get(i).target_name; }
	public String replicationTargetType(int i) { return replication_list.get(i).type; }

	// Setters

	public void addPersistenceRule(StorageDomain domain)
	{
		domains_list.add(domain);
	}

	public void addReplicationRule(ReplicationRule rule)
	{
		replication_list.add(rule);
	}
}
