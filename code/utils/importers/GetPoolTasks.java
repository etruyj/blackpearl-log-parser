//===================================================================
// GetPoolTasks.java
// 	Description:
// 		Loads each of the dataplanner logs into the parser
// 		to generate a full list of pool tasks.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers;

import com.socialvagrancy.blackpearl.logs.utils.inputs.LogReader;
import com.socialvagrancy.blackpearl.logs.utils.parsers.PoolTaskParser;
import com.socialvagrancy.blackpearl.logs.structures.Task;
import com.socialvagrancy.utils.Logger;

import java.util.ArrayList;

public class GetPoolTasks
{
	public static void main(String[] args)
	{
		ArrayList<Task> task_list = GetPoolTasks.fromDataplanner(args[0], 8, null);
		print(task_list);
	}

	public static ArrayList<Task> fromDataplanner(String dir_path, int log_count, Logger log)
	{
		String log_name = "logs/var.log.dataplanner-main.log";
		String file_name;
		PoolTaskParser pool_parser = new PoolTaskParser();

		for(int i=log_count; i>=0; i--)
		{
			if(i > 0)
			{
				file_name = dir_path + "/" + log_name + "." + i;
			}
			else
			{
				file_name = dir_path + "/" + log_name;
			}

			System.err.println(file_name);

			LogReader.readLog(file_name, pool_parser, log);
		}

		ArrayList<Task> task_list = pool_parser.getTaskList();

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
