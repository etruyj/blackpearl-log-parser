//===================================================================
// GetTapeTasks.java
// 	Description:
// 		Reads the dataplanner-main.logs to build an ArrayList
// 		of TapeTasks.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers;

import com.socialvagrancy.blackpearl.logs.utils.inputs.LogReader;
import com.socialvagrancy.blackpearl.logs.utils.parsers.TapeTaskParser;
import com.socialvagrancy.blackpearl.logs.structures.Task;
import com.socialvagrancy.utils.Logger;

import java.util.ArrayList;

public class GetTapeTasks
{
	public static void main(String[] args)
	{
		GetTapeTasks.fromDataplanner(args[0], 8, null);
	}

	public static ArrayList<Task> fromDataplanner(String log_dir, int log_count, Logger log)
	{
		String log_name = "logs/var.log.dataplanner-main.log";
		String file_name;
		TapeTaskParser task_parser = new TapeTaskParser();

		for(int i = log_count; i>=0; i--)
		{
			if(i > 0)
			{
				file_name = log_name + "." + i;
			}
			else
			{
				file_name = log_name;
			}
		
			System.err.println(log_dir + file_name);

			LogReader.readLog(log_dir + file_name, task_parser, log);

		}

		ArrayList<Task> task_list = task_parser.getTaskList();

		return task_list;
	}

	public static void print(ArrayList<Task> task_list)
	{
		for(int i=0; i<task_list.size(); i++)
		{
			for(int k=0; k<=task_list.get(i).copies; k++)
			{
				System.out.println(task_list.get(i).id + " " + task_list.get(i).type + " " + task_list.get(i).copies + " " + task_list.get(i).sd_copy.get(k).target + " " + task_list.get(i).sd_copy.get(k).created_at + " " + task_list.get(i).sd_copy.get(k).date_completed + " " + task_list.get(i).sd_copy.get(k).throughput);
			
			}
			if(task_list.get(i).chunk_id != null)
			{
				for(int j=0; j<task_list.get(i).chunk_id.length; j++)
				{
					System.out.println("\t- " + task_list.get(i).chunk_id[j]);
				}
			}
			else
			{
				System.out.println("\t- null");
			}
		}
	}
}
