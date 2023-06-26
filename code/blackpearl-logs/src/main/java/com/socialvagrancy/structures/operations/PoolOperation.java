//===================================================================
// PoolOperation.java
//	Description:
//		Holds info on pool related tasks
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.operations;

public class PoolOperation
{
	public String task_id;
	public String task_type;
	public String pool_name;
	public String pool_id;
	public String created_at;
	public String date_completed;
	public String duration;
	public String size;
	public String throughput;
	public String[] chunks;

	public int chunkCount() 
	{ 
		if(chunks == null)
		{
			return 0;
		}
		else
		{
			return chunks.length;
		}	
	}
}
