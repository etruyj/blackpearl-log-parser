//===================================================================
// GuiTapeLibraryPartitions.java
// 	A container class for the gui_tape_library_partitions.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

public class GuiTapeLibraryPartitions
{
	public TapePartition[] data;

	//=======================================
	// FUNCTIONS
	//=======================================

	public int partitionCount() { return data.length; }
	public String partitionID(int partition) { return data[partition].id; }
       	public String partitionName(int partition) { return data[partition].name; }	

	//=======================================
	// INNER CLASSES
	//=======================================

	public class TapePartition
	{
		public String case_id;
		public String name;
		public String error_message;
		public String serial_number;
		public String state;
		public String import_export_configuration;
		public String drive_type;
		public String[] tape_types;
		public String quiesced;
		public int minimum_read_reserved_drives;
		public int minimum_write_reserved_drives;
		public boolean auto_compaction_enabled;
		public String created_at;
		public String updated_at;
		public String id;
	}	
}
