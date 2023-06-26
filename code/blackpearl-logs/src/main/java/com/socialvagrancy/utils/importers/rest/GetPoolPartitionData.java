//===================================================================
// GetPoolPartitionData.java
// 	Description:
// 		Import the pool partition data from the 
// 		rest/gui_ds3_pool_partitions.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers.rest;

import com.socialvagrancy.blackpearl.logs.structures.rest.GuiPoolPartitions;
import com.socialvagrancy.blackpearl.logs.utils.inputs.LoadFile;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class GetPoolPartitionData
{
	public static GuiPoolPartitions fromRest(String dir_path)
	{
		String file_name = "/rest/gui_ds3_pool_partitions.json";
		Gson gson = new Gson();
		String json = LoadFile.json(dir_path + file_name);

		try
		{
			return gson.fromJson(json, GuiPoolPartitions.class);
		}
		catch(JsonParseException e)
		{
			System.err.println(e.getMessage());
		}

		return null;
	}
}
