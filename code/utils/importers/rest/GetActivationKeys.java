//===================================================================
// GetActivationKeys.java
// 	Description:
//		Import the rest/activation_keys.json into a class.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers.rest;

import com.socialvagrancy.blackpearl.logs.structures.rest.ActivationKeys;
import com.socialvagrancy.blackpearl.logs.utils.inputs.LoadFile;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class GetActivationKeys
{
	public static ActivationKeys fromRest(String dir_path)
	{
		String file_path = dir_path + "rest/activation_keys.json";
		Gson gson = new Gson();
		String json = LoadFile.json(file_path);

		try
		{
			if(json.length() > 0)
			{
				return gson.fromJson(json, ActivationKeys.class);
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
