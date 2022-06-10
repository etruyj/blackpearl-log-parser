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

public class TapeTaskParser implements ParserInterface
{
	ArrayList<Task> task_list;
	HashMap<String, Task> task_map;
	String write_task = "WriteChunkToTapeTask#";
	String read_task = "ReadChunkFromTapeTask#";
	
	public TapeTaskParser()
	{
		task_list = new ArrayList<Task>();
		task_map = new HashMap<String, Task>();
	}

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
				task_id = parseTaskID(line, read_task);
			
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
				task_id = parseTaskID(line, write_task);
		
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
			task.drive_wwn = searchDriveWWN(line, line.indexOf(drive_search) + drive_search.length());
		}
		else if(line.contains(chunk_search))
		{
			task.chunk_id = searchChunkID(line, task.chunk_id);
		}
		else if(line.contains(completion_search))
		{
			task.date_completed = searchTimeStamp(line);
		}
		else if(line.contains(throughput_search))
		{
			task.throughput = searchThroughput(line, line.indexOf(throughput_search) + throughput_search.length());
		}
		else if(line.contains(creation_search))
		{
			task.created_at = searchTimeStamp(line);
		}
		else
		{
			// No updates to task.
			task.type = "skip";
		}

		return task;
	}

	public String parseTaskID(String line, String task_type)
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

	public Task parseWrite(String line, Task task)
	{
		String completion_search = "Task has finished executing";
		String chunk_search = "SQL: UPDATE ds3.job_chunk ";
		String chunk_search_2 = "chunks in one task: ";
		String creation_search = "Locked Tape Drive";
		String drive_search = "RPC TapeDrive$";
		String throughput_search = "quiesced to tape";
		
		if(line.contains(creation_search))
		{
			task.created_at = searchTimeStamp(line);
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
			task.drive_wwn = searchDriveWWN(line, line.indexOf(drive_search) + drive_search.length());
		}
		else if(line.contains(completion_search))
		{
			task.date_completed = searchTimeStamp(line);
		}
		else if(line.contains(throughput_search))
		{
			String sub_search = "effectively ";
			task.throughput = searchThroughput(line, line.indexOf(sub_search) + sub_search.length());
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

	private String searchThroughput(String line, int start_index)
	{
		// Return the throughput.
		// Uses line splits as all the search characters occur repeatedly
		// in the string.

		String throughput = line.substring(start_index, line.length());
		String[] line_parts = throughput.split(" ");
		throughput = line_parts[0] + " " + line_parts[1];
		
		return throughput;
	}

	private String searchTimeStamp(String line)
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

	private ArrayList<Task> buildTaskList(HashMap<String, Task> task_map)
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

			task_list.add(task);
		}

		return task_list;
	}

	private Task getTask(String task_id)
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

	private void updateTask(String task_id, Task task)
	{
		// Adds or overwrites the task with the updated info.
		if(task.type.equals("skip"))
		{
			System.out.println(task.type);
		}
		task_map.put(task_id, task);
	}
}
