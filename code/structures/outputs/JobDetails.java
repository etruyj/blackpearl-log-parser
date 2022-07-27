//===================================================================
// JobDetails.java
// 	Description:
// 		This holds the final detailed information for the
// 		assembled job.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.outputs;

import com.socialvagrancy.blackpearl.logs.structures.operations.PoolOperation;
import com.socialvagrancy.blackpearl.logs.structures.operations.TapeOperation;
import com.socialvagrancy.blackpearl.logs.structures.CompletedJob;

import java.util.ArrayList;
import java.util.HashMap;

public class JobDetails
{
	public CompletedJob.JobInfo job_info;
	public HashMap<String, ArrayList<TapeOperation>> tape_copies;
	public HashMap<String, ArrayList<PoolOperation>> pool_copies;

	public JobDetails()
	{
		tape_copies = new HashMap<String, ArrayList<TapeOperation>>();
		pool_copies = new HashMap<String, ArrayList<PoolOperation>>();
	}

	//=======================================
	// Getters
	//=======================================

	public int tapeCopyCount(String chunk) 
	{
	       	if(tape_copies.get(chunk) == null)
		{
			return 0;
		}
		else
		{
			return tape_copies.get(chunk).size(); 
		}
	}

	public int poolCopyCount(String chunk) 
	{ 
		if(pool_copies.get(chunk) == null)
		{
			return 0;
		}
		else
		{
			return pool_copies.get(chunk).size(); 
		}
	}

	//=======================================
	// Settings
	//=======================================
	
	public void addPoolInfo(String chunk, ArrayList<PoolOperation> ops_list) { pool_copies.put(chunk, ops_list); }
}
