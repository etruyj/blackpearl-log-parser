//===================================================================
// TapeExchangeParser.java
// 	Description:
// 		Parses the tape_backend.log for information on tape
// 		exchanges.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.parsers;

import com.socialvagrancy.blackpearl.logs.structures.TapeExchange;

import java.util.ArrayList;
import java.util.HashMap;

public class TapeExchangeParser implements ParserInterface
{
	ArrayList<TapeExchange> exchange_list;
	HashMap<String, TapeExchange> exchange_map;

	public TapeExchangeParser()
	{
		exchange_list = new ArrayList<TapeExchange>();
		exchange_map = new HashMap<String, TapeExchange>();
	}

	//=======================================
	// Parsers
	//=======================================
	
	@Override
	public void parseLine(String line)
	{
		String move_search = "Move";
		String completed_search = "completed successfully";
		String started_search = "dispatched";

		TapeExchange exch = null;

		if(line.contains(move_search))
		{
			if(line.contains(started_search))
			{
				exch = searchStart(line);
			}
			else if(line.contains(completed_search))
			{
				exch = searchCompleted(line);
			}

			if(exch != null)
			{
				updateExchangeMap(exch);
			}
		}
	}

	private TapeExchange searchCompleted(String line)
	{
		TapeExchange exch = new TapeExchange();

		String drive_search = "TapeDrive:";
		String move_search = "Move";
		String trailing_chars = ") completed successfully";
		String edited_line = line.substring(line.indexOf(move_search) + move_search.length(), line.length());
		String[] line_parts = edited_line.split("->");
		String barcode;
		String drive;

		barcode = line_parts[0].substring(1, line_parts[0].indexOf(","));
		
		drive = line.substring(line.indexOf(drive_search) + drive_search.length(), line.length());
		drive = drive.substring(0, drive.indexOf(")"));

		if(line_parts[0].contains(drive_search))
		{
			exch.toDrive = false;
			line_parts[0] = line_parts[0].substring(line_parts[0].indexOf(" ") + 1, line_parts[0].lastIndexOf("("));
			line_parts[1] = line_parts[1].substring(0, line_parts[1].indexOf(")"));
		}
		else
		{
			exch.toDrive = true;

			// Parse Move(BARCODE, SRC->TGT(TapeDrive:######))
			line_parts[0] = line_parts[0].substring(line_parts[0].lastIndexOf(" ") + 1, line_parts[0].length());

			if(line_parts[1].contains(drive_search))
			{
				line_parts[1] = line_parts[1].substring(0, line_parts[1].indexOf("("));
			}
			else
			{
				// Slot to slot move
				// Move(BARCODE, SRC->TGT)
				drive = "NONE";
				line_parts[1] = line_parts[1].substring(0, line_parts[1].indexOf(")"));
			}
		}

		exch.tape_barcode = barcode;
		exch.drive_wwn = drive;
		exch.source = line_parts[0];
		exch.target = line_parts[1];
		exch.end_time = searchTimestamp(line);
		exch.partition_id = searchPartition(line);

		return exch;
	}

/*	Delete pending verification
 *	unused
 * 	private String searchMove(String line, TapeExchange exch)
	{
		String move_search = "Move";
		String drive_search = "TapeDrive:";
		String drive = line.substring(line.indexOf(move_search) + move_search.length(), line.length());
		drive = drive.substring(0, drive.indexOf(")"));
		System.out.println(line);
		System.out.println(drive);
		return drive;
	}
*/

	private String searchPartition(String line)
	{
		String partition_search = "TapePartition:";
		String partition = line.substring(line.indexOf(partition_search) + partition_search.length(), line.length());
		partition = partition.substring(0, partition.indexOf(":"));

		return partition;
	}

	private TapeExchange searchStart(String line)
	{
		TapeExchange exch = new TapeExchange();
		
		String move_search = "Move(";
		String edited_line = line.substring(line.indexOf(move_search) + move_search.length(), line.length());
		String[] line_parts = edited_line.split("->");
		
		// Clean up the TGT) dispatched
		line_parts[1] = line_parts[1].substring(0, line_parts[1].indexOf(")"));

		exch.source = line_parts[0];
		exch.target = line_parts[1];
		exch.partition_id = searchPartition(line);

		return exch;
	}

	private String searchTimestamp(String line)
	{
		String[] line_parts = line.split(" ");
		String timestamp;

		if(line_parts[1].length()==0)
		{
			timestamp = line_parts[0] + " 0" + line_parts[2] + " " + line_parts[3];
		}
		else
		{
			timestamp = line_parts[0] + " " + line_parts[1] + " " + line_parts[2];
		}

		return timestamp;
	}

	//=======================================
	// Getters
	//=======================================
	
	public ArrayList<TapeExchange> getExchangeList()
	{
		return exchange_list;
	}

	private TapeExchange getTapeExchange(String partition_source)
	{
		if(exchange_map.get(partition_source) == null)
		{
			TapeExchange exchange = new TapeExchange();
			return exchange;
		}
		else
		{
			return exchange_map.get(partition_source);
		}
	}

	//=======================================
	// Functions
	//=======================================
	
	private void updateExchangeMap(TapeExchange exch)
	{
		if(exch.end_time == null)
		{
			exchange_map.put(exch.partition_id + ":" + exch.source, exch);
		}
		else
		{
			if(exchange_map.get(exch.partition_id + ":" + exch.source) != null)
			{
				// There's a value in the source field.
				exch.start_time = exchange_map.get(exch.partition_id + ":" + exch.source).start_time;

				exchange_map.remove(exch.partition_id + ":" + exch.source);

				exchange_list.add(exch);
			}
			else
			{
				// There is no value in the source field.
				exchange_list.add(exch);
			}
		}
	}
}
