//===================================================================
// GenerateActiveJobs.java
// 	Description:
// 		Takes the job details for active jobs and the bucket
// 		information to determine percent complete and next
// 		step for the jobs.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.CompletedJob;
import com.socialvagrancy.blackpearl.logs.structures.outputs.ActiveJob;
import com.socialvagrancy.blackpearl.logs.structures.outputs.Bucket;
import com.socialvagrancy.blackpearl.logs.structures.outputs.JobDetails;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class GenerateActiveJobs
{
	private static String incomplete_date = "2001 Jan 01 00:00:01";

	public static ArrayList<ActiveJob> fromLogset(ArrayList<JobDetails> details_list, ArrayList<Bucket> bucket_list, HashMap<String, ArrayList<String>> chunk_id_map)
	{
		ArrayList<ActiveJob> job_list = new ArrayList<ActiveJob>();
		HashMap<String, Bucket> bucket_id_map = MapBucketToID.createMap(bucket_list);
		HashMap<String, Integer> job_id_map = new HashMap<String, Integer>();
		ActiveJob job;

		int job_index;

		for(int i=0; i<details_list.size(); i++)
		{
			// Consolidate all job details into a single job item.
			job_index = getJobIndex(job_id_map, details_list.get(i).id());

			// Create a new job entry
			if(job_index < 0) // doesn't exist
			{
				job = createJob(job_list, job_id_map, details_list.get(i), bucket_id_map, chunk_id_map);

				job_list.add(job);
				job_index = job_list.size() - 1;

				// Add it to the job_id_map;
				job_id_map.put(job.id, job_index);
			}

		}

		return job_list;
	}

	//=======================================
	// Functions
	//=======================================

	private static int calcCachedPercent(String written_to_cache, String total_size, int cache_percent)
	{
		BigDecimal written = new BigDecimal(written_to_cache);
		BigDecimal total = new BigDecimal(total_size);
		BigDecimal copy_percent = new BigDecimal(cache_percent);
		BigDecimal percent = written.divide(total);
		percent = percent.multiply(copy_percent);

		return percent.intValue();
	}

	private static int calcTaskPercent(JobDetails detail, int task_percent)
	{
		int percent_completed = 0;

		for(String chunk : detail.tape_copies.keySet())
		{
			for(int i=0; i < detail.tapeCopyCount(chunk); i++)
			{
				if(!detail.tape_copies.get(chunk).get(i).task_completed.equals(incomplete_date))
				{
					percent_completed += task_percent;
				}
			}
		}

		for(String chunk : detail.pool_copies.keySet())
		{
			for(int i=0; i < detail.tapeCopyCount(chunk); i++)
			{
				if(!detail.pool_copies.get(chunk).get(i).date_completed.equals(incomplete_date))
				{
					percent_completed += task_percent;
				}
			}
		}

		for(String chunk : detail.ds3_copies.keySet())
		{
			for(int i=0; i < detail.ds3CopyCount(chunk); i++)
			{
				if(!detail.ds3_copies.get(chunk).get(i).date_completed.equals(incomplete_date))
				{
					percent_completed += task_percent;
				}
			}
		}

		return percent_completed;
	}

	private static ActiveJob createJob(ArrayList<ActiveJob> job_list, HashMap<String, Integer> job_id_map, JobDetails detail, HashMap<String, Bucket> bucket_id_map, HashMap<String, ArrayList<String>> chunk_id_map)
	{
		int copies;
		ActiveJob job = new ActiveJob();
		Bucket bucket;
		
		job.name = detail.name();
		job.id = detail.id();
		job.request_type = detail.type();
		job.chunk = chunk_id_map.get(job.id);
		job.owner = detail.owner();
		job.created_at = detail.createdAt();
	
		bucket = bucket_id_map.get(detail.bucketID());
			
		// Determine how many copies of the data needs to be made
		// All copies are +1 the number of copies specified 
		// as a copy to/from cache is also made.
		if(bucket != null)
		{
			job.bucket = bucket.name;
				
			if(job.request_type.equals("put"))
			{
				copies = bucket.copyCount() + 1;
			}
			else // it's a GET
			{
				// Copies = restore to cache + restore to client
				// this doesn't factor in restore from pool,
				// which skips the cache.
				copies = 2;
			}
		}
		else
		{
			job.bucket = "UNKNOWN";
			copies = 0;
		}

		job.percent_per_copy = 100 / copies;
		job.percent_per_chunk = job.percent_per_copy / job.chunk.size();

		job.percent_complete = calcCachedPercent(detail.cachedSize(), detail.originalSize(), job.percent_per_copy);
		job.percent_complete += calcTaskPercent(detail, job.percent_per_chunk);
		job.current_state = findCurrentStatus(detail, 4);

		return job;
	}

	private static int findCurrentStatus(JobDetails detail, int current_state)
	{
		// Working on what the job is waiting on
		// Heirarchy
		// 0 - Writing to cache
		// 1 - Writing to pool
		// 2 - Writing to tape
		// 3 - Writing to DS3
		// 4 - Pending completion
		int status = current_state;

		// Cached Copies
		BigInteger cached = new BigInteger(detail.cachedSize());
		BigInteger total = new BigInteger(detail.originalSize());

		if(cached.compareTo(total) < 0) // Check if cached is less than total
		{
			status = 0;
		}

		// Pool Copies
		for(String chunk : detail.pool_copies.keySet())
		{
			for(int i=0; i < detail.tapeCopyCount(chunk); i++)
			{
				if(detail.pool_copies.get(chunk).get(i).date_completed.equals(incomplete_date))
				{
					if(status > 1)
					{
						status = 1;
					}	
				}
			}
		}

		// Tape Copies
		for(String chunk : detail.tape_copies.keySet())
		{
			for(int i=0; i < detail.tapeCopyCount(chunk); i++)
			{
				if(detail.tape_copies.get(chunk).get(i).task_completed.equals(incomplete_date))
				{
					if(status > 2)
					{
						status = 2;
					}	
				}
			}
		}

		// DS3 Copies
		for(String chunk : detail.ds3_copies.keySet())
		{
			for(int i=0; i < detail.ds3CopyCount(chunk); i++)
			{
				
				if(detail.ds3_copies.get(chunk).get(i).date_completed.equals(incomplete_date))
				{
					if(status > 3)
					{
						status = 3;
					}	
				}
			}
		}

		return status;
	}

	private static int getJobIndex(HashMap<String, Integer> job_id_map, String id)
	{
		if(job_id_map.get(id) == null)
		{
			return -1;
		}
		else
		{
			return job_id_map.get(id);
		}
	}
}
