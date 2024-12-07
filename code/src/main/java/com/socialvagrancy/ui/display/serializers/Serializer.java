//===================================================================
// Serializer.java
// 	Description:
// 		Takes the various output variable types and converts
// 		them to an ArrayList<OutputFormat> for parsing by the
// 		different displays.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui.display.serializers;

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
import com.socialvagrancy.utils.ui.structures.OutputFormat;

import java.util.ArrayList;

public class Serializer
{
	public static ArrayList<OutputFormat> serialize(ArrayList to_print)
	{
		if(to_print.size() >= 1)
		{
			if(to_print.get(0) instanceof ActiveJob)
			{
				return SerializeJobStatus.toOutputFormat(to_print);
			}
			else if(to_print.get(0) instanceof Bucket)
			{
				return SerializeBucketList.toOutputFormat(to_print);
			} else if(to_print.get(0) instanceof CifsConnectionSummary) {
                return SerializeCifsConnectionSummary.toOutputFormat(to_print);
            } else if(to_print.get(0) instanceof DataPolicy)
			{
				return SerializeDataPolicies.toOutputFormat(to_print);
			}
			else if(to_print.get(0) instanceof JobDetails)
			{
				return SerializeJobDetails.toOutputFormat(to_print);
			}
			else if(to_print.get(0) instanceof JobSummary)
			{
				return SerializeJobSummary.toOutputFormat(to_print);
			} else if(to_print.get(0) instanceof ShareSummary) {
                return SerializeShareSummary.toOutputFormat(to_print);
            } else if(to_print.get(0) instanceof StorageDomain)
			{
				return SerializeStorageDomains.toOutputFormat(to_print);
			}
			else if(to_print.get(0) instanceof SystemInfo)
			{
				return SerializeSystemInfo.toOutputFormat(to_print);
			} else if(to_print.get(0) instanceof TranslatedMessage) {
                return SerializeTranslatedMessage.toOutputFormat(to_print);
            } else
			{
				System.err.println("ERROR: Format of the results set is not recognized.");
			}
		}
		else
		{
			System.err.println("ERROR: No output for command.");
		}
		
		return new ArrayList<OutputFormat>();
	}
}
