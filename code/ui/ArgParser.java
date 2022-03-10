//===================================================================
// ArgParser.java
// 	Description: This class parses the args passed with the script
// 	call.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui;

import com.socialvagrancy.blackpearl.logs.ui.func.BuildPath;

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
		else if(args.length > 0)
		{
			setOptions(args);
		}

	}

	public void setOptions(String[] args)
	{
		setCommand(args[0]);

		// Don't parse the last passed arguement.
		// args.length - 1.
		// This allows PWD to be parsed separately.
		// Until I find a better way to catch it in
		// a switch.
		for(int i=1; i<args.length; i++)
		{
			switch(args[i])
			{
				case "-h":
				case "--help":
					setCommand("--help");
					break;
				case "-p":
				case "--path":
					if(args.length > i+1)
					{
						setPath(args[i+1]);
						i++;
					}
					break;
				case "-v":
				case "--ver":
				case "--version":
					setCommand("--version");
					break;
			}
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
		directory_path = p;
		
		directory_path = BuildPath.fromArgs(directory_path);
	}

	private void setPathCurrent(String p)
	{
		// Find the current path
	}
}
