// Holds WriteChunkToTapeTask and ReadChunkFromTapeTask

package com.socialvagrancy.blackpearl.logs.structures;

public class Task
{
	public String id;
	public String type;
	public String[] chunk_id;
	public String drive_wwn;
	public String created_at;
	public String date_completed;
	public String throughput;
}
