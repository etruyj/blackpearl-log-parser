//===================================================================
// JobIDtoChunkParser.java
// 	Description:
// 		This script parsers the dataplanner-main.log to create
// 		a mapping of job ids to chunks.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.parsers;

import java.util.ArrayList;
import java.util.HashMap;

public class JobIDtoChunkParser implements ParserInterface
{
	HashMap<String, ArrayList<String>> id_map;

	public JobIDtoChunkParser()
	{
		id_map = new HashMap<String, ArrayList<String>>();
	}

	//=======================================
	// Parsers
	//=======================================
	
	@Override
	public void parseLine(String line)
	{
		String search = "INSERT INTO ds3.job_chunk";
		String job_id;

		if(line.contains(search))
		{
			job_id = searchJobID(line);

			ArrayList<String> chunk_list = getChunks(job_id);
			
			chunk_list.add(searchChunkID(line));
			
			updateMap(job_id, chunk_list);
		}
	}

	public String searchChunkID(String line)
	{
		String[] query_parts = line.split("VALUES");
		String[] line_parts = query_parts[0].split(", ");
		int index=0;
		
		for(int i=0; i<line_parts.length; i++)
		{
			if(line_parts[i].equals("id"))
			{
				index = i;
			}
		}

		line_parts = query_parts[1].split(", ");

		String chunk = line_parts[index].substring(1, line_parts[index].length()-1);
		
		return chunk;
	}

	public String searchJobID(String line)
	{
		String[] query_parts = line.split("VALUES");
		String[] line_parts = query_parts[0].split(", ");
		int index=0;
		
		for(int i=0; i<line_parts.length; i++)
		{
			if(line_parts[i].equals("job_id"))
			{
				index = i;
			}
		}

		line_parts = query_parts[1].split(", ");

		String job = line_parts[index].substring(1, line_parts[index].length()-1);
		
		return job;
	}

	//=======================================
	// Getters
	//=======================================
	
	public HashMap<String, ArrayList<String>> getIDMap()
	{
		return id_map;
	}

	private ArrayList<String> getChunks(String job_id)
	{
		if(id_map.get(job_id) == null)
		{
			return new ArrayList<String>();
		}
		else
		{
			return id_map.get(job_id);
		}	
	}

	//=======================================
	// Getters
	//=======================================

	private void updateMap(String job_id, ArrayList<String> chunk_list)
	{
		id_map.put(job_id, chunk_list);
	}
}
