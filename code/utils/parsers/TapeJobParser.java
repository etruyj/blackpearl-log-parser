//===================================================================
// TapeJobParser.java
// 	Description:
// 		Parses the lines of the var.log.tape_backend.log
// 		to create a linker between jobs and the job.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.parsers;

import com.socialvagrancy.blackpearl.logs.structures.TapeJob;

import java.util.ArrayList;
import java.util.HashMap;

public class TapeJobParser implements ParserInterface
{
	ArrayList<TapeJob> job_list;
	HashMap<String, TapeJob> job_map;

	public TapeJobParser()
	{
		job_list = new ArrayList<TapeJob>();
		job_map = new HashMap<String, TapeJob>();
	}

	//=======================================
	// Parsers
	//=======================================
	
	@Override
	public void parseLine(String line)
	{
		String blob_search = "BlobIO";
		String completed_read_search = "Completed readData";
		String completed_write_search = "Completed writeData";
		String drive_search = "TapeDrive:";
		String start_read_search = "Processing readData";
		String start_write_search = "Processing writeData";
		boolean skip_update = false;

		if(line.contains(drive_search))
		{
			String drive_wwn = searchTapeDrive(line, line.indexOf(drive_search) + drive_search.length());
			TapeJob job = getTapeJob(drive_wwn);

			if(line.contains(blob_search))
			{
				job.blob = searchBlob(line, job.blob);
				job.request_type = searchRequestType(line);
			}
			else if(line.contains(completed_read_search) || line.contains(completed_write_search))
			{
				job.end_time = searchTimestamp(line);
				job.duration = searchDuration(line);
			}
			else if(line.contains(start_read_search) || line.contains(start_write_search))
			{
				job.start_time = searchTimestamp(line);
			}
			else
			{
				skip_update = true;
			}

			if(!skip_update)
			{
				updateJobMap(job);
			}
		}
	}
	
	public ArrayList<String> searchBlob(String line, ArrayList<String> blob_list)
	{
		String blob_search = "blobid=";
		String blob;

		if(line.contains(blob_search))
		{
			blob = line.substring(line.indexOf(blob_search) + blob_search.length(), line.length());
			blob = blob.substring(0, blob.indexOf(" "));
			blob_list.add(blob);
		}
		else
		{
			blob = "WARNING NO BLOB FOUND";
		}	

		return blob_list;
	}

	public String searchDuration(String line)
	{
		// Add + 1 to the lastIndexOf() to remove the leading space.
		String dur = line.substring(line.lastIndexOf(" ") + 1, line.length());

		return dur;
	}

	public String searchRequestType(String line)
	{
		if(line.contains("Read"))
		{
			return "GET";
		}
		else if(line.contains("Write"))
		{
			return "PUT";
		}
		else
		{
			return "UNEXPECTED RESULT";
		}
	}

	public String searchTapeDrive(String line, int index)
	{
		String drive = line.substring(index, line.length());
		
		if(drive.contains(":"))
		{
			drive = drive.substring(0, drive.indexOf(":"));
		}
		else
		{
			drive = drive.substring(0, drive.indexOf(")"));
		}
		
		return drive;
	}

	public String searchTimestamp(String line)
	{
		String[] line_parts = line.split(" ");
		String timestamp;

		if(line_parts[1].length() == 0)
		{
			// If the day is < 10, there is an extra blank space that needs to be
			// replaced with a 0.
			timestamp = line_parts[0] + " 0" + line_parts[2] + " " + line_parts[3];
		}
		else
		{
			timestamp = line_parts[0] + " " + line_parts[1] + " " + line_parts[2];
		}

		return timestamp;
	}

	//=======================================
	// Getters
	//=======================================
	
	public ArrayList<TapeJob> getJobList()
	{
		return job_list;
	}

	public TapeJob getTapeJob(String drive_wwn)
	{
		if(job_map.get(drive_wwn)==null)
		{
			TapeJob job = new TapeJob();
			job.drive_wwn = drive_wwn;
			return job;
		}
		else
		{
			return job_map.get(drive_wwn);
		}
	}

	//=======================================
	// Functions
	//=======================================
	
	private void addJobToList(String drive_wwn)
	{
		job_list.add(job_map.get(drive_wwn));
	}

	public void updateJobMap(TapeJob job)
	{
		if(job.end_time == null)
		{
			job_map.put(job.drive_wwn, job);
		}
		else
		{
			job_map.remove(job.drive_wwn);
			job_list.add(job);
		}
	}
}
