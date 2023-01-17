//===================================================================
// GetSystemInfo.java
// 	Description:
// 		Parses the logs to get necessary software information
// 		from the logs. Uses an ArrayList to standardize output
// 		to the display code even though there will only be
// 		one element in the list.
//===================================================================


package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.outputs.SystemInfo;
import com.socialvagrancy.blackpearl.logs.structures.rest.Nodes;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetNodes;
import java.util.ArrayList;

public class GetSystemInfo
{
	public static ArrayList<SystemInfo> fromJson(String dir_path)
	{
		ArrayList<SystemInfo> system_info = new ArrayList<SystemInfo>();
		SystemInfo info = new SystemInfo();

		String path = dir_path + "/rest/nodes.json";
		Nodes node = GetNodes.fromJson(path);
		
		info.hostname = node.getName();
		info.serial_number = node.getSerial();
		info.firmware_version = node.getVersion();
	
		system_info.add(info);

		return system_info;
	}
}
