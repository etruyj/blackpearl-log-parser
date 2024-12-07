package com.socialvagrancy.blackpearl.logs.utils.inputs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.StringBuilder;
import java.util.ArrayList;

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

    public static ArrayList<String> fullFileIntoArray(String path) {
		File file = new File(path);
        ArrayList<String> file_lines = new ArrayList<String>();

		if(file.exists())
		{
			String line = null;

			try
			{
				BufferedReader br = new BufferedReader(new FileReader(file));

				while((line = br.readLine()) != null)
				{
					file_lines.add(line);
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

		return file_lines;
    }
    
    public static ArrayList<String> partialFileIntoArray(String path, int start, int end) {
		File file = new File(path);
        int line_counter = 0;
        ArrayList<String> file_lines = new ArrayList<String>();

		if(file.exists())
		{
			String line = null;

			try
			{
				BufferedReader br = new BufferedReader(new FileReader(file));

				while((line = br.readLine()) != null && line_counter < end)
				{
                    // Only save lines after we got to the start.
                    if(line_counter >= start) {
					    file_lines.add(line);
                    }

                    line_counter++;
				}
                
                br.close();
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


		return file_lines;
    }
}
