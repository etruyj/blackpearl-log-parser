//===================================================================
// DebugCheckCompletedJobCount.java
// 	Description:
// 		This pulls the report generated in the 
// 		GatherCompletedJobDetails and provides a report of the
// 		jobs that aren't included in the final report. This is
// 		for internal tests/debugging to see what we're missing.
//===================================================================

package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.CompletedJob;
import com.socialvagrancy.blackpearl.logs.structures.outputs.JobDetails;
import com.socialvagrancy.blackpearl.logs.structures.outputs.JobSummary;
import com.socialvagrancy.blackpearl.logs.utils.importers.GetCompletedJobs;

import java.util.ArrayList;

public class DebugCheckCompletedJobCount
{
	public static ArrayList<JobSummary> findMissingJobs(String dir_path)
	{
		CompletedJob jobs = GetCompletedJobs.fromJson(dir_path + "/rest/gui_ds3_completed_jobs.json");

		ArrayList<JobDetails> job_list = GatherCompletedJobDetails.forCompletedJobs(dir_path, null);

		printJobListStatus(job_list);

		return compareJobLists(jobs, job_list);	
	}

	//=======================================
	// Private Functions
	//=======================================
	
	private static ArrayList<JobSummary> compareJobLists(CompletedJob jobs, ArrayList<JobDetails> job_list)
	{
		ArrayList<JobSummary> missing_jobs = new ArrayList<JobSummary>();
		JobSummary missing;
		boolean searching = true;
		int counter;

		for(int i=0; i<jobs.jobCount(); i++)
		{
			counter = 0;
			searching = true;

			while(counter < job_list.size() && searching)
			{
				if(jobs.id(i).equals(job_list.get(counter).id()))
				{
					searching = false;
					job_list.remove(counter);

				}

				counter++;
			}

			if(searching == true)
			{
				// End of list reached.
				// Job wasn't found in the list.
				missing = new JobSummary();

				missing.setCreatedAt(jobs.createdAt(i));
				missing.setId(jobs.id(i));
				missing.setName(jobs.name(i));
				missing.setSize(jobs.originalSize(i));

				missing_jobs.add(missing);
			}
		}

		printReport(jobs, job_list, missing_jobs);

		return missing_jobs;
	}

	private static void printReport(CompletedJob jobs, ArrayList<JobDetails> job_list, ArrayList<JobSummary> missing_jobs)
	{
		// Report
		System.err.println("Jobs in BlackPearl: " + jobs.jobCount());
		System.err.println("Jobs in report: " + (jobs.jobCount() - missing_jobs.size()));
		System.err.println("Jobs not in final report: " + missing_jobs.size());
		System.err.println("Weird artifacts: " + job_list.size());
	}

	private static void printJobListStatus(ArrayList<JobDetails> job_list)
	{
		System.out.println("job_name,tape_copies,pool_copies,replication_copies");

		for(int i=0; i<job_list.size(); i++)
		{
			System.out.println(job_list.get(i).name() + "," + job_list.get(i).tapeCopyCount() + "," + job_list.get(i).poolCopyCount() + "," + job_list.get(i).ds3CopyCount());
		}
	}
}
