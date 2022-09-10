//===================================================================
// Serializer.java
// 	Description:
// 		Takes the various output variable types and converts
// 		them to an ArrayList<OutputFormat> for parsing by the
// 		different displays.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui.display.serializers;

import com.socialvagrancy.blackpearl.logs.structures.outputs.Bucket;
import com.socialvagrancy.blackpearl.logs.structures.outputs.JobDetails;
import com.socialvagrancy.utils.ui.structures.OutputFormat;

import java.util.ArrayList;

public class Serializer
{
	public static ArrayList<OutputFormat> bucketList(ArrayList<Bucket> bucket_list)
	{
		return SerializeBucketList.toOutputFormat(bucket_list);
	}

	public static ArrayList<OutputFormat> completedJobDetails(ArrayList<JobDetails> details_list)
	{
		return SerializeJobDetails.toOutputFormat(details_list);
	}

	public static ArrayList<OutputFormat> serialize(ArrayList to_print)
	{
		if(to_print.size() >= 1)
		{
			if(to_print.get(0) instanceof Bucket)
			{
				return bucketList(to_print);
			}
			else if(to_print.get(0) instanceof JobDetails)
			{
				return completedJobDetails(to_print);
			}
		}

		return new ArrayList<OutputFormat>();
	}
}
