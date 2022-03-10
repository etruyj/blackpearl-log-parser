//===================================================================
// BuildPath.java
// 	Description: This class parses the command path to find the
// 	proper root directory for the logset.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui.func;

public class BuildPath
{
	public static String fromArgs(String path)
	{
		String dir = null;

		if(isWindows(path))
		{
		}
		else
		{
			dir = findRootPathLinux(path);	
		}

		return dir;
	}

	public static String findRootPathLinux(String path)
	{
		// Find the part of the path that points to manual.
		String[] parts = path.split("/");
		String searchFor = "manual";
		String dir;
		int start;

		if(path.substring(0, 1).equals("/"))
		{
			dir = "/";
			// First array index is actually blank when split at /
			start = 1;
			
		}
		else
		{
			dir = "";
			start = 0;
		}

		boolean searching = true;
		int itr = parts.length-1;

		while(itr >= 0 && searching)
		{
			if(parts[itr].length() > searchFor.length() &&
					parts[itr].substring(0, searchFor.length()).equals(searchFor))
			{
				// Found the root path.
				searching = false;
			}

			itr--;
		}

		// Build the path
		// start at i=1 as [0] is to the left of the initial /
		// got to itr + 1 as the while loop still decrements after
		// finding the manaul directory.
		for(int i=start; i<= itr + 1; i++)
		{
			dir = dir + parts[i] + "/";
		}

		return dir;
	}

	public static boolean isWindows(String pwd)
	{
		if(pwd.substring(0,1).equals("/") || pwd.substring(0,1).equals("."))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
