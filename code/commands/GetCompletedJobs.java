package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.CompletedJob;
import com.socialvagrancy.blackpearl.logs.utils.LoadFile;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class GetCompletedJobs
{
	public static CompletedJob fromJson(String path)
	{
		Gson gson = new Gson();
		String json = LoadFile.json(path);

		try
		{
			if(json.length() > 0)
			{
				CompletedJob jobs = gson.fromJson(json, CompletedJob.class);
				return jobs;
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
