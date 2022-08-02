//===================================================================
// CalcJobStats.java
// 	Description:
// 		Provides an average of PUT/GET job statistics.
//===================================================================

package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.CompletedJob;
import com.socialvagrancy.blackpearl.logs.structures.operations.DS3Operation;
import com.socialvagrancy.blackpearl.logs.structures.operations.PoolOperation;
import com.socialvagrancy.blackpearl.logs.structures.operations.TapeOperation;
import com.socialvagrancy.blackpearl.logs.structures.outputs.JobDetails;
import com.socialvagrancy.blackpearl.logs.utils.importers.GetCompletedJobs;
import com.socialvagrancy.blackpearl.logs.utils.importers.GetJobIDtoChunkMap;
import com.socialvagrancy.blackpearl.logs.utils.linkers.GenerateDS3Operations;
import com.socialvagrancy.blackpearl.logs.utils.linkers.GeneratePoolOperations;
import com.socialvagrancy.blackpearl.logs.utils.linkers.GenerateTapeOperations;
import com.socialvagrancy.blackpearl.logs.utils.linkers.LinkJobToDS3Operations;
import com.socialvagrancy.blackpearl.logs.utils.linkers.LinkJobToPoolOperations;
import com.socialvagrancy.blackpearl.logs.utils.linkers.LinkJobToTapeOperations;
import com.socialvagrancy.blackpearl.logs.utils.linkers.MapTapeOperationsToChunk;
import com.socialvagrancy.utils.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class CalcJobStats
{
	public static ArrayList<JobDetails> forCompletedJobs(String dir_path, Logger log)
	{
		boolean debugging = false;
		log = new Logger("../logs/bp_logs.log", 102400, 5, 1);
		String jobs_path = "rest/gui_ds3_completed_jobs.json";
		CompletedJob jobs = GetCompletedJobs.fromJson(dir_path + jobs_path);
		ArrayList<TapeOperation> ops_list = GenerateTapeOperations.fromLogs(dir_path, 8, 14, log, debugging);
		ArrayList<PoolOperation> pool_ops_list = GeneratePoolOperations.fromLogs(dir_path, 8, log, debugging);
		ArrayList<DS3Operation> ds3_ops_list = GenerateDS3Operations.fromLogs(dir_path, 8, log, debugging);
		HashMap<String, ArrayList<String>> id_chunk_map = GetJobIDtoChunkMap.fromDataplanner(dir_path, 8, log, debugging); 
		ArrayList<JobDetails> details_list = LinkJobToTapeOperations.createDetails(jobs, id_chunk_map, ops_list, log);
		details_list = LinkJobToPoolOperations.addPools(details_list, id_chunk_map, pool_ops_list, log);
		details_list = LinkJobToDS3Operations.addDS3(details_list, id_chunk_map, ds3_ops_list, log);

		testPrint(details_list);

		return details_list;

	}

	public static void testPrint(ArrayList<JobDetails> details_list)
	{
		System.out.println("job_name, request_type, created_at, date_completed, chunk, task_name, task_id, drive_sn, barcode, task_start, task_end, drive_loaded, exchange_start, exhange_end, write_start, write_end, size, bandwidth");

		for(int i=0; i < details_list.size(); i++)
		{
			for(String chunk : details_list.get(i).tape_copies.keySet())
			{
				// Tape Copies

				for(int j=0; j< details_list.get(i).tapeCopyCount(chunk); j++)
				{
					System.out.print(details_list.get(i).job_info.name + ",");
					System.out.print(details_list.get(i).job_info.request_type + ",");
					System.out.print(details_list.get(i).job_info.created_at + ",");
					System.out.print(details_list.get(i).job_info.date_completed + ",");
					System.out.print(chunk + ",");
					System.out.print("Tape Copy " + j + ",");
					System.out.print(details_list.get(i).tape_copies.get(chunk).get(j).id + ",");
					System.out.print(details_list.get(i).tape_copies.get(chunk).get(j).drive_wwn + ",");
					System.out.print(details_list.get(i).tape_copies.get(chunk).get(j).barcode + ",");
					System.out.print(details_list.get(i).tape_copies.get(chunk).get(j).task_created + ",");
					System.out.print(details_list.get(i).tape_copies.get(chunk).get(j).task_completed + ",");

					if(details_list.get(i).tape_copies.get(chunk).get(j).already_in_drive)
					{
						System.out.print("true,");
					}
					else
					{
						System.out.print("false,");
					}

					System.out.print(details_list.get(i).tape_copies.get(chunk).get(j).mount_start + ",");
					System.out.print(details_list.get(i).tape_copies.get(chunk).get(j).mount_end + ",");
					System.out.print(details_list.get(i).tape_copies.get(chunk).get(j).rw_start_time + ",");
					System.out.print(details_list.get(i).tape_copies.get(chunk).get(j).rw_end_time + ",");
					System.out.print(details_list.get(i).tape_copies.get(chunk).get(j).task_size + ",");
					System.out.println(details_list.get(i).tape_copies.get(chunk).get(j).task_throughput);
				}

				// Pool Copies
				
				for(int k=0; k < details_list.get(i).poolCopyCount(chunk); k++)
				{
					System.out.print(details_list.get(i).job_info.name + ",");
					System.out.print(details_list.get(i).job_info.request_type + ",");
					System.out.print(details_list.get(i).job_info.created_at + ",");
					System.out.print(details_list.get(i).job_info.date_completed + ",");
					System.out.print(chunk + ",");
					System.out.print("Pool Copy " + k + ",");

					System.out.print(details_list.get(i).pool_copies.get(chunk).get(k).task_id + ",");
					System.out.print(details_list.get(i).pool_copies.get(chunk).get(k).pool_name + ",");
					System.out.print(","); // barcode field [skip]
					System.out.print(details_list.get(i).pool_copies.get(chunk).get(k).created_at + ",");
					System.out.print(details_list.get(i).pool_copies.get(chunk).get(k).date_completed + ",");
					System.out.print(","); // Already in drive
					System.out.print(","); // Mount start
					System.out.print(","); // mount end
					System.out.print(","); // rw_start
					System.out.print(","); // rw_end
					System.out.print(details_list.get(i).pool_copies.get(chunk).get(k).size + ",");
					System.out.println(details_list.get(i).pool_copies.get(chunk).get(k).throughput);

				}

				// DS3 Copies

				for(int l=0; l < details_list.get(i).ds3CopyCount(chunk); l++)
				{
				
					System.out.print(details_list.get(i).job_info.name + ",");
					System.out.print(details_list.get(i).job_info.request_type + ",");
					System.out.print(details_list.get(i).job_info.created_at + ",");
					System.out.print(details_list.get(i).job_info.date_completed + ",");
					System.out.print(chunk + ",");
					System.out.print("DS3 Copy " + l + ",");

					System.out.print(details_list.get(i).ds3_copies.get(chunk).get(l).id + ",");
					System.out.print(details_list.get(i).ds3_copies.get(chunk).get(l).target_name + ",");
					System.out.print(","); // barcode field [skip]
					System.out.print(details_list.get(i).ds3_copies.get(chunk).get(l).created_at + ",");
					System.out.print(details_list.get(i).ds3_copies.get(chunk).get(l).date_completed + ",");
					System.out.print(","); // Already in drive
					System.out.print(","); // Mount start
					System.out.print(","); // mount end
					System.out.print(","); // rw_start
					System.out.print(","); // rw_end
					System.out.print(","); // size
					System.out.print("\n"); // throughput
				}
			}
		}
	}
}
