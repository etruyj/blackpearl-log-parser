//===================================================================
// SerializeShareSummary.java
// 	Description:
// 		Converts ArrayList<ShareSummary> to 
// 		an ArrayList<OutputFormat> for display.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui.display.serializers;

import com.socialvagrancy.blackpearl.logs.structures.outputs.ShareSummary;
import com.socialvagrancy.utils.ui.structures.OutputFormat;
import java.util.ArrayList;

public class SerializeShareSummary {
	public static ArrayList<OutputFormat> toOutputFormat(ArrayList<ShareSummary> info_list) {
		ArrayList<OutputFormat> output = new ArrayList<OutputFormat>();
		OutputFormat formatted_line;

		for(int i=0; i<info_list.size(); i++) {
			formatted_line = new OutputFormat();
			formatted_line.key = "share_name";
			formatted_line.value = String.valueOf(info_list.get(i).getShareName());
			output.add(formatted_line);

			formatted_line = new OutputFormat();
			formatted_line.key = "share_type";
			formatted_line.value = info_list.get(i).getShareType();
			output.add(formatted_line);
			
            formatted_line = new OutputFormat();
			formatted_line.key = "status";
			formatted_line.value = info_list.get(i).getShareStatus();
			output.add(formatted_line);
			
            formatted_line = new OutputFormat();
			formatted_line.key = "share_read_only";

            if(info_list.get(i).getShareReadOnly()) {
                formatted_line.value = "true";
            } else {
                formatted_line.value = "false";
            }
			output.add(formatted_line);
			
            formatted_line = new OutputFormat();
			formatted_line.key = "Path";
			formatted_line.value = info_list.get(i).getPath();
            output.add(formatted_line);
			
            formatted_line = new OutputFormat();
			formatted_line.key = "volume_name";
			formatted_line.value = info_list.get(i).getVolumeName();
            output.add(formatted_line);
			
            formatted_line = new OutputFormat();
			formatted_line.key = "volume_used";
			formatted_line.value = info_list.get(i).getVolumeUsed();
            output.add(formatted_line);
			
            formatted_line = new OutputFormat();
			formatted_line.key = "volume_read_only";

            if(info_list.get(i).getVolumeReadOnly()) {
                formatted_line.value = "true";
            } else {
                formatted_line.value = "false";
            }
			output.add(formatted_line);
			
            formatted_line = new OutputFormat();
			formatted_line.key = "pool_name";
			formatted_line.value = info_list.get(i).getPoolName();
            output.add(formatted_line);
			
            formatted_line = new OutputFormat();
			formatted_line.key = "pool_total_size";
			formatted_line.value = info_list.get(i).getPoolTotal();
            output.add(formatted_line);
		}

		return output;
	}
}
