//===================================================================
// DiskPartitionConfig.java
// 	Description:
// 		Holds the information necessary for configuring
// 		disk (pool) partitions.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.config;

import java.util.ArrayList;

public class DiskPartitionConfig
{
	String name;
	String type;
	ArrayList<String> members;

	public DiskPartitionConfig(String n, String t)
	{
		name = n;
		type = t;
	}

	public void addMember(String member)
	{
		if(members == null)
		{
			members = new ArrayList<String>();
		}

		members.add(member);
	}	
}
