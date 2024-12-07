//===================================================================
// MapAzureTargetToID.java
// 	Description:
// 		Creates a 
// 		HashMap<String, int> to map the azure target
// 		id to the azure target index in GuiAzureRepTarget. 
// 		This will allow linking the azure target to the 
// 		replication rule.
//
// 		Using an <id, integer> map instead of <id, class> as
// 		there is only limited information being used and this
// 		negates the need to create a specific class for the
// 		target.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.rest.GuiAzureRepTargets;

import java.util.ArrayList;
import java.util.HashMap;

public class MapAzureTargetToID
{
	public static HashMap<String, Integer> createMap(GuiAzureRepTargets targets)
	{
		HashMap<String, Integer> target_map = new HashMap<String, Integer>();
		
		if(targets != null)
		{	
			for(int i=0; i<targets.targetCount(); i++)
			{
				target_map.put(targets.id(i), i);
			}
		}

		return target_map;
	}
}
