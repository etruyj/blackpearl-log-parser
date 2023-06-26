//===================================================================
// MapVolumetoID.java
// 	Description:
// 		Creates a HashMap of VolumeID to VolumenData.
// 		Actually uses partition id instead of pool id as
// 		partition id is the UUID.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.rest.Volumes;

import java.util.ArrayList;
import java.util.HashMap;

public class MapVolumeToID
{
	public static HashMap<String, Volumes.Volume> createMap(Volumes vol)
	{
		HashMap<String, Volumes.Volume> volume_map = new HashMap<String, Volumes.Volume>();

		if(vol != null)
		{
			for(int i=0; i< vol.volumeCount(); i++)
			{
				volume_map.put(vol.id(i), vol.volumeData(i));
			}
		}

		return volume_map;
	}
}
