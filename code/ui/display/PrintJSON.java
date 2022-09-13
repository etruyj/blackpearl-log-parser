//===================================================================
// PrintJSON.java
// 	Description:
// 		Prints the variable in json format to the screen.
//===================================================================

package com.socialvagrancy.blackpearl.logs.ui.display;

import com.socialvagrancy.blackpearl.logs.structures.outputs.Configuration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.util.ArrayList;

public class PrintJSON
{
	public static void toShell(ArrayList<Configuration> to_print)
	{
		if(to_print.size() > 0)
		{
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(to_print);
			System.out.println(json);
		}
	}

	//=======================================
	// Private Function
	//=======================================
}
