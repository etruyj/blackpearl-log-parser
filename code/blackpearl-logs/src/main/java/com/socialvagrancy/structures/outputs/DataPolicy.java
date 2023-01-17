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
	public boolean blobbing_enabled;
	public boolean minimize_spanning;
	public String default_get_priority;
	public String default_put_priority;
	public String default_verify_priority;
	public String rebuild_priority;
	public String checksum;
	public boolean end_to_end_crc;
	public String versioning;
	public int versions_to_keep;
	public boolean replicated_puts;
	public ArrayList<PersistenceRule> persistence_rules;
	public ArrayList<ReplicationRule> replication_rules;

	public DataPolicy()
	{
		persistence_rules = new ArrayList<PersistenceRule>();
		replication_rules = new ArrayList<ReplicationRule>();
	}

	//=======================================
	// Functions
	//=======================================


	public int getStorageDomainCount() { return persistence_rules.size(); }

	// Get

	public int dataCopies() { return persistence_rules.size() + replication_rules.size(); }
	public String getID() { return id; }
	public String persistenceRuleIsolation(int i) { return persistence_rules.get(i).isolation_level; }
	public String persistenceRuleName(int i) { return persistence_rules.get(i).name; }
	public int persistenceRuleRetention(int i) { return persistence_rules.get(i).minimum_days_to_retain; }
	public String persistenceRuleType(int i) { return persistence_rules.get(i).type; }
	public int replicationRuleCount() { return replication_rules.size(); }
	public String replicationTargetID(int i) { return replication_rules.get(i).target_id; }
	public String replicationTargetIP(int i) { return replication_rules.get(i).target_ip; }
	public String replicationTargetName(int i) { return replication_rules.get(i).target_name; }
	public String replicationTargetType(int i) { return replication_rules.get(i).type; }
	public String storageDomainName(int i) { return persistence_rules.get(i).name; }
	public int storageDomainMemberCount(int i) { return persistence_rules.get(i).memberCount(); }

	// Setters

	public void addPersistenceRule(StorageDomain domain, String rule_type, String rule_state, String isolation, int days_to_retain)
	{
		PersistenceRule rule = new PersistenceRule();

		rule.type = rule_type;
		rule.state = rule_state;
		rule.isolation_level = isolation;
		rule.minimum_days_to_retain = days_to_retain;

		rule.name = domain.name();
		rule.write_optimization = domain.writeOptimization();
		rule.members = domain.getMembers();
		

		persistence_rules.add(rule);
	}

	public void addReplicationRule(ReplicationRule rule)
	{
		replication_rules.add(rule);
	}
}
