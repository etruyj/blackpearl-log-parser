//===================================================================
// DeleteFile.java
// 	Description:
// 		Deletes the specified file.
// 		Aimed to clear up the log directory after decompressing
// 		logs.
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.inputs;

import com.socialvagrancy.utils.Logger;
import java.io.File;

public class DeleteFile
{
	public static void delete(String file_path, Logger log)
	{
		File myFile = new File(file_path);

		if(myFile.delete())
		{
			log.INFO("File [" + file_path + "] deleted successfully.");
		}
		else
		{
			log.WARN("Unable to delete file [" + file_path + "].");
		}

	}
}
