//===================================================================
// GetTranslatedMessage.java
//      Description:
//          This importer reads in the translated messages text file
//          and builds an array list of messages.
//
// Created by Sean Snyder
//===================================================================

package com.socialvagrancy.blackpearl.logs.utils.importers;

import com.socialvagrancy.blackpearl.logs.structures.TranslatedMessage;
import com.socialvagrancy.blackpearl.logs.utils.inputs.LoadFile;

import java.util.ArrayList;
import java.util.List;

public class GetTranslatedMessages {
    public static ArrayList<TranslatedMessage> fromFile(String path) {
        ArrayList<TranslatedMessage> message_list = new ArrayList<TranslatedMessage>();
        TranslatedMessage message = null;
        String[] line_parts;

        ArrayList<String> file_data = LoadFile.fullFileIntoArray(path);    
    
        for(String line : file_data) {
            if(line.contains("ID")) {
                if(message != null) {
                    // Add the item at the beginning of the list so
                    // it is displayed newest first instead of oldest first.
                    message_list.add(0, message);
                }

                message = new TranslatedMessage();
            }

            line_parts = line.split(": ");

            if(line_parts.length > 1) {
                if(line_parts[0].contains("ID")) {
                    message.setId(Integer.valueOf(line_parts[1].trim()));
                } else if(line_parts[0].contains("Severity")) {
                    message.setSeverity(line_parts[1].trim());
                } else if(line_parts[0].contains("Description")) {
                    message.setDescription(line_parts[1].trim());
                } else if(line_parts[0].contains("Details")) {
                    message.setDetails(line_parts[1].trim());
                } else if(line_parts[0].contains("Created")) {
                    message.setCreated(line_parts[1].trim());
                }
            }
            
        }

        if(message != null) {
            // Add the item at the beginning of the list so
            // it is displayed newest first instead of oldest first.
            message_list.add(0, message);
        }

        return message_list;
    }
}
