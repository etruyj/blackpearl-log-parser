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

public class PoolTaskParser extends DataplannerParser
{
	HashMap<String, String> id_name_map;
	String write_task = "WriteChunkToPoolTask";
	String read_task = "IGNORE Read lock"; // Added IGNORE to skip search. Delete from string to resume searching.
	String read_task_2 = "read_from_pool_id";
	String search_pool_name = "Pool: ";

	public PoolTaskParser()
	{
		task_list = new ArrayList<Task>();
		task_map = new HashMap<String, Task>();
		id_name_map = new HashMap<String, String>();
	}

	//=======================================
	// LINE PARSING
	//=======================================

	@Override
	public void parseLine(String line)
	{
		String task_id;
		Task task;

		if(line.contains(write_task) || line.contains(read_task) || line.contains(read_task_2))
		{
			if(line.contains(read_task) || line.contains(read_task_2))
			{
				task_id = searchRPC(line);

				task = getTask(task_id);
				task.type = "GET";

				task = parseRead(line, task);

				// Don't update if no new info
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
		else if(line.contains(search_pool_name))
		{
			String[] id_name_pair = parseName(line);
			id_name_map.put(id_name_pair[0], id_name_pair[1]);
		}
	}

	public String[] parseName(String line)
	{
		String search_pool_id = "Pool: ";
		String[] id_name_pair = new String[2];
		String temp = line.substring(line.indexOf(search_pool_id) + search_pool_id.length(), line.length());
		String[] line_parts = temp.split(" ");

		// Set Pool ID
		id_name_pair[0] = line_parts[0];

		// Find Pool Name
		line_parts = line_parts[1].split("\\(");
		line_parts = line_parts[1].split("\\)");

		id_name_pair[1] = line_parts[0];

		return id_name_pair;
	}

	public Task parseRead(String line, Task task)
	{
		String search_created = "acquired";;
		String search_completed = "released";

		if(line.contains(read_task_2))
		{
			// Chunk
			task.chunk_id = searchSQLForChunk(line);
			
			// Pool ID
			task.sd_copy.get(task.copies).target_id = searchSQLForPoolID(line);
		}
/*		REMOVED FROM THE LOGIC
 *			NOT SURE HOW TO ATTACH THESE DATA POINTS TO
 *			THE OVERARCHING READ TASK.
 *
 * 		else if(line.contains(read_task))
		{
			if(line.contains(search_created))
			{
				// created at
				task.sd_copy.get(task.copies).created_at = searchTimestamp(line);

				// Pool ID
			}
			else if(line.contains(search_completed))
			{
				// date completed
				task.sd_copy.get(task.copies).date_completed = searchTimestamp(line);
				
				// Pool ID
			}
		}
*/
		else
		{
			task.type = "skip";
		}

		return task;
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
			task.sd_copy.get(task.copies).target_id = searchPoolID(line);
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
/*
	private String searchSize(String line)
	{
		// Return the size of the task being completed.
		String task_size;
		String[] line_parts = line.split("\\(");
		line_parts = line_parts[1].split("\\)");
		task_size = line_parts[0];
		
		return task_size;
	}
*/
	private String searchRPC(String line)
	{
		String[] line_parts = line.split("\\[");
		line_parts = line_parts[1].split("\\]");

		String rpc_id = line_parts[0];

		return rpc_id;
	}

	private String[] searchSQLForChunk(String line)
	{
		String[] line_parts = line.split(" VALUES ");
		line_parts = line_parts[1].split(", ");

		String[] chunk = new String[1];
	       	chunk[0] = line_parts[4];	
		chunk[0] = chunk[0].substring(1, chunk[0].length()-1); // strip the single-quotes.

		return chunk;
	}

	private String searchSQLForPoolID(String line)
	{
		String[] line_parts = line.split(" VALUES ");
		line_parts = line_parts[1].split(", ");

		String pool_id = line_parts[1];
		pool_id = pool_id.substring(1, pool_id.length()-1);

		return pool_id;
	}
/*
	private String searchTaskID(String line, String task_type)
	{
		String[] line_parts;
		String task_id = line.substring(line.indexOf(task_type), line.length());

		line_parts = task_id.split("\\]");
		line_parts = line_parts[0].split("\\[");
		task_id = line_parts[0];

		return task_id;
	}
*/
	private String searchThroughput(String line)
	{
		String search_start = "at ";
		String search_end = ".";
		String throughput = line.substring(line.indexOf(search_start) + search_start.length(), line.indexOf(search_end));

		return throughput;
	}
/*
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
*/
	//=======================================
	// FUNCTIONS
	//=======================================
	
	private ArrayList<Task> buildTaskList()
	{
		ArrayList<Task> task_list = new ArrayList<Task>();
		Task task;

		for(String key : task_map.keySet())
		{
			task = task_map.get(key);
			
			for(int i=0; i<task.sd_copy.size(); i++)
			{
				task.sd_copy.get(i).target = id_name_map.get(task.sd_copy.get(i).target_id);
			}


			task_list.add(task);
		}

		return task_list;
	}
/*
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
*/
}

