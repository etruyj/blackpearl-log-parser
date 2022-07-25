//===================================================================
// PoolTaskParser.java
// 	Description:
// 		Parses the dataplanner-main.logs for information on 
// 		pool copies.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.parsers;

import com.socialvagrancy.blackpearl.logs.structures.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class PoolTaskParser implements ParserInterface
{
	ArrayList<Task> task_list;
	HashMap<String, Task> task_map;
	String write_task = "WriteChunkToPoolTask";
	String read_task = "ReadChunkFromPoolTask";

	public PoolTaskParser()
	{
		task_list = new ArrayList<Task>();
		task_map = new HashMap<String, Task>();
	}

	//=======================================
	// LINE PARSING
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

				//System.err.println(line);
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

	public Task parseWrite(String line, Task task)
	{
		String search_start = "Enqueued " + task.id;
		String search_end = "Dequeued " + task.id;
		String search_pool = "Write lock acquired on pool";
		String search_throughput = "written to pool at";

		if(line.contains(search_start))
		{
			// Created_at
			task.sd_copy.get(task.copies).created_at = searchTimestamp(line);
			
			// Chunk
			task.chunk_id = searchChunks(line);
		}
		else if(line.contains(search_end))
		{
			// Date_completed
			task.sd_copy.get(task.copies).date_completed = searchTimestamp(line);
		}
		else if(line.contains(search_pool))
		{
			// pool_id
			task.sd_copy.get(task.copies).target = searchPoolID(line);
		}
		else if(line.contains(search_throughput))
		{
			// Throughput
			task.sd_copy.get(task.copies).throughput = searchThroughput(line);
			
			// Size
			task.sd_copy.get(task.copies).size = searchSize(line);
		}
		else
		{
			task.type = "skip";
		}

		return task;
	}

	//======================================
	// SEARCH FUNCTIONS
	//======================================

	private String[] searchChunks(String line)
	{
		String chunk_start = "Write Chunk ";
		String chunk_end = "] for execution";
		String[] chunks = new String[1];

		chunks[0] = line.substring(line.indexOf(chunk_start) + chunk_start.length(), line.indexOf(chunk_end));

		return chunks;
	}

	private String searchPoolID(String line)
	{
		String pool_start = "pool ";
		String pool_end = " by";
		String pool = line.substring(line.indexOf(pool_start) + pool_start.length(), line.indexOf(pool_end));

		return pool;
	}

	private String searchSize(String line)
	{
		// Return the size of the task being completed.
		String task_size;
		String[] line_parts = line.split("\\(");
		line_parts = line_parts[1].split("\\)");
		task_size = line_parts[0];
		
		return task_size;
	}

	private String searchTaskID(String line, String task_type)
	{
		String[] line_parts;
		String task_id = line.substring(line.indexOf(task_type), line.length());

		line_parts = task_id.split("\\]");
		line_parts = line_parts[0].split("\\[");
		task_id = line_parts[0];

		return task_id;
	}

	private String searchThroughput(String line)
	{
		String search_start = "at ";
		String search_end = ".";
		String throughput = line.substring(line.indexOf(search_start) + search_start.length(), line.indexOf(search_end));

		return throughput;
	}

	private String searchTimestamp(String line)
	{
		// Returns the MMM dd HH:mm:ss timestamp from the
		// log line.
		String[] line_parts = line.split(" ");
		String timestamp = line_parts[1] + " " + line_parts[2];
		line_parts = line_parts[3].split(",");
		timestamp += " " + line_parts[0];

		return timestamp;
	}

	//=======================================
	// FUNCTIONS
	//=======================================

	private ArrayList<Task> buildTaskList()
	{
		ArrayList<Task> task_list = new ArrayList<Task>();

		for(String key : task_map.keySet())
		{
			task_list.add(task_map.get(key));
		}

		return task_list;
	}

	private Task getTask(String task_id)
	{
		Task task = task_map.get(task_id);

		if(task == null)
		{
			task = new Task();
		}

		return task;
	}

	public ArrayList<Task> getTaskList() 
	{ 
		task_list.addAll(buildTaskList());
		return task_list;
       	}

	private void updateTask(String task_id, Task task)
	{
		// Double-check this field.
		if(!task.type.equals("skip"))
		{
			task_map.put(task_id, task);
		}
	}
}

