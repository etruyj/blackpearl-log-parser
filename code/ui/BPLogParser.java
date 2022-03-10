package com.socialvagrancy.blackpearl.logs.ui;

import com.socialvagrancy.blackpearl.logs.ui.display.Display;

public class BPLogParser
{

	public static void parseCommand(String command, String path)
	{
		switch(command)
		{
			case "job-statistics":
			case "job-stats":
				Controller.jobStatistics(path);
				break;
			case "-h":
			case "--help":
				Display.fromFile("../lib/help/options.txt");
				break;
			case "-v":
			case "--ver":
			case "--version":
				Display.fromFile("../lib/help/version.txt");
				break;
			default:
				System.err.println("Invalid command selected. Please use --help or -h to see available commands.");
				break;
		}
	}

	public static void main(String[] args)
	{
		ArgParser aparser = new ArgParser();
		aparser.parseArgs(args);

		//System.err.println(aparser.getPath());

		BPLogParser.parseCommand(aparser.getCommand(), aparser.getPath());
	}
}
