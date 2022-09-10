//===================================================================
// MapTapePartitionToSN.java
// 	Description:
// 		Creates a HashMap of Partition SN to Partition.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.rest.GuiTapeLibraryPartitions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapTapePartitionToSN
{
	public static HashMap<String, GuiTapeLibraryPartitions.TapePartition> createMap(GuiTapeLibraryPartitions tape_partitions)
	{
		HashMap<String, GuiTapeLibraryPartitions.TapePartition> partition_map = new HashMap<String, GuiTapeLibraryPartitions.TapePartition>();

		for(int i=0; i<tape_partitions.partitionCount(); i++)
		{
			partition_map.put(tape_partitions.serialNumber(i), tape_partitions.data[i]);
		}

		return partition_map;
	}
}
