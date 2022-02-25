package com.socialvagrancy.blackpearl.logs.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.StringBuilder;

public class LoadFile
{
	public static String json(String path)
	{
		File file = new File(path);
		StringBuilder json = new StringBuilder();

		if(file.exists())
		{
			String line = null;

			try
			{
				BufferedReader br = new BufferedReader(new FileReader(file));

				while((line = br.readLine()) != null)
				{
					json.append(line + "\n");
				}
			}
			catch(IOException e)
			{
				System.err.println(e.getMessage());
			}
		}
		else
		{
			System.err.println("ERROR: File: " + path + " does not exist.");
		}

		return json.toString();
	}
}
