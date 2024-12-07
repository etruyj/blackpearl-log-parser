//===================================================================
// MapActiveCifsShareToPid.java
// 	Description:
// 		Creates a HashMap of Pid to ActiveCifsShare.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.ActiveCifsShare;

import java.util.ArrayList;
import java.util.HashMap;

public class MapActiveCifsShareToPid {
	public static HashMap<String, ActiveCifsShare> createMap(ArrayList<ActiveCifsShare> share_list) {
		HashMap<String, ActiveCifsShare> share_map = new HashMap<String, ActiveCifsShare>();

        for(ActiveCifsShare share : share_list) {
            share_map.put(share.getPid(), share);
        }

        return share_map;
	}
}
