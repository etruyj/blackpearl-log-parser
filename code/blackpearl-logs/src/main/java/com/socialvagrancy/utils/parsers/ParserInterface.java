//===================================================================
// ParserInterface.java
// 	Description:
// 		This is an interface to allow standardized process
// 		for processing different log files.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.parsers;

public interface ParserInterface
{
	public abstract void parseLine(String line);
}
