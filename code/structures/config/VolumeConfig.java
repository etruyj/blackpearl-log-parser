//===================================================================
// VolumeConfig.java
// 	Description:
// 		Volume information for the system.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.config;

import java.math.BigInteger;

public class VolumeConfig
{
	public String name;
	public String pool_name;
	public BigInteger min_size;
	public BigInteger max_size;
	public boolean case_sensitive;
	public boolean compression_enabled;
	public boolean access_time;

	public VolumeConfig(String n, String p, boolean caseSensitive, boolean compression, boolean atime)
	{
		name = n;
		pool_name = p;
		case_sensitive = caseSensitive;
		compression_enabled = compression;
		access_time = atime;
	}
}
