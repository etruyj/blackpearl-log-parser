//===================================================================
// GetTapeTasks.java
// 	Description:
// 		Reads the dataplanner-main.logs to build an ArrayList
// 		of TapeTasks.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers;

import com.socialvagrancy.blackpearl.logs.utils.inputs.LogReader;
import com.socialvagrancy.blackpearl.logs.utils.parsers.TapeTaskParser;

public class GetTapeTasks
{
	public static void main(String[] args)
	{
		TapeTaskParser task_parser = new TapeTaskParser();
		LogReader.readLog(args[0], task_parser, null);
	}
}
