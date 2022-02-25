//===================================================================
// ArgParser.java
// 	Description: This class parses the args passed with the script
// 	call.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui;

package java.nio.Files;

public class ArgParser
{
	String command;
	String directory_path;
	boolean help;
	boolean version;

	public ArgParser()
	{
		command = "none";
		directory_path = "";
		help = false;
		version = false;
	}

	public void parseArgs(String[] args)
	{
		if(args.length == 2)
		{
			setCommand(args[0]);
			setPath(args[1]);
		}
	}

	//=======================================
	// Gettors
	//=======================================

	public String getCommand() { return command; }
	public boolean getHelp() { return help; }
	public String getPath() { return directory_path; }
	public boolean getVersion() { return version; }


	//=======================================
	// Settors
	//=======================================

	private void setCommand(String c)
	{
		command = c;
	}

	private void setPath(String p)
	{
		//====================================================
		// Process:
		// 	The goal is to find the root path for the
		// 	log folder. 
		//	
		//	Expected input styles:
		//		- absoute: /path/to/logs
		//		- relative: ../logs/ or ../logs/log/
		//		- current: .
		//
		//	Absolute:
		//	Relative:
		//	Current:
		//		Check directory structures to find
		//		current. Looking for log, rest, manual.
		//===================================================
		String[] path = p.split("/");
		String search = "manual";
		int start = 0;
		
		// Iterate backwards through the path for the log root directory.
		// then build the path out from there.
		int itr = path.length-1;

		while(itr > 0 && ( path[itr].length() < search.length() || !path[itr].substring(0, 6).equals(search)))
		{
			itr--;
		}

		// Check to see if the first character in the path is /. 
		// If so, start at index 1 since path[0] will be a blank space.
		if(p.substring(0, 1).equals("/"))
		{
			start = 1;
		}

		for(int i=start; i<=itr; i++)
		{
			System.out.println(path[i]);
			directory_path = directory_path + "/" + path[i];
		}
	}

	private void setPathCurrent(String p)
	{
		// Find the current path
	}
}
