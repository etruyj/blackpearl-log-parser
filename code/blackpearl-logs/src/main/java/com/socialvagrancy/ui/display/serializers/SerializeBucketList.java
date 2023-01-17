//===================================================================
// SerializeBucketList.java
// 	Description:
// 		Converts the ArrayList<Bucket> to an 
// 		ArrayList<OutputFormat> for display.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui.display.serializers;

import com.socialvagrancy.blackpearl.logs.structures.outputs.Bucket;
import com.socialvagrancy.utils.ui.structures.OutputFormat;

import java.util.ArrayList;

public class SerializeBucketList
{
	public static ArrayList<OutputFormat> toOutputFormat(ArrayList<Bucket> bucket_list)
	{
		ArrayList<OutputFormat> output = new ArrayList<OutputFormat>();
		OutputFormat formatted_line;

		for(int i=0; i<bucket_list.size(); i++)
		{
			formatted_line = new OutputFormat();
			formatted_line.key = "bucket_name";
			formatted_line.value = bucket_list.get(i).name;
			output.add(formatted_line);
			formatted_line = new OutputFormat();
			formatted_line.key = "data_policy";
			formatted_line.value = bucket_list.get(i).dataPolicy();
			output.add(formatted_line);
			formatted_line = new OutputFormat();
			formatted_line.key = "owner";
			formatted_line.value = bucket_list.get(i).owner;
			output.add(formatted_line);
			formatted_line = new OutputFormat();
			formatted_line.key = "size";
			formatted_line.value = bucket_list.get(i).size_human;
			output.add(formatted_line);
			formatted_line = new OutputFormat();
			formatted_line.key = "data_copies";
			formatted_line.value = String.valueOf(bucket_list.get(i).copyCount());
			output.add(formatted_line);
			formatted_line = new OutputFormat();
			formatted_line.key = "local_copies";
			formatted_line.value = String.valueOf(bucket_list.get(i).localCopies());
			output.add(formatted_line);
			formatted_line = new OutputFormat();
			formatted_line.key = "remote_copies";
			formatted_line.value = String.valueOf(bucket_list.get(i).remoteCopies());
			output.add(formatted_line);
		}

		return output;
	}
}
