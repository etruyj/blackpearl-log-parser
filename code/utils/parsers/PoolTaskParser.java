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
		String job_id;
		Task task;
		String sql_filter = "COPY";
		String delete_search = "DELETE FROM ds3.job";

		if(line.contains(write_task) || ((line.contains(read_task) || line.contains(read_task_2)) && !line.contains(sql_filter)))
		{
			if(line.contains(read_task) || line.contains(read_task_2))
			{
				job_id = searchJobIDCreation(line);
				task_id = searchRPC(line);

				task = getTask(job_id);
				task.id = task_id;
				task.type = "GET";
				
				task = parseRead(line, task);

				// Don't update if no new info
				if(!task.type.equals("skip"))
				{
					updateTask(job_id, task);
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
		else if(line.contains(delete_search))
		{
			job_id = searchJobIDDeletion(line);

			task = getTask(job_id);
			
			if(task.id != null)
			{
				task.sd_copy.get(task.copies).date_completed = searchTimestamp(line);

				updateTask(job_id, task);
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
			// Created at - Since I haven't found a better method
			task.sd_copy.get(task.copies).created_at = searchTimestamp(line);

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
	
	private String searchJobIDCreation(String line)
	{
		String[] line_parts = line.split(" VALUES ");
		line_parts = line_parts[1].split(", ");
		String job_id = line_parts[2].substring(1, line_parts[2].length()-1); // substring(1, length()-1) to strip single quotes

		return job_id;
	}

	private String searchJobIDDeletion(String line)
	{
		String id_search = "WHERE id = ";
		String array_search = "ANY ('"; 
		String id_end = "' [";
		String job_id;
		int starting_char = line.indexOf(id_search) + id_search.length() + 1; // +1 for the leading quote or square bracket.

		// Some reason there is an array input as well.
		// This allows for parsing the second type of SQL deletion.
		if(line.contains(array_search))
		{
			starting_char += array_search.length();
			id_end = "]')";
		}

		job_id = line.substring(starting_char, line.indexOf(id_end));

		return job_id;
	}

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
		
		try
		{	
			line_parts = line_parts[1].split(", ");
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			System.err.println(line);
		}

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
	
	private String searchThroughput(String line)
	{
		String search_start = "at ";
		String search_end = ".";
		String throughput = line.substring(line.indexOf(search_start) + search_start.length(), line.indexOf(search_end));

		return throughput;
	}
	
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
			

			// Add the human-readable pool name to the Task.
			for(int i=0; i<task.sd_copy.size(); i++)
			{
				task.sd_copy.get(i).target = id_name_map.get(task.sd_copy.get(i).target_id);
			}


			task_list.add(task);
		}

		return task_list;
	}
	
	@Override
	public ArrayList<Task> getTaskList() 
	{ 
		task_list.addAll(buildTaskList());
		return task_list;
       	}
}

