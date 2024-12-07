package com.socialvagrancy.blackpearl.logs.structures;

import java.util.ArrayList;

public class TapeJob extends TapeActivity
{;
	public String request_type;
	public String size;
	public String duration;
	public ArrayList<String> blob;

	public TapeJob()
	{
		request_type = null;
		size = null;
		drive_wwn = null;
		start_time = null;
		end_time = null;
		duration = null;
		blob = new ArrayList<String>();
	}
}
