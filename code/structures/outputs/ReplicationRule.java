//===================================================================
// ReplicationRule.java
// 	Description:
// 		A temporary holder for BlackPearl data replication rules.
// 		Holds DS3, AWS, and Azure targets.
//
// 		Not sure if this will end up being a permanent class
// 		or if a different class will need to be created 
// 		depending on what needs exist for this information.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.outputs;

public class ReplicationRule
{
	public String data_policy_id;
	public String id;
	public String target_id;
	public String target_name;
	public String target_ip;
	public String state;
	public String type; // AWS, Azure, DS3
}
