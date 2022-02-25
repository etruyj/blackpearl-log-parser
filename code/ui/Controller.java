//===================================================================
// Controller.java
// 	Description: This class calls the individual commands to allow
// 	easier management of the interface.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui;

import com.socialvagrancy.blackpearl.logs.commands.CalcJobStatistics;
import com.socialvagrancy.blackpearl.logs.structures.JobStatistics;

import java.util.ArrayList;

public class Controller
{
	public static ArrayList<JobStatistics> jobStatistics(String path)
	{
		ArrayList<JobStatistics> stat_list = CalcJobStatistics.calculate(path);
		
		CalcJobStatistics.print(stat_list);

		return stat_list;
	}
}
