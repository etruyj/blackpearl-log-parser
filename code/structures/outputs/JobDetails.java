//===================================================================
// JobDetails.java
// 	Description:
// 		This holds the final detailed information for the
// 		assembled job.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.outputs;

import com.socialvagrancy.blackpearl.logs.structures.operations.DS3Operation;
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
	public HashMap<String, ArrayList<DS3Operation>> ds3_copies;

	//=======================================
	// Calculated job values.
	//=======================================

	public String human_readable_size;
	public String job_duration;

	public JobDetails()
	{
		tape_copies = new HashMap<String, ArrayList<TapeOperation>>();
		pool_copies = new HashMap<String, ArrayList<PoolOperation>>();
		ds3_copies = new HashMap<String, ArrayList<DS3Operation>>();
	}

	//=======================================
	// Getters
	//=======================================

	public String bucketID() { return job_info.bucket_id; }
	public String cachedSize() { return job_info.cached_size_in_bytes; }
	public String completedSize() { return job_info.completed_size_in_bytes; }
	public String createdAt() { return job_info.created_at; }
	public String id() { return job_info.id; }
	public String originalSize() { return job_info.original_size_in_bytes; }
	public String owner() { return job_info.user_username; }
	public String name() { return job_info.name; }
	public String type() { return job_info.request_type; }

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

	public int ds3CopyCount(String chunk)
	{
		if(ds3_copies.get(chunk) == null)
		{
			return 0;
		}
		else
		{
			return ds3_copies.get(chunk).size();
		}
	}

	//=======================================
	// Settings
	//=======================================
	
	public void addPoolInfo(String chunk, ArrayList<PoolOperation> ops_list) { pool_copies.put(chunk, ops_list); }
	public void addDS3Info(String chunk, ArrayList<DS3Operation> ops_list) { ds3_copies.put(chunk, ops_list); }
}
