//===================================================================
// GeneratePoolOperation.java
// 	Description:
// 		Queries the logs to build out the information related
// 		to pool tasks.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.operations.PoolOperation;
import com.socialvagrancy.blackpearl.logs.structures.rest.Pools;
import com.socialvagrancy.blackpearl.logs.structures.Task;
import com.socialvagrancy.blackpearl.logs.utils.importers.GetPoolData;
import com.socialvagrancy.blackpearl.logs.utils.importers.GetPoolTasks;
import com.socialvagrancy.utils.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class GeneratePoolOperations
{
	public static void main(String[] args)
	{
		GeneratePoolOperations.fromLogs(args[0], 8, null, true);
	}

	public static ArrayList<PoolOperation> fromLogs(String dir_name, int log_count, Logger log, boolean debugging)
	{
		ArrayList<Task> task_list = GetPoolTasks.fromDataplanner(dir_name, log_count, log, debugging);
		
		// Marked for deletion
		// Not used.
		//Pools pool_info = GetPoolData.fromRest(dir_name);
		//HashMap<String, Pools.PoolData> pool_map = MapPoolIDtoPool.createMap(pool_info);
		ArrayList<PoolOperation> ops_list = new ArrayList<PoolOperation>();
		PoolOperation op;
		
		for(int i=0; i<task_list.size(); i++)
		{
			for(int j=0; j <= task_list.get(i).copies; j++)
			{
				op = new PoolOperation();

				op.task_id = task_list.get(i).id;
				op.task_type = task_list.get(i).type;
				op.created_at = task_list.get(i).sd_copy.get(j).created_at;
				op.date_completed = task_list.get(i).sd_copy.get(j).date_completed;
				op.chunks = task_list.get(i).chunk_id;
				op.pool_id = task_list.get(i).sd_copy.get(j).target_id;
				op.pool_name = task_list.get(i).sd_copy.get(j).target;
				op.size = task_list.get(i).sd_copy.get(j).size;
				op.throughput = task_list.get(i).sd_copy.get(j).throughput;

				ops_list.add(op);
			}
		}

		return ops_list;
	}
}
