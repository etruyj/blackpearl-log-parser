//===================================================================
// GetTapeTasks.java
// 	Parses the dataplanner-main log for information on the jobs.
//===================================================================

package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GetTapeTasks
{
	public static ArrayList<Task> fromDataPlannerMain(String path)
	{
		File file = new File(path);

		if(file.exists())
		{
	
			ArrayList<Task> task_list = new ArrayList<Task>();
			HashMap<String, Task> wtask_map = new HashMap<String, Task>();
			HashMap<String, Task> rtask_map = new HashMap<String, Task>();

			Task task;

			String write_task = "WriteChunkToTapeTask#";
			String read_task = "ReadChunkFromTapeTask#";

			try
			{
				BufferedReader br = new BufferedReader(new FileReader(file));

				String line = null;
				String[] parse_value;
				String[] log_line;
				String field;				

				while((line = br.readLine()) != null)
				{
					parse_value = line.split(" ");
					
					// To minimize processing time, we're processing input as it's read
					// from the file. This way the whole log doesn't need to be stored
					// in a variable.

					if(parse_value.length > 4 && 
						parse_value[4].length() > (write_task.length() + 1) && 
						(parse_value[4].substring(1, write_task.length()+1).equals(write_task) ||
						parse_value[4].substring(1, read_task.length()+1).equals(read_task)))
					{
						if(parse_value[4].substring(1, write_task.length()+1).equals(write_task))
						{
							field = parse_value[4].substring(write_task.length()+1, parse_value[4].length()-1);
				
							// If the task doesn't exist in the map, create a new task and put 
							// it in the map
							if(wtask_map.get(field)==null)
							{
								task = new Task();
								task.id = field;
								task.type = "PUT";
								task.created_at = parse_value[1] + " " + parse_value[2] + " " + parse_value[3];
								wtask_map.put(field, task);
								
							}
							
							// Pass the task entry from the map into the parse function to fill in the needed fields.
							// The completed task is returned to the entry on the task map.
							wtask_map.put(field, parseLineWrite(line, wtask_map.get(field), (write_task+field)));							
						}
						else if(parse_value[4].length() > read_task.length() && parse_value[4].substring(1, read_task.length()+1).equals(read_task))

						{
							field = parse_value[4].substring(read_task.length()+1, parse_value[4].length()-1);
				
							// If the task doesn't exist in the map, create a new task and put 
							// it in the map
							if(rtask_map.get(field)==null)
							{
								task = new Task();
								task.id = field;
								task.type = "GET";
								task.created_at = parse_value[1] + " " + parse_value[2] + " " + parse_value[3];
								rtask_map.put(field, task);
							}
							
							// Pass the task entry from the map into the parse function to fill in the needed fields.
							// The completed task is returned to the entry on the task map.
							rtask_map.put(field, parseLineRead(line, rtask_map.get(field), (read_task+field)));							

						}
					}
				}
		
				// End of parsing. Convert maps to an ArrayList for display
				// or return to calling function.	
				task_list.addAll(buildTaskList(wtask_map));
				task_list.addAll(buildTaskList(rtask_map));
			}
			catch(IOException e)
			{
				System.err.println(e.getMessage());

				return null;
			}

			return task_list;
		}
		else
		{
			System.err.println("ERROR: file " + path + " does not exist.");
			return null;
		}
	}

	public static ArrayList<Task> buildTaskList(HashMap<String, Task> task_map)
	{
		ArrayList<Task> task_list = new ArrayList<Task>();

		for(String key : task_map.keySet())
		{
			task_list.add(task_map.get(key));
		}

		return task_list;
	}

	public static Task parseLineRead(String line, Task task, String task_title)
	{
		String time_stamp = line.substring(5, 22);
		String log = line.substring((23 + task_title.length() + 7), line.length());
	
		switch(log.substring(0, 9))
		{
			case "RPC TapeD":
				task.drive_sn = getTapeDriveSN(log);
				break;
			case "SQL: UPDA":
				// read chunk task.
				task.chunk_id = getReadChunkId(line);
				break;
			case "There are":
				if(getCompletionDate(log))
				{
					task.date_completed=time_stamp;
				}
				break;
		}
		
		// Have to parse separately for the speed as the first character is a variable.
		String[] parse_value = log.split("\\.");
		String search = " read from tape at ";

		if(parse_value.length > 1)
		{	
			parse_value = parse_value[0].split("\\)");

			if(parse_value.length>1)
			{	
				if(parse_value[1].length() > search.length() && parse_value[1].substring(0, search.length()).equals(search))
				{
					parse_value[1] = parse_value[1].substring(search.length(), parse_value[1].length()).trim();				
					task.throughput = parse_value[1];
				}
			}
		}
		
		return task;
	}

	public static Task parseLineWrite(String line, Task task, String task_title)
	{
		String time_stamp = line.substring(5, 22);
		String log = line.substring((23 + task_title.length() + 7), line.length());

		switch(log.substring(0, 9))
		{
			case "RPC TapeD":
				task.drive_sn = getTapeDriveSN(log);
				break;
			case "Servicing":
				task.chunk_id = getWriteChunkIds(line);
				break;
			case "There are":
				if(getCompletionDate(log))
				{
					task.date_completed=time_stamp;
				}
				break;
			case "Will writ":
				break;
		}

		// Have to parse separately for the speed as the first character is a variable.
		String[] parse_value = log.split("\\.");
		String search = " written to tape at ";

		if(parse_value.length > 1)
		{	
			parse_value = parse_value[0].split("\\)");

			if(parse_value.length>1)
			{	
				if(parse_value[1].length() > search.length() && parse_value[1].substring(0, search.length()).equals(search))
				{
					parse_value[1] = parse_value[1].substring(search.length(), parse_value[1].length()).trim();				
					task.throughput = parse_value[1];
				}
			}
		}

		return task;
	}

	public static boolean getCompletionDate(String line)
	{
		String match = "There are no remaining storage domains containing tape to persist to.";

		if(line.substring(0, match.length()).equals(match))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static String[] getReadChunkId(String line)
	{
		String search_value = "UPDATE ds3.job_chunk SET blob_store_state = CAST('COMPLETED' AS ds3.job_chunk_blob_store_state)";
		String[] parse_values = line.split("SQL:");
		String[] chunk = null;

		if(parse_values.length > 1)
		{
			parse_values[1] = parse_values[1].trim();
		
			if(parse_values[1].length() > search_value.length() &&
				parse_values[1].substring(0, search_value.length()).equals(search_value))
			{
				parse_values = parse_values[1].split("WHERE");

				if(parse_values.length > 1)
				{
					parse_values = parse_values[1].split("\\)");
					parse_values = parse_values[0].split("\\(");

					if(parse_values.length > 1)
					{
						parse_values = parse_values[1].split("=");
						
						if(parse_values.length > 1)
						{
							chunk = new String[1];
							chunk[0] = parse_values[1];
							chunk[0] = chunk[0].trim();
							chunk[0] = chunk[0].substring(1, chunk[0].length()-1);
						}
					}
				}
			}
		}

		return chunk;
	}

	public static String getTapeDriveSN(String line)
	{
		String[] drive = line.split("\\$");
		drive = drive[1].split("\\.");

		return drive[0];
	}

	public static String[] getWriteChunkIds(String line)
	{
		String[] chunks = line.split("\\[");
		chunks = chunks[3].split("\\]");
		chunks = chunks[0].split(",");

		for(int i=0; i<chunks.length; i++)
		{
			chunks[i] = chunks[i].trim();
		}

		return chunks;
	}

	public static void print(ArrayList<Task> task_list)
	{
		for(int i=0; i<task_list.size(); i++)
		{
			if(task_list.get(i).type.equals("PUT"))
			{
				System.out.print("\nWriteChunkToTapeTask#");
			}
			else if(task_list.get(i).type.equals("GET"))
			{
				System.out.print("\nReadChunkFromTapeTask#");
			}

			System.out.println(task_list.get(i).id);
			System.out.println("\tCreated at: " + task_list.get(i).created_at);
			System.out.println("\tCompleted at: " + task_list.get(i).date_completed);
			System.out.println("\tTape Drive WWN: " + task_list.get(i).drive_sn);			
			if(task_list.get(i).chunk_id != null)
			{
				System.out.println("\tChunks:");
			
				for(int j=0; j < task_list.get(i).chunk_id.length; j++)
				{
					System.out.println("\t\t- " + task_list.get(i).chunk_id[j]);
				}
			}
		}
	}
}
