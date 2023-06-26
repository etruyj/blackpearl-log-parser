//===================================================================
// TapeTaskParser.java
// 	Description:
// 		Designed to read the lines of the dataplanner-main.log
// 		for events associated with tape tasks.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.parsers;

import com.socialvagrancy.blackpearl.logs.structures.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TapeTaskParser extends DataplannerParser
{
	String write_task = "WriteChunkToTapeTask#";
	String read_task = "ReadChunkFromTapeTask#";
	
	//=======================================
	// Line Parsing
	//=======================================

	@Override
	public void parseLine(String line)
	{
		String task_id;
		Task task;

		if(line.contains(write_task) || line.contains(read_task))
		{

			if(line.contains(read_task))
			{
				task_id = searchTaskID(line, read_task);
			
				task = getTask(task_id);	
			
				task.id = task_id;	
				task.type = "GET";
				task = parseRead(line, task);
				
				// Don't update if no new info.
				if(!task.type.equals("skip"))
				{
					updateTask(task_id, task);
				}
			}
			else if(line.contains(write_task))
			{
				task_id = searchTaskID(line, write_task);
		
				task = getTask(task_id);

				task.id = task_id;	
				task.type = "PUT";
				task = parseWrite(line, task);
				
				// Don't update if no new info.
				if(!task.type.equals("skip"))
				{
					updateTask(task_id, task);
				}
			}

		}

	}

	public Task parseRead(String line, Task task)
	{
		String completion_search = "Task has finished executing";
		String chunk_search = "SQL: UPDATE ds3.job_chunk ";
		String creation_search = "Locked Tape Drive";
		String drive_search = "RPC TapeDrive$";
		String throughput_search = "from tape at ";

		if(line.contains(drive_search))
		{
			task.sd_copy.get(task.copies).target = searchDriveWWN(line, line.indexOf(drive_search) + drive_search.length());
		}
		else if(line.contains(chunk_search))
		{
			task.chunk_id = searchChunkID(line, task.chunk_id);
		}
		else if(line.contains(completion_search))
		{
			task.sd_copy.get(task.copies).date_completed = searchTimestamp(line);
		}
		else if(line.contains(throughput_search))
		{
			task.sd_copy.get(task.copies).throughput = searchThroughput(line, line.indexOf(throughput_search) + throughput_search.length());
			
			// Grab the job size as well, since this is in the same line.
			task.sd_copy.get(task.copies).size = searchSize(line);
		}
		else if(line.contains(creation_search))
		{
			task.sd_copy.get(task.copies).created_at = searchTimestamp(line);
		}
		else
		{
			// No updates to task.
			task.type = "skip";
		}

		return task;
	}

	public Task parseWrite(String line, Task task)
	{
		String completion_search = "Task has finished executing";
		String chunk_search = "SQL: UPDATE ds3.job_chunk ";
		String chunk_search_2 = "chunks in one task: ";
		String creation_search = "Locked Tape Drive";
		String drive_search = "RPC TapeDrive$";
		String throughput_search = "quiesced to tape";
		String reaggregation_search = "since it can be re-aggregated.";
		String storage_domain_search = "storage domains remaining";

		if(line.contains(creation_search))
		{
			task.sd_copy.get(task.copies).created_at = searchTimestamp(line);
		}
		else if(line.contains(chunk_search) && task.chunk_id == null)
		{
			task.chunk_id = searchChunkID(line, task.chunk_id);
		}
		else if(line.contains(chunk_search_2) && task.chunk_id == null)
		{
			task.chunk_id = searchServicingChunks(line);
		}
		else if(line.contains(drive_search))
		{
			task.sd_copy.get(task.copies).target = searchDriveWWN(line, line.indexOf(drive_search) + drive_search.length());
		}
		else if(line.contains(completion_search))
		{
			task.sd_copy.get(task.copies).date_completed = searchTimestamp(line);
		}
		else if(line.contains(throughput_search))
		{
			String sub_search = "effectively ";
			task.sd_copy.get(task.copies).throughput = searchThroughput(line, line.indexOf(sub_search) + sub_search.length());
			
			// Grab the job size as well, since this is in the same line.
			task.sd_copy.get(task.copies).size = searchSize(line);
		}
		else if(line.contains(reaggregation_search))
		{
			// The task was removed and the chunks are added to a different tape task.
			// Not sure how to filter these out at this stage.
			// Right now, I'll delete the chunks from the task since I have a flag to 
			// filter out chunk-less tasks.
			
			//task.chunk_id = null;
			
			// Process 2 for tracking purposes
			task.sd_copy.get(task.copies).date_completed = searchTimestamp(line);
		}
		else if(line.contains(storage_domain_search))
		{
			// Mark the first task as completed before starting to store the next task.
			// If there are multiple tape copies it looks like this message comes
			// just before the task is listed as complete.
			if(task.sd_copy.get(task.copies).date_completed == null)
			{
				task.sd_copy.get(task.copies).date_completed = searchTimestamp(line);
			}

			task.copies++;
			task.nextCopy();
		}
		else
		{
			// No updates to task
			task.type = "skip";
		}
	
		return task;
	}

	private String[] searchChunkID(String line, String[] chunks)
	{
		// Add the chunk id to the list of chunk_ids for the task.
		
		// Create a new array 1 index larger than the previous 
		// to accommodate the new chunk.
		String[] id_set;
		int chunk_index;

		if(chunks != null)
		{
			id_set = new String[chunks.length + 1];
			chunk_index = chunks.length;
		}
		else
		{
			id_set = new String[1];
			chunk_index = 0;
		}

		// Copy the old array to the new
		for(int i=0; i<chunk_index; i++)
		{
			id_set[i] = chunks[i];
		}

		// Find the new chunk ID
		// this is couched in a WHERE SQL clause
		// WHERE (id = '')
		String id_search = "id = '";

		String chunk_id = line.substring(line.lastIndexOf(id_search) + id_search.length(), line.lastIndexOf("'"));

		// Add the chunk_id to the list
		id_set[chunk_index] = chunk_id;

		return id_set;
	}

	private String[] searchServicingChunks(String line)
	{
		String start = "task: ";
		String end = ".";
		String chunk_list = line.substring(line.indexOf(start) + start.length() + 2, line.indexOf(end) + end.length() - 3);
		String[] chunks = chunk_list.split(", ");
		
		return chunks;
	}

	private String searchDriveWWN(String line, int start_index)
	{
		// Return the drive sn for the task.
		// In the format of TapeDrive$#######.
		String drive_sn = line.substring(start_index, line.indexOf("."));
		return drive_sn; 
	}


}
