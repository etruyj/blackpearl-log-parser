//===================================================================
// StorageDomainMemberConfig.java
// 	Description:
// 		A class for holding the necessary information for
// 		displaying the configuration information.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.config;

public class StorageDomainMemberConfig
{
	String partition;
	String tape_type;
	int auto_compaction_threshold;
	String write_optimization;

	public StorageDomainMemberConfig(String par, String optimization)
	{
		partition = par;
		write_optimization = optimization;
	}

	public StorageDomainMemberConfig(String par, String type, int compaction, String optimization)
	{
		partition = par;
		tape_type = type;
		auto_compaction_threshold = compaction;
		write_optimization = optimization;
	}
}
