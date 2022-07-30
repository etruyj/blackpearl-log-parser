//===================================================================
// GenerateDS3Operations.java
// 	Description:
// 		Pools the DS3 information together and formats it in
// 		an ArrayList<DS3Operation>.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.Task;
import com.socialvagrancy.blackpearl.logs.structures.operations.DS3Operation;
import com.socialvagrancy.blackpearl.logs.utils.importers.GetDS3Tasks;
import com.socialvagrancy.blackpearl.logs.utils.BPLogDateConverter;
import com.socialvagrancy.utils.Logger;

import java.util.ArrayList;

public class GenerateDS3Operations
{
	public static ArrayList<DS3Operation> fromLogs(String dir_path, int log_count, Logger log, boolean debugging)
	{
		ArrayList<DS3Operation> ops_list = new ArrayList<DS3Operation>();
		ArrayList<Task> task_list = GetDS3Tasks.fromDataplanner(dir_path, log_count, log, debugging);
		DS3Operation op;

		for(int i=0; i<task_list.size(); i++)
		{
			for(int j=0; j<=task_list.get(i).copies; j++)
			{
				op = new DS3Operation();

				op.id = task_list.get(i).id;
				op.type = task_list.get(i).type;
				op.created_at = BPLogDateConverter.formatDataPlannerTimestamp(task_list.get(i).sd_copy.get(j).created_at);
				op.date_completed = BPLogDateConverter.formatDataPlannerTimestamp(task_list.get(i).sd_copy.get(j).date_completed);
				op.chunks = task_list.get(i).chunk_id;
				op.target_id = task_list.get(i).sd_copy.get(j).target_id;
				op.target_name = task_list.get(i).sd_copy.get(j).target;

				ops_list.add(op);

			}
		}

		return ops_list;
	}
}
