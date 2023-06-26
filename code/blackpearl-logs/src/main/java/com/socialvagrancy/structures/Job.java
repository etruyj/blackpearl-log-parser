package com.socialvagrancy.blackpearl.logs.structures;

public class Job
{
	public JobInfo[] data;

	public class JobInfo
	{
		public String bucket_id;
		public String name;
		public String request_type;
		public String original_size_in_bytes;
		public String cached_size_in_bytes;
		public String completed_size_in_bytes;
		public String created_at;
		public String id;	
	}
}

