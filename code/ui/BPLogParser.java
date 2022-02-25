package com.socialvagrancy.blackpearl.logs.ui;

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

		BPLogParser.parseCommand(aparser.getCommand(), "../logs/");
	}
}
