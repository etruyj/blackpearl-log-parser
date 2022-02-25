//===================================================================
// GetTapeJobs.java
// 	Description: Parse logs for start/end time for read/write
// 	jobs. Also gathers blobs with the associated job.
//===================================================================

package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.TapeJob;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GetTapeJobs
{
	public static ArrayList<TapeJob> fromTapeBackend(String path)
	{
		File file = new File(path);

		if(file.exists())
		{
			HashMap<String, TapeJob> job_map = new HashMap<String, TapeJob>();
			ArrayList<TapeJob> job_list = new ArrayList<TapeJob>();
			TapeJob job;

			try
			{
				BufferedReader br = new BufferedReader(new FileReader(file));

				String line = null;
				String[] parse_values;

				while((line = br.readLine()) != null)
				{
					parse_values = line.split(":");
					
					if(parse_values.length > 5 && parse_values[5].trim().equals("TapeDrive"))
					{
						job = parseLine(line);

						if(job != null)
						{
							job = consolidateJob(job, job_map);

							if(job != null)
							{
								job_list.add(job);
							}
						}
					}
				}
			}
			catch(IOException e)
			{
				System.err.println(e.getMessage());

				return null;
			}
		
			return job_list;
		}
		else
		{
			System.err.println("ERROR: file " + path + " does not exist");

			return null;
		}

	}

	public static TapeJob consolidateJob(TapeJob job, HashMap<String, TapeJob> job_map)
	{
		if(job.start_time != null)
		{
			job_map.put(job.drive_sn, job);
		}
		else if(job.end_time != null)
		{
			if(job_map.get(job.drive_sn) != null)
			{
				job.start_time = job_map.get(job.drive_sn).start_time;
				job.blob = job_map.get(job.drive_sn).blob;
			
				// Clear old entry from map.
				job_map.remove(job.drive_sn);
			}

			return job;
		}
		else
		{
			TapeJob temp = job;
			
			if(job_map.get(job.drive_sn) != null)
			{
				temp = job_map.get(job.drive_sn);
				temp.blob.add(job.blob.get(0));
			}
			

			job_map.put(temp.drive_sn, temp);
		}

		// Empty set for incomplete.
		return null;
	}

	public static TapeJob parseLine(String line)
	{
		TapeJob job = null;
		String[] parse_values = line.split(":");
		String timestamp = line.substring(0, 15);
		String drive_sn = parse_values[6].trim();
		
		if(parse_values[parse_values.length-3].trim().length() > 19 &&
				(parse_values[parse_values.length-3].trim().substring(0,19).equals("Completed writeData") ||
				parse_values[parse_values.length-3].trim().substring(0,18).equals("Completed readData")))
		{
			job = populateCompleted(line);
			job.end_time = timestamp;
		}
		else if(parse_values[parse_values.length-2].trim().length() > 6 &&
				parse_values[parse_values.length-2].trim().substring(0,6).equals("BlobIO"))
		{
			job = populateBlob(line);
		}
		else if(parse_values[parse_values.length-1].trim().length() > 20 && 
				(parse_values[parse_values.length-1].trim().substring(0,20).equals("Processing writeData") ||
				parse_values[parse_values.length-1].trim().substring(0,19).equals("Processing readData")))
		{
			job = populateStart(line);
			job.start_time = timestamp;
		}

		return job;
	}

	public static TapeJob populateBlob(String line)
	{
		TapeJob job = new TapeJob();
		String[] parse_values = line.split(":");
		
		job.drive_sn = parse_values[parse_values.length-4].trim();

		if(parse_values[parse_values.length-2].trim().substring(7, 12).equals("Write"))
		{
			job.request_type = "PUT";
		}
		else if(parse_values[parse_values.length-2].trim().substring(7, 11).equals("Read"))
		{
			job.request_type = "GET";
		}

		// Get Blob
		parse_values = line.split("\\(");
		parse_values = parse_values[1].split("\\)");
		
		parse_values = parse_values[0].split("/");
		
		job.blob.add(parse_values[parse_values.length-1]);

		return job;
	}

	public static TapeJob populateCompleted(String line)
	{
		TapeJob job = new TapeJob();
		String[] parse_values = line.split(":");

		job.drive_sn = parse_values[parse_values.length-5].trim();

		if(parse_values[parse_values.length-3].trim().substring(10,15).equals("write"))
		{
			job.request_type = "PUT";
		}
		else if(parse_values[parse_values.length-3].trim().substring(10,14).equals("read"))
		{
			job.request_type = "GET";
		}

		job.duration = parse_values[parse_values.length-3].substring(parse_values[parse_values.length-3].length()-2, parse_values[parse_values.length-3].length()) +
			       ":" + parse_values[parse_values.length-2] + ":" + parse_values[parse_values.length-1];	

		
		parse_values = line.split("<");
		parse_values = parse_values[parse_values.length-1].split(">");

		job.size = parse_values[0];

		return job;		
	}

	public static TapeJob populateStart(String line)
	{
		TapeJob job = new TapeJob();
		String[] parse_values = line.split(":");

		job.drive_sn = parse_values[parse_values.length-3].trim();

		if(parse_values[parse_values.length-1].trim().substring(11,16).equals("write"))
		{
			job.request_type = "PUT";
		}
		else if(parse_values[parse_values.length-1].trim().substring(11,15).equals("read"))
		{
			job.request_type = "GET";
		}

		parse_values = line.split("<");
		parse_values = parse_values[parse_values.length-1].split(">");

		job.size = parse_values[0];
		
		return job;
	}

	public static void print(ArrayList<TapeJob> job_list)
	{
		for(int i=0; i<job_list.size(); i++)
		{
			System.out.println("Job [" + i + "] [" + job_list.get(i).drive_sn + "] " +
					job_list.get(i).request_type +
					" <" + job_list.get(i).size + "> Started at " +
					job_list.get(i).start_time + " and completed at " +
					job_list.get(i).end_time + 
					" contains (" + job_list.get(i).blob.size() + ") blobs");
		}
	}
}
