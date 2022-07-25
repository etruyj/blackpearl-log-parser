//===================================================================
// GetPoolData.java
// 	Description:
// 		Import the pool data from the rest/pools.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers;

import com.socialvagrancy.blackpearl.logs.structures.rest.Pools;
import com.socialvagrancy.blackpearl.logs.utils.inputs.LoadFile;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class GetPoolData
{
	public static Pools fromRest(String dir_path)
	{
		String file_name = "/rest/pools.json";
		Gson gson = new Gson();
		String json = LoadFile.json(dir_path + file_name);

		try
		{
			return gson.fromJson(json, Pools.class);
		}
		catch(JsonParseException e)
		{
			System.err.println(e.getMessage());
		}

		return null;
	}
}
