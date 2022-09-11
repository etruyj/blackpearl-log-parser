//===================================================================
// StorageDomain.java
// 	Description:
// 		Holds the linked information for storage domains and
// 		their members for display.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.outputs;

import com.socialvagrancy.blackpearl.logs.structures.rest.GuiStorageDomainMembers;
import com.socialvagrancy.blackpearl.logs.structures.rest.data.StorageDomainMember;
import java.util.ArrayList;

public class StorageDomain
{
	public String id;
	public String name;
       	public String write_optimization;	
	public ArrayList<StorageDomainMember> members;

	public StorageDomain()
	{
		members = new ArrayList<StorageDomainMember>();
	}

	//=======================================
	// Functions
	//=======================================
	
	public void addMember(StorageDomainMember member)
	{
		members.add(member);
	}

	public String getID() { return id; }
	public int memberCount() { return members.size(); }
	public String memberName(int id) { return members.get(id).member_name; }
	public String name() { return name; }
	public String writeOptimization() { return write_optimization; }

	public String memberType(int id)
	{
		return members.get(id).getType(); 

		/*
		 * MARK FOR DELETION
		if(members.get(id).pool_partition_id == null)
		{
			return "tape";
		}
		else if(members.get(id).tape_partition_id == null)
		{
			return "pool";
		}

		return "none listed";
		*/
	}

	public String memberState(int id)
	{
		return members.get(id).state;
	}

	public String memberTapeType(int id)
	{
		return members.get(id).tape_type;
	}

	public String getTargetID(int id)
	{
		if(members.get(id).pool_partition_id != null)
		{
			return members.get(id).pool_partition_id;
		}

		if(members.get(id).tape_partition_id != null)
		{
			return members.get(id).tape_partition_id;
		}

		return "none";
	}

	public String memberWritePreference(int id)
	{
		return members.get(id).write_preference;
	}
}
