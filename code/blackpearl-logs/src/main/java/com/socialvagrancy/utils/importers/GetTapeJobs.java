//===================================================================
// GetTapeJobs.java
// 	Description:
// 		Scans the tape_backend.logs for information on the
// 		tape jobs. This info links TapeTasks in the dataplanner
// 		logs to the physical tape moves occuring in the library.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers;

import com.socialvagrancy.blackpearl.logs.structures.TapeJob;
import com.socialvagrancy.blackpearl.logs.utils.decompressors.BZIP2;
import com.socialvagrancy.blackpearl.logs.utils.inputs.DeleteFile;
import com.socialvagrancy.blackpearl.logs.utils.inputs.LogReader;
import com.socialvagrancy.blackpearl.logs.utils.parsers.TapeJobParser;
import com.socialvagrancy.utils.Logger;

import java.util.ArrayList;

public class GetTapeJobs
{
	public static void main(String[] args)
	{
		Logger log = new Logger("../logs/bp_parser.log", 1024, 1, 1);
		GetTapeJobs.fromTapeBackend(args[0], 14, log, true);
	}

	public static ArrayList<TapeJob> fromTapeBackend(String dir_path, int log_count, Logger log, boolean debugging)
	{
		ArrayList<String> exception_list = new ArrayList<String>();

		System.err.print("Importing tape jobs...\t\t");

		String log_name = "logs/var.log.tape_backend.log";
		String file_name;

		TapeJobParser parser = new TapeJobParser();

		// Count down to -1 as there is a log.0 for tape_backend.
		for(int i=log_count; i>=-1; i--)
		{
			try
			{
				if(i!=-1)
				{
					// Add Unzip the bzip 2
					file_name = log_name + "." + i;
					BZIP2.decompress(dir_path + file_name + ".bz2", log);
				}
				else
				{
					file_name = log_name;
				}

				if(debugging)
				{
					System.err.print("\n");
					System.err.println(dir_path + file_name);
				}
			
				LogReader.readLog(dir_path + file_name, parser, null);
				

				if(i!=-1)
				{
					DeleteFile.delete(dir_path + file_name, log);
				}
			}
			catch(Exception e)
			{
				// For cleaner output
				// catch the exceptions and print them after the complete is printed.
				exception_list.add(e.getMessage());
			}
		}

		System.err.println("[COMPLETE]");

		for(int i=0; i<exception_list.size(); i++)
		{
			System.err.println(exception_list.get(i));
		}

		return parser.getJobList();
	}

	public static void print(ArrayList<TapeJob> job_list)
	{
		for(int i=0; i<job_list.size(); i++)
		{
			System.out.println(job_list.get(i).drive_wwn + " " + job_list.get(i).request_type + " " + job_list.get(i).start_time 
					+ " " + job_list.get(i).end_time + " " + job_list.get(i).duration);

			for(int j=0; j<job_list.get(i).blob.size(); j++)
			{
				System.out.println("\t- " + job_list.get(i).blob.get(j));
			}
		}
	}
}
