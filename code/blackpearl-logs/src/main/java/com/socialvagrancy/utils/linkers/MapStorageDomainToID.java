//===================================================================
// MapStorageDomainToID.java
// 	Description:
// 		Creates a 
// 		HashMap<String, StorageDomain> to map the storage domain
// 		id to the storage domain. This will allow linking the 
// 		members to the respective Storage Domains.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.outputs.StorageDomain;

import java.util.ArrayList;
import java.util.HashMap;

public class MapStorageDomainToID
{
	public static HashMap<String, StorageDomain> createMap(ArrayList<StorageDomain> domain_list)
	{
		HashMap<String, StorageDomain> domain_map = new HashMap<String, StorageDomain>();
	
		for(int i=0; i<domain_list.size(); i++)
		{
			domain_map.put(domain_list.get(i).getID(), domain_list.get(i));
		}

		return domain_map;
	}
}
