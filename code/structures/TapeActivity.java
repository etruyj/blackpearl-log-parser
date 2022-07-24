//===================================================================
// TapeExchange.java
// 	Description: Super-class for tape_backend.log information to
// 	allow preparation and sorting of the data for attachment to
// 	jobs. 
//
// 	Behavior: Allows sorting by tape drive.
//
// 	Sub-classes:
// 		- TapeExchange.java
// 		- TapeJob.java
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures;

public class TapeActivity
{
	public String drive_wwn;
	public String start_time;
	public String end_time;

	public int compareTo(TapeExchange e)
	{
		return this.drive_wwn.compareTo(e.drive_wwn);
	}
}
