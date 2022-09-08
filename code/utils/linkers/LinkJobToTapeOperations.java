//===================================================================
// LinkJobToTapeOperation.java
// 	Description:
// 		Link the tape information to the tape information.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.CompletedJob;
import com.socialvagrancy.blackpearl.logs.structures.operations.TapeOperation;
import com.socialvagrancy.blackpearl.logs.structures.outputs.JobDetails;
import com.socialvagrancy.blackpearl.logs.utils.BPLogDateConverter;
import com.socialvagrancy.utils.Logger;
import com.socialvagrancy.utils.storage.UnitConverter;

import java.util.ArrayList;
import java.util.HashMap;

public class LinkJobToTapeOperations
{
	public static ArrayList<JobDetails> createDetails(CompletedJob jobs, HashMap<String, ArrayList<String>> chunk_id_map, ArrayList<TapeOperation> ops_list, Logger log)
	{
		ArrayList<JobDetails> detailed_list = new ArrayList<JobDetails>();
		HashMap<String, ArrayList<TapeOperation>> chunk_op_map = MapTapeOperationsToChunk.createMap(ops_list);
		JobDetails details;
		ArrayList<String> chunks;
		int dropped_job_count = 0;


		System.err.print("\n");
		System.err.println("Job Count: " + jobs.data.length);
		System.err.println("Chunk/ID Map size: " + chunk_id_map.size());
		System.err.println("Chunk/Ops Map size: " + chunk_op_map.size());
		
		for(int i=0; i < jobs.data.length; i++)
		{
			details = new JobDetails();
			details.job_info = jobs.data[i];
			
			details.job_info.created_at = BPLogDateConverter.formatCompletedJobsTimestamp(details.job_info.created_at);
			details.job_info.date_completed = BPLogDateConverter.formatCompletedJobsTimestamp(details.job_info.date_completed);

			details.job_duration = BPLogDateConverter.calcDuration(details.job_info.created_at, details.job_info.date_completed);

			details.human_readable_size = UnitConverter.bytesToHumanReadable(details.job_info.original_size_in_bytes);

			chunks = chunk_id_map.get(details.job_info.id);

			if(chunks != null)
			{
				for(int j=0; j < chunks.size(); j++)
				{
					details.tape_copies.put(chunks.get(j), chunk_op_map.get(chunks.get(j)));
					detailed_list.add(details);
				}
			}
			else
			{
				dropped_job_count++;
				log.WARN("Job [" + details.job_info.name + "] created at " + details.job_info.created_at + " does not have chunks listed in the logs.");
			}
		}
		
		System.err.println("Jobs without data: " + dropped_job_count);
		System.err.println("Jobs paired: " + detailed_list.size());
		System.err.print("\n");

		return detailed_list;
	}
}
