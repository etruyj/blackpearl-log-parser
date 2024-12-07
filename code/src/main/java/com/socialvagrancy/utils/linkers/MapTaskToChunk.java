//===================================================================
// MapTaskToChunk.java
// 	Description:
// 		Creates a HashMap of chunk to task in order to allow
// 		attaching gui job info to tasks.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.Task;
import com.socialvagrancy.utils.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class MapTaskToChunk
{
	public static HashMap<String, Task> createMap(ArrayList<Task> task_map, Logger log)
	{
		HashMap<String, Task> chunk_task_map = new HashMap<String, Task>();

		for(int i=0; i<task_map.size(); i++)
		{
			if(task_map.get(i).chunk_id != null)
			{
				for(int j=0; j<task_map.get(i).chunk_id.length; j++)
				{
					chunk_task_map.put(task_map.get(i).chunk_id[j], task_map.get(i));
				}
			}
			else
			{
				log.WARN("Task [" + task_map.get(i).id + "] does not have associated chunks.");
				System.err.println("Task [" + task_map.get(i).id + "] does not have associated chunks.");
			}
		}

		return chunk_task_map;
	}
}
