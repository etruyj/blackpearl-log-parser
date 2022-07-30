//===================================================================
// DataplannerParser.java
// 	Description:
//		Abstract class to hold some of the basic functions
//		in parsing the dataplanner, such as get timestamp.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.parsers;

import com.socialvagrancy.blackpearl.logs.structures.Task;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class DataplannerParser implements ParserInterface
{
	ArrayList<Task> task_list;
	HashMap<String, Task> task_map;
	
	public DataplannerParser()
	{
		task_list = new ArrayList<Task>();
		task_map = new HashMap<String, Task>();
	}

	//=======================================
	// Search Fields
	//=======================================
	
	public String searchTaskID(String line, String task_type)
	{
		// Located the ReadChunkToTapeTask or WriteChunkToTapeTask #
		// Usually is either a bracketed value or followed by a bracketed value.
		// Start with ] to ensure the task_id is at index 0
		String[] line_parts;
		String task_id = line.substring(line.indexOf(task_type), line.length());
		line_parts = task_id.split("\\]");
		line_parts = line_parts[0].split("\\[");
		task_id = line_parts[0];

		return task_id;
	}

	public String searchSize(String line)
	{
		// Returns the size of the job.
		// This is included in the throughput line.
		String task_size;
		String[] line_parts = line.split("\\(");
	
		line_parts = line_parts[1].split("\\)");
		
		task_size = line_parts[0];

		return task_size;
	}

	public String searchThroughput(String line, int start_index)
	{
		// Return the throughput.
		// Uses line splits as all the search characters occur repeatedly
		// in the string.

		String throughput = line.substring(start_index, line.length());
		String[] line_parts = throughput.split(" ");
		throughput = line_parts[0] + " " + line_parts[1];
		
		return throughput;
	}

	public String searchTimestamp(String line)
	{
		// Find the MMM dd hh:mm:ss timestamp.
		String[] line_parts = line.split(" ");
		String timestamp = line_parts[1] + " " + line_parts[2];
		line_parts = line_parts[3].split(",");
		timestamp += " " + line_parts[0];

		return timestamp;
	}

	//=======================================
	// Getters
	//=======================================
	
	public ArrayList<Task> getTaskList()
	{
		// Convert the task_maps to ArrayLists 
		// before returning the array list.
		// This seems to be the best place to make
		// this call.
		task_list.addAll(buildTaskList(task_map));

		return task_list;
	}

	//=======================================
	// Functions
	//=======================================

	public ArrayList<Task> buildTaskList(HashMap<String, Task> task_map)
	{
		// Convert the task_map to an ArrayList<Task>
		ArrayList<Task> task_list = new ArrayList<Task>();
		Task task;

		for(String key : task_map.keySet())
		{
			task = task_map.get(key);

			// Catch to overwrite the "skip"
			// type, even though it is being filtered
			// out in testing. Not sure why it's being
			// stored.
			if(key.contains("Read"))
			{
				task.type = "GET";
			}
			else if(key.contains("Write"))
			{
				task.type = "PUT";
			}

			// Added a filter for tasks without chunks.
			// These can't be paired with anything anyway, but 
			// the BlackPearl can re-aggregate jobs after they are
			// queued. There doesn't appear to be a clear, job reaggrated
			// message to parse for, so this filter aims to catch those
			// hanging jobs while leaving active jobs in the list.

			if(task.chunk_id != null)
			{
				task_list.add(task);
			}
			else
			{
				System.err.println("Dropping: " + task.id);
			}
		}

		return task_list;
	}

	public Task getTask(String task_id)
	{
		// Find out which task is being edited.
		// If the task doesn't exist in the map,
		// create a new one.

		Task task = task_map.get(task_id);

		if(task == null)
		{
			task = new Task();
		}

		return task;
	}

	public void updateTask(String task_id, Task task)
	{
		// Adds or overwrites the task with the updated info.
		if(task.type.equals("skip"))
		{
			System.out.println(task.type);
		}
		task_map.put(task_id, task);
	}
}
