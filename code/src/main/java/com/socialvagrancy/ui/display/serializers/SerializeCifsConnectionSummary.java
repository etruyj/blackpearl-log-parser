//===================================================================
// SerializeCifsConnectionSummary.java
// 	Description:
// 		Converts ArrayList<CifsConnectionSummary> to 
// 		an ArrayList<OutputFormat> for display.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui.display.serializers;

import com.socialvagrancy.blackpearl.logs.structures.outputs.CifsConnectionSummary;
import com.socialvagrancy.utils.ui.structures.OutputFormat;
import java.util.ArrayList;

public class SerializeCifsConnectionSummary {
	public static ArrayList<OutputFormat> toOutputFormat(ArrayList<CifsConnectionSummary> info_list) {
		ArrayList<OutputFormat> output = new ArrayList<OutputFormat>();
		OutputFormat formatted_line;

		for(int i=0; i<info_list.size(); i++) {
			formatted_line = new OutputFormat();
			formatted_line.key = "share_name";
			formatted_line.value = String.valueOf(info_list.get(i).getShareName());
			output.add(formatted_line);

			formatted_line = new OutputFormat();
			formatted_line.key = "connected_host";
			formatted_line.value = info_list.get(i).getConnectedHost();
			output.add(formatted_line);
			
            formatted_line = new OutputFormat();
			formatted_line.key = "user";
			formatted_line.value = info_list.get(i).getUser();
			output.add(formatted_line);
			
            formatted_line = new OutputFormat();
			formatted_line.key = "group";
			formatted_line.value = info_list.get(i).getGroup();
			output.add(formatted_line);
			
            formatted_line = new OutputFormat();
			formatted_line.key = "connected_at";
			formatted_line.value = info_list.get(i).getConnectedAt();
            output.add(formatted_line);
			
            formatted_line = new OutputFormat();
			formatted_line.key = "protocol";
			formatted_line.value = info_list.get(i).getProtocol();
            output.add(formatted_line);
			
            formatted_line = new OutputFormat();
			formatted_line.key = "encryption";
			formatted_line.value = info_list.get(i).getEncryption();
            output.add(formatted_line);
			
            formatted_line = new OutputFormat();
			formatted_line.key = "signing";
			formatted_line.value = info_list.get(i).getSigning();
			output.add(formatted_line);
		}

		return output;
	}
}
