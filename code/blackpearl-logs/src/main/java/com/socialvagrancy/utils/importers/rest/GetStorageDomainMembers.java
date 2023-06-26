//===================================================================
// GetStorageDomainMembers.java
// 	Description:
//		Import the rest/gui_ds3_storage_domain_members.json 
//		into a class.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers.rest;

import com.socialvagrancy.blackpearl.logs.structures.rest.GuiStorageDomainMembers;
import com.socialvagrancy.blackpearl.logs.utils.inputs.LoadFile;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class GetStorageDomainMembers
{
	public static GuiStorageDomainMembers fromJson(String file_path)
	{
		Gson gson = new Gson();
		String json = LoadFile.json(file_path);

		try
		{
			if(json.length() > 0)
			{
				return gson.fromJson(json, GuiStorageDomainMembers.class);
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
