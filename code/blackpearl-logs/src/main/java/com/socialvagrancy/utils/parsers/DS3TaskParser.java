//===================================================================
// DS3TaskParser.java
// 	Description:
// 		Parses the dataplanner-main.log for information
// 		related to the ds3 replication.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.parsers;

import com.socialvagrancy.blackpearl.logs.structures.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class DS3TaskParser extends DataplannerParser
{

	//=======================================
	// Parsers
	//=======================================

	@Override
	public void parseLine(String line)
	{
		String read_search = "ReadChunkFromDs3TargetTask#";
		String write_search = "WriteChunkToDs3TargetTask#";
	
		String task_id;
		Task task;

		if(line.contains(read_search) || line.contains(write_search))
		{
			if(line.contains(read_search))
			{
				System.err.println("WARN: No logic to parse read inputs.");
			}
			else if(line.contains(write_search))
			{
				task_id = searchTaskID(line, write_search);

				task = getTask(task_id);
				task.type = "PUT";
				task.id = task_id;

				task = parseWrite(line, task);

				if(!task.type.equals("skip"))
				{
					updateTask(task_id, task);
				}
			}
		}
	}

	public Task parseWrite(String line, Task task)
	{
		String search_start = "Enqueued";
		String search_end = "Task has finished executing";
		String search_target = "succeeded to check if chunk";
		String search_target_id = "Will use target: ";

		if(line.contains(search_start))
		{
			task.sd_copy.get(task.copies).created_at = searchTimestamp(line);

			// Chunk ID
			task.chunk_id = searchChunk(line);
		}
		else if(line.contains(search_end))
		{
			task.sd_copy.get(task.copies).date_completed = searchTimestamp(line);
		}
		else if(line.contains(search_target))
		{
			// Target Name
			task.sd_copy.get(task.copies).target = searchTargetName(line);
		}
		else if(line.contains(search_target_id))
		{
			// Target ID
			task.sd_copy.get(task.copies).target_id = searchTargetID(line);
		}
		else
		{
			task.type = "skip";
		}

		return task;
	}

	//=======================================
	// Functions
	//=======================================

	private String[] searchChunk(String line)
	{
		String[] chunks = new String[1];
		String chunk_start = "[Write Chunk ";
		String chunk_end = "] for execution";

		chunks[0] = line.substring(line.indexOf(chunk_start) + chunk_start.length(), line.indexOf(chunk_end));

		return chunks;
	}

	private String searchTargetID(String line)
	{
		String target_id;
		String id_start = "Will use target: ";
		String id_end = " (BaseTargetTask";

		target_id = line.substring(line.indexOf(id_start) + id_start.length(), line.indexOf(id_end));
		
		return target_id;
	}

	private String searchTargetName(String line)
	{
		String target_name;
		String name_start = "On ";
		String name_end = ", succeeded";

		target_name = line.substring(line.indexOf(name_start) + name_start.length(), line.indexOf(name_end));

		return target_name;
	}
}
