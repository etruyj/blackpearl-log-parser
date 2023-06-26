//===================================================================
// GatherCompletedJobDetails.java
// 	Description:
// 		Parses the logs for operations related to the completed
// 		jobs.
// 		
// 		Tasks Tracked:
// 			- tape operations
// 			- pool operations
// 			- ds3 replications operations.
//===================================================================

package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.CompletedJob;
import com.socialvagrancy.blackpearl.logs.structures.operations.DS3Operation;
import com.socialvagrancy.blackpearl.logs.structures.operations.PoolOperation;
import com.socialvagrancy.blackpearl.logs.structures.operations.TapeOperation;
import com.socialvagrancy.blackpearl.logs.structures.outputs.Bucket;
import com.socialvagrancy.blackpearl.logs.structures.outputs.JobDetails;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiTapeLibraryPartitions;
import com.socialvagrancy.blackpearl.logs.utils.importers.GetCompletedJobs;
import com.socialvagrancy.blackpearl.logs.utils.importers.GetJobIDtoChunkMap;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetTapePartitions;
import com.socialvagrancy.blackpearl.logs.utils.linkers.GenerateDS3Operations;
import com.socialvagrancy.blackpearl.logs.utils.linkers.GeneratePoolOperations;
import com.socialvagrancy.blackpearl.logs.utils.linkers.GenerateTapeOperations;
import com.socialvagrancy.blackpearl.logs.utils.linkers.LinkJobToDS3Operations;
import com.socialvagrancy.blackpearl.logs.utils.linkers.LinkJobToPoolOperations;
import com.socialvagrancy.blackpearl.logs.utils.linkers.LinkJobToTapeOperations;
import com.socialvagrancy.blackpearl.logs.utils.linkers.MapBucketToID;
import com.socialvagrancy.blackpearl.logs.utils.linkers.MapTapeOperationsToChunk;
import com.socialvagrancy.utils.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class GatherCompletedJobDetails
{
	public static ArrayList<JobDetails> forCompletedJobs(String dir_path, Logger log)
	{
		boolean debugging = false;
		log = new Logger("../logs/bp_logs.log", 102400, 5, 1);
		String jobs_path = "rest/gui_ds3_completed_jobs.json";
		CompletedJob jobs = GetCompletedJobs.fromJson(dir_path + jobs_path);
		GuiTapeLibraryPartitions tape_partitions = GetTapePartitions.fromRest(dir_path);
		ArrayList<Bucket> bucket_list = ListBuckets.fromRest(dir_path);
		ArrayList<TapeOperation> ops_list = GenerateTapeOperations.fromLogs(dir_path, 8, 14, tape_partitions, log, debugging);
		ArrayList<PoolOperation> pool_ops_list = GeneratePoolOperations.fromLogs(dir_path, 8, log, debugging);
		ArrayList<DS3Operation> ds3_ops_list = GenerateDS3Operations.fromLogs(dir_path, 8, log, debugging);
		HashMap<String, ArrayList<String>> id_chunk_map = GetJobIDtoChunkMap.fromDataplanner(dir_path, 8, log, debugging); 
		HashMap<String, Bucket> id_bucket_map = MapBucketToID.createMap(bucket_list);
		ArrayList<JobDetails> details_list = LinkJobToTapeOperations.createDetails(jobs, id_chunk_map, id_bucket_map, ops_list, log);
		details_list = LinkJobToDS3Operations.addDS3(details_list, id_chunk_map, ds3_ops_list, log);
		details_list = LinkJobToPoolOperations.addPools(details_list, id_chunk_map, pool_ops_list, log);

		//testPrint(details_list);

		return details_list;

	}
}
