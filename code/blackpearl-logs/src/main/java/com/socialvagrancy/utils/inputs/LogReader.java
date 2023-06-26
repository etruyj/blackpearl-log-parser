//===================================================================
// LogReader.java
// 	Description:
// 		Reads the log and passes each line to the parser to
// 		determine useful information.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.inputs;

import com.socialvagrancy.blackpearl.logs.utils.parsers.ParserInterface;
import com.socialvagrancy.utils.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LogReader
{
	public static ParserInterface readLog(String path, ParserInterface parser, Logger log) throws Exception
	{
		File file = new File(path);

		if(file.exists())
		{
			String line = null;

			try
			{
				BufferedReader br = new BufferedReader(new FileReader(file));

				while((line = br.readLine()) != null)
				{
					parser.parseLine(line);
				}
			}
			catch(IOException e)
			{
				System.err.println(e.getMessage());
			}
		}
		else
		{
			throw new Exception("File [" + path + "] does not exist.");
		}

		return parser;
	}
}
