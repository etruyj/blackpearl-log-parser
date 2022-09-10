package com.socialvagrancy.blackpearl.logs.ui;

import com.socialvagrancy.blackpearl.logs.ui.display.Display;
import com.socialvagrancy.blackpearl.logs.structures.outputs.JobDetails;
import java.util.ArrayList;

public class BPLogParser
{

	public static void parseCommand(String command, String path, String output_format)
	{
		switch(command)
		{
			case "active-job-details":
				Controller.activeJobDetails(path);
				break;
			case "active-job-status":
			case "job-status":
				Controller.activeJobStatus(path);
				break;
			case "completed-job-details":
			case "job-details":
				ArrayList<JobDetails> details_list = Controller.completedJobDetails(path);
				Display.print(details_list, output_format);
				break;
			case "list-buckets":
				Controller.listBuckets(path);
				break;
			case "list-data-policies":
			case "list-policies":
				Controller.listDataPolicies(path);
				break;
			case "list-storage-domains":
			case "list-domains":
				Controller.listStorageDomains(path);
				break;
			case "system-info":
				Controller.systemInfo(path);
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
	}

	public static void main(String[] args)
	{
		ArgParser aparser = new ArgParser();
		aparser.parseArgs(args);

		//System.err.println(aparser.getPath());

		BPLogParser.parseCommand(aparser.getCommand(), aparser.getPath(), "csv");
	}
}
