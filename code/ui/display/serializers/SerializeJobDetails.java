//===================================================================
// SerializeJobDetails.java
// 	Description:
//		Converts the ArrayList<JobDetails> to an 
//		ArrayList<OutputFormat>
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui.display.serializers;

import com.socialvagrancy.blackpearl.logs.structures.outputs.JobDetails;
import com.socialvagrancy.utils.ui.structures.OutputFormat;

import java.util.ArrayList;

public class SerializeJobDetails
{
	public static ArrayList<OutputFormat> toOutputFormat(ArrayList<JobDetails> details_list)
	{
		ArrayList<OutputFormat> formatted_list = new ArrayList<OutputFormat>();
		OutputFormat formatted_line;

		for(int i=0; i<details_list.size(); i++)
		{
			// Disk Tasks
			for(String chunk : details_list.get(i).pool_copies.keySet())
			{
				for(int j=0; j<details_list.get(i).poolCopyCount(chunk); j++)
				{
					// Standard values for every line
					formatted_line = new OutputFormat();
					formatted_line.key = "job_name";
					formatted_line.value = details_list.get(i).name();
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "request_type";
					formatted_line.value = details_list.get(i).type();
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "bucket_name";
					formatted_line.value = details_list.get(i).bucket;
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "size";
					formatted_line.value = details_list.get(i).human_readable_size;
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "user";
					formatted_line.value = details_list.get(i).owner();
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "created_at";
					formatted_line.value = details_list.get(i).createdAt();
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "date_completed";
					formatted_line.value = details_list.get(i).createdAt();
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "duration";
					formatted_line.value = details_list.get(i).job_duration;
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "chunk";
					formatted_line.value = chunk;
					formatted_list.add(formatted_line);
				
					// Task specific requests
					formatted_line = new OutputFormat();
					formatted_line.key = "task";
					formatted_line.value = details_list.get(i).poolTaskID(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "type";
					formatted_line.value = "Pool";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "task_start";
					formatted_line.value = details_list.get(i).poolTaskStart(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "task_end";
					formatted_line.value = details_list.get(i).poolTaskEnd(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "task_duration";
					formatted_line.value = details_list.get(i).poolTaskDuration(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "task_size";
					formatted_line.value = details_list.get(i).poolTaskSize(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "throughput";
					formatted_line.value = details_list.get(i).poolTaskThroughput(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "source/target";
					formatted_line.value = details_list.get(i).poolName(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "drive_sn";
					// No Key. BLANK for pools, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "drive_number";
					// No Key. BLANK for pools, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "bar_code";
					// No Key. BLANK for pools, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "already_in_drive";
					// No Key. BLANK for pool, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "mount_start";
					// No Key. BLANK for pools, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "mount_end";
					// No Key. BLANK for pools, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "mount_duration";
					// No Key. BLANK for pools, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "data_start";
					// No Key. BLANK for pools, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "data_end";
					// No Key. BLANK for pools, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "data_duration";
					// No Key. BLANK for pools, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
				}
			}

			// DS3 Tasks
			for(String chunk : details_list.get(i).ds3_copies.keySet())
			{
				for(int j=0; j<details_list.get(i).ds3CopyCount(chunk); j++)
				{
					// Standard values for every line
					formatted_line = new OutputFormat();
					formatted_line.key = "job_name";
					formatted_line.value = details_list.get(i).name();
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "request_type";
					formatted_line.value = details_list.get(i).type();
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "bucket_name";
					formatted_line.value = details_list.get(i).bucket;
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "size";
					formatted_line.value = details_list.get(i).human_readable_size;
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "user";
					formatted_line.value = details_list.get(i).owner();
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "created_at";
					formatted_line.value = details_list.get(i).createdAt();
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "date_completed";
					formatted_line.value = details_list.get(i).createdAt();
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "duration";
					formatted_line.value = details_list.get(i).job_duration;
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "chunk";
					formatted_line.value = chunk;
					formatted_list.add(formatted_line);
				
					// Task specific requests
					formatted_line = new OutputFormat();
					formatted_line.key = "task";
					formatted_line.value = details_list.get(i).ds3TaskID(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "type";
					formatted_line.value = "DS3";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "task_start";
					formatted_line.value = details_list.get(i).ds3TaskStart(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "task_end";
					formatted_line.value = details_list.get(i).ds3TaskEnd(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "task_duration";
					formatted_line.value = details_list.get(i).ds3TaskDuration(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "task_size";
					formatted_line.value = details_list.get(i).ds3TaskSize(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "throughput";
					formatted_line.value = details_list.get(i).ds3TaskThroughput(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "source/target";
					formatted_line.value = details_list.get(i).ds3Name(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "drive_sn";
					// No Key. BLANK for ds3, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "drive_number";
					// No Key. BLANK for ds3, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "bar_code";
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "already_in_drive";
					// No Key. BLANK for ds3, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "mount_start";
					// No Key. BLANK for ds3, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "mount_end";
					// No Key. BLANK for ds3, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "mount_duration";
					// No Key. BLANK for ds3, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "data_start";
					// No Key. BLANK for ds3, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "data_end";
					// No Key. BLANK for ds3, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "data_duration";
					// No Key. BLANK for ds3, but needed for tape drives.
					formatted_line.value = "";
					formatted_list.add(formatted_line);
				}
			}
		
			// Tape Tasks
			for(String chunk : details_list.get(i).tape_copies.keySet())
			{
				for(int j=0; j<details_list.get(i).tapeCopyCount(chunk); j++)
				{
					// Standard values for every line
					formatted_line = new OutputFormat();
					formatted_line.key = "job_name";
					formatted_line.value = details_list.get(i).name();
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "request_type";
					formatted_line.value = details_list.get(i).type();
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "bucket_name";
					formatted_line.value = details_list.get(i).bucket;
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "size";
					formatted_line.value = details_list.get(i).human_readable_size;
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "user";
					formatted_line.value = details_list.get(i).owner();
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "created_at";
					formatted_line.value = details_list.get(i).createdAt();
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "date_completed";
					formatted_line.value = details_list.get(i).createdAt();
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "duration";
					formatted_line.value = details_list.get(i).job_duration;
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "chunk";
					formatted_line.value = chunk;
					formatted_list.add(formatted_line);
				
					// Task specific requests
					formatted_line = new OutputFormat();
					formatted_line.key = "task";
					formatted_line.value = details_list.get(i).tapeTaskID(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "type";
					formatted_line.value = "Tape";
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "task_start";
					formatted_line.value = details_list.get(i).tapeTaskStart(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "task_end";
					formatted_line.value = details_list.get(i).tapeTaskEnd(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "task_duration";
					formatted_line.value = details_list.get(i).tapeTaskDuration(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "task_size";
					formatted_line.value = details_list.get(i).tapeTaskSize(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "throughput";
					formatted_line.value = details_list.get(i).tapeTaskThroughput(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "source/target";
					formatted_line.value = details_list.get(i).tapePartition(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "drive_sn";
					formatted_line.value = details_list.get(i).tapeDriveSN(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "drive_number";
					formatted_line.value = details_list.get(i).tapeDriveNumber(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "bar_code";
					formatted_line.value = details_list.get(i).tapeBarcode(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "already_in_drive";
					formatted_line.value = details_list.get(i).tapeInDrive(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "mount_start";
					formatted_line.value = details_list.get(i).tapeMountStart(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "mount_end";
					formatted_line.value = details_list.get(i).tapeMountEnd(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "mount_duration";
					formatted_line.value = details_list.get(i).tapeMountDuration(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "data_start";
					formatted_line.value = details_list.get(i).tapeDataStart(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "data_end";
					formatted_line.value = details_list.get(i).tapeDataEnd(chunk, j);
					formatted_list.add(formatted_line);
					formatted_line = new OutputFormat();
					formatted_line.key = "data_duration";
					formatted_line.value = details_list.get(i).tapeDataDuration(chunk, j);
					formatted_list.add(formatted_line);
				}
			}
		}
		
		return formatted_list;
	}
}
