//===================================================================
// GetDS3Targetss.java
// 	Description:
//		Import the rest/gui_ds3_ds3_targets.json into a class.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers.rest;

import com.socialvagrancy.blackpearl.logs.structures.rest.GuiDS3RepTargets;
import com.socialvagrancy.blackpearl.logs.utils.inputs.LoadFile;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class GetDS3Targets
{
	public static GuiDS3RepTargets fromJson(String file_path)
	{
		Gson gson = new Gson();
		String json = LoadFile.json(file_path);

		try
		{
			if(json.length() > 0)
			{
				return gson.fromJson(json, GuiDS3RepTargets.class);
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
