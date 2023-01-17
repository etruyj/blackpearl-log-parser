//===================================================================
// MapPersistenceRuletoID.java
// 	Description:
// 		Creates a HashMap of id to DataPersistenceRules.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.rest.GuiDataPersistenceRules;

import java.util.HashMap;

public class MapPersistenceRuleToID
{
	public static HashMap<String, GuiDataPersistenceRules.PersistenceRule> createMap(GuiDataPersistenceRules rules)
	{
		HashMap<String, GuiDataPersistenceRules.PersistenceRule> rules_map = new HashMap<String, GuiDataPersistenceRules.PersistenceRule>();
		
		if(rules != null)
		{
			for(int i=0; i< rules.getRuleCount(); i++)
			{
				rules_map.put(rules.id(i), rules.getRule(i));
			}
		}

		return rules_map;
	}
}
