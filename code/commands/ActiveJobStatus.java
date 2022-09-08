//===================================================================
// ActiveJobStatus.java
// 	Description:
// 		Determines the status of active jobs in the system.
//===================================================================

package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.CompletedJob;
import com.socialvagrancy.blackpearl.logs.structures.operations.DS3Operation;
import com.socialvagrancy.blackpearl.logs.structures.operations.PoolOperation;
import com.socialvagrancy.blackpearl.logs.structures.operations.TapeOperation;
import com.socialvagrancy.blackpearl.logs.structures.outputs.ActiveJob;
import com.socialvagrancy.blackpearl.logs.structures.outputs.Bucket;
import com.socialvagrancy.blackpearl.logs.structures.outputs.JobDetails;
import com.socialvagrancy.blackpearl.logs.utils.importers.GetCompletedJobs;
import com.socialvagrancy.blackpearl.logs.utils.importers.GetJobIDtoChunkMap;
import com.socialvagrancy.blackpearl.logs.utils.linkers.GenerateActiveJobs;
import com.socialvagrancy.blackpearl.logs.utils.linkers.GenerateDS3Operations;
import com.socialvagrancy.blackpearl.logs.utils.linkers.GeneratePoolOperations;
import com.socialvagrancy.blackpearl.logs.utils.linkers.GenerateTapeOperations;
import com.socialvagrancy.blackpearl.logs.utils.linkers.LinkJobToDS3Operations;
import com.socialvagrancy.blackpearl.logs.utils.linkers.LinkJobToPoolOperations;
import com.socialvagrancy.blackpearl.logs.utils.linkers.LinkJobToTapeOperations;
import com.socialvagrancy.utils.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class ActiveJobStatus
{
	public static ArrayList<ActiveJob> fromRest(String dir_path)
	{
		boolean debugging = false;
		Logger log = new Logger("../logs/bp_logs.log", 102400, 5, 1);
		CompletedJob jobs = GetCompletedJobs.fromJson(dir_path + "/rest/gui_ds3_active_jobs.json");
		ArrayList<TapeOperation> ops_list = GenerateTapeOperations.fromLogs(dir_path, 8, 14, log, debugging);
		ArrayList<PoolOperation> pool_ops_list = GeneratePoolOperations.fromLogs(dir_path, 8, log, debugging);
		ArrayList<DS3Operation> ds3_ops_list = GenerateDS3Operations.fromLogs(dir_path, 8, log, debugging);
		HashMap<String, ArrayList<String>> id_chunk_map = GetJobIDtoChunkMap.fromDataplanner(dir_path, 8, log, debugging);
		ArrayList<JobDetails> details_list = LinkJobToTapeOperations.createDetails(jobs, id_chunk_map, ops_list, log);
		details_list = LinkJobToPoolOperations.addPools(details_list, id_chunk_map, pool_ops_list, log);
		details_list = LinkJobToDS3Operations.addDS3(details_list, id_chunk_map, ds3_ops_list, log);

		ArrayList<Bucket> bucket_list = ListBuckets.fromRest(dir_path);
		
		ArrayList<ActiveJob> job_list = GenerateActiveJobs.fromLogset(details_list, bucket_list, id_chunk_map);

		return job_list;
	}

	public static void testPrint(ArrayList<ActiveJob> job_list)
	{
		System.out.println("job_name,request_type,bucket,owner,created_at,%_complete,state");

		for(int i=0; i<job_list.size(); i++)
		{
			System.out.print(job_list.get(i).name + ",");
			System.out.print(job_list.get(i).request_type + ",");
			System.out.print(job_list.get(i).bucket + ",");
			System.out.print(job_list.get(i).owner + ",");
			System.out.print(job_list.get(i).created_at + ",");
			System.out.print(job_list.get(i).percent_complete + "%,");
			System.out.println(job_list.get(i).currentStatus());
		}
	}
}
