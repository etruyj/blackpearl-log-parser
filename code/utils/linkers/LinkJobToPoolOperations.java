//===================================================================
// LinkJobToPoolOperation.java
// 	Description:
// 		Links ArrayList<JobDetails> to ArrayList<PoolOperation>
// 		via a HashMap<String, ArrayList<PoolOperation>. This task
// 		is performed after tape operations are attached as tape
// 		operations are predominately the primary piece of
// 		information the user is looking for.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.CompletedJob;
import com.socialvagrancy.blackpearl.logs.structures.operations.PoolOperation;
import com.socialvagrancy.blackpearl.logs.structures.outputs.JobDetails;
import com.socialvagrancy.utils.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class LinkJobToPoolOperations
{
	public static ArrayList<JobDetails> addPools(ArrayList<JobDetails> details_list, HashMap<String, ArrayList<String>> chunk_id_map, ArrayList<PoolOperation> ops_list, Logger log)
	{
		HashMap<String, ArrayList<PoolOperation>> ops_map = MapPoolOperationsToChunk.createMap(ops_list);
		ArrayList<String> chunks;
		int no_pool_job_count = 0;
		int chunk_count = 0;

		System.err.println("Pool list size: " + ops_list.size());
		System.err.println("Pool Map Size: " + ops_map.size());

		for(int i=0; i < details_list.size(); i++)
		{
			chunks = chunk_id_map.get(details_list.get(i).job_info.id);

			if(chunks != null)
			{
				chunk_count += chunks.size();

				for(int j=0; j < chunks.size(); j++)
				{
					if(ops_map.get(chunks.get(j)) != null)
					{
						details_list.get(i).addPoolInfo(chunks.get(j), ops_map.get(chunks.get(j)));
					}
					else
					{
						no_pool_job_count++;
						log.WARN("No pool copies exist for job [" 
								+ details_list.get(i).job_info.name + "] that was created at "
								+ details_list.get(i).job_info.created_at);
					}
				}
			}
		}

		System.err.println("Job Size: " + details_list.size() + "\t" + "Chunk count: " + chunk_count);
		System.err.println("Jobs without pool copies: " + no_pool_job_count);
		System.err.println("\n");

		return details_list;
	}
}
