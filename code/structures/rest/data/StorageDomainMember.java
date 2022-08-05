//===================================================================
// StorageDomainMembers.java
// 	Description:
// 		A subclass for rest/GuiStorageDomainMember, 
// 		MapSDMemberToSDID, and output/StorageDomain;
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest.data;

public class StorageDomainMember
{
	public String pool_partition_id;
	public String tape_partition_id;
	public String storage_domain_id;
	public String write_preference;
	public String tape_type;
	public String state;
	public int auto_compaction_threshold;
	public String created_at;
	public String updated_at;
	public String id;
}
