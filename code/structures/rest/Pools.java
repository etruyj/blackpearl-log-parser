//===================================================================
// Pools.java
// 	Description:
// 		A container for the rest/pools.json values.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

import com.socialvagrancy.blackpearl.logs.structures.rest.data.Pool;
import java.math.BigInteger;

public class Pools
{
	public Pool[] data;

	public int driveCount(int pool) { return data[pool].disk_ids.length; }
	public String highWater(int pool) { return data[pool].high_water_mark; }
	public String name(int pool) { return data[pool].name; }
	public int poolCount() { return data.length; }
	public String powerSavingMode(int pool) { return data[pool].power_saving_mode; }
	public String protection(int pool) { return data[pool].protection; }
}
