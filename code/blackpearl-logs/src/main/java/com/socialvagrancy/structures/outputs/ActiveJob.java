//===================================================================
// ActiveJob.java
// 	Description:
// 		Holds information on active jobs.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.outputs;

import java.util.ArrayList;

public class ActiveJob
{
	public String bucket;
	public ArrayList<String> chunk;
	public int copies;
	public String created_at;
	public String id;
	public String name;
	public String next_step;
	public String owner;
	public int percent_per_chunk;
	public int percent_complete;
	public int percent_per_copy;
	public String request_type;
	public int current_state;

	public ActiveJob() { current_state = 4; }

	//=======================================
	// Functions
	//=======================================

	public String currentStatus() { return STEPS.values()[current_state].toString(); }

	//=======================================
	// Inner Classes
	//=======================================

	private enum STEPS
	{
		WRITE_CACHE("Writing to cache"),
		WRITE_POOL("Writing to pool"),
		WRITE_TAPE("Writing to tape"),
		WRITE_DS3("Writing to DS3"),
		PENDING_COMPLETE("Pending completion");

		String status;

		private STEPS(String msg)
		{
			this.status = msg;
		}

		@Override
		public String toString() { return status; }	
	}
}
