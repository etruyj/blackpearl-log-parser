//===================================================================
// Shares.java
// 	Description:
// 		A container class for rest/shares.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

public class Shares
{
	public Share[] data;

	//=======================================
	// Getters
	//=======================================

	public String accessControl(int share) { return data[share].access_control; }
	public String comment(int share) { return data[share].comment; }
	public int count() { return data.length; }
	public String mountPoint(int share) { return data[share].mount_point; }
	public String name(int share) { return data[share].name; }
	public boolean readOnly(int share) { return data[share].read_only; }
	public String type(int share) { return data[share].type; }
	public String volumeID(int share) { return data[share].volume_id; }

	//=======================================
	// Inner Classes
	//=======================================

	public class Share
	{
		public String name;
		public boolean read_only;
		public String access_control;
		public String comment;
		public String path;
		public String type;
		public String service_id;
		public String volume_id;
		public String created_at;
		public String updated_at;
		public String id;
		public String mount_point;
		public String status;
	}
}
