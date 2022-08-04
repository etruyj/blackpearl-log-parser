//===================================================================
// ListBuckets.java
// 	Description:
// 		Lists the buckets, their size, and the owner
//===================================================================

package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.rest.GuiBucket;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetBuckets;
import com.socialvagrancy.utils.storage.UnitConverter;

import java.math.BigInteger;

public class ListBuckets
{
	public static GuiBucket fromRest(String dir_path)
	{
		String path = dir_path + "/rest/gui_ds3_buckets.json";
		GuiBucket buckets = GetBuckets.fromJson(path);
		
		testPrint(buckets);

		return buckets;
	}

	public static void testPrint(GuiBucket buckets)
	{
		for(int i=0; i<buckets.bucketCount(); i++)
		{
			System.out.println(buckets.getName(i) + "\t"
					+ buckets.getOwner(i) + "\t"
					+ UnitConverter.bytesToHumanReadable(buckets.getSize(i)));
		}
	}
}
