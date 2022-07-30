//===================================================================
// DS3Operation.java
// 	Description:
// 		Holds the modified DS3 replication information.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.operations;

import com.socialvagrancy.blackpearl.logs.structures.CompletedJob;

public class DS3Operation
{
	public String id;
	public String type;
	public String created_at;
	public String date_completed;
	public String[] chunks;
	public String target_id;
	public String target_name;

	public int chunkCount()
	{
		if(chunks==null)
		{
			return 0;
		}
		else
		{
			return chunks.length;
		}
	}
}
