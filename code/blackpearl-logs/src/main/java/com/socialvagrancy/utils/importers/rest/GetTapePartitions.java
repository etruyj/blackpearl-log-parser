//===================================================================
// GetTapePartitions.java
// 	Description:
// 		Import the tape partition data from the 
// 		rest/gui_tape_library_partitions.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers.rest;

import com.socialvagrancy.blackpearl.logs.structures.rest.GuiTapeLibraryPartitions;
import com.socialvagrancy.blackpearl.logs.utils.inputs.LoadFile;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class GetTapePartitions
{
	public static GuiTapeLibraryPartitions fromRest(String dir_path)
	{
		String file_name = "/rest/gui_tape_library_partitions.json";
		Gson gson = new Gson();
		String json = LoadFile.json(dir_path + file_name);

		try
		{
			return gson.fromJson(json, GuiTapeLibraryPartitions.class);
		}
		catch(JsonParseException e)
		{
			System.err.println(e.getMessage());
		}

		return null;
	}
}
