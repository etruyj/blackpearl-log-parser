//===================================================================
// SerializeTranslatedMessage.java
// 	Description:
// 		Converts ArrayList<TranslatedMessage> to 
// 		an ArrayList<OutputFormat> for display.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui.display.serializers;

import com.socialvagrancy.blackpearl.logs.structures.TranslatedMessage;
import com.socialvagrancy.utils.ui.structures.OutputFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SerializeTranslatedMessage {
	public static ArrayList<OutputFormat> toOutputFormat(ArrayList<TranslatedMessage> info_list) {
		ArrayList<OutputFormat> output = new ArrayList<OutputFormat>();
		OutputFormat formatted_line;

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

		for(int i=0; i<info_list.size(); i++) {
			formatted_line = new OutputFormat();
			formatted_line.key = "id";
			formatted_line.value = String.valueOf(info_list.get(i).getId());
			output.add(formatted_line);

			formatted_line = new OutputFormat();
			formatted_line.key = "created";
			formatted_line.value = info_list.get(i).getCreated().format(format);
			output.add(formatted_line);
			
            formatted_line = new OutputFormat();
			formatted_line.key = "severity";
			formatted_line.value = info_list.get(i).getSeverity();
			output.add(formatted_line);
			
            formatted_line = new OutputFormat();
			formatted_line.key = "description";
			formatted_line.value = info_list.get(i).getDescription();
			output.add(formatted_line);
			
            formatted_line = new OutputFormat();
			formatted_line.key = "details";
			if(info_list.get(i).getDetails() != null) {
                formatted_line.value = info_list.get(i).getDetails();
            } else {
                formatted_line.value = "";
            }
			output.add(formatted_line);
		}

		return output;
	}
}
