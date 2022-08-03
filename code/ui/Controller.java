//===================================================================
// Controller.java
// 	Description: This class calls the individual commands to allow
// 	easier management of the interface.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui;

import com.socialvagrancy.blackpearl.logs.commands.CalcJobStats;
import com.socialvagrancy.blackpearl.logs.commands.GatherCompletedJobDetails;
import com.socialvagrancy.blackpearl.logs.commands.GetSystemInfo;
import com.socialvagrancy.blackpearl.logs.structures.JobStatistics;

import java.util.ArrayList;

public class Controller
{
	public static ArrayList<JobStatistics> jobStatistics(String path)
	{
		CalcJobStats.forCompletedJobs(path, null);
	//	ArrayList<JobStatistics> stat_list = CalcJobStats.fromCompletedJobs(path, null);
		
	//	CalcJobStatistics.print(stat_list);

		return null;
	}

	public static void completedJobDetails(String path)
	{
		GatherCompletedJobDetails.forCompletedJobs(path, null);
	}

	public static void systemInfo(String path)
	{
		GetSystemInfo.fromJson(path);
	}
}
