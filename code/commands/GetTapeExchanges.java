//===================================================================
// GetTapeExchanges.java
// 	Description: Parses the var.log.tape_backend.log for tape
// 	exchange information. 
//===================================================================

package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.TapeExchange;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GetTapeExchanges
{
	public static ArrayList<TapeExchange> fromTapeBackend(String path)
	{
		HashMap<String, TapeExchange> exchange_map = new HashMap<String, TapeExchange>();
		ArrayList<TapeExchange> exchange_list = new ArrayList<TapeExchange>();
		TapeExchange exchange;

		File file = new File(path);

		if(file.exists())
		{
			try
			{
				BufferedReader br = new BufferedReader(new FileReader(file));

				String line = null;
				String[] parse_values;

				while((line = br.readLine()) != null)
				{
					parse_values = line.split(":");
					if(parse_values.length>5)
					{
						parse_values[5] = parse_values[5].trim();
						
						if(parse_values[5].equals("TapePartition") || parse_values[5].equals("TapeDrive"))
						{
							exchange = parseLine(line);
							
							if(exchange != null)
							{
								exchange = consolidateExchange(exchange, exchange_map);

								if(exchange != null)
								{
									exchange_list.add(exchange);
								}
							}
						}
					}		
				}
				
				return exchange_list;
			}
			catch(IOException e)
			{
				System.err.println(e.getMessage());
				return null;
			}
		}
		else
		{
			System.err.println("ERROR: file " + path + " does not exist");
			return null;
		}
	}

	public static TapeExchange consolidateExchange(TapeExchange exchange, HashMap<String, TapeExchange> exchange_map)
	{
		if(exchange.prepare_time != null)
		{
			exchange_map.put(exchange.drive_sn, exchange);

		}
		else if(exchange.start_time != null)
		{
			exchange_map.put(exchange.partition_id + ":" + exchange.source + ":" + exchange.target, exchange);

		}
		else if(exchange.end_time != null)
		{
			if(exchange_map.get(exchange.drive_sn) != null)
			{
				exchange.prepare_time = exchange_map.get(exchange.drive_sn).prepare_time;
				
				// Clear this entry from the map so we don't find it again.
				exchange_map.remove(exchange.drive_sn);
			}

			if(exchange_map.get(exchange.partition_id + ":" + exchange.source + ":" + exchange.target) != null)
			{
				exchange.start_time = exchange_map.get(exchange.partition_id + ":" + exchange.source + ":" + exchange.target).start_time;

				// Clear this entry from the map so we don't find it again.
				exchange_map.remove(exchange.partition_id + ":" + exchange.source + ":" + exchange.target);
				
				return exchange;
			}

		}
			
		// Only return completed exchange variable
		return null;
	}

	public static TapeExchange parseLine(String line)
	{
		TapeExchange exchange = null;
		String timestamp = line.substring(0, 15);
		String[] values = line.split(":");
		values[5] = values[5].trim();

		switch(values[5])
		{
			case "TapePartition":
				exchange = populateMoveDetails(line, timestamp);
				break;
			case "TapeDrive":
				exchange = populatePrepareDetails(line, timestamp);
				break;
		}


		return exchange;	
	}

	public static TapeExchange parseCompletedMove(String line, String timestamp)
	{
		TapeExchange exchange = new TapeExchange();
		String[] values;
		String match_end = ") completed successfully";
		
		// Set Tape Partition ID;
		values = line.split(":");
		exchange.partition_id = values[6];

		exchange.end_time = timestamp;

		values = line.split(",");
		exchange.tape_barcode = values[0].split("Move\\(")[1];
			
		
		values[1] = values[1].substring(0, values[1].length() - match_end.length());
		values = values[1].split("\\)");

		if(values.length==1)
		{
			// move from slot to drive.
			exchange.toDrive = true;

			values = values[0].split("->");
			exchange.source = values[0].trim();

			values = values[1].split("\\(");
			exchange.target = values[0];

			if(values.length==1)
			{
				exchange.toDrive = false;
				exchange.drive_sn = "none";
			}
			else
			{
				values = values[1].split(":");
				exchange.drive_sn = values[1];
			}
		}
		else if(values.length==2)
		{
			// Move from drive to slot
			exchange.toDrive = false;
			
			exchange.target = values[1].substring(2, values[1].length());
				
			values = values[0].split("\\(");
			exchange.source = values[0].trim();

			values = values[1].split(":");
			exchange.drive_sn = values[1];

		}

		return exchange;
	}
			
	public static TapeExchange parseStartMove(String line, String timestamp)
	{
		TapeExchange exchange = new TapeExchange();
		String[] values;
		
		// Set Tape Partition ID;
		values = line.split(":");
		exchange.partition_id = values[6];
		
		values = line.split("\\(");
		values = values[values.length-1].split("\\)");
		values = values[0].split("->");

		exchange.source = values[0];
		exchange.target = values[1];
		exchange.start_time = timestamp;
			
		return exchange;
	}

	public static TapeExchange populateMoveDetails(String line, String timestamp)
	{
		TapeExchange exchange = null;
		String match_start = "dispatched";
		String match_end = ") completed successfully";
		String[] values;

		if(line.substring(line.length()-match_start.length(), line.length()).equals(match_start))
		{
			exchange = parseStartMove(line, timestamp);
		}
		else if(line.substring(line.length()-match_end.length(), line.length()).equals(match_end))
		{
			exchange = parseCompletedMove(line, timestamp);
		}
		
		return exchange;
	}

	public static TapeExchange populatePrepareDetails(String line, String timestamp)
	{
		TapeExchange exchange = new TapeExchange();
		String[] fields = line.split(":");
		String match = "PrepareToMove() called";
		String match_protocol = " SCSI";

		if(fields[fields.length-1].equals(match) && fields[fields.length-3].equals(match_protocol))
		{
			exchange.drive_sn = fields[6];
			exchange.prepare_time = timestamp;

		}
		else
		{
			// Filter out unless info
			return null;
		}

		return exchange;
	}

	public static void print(ArrayList<TapeExchange> exchange_list)
	{
		for(int i=0; i<exchange_list.size(); i++)
		{
			System.out.println("Move (" + i + "): Tape (" + exchange_list.get(i).tape_barcode + ") to drive (" + 
					exchange_list.get(i).drive_sn + ") Slots [" + exchange_list.get(i).source + "->" +
					exchange_list.get(i).target + "] " + exchange_list.get(i).prepare_time + " " + 
					exchange_list.get(i).start_time + " " + exchange_list.get(i).end_time);
		}
	}
}
