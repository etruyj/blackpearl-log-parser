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
			dir = findRootPathWindows(path);
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
		String[] searchFor = new String[3];
	       	searchFor[0] = "manual";
	       	searchFor[1] = "error";
	       	searchFor[2] = "scheduled";
		
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
			if(parts[itr].length() > searchFor[0].length() &&
					parts[itr].substring(0, searchFor[0].length()).equals(searchFor[0]))
			{
				// Found the root path.
				searching = false;
			}
			else if(parts[itr].length() > searchFor[1].length() &&
					parts[itr].substring(0, searchFor[1].length()).equals(searchFor[1]))
			{
				// Found the root path.
				searching = false;
			}
			else if(parts[itr].length() > searchFor[2].length() &&
					parts[itr].substring(0, searchFor[2].length()).equals(searchFor[2]))
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

	public static String findRootPathWindows(String path)
	{
		String[] path_parts = path.split("\\\\");
		String root_path = "";
		
		boolean searching = true;
		int counter = 0;
		
		while(searching)
		{
			root_path += path_parts[counter] + "/";

			if(path_parts[counter].length() >= 6 && path_parts[counter].substring(0,6).equals("manual"))
			{
				searching = false;
			}
			else if(path_parts[counter].length() >= 5 && path_parts[counter].substring(0,5).equals("error"))
			{
				searching = false;
			}
			else if(path_parts[counter].length() >= 9 && path_parts[counter].substring(0,9).equals("scheduled"))
			{
				searching = false;
			}
			
			counter++;
		}

		return root_path;
	}

	public static boolean isWindows(String path)
	{

		if(path.substring(0,1).equals("/") || path.substring(0,1).equals("."))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
