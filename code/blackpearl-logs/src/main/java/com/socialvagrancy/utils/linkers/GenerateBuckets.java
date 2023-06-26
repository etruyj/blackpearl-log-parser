//===================================================================
// GenerateBuckets.java
// 	Description:
// 		Takes the GuiBucket and the DataPolicy and creates
// 		a single Bucket object that can be used for output
// 		or further functions.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.outputs.Bucket;
import com.socialvagrancy.blackpearl.logs.structures.outputs.DataPolicy;
import com.socialvagrancy.blackpearl.logs.structures.rest.GuiBucket;
import com.socialvagrancy.utils.storage.UnitConverter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class GenerateBuckets
{
	public static ArrayList<Bucket> fromRest(GuiBucket buckets, ArrayList<DataPolicy> policy_list)
	{
		ArrayList<Bucket> bucket_list = new ArrayList<Bucket>();
		HashMap<String, DataPolicy> policy_map = MapDataPolicyToID.createMap(policy_list);
		Bucket bucket;

		for(int i=0; i<buckets.bucketCount(); i++)
		{
			bucket = new Bucket();
		
			bucket.name = buckets.getName(i);
			bucket.id = buckets.getID(i);
			bucket.owner = buckets.getOwner(i);
			bucket.size_human = UnitConverter.bytesToHumanReadable(buckets.getSize(i));
			bucket.attachPolicy(policy_map.get(buckets.getDataPolicyID(i)));

			bucket_list.add(bucket);
		}

		return bucket_list;
	}
}
