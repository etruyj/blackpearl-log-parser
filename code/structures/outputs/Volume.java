//===================================================================
// Volume.java
// 	Description:
// 		Volume information for the system.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.outputs;

import java.math.BigInteger;

public class Volume
{
	public String name;
	public String pool_id;
	public String pool_name;
	public BigInteger min_size;
	public BigInteger max_size;
	public boolean case_sensitive;
	public boolean compression_enabled;
	public boolean access_time;
}
