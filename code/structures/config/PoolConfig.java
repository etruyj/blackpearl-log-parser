//===================================================================
// PoolConfig.java
// 	Description:
// 		Holds output information for BlackPearl pool infomration
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.config;

public class PoolConfig
{
	public String name;
	public String type;
	public String high_water_mark;
	public boolean power_saving_mode;
	public int drive_count;
	public String protection_level;
	public int stripes;
	public boolean write_performance_drives;
	public boolean metadata_drives;

	public PoolConfig(String n, String t, String h, String psm, int disks, String protection, int arrays)
	{
		name = n;
		type = t;
		high_water_mark = h;

		if(psm.equals("disabled"))
		{
			power_saving_mode = false;
		}
		else
		{
			power_saving_mode = true;
		}

		drive_count = disks;
		protection_level = protection;
		stripes = arrays;
	}
}
