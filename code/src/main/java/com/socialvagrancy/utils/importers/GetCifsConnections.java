//===================================================================
// GetCifsConnections.java
//      Description:
//          This class pulls the active CIFS connections from the 
//          pre/16_cifs.rb.txt file.
//
// Created by Sean Snyder
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers;

import com.socialvagrancy.blackpearl.logs.structures.CifsConnection;
import com.socialvagrancy.blackpearl.logs.utils.inputs.LoadFile;

import java.util.ArrayList;
import java.util.List;

public class GetCifsConnections {
    public static List<CifsConnection> fromFile(String dir_path) {
        // This function loads sections of the file to search
        // for a specific table and process that into the 
        // array list of the variable. Once the table is complete
        // it stops iterating through the file.
        String path = dir_path + "/pre/16_cifs.rb.txt";
        int first_line = 0;
        int fetch_count = 10;
        
        ArrayList<CifsConnection> cifs_connections = new ArrayList<CifsConnection>();
        CifsConnection conn = null;

        // Search the file for this header, which lets us know we're at the
        // correct table.
        String table_header = "PID     Username     Group        Machine                                   Protocol Version  Encryption           Signing";
        boolean section_found = false;
        boolean section_complete = false;

        ArrayList<String> file_data = LoadFile.fullFileIntoArray(path);

        String[] parts = null;
        String ad_group;

        for(String line : file_data) {
            if(!section_found && line.contains(table_header)) {
                section_found = true;
            } else if(section_found && line.length()==0) {
                section_complete = true; 
            } else if(section_found && !section_complete) {
                parts = line.split("\\s{1,}");
                
                if(parts.length >= 8) {
                    conn = new CifsConnection();
                    ad_group = "";

                    conn.setPid(parts[0].trim());
                    conn.setUser(parts[1].trim());
                    
                    // Active Directory Groups can have spaces.
                    // There should be 8 fields in the table. If there are more
                    // line parts, the overflow fields should be part of the 
                    // AD group.
                    // parts.length (number of parts) - 8 (expected field count) + 2 (starting index)
                    for(int i=2; i<=(parts.length - 8 + 2); i++) {
                        ad_group = ad_group + parts[i] + " ";
                    }

                    conn.setGroup(ad_group.trim());
                    conn.setSigning(parts[parts.length-1].trim());
                    conn.setEncryption(parts[parts.length-2].trim());
                    conn.setProtocol(parts[parts.length-3].trim());
                    conn.setIpv4Details(parts[parts.length-4].trim());
                    conn.setIp(parts[parts.length-5].trim());
                    
                    cifs_connections.add(conn);
                }
            }
        }

        return cifs_connections;
    }
}
