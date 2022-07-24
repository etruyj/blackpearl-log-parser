//===================================================================
// TapeOperation.java
// 	Description:
// 		This variable holds all the information on a tape
// 		operation (read/write) from tape task to the completion
// 		of the tape job.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.operations;

import java.util.ArrayList;

public class TapeOperation
{
	// Dataplanner
	public String id;
	public String request_type;
	public String[] chunk_id;
	public String drive_wwn;
	public String task_created;
	public String task_completed;
	public String task_duration;
	public String task_size;
	public String task_throughput;

	// Tape Exchange	
	public boolean already_in_drive;
	public String barcode;
	public String partition_id;
	public String source;
	public String target;
	public String mount_start;
	public String mount_end;
	public String mount_duration;

	// Tape Job
	public String size;
	public String rw_start_time;
	public String rw_end_time;
	public String rw_duration;
	public ArrayList<String> blob;

	public TapeOperation()
	{
		blob = new ArrayList<String>();
		already_in_drive = false;
	}

}
