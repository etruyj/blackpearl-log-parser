//===================================================================
// SerializeSystemInfo.java
// 	Description:
// 		Converts ArrayList<SystemInfo> to 
// 		an ArrayList<OutputFormat> for display.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui.display.serializers;

import com.socialvagrancy.blackpearl.logs.structures.outputs.SystemInfo;
import com.socialvagrancy.utils.ui.structures.OutputFormat;
import java.util.ArrayList;

public class SerializeSystemInfo
{
	public static ArrayList<OutputFormat> toOutputFormat(ArrayList<SystemInfo> info_list)
	{
		ArrayList<OutputFormat> output = new ArrayList<OutputFormat>();
		OutputFormat formatted_line;

		for(int i=0; i<info_list.size(); i++)
		{
			formatted_line = new OutputFormat();
			formatted_line.key = "hostname";
			formatted_line.value = info_list.get(i).hostname;
			output.add(formatted_line);
			formatted_line = new OutputFormat();
			formatted_line.key = "serial_number";
			formatted_line.value = info_list.get(i).serial_number;
			output.add(formatted_line);
			formatted_line = new OutputFormat();
			formatted_line.key = "firmware_version";
			formatted_line.value = info_list.get(i).firmware_version;
			output.add(formatted_line);
		}

		return output;
	}
}
