// Holds WriteChunkToTapeTask and ReadChunkFromTapeTask

package com.socialvagrancy.blackpearl.logs.structures;

import java.util.ArrayList;

public class Task
{
	public String id;
	public String type;
	public String[] chunk_id;
	public String size;
	public int copies;
	public ArrayList<StorageDomainCopy> sd_copy;

	public Task()
	{
		copies = 0;
		sd_copy = new ArrayList<StorageDomainCopy>();
		StorageDomainCopy copy = new StorageDomainCopy();
		sd_copy.add(copy);
	}

	public void nextCopy()
	{
		StorageDomainCopy copy = new StorageDomainCopy();
		sd_copy.add(copy);
	}
}
