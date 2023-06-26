//===================================================================
// ListBuckets.java
// 	Description:
// 		Lists the buckets, their size, and the owner
//===================================================================

package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.rest.GuiBucket;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetBuckets;
import com.socialvagrancy.blackpearl.logs.utils.linkers.GenerateBuckets;
import com.socialvagrancy.blackpearl.logs.structures.outputs.Bucket;
import com.socialvagrancy.blackpearl.logs.structures.outputs.DataPolicy;
import com.socialvagrancy.utils.storage.UnitConverter;


import java.math.BigInteger;
import java.util.ArrayList;

public class ListBuckets
{
	public static ArrayList<Bucket> fromRest(String dir_path)
	{
		String path = dir_path + "/rest/gui_ds3_buckets.json";
		GuiBucket buckets = GetBuckets.fromJson(path);
		ArrayList<DataPolicy> policy_list = ListDataPolicies.fromRest(dir_path);
		ArrayList<Bucket> bucket_list = GenerateBuckets.fromRest(buckets, policy_list);

		return bucket_list;
	}

	public static void testPrint(ArrayList<Bucket> bucket_list)
	{
		System.out.println("bucket_name,data_policy,owner,size,data_copies, local_copies, replicated_copies");
		for(int i=0; i<bucket_list.size(); i++)
		{
			System.out.println(bucket_list.get(i).name + ","
					+ bucket_list.get(i).dataPolicy() + ","
					+ bucket_list.get(i).owner + ","
					+ bucket_list.get(i).size_human + "," 
					+ bucket_list.get(i).copyCount() + ","
					+ bucket_list.get(i).localCopies() + ","
					+ bucket_list.get(i).remoteCopies());
		}
	}
}
