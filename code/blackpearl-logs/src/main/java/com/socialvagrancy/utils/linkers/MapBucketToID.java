//===================================================================
// MapBucketToID.java
// 	Description:
// 		Creates a 
// 		HashMap<String, Bucket> to map the bucket
// 		id to the bucket. This will allow linking the 
// 		buckets to jobs.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.outputs.Bucket;

import java.util.ArrayList;
import java.util.HashMap;

public class MapBucketToID
{
	public static HashMap<String, Bucket> createMap(ArrayList<Bucket> bucket_list)
	{
		HashMap<String, Bucket> bucket_map = new HashMap<String, Bucket>();
	
		for(int i=0; i<bucket_list.size(); i++)
		{
			bucket_map.put(bucket_list.get(i).id(), bucket_list.get(i));
		}

		return bucket_map;
	}

	public static HashMap<String, Integer> createIndexMap(ArrayList<Bucket> bucket_list)
	{
		HashMap<String, Integer> bucket_map = new HashMap<String, Integer>();

		for(int i=0; i<bucket_list.size(); i++)
		{
			bucket_map.put(bucket_list.get(i).id(), i);
		}

		return bucket_map;
	}
}
