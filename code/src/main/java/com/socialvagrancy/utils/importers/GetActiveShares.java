//===================================================================
// GetActiveShares.java
//      Description:
//          This class pulls the active CIFS shares from the 
//          pre/16_cifs.rb.txt file.
//
// Created by Sean Snyder
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers;

import com.socialvagrancy.blackpearl.logs.structures.ActiveCifsShare;
import com.socialvagrancy.blackpearl.logs.utils.inputs.LoadFile;

import java.util.ArrayList;

public class GetActiveShares {
    public static ArrayList<ActiveCifsShare> fromFile(String dir_path) {
        // This function loads sections of the file to search
        // for a specific table and process that into the 
        // array list of the variable. Once the table is complete
        // it stops iterating through the file.
        String path = dir_path + "/pre/16_cifs.rb.txt";
        int first_line = 0;
        int fetch_count = 10;
        
        ArrayList<ActiveCifsShare> share_list = new ArrayList<ActiveCifsShare>();
        ActiveCifsShare sh = null;

        // Search the file for this header, which lets us know we're at the
        // correct table.
        String table_header = "Service      pid     Machine       Connected at                     Encryption   Signing";
        boolean section_found = false;
        boolean section_complete = false;

        ArrayList<String> file_data = LoadFile.fullFileIntoArray(path);

        String[] parts = null;
        String connected_at;

        for(String line : file_data) {
            if(!section_found && line.contains(table_header)) {
                section_found = true;
            } else if(section_found && line.length()==0) {
                section_complete = true; 
            } else if(section_found && !section_complete) {
                parts = line.split("\\s{1,}");
                
                if(parts.length >= 6) {
                    sh = new ActiveCifsShare();

                    sh.setShare(parts[0].trim());
                    sh.setPid(parts[1].trim());
                    sh.setMachine(parts[2].trim());
                    
                    // Connected at has spaces
                    // So the string must be pieced together
                    // The goal is to find all the values in the 
                    // gap.
                    // Start at part 3.
                    // work the way up from three to whatever extra characters are present.
                    // extra characters = parts.length - expected fields (6)
                    // add 3 to see how many past the initial index are required.
                    connected_at = "";
                    for(int i=3; i<(parts.length-6+3); i++) {
                        connected_at = connected_at + parts[i] + " ";
                    }

                    sh.setConnectedAt(connected_at.trim());
                    sh.setEncryption(parts[parts.length-2].trim());
                    sh.setSigning(parts[parts.length-1].trim());
                    
                    share_list.add(sh);
                }
            }
        }

        return share_list;
    }
}
