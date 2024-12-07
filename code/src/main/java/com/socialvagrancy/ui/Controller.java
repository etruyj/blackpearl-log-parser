//===================================================================
// Controller.java
// 	Description: This class calls the individual commands to allow
// 	easier management of the interface.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui;

import com.socialvagrancy.blackpearl.logs.commands.ActiveJobStatus;
import com.socialvagrancy.blackpearl.logs.commands.DebugCheckCompletedJobCount;
import com.socialvagrancy.blackpearl.logs.commands.FetchConfig;
import com.socialvagrancy.blackpearl.logs.commands.GatherCompletedJobDetails;
import com.socialvagrancy.blackpearl.logs.commands.GetSystemInfo;
import com.socialvagrancy.blackpearl.logs.commands.ListBuckets;
import com.socialvagrancy.blackpearl.logs.commands.ListCifsConnections;
import com.socialvagrancy.blackpearl.logs.commands.ListDataPolicies;
import com.socialvagrancy.blackpearl.logs.commands.ListMessages;
import com.socialvagrancy.blackpearl.logs.commands.ListShares;
import com.socialvagrancy.blackpearl.logs.commands.ListStorageDomains;
import com.socialvagrancy.blackpearl.logs.structures.config.Configuration;
import com.socialvagrancy.blackpearl.logs.structures.outputs.ActiveJob;
import com.socialvagrancy.blackpearl.logs.structures.outputs.Bucket;
import com.socialvagrancy.blackpearl.logs.structures.outputs.CifsConnectionSummary;
import com.socialvagrancy.blackpearl.logs.structures.outputs.DataPolicy;
import com.socialvagrancy.blackpearl.logs.structures.outputs.JobDetails;
import com.socialvagrancy.blackpearl.logs.structures.outputs.JobSummary;
import com.socialvagrancy.blackpearl.logs.structures.outputs.ShareSummary;
import com.socialvagrancy.blackpearl.logs.structures.outputs.StorageDomain;
import com.socialvagrancy.blackpearl.logs.structures.outputs.SystemInfo;
import com.socialvagrancy.blackpearl.logs.structures.TranslatedMessage;

import java.util.ArrayList;

public class Controller
{
	public static ArrayList<ActiveJob> activeJobStatus(String path)
	{
		return ActiveJobStatus.fromRest(path);
	}

	public static ArrayList<JobSummary> debugMissingJobs(String path)
	{
		return DebugCheckCompletedJobCount.findMissingJobs(path);
	}

	public static ArrayList<JobDetails> completedJobDetails(String path)
	{
		return GatherCompletedJobDetails.forCompletedJobs(path, null);
	}

	public static ArrayList<Configuration> fetchConfiguration(String path)
	{
		return FetchConfig.fromRest(path);
	}

	public static ArrayList<Bucket> listBuckets(String path)
	{
		return ListBuckets.fromRest(path);
	}

    public static ArrayList<CifsConnectionSummary> listCifsConnections(String path) {
        return ListCifsConnections.active(path);
    }

	public static ArrayList<DataPolicy> listDataPolicies(String path)
	{
		return ListDataPolicies.fromRest(path);
	}

    public static ArrayList<TranslatedMessage> listMessages(String path) {
        return ListMessages.allTranslatedMessages(path);
    }

    public static ArrayList<ShareSummary> listShares(String path) {
        return ListShares.all(path);
    }

	public static ArrayList<StorageDomain> listStorageDomains(String path)
	{
		return ListStorageDomains.fromRest(path);
	}

	public static ArrayList<SystemInfo> systemInfo(String path)
	{
		return GetSystemInfo.fromJson(path);
	}
}
