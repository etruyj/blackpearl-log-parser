//===================================================================
// JobSummary.java
// 	Description:
// 		Holds basic job information for output: name, id, 
// 		created at, and size.
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.outputs;

import java.math.BigInteger;

public class JobSummary
{
	public String name;
	public String id;
	public String created_at;
	public BigInteger size;

	//=======================================
	// Getters
	//=======================================
	public String createdAt() { return created_at; }
	public String id() { return id; }
	public String name() { return name; }
	public BigInteger size() { return size; }
	
	//=======================================
	// Setter
	//=======================================
	public void setCreatedAt(String c) { created_at = c; }
	public void setId(String i) { id = i; }
	public void setName(String n) { name = n; }
	public void setSize(String s) { size = new BigInteger(s); }
	
}
