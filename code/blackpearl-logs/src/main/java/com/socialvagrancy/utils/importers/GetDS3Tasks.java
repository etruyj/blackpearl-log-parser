//===================================================================
// GetDS3Tasks.java
// 	Description:
// 		Loads each of the dataplanner logs into the parser
// 		to generate a full list of pool tasks.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers;

import com.socialvagrancy.blackpearl.logs.utils.inputs.LogReader;
import com.socialvagrancy.blackpearl.logs.utils.parsers.DS3TaskParser;
import com.socialvagrancy.blackpearl.logs.structures.Task;
import com.socialvagrancy.utils.Logger;

import java.util.ArrayList;

public class GetDS3Tasks
{
	public static void main(String[] args)
	{
		ArrayList<Task> task_list = GetDS3Tasks.fromDataplanner(args[0], 8, null, true);
		print(task_list);
	}

	public static ArrayList<Task> fromDataplanner(String dir_path, int log_count, Logger log, boolean debugging)
	{
		ArrayList<String> exception_list = new ArrayList<String>();

		if(!debugging)
		{
			System.err.print("Importing pool tasks...\t\t");
		}
		else
		{
			System.err.println("Importing pool tasks...\t\t");
		}

		String log_name = "logs/var.log.dataplanner-main.log";
		String file_name;
		DS3TaskParser ds3_parser = new DS3TaskParser();

		for(int i=log_count; i>=0; i--)
		{
			try
			{
				if(i > 0)
				{
					file_name = dir_path + "/" + log_name + "." + i;
				}
				else
				{
					file_name = dir_path + "/" + log_name;
				}

				if(debugging)
				{
					System.err.println(file_name);
				}

				LogReader.readLog(file_name, ds3_parser, log);
			}
			catch(Exception e)
			{
				// For cleaner output, collect exceptions and print them after
				// the completed.
				exception_list.add(e.getMessage());
			}
		}

		ArrayList<Task> task_list = ds3_parser.getTaskList();

		if(!debugging)
		{
			System.err.println("[COMPLETE]");
		}

		for(int i=0; i<exception_list.size(); i++)
		{
			System.err.println(exception_list.get(i));
		}

		return task_list;
	}

	public static void print(ArrayList<Task> task_list)
	{
		for(int i=0; i<task_list.size(); i++)
		{
			System.out.print(task_list.get(i).id + "\t");
			System.out.print(task_list.get(i).type + "\t");
			System.out.print(task_list.get(i).copies + "\t");	
			for(int j=0; j<=task_list.get(i).copies; j++)
			{
				System.out.print(task_list.get(i).sd_copy.get(j).created_at + "\t");
				System.out.print(task_list.get(i).sd_copy.get(j).date_completed + "\t");
				System.out.print(task_list.get(i).sd_copy.get(j).size + "\t");
				System.out.print(task_list.get(i).sd_copy.get(j).throughput + "\t");
				System.out.println(task_list.get(i).sd_copy.get(j).target + "\t");
			}
		}
	}
}
