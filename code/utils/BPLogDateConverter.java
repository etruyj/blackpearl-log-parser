//===================================================================
// BPLogDateConverter.java
// 	Description: A script to handle formatting the timestamp of
// 	BlackPearl logs to a standard format. This is necessary as
// 	different logs present timestamps in different formats and 
// 	handling each as they arrive is creating a lot of overhead
// 	in the code.
//
// 	Bonus: This script can also determine duration between formatted
// 	timestamps.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.Duration;

public class BPLogDateConverter
{
	public static String calcDuration(String t1, String t2)
	{
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm:ss");
		LocalDateTime start = LocalDateTime.parse(t1, format);
		LocalDateTime end = LocalDateTime.parse(t2, format);

		Duration duration = Duration.between(start, end);

		long seconds = duration.toSeconds() % 60;
		long minutes = duration.toMinutes() % 60;
		long hours = duration.toHours();

		return hours + ":" + minutes + ":" + seconds;
	}

	public static String formatCompletedJobsTimestamp(String timestamp)
	{
		DateTimeFormatter in_format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter out_format = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm:ss");
		// Clean the JSON output to something LocalDateTime can parse.
		timestamp = timestamp.replace("T", " ");
		String[] cleaned_time = timestamp.split("\\.");

		LocalDateTime stamp = LocalDateTime.parse(cleaned_time[0], in_format);

		return stamp.format(out_format);
	}

	public static LocalDateTime formatDataPlannerTimestamp(String timestamp)
	{
		timestamp = reconstituteTimestamp(timestamp.substring(0, timestamp.length()-4));
		DateTimeFormatter out_format = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm:ss");

		return LocalDateTime.parse(timestamp, out_format);
		
	}

	public static LocalDateTime formatTapeBackendTimestamp(String timestamp)
	{
		if(timestamp == null)
		{
			return null;
		}
		else
		{
			timestamp = reconstituteTimestamp(timestamp);
			DateTimeFormatter out_format = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm:ss");

			return LocalDateTime.parse(timestamp, out_format);
		}
	}

	//=======================================
	// Internal Functions
	//=======================================
	
	// add the year to the time stamp.
	private static String addYear(String timestamp)
	{
		// This adds the year to the string for parsing as 
		// local date time needs the year. This value is mostly
		// superfluous, the only thing to parse is if the dates span
		// a new year for duration sake. 
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm:ss");
		LocalDateTime stamp = LocalDateTime.parse(year + " " + timestamp, format);

		if(now.getMonthValue() == 1 && stamp.getMonthValue() == 12)
		{
			return (year - 1) + " " + timestamp;
		}

		return year + " " + timestamp;

	}

	private static String reconstituteTimestamp(String timestamp)
	{
		// Works for timestamps in MMM (D) HH:mm:ss format

		timestamp = timestamp.trim();	
		String[] time = timestamp.split(" ");
	
		// time.length == 4 and NOT 3
		// because the split(" ") is adding a 
		// fourth index that is null.	
		if(time.length >= 3)
		{
			if(time[1].length() == 0)
			{
				timestamp = time[0].trim() + " 0" + time[2].trim() + " " + time[3].trim();
			}
			else
			{
				timestamp = time[0].trim() + " " + time[1].trim() + " " + time[2].trim();
			}
		}
		else
		{
			return null;
		}

		timestamp = addYear(timestamp);

		return timestamp;
	}
}
