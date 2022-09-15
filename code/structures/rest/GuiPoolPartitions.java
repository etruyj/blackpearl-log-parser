//===================================================================
// GuiPoolPartitions.java
// 	Description:
// 		A container class for the rest/gui_ds3_pool_partitions.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

public class GuiPoolPartitions
{
	PoolPartition[] data;

	//=======================================
	// Getters
	//=======================================

	public int count() { return data.length; }	
	public String id(int pool) { return data[pool].id; }
	public String name(int pool) { return data[pool].name; }
	public PoolPartition partitionData(int pool) { return data[pool]; }
	public int poolCount(int pool) { return data[pool].pool_ids.length; }
	public String poolID(int pool, int id) { return data[pool].pool_ids[id]; }
	public String type(int pool) { return data[pool].type; }

	//=======================================
	// Inner Classes
	//=======================================
	
	public class PoolPartition
	{
		String name;
		String type;
		String[] pool_ids;
		String created_at;
		String updated_at;
		String id;
	
		public String name() { return name; }
	}
}
