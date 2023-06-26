//===================================================================
// GetJson.java
// 	Description:
// 		An attempt to use Generics to import JSON data into
// 		java classes without having to code a class for 
// 		each report.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers.rest;

import com.socialvagrancy.blackpearl.logs.structures.rest.*;
import com.socialvagrancy.blackpearl.logs.utils.inputs.LoadFile;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class GetJson
{
	public static <T> T fromRest(String file_path, Class<T> obj)
	{
		Gson gson = new Gson();
		String json = LoadFile.json(file_path);

		try
		{
			if(json.length() > 0)
			{
				Object o = gson.fromJson(json, obj.getClass());
				return (T) o;
			}
		}
		catch(JsonParseException e)
		{
			System.err.println(e.getMessage());
			System.err.println(json);
		}

		return null;
	}
}
