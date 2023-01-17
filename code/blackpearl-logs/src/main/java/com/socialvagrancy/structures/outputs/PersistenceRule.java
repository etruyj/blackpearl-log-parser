//===================================================================
// PersistenceRule.java
// 	Description:
// 		Data Persistence Rule for the outputs/DataPolicy.java
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.outputs;

public class PersistenceRule extends StorageDomain
{
	String isolation_level;
	int minimum_days_to_retain;
	String state;
	String type;
}
