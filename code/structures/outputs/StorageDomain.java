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
	public int days_to_verify;
	public String media_ejection_upon_cron;
	public String ltfs_file_naming;
	public boolean media_ejection_allowed;
	public boolean auto_eject_upon_completion;
	public boolean auto_eject_upon_cancellation;
	public boolean auto_eject_upon_media_full;
	public boolean verify_prior_to_auto_eject;

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

	public int daysToVerify() { return days_to_verify; }
	public String getID() { return id; }
	public boolean ejectAllowed() { return media_ejection_allowed; }
	public boolean ejectOnCancel() { return auto_eject_upon_cancellation; }
	public boolean ejectOnComplete() { return auto_eject_upon_completion; }
	public boolean ejectOnFull() { return auto_eject_upon_media_full; }
	public ArrayList<StorageDomainMember> getMembers() { return members; }
	public String ltfsNaming() { return ltfs_file_naming; }
	public int memberCount() { return members.size(); }
	public String memberName(int id) { return members.get(id).member_name; }
	public String name() { return name; }
	public String scheduledEject() { return media_ejection_upon_cron; }
       	public boolean verifyBeforeEject() { return verify_prior_to_auto_eject; }	
	public String writeOptimization() { return write_optimization; }

	public int memberCompactionThreshold(int id)
	{
		return members.get(id).auto_compaction_threshold;
	}

	public String memberType(int id)
	{
		return members.get(id).getType(); 
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
