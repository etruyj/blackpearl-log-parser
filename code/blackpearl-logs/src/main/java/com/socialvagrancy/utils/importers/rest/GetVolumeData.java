//===================================================================
// GetVolumeData.java
// 	Description:
// 		Import the volume data from the rest/volumes.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers.rest;

import com.socialvagrancy.blackpearl.logs.structures.rest.Volumes;
import com.socialvagrancy.blackpearl.logs.utils.inputs.LoadFile;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class GetVolumeData
{
	public static Volumes fromRest(String dir_path)
	{
		String file_name = "/rest/volumes.json";
		Gson gson = new Gson();
		String json = LoadFile.json(dir_path + file_name);

		try
		{
			return gson.fromJson(json, Volumes.class);
		}
		catch(JsonParseException e)
		{
			System.err.println(e.getMessage());
		}

		return null;
	}
}
