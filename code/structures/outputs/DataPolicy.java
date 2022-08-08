//===================================================================
// DataPolicy.java
// 	Description:
// 		A container class for data policies and their
// 		data persistence rules.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.outputs;

import java.util.ArrayList;

public class DataPolicy
{
	public String name;
	public String id;
	public String checksum;
	public ArrayList<StorageDomain> domains_list;

	public DataPolicy()
	{
		domains_list = new ArrayList<StorageDomain>();
	}

	//=======================================
	// Functions
	//=======================================
	
	public int getStorageDomainCount()
	{
		return domains_list.size();
	}

	public void addPersistenceRule(StorageDomain domain)
	{
		domains_list.add(domain);
	}
}
