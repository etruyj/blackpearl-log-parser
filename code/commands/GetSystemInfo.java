
package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.rest.Nodes;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetNodes;

public class GetSystemInfo
{
	public static void fromJson(String dir_path)
	{
		String path = dir_path + "/rest/nodes.json";
		Nodes node = GetNodes.fromJson(path);
		System.out.println("Name: " + node.getName());
		System.out.println("Serial: " + node.getSerial());
		System.out.println("Ver: " + node.getVersion());
	}
}
