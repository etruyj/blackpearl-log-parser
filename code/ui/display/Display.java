//===================================================================
// Display.java
// 	Descriptions: Handles output to shell for UI calls.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui.display;

import com.socialvagrancy.blackpearl.logs.ui.display.serializers.Serializer;
import com.socialvagrancy.utils.ui.display.Print;
import com.socialvagrancy.utils.ui.display.Table;
import com.socialvagrancy.utils.ui.structures.OutputFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Display
{
	public static void print(ArrayList to_print, String output_format)
	{
		ArrayList<OutputFormat> output = Serializer.serialize(to_print);
		
		if(output.size() > 0)
		{
			if(output_format.equals("csv") || output_format.equals("table"))
			{	
				Table.format(output, output_format);
			}
			else if(output_format.equals("shell"))
			{
				System.err.println("ERROR: Shell output unavailable.");
			}
			else
			{
				System.err.println("ERROR: Invalid output format specified. Please view -h/--help to see valid formats");
			}		
		}
	}

	public static void fromFile(String file_path)
	{
		Path path = Paths.get(file_path);

		if(Files.exists(path))
		{
			try
			{
				BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8);

				String line = null;

				while((line = br.readLine()) != null)
				{
					System.out.println(line);
				}
			}
			catch(IOException e)
			{
				System.err.println(e.getMessage());
			}
		}
		else
		{
			System.err.println("ERROR: Unable to find file " + path);
		}
	}
}
