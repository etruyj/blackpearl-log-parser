//===================================================================
// GetShareData.java
// 	Description:
// 		Import the share data from the rest/shares.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers.rest;

import com.socialvagrancy.blackpearl.logs.structures.rest.Shares;
import com.socialvagrancy.blackpearl.logs.utils.inputs.LoadFile;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class GetShareData
{
	public static Shares fromRest(String dir_path)
	{
		String file_name = "/rest/shares.json";
		Gson gson = new Gson();
		String json = LoadFile.json(dir_path + file_name);

		try
		{
			return gson.fromJson(json, Shares.class);
		}
		catch(JsonParseException e)
		{
			System.err.println(e.getMessage());
		}

		return null;
	}
}
