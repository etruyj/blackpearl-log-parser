//===================================================================
// GuiStorageDomainMembers.java
// 	Description:
// 		A container for rest/gui_ds3_storage_domain_members.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

import com.socialvagrancy.blackpearl.logs.structures.rest.data.StorageDomainMember;

public class GuiStorageDomainMembers
{
	public StorageDomainMember[] data;

	//=======================================
	// Fuctions
	//=======================================

	public int getMemberCount()
	{
		if(data==null)
		{
			return 0;
		}
		else
		{
			return data.length;
		}
	}

	public StorageDomainMember getMember(int member)
	{
		return data[member];
	}

	public String getMemberID(int member) 
	{
	       	if(getMemberType(member).equals("pool"))
		{	
			return data[member].pool_partition_id;
		}
		else
		{
			return data[member].tape_partition_id;
		}	
	}

	public String getMemberType(int member) { return data[member].getType(); }
	public String getStorageDomainID(int member) { return data[member].storage_domain_id; }
	public String getWritePreference(int member) { return data[member].write_preference; }
	public String getTapeType(int member) { return data[member].tape_type; }

	public void setMemberName(int member, String name) { data[member].member_name = name; }
}
