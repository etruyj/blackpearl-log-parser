package com.socialvagrancy.blackpearl.logs.ui;

import com.socialvagrancy.blackpearl.logs.ui.display.Display;
import com.socialvagrancy.blackpearl.logs.structures.outputs.JobDetails;
import java.util.ArrayList;

public class BPLogParser
{

	public static void parseCommand(String command, String path, String output_format)
	{
		ArrayList to_print = null; // Set to null for error handling.
		
		switch(command)
		{
			case "active-job-status":
			case "job-status":
				to_print = Controller.activeJobStatus(path);
				break;
			case "completed-job-details":
			case "job-details":
				to_print = Controller.completedJobDetails(path);
				break;
			case "fetch-config":
				to_print = Controller.fetchConfiguration(path);
				output_format = "json";
				break;
			case "list-buckets":
				to_print = Controller.listBuckets(path);
				break;
			case "list-data-policies":
			case "list-policies":
				to_print = Controller.listDataPolicies(path);
				break;
			case "list-storage-domains":
			case "list-domains":
				to_print = Controller.listStorageDomains(path);
				break;
			case "system-info":
				to_print = Controller.systemInfo(path);
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
				System.err.println(command);
				System.err.println("Invalid command selected. Please use --help or -h to see available commands.");
				break;
		}

		if(to_print != null)
		{
			Display.print(to_print, output_format);
		}
	}

	public static void main(String[] args)
	{
		ArgParser aparser = new ArgParser();
		aparser.parseArgs(args);

		//System.err.println(aparser.getPath());

		BPLogParser.parseCommand(aparser.getCommand(), aparser.getPath(), "csv");
	}
}
