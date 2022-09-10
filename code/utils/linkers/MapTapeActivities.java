//===================================================================
// MapTapeActivities.java
// 	Description: 
// 		Creates a HashMap of Drives to a TreeMap (of mount times
//		tape exchanges). This allows a segmenting a search down
//		to the specific drive, and then using TreeMap functions
//		to find the the closest tape exchange to the drive.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.linkers;

import com.socialvagrancy.blackpearl.logs.structures.TapeActivity;
import com.socialvagrancy.blackpearl.logs.structures.TapeExchange;
import com.socialvagrancy.blackpearl.logs.structures.TapeJob;
import com.socialvagrancy.blackpearl.logs.utils.BPLogDateConverter;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

public class MapTapeActivities
{
	public static <T extends TapeActivity> HashMap<String, TreeMap<LocalDateTime, T>> mapActivity(ArrayList<T> action_list)
	{
		HashMap<String, TreeMap<LocalDateTime, T>> action_map = new HashMap<String, TreeMap<LocalDateTime, T>>();
		TreeMap<LocalDateTime, T> log_tree = null;
		LocalDateTime timestamp;
		String compare_drive = "empty"; // value to be compared to split the array down into individual tree maps.
		String date;
		DateTimeFormatter excel_format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter in_format = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm:ss");
		
		// Order the array list by tape drive;
		Collections.sort(action_list, (T t1, T t2) -> t1.drive_wwn.compareTo(t2.drive_wwn));
		
		for(int i=0; i < action_list.size(); i++)
		{
			// Add log_tree to hashmap and create new log_tree
			if(!action_list.get(i).drive_wwn.equals(compare_drive))
			{
				if(log_tree != null)
				{
					action_map.put(compare_drive, log_tree);
				}

				compare_drive = action_list.get(i).drive_wwn;
				log_tree = new TreeMap<LocalDateTime, T>();
			}

			if(action_list.get(i).start_time != null)
			{
				// Convert the start_tiem into a time_stamp for searching in the tree.
				// As the logs don't include a year field, the current year is grabbed
				// from the file.
				// 
				// Converted to date first to use the BPLogDateConverter to ensure the
				// timestamp is in a standard format. This includes adding a year and
				// adding a leading 0 to single digit dates. The output here is a string,
				// so it needs to be converted back into LocalDateTime for the TreeMap. 
				date = BPLogDateConverter.formatTapeBackendTimestamp(action_list.get(i).start_time);		
				timestamp = LocalDateTime.parse(date, excel_format);

				log_tree.put(timestamp, action_list.get(i));
			}

		}
		
		// Add the last tree to the list.
		action_map.put(compare_drive, log_tree);
		
		return action_map;
	}
}
