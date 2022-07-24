//===================================================================
// JobDetails.java
// 	Description:
// 		This holds the final detailed information for the
// 		assembled job.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.outputs;

import com.socialvagrancy.blackpearl.logs.structures.operations.TapeOperation;
import com.socialvagrancy.blackpearl.logs.structures.CompletedJob;

import java.util.ArrayList;
import java.util.HashMap;

public class JobDetails
{
	public CompletedJob.JobInfo job_info;
	public HashMap<String, ArrayList<TapeOperation>> tape_copies;

	public JobDetails()
	{
		tape_copies = new HashMap<String, ArrayList<TapeOperation>>();
	}

	//=======================================
	// Getters
	//=======================================

	public int tapeCopyCount(String chunk) { return tape_copies.get(chunk).size(); }
}
