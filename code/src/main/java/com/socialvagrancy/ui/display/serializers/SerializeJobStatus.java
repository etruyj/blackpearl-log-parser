//===================================================================
// SerializeJobStatus.java
// 	Description:
// 		Converts ArrayList<ActiveJobStatus> to an
// 		ArrayList<OutputFormat>
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui.display.serializers;

import com.socialvagrancy.blackpearl.logs.structures.outputs.ActiveJob;
import com.socialvagrancy.utils.ui.structures.OutputFormat;
import java.util.ArrayList;

public class SerializeJobStatus
{
	public static ArrayList<OutputFormat> toOutputFormat(ArrayList<ActiveJob> job_list)
	{
		ArrayList<OutputFormat> output = new ArrayList<OutputFormat>();
		OutputFormat format;

		for(int i=0; i<job_list.size(); i++)
		{
			format = new OutputFormat();
			format.key = "name";
			format.value = job_list.get(i).name;
			output.add(format);
			format = new OutputFormat();
			format.key = "request_type";
			format.value = job_list.get(i).request_type;
			output.add(format);
			format = new OutputFormat();
			format.key = "bucket";
			format.value = job_list.get(i).bucket;
			output.add(format);
			format = new OutputFormat();
			format.key = "user";
			format.value = job_list.get(i).owner;
			output.add(format);
			format = new OutputFormat();
			format.key = "created_at";
			format.value = job_list.get(i).created_at;
			output.add(format);
			format = new OutputFormat();
			format.key = "percent_complete";
			format.value = job_list.get(i).percent_complete + "%";
			output.add(format);
			format = new OutputFormat();
			format.key = "current_status";
			format.value = job_list.get(i).currentStatus();
			output.add(format);
		}

		return output;
	}
}
