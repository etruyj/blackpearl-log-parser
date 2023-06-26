//===================================================================
// GetTapeExchanges.java
// 	Description:
// 		Flips through the tape_backend.logs to build a list of
// 		tape exchanges.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers;

import com.socialvagrancy.blackpearl.logs.structures.TapeExchange;
import com.socialvagrancy.blackpearl.logs.utils.decompressors.BZIP2;
import com.socialvagrancy.blackpearl.logs.utils.inputs.DeleteFile;
import com.socialvagrancy.blackpearl.logs.utils.inputs.LogReader;
import com.socialvagrancy.blackpearl.logs.utils.parsers.TapeExchangeParser;
import com.socialvagrancy.utils.Logger;

import java.util.ArrayList;

public class GetTapeExchanges
{
	public static void main(String[] args)
	{
		Logger log = new Logger("../logs/bp_log.log", 1024, 1, 1);
		GetTapeExchanges.fromTapeBackend(args[0], 14, log, true);
	}

	public static ArrayList<TapeExchange> fromTapeBackend(String dir_path, int log_count, Logger log, boolean debugging)
	{
		ArrayList<String> exception_list = new ArrayList<String>();

		System.err.print("Importing tape exchanges...\t");

		String log_name = "logs/var.log.tape_backend.log";
		String file_name;
		
		TapeExchangeParser parser = new TapeExchangeParser();

		for(int i=log_count; i>=-1; i--)
		{
			try
			{
				if(i>-1)
				{
					file_name = log_name + "." + i;
				
					BZIP2.decompress(dir_path + file_name + ".bz2", log);
				}
				else
				{
					file_name = log_name;
				}

				if(debugging)
				{
					System.err.print("\n"); // new line on importing... message.
					System.err.println(dir_path + file_name);
				}

				LogReader.readLog(dir_path + file_name, parser, log);

				if(i>-1)
				{
					DeleteFile.delete(dir_path + file_name, log);
				}
			}
			catch(Exception e)
			{
				// For cleaner output, collect exceptions and print them
				// after [COMPLETE]
				exception_list.add(e.getMessage());
			}
		}

		System.err.println("[COMPLETE]");

		for(int i=0; i<exception_list.size(); i++)
		{
			System.err.println(exception_list.get(i));
		}

		return parser.getExchangeList();
	}
}
