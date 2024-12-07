//===================================================================
// TranslatedMessage.java
//      Description:
//          This model holds the message information defined in the 
//          pre/17_translate_message.rb.txt
//
// Created by Sean Snyder
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TranslatedMessage {
    private int id;
    private String severity;
    private String description;
    private String details;
    private ZonedDateTime created;

    //===========================================
    // Getters
    //===========================================
    public int getId() { return id; }
    public String getSeverity() { return severity; }
    public String getDescription() { return description; }
    public String getDetails() { return details; }
    public ZonedDateTime getCreated() { return created; }

    //===========================================
    // Setters
    //===========================================
    public void setId(int id) { this.id = id; }
    public void setSeverity(String severity) { this.severity = severity; }
    public void setDescription(String description) { this.description = description; }
    public void setDetails(String details) { this.details = details; }
    public void setCreated(String created) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        this.created = ZonedDateTime.parse(created, format);
    }
}
