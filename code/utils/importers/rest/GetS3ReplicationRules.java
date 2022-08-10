//===================================================================
// GetS3ReplicationRules.java
// 	Description:
//		Import the rest/gui_ds3_s3_data_replication_rules.json
//		into a class.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers.rest;

import com.socialvagrancy.blackpearl.logs.structures.rest.GuiS3RepRules;
import com.socialvagrancy.blackpearl.logs.utils.inputs.LoadFile;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class GetS3ReplicationRules
{
	public static GuiS3RepRules fromJson(String file_path)
	{
		Gson gson = new Gson();
		String json = LoadFile.json(file_path);

		try
		{
			if(json.length() > 0)
			{
				return gson.fromJson(json, GuiS3RepRules.class);
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
