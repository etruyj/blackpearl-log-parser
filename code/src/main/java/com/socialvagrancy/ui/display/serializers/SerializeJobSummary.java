//===================================================================
// SerializeJobSummary.java
// 	Description:
//		Converts the ArrayList<JobSummary> to an 
//		ArrayList<OutputFormat>
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui.display.serializers;

import com.socialvagrancy.blackpearl.logs.structures.outputs.JobSummary;
import com.socialvagrancy.utils.ui.structures.OutputFormat;

import java.util.ArrayList;

public class SerializeJobSummary
{
	public static ArrayList<OutputFormat> toOutputFormat(ArrayList<JobSummary> job_list)
	{
		ArrayList<OutputFormat> formatted_list = new ArrayList<OutputFormat>();
		OutputFormat formatted_line;

		for(int i=0; i<job_list.size(); i++)
		{
			formatted_line = new OutputFormat();
			formatted_line.key = "job_name";
			formatted_line.value = job_list.get(i).name();
			formatted_list.add(formatted_line);

			formatted_line = new OutputFormat();
			formatted_line.key = "job_id";
			formatted_line.value = job_list.get(i).id();
			formatted_list.add(formatted_line);

			formatted_line = new OutputFormat();
			formatted_line.key = "created_at";
			formatted_line.value = job_list.get(i).createdAt();
			formatted_list.add(formatted_line);

			formatted_line = new OutputFormat();
			formatted_line.key = "size";
			formatted_line.value = String.valueOf(job_list.get(i).size());
			formatted_list.add(formatted_line);

		}

		return formatted_list;
	}
}
