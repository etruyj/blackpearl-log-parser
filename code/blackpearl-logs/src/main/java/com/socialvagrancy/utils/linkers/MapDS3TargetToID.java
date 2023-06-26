//===================================================================
// MapDS3TargetToID.java
// 	Description:
// 		Creates a 
// 		HashMap<String, int> to map the ds3 target
// 		id to the ds3 target index in GuiDS3RepTarget. 
// 		This will allow linking the ds3 target to the 
// 		replication rule.
//
// 		Using an <id, integer> map instead of <id, class> as
// 		there is only limited information being used and this
// 		negates the need to create a specific class for the
// 		target.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.rest.GuiDS3RepTargets;

import java.util.ArrayList;
import java.util.HashMap;

public class MapDS3TargetToID
{
	public static HashMap<String, Integer> createMap(GuiDS3RepTargets targets)
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
