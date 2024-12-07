package com.socialvagrancy.blackpearl.logs.structures;

public class CompletedJob
{
	public JobInfo[] data;

	public int jobCount()
	{
		if(data == null)
		{
			return 0;
		}
		else
		{
			return data.length;
		}
	}


	public String bucketID(int job) { return data[job].bucket_id; }
	public String cachedSize(int job) { return data[job].cached_size_in_bytes; }
	public String createdAt(int job) { return data[job].created_at; }
	public String completedSize(int job) { return data[job].completed_size_in_bytes; }
	public String id(int job) { return data[job].id; }
	public String originalSize(int job) { return data[job].original_size_in_bytes; }
	public String name(int job) { return data[job].name; }
	public String type(int job) { return data[job].request_type; }

	public class JobInfo
	{
		public String bucket_id;
		public String date_completed;
		public String error_message;
		public boolean truncated;
		public String name;
		public String chunk_client_processing_order_guarantee;	
		public String node;
		public String priority;
		public String request_type;
		public String original_size_in_bytes;
		public String cached_size_in_bytes;
		public String completed_size_in_bytes;
		public int user_id;
		public String created_at;
		public String update_at;
		public String id;
		public String user_username;
	}
}

