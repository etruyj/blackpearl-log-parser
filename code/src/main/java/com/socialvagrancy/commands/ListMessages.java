//===================================================================
// ListMessages.java
//      This function reads the logs and provides a list of all
//      error messages in the system.
//
// Created by Sean Snyder
//===================================================================

package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.TranslatedMessage;
import com.socialvagrancy.blackpearl.logs.utils.importers.GetTranslatedMessages;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ListMessages {
    public static ArrayList<TranslatedMessage> allTranslatedMessages(String dir_path) {
        String path = dir_path + "/pre/17_translate_messages.rb.txt";

        ArrayList<TranslatedMessage> message_list = GetTranslatedMessages.fromFile(path);

        for(TranslatedMessage message : message_list) {
            System.out.println(message.getSeverity() + ": " + message.getDescription());
        }

        return message_list;
    }

    public static ArrayList<TranslatedMessage> filteredList(String dir_path, String level, String after) {
        // Fetch a list of all translated messages and then apply filters.
        ArrayList<TranslatedMessage> message_list = allTranslatedMessages(dir_path);
    
        if(after != null) {
//            message_list = messagesNewerThan(after, message_list);
        }
    
        return message_list;
    }
    
    //===========================================
    // Private Functions
    //===========================================
    private static List<TranslatedMessage> messagesNewerThan(String date, ArrayList<TranslatedMessage> message_list) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ZonedDateTime ref_time = ZonedDateTime.parse(date, format);

        int counter = 0;

        for(TranslatedMessage message : message_list) {
            if(message.getCreated().isBefore(ref_time)) {
                break;
            }

            counter++;
        }

        return message_list.subList(0, counter);
    }
}
