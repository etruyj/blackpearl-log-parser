//===================================================================
// Controller.java
// 	Description: This class calls the individual commands to allow
// 	easier management of the interface.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui;

import com.socialvagrancy.blackpearl.logs.commands.ActiveJobStatus;
import com.socialvagrancy.blackpearl.logs.commands.CalcJobStats;
import com.socialvagrancy.blackpearl.logs.commands.GatherActiveJobDetails;
import com.socialvagrancy.blackpearl.logs.commands.GatherCompletedJobDetails;
import com.socialvagrancy.blackpearl.logs.commands.GetSystemInfo;
import com.socialvagrancy.blackpearl.logs.commands.ListBuckets;
import com.socialvagrancy.blackpearl.logs.commands.ListDataPolicies;
import com.socialvagrancy.blackpearl.logs.commands.ListStorageDomains;
import com.socialvagrancy.blackpearl.logs.structures.outputs.ActiveJob;
import com.socialvagrancy.blackpearl.logs.structures.outputs.Bucket;
import com.socialvagrancy.blackpearl.logs.structures.outputs.DataPolicy;
import com.socialvagrancy.blackpearl.logs.structures.outputs.JobDetails;
import com.socialvagrancy.blackpearl.logs.structures.outputs.StorageDomain;

import java.util.ArrayList;

public class Controller
{
	public static void activeJobDetails(String path)
	{
		GatherActiveJobDetails.fromLogs(path, null);
	}

	public static void activeJobStatus(String path)
	{
		ArrayList<ActiveJob> job_list = ActiveJobStatus.fromRest(path);
		ActiveJobStatus.testPrint(job_list);
	}

	public static ArrayList<JobDetails> completedJobDetails(String path)
	{
		return GatherCompletedJobDetails.forCompletedJobs(path, null);
	}

	public static ArrayList<Bucket> listBuckets(String path)
	{
		return ListBuckets.fromRest(path);
	}

	public static void listDataPolicies(String path)
	{
		ArrayList<DataPolicy> policy_list = ListDataPolicies.fromRest(path);
		ListDataPolicies.testPrint(policy_list);
	}

	public static void listStorageDomains(String path)
	{
		ArrayList<StorageDomain> domain_list = ListStorageDomains.fromRest(path);
		ListStorageDomains.testPrint(domain_list);
	}

	public static void systemInfo(String path)
	{
		GetSystemInfo.fromJson(path);
	}
}
