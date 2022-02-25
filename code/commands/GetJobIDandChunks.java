//===================================================================
// GetJobIdandChunks.java
// 	Returns a list of chunks associated with each job_id
//===================================================================

package com.socialvagrancy.blackpearl.logs.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GetJobIDandChunks
{
	public static HashMap<String, ArrayList<String>> fromDataplannerMain(String path)
	{
		File file = new File(path);
		String match = "SQLTrans";

		HashMap<String, ArrayList<String>> id_map = new HashMap<String, ArrayList<String>>();
		ArrayList<String> chunks;

		if(file.exists())
		{
			try
			{
				BufferedReader br = new BufferedReader(new FileReader(file));

				String line = null;
				String[] parse_value;
				String[] id_pair;

				int itr = 0;
				while((line = br.readLine()) != null)
				{
					parse_value = line.split("\\|");
					
					if(parse_value.length > 1 && 
						parse_value[1].length() > match.length() && 
						parse_value[1].substring(1, match.length()+1).equals(match))
					{
						id_pair = parseLine(parse_value[1]);
						
						if(id_pair[0] != null)
						{
							if(id_map.get(id_pair[0])==null)
							{
								chunks = new ArrayList<String>();
								chunks.add(id_pair[1]);

								id_map.put(id_pair[0], chunks);
							}
							else
							{
								chunks = id_map.get(id_pair[0]);
								chunks.add(id_pair[1]);
	
								id_map.put(id_pair[0], chunks);
							}
						}
					}
				}
			
				return id_map;
			}
			catch(IOException e)
			{
				System.err.println(e.getMessage());
				return null;
			}
		}
		else
		{
			System.err.println("ERROR: file " + path + " does not exist.");
			return null;
		}
	}

	public static String[] parseLine(String line)
	{
		String[] parse_value = line.split(":");
		String search_write = "INSERT INTO ds3.job_chunk(blob_store_state, job_id, chunk_number, id, pending_target_commit)";
		String search_read = "INSERT INTO ds3.job_chunk(blob_store_state, job_id, chunk_number, read_from_tape_id, id, pending_target_commit)";
		String[] result = new String[2];

		parse_value[1] = parse_value[1].trim();
	

		if(parse_value[1].length() > search_write.length() && parse_value[1].substring(0, search_write.length()).equals(search_write))
		{
			parse_value = parse_value[1].split("VALUES");
			parse_value = parse_value[1].split(",");
		
			// Set Job ID
			parse_value[1] = parse_value[1].trim();
			result[0] = parse_value[1].substring(1, parse_value[1].length()-1);

			// Set Chunk ID
			parse_value[3] = parse_value[3].trim();
			result[1] = parse_value[3].substring(1, parse_value[3].length()-1);
		}
		else if(parse_value[1].length() > search_read.length() && parse_value[1].substring(0, search_read.length()).equals(search_read))
		{
			parse_value = parse_value[1].split("VALUES");
			parse_value = parse_value[1].split(",");
		
			// Set Job ID
			parse_value[1] = parse_value[1].trim();
			result[0] = parse_value[1].substring(1, parse_value[1].length()-1);

			// Set Chunk ID
			parse_value[4] = parse_value[4].trim();
			result[1] = parse_value[4].substring(1, parse_value[4].length()-1);
		
			
		}

		return result;
	}

	public static void print(HashMap<String, ArrayList<String>> id_map)
	{
		for(String key : id_map.keySet())
		{
			System.err.println("\nJob ID: " + key);
			
			for(int i=0; i<id_map.get(key).size(); i++)
			{
				System.err.println("\t- " + id_map.get(key).get(i));
			}
		}
	}
}
